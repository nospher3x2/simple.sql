package com.nosphery.simplesql.repository.functions.type;

import com.nosphery.simplesql.repository.exceptions.SimpleModelException;
import com.nosphery.simplesql.table.Table;
import com.nosphery.simplesql.repository.annotations.SimpleColumn;
import com.nosphery.simplesql.repository.annotations.SimpleModel;
import com.nosphery.simplesql.repository.functions.SimpleFunction;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author oNospher
 **/
public class CreateTableFunction<T> extends SimpleFunction<T> {

    public CreateTableFunction(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public void execute() {
        Objects.requireNonNull(this.clazz());
        if(!this.clazz().isAnnotationPresent(SimpleModel.class)) {
            throw new SimpleModelException("SimpleModel annotation was not found in " + this.clazz().getName() + ".class");
        }
        SimpleModel simpleModel = this.clazz().getDeclaredAnnotation(SimpleModel.class);
        String table_name = simpleModel.name().equals("") ? this.clazz().getSimpleName().toLowerCase() : simpleModel.name();
        Table table = new Table(table_name);

        for (Field declaredField : this.clazz().getDeclaredFields()) {
            declaredField.setAccessible(true);

            if (declaredField.isAnnotationPresent(SimpleColumn.class)) {
                SimpleColumn simpleColumn = declaredField.getDeclaredAnnotation(SimpleColumn.class);
                simpleColumn.type().length(simpleColumn.length());
                simpleColumn.type().injectProperties(simpleColumn.properties());

                String name = simpleColumn.name().equals("") ? declaredField.getName().toLowerCase() : simpleColumn.name();

                table.addColumn(name, simpleColumn.type());
            }
        }

        table.create();
    }
}
