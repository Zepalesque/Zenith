package net.zepalesque.zenith.util;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Objects;

public class Range {
	
	// i wish java had value types so this could be one
	public static final class Int implements Iterable<Integer> {
		private final int minInc;
		private final int maxExc;
		
		@NotNull
		private Int(int minInc, int maxExc) {
			this.minInc = minInc;
			this.maxExc = maxExc;
		}
		
		@NotNull
		public static Int incExc(int from, int to) {
			return new Int(from, to);
		}
		
		@NotNull
		public static Int excInc(int from, int to) {
			var a = from < to ? from + 1 : from - 1;
			var b = from < to ? to + 1 : from - 1;
			return new Int(a, b);
		}
		
		@NotNull
		public static Int excExc(int from, int to) {
			var a = from < to ? from + 1 : from - 1;
			return new Int(a, to);
		}
		
		@NotNull
		public static Int incInc(int from, int to) {
			var b = from < to ? to + 1 : from - 1;
			return new Int(from, b);
		}
		
		@NotNull
		@Override
			public IntRangeIter iterator() {
				return new IntRangeIter(this.minInc, this.maxExc, this.minInc < this.maxExc);
			}
		
		public int minInc() {
			return this.minInc;
		}
		
		public int maxExc() {
			return this.maxExc;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (Int) obj;
			return this.minInc == that.minInc &&
				this.maxExc == that.maxExc;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(this.minInc, this.maxExc);
		}
		
		@Override
		public String toString() {
			return this.minInc + ".." + this.maxExc;
		}
		
	}
	
	public static final class IntRangeIter implements Iterator<Integer> {
		private int minInc;
		private final int maxExc;
		private final boolean forwards;

		public IntRangeIter(int minInc, int maxExc, boolean forwards) {
			this.minInc = minInc;
			this.maxExc = maxExc;
			this.forwards = forwards;
		}
			
			@Override
			public boolean hasNext() {
				return this.forwards
					? this.minInc < this.maxExc
					: this.minInc > this.maxExc;
			}
			
			// Java really should've just done Iterator as Optional<T> next() but oh well
			@Override
			public Integer next() {
				return this.forwards
					? this.minInc++
					: this.minInc--;
			}
		
		@Override
		public boolean equals(Object obj) {
				if (obj == this) return true;
				if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (IntRangeIter) obj;
			return this.minInc == that.minInc &&
				this.maxExc == that.maxExc;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(this.minInc, this.maxExc, this.forwards);
		}
		
		@Override
		public String toString() {
			return "IntRangeIter[" +
				"minInc=" + this.minInc + ", " +
				"maxExc=" + this.maxExc + ", " +
				"forwards=" + this.forwards + ']';
		}
		
	}
	
	
}
