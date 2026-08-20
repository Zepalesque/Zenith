package net.zepalesque.zenith.util;

public class Tuples {
	/**
	 * A zero-element tuple. This essentially acts as a unit type.
	 * More accurately, a nullable/optional unit type, thanks to Java's usage of null everywhere.
	 */
	public record T0() implements Tuple {
		public T0 map() {
			return this;
		}
	}
	
	/**
	 * A one-element tuple.
	 * @param _0 The first element
	 * @param <U0> The type of the first element
	 */
	public record T1<U0>(U0 _0) implements Tuple { }
	
	/**
	 * A two-element tuple.
	 * @param _0 The first element
	 * @param _1 The second element
	 * @param <U0> The type of the first element
	 * @param <U1> The type of the second element
	 */
	public record T2<U0, U1>(U0 _0, U1 _1) implements Tuple { }
	
	/**
	 * A three-element tuple.
	 * @param _0 The first element
	 * @param _1 The second element
	 * @param _2 The third element
	 * @param <U0> The type of the first element
	 * @param <U1> The type of the second element
	 * @param <U2> The type of the third element
	 */
	public record T3<U0, U1, U2>(U0 _0, U1 _1, U2 _2) implements Tuple { }
	
	/**
	 * A four-element tuple.
	 * @param _0 The first element
	 * @param _1 The second element
	 * @param _2 The third element
	 * @param _3 The fourth element
	 * @param <U0> The type of the first element
	 * @param <U1> The type of the second element
	 * @param <U2> The type of the third element
	 * @param <U3> The type of the fourth element
	 */
	public record T4<U0, U1, U2, U3>(U0 _0, U1 _1, U2 _2, U3 _3) implements Tuple {}
	
	/**
	 * A five-element tuple.
	 * @param _0 The first element
	 * @param _1 The second element
	 * @param _2 The third element
	 * @param _3 The fourth element
	 * @param _4 The fifth element
	 * @param <U0> The type of the first element
	 * @param <U1> The type of the second element
	 * @param <U2> The type of the third element
	 * @param <U3> The type of the fourth element
	 * @param <U4> The type of the fifth element
	 */
	public record T5<U0, U1, U2, U3, U4>(U0 _0, U1 _1, U2 _2, U3 _3, U4 _4) implements Tuple {}
	
	/**
	 * A six-element tuple.
	 * @param _0 The first element
	 * @param _1 The second element
	 * @param _2 The third element
	 * @param _3 The fourth element
	 * @param _4 The fifth element
	 * @param _5 The sixth element
	 * @param <U0> The type of the first element
	 * @param <U1> The type of the second element
	 * @param <U2> The type of the third element
	 * @param <U3> The type of the fourth element
	 * @param <U4> The type of the fifth element
	 * @param <U5> The type of the sixth element
	 */
	public record T6<U0, U1, U2, U3, U4, U5>(U0 _0, U1 _1, U2 _2, U3 _3, U4 _4, U5 _5) implements Tuple {}
	
	/**
	 * A seven-element tuple.
	 * @param _0 The first element
	 * @param _1 The second element
	 * @param _2 The third element
	 * @param _3 The fourth element
	 * @param _4 The fifth element
	 * @param _5 The sixth element
	 * @param _6 The seventh element
	 * @param <U0> The type of the first element
	 * @param <U1> The type of the second element
	 * @param <U2> The type of the third element
	 * @param <U3> The type of the fourth element
	 * @param <U4> The type of the fifth element
	 * @param <U5> The type of the sixth element
	 * @param <U6> The type of the seventh element
	 */
	public record T7<U0, U1, U2, U3, U4, U5, U6>(U0 _0, U1 _1, U2 _2, U3 _3, U4 _4, U5 _5, U6 _6) implements Tuple {}
	
	/**
	 * An eight-element tuple.
	 * @param _0 The first element
	 * @param _1 The second element
	 * @param _2 The third element
	 * @param _3 The fourth element
	 * @param _4 The fifth element
	 * @param _5 The sixth element
	 * @param _6 The seventh element
	 * @param _7 The eighth element
	 * @param <U0> The type of the first element
	 * @param <U1> The type of the second element
	 * @param <U2> The type of the third element
	 * @param <U3> The type of the fourth element
	 * @param <U4> The type of the fifth element
	 * @param <U5> The type of the sixth element
	 * @param <U6> The type of the seventh element
	 * @param <U7> The type of the eighth element
	 */
	public record T8<U0, U1, U2, U3, U4, U5, U6, U7>(U0 _0, U1 _1, U2 _2, U3 _3, U4 _4, U5 _5, U6 _6, U7 _7) implements Tuple {}
	
	/**
	 * A 9+ element tuple.
	 * @param _0 The first element
	 * @param _1 The second element
	 * @param _2 The third element
	 * @param _3 The fourth element
	 * @param _4 The fifth element
	 * @param _5 The sixth element
	 * @param _6 The seventh element
	 * @param _7 The eighth element
	 * @param rest The tuple holding the other elements
	 * @param <U0> The type of the first element
	 * @param <U1> The type of the second element
	 * @param <U2> The type of the third element
	 * @param <U3> The type of the fourth element
	 * @param <U4> The type of the fifth element
	 * @param <U5> The type of the sixth element
	 * @param <U6> The type of the seventh element
	 * @param <U7> The type of the eighth element
	 * @param <R> The type of the tuple holding the other elements. Must implement the sealed interface {@link Tuple}
	 */
	public record TN<U0, U1, U2, U3, U4, U5, U6, U7, R extends Tuple>(U0 _0, U1 _1, U2 _2, U3 _3, U4 _4, U5 _5, U6 _6, U7 _7, R rest) implements Tuple {}
	
	/**
	 * An interface denoting that a type is a tuple. Cannot be implemented manually and is restricted to a small set of types.
	 */
	public sealed interface Tuple permits T0, T1, T2, T3, T4, T5, T6, T7, T8, TN {}

}
