package net.zepalesque.zenith.util.lambda;

public class Functions {

    public static interface F3<T1, T2, T3, R> {

        R accept(T1 t1, T2 t2, T3 t3);
    }

    public static interface F4<T1, T2, T3, T4, R> {

        R accept(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    public static interface F5<T1, T2, T3, T4, T5, R> {

        R accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

    public static interface F6<T1, T2, T3, T4, T5, T6, R> {

        R accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
    }

    public static interface F7<T1, T2, T3, T4, T5, T6, T7, R> {

        R accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
    }

    public static interface F8<T1, T2, T3, T4, T5, T6, T7, T8, R> {

        R accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
    }
}
