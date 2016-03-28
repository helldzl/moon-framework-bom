package org.moonframework.model.mybatis.criterion;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/18
 */
public interface Criterion {

    /**
     * @param condition condition
     */
    void toSqlString(QueryCondition condition);

}
