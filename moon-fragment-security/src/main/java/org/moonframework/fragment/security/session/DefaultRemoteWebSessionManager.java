package org.moonframework.fragment.security.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import java.io.Serializable;

/**
 * <p>支持基于RMI方式的会话持久化</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/4/29
 */
public class DefaultRemoteWebSessionManager extends DefaultWebSessionManager {

    /**
     * <p>因为是通过RMI方式进行持久化操作, ID是在远程生成的, 所以需要手工设置值. 而本地方式引用的session是同一个实例所以不需要处理.</p>
     *
     * @param session
     */
    @Override
    protected void create(Session session) {
        Serializable sessionId = sessionDAO.create(session);
        if (session.getId() == null)
            ((SimpleSession) session).setId(sessionId);
    }
}
