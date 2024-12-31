package net.zepalesque.zenith.api.function;

/**
 * <p>A variety of multi-argument {@link java.util.function.Predicate Predicates}.</p>
 * <p>Use {@link java.util.function.Predicate Predicate}{@literal <T>} or {@link java.util.function.BiPredicate BiPredicate}{@literal <T, U>} in place of theoretical {@code "P1"} and "{@code "P        2"} classes.</p>
 */
public class Predicates {

    public interface P3<T1, T2, T3> {
        boolean test(T1 t1, T2 t2, T3 t3);
    }

    public interface P4<T1, T2, T3, T4> {
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    public interface P5<T1, T2, T3, T4, T5> {
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

    public interface P6<T1, T2, T3, T4, T5, T6> {
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
    }

    public interface P7<T1, T2, T3, T4, T5, T6, T7> {
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
    }

    public interface P8<T1, T2, T3, T4, T5, T6, T7, T8> {
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
    }
}
