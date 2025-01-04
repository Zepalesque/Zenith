package net.zepalesque.zenith.api.packconfig;

import com.google.common.collect.ImmutableMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.zepalesque.zenith.core.Zenith;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PackConfig {

    private final ResourceLocation id;
    private final PackType type;
    private final HashMap<Supplier<Boolean>, PackResources> resources = new HashMap<>();
    private final String folder;
    private final boolean hideInMenu;
    private boolean locked = false;

    public PackConfig(ResourceLocation id, PackType type, boolean hideInMenu) {
        this.id = id;
        this.type = type;
        this.folder = switch (type) {
            case SERVER_DATA -> "data/";
            case CLIENT_RESOURCES -> "resource/";
        };
        this.hideInMenu = hideInMenu;
    }

    public PackConfig(ResourceLocation id, PackType type) {
        this(id, type, true);
    }

    public ConfigAssembledPackResources.AssembledResourcesSupplier generate(Path path) {
        ImmutableMap<Supplier<Boolean>, PackResources> builder = ImmutableMap.copyOf(resources);
        locked = true;
        return new ConfigAssembledPackResources.AssembledResourcesSupplier(builder, path);
    }

    public PathPackResources createPack(String path, String id) {
        Path resource = ModList.get().getModFileById(this.id.getNamespace()).getFile().findResource("packs/" + this.folder + path + id);
        PackLocationInfo loc = new PackLocationInfo(id, Component.empty(), PackSource.BUILT_IN, Optional.empty());
        return new PathPackResources(loc, resource);
    }

    public <B, T extends ModConfigSpec.ConfigValue<B>> T register(T config, String path, String id, Predicate<B> predicate) {
        if (!locked) {
            resources.putIfAbsent(() -> predicate.test(config.get()), createPack(path, id));
            Zenith.LOGGER.info("Registered config {}{}{} for pack {}...", this.folder, path, id, this.id);
        } else {
            Zenith.LOGGER.warn("Attempted to register config {}{}{} for pack {} after locking was already complete!", this.folder, path, id, this.id);
        }
        return config;
    }

    public <B, T extends ModConfigSpec.ConfigValue<B>> T register(T config, String id, Predicate<B> predicate) {
        return register(config, "", id, predicate);
    }

    public <T extends ModConfigSpec.ConfigValue<Boolean>> T register(T config, String path, String id, boolean predicate) {
        return register(config, path, id, bool -> bool == predicate);
    }

    public <T extends ModConfigSpec.ConfigValue<Boolean>> T register(T config, String id, boolean predicate) {
        return register(config, "", id, predicate);
    }

    public <T extends ModConfigSpec.ConfigValue<Boolean>> T register(T config, String path, String id) {
        return register(config, path, id, true);
    }

    public <T extends ModConfigSpec.ConfigValue<Boolean>> T register(T config, String id) {
        return register(config, "", id);
    }


    public void setup(AddPackFindersEvent event) {
        if (event.getPackType() == this.type) {
            PackUtils.setupPack(event, this.id, this.folder, true, this.hideInMenu, this::generate);
        }
    }
}
