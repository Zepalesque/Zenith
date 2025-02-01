package net.zepalesque.zenith.api.packconfig;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.CompositePackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.IoSupplier;
import net.zepalesque.zenith.core.Zenith;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ConfigAssembledPackResources extends AbstractPackResources {

    private final Map<Supplier<Boolean>, PackResources> packs;
    private final Map<String, Map<Supplier<Boolean>, PackResources>> assetNamespaces;
    private final Map<String, Map<Supplier<Boolean>, PackResources>> dataNamespaces;
    private final Path source;
    private static final Gson GSON = new Gson();
    protected ConfigAssembledPackResources(PackLocationInfo id, ImmutableMap<Supplier<Boolean>, PackResources> packs, Path source) {
        super(id);
        this.source = source;
        this.packs = packs;
        this.assetNamespaces = this.buildNamespaceMap(PackType.CLIENT_RESOURCES, packs);
        this.dataNamespaces = this.buildNamespaceMap(PackType.SERVER_DATA, packs);
    }

    private Map<String, Map<Supplier<Boolean>, PackResources>> buildNamespaceMap(PackType type, Map<Supplier<Boolean>, PackResources> packMap) {
        Map<String, Map<Supplier<Boolean>, PackResources>> map = new HashMap<>();
        for (Map.Entry<Supplier<Boolean>, PackResources> entry : packMap.entrySet()) {
            if (entry.getValue() != null) {
                PackResources pack = entry.getValue();
                for (String namespace : pack.getNamespaces(type)) {
                    map.computeIfAbsent(namespace, k -> new HashMap<>()).put(entry.getKey(), pack);
                }
            }
        }
        map.replaceAll((k, list) -> ImmutableMap.copyOf(list));
        return ImmutableMap.copyOf(map);
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... paths) {
        Path path = this.resolve(paths);
        return !Files.exists(path) ? null : IoSupplier.create(path);
    }

    protected Path resolve(String... paths) {
        Path path = this.source;

        for (String name : paths) {
            path = path.resolve(name);
        }
        return path;
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
        if (type == PackType.CLIENT_RESOURCES && location.getPath().matches("lang/.+\\.json")) {
            return handleTranslations(location);
        } else if (type == PackType.SERVER_DATA) {
            if (location.getPath().matches("data_maps/.+\\.json")) return handleDataMaps(location);
            else if (location.getPath().matches("tags/.+\\.json")) return handleTags(location);
        }
        for (PackResources pack : this.getCandidatePacks(type, location)) {
            IoSupplier<InputStream> ioSupplier = pack.getResource(type, location);
            if (ioSupplier != null) {
                return ioSupplier;
            }
        }
        return null;
    }

    @Override
    public void listResources(PackType type, String resourceNamespace, String paths, ResourceOutput resourceOutput) {
        for (PackResources delegate : this.getAvaliablePacks()) {
            delegate.listResources(type, resourceNamespace, paths, resourceOutput);
        }
    }

    public List<PackResources> getAvaliablePacks() {
        return packs.entrySet().stream().filter(entry -> entry.getKey().get()).map(Map.Entry::getValue).toList();
    }

    @Override
    public @NotNull Set<String> getNamespaces(PackType type) {
        return type == PackType.CLIENT_RESOURCES ? assetNamespaces.keySet() : dataNamespaces.keySet();
    }

    @Override
    public void close() {
        for (PackResources pack : packs.values()) {
            pack.close();
        }
    }

    private List<PackResources> getCandidatePacks(PackType type, ResourceLocation location) {
        if (type == PackType.CLIENT_RESOURCES) {
            Map<Supplier<Boolean>, PackResources> packsWithNamespace = this.assetNamespaces.get(location.getNamespace());
            return packsWithNamespace == null ? Collections.emptyList() : packsWithNamespace.entrySet().stream().filter(entry -> entry.getKey().get()).map(Map.Entry::getValue).toList();
        } else {
            Map<Supplier<Boolean>, PackResources> packsWithNamespace = this.dataNamespaces.get(location.getNamespace());
            return packsWithNamespace == null ? Collections.emptyList() : packsWithNamespace.entrySet().stream().filter(entry -> entry.getKey().get()).map(Map.Entry::getValue).toList();
        }
    }

    public String toString() {
        return String.format("%s: %s", this.getClass().getName(), source);
    }



    public record AssembledResourcesSupplier(ImmutableMap<Supplier<Boolean>, PackResources> packs, Path source) implements Pack.ResourcesSupplier {
        @Override
        public PackResources openPrimary(PackLocationInfo location) {
            return new ConfigAssembledPackResources(location, this.packs, this.source);
        }

        @Override
        public PackResources openFull(PackLocationInfo location, Pack.Metadata info) {
            PackResources packresources = this.openPrimary(location);
            List<String> list = info.overlays();
            if (list.isEmpty()) {
                return packresources;
            } else {
                List<PackResources> list1 = new ArrayList<>(list.size());

                for (String s : list) {
                    Path path = this.source.resolve(s);
                    list1.add(new ConfigAssembledPackResources(location, this.packs, path));
                }

                return new CompositePackResources(packresources, list1);
            }
        }
    }

    protected IoSupplier<InputStream> handleTranslations(ResourceLocation location) {
        return handleConflicts(PackType.CLIENT_RESOURCES, location, (combined, addend) -> addend.entrySet().forEach(entry -> combined.add(entry.getKey(), entry.getValue())));
    }

    protected IoSupplier<InputStream> handleTags(ResourceLocation location) {
        return handleConflicts(PackType.SERVER_DATA, location, (combined, addend) -> {
            for (String array : new String[] {"remove", "values"}) {
                if (addend.has(array)) {
                    JsonArray addendValues = addend.get(array).getAsJsonArray();
                    if (combined.has(array)) {
                        combined.add(array, new JsonArray());
                    }
                    JsonArray combinedValues = combined.get(array).getAsJsonArray();

                    addendValues.asList().forEach(combinedValues::add);
                }
            }
        });
    }

    protected IoSupplier<InputStream> handleDataMaps(ResourceLocation location) {
        return handleConflicts(PackType.SERVER_DATA, location, (combined, addend) -> {
            for (String array : new String[] {"remove", "values"}) {
                if (addend.has(array)) {
                    JsonObject addendValues = addend.get(array).getAsJsonObject();
                    if (combined.has(array)) {
                        combined.add(array, new JsonArray());
                    }
                    JsonObject combinedValues = combined.get(array).getAsJsonObject();

                    addendValues.entrySet().forEach(entry -> combinedValues.add(entry.getKey(), entry.getValue()));
                }
            }
        });
    }

    protected IoSupplier<InputStream> handleConflicts(PackType type, ResourceLocation location, BiConsumer<JsonObject, JsonObject> combiner) {
        JsonObject combined = new JsonObject();
        for (PackResources pack : getCandidatePacks(type, location)) {
            IoSupplier<InputStream> ioSupplier = pack.getResource(type, location);
            if (ioSupplier != null) {
                try {
                    JsonObject jsonobject = GSON.fromJson(new InputStreamReader(ioSupplier.get(), StandardCharsets.UTF_8), JsonObject.class);
                    combiner.accept(combined, jsonobject);
                } catch (Exception e) {
                    Zenith.LOGGER.error("Caught exception when trying to combine pack config resource files!", e);
                }
            }
        }

        if (combined.entrySet().isEmpty()) {
            return null;
        }
        String input = GSON.toJson(combined);
        return () -> new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    }
}
