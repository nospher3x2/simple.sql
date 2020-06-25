package com.nosphery.simplesql.repository.functions.type;

import com.nosphery.simplesql.repository.annotations.enums.ColumnProperties;
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
        if (!this.clazz().isAnnotationPresent(SimpleModel.class)) return;

        SimpleModel simpleModel = this.clazz().getDeclaredAnnotation(SimpleModel.class);
        String table_name = simpleModel.name().equals("") ? this.clazz().getName().toLowerCase() : simpleModel.name();
        Table table = new Table(table_name);

        for (Field declaredField : this.clazz().getDeclaredFields()) {
            declaredField.setAccessible(true);

            if (declaredField.isAnnotationPresent(SimpleColumn.class)) {
                SimpleColumn simpleColumn = declaredField.getDeclaredAnnotation(SimpleColumn.class);
                simpleColumn.type().length(simpleColumn.length());
                simpleColumn.type().injectProperties(simpleColumn.properties());

                table.addColumn(simpleColumn.name(), simpleColumn.type());
            }
        }

        table.create();
    }
}
