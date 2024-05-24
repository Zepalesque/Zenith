package net.zepalesque.zenith.world.feature.gen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.world.feature.gen.config.PredicateStateConfig;

public class ZenithFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Zenith.MODID);

    public static DeferredHolder<Feature<?>, Feature<PredicateStateConfig>> TEST_BELOW_BLOCK = FEATURES.register("test_below_block", () -> new TestBelowBlockFeature(PredicateStateConfig.CODEC));
    public static DeferredHolder<Feature<?>, Feature<PredicateStateConfig>> TEST_AT_BLOCK = FEATURES.register("test_at_block", () -> new TestAtBlockFeature(PredicateStateConfig.CODEC));

}
