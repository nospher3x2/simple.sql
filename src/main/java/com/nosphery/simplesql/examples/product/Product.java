package com.nosphery.simplesql.examples.product;

import com.nosphery.simplesql.repository.annotations.enums.ColumnProperties;
import com.nosphery.simplesql.table.enums.TableColumn;
import com.nosphery.simplesql.repository.annotations.SimpleColumn;
import com.nosphery.simplesql.repository.annotations.SimpleModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author oNospher
 **/
@AllArgsConstructor
@Getter @Setter
@SimpleModel
public class Product {

    @SimpleColumn(type = TableColumn.INTEGER, properties = {
            ColumnProperties.AUTO_INCREMENT,
            ColumnProperties.PRIMARY_KEY,
            ColumnProperties.REQUIRED
    })
    private final Integer id;

    @SimpleColumn(type = TableColumn.STRING, properties = {
            ColumnProperties.REQUIRED,
            ColumnProperties.UNIQUE
    })
    private String name;

    @SimpleColumn(type = TableColumn.INTEGER, properties = {
            ColumnProperties.REQUIRED
    })
    private Integer clientId;

}
