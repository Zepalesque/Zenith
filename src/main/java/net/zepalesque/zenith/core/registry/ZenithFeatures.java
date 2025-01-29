package net.zepalesque.zenith.core.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.world.feature.gen.ExtendableStateListBlockFeature;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.api.world.feature.gen.BlockWithPredicateFeature;
import net.zepalesque.zenith.api.world.feature.gen.LargeRockFeature;
import net.zepalesque.zenith.api.world.feature.gen.LakeWithFloorFeature;

public class ZenithFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Zenith.MODID);

    public static DeferredHolder<Feature<?>, Feature<BlockWithPredicateFeature.Config>> BLOCK_WITH_PREDICATE = FEATURES.register("block_with_predicate", () -> new BlockWithPredicateFeature(BlockWithPredicateFeature.Config.CODEC));
    public static DeferredHolder<Feature<?>, Feature<LakeWithFloorFeature.Config>> SURFACE_RULE_LAKE = FEATURES.register("surface_rule_lake", () -> new LakeWithFloorFeature(LakeWithFloorFeature.Config.CODEC));
    public static DeferredHolder<Feature<?>, Feature<LargeRockFeature.Config>> LARGE_ROCK = FEATURES.register("large_rock", () -> new LargeRockFeature(LargeRockFeature.Config.CODEC));
    public static DeferredHolder<Feature<?>, Feature<ExtendableStateListBlockFeature.Config>> EXTENDABLE_STATE_LIST_BLOCK = FEATURES.register("extendable_state_list", () -> new ExtendableStateListBlockFeature(ExtendableStateListBlockFeature.Config.CODEC));
}
