package net.zepalesque.zenith.world.tree.trunk;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.Zenith;

public class ZenithTrunkPlacers {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, Zenith.MODID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<IntProviderTrunkPlacer>> INT_PROVIDER = TRUNK_PLACERS.register("int_provider_trunk", () -> new TrunkPlacerType<>(IntProviderTrunkPlacer.CODEC));

}
