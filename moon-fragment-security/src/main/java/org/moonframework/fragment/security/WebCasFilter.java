package org.moonframework.fragment.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.moonframework.security.domain.User;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>扩展CAS Filter, 这里需要完成2个新功能</p>
 * <ul>
 * <li>增加APP KEYS的验证</li>
 * <li>认证成功后需要查询用户详细信息</li>
 * </ul>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/4/20
 */
public class WebCasFilter extends CasFilter {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 登陆成功后处理逻辑的抽象接口
     */
    private WebLogin webLogin;

    /**
     * 单个app key
     */
    private String appKey;

    /**
     * 多个app key, 优先验证appMap
     */
    private Map<String, String> appKeys;

    /**
     * <p>创建TOKEN</p>
     *
     * @param request  request
     * @param response response
     * @return AuthenticationToken
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ticket = httpRequest.getParameter("ticket");
        String appKey = getAppKey(httpRequest);
        if (appKey == null)
            throw new IllegalArgumentException("App key not found exception, please check configuration file, Host : " + request.getServerName());
        return new WebCasToken(ticket, appKey);
    }

    /**
     * <p>登录成功后进行会话处理</p>
     *
     * @param token    token
     * @param subject  subject
     * @param request  request
     * @param response response
     * @return boolean
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        Session session = subject.getSession();
        User user = (User) subject.getPrincipal();
        if (webLogin != null)
            webLogin.onLogin(user, session);
        issueSuccessRedirect(request, response);
        return false;
    }

    /**
     * <p>获得当前系统的APP KEY</p>
     *
     * @param request request
     * @return APP KEY
     */
    protected String getAppKey(HttpServletRequest request) {
        return CollectionUtils.isEmpty(appKeys) ? appKey : appKeys.get(request.getServerName());
    }

    // set method

    public void setWebLogin(WebLogin webLogin) {
        this.webLogin = webLogin;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setAppKeys(Map<String, String> appKeys) {
        this.appKeys = appKeys;
    }
}
