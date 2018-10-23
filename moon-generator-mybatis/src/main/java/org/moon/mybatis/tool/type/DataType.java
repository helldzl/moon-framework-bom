package org.moon.mybatis.tool.type;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/8/17
 */
public enum DataType {

    TINYINT(Category.NUMERIC, "tinyint", "Byte", "java.lang.Byte", false, "byte"),

    SMALLINT(Category.NUMERIC, "smallint", "Short", "java.lang.Short", false, "short"),

    MEDIUMINT(Category.NUMERIC, "mediumint", "Integer", "java.lang.Integer", false, "int"),

    INT(Category.NUMERIC, "int", "Integer", "java.lang.Integer", false, "int"),

    BIGINT(Category.NUMERIC, "bigint", "Long", "java.lang.Long", false, "long"),

    FLOAT(Category.NUMERIC, "float", "Double", "java.lang.Double", false, "double"),

    DOUBLE(Category.NUMERIC, "double", "Double", "java.lang.Double", false, "double"),

    DECIMAL(Category.NUMERIC, "decimal", "BigDecimal", "java.math.BigDecimal", true, "bigdecimal"),

    DATE(Category.DATETIME, "date", "Date", "java.util.Date", true, "date"),

    TIME(Category.DATETIME, "time", "Date", "java.util.Date", true, "date"),

    DATETIME(Category.DATETIME, "datetime", "Date", "java.util.Date", true, "date"),

    TIMESTAMP(Category.DATETIME, "timestamp", "Date", "java.util.Date", true, "date"),

    YEAR(Category.DATETIME, "year", "Integer", "java.lang.Integer", false, "int"),

    CHAR(Category.STRING, "char", "String", "java.lang.String", false, "string"),

    VARCHAR(Category.STRING, "varchar", "String", "java.lang.String", false, "string"),

    NVARCHAR(Category.STRING, "nvarchar", "String", "java.lang.String", false, "string"),

    TINYTEXT(Category.STRING, "tinytext", "String", "java.lang.String", false, "string"),

    TEXT(Category.STRING, "text", "String", "java.lang.String", false, "string"),

    MEDIUMTEXT(Category.STRING, "mediumtext", "String", "java.lang.String", false, "string"),

    LONGTEXT(Category.STRING, "longtext", "String", "java.lang.String", false, "string"),

    ENUM(Category.STRING, "enum", "String", "java.lang.String", false, "string");

    private final Category category;
    private final String name;              // 数据库
    private final String javaType;          // java类型
    private final String qualifiedName;     // 全限定名称
    private final boolean imported;         // 是否import
    private final String alias;             // 别名

    private static Map<String, DataType> map = new HashMap<String, DataType>();

    static {
        for (DataType o : DataType.values())
            map.put(o.getName(), o);
    }

    public static DataType from(String name) {
        return map.get(name);
    }

    DataType(Category category, String name, String javaType, String qualifiedName, boolean imported, String alias) {
        this.category = category;
        this.name = name;
        this.javaType = javaType;
        this.qualifiedName = qualifiedName;
        this.imported = imported;
        this.alias = alias;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public boolean isImported() {
        return imported;
    }

    public String getAlias() {
        return alias;
    }

    public enum Category {
        NUMERIC,

        DATETIME,

        STRING,

        GEO,
    }

}
