package net.zepalesque.zenith.event.listener;


import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.biometint.BiomeTint;
import net.zepalesque.zenith.api.biometint.BiomeTints;
import net.zepalesque.zenith.network.ZenithNetworking;
import net.zepalesque.zenith.network.packet.BiomeTintSyncPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Zenith.MODID)
public class BiomeTintListener {

    @SubscribeEvent
    public static void updateTints(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
            Registry<Biome> registry = player.level().registryAccess().registryOrThrow(Registries.BIOME);
            Map<ResourceLocation, Map<ResourceLocation, Integer>> map = new HashMap<>();
            BiomeTints.TINT_REGISTRY.forEach(tint -> {
                ResourceLocation loc = BiomeTints.TINT_REGISTRY.getKey(tint);
                Map<ResourceLocation, Integer> tints = tint.toSync().entrySet().stream().collect(Collectors.toMap(
                        entry -> entry.getKey().location(), Map.Entry::getValue
                ));
                map.put(loc, tints, tint.getOverride());
            });
            ZenithNetworking.sendToPlayer(new BiomeTintSyncPacket(map), player);
        }
    }


    @SubscribeEvent
    public static void clearTints(ClientPlayerNetworkEvent.LoggingOut event) {
        BiomeTints.TINT_REGISTRY.forEach(BiomeTint::clear);
    }
}
