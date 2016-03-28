package org.moonframework.security.core.annotation;

import java.lang.annotation.*;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/23
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface WebSecured {

    /**
     * Returns the list of security configuration attributes (e.g. ROLE_USER, ROLE_ADMIN).
     *
     * @return String[] The secure method attributes
     */
    String[] value() default "";

}
