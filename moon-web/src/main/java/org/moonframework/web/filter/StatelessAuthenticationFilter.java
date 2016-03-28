package org.moonframework.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.moonframework.security.authentication.StatelessToken;
import org.moonframework.security.domain.User;
import org.moonframework.web.constants.LoginContextConstants;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>无状态的权限授权与认证</p>
 * <p>优先使用appMap, 如果为空则使用appKey.</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2015/12/31
 */
public class StatelessAuthenticationFilter extends AccessControlFilter {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    private String idAttribute = LoginContextConstants.KEY_UC_ID;
    private String userAttribute = LoginContextConstants.KEY_UC_NAME;
    private String appKey;
    private Map<String, String> appMap;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // return false, request should be handled by the onAccessDenied(request,response,mappedValue) method instead.
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            User user = getUser(httpServletRequest);
            if (user == null)
                return true;

            getSubject(request, response).login(new StatelessToken(user));
        } catch (IllegalArgumentException e) {
            logger.error(() -> "App key not found exception, please check configuration file, Host : " + request.getServerName(), e);
            return false;
        } catch (Exception e) {
            logger.error(() -> "StatelessAuthenticationFilter onAccessDenied method error", e);
            return false;
        }

        return true;
    }


    /**
     * <p>默认行为会从会话中获取用户名称, 根据请求获得用户信息用于验证权限, override此方法实现各个应用自己的逻辑</p>
     * <p>具体权限有缓存层</p>
     *
     * @param request request
     * @return Security User
     */
    protected User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object id = session.getAttribute(idAttribute);
        if (id == null)
            return null;

        Object username = session.getAttribute(userAttribute);
        String appKey = getAppKey(request);
        logger.debug(() -> String.format("Do Authentication Info! APP KEY : %s, userId : %s, username : %s", appKey, id, username));
        return new User(appKey, (Long) id, (String) username);
    }

    /**
     * <p>先用appMap, 不存在就使用appKey</p>
     * <p>appMap和appKey只能二选一</p>
     *
     * @param request request
     * @return app key
     */
    protected String getAppKey(HttpServletRequest request) {
        if (appMap == null || appMap.isEmpty())
            return appKey;
        String serverName = request.getServerName();
        return appMap.get(serverName);
    }

    /**
     * <p>设置用户会话属性, 默认为username</p>
     *
     * @param userAttribute user attribute
     */
    public void setUserAttribute(String userAttribute) {
        this.userAttribute = userAttribute;
    }

    /**
     * <p>设置应用的唯一KEY</p>
     *
     * @param appKey appKey
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * <p>设置不同域名与APP KEY的映射关系</p>
     *
     * @param appMap appMap
     */
    public void setAppMap(Map<String, String> appMap) {
        this.appMap = appMap;
    }
}
