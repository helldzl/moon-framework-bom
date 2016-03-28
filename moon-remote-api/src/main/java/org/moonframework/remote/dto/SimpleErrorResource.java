package org.moonframework.remote.dto;

import java.io.Serializable;

/**
 * 接口返回对象
 * Created by lcj on 2015/9/7.
 */
public class SimpleErrorResource implements Serializable{

    private static final long serialVersionUID = 7477369704279077421L;

    /**
     * 错误字段
     * 对应接口参数
     */
    private String field;

    /**
     * 错误编码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
