package net.zepalesque.zenith.util.function;

import net.zepalesque.zenith.util.Tuples;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

// none of this would be necessary if only java was designed better smh my head

/**
 * <p>A variety of multi-argument functions, ranging from three to eight inputs.</p>
 * @see Function
 * @see java.util.function.BiFunction BiFunction
 */
@SuppressWarnings("unused")
public class Functions {
    
    /**
     * A function with three inputs. Longer name would be {@code 'TriFunction'.}
     */
    public interface F3<T1, T2, T3, R> {
        
        /**
         * Applies this function to the given arguments.
         *
         * @param t1 the first function argument
         * @param t2 the second function argument
         * @param t3 the third function argument
         * @return the function result
         */
        R apply(T1 t1, T2 t2, T3 t3);
        
        default F3Tuple<T1, T2, T3, R> asTupleFn() {
            return new F3Tuple<>(this);
        }
        
        /**
         * Returns a composed function that first applies this function to
         * its input, and then applies the {@code after} function to the result.
         * If evaluation of either function throws an exception, it is relayed to
         * the caller of the composed function.
         *
         * @param <V>   the type of output of the {@code after} function, and of the
         *              composed function
         * @param after the function to apply after this function is applied
         * @return a composed function that first applies this function and then
         * applies the {@code after} function
         */
        default <V> F3<T1, T2, T3, V> andThen(@NotNull Function<? super R, ? extends V> after) {
            return (t1, t2, t3) -> after.apply(this.apply(t1, t2, t3));
        }
    }
    
    public record F3Tuple<T1, T2, T3, R>(F3<T1, T2, T3, R> f) implements Function<Tuples.T3<T1, T2, T3>, R> {
        
        @Override
        public R apply(Tuples.T3<T1, T2, T3> tuple) {
            return this.f.apply(tuple._0(), tuple._1(), tuple._2());
        }
    }
    
    /**
     * A function with four inputs. Longer name would be {@code 'QuatFunction'.}
     */
    public interface F4<T1, T2, T3, T4, R> {
        
        /**
         * Applies this function to the given arguments.
         *
         * @param t1 the first function argument
         * @param t2 the second function argument
         * @param t3 the third function argument
         * @param t4 the fourth function argument
         * @return the function result
         */
        R apply(T1 t1, T2 t2, T3 t3, T4 t4);
        
        default F4Tuple<T1, T2, T3, T4, R> asTupleFn() {
            return new F4Tuple<>(this);
        }
        
        /**
         * Returns a composed function that first applies this function to
         * its input, and then applies the {@code after} function to the result.
         * If evaluation of either function throws an exception, it is relayed to
         * the caller of the composed function.
         *
         * @param <V>   the type of output of the {@code after} function, and of the
         *              composed function
         * @param after the function to apply after this function is applied
         * @return a composed function that first applies this function and then
         * applies the {@code after} function
         */
        default <V> F4<T1, T2, T3, T4, V> andThen(@NotNull Function<? super R, ? extends V> after) {
            return (t1, t2, t3, t4) -> after.apply(this.apply(t1, t2, t3, t4));
        }
    }
    
    public record F4Tuple<T1, T2, T3, T4, R>(F4<T1, T2, T3, T4, R> f) implements Function<Tuples.T4<T1, T2, T3, T4>, R> {
        @Override
        public R apply(Tuples.T4<T1, T2, T3, T4> tuple) {
            return this.f.apply(tuple._0(), tuple._1(), tuple._2(), tuple._3());
        }
    }
    
    /**
     * A function with five inputs. Longer name would be {@code 'QuinFunction'.}
     */
    public interface F5<T1, T2, T3, T4, T5, R> {
        
        /**
         * Applies this function to the given arguments.
         *
         * @param t1 the first function argument
         * @param t2 the second function argument
         * @param t3 the third function argument
         * @param t4 the fourth function argument
         * @param t5 the fifth function argument
         * @return the function result
         */
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
        
        default F5Tuple<T1, T2, T3, T4, T5, R> asTupleFn() {
            return new F5Tuple<>(this);
        }
        
