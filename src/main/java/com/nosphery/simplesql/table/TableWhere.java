package com.nosphery.simplesql.table;

import java.util.Arrays;

/**
 * @author oNospher
 **/
public class TableWhere {

    private String name, operator;
    private Object value;
    private Boolean in;

    public TableWhere(String name, Object value) {
        this(name, "=", value);
    }

    public TableWhere(String name, Object values, boolean in) {
        this.name = name.toLowerCase();
        this.value = values;
        this.in = in;
    }

    public TableWhere(String key, String operator, Object value) {
        this.name = key;
        this.operator = operator;
        this.value = value;
    }

    public Object[] getValues() {
        if (value.getClass().isArray()) return (Object[])value;
        return new Object[]{ value };
    }

    public String toSQL() {
        if (operator ==  null) {
            String[] questionMarks = Arrays.stream(this.getValues()).map(o -> "?").toArray(String[]::new);
            return "`" + name + "` " + (in ? "" : "NOT ") + "IN(" + String.join(",", questionMarks) + ")";
        }
        return "`" + name + "` "+operator+" ?";
    }
}
