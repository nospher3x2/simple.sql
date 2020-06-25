package com.nosphery.simplesql.examples.client;

import com.nosphery.simplesql.database.table.enums.TableColumn;
import com.nosphery.simplesql.examples.product.Product;
import com.nosphery.simplesql.repository.annotations.Relation;
import com.nosphery.simplesql.repository.annotations.Column;
import com.nosphery.simplesql.repository.annotations.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author oNospher
 **/
@AllArgsConstructor
@Getter @Setter
@Model(name = "client")
public class Client {

    @Column(type = TableColumn.INTEGER, autoincrement = true)
    private final Integer id;

    @Column(type = TableColumn.STRING, length = 60, nullable = false)
    private String firstName, lastName;

    @Column(type = TableColumn.STRING, length = 80, unique = true)
    private String email;

    @Relation(to = Product.class)
    private List<Product> productsPurchased;

}
