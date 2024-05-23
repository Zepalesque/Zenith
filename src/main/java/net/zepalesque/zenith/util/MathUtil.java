package net.zepalesque.zenith.util;

import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class MathUtil {


    public static int clampedLerpInt(float delta, int min, int max) {
        return Mth.clamp(Mth.lerpInt(delta, min, max), min, max);
    }

    public static int toNearestEven(float f) {
        int floored = Mth.floor(f);
        return (floored % 2) == 0 ? floored : floored + 1;
    }

    public static int toNearestEven(double d) {
        int floored = Mth.floor(d);
        return (floored % 2) == 0 ? floored : floored + 1;
    }

    public static double nextDouble(double bounds, RandomSource random) {
        return random.nextDouble() * bounds;
    }

    public static float nextFloat(float bounds, RandomSource random) {
        return random.nextFloat() * bounds;
    }

    public static double nextDouble(double min, double max, RandomSource random) {
        return min + (random.nextDouble() * (max - min));
    }

    public static float nextFloat(float min, float max, RandomSource random) {
        return min + (random.nextFloat() * (max - min));
    }

}
