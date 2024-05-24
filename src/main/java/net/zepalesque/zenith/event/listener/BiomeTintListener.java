package net.zepalesque.zenith.event.listener;


import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.biometint.BiomeTint;
import net.zepalesque.zenith.api.biometint.BiomeTints;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Zenith.MODID)
public class BiomeTintListener {

    @SubscribeEvent
    public static void updateTints(ClientPlayerNetworkEvent.LoggingIn event) {
        BiomeTints.TINT_REGISTRY.forEach(tint -> {
            tint.clear();
            Registry<Biome> registry = event.getPlayer().level().registryAccess().registryOrThrow(Registries.BIOME);
            for (Map.Entry<ResourceKey<Biome>, Integer> entry : registry.getDataMap(tint.getDataMap()).entrySet()) {
                Biome b = registry.get(entry.getKey());
                tint.addTint(b, entry.getValue());
            }
        });
    }

    @SubscribeEvent
    public static void clearTints(ClientPlayerNetworkEvent.LoggingOut event) {
        BiomeTints.TINT_REGISTRY.forEach(BiomeTint::clear);
    }
}
