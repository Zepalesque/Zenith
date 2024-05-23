package net.zepalesque.zenith.util.lambda;

public class Consumers {

    public static interface C3<T1, T2, T3> {

        void accept(T1 t1, T2 t2, T3 t3);
    }

    public static interface C4<T1, T2, T3, T4> {

        void accept(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    public static interface C5<T1, T2, T3, T4, T5> {

        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

    public static interface C6<T1, T2, T3, T4, T5, T6> {

        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
    }

    public static interface C7<T1, T2, T3, T4, T5, T6, T7> {

        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
    }

    public static interface C8<T1, T2, T3, T4, T5, T6, T7, T8> {

        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
    }
}
