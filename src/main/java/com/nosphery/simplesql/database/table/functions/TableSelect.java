package com.nosphery.simplesql.database.table.functions;

import com.nosphery.simplesql.database.table.Table;
import com.nosphery.simplesql.database.table.TableRow;
import com.nosphery.simplesql.database.table.builder.QueryBuilder;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author oNospher
 **/
public class TableSelect<S> extends QueryBuilder<S> {

    private String fields = "*";
    private Integer limit;

    public TableSelect(Connection connection, Table table) {
        super(connection, table);
    }

    public S limit(int len) {
        limit = len;
        return (S) this;
    }

    public S columns(String... fields) {
        this.fields = String.join(",", Arrays.stream(fields).map(s->"`"+s+"`").toArray(String[]::new));
        return (S) this;
    }

    @SneakyThrows
    public Number sum(String field) {
        this.fields = "SUM(`"+field+"`) as total";
        ResultSet rs = this.execute();
        Number value = rs.first() ? (Number) rs.getObject("total") : 0;
        this.close(rs);
        return value;
    }

    @SneakyThrows
    public long count() {
        this.fields = "COUNT(*) as total";
        ResultSet rs = this.execute();
        long value = rs.first() ? rs.getLong("total") : 0;
        this.close(rs);
        return value;
    }

    @SneakyThrows
    public Set<TableRow> get() {
        ResultSet rs = this.execute();

        Set<TableRow> rows = new HashSet<>();
        while (rs.next()) {
            rows.add(new TableRow(rs));
        }
        this.close(rs);
        return rows;
    }

    @SneakyThrows
    public TableRow first() {
        ResultSet rs = this.execute();
        TableRow row = (rs.first() ? new TableRow(rs) : null);
        this.close(rs);
        return row;
    }

    private void close(ResultSet rs) {
        try (Statement statement = rs.getStatement()) {
            statement.closeOnCompletion();
            rs.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private ResultSet execute() throws Exception {
        QueryBuilderResult query = this.buildQuery();

        String sql = "SELECT "+fields+" FROM `"+table+"`"+query.sql;
        if (limit != null) sql+=" LIMIT "+limit;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int x = 0; x < query.values.size(); x++)
                ps.setObject(x + 1, query.values.get(x));

            return ps.executeQuery();
        }
    }
}
