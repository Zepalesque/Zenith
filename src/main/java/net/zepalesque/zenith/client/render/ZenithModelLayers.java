package net.zepalesque.zenith.client.render;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.zenith.Zenith;

public class ZenithModelLayers {
    public static final ModelLayerLocation ZENITH_BOAT = register("zenith_boat");
    public static final ModelLayerLocation ZENITH_CHEST_BOAT = register("zenith_chest_boat");

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String name, String type) {
        return register(new ResourceLocation(Zenith.MODID, name), type);
    }

    private static ModelLayerLocation register(ResourceLocation location, String type) {
        return new ModelLayerLocation(location, type);
    }
}
