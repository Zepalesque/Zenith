package net.zepalesque.zenith.world.density;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.Zenith;

public class ZenithDensityFunctions {
    public static final DeferredRegister<MapCodec<? extends DensityFunction>> FUNCTIONS = DeferredRegister.create(BuiltInRegistries.DENSITY_FUNCTION_TYPE, Zenith.MODID);

    public static DeferredHolder<MapCodec<? extends DensityFunction>, ? extends MapCodec<? extends DensityFunction>> PERLIN_NOISE =
            FUNCTIONS.register("perlin_noise", PerlinNoiseFunction.CODEC::codec);

    public static DeferredHolder<MapCodec<? extends DensityFunction>, ? extends MapCodec<? extends DensityFunction>> SQUARE_ROOT =
            FUNCTIONS.register("square_root", SquareRootFunction.CODEC::codec);

    public static DeferredHolder<MapCodec<? extends DensityFunction>, ? extends MapCodec<? extends DensityFunction>> CUBE_ROOT =
            FUNCTIONS.register("cube_root", CubeRootFunction.CODEC::codec);

}
