package com.nosphery.simplesql.repository.functions.type;

import com.nosphery.simplesql.repository.annotations.SimpleModel;
import com.nosphery.simplesql.repository.functions.SimpleFunction;
import com.nosphery.simplesql.table.Table;
import com.nosphery.simplesql.table.TableRow;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * @author oNospher
 **/
public class SearchOneFunction<T> extends SimpleFunction<T> {

    private T t;

    public SearchOneFunction(Class<T> clazz) {
        super(clazz);
        try {
            this.t = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException exception) {
            exception.printStackTrace();
        }
    }

    public <K extends String, V> T execute(K key, V value) {
        Objects.requireNonNull(this.clazz());
        if (!this.clazz().isAnnotationPresent(SimpleModel.class)) return null;

        SimpleModel simpleModel = this.clazz().getDeclaredAnnotation(SimpleModel.class);
        String table_name = simpleModel.name().equals("") ? this.clazz().getName().toLowerCase() : simpleModel.name();
        Table table = new Table(table_name);

        TableRow row = table.select().where(key, value).first();
        Map<String, Object> map = row.toMap();

        Class<?> clazz = t.getClass();

        T element = t;

        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);

         //   declaredField.set(element, map.get(declaredField.getName()));

        }

        return element;
    }
}
