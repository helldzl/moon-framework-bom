package org.moon.mybatis.tool.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moon.mybatis.tool.type.DataType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/8/17
 */
public class Table {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private String tableName;
    private List<Column> columns;
    private Set<DataType> set = new HashSet<>();

    /**
     * @param name
     * @return
     */
    public static String propertyName(String name) {
        return getName(name, false);
    }

    /**
     * @param name
     * @return
     */
    public static String className(String name) {
        return getName(name, true);
    }

    /**
     * @param name
     * @param convert 首字母是否大写
     * @return
     */
    public static String getName(String name, boolean convert) {
        String n = name.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (char c : n.toCharArray()) {
            if ('_' == c) {
                convert = true;
            } else {
                sb.append(convert ? Character.toUpperCase(c) : c);
                convert = false;
            }
        }
        return sb.toString();
    }

    //

    public Table(String tableName) {
        this(tableName, new ArrayList<>());
    }

    public Table(String tableName, List<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    //

    public Set<DataType> getImports() {
        return set;
    }

    public Set<DataType> setImports(List<Column> columns) {
        for (Column column : columns) {
            DataType dataType = column.getDataTypeEnum();
            if (dataType == null) {
                logger.error("No Map, Data Type : " + column.getDataType());
                continue;
            }
            if (dataType.isImported() && !set.contains(dataType))
                set.add(dataType);
        }
        return set;
    }

    public boolean add(Column column) {
        return columns.add(column);
    }

    public String getClassName() {
        return className(tableName);
    }

    public String getRefClassName() {
        return propertyName(tableName);
    }

    // get and set

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
