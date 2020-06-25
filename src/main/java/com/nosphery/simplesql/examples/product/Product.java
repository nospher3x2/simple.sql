package com.nosphery.simplesql.examples.product;

import com.nosphery.simplesql.database.table.enums.TableColumn;
import com.nosphery.simplesql.examples.client.Client;
import com.nosphery.simplesql.examples.client.ClientRepository;
import com.nosphery.simplesql.repository.annotations.Column;
import com.nosphery.simplesql.repository.annotations.Model;
import com.nosphery.simplesql.repository.annotations.Relation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author oNospher
 **/
@AllArgsConstructor
@Getter @Setter
@Model
public class Product {

    @Column(type = TableColumn.INTEGER, autoincrement = true)
    private final Integer id;

    @Column(type = TableColumn.STRING, nullable = false, unique = true)
    private String name;

    @Column(type = TableColumn.INTEGER, nullable = false)
    private Integer clientId;

}
