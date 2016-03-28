package org.moonframework.core.factory;

import java.util.Date;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/20
 */
public class DateFactory {

    private static final ThreadLocal<Date> CURRENT_TIME = new ThreadLocal<Date>() {
        @Override
        protected Date initialValue() {
            return new Date();
        }
    };

    /**
     * <p>Returns the current thread's unique DATE, assigning it if necessary</p>
     *
     * @return date time
     */
    public static Date current() {
        return CURRENT_TIME.get();
    }

}
