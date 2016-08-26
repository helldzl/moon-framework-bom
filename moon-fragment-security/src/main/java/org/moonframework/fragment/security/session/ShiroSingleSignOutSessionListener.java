package org.moonframework.fragment.security.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 * <p>监听会话</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/4/28
 */
public class ShiroSingleSignOutSessionListener extends SessionListenerAdapter {

    private DefaultWebSessionMappingStorage sessionMappingStorage;

    @Override
    public void onStop(Session session) {
        sessionMappingStorage.remove(session);
    }

    public void setSessionMappingStorage(DefaultWebSessionMappingStorage sessionMappingStorage) {
        this.sessionMappingStorage = sessionMappingStorage;
    }

}
