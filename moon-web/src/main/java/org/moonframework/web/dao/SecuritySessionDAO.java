package org.moonframework.web.dao;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.moonframework.web.utils.SerializableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.List;

/**
 * Session持久化
 *
 * @author quzile
 * @version 1.0
 * @since 2015/12/1
 */
public class SecuritySessionDAO extends CachingSessionDAO {

    protected JdbcTemplate jdbcTemplate;

    private static final String UPDATE = "UPDATE sessions SET session = ? WHERE session_id = ?";
    private static final String DELETE = "DELETE FROM sessions WHERE session_id = ?";
    private static final String INSERT = "INSERT INTO sessions(session_id, session) VALUES(?, ?)";
    private static final String SELECT = "SELECT session FROM sessions WHERE session_id = ?";

    public SecuritySessionDAO() {
        this(null);
    }

    public SecuritySessionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        setActiveSessionsCacheName("shiro-activeSessionCache");
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid())
            return;
        jdbcTemplate.update(UPDATE, SerializableUtils.serialize(session), session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        jdbcTemplate.update(DELETE, session.getId());
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        jdbcTemplate.update(INSERT, sessionId, SerializableUtils.serialize(session));
        return session.getId();
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        List<String> list = jdbcTemplate.queryForList(SELECT, String.class, sessionId);
        if (list.size() == 0)
            return null;
        return SerializableUtils.deserialize(list.get(0));
    }

}
