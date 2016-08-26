/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.moonframework.web.cas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;
import org.moonframework.model.mybatis.domain.Response;
import org.moonframework.web.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Filter implementation to intercept all requests and attempt to authenticate
 * the user by redirecting them to CAS (unless the user has a ticket).
 * <p>
 * This filter allows you to specify the following parameters (at either the context-level or the filter-level):
 * <ul>
 * <li><code>casServerLoginUrl</code> - the url to log into CAS, i.e. https://cas.rutgers.edu/login</li>
 * <li><code>renew</code> - true/false on whether to use renew or not.</li>
 * <li><code>gateway</code> - true/false on whether to use gateway or not.</li>
 * </ul>
 * <p>
 * <p>Please see AbstractCasFilter for additional properties.</p>
 *
 * @author Scott Battaglia
 * @version $Revision: 11768 $ $Date: 2007-02-07 15:44:16 -0500 (Wed, 07 Feb 2007) $
 * @since 3.0
 */
public class AuthenticationFilterProxy extends AbstractCasFilter {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilterProxy.class);

    /**
     * JSON Object Mapper
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * The URL to the CAS Server login.
     */
    private String casServerLoginUrl;

    /**
     * Whether to send the renew request or not.
     */
    private boolean renew = false;

    /**
     * Whether to send the gateway request or not.
     */
    private boolean gateway = false;
    /**
     * 被忽略不被安全检查的url
     */
    private List<String> ignoralList = new ArrayList<String>();

    private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();

    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        if (!isIgnoreInitConfiguration()) {
            super.initInternal(filterConfig);
            setCasServerLoginUrl(getPropertyFromInitParams(filterConfig, "casServerLoginUrl", null));
            log.trace("Loaded CasServerLoginUrl parameter: " + this.casServerLoginUrl);
            setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
            log.trace("Loaded renew parameter: " + this.renew);
            setGateway(parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
            log.trace("Loaded gateway parameter: " + this.gateway);

            final String gatewayStorageClass = getPropertyFromInitParams(filterConfig, "gatewayStorageClass", null);

            if (gatewayStorageClass != null) {
                try {
                    this.gatewayStorage = (GatewayResolver) Class.forName(gatewayStorageClass).newInstance();
                } catch (final Exception e) {
                    log.error(e, e);
                    throw new ServletException(e);
                }
            }
        }
    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
    }

    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpSession session = request.getSession(false);
        final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;

        this.setServerName(request.getServerName());
        /*********************************************************/
        //判断忽略登录的URI，如果匹配，忽略登录，直接跳转到下一个过滤器
        String currentUrl = request.getServletPath();
        String actualUrl = StringUtils.isEmpty(currentUrl) ? "/" : currentUrl;
        if (!actualUrl.startsWith("/")) {
            actualUrl = "/" + actualUrl;
        }

        if ((!needCheckLogin(currentUrl)) && (!hasUserCookie(request)) ) {
            LOG.info("当前请求{}匹配成功，无需登录", currentUrl);
            filterChain.doFilter(request, response);
            return;
        }
        /*********************************************************/
        if (assertion != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String serviceUrl = constructServiceUrl(request, response);
        final String ticket = CommonUtils.safeGetParameter(request, getArtifactParameterName());
        final boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);

        if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
            filterChain.doFilter(request, response);
            return;

        }

        // CHECK HEADER START, 如果是AJAX请求, 进行处理
        String text = request.getHeader("x-requested-with");
        if ("XMLHttpRequest".equals(text)) {
            // https://tools.ietf.org/html/rfc7235#section-4.1
            String result = objectMapper.writeValueAsString(new Response(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), "Unauthorized"));
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            response.setContentLength(result.length());
            response.setHeader("WWW-Authenticate", "Form realm=\"user\"");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter out = response.getWriter();
            out.append(result);
            return;
        }
        // CHECK HEADER END

        final String modifiedServiceUrl;

        log.debug("no ticket and no assertion found");
        if (this.gateway) {
            log.debug("setting gateway attribute in session");
            modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
        } else {
            modifiedServiceUrl = serviceUrl;
        }

        if (log.isDebugEnabled()) {
            log.debug("Constructed service url: " + modifiedServiceUrl);
        }

        final String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);

        if (log.isDebugEnabled()) {
            log.debug("redirecting to \"" + urlToRedirectTo + "\"");
        }

        response.sendRedirect(urlToRedirectTo);
    }

    /**
     *
     * @Description: 判断是否存在用户ticket信息，有返回true，无返回false
     * @return
     */
    private boolean hasUserCookie(HttpServletRequest request) {
        return CookieUtils.getCookieValue(request,"CASTGC") == null ? false : true;
    }

    /**
     * @param url 当前请求的url
     * @return 是否需要登录
     * @Function: com.budee.qr.filter.AuthenticationFilterProxy.needCheckLogin
     * @Description: 判断是否需要登录，返回false，无需登录
     * @version:v0.0.1
     * @author:hongyang
     * @date:2015年7月17日 上午10:33:39
     */
    private boolean needCheckLogin(String url) {
        String actualUrl = StringUtils.isEmpty(url) ? "/" : url;
        ;
        if (!actualUrl.startsWith("/")) {
            actualUrl = "/" + actualUrl;
        }

        for (String resource : ignoralList) {
            if (actualUrl.startsWith(resource) || CheckLoginUtil.checkIgnoreLogin(actualUrl, resource)) {
                return false;
            }
        }

        return true;
    }

    public final void setRenew(final boolean renew) {
        this.renew = renew;
    }

    public final void setGateway(final boolean gateway) {
        this.gateway = gateway;
    }

    public final void setCasServerLoginUrl(final String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }

    /**
     * @param ignoralList the ignoralList to set
     */

    public void setIgnoralList(List<String> ignoralList) {
        this.ignoralList = ignoralList;
    }

    public final void setGatewayStorage(final GatewayResolver gatewayStorage) {
        this.gatewayStorage = gatewayStorage;
    }

}
