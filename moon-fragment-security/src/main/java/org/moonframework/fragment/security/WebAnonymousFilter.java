package org.moonframework.fragment.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>判断如果客户端没有登录, 且存在Ticket Granted Cookie, 且非Ajax请求的情况</p>
 * <p>满足条件就尝试进行登录</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/4/23
 */
public class WebAnonymousFilter extends AnonymousFilter {

    private String loginUrl = "/login.jsp";
    private String ticketGrantedCookieName;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (!SecurityUtils.getSubject().isAuthenticated() && hasTGCandNotAjax(request)) {
            // attempt login
            try {
                WebUtils.saveRequest(request);
                WebUtils.issueRedirect(request, response, loginUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean hasTGCandNotAjax(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Cookie[] cookies = httpServletRequest.getCookies();
        String ajaxHeader = httpServletRequest.getHeader("x-requested-with");

        if (ajaxHeader != null && ajaxHeader.equalsIgnoreCase("XMLHttpRequest")){
            return false;
        }

        if (cookies == null)
            return false;

        for (Cookie cookie : cookies) {
            if (ticketGrantedCookieName.equals(cookie.getName()))
                return true;
        }

        return false;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public void setTicketGrantedCookieName(String ticketGrantedCookieName) {
        this.ticketGrantedCookieName = ticketGrantedCookieName;
    }
}
