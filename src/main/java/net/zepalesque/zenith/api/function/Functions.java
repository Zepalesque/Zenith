package net.zepalesque.zenith.api.function;

/**
 * <p>A variety of multi-argument {@link java.util.function.Function Functions}.</p>
 * <p>Use {@link java.util.function.Function Function}{@literal <T, R>} and {@link java.util.function.BiFunction BiFunction}{@literal <T, U, R>} in place of theoretical {@code "F1"} and "{@code "F2"} classes.</p>
 */
public class Functions {

    public interface F3<T1, T2, T3, R> {
        R accept(T1 t1, T2 t2, T3 t3);
    }

    public interface F4<T1, T2, T3, T4, R> {
        R accept(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    public interface F5<T1, T2, T3, T4, T5, R> {
        R accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

    public interface F6<T1, T2, T3, T4, T5, T6, R> {
        R accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
    }

    public interface F7<T1, T2, T3, T4, T5, T6, T7, R> {
        R accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
    }

    public interface F8<T1, T2, T3, T4, T5, T6, T7, T8, R> {
        R accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
    }
}
