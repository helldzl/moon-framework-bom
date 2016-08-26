package org.moonframework.core.message;

import org.moonframework.core.util.AssertUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.MessageSourceResolvable;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

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
        AssertUtils.isTrue(expression);
    }

    protected void isTrue(boolean expression, String message) {
        AssertUtils.isTrue(expression, () -> getMessage(message));
    }

    protected void isTrue(boolean expression, Supplier<String> supplier) {
        AssertUtils.isTrue(expression, supplier);
    }

    protected void isTrue(boolean expression, String message, Object[] args) {
        AssertUtils.isTrue(expression, () -> getMessage(message, args));
    }

    protected void notNull(Object object) {
        AssertUtils.notNull(object);
    }

    protected void notNull(Object object, String message) {
        AssertUtils.notNull(object, () -> getMessage(message));
    }

    protected void notNull(Object object, Supplier<String> supplier) {
        AssertUtils.notNull(object, supplier);
    }

    protected void notNull(Object object, String message, Object[] args) {
        AssertUtils.notNull(object, () -> getMessage(message, args));
    }

    protected void notEmpty(Object[] array) {
        AssertUtils.notEmpty(array);
    }

    protected void notEmpty(Object[] array, String message) {
        AssertUtils.notEmpty(array, () -> getMessage(message));
    }

    protected void notEmpty(Object[] array, String message, Object[] args) {
        AssertUtils.notEmpty(array, () -> getMessage(message, args));
    }

    protected void notEmpty(Collection<?> collection) {
        AssertUtils.notEmpty(collection);
    }

    protected void notEmpty(Collection<?> collection, String message) {
        AssertUtils.notEmpty(collection, () -> getMessage(message));
    }

    protected void notEmpty(Collection<?> collection, String message, Object[] args) {
        AssertUtils.notEmpty(collection, () -> getMessage(message, args));
    }

    protected void notEmpty(Map<?, ?> map) {
        AssertUtils.notEmpty(map);
    }

    protected void notEmpty(Map<?, ?> map, String message) {
        AssertUtils.notEmpty(map, () -> getMessage(message));
    }

    protected void notEmpty(Map<?, ?> map, String message, Object[] args) {
        AssertUtils.notEmpty(map, () -> getMessage(message, args));
    }

}
