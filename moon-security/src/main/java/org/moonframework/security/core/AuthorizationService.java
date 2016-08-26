package org.moonframework.security.core;

import org.moonframework.security.authentication.PermissionControl;

import java.util.Set;

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
     * @param appKey       appKey
     * @param userId       userId
     * @param defaultRoles defaultRoles
     * @return PermissionControl
     */
    PermissionControl doGetAuthorizationInfo(String appKey, Long userId, Set<String> defaultRoles);

    /**
     * @param appKey   appKey
     * @param username username
     * @return PermissionControl
     */
    PermissionControl doGetAuthorizationInfo(String appKey, String username);

    /**
     * @param appKey       appKey
     * @param username     username
     * @param defaultRoles defaultRoles
     * @return PermissionControl
     */
    PermissionControl doGetAuthorizationInfo(String appKey, String username, Set<String> defaultRoles);

}
