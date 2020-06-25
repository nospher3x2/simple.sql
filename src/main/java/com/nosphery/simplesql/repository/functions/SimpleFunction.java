package com.nosphery.simplesql.repository.functions;

/**
 * @author oNospher
 **/
public abstract class SimpleFunction<T> {

    private final Class<T> clazz;

    protected SimpleFunction(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void execute() { }

    public Class<T> clazz() {
        return clazz;
    }
}
