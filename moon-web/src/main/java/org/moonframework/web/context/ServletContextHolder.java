package org.moonframework.web.context;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/8
 */
public class ServletContextHolder implements ServletContextAware {

    private static ServletContext servletContext;

    public ServletContextHolder() {
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        if (this.servletContext == null)
            this.servletContext = servletContext;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

}
