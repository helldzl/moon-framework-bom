package org.moonframework.fragment.security.session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/28
 */
public class ShiroSingleSignOutHandler {

    protected final Logger logger = LogManager.getLogger(ShiroSingleSignOutHandler.class);

    /**
     * Mapping of token IDs and session IDs to Shiro sessions
     */
    private DefaultWebSessionMappingStorage sessionMappingStorage;

    /**
     * The name of the artifact parameter.  This is used to capture the session identifier.
     */
    private String artifactParameterName = "ticket";

    /**
     * Parameter name that stores logout request
     */
    private String logoutParameterName = "logoutRequest";

    // get and set

    public DefaultWebSessionMappingStorage getSessionMappingStorage() {
        return sessionMappingStorage;
    }

    public void setSessionMappingStorage(DefaultWebSessionMappingStorage sessionMappingStorage) {
        this.sessionMappingStorage = sessionMappingStorage;
    }

    public void setArtifactParameterName(String artifactParameterName) {
        this.artifactParameterName = artifactParameterName;
    }

    public void setLogoutParameterName(String logoutParameterName) {
        this.logoutParameterName = logoutParameterName;
    }

    /**
     * Initializes the component for use.
     */
    public void init() {
        CommonUtils.assertNotNull(this.artifactParameterName, "artifactParameterName cannot be null.");
        CommonUtils.assertNotNull(this.logoutParameterName, "logoutParameterName cannot be null.");
        CommonUtils.assertNotNull(this.sessionMappingStorage, "sessionMappingStorage cannot be null.");
    }

    /**
     * Determines whether the given request contains an authentication token.
     *
     * @param request HTTP request.
     * @return True if request contains authentication token, false otherwise.
     */
    public boolean isTokenRequest(HttpServletRequest request) {
        return CommonUtils.isNotBlank(CommonUtils.safeGetParameter(request, this.artifactParameterName));
    }

    /**
     * Determines whether the given request is a CAS logout request.
     *
     * @param request HTTP request.
     * @return True if request is logout request, false otherwise.
     */
    public boolean isLogoutRequest(HttpServletRequest request) {
        return "POST".equals(request.getMethod()) && !isMultipartRequest(request) &&
                CommonUtils.isNotBlank(CommonUtils.safeGetParameter(request, this.logoutParameterName));
    }

    /**
     * Associates a token request with the current session by recording the mapping
     *
     * @param request HTTP request containing an authentication token.
     */
    public void recordSession(HttpServletRequest request) {
        Session session = SecurityUtils.getSubject().getSession();
        String token = CommonUtils.safeGetParameter(request, artifactParameterName);

        logger.debug(() -> "Recording session for token " + token);

        sessionMappingStorage.remove(session);
        sessionMappingStorage.put(token, session);
    }

    /**
     * Destroys the current session for the given CAS logout request.
     *
     * @param request HTTP request containing a CAS logout message.
     */
    public void destroySession(final HttpServletRequest request) {
        String logoutMessage = CommonUtils.safeGetParameter(request, logoutParameterName);
        logger.debug(() -> "Logout request:\n" + logoutMessage);

        String token = XmlUtils.getTextForElement(logoutMessage, "SessionIndex");
        if (CommonUtils.isNotBlank(token)) {
            Session session = sessionMappingStorage.get(token);
            if (session != null) {
                try {
                    session.stop();
                    logger.debug(() -> "Invalidating session [" + session.getId() + "] for token [" + token + "]");
                } catch (IllegalStateException e) {
                    logger.debug(() -> "Error invalidating session.", e);
                }
            }
        }
    }

    private boolean isMultipartRequest(HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().toLowerCase().startsWith("multipart");
    }

}
