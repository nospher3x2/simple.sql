package com.nosphery.simplesql.table.functions;

import com.nosphery.simplesql.table.Table;
import com.nosphery.simplesql.table.builder.QueryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author oNospher
 **/
public class TableDelete<D> extends QueryBuilder<D> {

    public TableDelete(Connection connection, Table table) {
        super(connection, table);
    }

    public void execute() throws SQLException {
        QueryBuilderResult query = this.buildQuery();

        String sql = "DELETE FROM `"+table+"`"+query.sql;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int $ = 0; $ < query.values.size(); $++)
                ps.setObject($+1, query.values.get($));
            ps.executeUpdate();
        }
    }
}
