package com.nosphery.simplesql.database.table.functions;

import com.nosphery.simplesql.database.table.Table;
import com.nosphery.simplesql.database.table.builder.QueryBuilder;
import lombok.val;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author oNospher
 **/
public class TableUpdate<U> extends QueryBuilder<U> {

    private String[] fields;
    private Object[] values;

    public TableUpdate(Connection connection, Table table, String... fields) {
        super(connection, table);
        this.fields = fields;
    }

    public U values(Object... objects) {
        if (objects.length != fields.length)
            throw new RuntimeException("Invalid size, values size need to be equal to fields size");
        this.values = objects;
        return (U) this;
    }

    public Integer execute() throws SQLException {
        QueryBuilderResult query = this.buildQuery();

        String[] fields = Arrays.stream(this.fields).map(s->"`"+s+"` = ?").toArray(String[]::new);

        String join = String.join(", ", fields);

        String sql = "UPDATE `"+table+"` SET "+join+ query.sql;

        try (val ps = connection.prepareStatement(sql)) {
            int column = 1;
            for (Object o : values) ps.setObject(column++, o);
            for (Object o : query.values()) ps.setObject(column++, o);
            return ps.executeUpdate();
        }
    }
}
