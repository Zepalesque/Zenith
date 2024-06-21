package net.zepalesque.zenith.world.feature.gen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.Zenith;

public class ZenithFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Zenith.MODID);

    public static DeferredHolder<Feature<?>, Feature<BlockWithPredicateFeature.Config>> BLOCK_WITH_PREDICATE = FEATURES.register("block_with_predicate", () -> new BlockWithPredicateFeature(BlockWithPredicateFeature.Config.CODEC));
    public static DeferredHolder<Feature<?>, Feature<SurfaceRuleLakeFeature.Config>> SURFACE_RULE_LAKE = FEATURES.register("surface_rule_lake", () -> new SurfaceRuleLakeFeature(SurfaceRuleLakeFeature.Config.CODEC));

}
