package com.nosphery.simplesql.repository.functions.type;

import com.nosphery.simplesql.repository.annotations.SimpleColumn;
import com.nosphery.simplesql.repository.annotations.SimpleModel;
import com.nosphery.simplesql.repository.exceptions.SimpleModelException;
import com.nosphery.simplesql.repository.functions.SimpleFunction;

import java.lang.reflect.Field;

/**
 * @author oNospher
 **/
public class InsertFunction<T> extends SimpleFunction<T> {

    public InsertFunction(Class<T> clazz) {
        super(clazz);
    }

    public T execute(T element) {
        if(!this.clazz().isAnnotationPresent(SimpleModel.class)) {
            throw new SimpleModelException("SimpleModel annotation was not found in " + this.clazz().getName() + ".class");
        }

        Field[] preField = this.clazz().getDeclaredFields();

        for (Field field : preField) {
            if(!field.isAnnotationPresent(SimpleColumn.class)) continue;

        }

        return element;
    }
}
