package net.zepalesque.zenith.util;

import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Function;

public class ArrayUtil {
    
    public static <T> T[] generateContents(T[] array, Function<Integer, ? extends T> factory) {
        for (int index = 0; index < array.length; index++) array[index] = factory.apply(index);
        return array;
    }
    
    public static int[] generateContents(int[] array, Function<Integer, Integer> factory) {
        for (int index = 0; index < array.length; index++) array[index] = factory.apply(index);
        return array;
    }
    
    public static byte[] generateContents(byte[] array, Function<Integer, Byte> factory) {
        for (int index = 0; index < array.length; index++) array[index] = factory.apply(index);
        return array;
    }
    
    public static short[] generateContents(short[] array, Function<Integer, Short> factory) {
        for (int index = 0; index < array.length; index++) array[index] = factory.apply(index);
        return array;
    }
    
    public static long[] generateContents(long[] array, Function<Integer, Long> factory) {
        for (int index = 0; index < array.length; index++) array[index] = factory.apply(index);
        return array;
    }
    
    public static float[] generateContents(float[] array, Function<Integer, Float> factory) {
        for (int index = 0; index < array.length; index++) array[index] = factory.apply(index);
        return array;
    }
    
    public static double[] generateContents(double[] array, Function<Integer, Double> factory) {
        for (int index = 0; index < array.length; index++) array[index] = factory.apply(index);
        return array;
    }
    
    public static boolean[] generateContents(boolean[] array, Function<Integer, Boolean> factory) {
        for (int index = 0; index < array.length; index++) array[index] = factory.apply(index);
        return array;
    }
    
    public static char[] generateContents(char[] array, Function<Integer, Character> factory) {
        for (int index = 0; index < array.length; index++) array[index] = factory.apply(index);
        return array;
    }
    
    public static <T, O extends T> T[] copyFrom(T[] array, O[] toCopy, @Nullable T padding) {
        boolean shouldPad = array.length > toCopy.length;
        int split = shouldPad ? toCopy.length : array.length;
        System.arraycopy(toCopy, 0, array, 0, split);
        if (shouldPad) Arrays.fill(array, split, array.length, padding);
        return array;
    }
    
    public static int[] copyFrom(int[] array, int[] toCopy, @Nullable Integer padding) {
        boolean shouldPad = array.length > toCopy.length;
        int split = shouldPad ? toCopy.length : array.length;
        System.arraycopy(toCopy, 0, array, 0, split);
        if (shouldPad) Arrays.fill(array, split, array.length, padding == null ? 0 : padding);
        return array;
    }
    
    public static byte[] copyFrom(byte[] array, byte[] toCopy, @Nullable Byte padding) {
        boolean shouldPad = array.length > toCopy.length;
        int split = shouldPad ? toCopy.length : array.length;
        System.arraycopy(toCopy, 0, array, 0, split);
        if (shouldPad) Arrays.fill(array, split, array.length, padding == null ? 0 : padding);
        return array;
    }
    
    public static short[] copyFrom(short[] array, short[] toCopy, @Nullable Short padding) {
        boolean shouldPad = array.length > toCopy.length;
        int split = shouldPad ? toCopy.length : array.length;
        System.arraycopy(toCopy, 0, array, 0, split);
        if (shouldPad) Arrays.fill(array, split, array.length, padding == null ? 0 : padding);
        return array;
    }
    
    public static long[] copyFrom(long[] array, long[] toCopy, @Nullable Long padding) {
        boolean shouldPad = array.length > toCopy.length;
        int split = shouldPad ? toCopy.length : array.length;
        System.arraycopy(toCopy, 0, array, 0, split);
        if (shouldPad) Arrays.fill(array, split, array.length, padding == null ? 0L : padding);
        return array;
    }
    
    public static float[] copyFrom(float[] array, float[] toCopy, @Nullable Float padding) {
        boolean shouldPad = array.length > toCopy.length;
        int split = shouldPad ? toCopy.length : array.length;
        System.arraycopy(toCopy, 0, array, 0, split);
        if (shouldPad) Arrays.fill(array, split, array.length, padding == null ? 0F : padding);
        return array;
    }
    
    public static double[] copyFrom(double[] array, double[] toCopy, @Nullable Double padding) {
        boolean shouldPad = array.length > toCopy.length;
        int split = shouldPad ? toCopy.length : array.length;
        System.arraycopy(toCopy, 0, array, 0, split);
        if (shouldPad) Arrays.fill(array, split, array.length, padding == null ? 0D : padding);
        return array;
    }
    
    public static boolean[] copyFrom(boolean[] array, boolean[] toCopy, @Nullable Boolean padding) {
        boolean shouldPad = array.length > toCopy.length;
        int split = shouldPad ? toCopy.length : array.length;
        System.arraycopy(toCopy, 0, array, 0, split);
        if (shouldPad) Arrays.fill(array, split, array.length, padding != null && padding);
        return array;
    }
    
    public static char[] copyFrom(char[] array, char[] toCopy, @Nullable Character padding) {
        boolean shouldPad = array.length > toCopy.length;
        int split = shouldPad ? toCopy.length : array.length;
        System.arraycopy(toCopy, 0, array, 0, split);
        if (shouldPad) Arrays.fill(array, split, array.length, padding == null ? 0 : padding);
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
    
    public static void shuffle(int[] array, RandomSource rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int element = array[index];
            
            array[index] = array[i];
            array[i] = element;
        }
    }
    
    public static void shuffle(byte[] array, RandomSource rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            byte element = array[index];
            
            array[index] = array[i];
            array[i] = element;
        }
    }
    
    public static void shuffle(short[] array, RandomSource rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            short element = array[index];
            
            array[index] = array[i];
            array[i] = element;
        }
    }
    
    public static void shuffle(long[] array, RandomSource rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            long element = array[index];
            
            array[index] = array[i];
            array[i] = element;
        }
    }
    
    public static void shuffle(float[] array, RandomSource rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            float element = array[index];
            
            array[index] = array[i];
            array[i] = element;
        }
    }
    
    public static void shuffle(double[] array, RandomSource rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            double element = array[index];
            
            array[index] = array[i];
            array[i] = element;
        }
    }
    
    public static void shuffle(boolean[] array, RandomSource rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            boolean element = array[index];
            
            array[index] = array[i];
            array[i] = element;
        }
    }
    
    public static void shuffle(char[] array, RandomSource rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            char element = array[index];
            
            array[index] = array[i];
            array[i] = element;
        }
    }
}
