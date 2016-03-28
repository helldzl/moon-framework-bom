package org.moonframework.model.mybatis.criterion;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/19
 */
public class StringToken implements QueryToken {

    private String value;

    public StringToken(String value) {
        this.value = value;
    }

    @Override
    public boolean isParam() {
        return false;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
