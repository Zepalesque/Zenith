package net.zepalesque.zenith.core.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.api.world.tree.trunk.IntProviderTrunkPlacer;

public class ZenithTrunkPlacers {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, Zenith.MODID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<IntProviderTrunkPlacer>> INT_PROVIDER = TRUNK_PLACERS.register("int_provider_trunk", () -> new TrunkPlacerType<>(IntProviderTrunkPlacer.CODEC));

}
