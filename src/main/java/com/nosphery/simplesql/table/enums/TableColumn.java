package com.nosphery.simplesql.table.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author oNospher
 **/
@AllArgsConstructor
@Getter
public enum TableColumn {

    STRING("VARCHAR(%d)", "", false),
    BYTE("TINYINT", "", false),
    BOOLEAN("BOOLEAN", "", false),
    SHORT("MEDIUMINT", "", false),
    INTEGER("INT", "", false),
    DOUBLE("DOUBLE", "", false),
    LONG("BIGINT", "", false),
    TEXT("TEXT", "", false);

    private String syntax;
    private String defaultValue;
    private boolean primaryKey;

    public TableColumn primaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public TableColumn length(Integer length) {
        if(this == TableColumn.STRING) this.syntax += String.format(this.getSyntax(), length);
        return this;
    }

    public TableColumn autoIncrement(boolean value) {
        if(!value) return this;

        TableColumn[] alloweds = new TableColumn[]{
                TableColumn.INTEGER,
                TableColumn.LONG,
                TableColumn.LONG
        };

        if(Arrays.asList(alloweds).contains(this) ) this.syntax += " AUTO_INCREMENT";
        return this;
    }

    public TableColumn unique(Boolean value) {
        if(value) this.syntax += " UNIQUE";
        return this;
    }

    public TableColumn nullable(Boolean value) {
        if(!value) this.syntax += " NOT NULL";
        return this;
    }
}
