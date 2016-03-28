package org.moonframework.remote.dto;

import java.util.List;

/**
 * response
 */
public class SimpleResponseBuilder {

    private Integer code;

    private String message;

    private List<SimpleErrorResource> errors;

    public SimpleResponseBuilder() {
        super();
    }

    public SimpleResponseBuilder setCode(Integer code) {
        this.code = code;
        return this;
    }

    public SimpleResponseBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public SimpleResponseBuilder setErrors(List<SimpleErrorResource> errors) {
        this.errors = errors;
        return this;
    }

    public SimpleResponseBuilder code(CommonCode code) {
        this.code = code.getCode();
        return this;
    }

    public SimpleResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public SimpleResponseBuilder success() {
        this.code = CommonCode.SUCCESS.getCode();
        this.message = CommonCode.SUCCESS.getMessage();
        return this;
    }

    public SimpleResponseBuilder errors() {
        this.code = CommonCode.FAILURE.getCode();
        this.message = CommonCode.FAILURE.getMessage();
        return this;
    }

    public SimpleResponseBuilder errors(List<SimpleErrorResource> errors) {
        this.code = CommonCode.PARAM_INVALID.getCode();
        this.message = CommonCode.PARAM_INVALID.getMessage();
        this.errors = errors;
        return this;
    }

    public <T> SimpleResponse<T> build(T data) {
        SimpleResponse<T> simpleResponse = new SimpleResponse<>();
        simpleResponse.setCode(code);
        simpleResponse.setMessage(message);
        simpleResponse.setData(data);
        simpleResponse.setError(errors);
        return simpleResponse;
    }

    public SimpleResponse<Void> build() {
        SimpleResponse<Void> simpleResponse = new SimpleResponse<>();
        simpleResponse.setCode(code);
        simpleResponse.setMessage(message);
        simpleResponse.setError(errors);
        return simpleResponse;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<SimpleErrorResource> getErrors() {
        return errors;
    }
}
