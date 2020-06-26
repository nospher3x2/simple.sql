package com.nosphery.simplesql.repository.functions.type;

import com.nosphery.simplesql.repository.annotations.SimpleColumn;
import com.nosphery.simplesql.repository.annotations.SimpleModel;
import com.nosphery.simplesql.repository.exceptions.SimpleModelException;
import com.nosphery.simplesql.repository.functions.SimpleFunction;
import com.nosphery.simplesql.table.Table;
import com.nosphery.simplesql.table.TableRow;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author oNospher
 **/
public class FindOneFunction<T> extends SimpleFunction<T> {

    private T t;

    public FindOneFunction(Class<T> clazz) {
        super(clazz);
        try {
            this.t = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException exception) {
            exception.printStackTrace();
        }
    }

    public <K extends String, V> T execute(K key, V value) {
        Objects.requireNonNull(this.clazz());
        if(!this.clazz().isAnnotationPresent(SimpleModel.class)) {
            throw new SimpleModelException("SimpleModel annotation was not found in " + this.clazz().getName() + ".class");
        }

        SimpleModel simpleModel = this.clazz().getDeclaredAnnotation(SimpleModel.class);
        String table_name = simpleModel.name().equals("") ? this.clazz().getName().toLowerCase() : simpleModel.name();
        Table table = new Table(table_name);

        TableRow row = table.select().where(key, value).first();
        if(row == null) return null;

        Class<?> clazz = t.getClass();
        T element = null;
        try {
            element = (T) clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        if(element == null) return null;

        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);

            if (declaredField.isAnnotationPresent(SimpleColumn.class)) {
                SimpleColumn simpleColumn = declaredField.getDeclaredAnnotation(SimpleColumn.class);
                String name = simpleColumn.name().equals("") ? declaredField.getName().toLowerCase() : simpleColumn.name();

                try {
                    declaredField.set(element, row.get(name));
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                }
            }
        }

        return element;
    }
}
