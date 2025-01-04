package net.zepalesque.zenith.api.packconfig;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PackUtils {

    public static void setupPack(AddPackFindersEvent event, String modid, String path, String id, boolean required, boolean hidden, Function<Path, Pack.ResourcesSupplier> packBuilder) {
        PackLocationInfo loc = new PackLocationInfo(id, Component.translatable("pack." + modid + "." + id + ".title"), PackSource.BUILT_IN, Optional.empty());
        Path resourcePath = ModList.get().getModFileById(modid).getFile().findResource("packs/" + modid + "/" + path);
        PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack." + modid + "." + id + ".description"),
                SharedConstants.getCurrentVersion().getPackVersion(event.getPackType()));
        Pack.Metadata meta = new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), hidden);
        Pack.ResourcesSupplier resources = packBuilder.apply(resourcePath);
        event.addRepositorySource((source) ->
                source.accept(new Pack(
                        loc,
                        resources,
                        meta,
                        new PackSelectionConfig(required, Pack.Position.TOP, false))
                ));

    }

    public static void setupPack(AddPackFindersEvent event, String modid, String path, String id, boolean required, boolean hidden) {
        setupPack(event, modid, path, id, required, hidden, PathPackResources.PathResourcesSupplier::new);
    }

    public static void setupPack(AddPackFindersEvent event, ResourceLocation location, String folder, boolean required, boolean hidden, Function<Path, Pack.ResourcesSupplier> packBuilder) {
        String path = location.getPath();
        setupPack(event, location.getNamespace(), folder + path, path, required, hidden, packBuilder);
    }
}
