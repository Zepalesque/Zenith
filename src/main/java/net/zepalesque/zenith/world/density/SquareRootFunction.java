package net.zepalesque.zenith.world.density;

import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public record SquareRootFunction(DensityFunction input) implements DensityFunction {

    public static final KeyDispatchDataCodec<SquareRootFunction> CODEC = KeyDispatchDataCodec.of(DensityFunction.HOLDER_HELPER_CODEC.fieldOf("argument").xmap(SquareRootFunction::new, SquareRootFunction::input));

    @Override
    public double compute(FunctionContext context) {
        return Math.sqrt(this.input().compute(context));
    }

    @Override
    public void fillArray(double[] array, ContextProvider provider) {
        this.input().fillArray(array, provider);

        for (int i = 0; i < array.length; ++i) {
            array[i] = Math.sqrt(array[i]);
        }
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return new SquareRootFunction(this.input.mapAll(visitor));
    }

    @Override
    public double minValue() {
        return Math.sqrt(Math.max(this.input.minValue(), 0D));
    }

    @Override
    public double maxValue() {
        return Math.sqrt(Math.max(this.input.maxValue(), 0D));
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
