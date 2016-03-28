package org.moonframework.model.mybatis.criterion;

/**
 * <p>简单表达式</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/1/18
 */
public class SimpleExpression implements Criterion {

    private final String propertyName;
    private final Object value;
    private boolean ignoreCase;
    private final String op;

    protected SimpleExpression(String propertyName, Object value, String op) {
        this.propertyName = propertyName;
        this.value = value;
        this.op = op;
    }

    protected SimpleExpression(String propertyName, Object value, String op, boolean ignoreCase) {
        this.propertyName = propertyName;
        this.value = value;
        this.op = op;
        this.ignoreCase = ignoreCase;
    }

    @Override
    public void toSqlString(QueryCondition condition) {
        condition.stringToken(propertyName + op);
        condition.valueToken(value);
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getValue() {
        return value;
    }

    public String getOp() {
        return op;
    }

    /**
     * Make case insensitive.  No effect for non-String values
     *
     * @return {@code this}, for method chaining
     */
    public SimpleExpression ignoreCase() {
        ignoreCase = true;
        return this;
    }

    @Override
    public String toString() {
        return propertyName + getOp() + value;
    }

}
