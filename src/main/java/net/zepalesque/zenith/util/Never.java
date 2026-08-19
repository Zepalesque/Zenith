package net.zepalesque.zenith.util;

public final class Never {
	private Never() {
		throw new IllegalStateException("Never type is uninstantiable!");
	}
	
	@Override
	public boolean equals(Object obj) {
		throw new IllegalStateException("Never type is uninstantiable!");
	}
	
	@Override
	public int hashCode() {
		throw new IllegalStateException("Never type is uninstantiable!");
	}
	
	@Override
	public String toString() {
		throw new IllegalStateException("Never type is uninstantiable!");
	}
	
	public <T> T into() {
		throw new IllegalStateException("Never type is uninstantiable!");
	}
	
	public static <T extends RuntimeException> Never panic(T payload) {
		throw payload;
	}
}

