package com.nosphery.simplesql.repository.annotations;

import com.nosphery.simplesql.database.table.enums.TableColumn;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author oNospher
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    String name() default "";

    boolean primary() default false;

    boolean unique() default false;

    boolean autoincrement() default false;

    boolean nullable() default true;

    int length() default 255;

    TableColumn type();

}
