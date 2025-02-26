package net.zepalesque.zenith.api.world.density;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.zepalesque.zenith.api.noise.SeededPerlinNoiseHolder;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.mixin.mixins.common.accessor.PerlinNoiseAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class PerlinNoiseFunction implements DensityFunction, SeededPerlinNoiseHolder<PerlinNoiseFunction> {

    public static final KeyDispatchDataCodec<PerlinNoiseFunction> CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(
            p_208798_ -> p_208798_.group(
                            NormalNoise.NoiseParameters.CODEC.fieldOf("noise").forGetter((func) -> func.params),
                            Codec.DOUBLE.fieldOf("xz_scale").forGetter((func) -> func.xzScale),
                            Codec.DOUBLE.fieldOf("y_scale").forGetter((func) -> func.yScale),
                            Codec.LONG.fieldOf("seed_offset").forGetter((func) -> func.seedOffset)
                    )
                    .apply(p_208798_, PerlinNoiseFunction::new)));

    @Nullable
    public PerlinNoise noise = null;
    private static final Map<Long, PerlinNoiseVisitor> VISITORS = new HashMap<>();
    // This is used before the seed is initialized, for methods such as DensityFunction#maxValue
    private final PerlinNoise fakeNoise;
    public final Holder<NormalNoise.NoiseParameters> params;
    private final long seedOffset;
    private final double xzScale;
    private final double yScale;

    public PerlinNoiseFunction(Holder<NormalNoise.NoiseParameters> params, double xzScale, double yScale, long seedOffset) {
        this.seedOffset = seedOffset;
        this.params = params;
        this.xzScale = xzScale;
        this.yScale = yScale;
        // Don't get holder value when in datagen, use placeholder noise in that case
        if (params.isBound()) this.fakeNoise = PerlinNoise.create(new XoroshiroRandomSource(seedOffset), params.value().firstOctave(), params.value().amplitudes());
        else {
            this.fakeNoise = PerlinNoise.create(new XoroshiroRandomSource(seedOffset), 1, 1D);
            Zenith.LOGGER.debug("PerlinNoiseFunction could not initialize fake noise with given noise parameters! Hopefully this means we are in datagen...");
        }
    }

    public double compute(FunctionContext context) {
        if (!this.initialized()) {
            throw new NullPointerException("PerlinNoiseFunction has not been initialized yet! Please initialize by running mapAll on this function or a parent function with a PerlinNoiseVisitor!");
        } else {
            return this.compute((double)context.blockX() * this.xzScale, (double)context.blockY() * this.yScale, (double)context.blockZ() * this.xzScale);
        }
    }

    @Override
    public void fillArray(double[] array, ContextProvider contextProvider) {
        contextProvider.fillAllDirectly(array, this);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return visitor.apply(this);
    }


    @Override
    public double minValue() {
        return -this.maxValue();
    }

    @Override
    public double maxValue() {
        if (this.noise != null) {
            return ((PerlinNoiseAccessor)this.noise).callMaxValue();
        } else {
            return ((PerlinNoiseAccessor)this.fakeNoise).callMaxValue();
        }
    }

    public static PerlinNoiseVisitor createOrGetVisitor(long worldSeed) {
        return VISITORS.computeIfAbsent(worldSeed, seed -> new PerlinNoiseVisitor(noise -> {
            if (noise.initialized()) {
                return noise;
            } else {
                return noise.initialize(seed);
            }
        }));
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
    public PerlinNoiseFunction initialize(PerlinNoise noise) {
        if (!this.initialized()) {
            this.noise = noise;
        }
        return this;
    }

    @Override
    @Nullable
    public PerlinNoise noise() {
        return this.noise;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }

    public record PerlinNoiseVisitor(UnaryOperator<PerlinNoiseFunction> operator) implements DensityFunction.Visitor {
        @Override
        public DensityFunction apply(DensityFunction function) {
            if (function instanceof PerlinNoiseFunction pnf) {
                return operator.apply(pnf);
            }
            return function;
        }
    }
}
