package org.moonframework.security.authentication;

import java.io.Serializable;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/18
 */
public class Authority implements Serializable {

    private static final long serialVersionUID = -1612231433775433714L;
    private Long id;
    private String name;
    private Integer priority;
    private String permission;

    public Authority(Long id, String name, String permission) {
        this.id = id;
        this.name = name;
        this.permission = permission;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Authority))
            return false;

        return ((Authority) obj).getId().equals(this.id);
    }

    @Override
    public int hashCode() {
        if (id == null)
            return super.hashCode();
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return "[name=" + this.name + "]";
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}
