package net.zepalesque.zenith.api.noise;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface SeededPerlinNoiseHolder<T extends SeededPerlinNoiseHolder<T>> {

    Holder<NormalNoise.NoiseParameters> params();

    long seedOffset();

    default boolean initialized() {
        return this.noise() != null;
    }

    default double compute(double x, double y, double z) {
        PerlinNoise noise = this.noise();
        if (noise != null) {
            return noise.getValue(x, y, z);
        } else return Double.NaN;
    }

    // Should set the noise if it has not yet been set
    T initialize(PerlinNoise noise);

    default T initialize(Function<Long, RandomSource> factory) {
        this.ensureParamsBound();
        PerlinNoise noise = PerlinNoise.create(factory.apply(this.seedOffset()), this.params().value().firstOctave(), this.params().value().amplitudes());
        return this.initialize(noise);
    }

    default T initialize(long worldSeed) {
        if (this.initialized()) return self();
        return this.initialize(offset -> new XoroshiroRandomSource(worldSeed + offset));
    }

    default void ensureParamsBound() {
        if (!this.params().isBound()) throw new IllegalStateException("Can't initialize perlin noise! Parameter holder is unbound!");
    }

    // Will return null if this has not yet been initialized
    @Nullable
    PerlinNoise noise();

    @SuppressWarnings("unchecked")
    default T self() {
        return (T) this;
    }

}
