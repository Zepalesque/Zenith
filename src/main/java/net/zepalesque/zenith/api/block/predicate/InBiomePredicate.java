package net.zepalesque.zenith.api.block.predicate;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.phys.shapes.Shapes;
import net.zepalesque.zenith.core.registry.ZenithBlockPredicates;

public record InBiomePredicate(HolderSet<Biome> biomes) implements BlockPredicate {
    public static MapCodec<InBiomePredicate> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                    RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(InBiomePredicate::biomes))
            .apply(builder, InBiomePredicate::new)
    );

    @Override
    public BlockPredicateType<?> type() {
        return ZenithBlockPredicates.IN_BIOME.get();
    }

    public boolean test(WorldGenLevel level, BlockPos pos) {
        return this.biomes.contains(level.getBiome(pos));
    }
}
