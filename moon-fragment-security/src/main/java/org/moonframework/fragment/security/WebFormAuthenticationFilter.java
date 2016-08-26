package org.moonframework.fragment.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.moonframework.model.mybatis.domain.Response;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>Requires the requesting user to be authenticated for the request to continue, and if they are not, forces the user to login via by redirecting them to the loginUrl you configure.</p>
 * <p>
 * <a href="http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authc/FormAuthenticationFilter.html">FormAuthenticationFilter</a>
 * </p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/4/23
 */
public class WebFormAuthenticationFilter extends FormAuthenticationFilter {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (logger.isTraceEnabled()) {
                    logger.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (logger.isTraceEnabled()) {
                    logger.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (logger.isTraceEnabled()) {
                logger.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            // ajax
            if (isXMLHttpRequest(request))
                response(response);
            else
                saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    /**
     * <p>是否是XMLHttpRequest请求</p>
     *
     * @param request request
     * @return true of false
     */
    protected boolean isXMLHttpRequest(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        return "XMLHttpRequest".equals(httpServletRequest.getHeader("x-requested-with"));
    }

    /**
     * <p>输出信息</p>
     *
     * @param response ServletResponse
     */
    protected void response(ServletResponse response) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            // https://tools.ietf.org/html/rfc7235#section-4.1
            String result = objectMapper.writeValueAsString(new Response(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), "Unauthorized"));
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setContentLength(result.length());
            httpServletResponse.setHeader("WWW-Authenticate", "Form realm=\"user\"");
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter out = httpServletResponse.getWriter();
            out.append(result);
        } catch (IOException ex) {
            logger.error(() -> "onLoginFailure Error", ex);
        }
    }

}
