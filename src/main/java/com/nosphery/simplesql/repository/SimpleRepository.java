package com.nosphery.simplesql.repository;

import com.nosphery.simplesql.repository.functions.type.CreateTableFunction;

/**
 * @author oNospher
 **/
public class SimpleRepository<T> {

    private Class<T> clazz;

    public SimpleRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public CreateTableFunction<T> createTableFunction() {
        return new CreateTableFunction<>(clazz);
    }
}
