package com.nosphery.simplesql.repository;

import com.nosphery.simplesql.repository.functions.type.CreateTableFunction;
import com.nosphery.simplesql.repository.functions.type.InsertFunction;
import com.nosphery.simplesql.repository.functions.type.FindAllFunction;
import com.nosphery.simplesql.repository.functions.type.FindOneFunction;

/**
 * @author oNospher
 **/
public class SimpleRepository<T> {

    private T t;
    private Class<T> clazz = (Class<T>) t.getClass();

    public CreateTableFunction<T> createTable() {
        return new CreateTableFunction<>(clazz);
    }

    public InsertFunction<T> insert() {
        return new InsertFunction<>(clazz);
    }

    public FindOneFunction<T> findOne() {
        return new FindOneFunction<>(clazz);
    }

    public FindAllFunction<T> findAll() {
        return new FindAllFunction<>(clazz);
    }
}
