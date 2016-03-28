package org.moonframework.validation;

import org.moonframework.core.support.Builder;
import org.moonframework.validation.domain.FieldErrorResource;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class InvalidExceptionBuilder implements Builder<InvalidException> {

    private String message;

    private List<FieldErrorResource> errors = new ArrayList<>();

    protected InvalidExceptionBuilder() {
    }

    protected InvalidExceptionBuilder(String code, String... args) {
        this.message = MessageSourceFactory.getMessage(code, args);
    }

    public InvalidExceptionBuilder addField(String field, String code, String... args) {
        FieldErrorResource fieldErrorResource = new FieldErrorResource();
        fieldErrorResource.setField(field);
        fieldErrorResource.setCode(code);
        fieldErrorResource.setMessage(MessageSourceFactory.getMessage(code, args));
        errors.add(fieldErrorResource);
        return this;
    }

    public InvalidExceptionBuilder setMessage(String code, String... args) {
        this.message = MessageSourceFactory.getMessage(code, args);
        return this;
    }

    @Override
    public InvalidException build() {
        return new InvalidException(message, errors);
    }

}

