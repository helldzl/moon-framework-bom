package org.moonframework.fragment.security.session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.web.servlet.AdviceFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/28
 */
public class ShiroSingleSignOutFilter extends AdviceFilter {

    protected final Logger logger = LogManager.getLogger(ShiroSingleSignOutFilter.class);

    private ShiroSingleSignOutHandler handler = new ShiroSingleSignOutHandler();

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (handler.isTokenRequest(httpServletRequest)) {
            handler.recordSession(httpServletRequest);
        } else if (handler.isLogoutRequest(httpServletRequest)) {
            handler.destroySession(httpServletRequest);
            // Do not continue up filter chain
            return false;
        } else {
            logger.debug(() -> "Ignoring URI " + httpServletRequest.getRequestURI());
        }

        return true;
    }

    public void setHandler(ShiroSingleSignOutHandler handler) {
        this.handler = handler;
    }
}
