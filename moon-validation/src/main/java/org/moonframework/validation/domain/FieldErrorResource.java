package org.moonframework.validation.domain;

import java.io.Serializable;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/16
 */
public class FieldErrorResource implements Serializable {

    private static final long serialVersionUID = 7477369704279077421L;
    private String resource;
    private String field;
    private String code;
    private String message;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

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
