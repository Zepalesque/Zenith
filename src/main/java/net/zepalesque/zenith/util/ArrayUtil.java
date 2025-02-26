package net.zepalesque.zenith.util;

import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

import java.sql.Array;
import java.util.Arrays;
import java.util.function.Function;

// TODO: Expand, add methods for primitives, move to Zenith
public class ArrayUtil {

    public static <T> T[] generateContents(T[] array, Function<Integer, ? extends T> factory) {
        for (int index = 0; index < array.length; index++) {
            array[index] = factory.apply(index);
        }
        return array;
    }

    public static <T, O extends T> T[] copyFrom(T[] array, O[] toCopy, @Nullable T padding) {
        boolean shouldPad = array.length > toCopy.length;
        int split = shouldPad ? toCopy.length : array.length;
        System.arraycopy(toCopy, 0, array, 0, split);
        if (shouldPad) {
            Arrays.fill(array, split, array.length, padding);
        }
        return array;
    }

    // Uses Fisher-Yates algorithm
    public static <T> void shuffle(T[] array, RandomSource rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            T element = array[i];

            array[i] = array[index];
            array[index] = element;
        }
    }

    public static <T, A extends T, B extends T> T[] union(A[] arrayA, B[] arrayB) {
        if (arrayA.length == 0) return Arrays.copyOf(arrayB, arrayB.length);
        else if (arrayB.length == 0) return Arrays.copyOf(arrayA, arrayA.length);

        T[] array = Arrays.copyOf(arrayA, arrayA.length + arrayB.length);
        System.arraycopy(arrayB, 0, array, arrayA.length, arrayB.length);
        return array;
    }
}
