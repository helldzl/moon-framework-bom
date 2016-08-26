package org.moonframework.remote.dto;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/6
 */
public class InvalidResponseException extends RuntimeException {

    private SimpleResponse<?> response;

    public InvalidResponseException(SimpleResponse<?> response) {
        this.response = response;
    }

    public InvalidResponseException(String message, SimpleResponse<?> response) {
        super(message);
        this.response = response;
    }

    public InvalidResponseException(String message, Throwable cause, SimpleResponse<?> response) {
        super(message, cause);
        this.response = response;
    }

    public InvalidResponseException(Throwable cause, SimpleResponse<?> response) {
        super(cause);
        this.response = response;
    }

    public InvalidResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, SimpleResponse<?> response) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.response = response;
    }

    public SimpleResponse<?> getResponse() {
        return response;
    }

    public void setResponse(SimpleResponse<?> response) {
        this.response = response;
    }

}
