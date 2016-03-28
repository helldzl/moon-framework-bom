package org.moonframework.security.core;

import org.moonframework.security.authentication.PermissionControl;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/31
 */
public interface AuthorizationService {

    /**
     * @param appKey appKey
     * @param userId userId
     * @return PermissionControl
     */
    PermissionControl doGetAuthorizationInfo(String appKey, Long userId);

    /**
     * @param appKey   appKey
     * @param username username
     * @return PermissionControl
     */
    PermissionControl doGetAuthorizationInfo(String appKey, String username);

}
