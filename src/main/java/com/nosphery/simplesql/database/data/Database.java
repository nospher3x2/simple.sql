package com.nosphery.simplesql.database.data;

import com.nosphery.simplesql.database.enums.DatabaseType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author oNospher
 **/

@Getter
public class Database {

    protected String host, username, password, database, databaseSrc;

    @Setter
    private Boolean cachePrepStmts;
    @Setter
    private Integer prepStmtCacheSize, prepStmtCacheSqlLimit;

    private DatabaseType databaseType;
    private HikariDataSource hikariDataSource;

    public Database(String host, String username, String password, String database) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public Database(String database, String databaseSrc) {
        this.database = database;
        this.databaseSrc = databaseSrc;
    }

    public void start(DatabaseType databaseType) {
        HikariConfig hikariConfig = new HikariConfig();
        this.databaseType = databaseType;

        String jdbcUrl = databaseType.equals(DatabaseType.SQLITE) ? String.format(
                "sqlite:%s",
                new File(this.databaseSrc, database + ".db")
        ) : String.format(
                "mysql://%s/%s",
                this.host,
                this.database
        );

        hikariConfig.setJdbcUrl(jdbcUrl);

        if(this.databaseType == DatabaseType.MYSQL) {
            hikariConfig.setUsername(this.username);
            hikariConfig.setPassword(this.password);
        }

        hikariConfig.addDataSourceProperty(
                "cachePrepStmts",
                this.cachePrepStmts == null ? "true" : String.valueOf(this.cachePrepStmts)
        );

        hikariConfig.addDataSourceProperty(
                "prepStmtCacheSize",
                this.prepStmtCacheSize == null ? "250" : String.valueOf(this.prepStmtCacheSize)
        );

        hikariConfig.addDataSourceProperty(
                "prepStmtCacheSqlLimit",
                this.prepStmtCacheSqlLimit == null ? "2048" : String.valueOf(this.prepStmtCacheSqlLimit)
        );

        this.hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public void refresh() {
        if (this.isClosed()) this.start(this.databaseType);
    }

    public Connection getConnection() throws SQLException {
        return this.hikariDataSource.getConnection();
    }

    private boolean isClosed() {
        return this.hikariDataSource == null || this.hikariDataSource.isClosed();
    }
}
