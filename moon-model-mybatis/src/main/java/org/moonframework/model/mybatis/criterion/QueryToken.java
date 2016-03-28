package org.moonframework.model.mybatis.criterion;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/19
 */
public interface QueryToken {

    boolean isParam();

    Object getValue();

}
