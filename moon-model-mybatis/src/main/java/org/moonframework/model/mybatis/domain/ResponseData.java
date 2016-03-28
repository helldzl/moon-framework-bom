package org.moonframework.model.mybatis.domain;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/9
 */
public class ResponseData extends Response {

    private static final long serialVersionUID = -9009486030474122341L;
    private Object data;

    public ResponseData(String code, String message, Object data) {
        super(code, message);
        this.data = data;
    }

    @JsonView(BaseEntity.View.class)
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
