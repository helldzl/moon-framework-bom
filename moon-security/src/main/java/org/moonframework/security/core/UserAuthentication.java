package org.moonframework.security.core;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/5
 */
public enum UserAuthentication {

    /**
     * 对ROLES, PERMISSIONS的授权进行检查
     */
    DEFAULT,

    /**
     * 只检查当前用户是否登录认证, 忽略ROLES, PERMISSIONS的授权校验
     */
    AUTHENTICATION_ONLY

}
