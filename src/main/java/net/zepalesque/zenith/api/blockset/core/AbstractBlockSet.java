package net.zepalesque.zenith.api.blockset.core;

public abstract class AbstractBlockSet implements BlockSet {

    protected final DatagenHandler handler = new DatagenHandler();

    @Override
    public DatagenHandler handler() {
        return this.handler;
    }
}
