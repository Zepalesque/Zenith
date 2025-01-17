package net.zepalesque.zenith.api.block.predicate;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.zepalesque.zenith.core.registry.ZenithBlockPredicates;

public record InBiomePredicate(Either<HolderSet<Biome>, TagKey<Biome>> biomes) implements BlockPredicate {
    public static MapCodec<InBiomePredicate> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                    Codec.either(RegistryCodecs.homogeneousList(Registries.BIOME), TagKey.hashedCodec(Registries.BIOME)).fieldOf("biomes").forGetter(InBiomePredicate::biomes))
            .apply(builder, InBiomePredicate::new)
    );

    @Override
    public BlockPredicateType<?> type() {
        return ZenithBlockPredicates.IN_BIOME.get();
    }

    public boolean test(WorldGenLevel level, BlockPos pos) {
        Holder<Biome> biome = level.getBiome(pos);
        return this.biomes.map(holders -> holders.contains(biome), biome::is);
    }

    public static InBiomePredicate inTag(TagKey<Biome> tag) {
        return new InBiomePredicate(Either.right(tag));
    }

    public static InBiomePredicate inSet(HolderSet<Biome> set) {
        return new InBiomePredicate(Either.left(set));
    }
}
