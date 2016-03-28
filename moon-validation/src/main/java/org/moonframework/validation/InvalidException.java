package org.moonframework.validation;

import org.moonframework.validation.domain.FieldErrorResource;

import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/16
 */
public class InvalidException extends RuntimeException {

    private static final long serialVersionUID = -7511910916604039434L;
    private List<FieldErrorResource> errors;

    public InvalidException() {
    }

    public static InvalidExceptionBuilder builder() {
        return new InvalidExceptionBuilder();
    }

    public static InvalidExceptionBuilder builder(String code, String ...args) {
        return new InvalidExceptionBuilder(code,args);
    }

    public InvalidException(String message, List<FieldErrorResource> errors) {
        super(message);
        this.errors = errors;
    }

    public List<FieldErrorResource> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldErrorResource> errors) {
        this.errors = errors;
    }
}
