package org.moonframework.fragment.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.moonframework.security.authentication.PermissionControl;
import org.moonframework.security.core.AuthorizationService;
import org.moonframework.security.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>认证与授权</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/4/20
 */
public class WebCasRealm extends CasRealm implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(WebCasRealm.class);

    private AuthorizationService authorizationService;

    private String userId = "ucid";

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (authorizationService == null)
            throw new IllegalArgumentException("Please set authorization service, AuthorizationService can not be null");
    }

    /**
     * <p>授权</p>
     *
     * @param principals principals
     * @return AuthorizationInfo
     */
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

        // find default Roles and Permissions
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        split(getDefaultRoles()).forEach(info::addRole);

        // find user Roles and Permissions
        PermissionControl control = authorizationService.doGetAuthorizationInfo(user.getAppKey(), user.getUserId(), info.getRoles());
        if (control != null) {
            control.getRoles().forEach(info::addRole);
            control.getAuthorities().forEach(info::addStringPermission);
        }
        return info;
    }

    /**
     * <p>认证</p>
     *
     * @param token token
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    @SuppressWarnings("unchecked")
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        WebCasToken casToken = (WebCasToken) token;
        if (token == null) {
            return null;
        }

        String ticket = (String) casToken.getCredentials();
        if (!StringUtils.hasText(ticket)) {
            return null;
        }

        TicketValidator ticketValidator = ensureTicketValidator();

        try {
            // contact CAS server to validate service ticket
            Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
            // get principal, user id and attributes
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String userId = casPrincipal.getName();
            log.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[]{
                    ticket, getCasServerUrlPrefix(), userId
            });

            Map<String, Object> attributes = casPrincipal.getAttributes();
            // refresh authentication token (user id + remember me)
            casToken.setUserId(userId);
            String rememberMeAttributeName = getRememberMeAttributeName();
            String rememberMeStringValue = (String) attributes.get(rememberMeAttributeName);
            boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
            if (isRemembered) {
                casToken.setRememberMe(true);
            }
            // create simple authentication info
            // List<Object> principals = CollectionUtils.asList(userId, attributes);
            Object principals = principals(casToken.getAppKey(), userId, attributes);
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
            return new SimpleAuthenticationInfo(principalCollection, ticket);
        } catch (TicketValidationException e) {
            throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
        }
    }

    private User principals(String appKey, String name, Map<String, Object> attributes) {
        Object id = attributes.get(userId);
        if (id == null)
            throw new IllegalArgumentException("User id is null");
        return new User(appKey, Long.valueOf((String) id), name);
    }

    /**
     * Split a string into a list of not empty and trimmed strings, delimiter is a comma.
     *
     * @param s the input string
     * @return the list of not empty and trimmed strings
     */
    private List<String> split(String s) {
        List<String> list = new ArrayList<>();
        String[] elements = StringUtils.split(s, ',');
        if (elements != null && elements.length > 0) {
            for (String element : elements) {
                if (StringUtils.hasText(element)) {
                    list.add(element.trim());
                }
            }
        }
        return list;
    }

}
