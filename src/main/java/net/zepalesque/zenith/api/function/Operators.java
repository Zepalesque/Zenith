package net.zepalesque.zenith.api.function;

import java.util.function.Function;

public class Operators {

    public interface O3<T> extends Functions.F3<T, T, T, T> {}

    public interface O4<T> extends Functions.F4<T, T, T, T, T> {}

    public interface O5<T> extends Functions.F5<T, T, T, T, T, T> {}

    public interface O6<T> extends Functions.F6<T, T, T, T, T, T, T> {}

    public interface O7<T> extends Functions.F7<T, T, T, T, T, T, T, T> {}

    public interface O8<T> extends Functions.F8<T, T, T, T, T, T, T, T, T> {}
}
