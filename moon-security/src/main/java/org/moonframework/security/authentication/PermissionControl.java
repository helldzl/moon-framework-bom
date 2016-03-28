package org.moonframework.security.authentication;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/18
 */
public class PermissionControl implements Serializable {

    private static final long serialVersionUID = -6066156752418093514L;

    private Long siteId;
    private Long userId;
    private Set<String> roles = new HashSet<>();
    private Set<String> authorities = new HashSet<>();

    public PermissionControl() {
    }

    public PermissionControl(Long siteId, Long userId) {
        this.siteId = siteId;
        this.userId = userId;
    }

    /**
     * <p>是否具有该角色的访问控制</p>
     *
     * @param roleName 角色名称
     * @return true or false
     */
    public boolean hasRole(String roleName) {
        return roles.contains(roleName);
    }

    /**
     * <p>是否具有该权限资源的访问控制</p>
     *
     * @param permission 权限字符串
     * @return true or false
     */
    public boolean isPermitted(String permission) {
        return authorities.contains(permission);
    }

    public void add(Role role) {
        roles.add(role.getName());
        //roles.put(role.getName(), role);
    }

    public void add(Authority authority) {
        authorities.add(authority.getPermission());
        //authorities.put(authority.getPermission(), authority);
    }

    public Long getSiteId() {
        return siteId;
    }

    public Long getUserId() {
        return userId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(512);
        sb.append("site:").append(siteId).append(" user:").append(userId).append('\n');
        sb.append("roles:").append(roles).append('\n');
        sb.append("permissions:").append(authorities);
        return sb.toString();
    }

}
