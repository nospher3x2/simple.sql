package com.nosphery.simplesql.table.enums;

import com.nosphery.simplesql.repository.annotations.enums.ColumnProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

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

    public void injectProperties(ColumnProperties... properties) {
        List<ColumnProperties> propertiesList = Arrays.asList(properties);
        if(propertiesList.contains(ColumnProperties.AUTO_INCREMENT)) {
            TableColumn[] alloweds = new TableColumn[]{
                    TableColumn.INTEGER,
                    TableColumn.LONG,
                    TableColumn.LONG
            };

            if(Arrays.asList(alloweds).contains(this)) this.syntax += "AUTO_INCREMENT ";
        }

        if(propertiesList.contains(ColumnProperties.PRIMARY_KEY)) this.primaryKey = true;
        if(propertiesList.contains(ColumnProperties.UNIQUE)) this.syntax += " UNIQUE";
        if(propertiesList.contains(ColumnProperties.REQUIRED)) this.syntax += " NOT NULL";
    }

    public void length(Integer length) {
        if(!this.equals(TableColumn.STRING)) return;
        this.syntax += String.format(this.getSyntax(), length);
    }
}
