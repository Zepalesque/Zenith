package net.zepalesque.zenith.api.block.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.zepalesque.zenith.api.noise.SeededPerlinNoiseHolder;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.core.registry.ZenithBlockPredicates;
import org.jetbrains.annotations.Nullable;

public class NoisePredicate implements BlockPredicate, SeededPerlinNoiseHolder<NoisePredicate> {

    public static MapCodec<NoisePredicate> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            NormalNoise.NoiseParameters.CODEC.fieldOf("noise").forGetter(predicate -> predicate.params),
            Codec.LONG.fieldOf("seed_offset").forGetter(predicate -> predicate.seedOffset),
            Codec.DOUBLE.fieldOf("min_threshold").forGetter(predicate -> predicate.minThreshold),
            Codec.DOUBLE.fieldOf("max_threshold").forGetter(predicate -> predicate.maxThreshold)
            ).apply(builder, NoisePredicate::new)
    );

    private final Holder<NormalNoise.NoiseParameters> params;
    private final double minThreshold;
    private final double maxThreshold;
    private final long seedOffset;

    @Nullable
    private PerlinNoise noise = null;


    public NoisePredicate(Holder<NormalNoise.NoiseParameters> params, long seedOffset, double minThreshold, double maxThreshold) {
        this.params = params;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
        this.seedOffset = seedOffset;
    }

    @Override
    public BlockPredicateType<?> type() {
        return ZenithBlockPredicates.PERLIN_NOISE.get();
    }

    @Override
    public boolean test(WorldGenLevel genLevel, BlockPos pos) {
        this.initialize(genLevel.getSeed());
        if (this.noise == null) Zenith.LOGGER.warn("NoisePredicate has no noise value, but we just initialized it. This should not be possible. Please report this to the Zenith issue tracker.");
        double value = this.noise.getValue(pos.getX(), 0.0, pos.getZ());

        return value <= this.maxThreshold && value >= this.minThreshold;
    }

    @Override
    public Holder<NormalNoise.NoiseParameters> params() {
        return this.params;
    }

    @Override
    public long seedOffset() {
        return this.seedOffset;
    }

    @Override
    public NoisePredicate initialize(PerlinNoise noise) {
        if (!this.initialized()) {
            this.noise = noise;
        }
        return this;
    }

    @Override
    public PerlinNoise noise() {
        return this.noise;
    }
}
