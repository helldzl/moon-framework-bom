package org.moonframework.model.mybatis.criterion;

/**
 * <p>逻辑表达式</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/1/19
 */
public class LogicalExpression implements Criterion {

    private final Criterion lhs;
    private final Criterion rhs;
    private final String op;

    public LogicalExpression(Criterion lhs, Criterion rhs, String op) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    public String getOp() {
        return op;
    }

    @Override
    public void toSqlString(QueryCondition condition) {
        condition.stringToken("(");
        lhs.toSqlString(condition);
        condition.stringToken(" " + op + " ");
        rhs.toSqlString(condition);
        condition.stringToken(")");
    }

    @Override
    public String toString() {
        return lhs.toString() + ' ' + getOp() + ' ' + rhs.toString();
    }

}
