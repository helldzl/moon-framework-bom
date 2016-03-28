package org.moonframework.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/14
 */
@Component
public class MessageSourceFactory implements MessageSourceAware {

    private static MessageSource messages;

    public static String getMessage(String code, String... args) {
        return messages.getMessage(code, args, null);
    }

    public static String getMessage(String code) {
        return messages.getMessage(code, null, null);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = messageSource;
    }

}
