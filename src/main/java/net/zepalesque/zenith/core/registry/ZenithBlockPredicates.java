package net.zepalesque.zenith.core.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.block.predicate.InBiomePredicate;
import net.zepalesque.zenith.api.block.predicate.NoisePredicate;
import net.zepalesque.zenith.core.Zenith;

public class ZenithBlockPredicates {

    public static final DeferredRegister<BlockPredicateType<?>> PREDICATES = DeferredRegister.create(BuiltInRegistries.BLOCK_PREDICATE_TYPE, Zenith.MODID);

    public static final DeferredHolder<BlockPredicateType<?>, BlockPredicateType<InBiomePredicate>> IN_BIOME =
            PREDICATES.register("in_biome", () -> () -> InBiomePredicate.CODEC);
    public static final DeferredHolder<BlockPredicateType<?>, BlockPredicateType<NoisePredicate>> PERLIN_NOISE =
            PREDICATES.register("perlin_noise", () -> () -> NoisePredicate.CODEC);
}