        /**
         * Returns a composed function that first applies this function to
         * its input, and then applies the {@code after} function to the result.
         * If evaluation of either function throws an exception, it is relayed to
         * the caller of the composed function.
         *
         * @param <V>   the type of output of the {@code after} function, and of the
         *              composed function
         * @param after the function to apply after this function is applied
         * @return a composed function that first applies this function and then
         * applies the {@code after} function
         */
        default <V> F5<T1, T2, T3, T4, T5, V> andThen(@NotNull Function<? super R, ? extends V> after) {
            return (t1, t2, t3, t4, t5) -> after.apply(this.apply(t1, t2, t3, t4, t5));
        }
    }
    
    public record F5Tuple<T1, T2, T3, T4, T5, R>(F5<T1, T2, T3, T4, T5, R> f) implements Function<Tuples.T5<T1, T2, T3, T4, T5>, R> {
        @Override
        public R apply(Tuples.T5<T1, T2, T3, T4, T5> tuple) {
            return this.f.apply(tuple._0(), tuple._1(), tuple._2(), tuple._3(), tuple._4());
        }
    }
    
    /**
     * A function with six inputs. Longer name would be {@code 'HexFunction'.}
     */
    public interface F6<T1, T2, T3, T4, T5, T6, R> {
        
        /**
         * Applies this function to the given arguments.
         *
         * @param t1 the first function argument
         * @param t2 the second function argument
         * @param t3 the third function argument
         * @param t4 the fourth function argument
         * @param t5 the fifth function argument
         * @param t6 the sixth function argument
         * @return the function result
         */
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
        
        default F6Tuple<T1, T2, T3, T4, T5, T6, R> asTupleFn() {
            return new F6Tuple<>(this);
        }
        
        /**
         * Returns a composed function that first applies this function to
         * its input, and then applies the {@code after} function to the result.
         * If evaluation of either function throws an exception, it is relayed to
         * the caller of the composed function.
         *
         * @param <V>   the type of output of the {@code after} function, and of the
         *              composed function
         * @param after the function to apply after this function is applied
         * @return a composed function that first applies this function and then
         * applies the {@code after} function
         */
        default <V> F6<T1, T2, T3, T4, T5, T6, V> andThen(@NotNull Function<? super R, ? extends V> after) {
            return (t1, t2, t3, t4, t5, t6) -> after.apply(this.apply(t1, t2, t3, t4, t5, t6));
        }
    }
    
    public record F6Tuple<T1, T2, T3, T4, T5, T6, R>(F6<T1, T2, T3, T4, T5, T6, R> f) implements Function<Tuples.T6<T1, T2, T3, T4, T5, T6>, R> {
        @Override
        public R apply(Tuples.T6<T1, T2, T3, T4, T5, T6> tuple) {
            return this.f.apply(tuple._0(), tuple._1(), tuple._2(), tuple._3(), tuple._4(), tuple._5());
        }
    }
    
    /**
     * A function with seven inputs. Longer name would be {@code 'SeptFunction'.}
     */
    public interface F7<T1, T2, T3, T4, T5, T6, T7, R> {
        
        /**
         * Applies this function to the given arguments.
         *
         * @param t1 the first function argument
         * @param t2 the second function argument
         * @param t3 the third function argument
         * @param t4 the fourth function argument
         * @param t5 the fifth function argument
         * @param t6 the sixth function argument
         * @param t7 the seventh function argument
         * @return the function result
         */
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
        
        default F7Tuple<T1, T2, T3, T4, T5, T6, T7, R> asTupleFn() {
            return new F7Tuple<>(this);
        }
        
        
        /**
         * Returns a composed function that first applies this function to
         * its input, and then applies the {@code after} function to the result.
         * If evaluation of either function throws an exception, it is relayed to
         * the caller of the composed function.
         *
         * @param <V>   the type of output of the {@code after} function, and of the
         *              composed function
         * @param after the function to apply after this function is applied
         * @return a composed function that first applies this function and then
         * applies the {@code after} function
         */
        default <V> F7<T1, T2, T3, T4, T5, T6, T7, V> andThen(@NotNull Function<? super R, ? extends V> after) {
            return (t1, t2, t3, t4, t5, t6, t7) -> after.apply(this.apply(t1, t2, t3, t4, t5, t6, t7));
        }
    }
    
