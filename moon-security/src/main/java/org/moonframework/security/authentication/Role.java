package org.moonframework.security.authentication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/18
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 8937446928125823599L;
    private Long id;
    private String name;
    private Map<String, Authority> authorities = new HashMap<>();

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void add(Authority authority) {
        authorities.put(authority.getPermission(), authority);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Role))
            return false;

        return (((Role) obj).getId().equals(this.id));
    }

    @Override
    public int hashCode() {
        if (id == null)
            return super.hashCode();
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return "[name=" + this.name + ", values=(" + this.authorities + ")]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Map<String, Authority> authorities) {
        this.authorities = authorities;
    }
}
