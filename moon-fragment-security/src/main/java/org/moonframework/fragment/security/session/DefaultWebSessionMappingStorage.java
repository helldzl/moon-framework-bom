package org.moonframework.fragment.security.session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/28
 */
public class DefaultWebSessionMappingStorage {

    protected final Logger logger = LogManager.getLogger(DefaultWebSessionMappingStorage.class);

    // token 到 session 的映射
    private final Map<String, Session> sessions = new HashMap<>();

    // session id 到 token的映射
    private final Map<Serializable, String> mapping = new HashMap<>();

    public synchronized Session get(String token) {
        return sessions.get(token);
    }

    public synchronized void put(String token, Session session) {
        mapping.put(session.getId(), token);
        sessions.put(token, session);
    }

    public synchronized void remove(Session session) {
        Serializable id = session.getId();

        logger.debug(() -> "Attempting to remove Session=[" + id + "]");

        String token = mapping.get(id);
        sessions.remove(token);
        mapping.remove(id);
    }

}
