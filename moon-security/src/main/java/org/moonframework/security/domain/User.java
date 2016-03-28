package org.moonframework.security.domain;

import java.io.Serializable;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/4
 */
public class User implements Serializable {

    private static final long serialVersionUID = -5820185835328705157L;
    private Long userId;
    private String appKey;
    private String username;

    /**
     * @param appKey   appKey, notNull
     * @param userId   userId, notNull
     * @param username username
     */
    public User(String appKey, Long userId, String username) {
        if (appKey == null || userId == null)
            throw new IllegalArgumentException("appKey or userId is null");
        this.userId = userId;
        this.appKey = appKey;
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof User))
            return false;

        return appKey.equals(((User) obj).getAppKey()) && userId.equals(((User) obj).getUserId());
    }

    @Override
    public int hashCode() {
        return (appKey + ":" + userId).hashCode();
    }

    public Long getUserId() {
        return userId;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getUsername() {
        return username;
    }

}
