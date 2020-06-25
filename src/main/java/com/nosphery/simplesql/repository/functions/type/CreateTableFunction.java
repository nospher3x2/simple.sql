package com.nosphery.simplesql.repository.functions.type;

import com.nosphery.simplesql.table.Table;
import com.nosphery.simplesql.repository.annotations.Column;
import com.nosphery.simplesql.repository.annotations.Model;
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
        if (!this.clazz().isAnnotationPresent(Model.class)) return;

        Model model = this.clazz().getDeclaredAnnotation(Model.class);
        String table_name = model.name().equals("") ? this.clazz().getName().toLowerCase() : model.name();
        Table table = new Table(table_name);

        for (Field declaredField : this.clazz().getDeclaredFields()) {
            declaredField.setAccessible(true);

            if (declaredField.isAnnotationPresent(Column.class)) {
                Column column = declaredField.getDeclaredAnnotation(Column.class);
                column.type().primaryKey(column.primary())
                        .autoIncrement(column.autoincrement())
                        .unique(column.unique())
                        .nullable(column.nullable())
                        .length(column.length());

                table.addColumn(column.name(), column.type());
            }
        }

        table.create();
    }
}
