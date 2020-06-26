package com.nosphery.simplesql.repository.functions.type;

import com.nosphery.simplesql.repository.annotations.SimpleModel;
import com.nosphery.simplesql.repository.exceptions.SimpleModelException;
import com.nosphery.simplesql.repository.functions.SimpleFunction;
import com.nosphery.simplesql.table.Table;
import com.nosphery.simplesql.table.TableRow;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author oNospher
 **/
public class FindAllFunction<T> extends SimpleFunction<T> {

    public FindAllFunction(Class<T> clazz) {
        super(clazz);
    }

    public <K extends String, V> Set<T> execute(K key, V value) {
        Objects.requireNonNull(this.clazz());
        Set<T> results = new HashSet<>();

        if(!this.clazz().isAnnotationPresent(SimpleModel.class)) {
            throw new SimpleModelException("SimpleModel annotation was not found in " + this.clazz().getName() + ".class");
        }

        SimpleModel simpleModel = this.clazz().getDeclaredAnnotation(SimpleModel.class);
        String table_name = simpleModel.name().equals("") ? this.clazz().getName().toLowerCase() : simpleModel.name();
        Table table = new Table(table_name);

        Set<TableRow> rows = table.select().where(key, value).get();
        if(rows == null || rows.isEmpty()) return results;

        rows.forEach(tableRow -> {
            T element = new FindOneFunction<>(this.clazz()).execute(key, value);
            results.add(element);
        });

        return results;
    }
}
