package com.nosphery.simplesql;

import com.nosphery.simplesql.database.data.Database;
import com.nosphery.simplesql.database.enums.DatabaseType;
import com.nosphery.simplesql.table.Table;
import lombok.Getter;

import java.sql.SQLException;

/**
 * @author oNospher
 **/
public class SimpleSQL {

    @Getter
    private static SimpleSQL instance;

    public SimpleSQL() throws SQLException {
        instance = this;

        Database database = new Database(
                "localhost",
                "root",
                "root",
                "test"
        );

        database.start(DatabaseType.MYSQL);
        Table.setDefaultConnection(database.getConnection());
    }

    public static void main(String[] args) throws SQLException {
        new SimpleSQL();
    }
}
