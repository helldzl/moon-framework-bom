package org.moonframework.model.mybatis.criterion;

/**
 * <p>正则表达式</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/1/18
 */
public class LikeExpression implements Criterion {

    private final String propertyName;
    private final Object value;
    private final Character escapeChar;
    private final boolean ignoreCase;

    protected LikeExpression(String propertyName, Object value, Character escapeChar, boolean ignoreCase) {
        this.propertyName = propertyName;
        this.value = value;
        this.escapeChar = escapeChar;
        this.ignoreCase = ignoreCase;
    }

    protected LikeExpression(String propertyName, String value) {
        this(propertyName, value, null, false);
    }

    protected LikeExpression(String propertyName, String value, MatchMode matchMode) {
        this(propertyName, matchMode.toMatchString(value));
    }

    protected LikeExpression(
            String propertyName,
            String value,
            MatchMode matchMode,
            Character escapeChar,
            boolean ignoreCase) {
        this(propertyName, matchMode.toMatchString(value), escapeChar, ignoreCase);
    }

    @Override
    public void toSqlString(QueryCondition condition) {
        condition.stringToken(propertyName + " LIKE ");
        condition.valueToken(value);
    }
}
