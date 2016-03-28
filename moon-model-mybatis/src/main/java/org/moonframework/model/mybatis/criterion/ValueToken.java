package org.moonframework.model.mybatis.criterion;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/19
 */
public class ValueToken implements QueryToken {

    private Object value;

    public ValueToken(Object value) {
        this.value = value;
    }

    @Override
    public boolean isParam() {
        return true;
    }

    @Override
    public Object getValue() {
        return value;
    }

}
