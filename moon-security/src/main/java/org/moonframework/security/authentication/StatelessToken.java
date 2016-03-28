package org.moonframework.security.authentication;

import org.apache.shiro.authc.AuthenticationToken;
import org.moonframework.security.domain.User;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/31
 */
public class StatelessToken implements AuthenticationToken {

    private User user;

    public StatelessToken(User user) {
        this.user = user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}
