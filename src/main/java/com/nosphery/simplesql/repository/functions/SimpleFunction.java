package com.nosphery.simplesql.repository.functions;

import lombok.RequiredArgsConstructor;

/**
 * @author oNospher
 **/
@RequiredArgsConstructor
public abstract class SimpleFunction<T> {

    private final Class<T> clazz;

    public abstract void execute();

    public Class<T> clazz() {
        return clazz;
    }
}
