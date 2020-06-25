package com.nosphery.simplesql.database.table;

import com.nosphery.simplesql.database.table.enums.TableColumn;
import com.nosphery.simplesql.database.table.functions.TableDelete;
import com.nosphery.simplesql.database.table.functions.TableInsert;
import com.nosphery.simplesql.database.table.functions.TableSelect;
import com.nosphery.simplesql.database.table.functions.TableUpdate;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author oNospher
 **/
@Getter
public class Table {

    @Getter @Setter
    private static Connection defaultConnection;

    @Getter
    private final String name;
    @Getter
    @Setter
    private String engine = "InnoDB";
    private Map<String, TableColumn> columns = new LinkedHashMap<>();

    public Table(String name) {
        this.name = name;
    }
    public void addColumn(String name, TableColumn type) {
        this.columns.put(name.toLowerCase(), type);
    }

    public TableSelect<?> select(Connection connection) {
        return new TableSelect<>(connection, this);
    }
    public TableSelect<?> select() {
        return this.select(defaultConnection);
    }

    public TableInsert<?> insert(String... fields) {
        return this.insert(defaultConnection, fields);
    }
    public TableInsert<?> insert(Connection connection, String... fields) {
        return new TableInsert<>(connection, this, fields);
    }

    public TableUpdate<?> update(String... fields) { return this.update(defaultConnection, fields); }

    public TableUpdate<?> update(Connection connection, String... fields) {
        return new TableUpdate<>(connection, this, fields);
    }

    public TableDelete<?> delete(Connection connection) {
        return new TableDelete<>(connection, this);
    }
    public TableDelete<?> delete() {
        return new TableDelete<>(defaultConnection, this);
    }

    public void create() {
        create(defaultConnection);
    }

    public boolean create(Connection connection) {
        try (Statement s = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `%s`(%s) Engine=%s";
            List<String> fields = new ArrayList<>();
            List<String> primary = new ArrayList<>();
            columns.forEach((name, column) -> {
                if (column.isPrimaryKey()) primary.add("`"+name+"`");
                String field = "`"+name+"` "+column.getSyntax();
                if (column.getDefaultValue() != null && !column.getDefaultValue().equalsIgnoreCase(""))
                    field+= " DEFAULT '"+column.getDefaultValue()+"'";
                fields.add(field);
            });
            if(!primary.isEmpty()) fields.add("PRIMARY KEY ("+String.join(",", primary.toArray(new String[0]))+")");
            String fields_str = String.join(",", fields.toArray(new String[0]));
            sql = String.format(sql, name, fields_str, engine);

            s.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean drop() {
        return drop(defaultConnection);
    }

    public boolean drop(Connection connection) {
        try (Statement s = connection.createStatement()) {
            s.executeUpdate("DROP TABLE `" + this.name + "`");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