    public record F7Tuple<T1, T2, T3, T4, T5, T6, T7, R>(F7<T1, T2, T3, T4, T5, T6, T7, R> f) implements Function<Tuples.T7<T1, T2, T3, T4, T5, T6, T7>, R> {
        @Override
        public R apply(Tuples.T7<T1, T2, T3, T4, T5, T6, T7> tuple) {
            return this.f.apply(tuple._0(), tuple._1(), tuple._2(), tuple._3(), tuple._4(), tuple._5(), tuple._6());
        }
    }
    
    /**
     * A function with eight inputs. Longer name would be {@code 'OctFunction'.}
     */
    public interface F8<T1, T2, T3, T4, T5, T6, T7, T8, R> {
        
        /**
         * Applies this function to the given arguments.
         *
         * @param t1 the first function argument
         * @param t2 the second function argument
         * @param t3 the third function argument
         * @param t4 the fourth function argument
         * @param t5 the fifth function argument
         * @param t6 the sixth function argument
         * @param t7 the seventh function argument
         * @param t8 the eighth function argument
         * @return the function result
         */
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
        
        /**
         * Returns a composed function that first applies this function to
         * its input, and then applies the {@code after} function to the result.
         * If evaluation of either function throws an exception, it is relayed to
         * the caller of the composed function.
         *
         * @param <V>   the type of output of the {@code after} function, and of the
         *              composed function
         * @param after the function to apply after this function is applied
         * @return a composed function that first applies this function and then
         * applies the {@code after} function
         */
        default <V> F8<T1, T2, T3, T4, T5, T6, T7, T8, V> andThen(@NotNull Function<? super R, ? extends V> after) {
            return (t1, t2, t3, t4, t5, t6, t7, t8) -> after.apply(this.apply(t1, t2, t3, t4, t5, t6, t7, t8));
        }
    }
    
    public record F8Tuple<T1, T2, T3, T4, T5, T6, T7, T8, R>(F8<T1, T2, T3, T4, T5, T6, T7, T8, R> f) implements Function<Tuples.T8<T1, T2, T3, T4, T5, T6, T7, T8>, R> {
        @Override
        public R apply(Tuples.T8<T1, T2, T3, T4, T5, T6, T7, T8> tuple) {
            return this.f.apply(tuple._0(), tuple._1(), tuple._2(), tuple._3(), tuple._4(), tuple._5(), tuple._6(), tuple._7());
        }
    }
    
    /**
     * A function that takes an {@code int} as an input and returns a {@code byte}.
     */
    public interface Int2ByteFunc {
        
        /**
         * Applies this function to the given argument.
         *
         * @param value the function argument
         * @return the function result
         */
        byte applyAsByte(int value);
    }
    
    /**
     * A function that takes an {@code int} as an input and returns a {@code short}.
     */
    public interface Int2ShortFunc {
        
        /**
         * Applies this function to the given argument.
         *
         * @param value the function argument
         * @return the function result
         */
        short applyAsShort(int value);
    }
    
    /**
     * A function that takes an {@code int} as an input and returns a {@code float}.
     */
    public interface Int2FloatFunc {
        
        /**
         * Applies this function to the given argument.
         *
         * @param value the function argument
         * @return the function result
         */
        float applyAsFloat(int value);
    }
    
    /**
     * A function that takes an {@code int} as an input and returns a {@code boolean}.
     */
    public interface Int2BoolFunc {
        
        /**
         * Applies this function to the given argument.
         *
         * @param value the function argument
         * @return the function result
         */
        boolean applyAsBoolean(int value);
        
    }
    
    /**
     * A function that takes an {@code int} as an input and returns a {@code char}.
     */
    public interface Int2CharFunc {
        
        /**
         * Applies this function to the given argument.
         *
         * @param value the function argument
         * @return the function result
         */
        char applyAsChar(int value);
    }
}
