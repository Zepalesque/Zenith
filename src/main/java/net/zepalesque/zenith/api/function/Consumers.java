package net.zepalesque.zenith.api.function;

/**
 * <p>A variety of multi-argument {@link java.util.function.Consumer Consumers}.</p>
 * <p>Use {@link java.util.function.Consumer Consumer}{@literal <T>} and {@link java.util.function.BiConsumer BiConsumer}{@literal <T, U>} in place of theoretical {@code "C1"} and "{@code "C2"} classes.</p>
 */
public class Consumers {

    public interface C3<T1, T2, T3> {
        void accept(T1 t1, T2 t2, T3 t3);
    }

    public interface C4<T1, T2, T3, T4> {
        void accept(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    public interface C5<T1, T2, T3, T4, T5> {
        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

    public interface C6<T1, T2, T3, T4, T5, T6> {
        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
    }

    public interface C7<T1, T2, T3, T4, T5, T6, T7> {
        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
    }

    public interface C8<T1, T2, T3, T4, T5, T6, T7, T8> {
        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
    }
}
