package org.moonframework.fragment.security;

import org.apache.shiro.session.Session;
import org.moonframework.security.domain.User;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/21
 */
public interface WebLogin {

    /**
     * <p>用户登录成功后, 需要根据CAS Server返回的用户身份(principals)查询系统特定的用户详细信息, 并保存到用户会话</p>
     *
     * @param user    CAS server responds by the identity of the authenticated user
     * @param session Shiro Session
     */
    void onLogin(User user, Session session);

}
