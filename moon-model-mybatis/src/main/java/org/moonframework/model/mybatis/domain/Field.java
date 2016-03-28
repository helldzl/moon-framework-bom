package org.moonframework.model.mybatis.domain;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/11/18
 */
public interface Field {

    /**
     * field name
     *
     * @return field name
     */
    String getName();

    /**
     * alias
     *
     * @return alias
     */
    String getAlias();

    /**
     * full name
     *
     * @return full name
     */
    String getFullname();

}
