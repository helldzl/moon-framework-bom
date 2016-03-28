package org.moonframework.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.moonframework.web.constants.LoginContextConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class UserSaveSessionFilter implements Filter {
    
    protected final Logger logger = LogManager.getLogger(this.getClass());
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (servletRequest instanceof HttpServletRequestWrapper){
            HttpServletRequest request = (HttpServletRequest) ((HttpServletRequestWrapper)servletRequest).getRequest();

            HttpSession session = request.getSession(true);
            Assertion assertion = AssertionHolder.getAssertion();
            // 如果是第一次打开，在AuthenticationFilterProxy会转发/login,所以要直接跳走
            if (assertion == null) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }else{
                if(isEmpty(session.getAttribute(LoginContextConstants.KEY_UC_NAME))){
                    String ucName = assertion.getPrincipal().getName();
                    Long ucid = 1L;
                    if(null !=assertion.getPrincipal().getAttributes().get("ucid")){
                        ucid = Long.valueOf((String)assertion.getPrincipal().getAttributes().get("ucid") );
                    }
                    logger.info(ucName);
                    session.setAttribute(LoginContextConstants.KEY_UC_NAME, ucName);
                    session.setAttribute(LoginContextConstants.KEY_UC_ID, ucid);
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    @Override
    public void destroy() {
    }
    
    public boolean isEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if ("".equals(pObj))
            return true;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }else if(pObj.getClass().isArray()){
            if(((Object[])pObj).length == 0){
                return true;
            }
        }
        return false;
    }
}
