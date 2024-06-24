package net.zepalesque.zenith.client.event.listener;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.biometint.BiomeTint;
import net.zepalesque.zenith.api.biometint.BiomeTints;

@Mod.EventBusSubscriber(modid = Zenith.MODID, value = Dist.CLIENT)
public class ClientTintListener {
    @SubscribeEvent
    public static void clearTints(ClientPlayerNetworkEvent.LoggingOut event) {
        BiomeTints.TINT_REGISTRY.forEach(BiomeTint::clear);
    }
}
