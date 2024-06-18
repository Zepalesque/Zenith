package net.zepalesque.zenith.world.density;

import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public record CubeRootFunction(DensityFunction input) implements DensityFunction {

    public static final KeyDispatchDataCodec<CubeRootFunction> CODEC = KeyDispatchDataCodec.of(DensityFunction.HOLDER_HELPER_CODEC.fieldOf("argument").xmap(CubeRootFunction::new, CubeRootFunction::input));

    @Override
    public double compute(FunctionContext context) {
        return Math.cbrt(this.input().compute(context));
    }

    @Override
    public void fillArray(double[] array, ContextProvider provider) {
        this.input().fillArray(array, provider);

        for (int i = 0; i < array.length; ++i) {
            array[i] = Math.cbrt(array[i]);
        }
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return new CubeRootFunction(this.input.mapAll(visitor));
    }

    @Override
    public double minValue() {
        return Math.cbrt(this.input.minValue());
    }

    @Override
    public double maxValue() {
        return Math.cbrt(this.input.maxValue());
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
