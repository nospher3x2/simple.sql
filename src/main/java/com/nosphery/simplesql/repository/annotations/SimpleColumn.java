package com.nosphery.simplesql.repository.annotations;

import com.nosphery.simplesql.repository.annotations.enums.ColumnProperties;
import com.nosphery.simplesql.table.enums.TableColumn;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author oNospher
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleColumn {

    String name() default "";

    int length() default 255;

    ColumnProperties[] properties();

    TableColumn type();

}
