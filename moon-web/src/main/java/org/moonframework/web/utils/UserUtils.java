package org.moonframework.web.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.moonframework.security.domain.User;

public class UserUtils {
    public final static Long getCurrentUserId() {
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser.isAuthenticated()) {
            return ((User) currentUser.getPrincipal()).getUserId();
        } else {
            return 0L;
        }
    }
    
    public final static boolean checkCurrentUserRoles(String roleIdentifier) {
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser.isAuthenticated()) {
            return currentUser.hasRole(roleIdentifier);
        } else {
            return false;
        }
    }
}
