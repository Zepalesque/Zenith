package net.zepalesque.zenith.client;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.client.render.ZenithModelLayers;

@Mod.EventBusSubscriber(
        modid = Zenith.MODID,
        value = Dist.CLIENT,
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ZenithClient {

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ZenithModelLayers.ZENITH_BOAT, BoatModel::createBodyModel);
        event.registerLayerDefinition(ZenithModelLayers.ZENITH_CHEST_BOAT, ChestBoatModel::createBodyModel);

    }
}
