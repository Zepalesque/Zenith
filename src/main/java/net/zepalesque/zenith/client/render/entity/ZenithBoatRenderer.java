package net.zepalesque.zenith.client.render.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.zepalesque.zenith.client.render.ZenithModelLayers;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ZenithBoatRenderer extends BoatRenderer {
    private final Pair<ResourceLocation, ListModel<Boat>> skyrootBoatResource;

    public ZenithBoatRenderer(EntityRendererProvider.Context context, boolean chest, String modid, String wood) {
        super(context, chest);
        ResourceLocation boat_texture = new ResourceLocation(modid, "textures/entity/" + (chest ? "chest_" : "") + "boat/" + wood + ".png");
        this.skyrootBoatResource = Pair.of(boat_texture, chest ? new ChestBoatModel(context.bakeLayer(ZenithModelLayers.ZENITH_CHEST_BOAT)) : new BoatModel(context.bakeLayer(ZenithModelLayers.ZENITH_BOAT)));
    }

    @Nonnull
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(@Nonnull Boat boat) {
        return this.skyrootBoatResource;
    }
}
