package org.moonframework.model.mybatis.criterion;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/18
 */
public class InExpression implements Criterion {

    private final String propertyName;
    private final Object[] values;

    protected InExpression(String propertyName, Object[] values) {
        this.propertyName = propertyName;
        this.values = values;
    }

    @Override
    public void toSqlString(QueryCondition condition) {
        condition.stringToken(propertyName + " IN (");
        int len = values.length;
        if (len > 0) {
            for (int i = 0; i < len - 1; i++) {
                condition.valueToken(values[i]);
                condition.stringToken(", ");
            }
            condition.valueToken(values[len - 1]);
        }
        condition.stringToken(")");
    }

    @Override
    public String toString() {
        return propertyName + " IN (" + StringHelper.toString(values) + ')';
    }

}
