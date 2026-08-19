package net.zepalesque.zenith.util;

import java.util.stream.Stream;

public class Tuples {
	/**
	 * A zero-element tuple. This essentially acts as a unit type.
	 * More accurately, a nullable/optional unit type, thanks to Java's usage of null everywhere.
	 */
	public record T0() implements Tuple {}
	
	/**
	 * A one-element tuple.
	 * @param a The first element
	 * @param <U0> The type of the first element
	 */
	// TODO: Map functions?
	public record T1<U0>(U0 a) implements Tuple {}
	
	public record T2<U0, U1>(U0 a, U1 b) implements Tuple {}
	
	public record T3<U0, U1, U2>(U0 a, U1 b, U2 c) implements Tuple {}
	
	public record T4<U0, U1, U2, U3>(U0 a, U1 b, U2 c, U3 d) implements Tuple {}
	
	public record T5<U0, U1, U2, U3, U4>(U0 a, U1 b, U2 c, U3 d, U4 e) implements Tuple {}
	
	public record T6<U0, U1, U2, U3, U4, U5>(U0 a, U1 b, U2 c, U3 d, U4 e, U5 f) implements Tuple {}
	
	public record T7<U0, U1, U2, U3, U4, U5, U6>(U0 a, U1 b, U2 c, U3 d, U4 e, U5 f, U6 g) implements Tuple {}
	
	public record T8<U0, U1, U2, U3, U4, U5, U6, U7>(U0 a, U1 b, U2 c, U3 d, U4 e, U5 f, U6 g, U7 h) implements Tuple {}
	
	public record TMore<U0, U1, U2, U3, U4, U5, U6, U7, R extends Tuple>(U0 a, U1 b, U2 c, U3 d, U4 e, U5 f, U6 g, U7 h, R rest) implements Tuple {}
	
	public sealed interface Tuple permits T0, T1, T2, T3, T4, T5, T6, T7, T8, TMore {}

}
