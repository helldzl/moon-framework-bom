package org.moonframework.model.mybatis.criterion;

/**
 * <p>范围表达式</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/1/18
 */
public class BetweenExpression implements Criterion {

    private final String propertyName;
    private final Object lo;
    private final Object hi;

    protected BetweenExpression(String propertyName, Object lo, Object hi) {
        this.propertyName = propertyName;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public void toSqlString(QueryCondition condition) {
        condition.stringToken(propertyName + " BETWEEN ");
        condition.valueToken(lo);
        condition.stringToken(" AND ");
        condition.valueToken(hi);
    }

    @Override
    public String toString() {
        return propertyName + " BETWEEN " + lo + " AND " + hi;
    }

}
