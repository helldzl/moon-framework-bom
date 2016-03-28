package org.moonframework.security.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.moonframework.security.authentication.PermissionControl;
import org.moonframework.security.authentication.StatelessToken;
import org.moonframework.security.authentication.credential.SecureCredentialsMatcher;
import org.moonframework.security.core.AuthorizationService;
import org.moonframework.security.domain.User;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/31
 */
public class StatelessRealm extends AuthorizingRealm {

    private AuthorizationService service;

    public StatelessRealm() {
        super();
        setName("StatelessRealm");
        setCredentialsMatcher(new SecureCredentialsMatcher());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // null username are invalid
        if (principals == null)
            throw new AuthorizationException("Principals argument cannot be null.");

        // get the principal this realm cares about:
        User user = (User) getAvailablePrincipal(principals);

        // null app key are invalid
        if (user.getAppKey() == null)
            throw new AuthorizationException("App Key argument cannot be null.");

        PermissionControl control = service.doGetAuthorizationInfo(user.getAppKey(), user.getUserId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (control != null) {
            info.setRoles(control.getRoles());
            info.setStringPermissions(control.getAuthorities());
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) token;
        User user = (User) statelessToken.getPrincipal();
        return new SimpleAuthenticationInfo(user, null, getName());
    }

    public void setService(AuthorizationService service) {
        this.service = service;
    }
}
