package net.zepalesque.zenith.world.density;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.zepalesque.zenith.mixin.mixins.common.accessor.PerlinNoiseAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class PerlinNoiseFunction implements DensityFunction {

    public static final KeyDispatchDataCodec<PerlinNoiseFunction> CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(
            p_208798_ -> p_208798_.group(
                            NormalNoise.NoiseParameters.DIRECT_CODEC.fieldOf("noise").forGetter((func) -> func.params),
                            Codec.DOUBLE.fieldOf("xz_scale").forGetter((func) -> func.xzScale),
                            Codec.DOUBLE.fieldOf("y_scale").forGetter((func) -> func.yScale),
                            Codec.LONG.fieldOf("seed").forGetter((func) -> func.seed)
                    )
                    .apply(p_208798_, PerlinNoiseFunction::new)));

    @Nullable
    public PerlinNoise noise;
    public final NormalNoise.NoiseParameters params;
    private final long seed;
    private final double xzScale;
    private final double yScale;

    public PerlinNoiseFunction(NormalNoise.NoiseParameters params, double xzScale, double yScale, long seed) {
        this.seed = seed;
        this.params = params;
        this.xzScale = xzScale;
        this.yScale = yScale;
    }

    public PerlinNoiseFunction initialize(Function<Long, RandomSource> rand) {
        this.noise = PerlinNoise.create(rand.apply(this.seed), this.params.firstOctave(), this.params.amplitudes());
        return this;
    }

    public double compute(FunctionContext pContext) {
        if (this.noise == null) {
            throw new NullPointerException("Perlin noise has not been initialized yet!");
        } else {
            return this.noise
                    .getValue((double)pContext.blockX() * this.xzScale, (double)pContext.blockY() * this.yScale, (double)pContext.blockZ() * this.xzScale);
        }
    }

    @Override
    public void fillArray(double[] pArray, ContextProvider pContextProvider) {
        pContextProvider.fillAllDirectly(pArray, this);
    }

    @Override
    public DensityFunction mapAll(Visitor pVisitor) {
        return pVisitor.apply(this);
    }

    @Override
    public double minValue() {
        return -this.maxValue();
    }

    @Override
    public double maxValue() {
        return ((PerlinNoiseAccessor)this.noise).callMaxValue();
    }


    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }

    public record PerlinSeedSetter(long seed) implements DensityFunction.Visitor {

        @Override
        public DensityFunction apply(DensityFunction function) {
            if (function instanceof PerlinNoiseFunction pnf) {
                return pnf.initialize(offset -> createRandom(seed, offset));
            }
            return function;
        }

        public static RandomSource createRandom(long seed, long seedOffset) {
            return new XoroshiroRandomSource(seed + seedOffset);
        }
    }

}
