package org.moonframework.security.core.annotation;

import org.moonframework.security.core.UserAuthentication;

import java.lang.annotation.*;

/**
 * <p>https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.6.1</p>
 *
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

    /**
     * <p>User Roles</p>
     * <p>Returns the list of security configuration attributes</p>
     *
     * @return String[] The secure method attributes
     */
    String[] roles() default "";

    /**
     * <p>User Permissions</p>
     * <p>Returns the list of security configuration attributes</p>
     *
     * @return String[] The secure method attributes
     */
    String[] permissions() default "";

    /**
     * <p>authentication check enum</p>
     *
     * @return UserAuthentication
     */
    UserAuthentication authentication() default UserAuthentication.DEFAULT;

    /**
     * <p>is throw a exception on not authentication</p>
     * <p>used with exception()</p>
     *
     * @return true if throw
     * @see #exception()
     */
    boolean throwOnUnauthenticated() default false;

    /**
     * <p>Custom Exception</p>
     * <p>used with throwOnUnauthentication()</p>
     *
     * @return Class type of Exception
     * @see #throwOnUnauthenticated()
     */
    Class<? extends RuntimeException> exception() default RuntimeException.class;

}
