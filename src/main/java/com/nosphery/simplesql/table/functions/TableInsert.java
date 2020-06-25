package com.nosphery.simplesql.table.functions;

import com.nosphery.simplesql.table.Table;
import com.nosphery.simplesql.table.builder.QueryBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author oNospher
 **/
public class TableInsert<I> extends QueryBuilder<I> {

    private final String[] fields;

    public TableInsert(Connection connection, Table table, String... fields) {
        super(connection, table);
        this.fields = fields;
    }

    public Integer one(Object... values) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(this.sql(), Statement.RETURN_GENERATED_KEYS)) {
            for (int x = 0; x < values.length; x++) {
                ps.setObject(x+1, values[x]);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else return 0;
            }
        }
    }

    public int[] many(List<Object[]> list) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(this.sql(), Statement.RETURN_GENERATED_KEYS)) {
            for (Object[] arr : list) {
                arr = Stream.of(arr).map(o->o instanceof UUID ? o.toString(): o).toArray();
                for (int x = 0; x < arr.length; x++) {
                    ps.setObject(x+1, arr[x]);
                }
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    while (rs.next()) ids.add(rs.getInt(1));
                }
            }
        }
        return ids.stream().mapToInt(i->i).toArray();
    }

    private String sql() {
        String columns = String.join(",", Stream.of(this.fields).map(s->'`'+s+'`').toArray(String[]::new));
        String marks = String.join(",", Stream.of(this.fields).map(s->"?").toArray(String[]::new));
        return "INSERT INTO `"+table+"` ("+columns+") VALUES ("+marks+")";
    }
}
