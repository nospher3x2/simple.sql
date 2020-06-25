package com.nosphery.simplesql.table.builder;

import com.nosphery.simplesql.table.Table;
import com.nosphery.simplesql.table.TableWhere;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author oNospher
 **/
public abstract class QueryBuilder<T> {

    protected Connection connection;
    protected String table;

    private List<List<TableWhere>> groups = new ArrayList<>();

    public QueryBuilder(Connection connection, Table table) {
        this.connection = connection;
        this.table = table.getName();
    }

    private List<TableWhere> last() {
        if (groups.size() == 0) groups.add(new ArrayList<>());
        return groups.get(groups.size()-1);
    }

    public T where(String key, Object value) {
        last().add(new TableWhere(key, value));
        return (T) this;
    }

    public T where(String key, String operator, Object value) {
        last().add(new TableWhere(key, operator, value));
        return (T) this;
    }

    public T whereIn(String key, Object[] values) {
        last().add(new TableWhere(key, values, true));
        return (T) this;
    }

    public T whereNotIn(String key, Object[] values) {
        last().add(new TableWhere(key, values, false));
        return (T) this;
    }

    public T or() {
        if (last().isEmpty()) return (T) this;
        groups.add(new ArrayList<>());
        return (T) this;
    }

    public QueryBuilderResult buildQuery() {
        List<Object> values = new ArrayList<>();
        String sql = "";
        if (groups.size() > 0) {
            sql = " WHERE ";
            String[] groups = this.groups.stream().map((list) -> {
                String[] parameters = list.stream().map((tableWhere) -> {
                    Collections.addAll(values, tableWhere.getValues());
                    return tableWhere.toSQL();
                }).toArray(String[]::new);
                return "("+String.join(" AND ", parameters)+")";
            }).toArray(String[]::new);
            sql+= " "+String.join(" OR ", groups);
        }
        return new QueryBuilderResult(sql, values);
    }

    @AllArgsConstructor
    public static class QueryBuilderResult {

        public String sql;
        public List<Object> values;

        public Object[] values() {
            return values.toArray();
        }
    }
}
