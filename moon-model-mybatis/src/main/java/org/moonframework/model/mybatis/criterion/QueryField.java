package org.moonframework.model.mybatis.criterion;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/19
 */
public class QueryField {

    private String table;
    private String fieldname;

    public QueryField(String fieldname) {
        int i = fieldname.indexOf('.');
        if (i > 0) {
            this.table = fieldname.substring(0, i);
            this.fieldname = fieldname.substring(i + 1);
        } else {
            this.fieldname = fieldname;
        }
    }

    public QueryField(String table, String fieldname) {
        this.table = table;
        this.fieldname = fieldname;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (table != null && !table.isEmpty())
            builder.append(table).append('.');
        if ("*".equals(fieldname))
            builder.append('*');
        else
            builder.append(fieldname);
        return builder.toString();
    }
}
