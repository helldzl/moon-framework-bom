package org.moonframework.core.message;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/14
 */
public class MessageSourceAdapter implements MessageSourceAware {

    protected MessageSource messages;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = messageSource;
    }

    protected String getMessage(MessageSourceResolvable resolvable, Locale locale) {
        return messages.getMessage(resolvable, locale);
    }

    protected String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messages.getMessage(code, args, defaultMessage, locale);
    }

    protected String getMessage(String code, Object[] args, Locale locale) {
        return messages.getMessage(code, args, locale);
    }

    protected String getMessage(String code, Object[] args) {
        return messages.getMessage(code, args, null);
    }

    protected String getMessage(String code) {
        return messages.getMessage(code, null, null);
    }

    // Assert method

    protected void isTrue(boolean expression) {
        Assert.isTrue(expression);
    }

    protected void isTrue(boolean expression, String message) {
        Assert.isTrue(expression, getMessage(message));
    }

    protected void isTrue(boolean expression, String message, Object[] args) {
        Assert.isTrue(expression, getMessage(message, args));
    }

    protected void notNull(Object object) {
        Assert.notNull(object);
    }

    protected void notNull(Object object, String message) {
        Assert.notNull(object, getMessage(message));
    }

    protected void notNull(Object object, String message, Object[] args) {
        Assert.notNull(object, getMessage(message, args));
    }

    protected void notEmpty(Object[] array) {
        Assert.notEmpty(array);
    }

    protected void notEmpty(Object[] array, String message) {
        Assert.notEmpty(array, getMessage(message));
    }

    protected void notEmpty(Object[] array, String message, Object[] args) {
        Assert.notEmpty(array, getMessage(message, args));
    }

    protected void notEmpty(Collection<?> collection) {
        Assert.notEmpty(collection);
    }

    protected void notEmpty(Collection<?> collection, String message) {
        Assert.notEmpty(collection, getMessage(message));
    }

    protected void notEmpty(Collection<?> collection, String message, Object[] args) {
        Assert.notEmpty(collection, getMessage(message, args));
    }

    protected void notEmpty(Map<?, ?> map) {
        Assert.notEmpty(map);
    }

    protected void notEmpty(Map<?, ?> map, String message) {
        Assert.notEmpty(map, getMessage(message));
    }

    protected void notEmpty(Map<?, ?> map, String message, Object[] args) {
        Assert.notEmpty(map, getMessage(message, args));
    }

}
