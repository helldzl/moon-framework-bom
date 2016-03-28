package org.moonframework.validation;

import org.springframework.validation.Errors;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/16
 */
public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = -1502862400875449734L;
    private Errors errors;

    public InvalidRequestException(Errors errors) {
        this.errors = errors;
    }

    public InvalidRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
