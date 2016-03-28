package org.moonframework.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.moonframework.model.mybatis.domain.Response;
import org.moonframework.security.core.annotation.WebSecured;
import org.springframework.context.MessageSource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc-config-interceptors">interceptors</a>
 *
 * @author quzile
 * @version 1.0
 * @since 2015/12/14
 */
public class AppSecurityInterceptor extends HandlerInterceptorAdapter {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    // fully thread safe
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 默认没有权限时重定向的地址
     */
    private String redirect = "/unauthorized";

    /**
     * 国际化支持
     */
    private MessageSource messages;

    /**
     * code
     */
    private String code = "error.unauthorized";

    /**
     * default message
     */
    private static final String DEFAULT_MESSAGE = "Authorization failed";

    /**
     * <p>权限拦截器核心业务逻辑</p>
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return true or false
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            boolean isPermitted = isPermitted(method, handlerMethod.getBeanType().getName() + "." + method.getName());
            // unauthorized
            if (!isPermitted) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Request is not permitted, Method : " + handlerMethod.getBeanType().getName() + "." + method.getName());
                }

                String text = request.getHeader("x-requested-with");
                if (text != null && "XMLHttpRequest".equals(text)) {
                    // Ajax request
                    String msg = messages.getMessage(code, null, DEFAULT_MESSAGE, null);
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=utf-8");

                    PrintWriter out = response.getWriter();
                    out.append(objectMapper.writeValueAsString(new Response(code, msg)));
                } else {
                    // normal request
                    response.sendRedirect(redirect);
                }
                return false;
            }
        }

        return super.preHandle(request, response, handler);
    }

    /**
     * <p>只有被安全注解的方法才参与验证</p>
     *
     * @param method     method
     * @param permission permission
     * @return true if permitted or has role
     */
    private boolean isPermitted(Method method, String permission) {
        // check if method or class is @WebSecured annotation present
        Set<String> roles = new HashSet<>();
        value(method.getDeclaringClass(), roles);
        value(method, roles);
        if (roles.isEmpty())
            return true;

        // is authenticated
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated())
            return false;

        // check roles or permissions
        if (roles.size() == 1 && roles.contains(""))
            return currentUser.isPermitted(permission);
        else
            return currentUser.hasAllRoles(roles);
    }

    /**
     * <p>获取value集合</p>
     *
     * @param annotatedElement annotatedElement
     * @param set              set
     */
    private void value(AnnotatedElement annotatedElement, Set<String> set) {
        if (annotatedElement.isAnnotationPresent(WebSecured.class))
            set.addAll(Arrays.asList(annotatedElement.getAnnotation(WebSecured.class).value()));
    }

    /**
     * <p>PRINT</p>
     *
     * @param username username
     * @param method   method
     */
    private void print(String username, String method) {
        System.out.printf("User : %s, Method : %s %n", username, method);
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = messageSource;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
