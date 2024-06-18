package net.zepalesque.zenith.world.density;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.Zenith;

public class ZenithDensityFunctions {
    public static final DeferredRegister<Codec<? extends DensityFunction>> FUNCTIONS = DeferredRegister.create(BuiltInRegistries.DENSITY_FUNCTION_TYPE, Zenith.MODID);

    public static DeferredHolder<Codec<? extends DensityFunction>, ? extends Codec<? extends DensityFunction>> PERLIN_NOISE =
            FUNCTIONS.register("perlin_noise", PerlinNoiseFunction.CODEC::codec);

    public static DeferredHolder<Codec<? extends DensityFunction>, ? extends Codec<? extends DensityFunction>> SQUARE_ROOT =
            FUNCTIONS.register("square_root", SquareRootFunction.CODEC::codec);

    public static DeferredHolder<Codec<? extends DensityFunction>, ? extends Codec<? extends DensityFunction>> CUBE_ROOT =
            FUNCTIONS.register("cube_root", CubeRootFunction.CODEC::codec);

}
