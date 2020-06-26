package com.nosphery.simplesql.examples.client;

import com.nosphery.simplesql.repository.annotations.enums.ColumnProperties;
import com.nosphery.simplesql.table.enums.TableColumn;
import com.nosphery.simplesql.examples.product.Product;
import com.nosphery.simplesql.repository.annotations.SimpleColumn;
import com.nosphery.simplesql.repository.annotations.SimpleModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author oNospher
 **/
@AllArgsConstructor
@Getter @Setter
@SimpleModel(name = "clients")
public class Client {

    @SimpleColumn(type = TableColumn.INTEGER, properties = {
            ColumnProperties.AUTO_INCREMENT,
            ColumnProperties.PRIMARY_KEY,
            ColumnProperties.REQUIRED
    })
    private final Integer id;

    @SimpleColumn(type = TableColumn.STRING, properties = {
            ColumnProperties.REQUIRED,
    }, length = 60)
    private String firstName, lastName;

    @SimpleColumn(type = TableColumn.STRING, properties = {
            ColumnProperties.REQUIRED
    }, length = 80)
    private String email;

    private List<Product> productsPurchased;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", productsPurchased=" + productsPurchased +
                '}';
    }
}
