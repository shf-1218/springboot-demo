package com.hongfei.springbootshiro.user.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "role_permission")
public class RolePermission {
    /**
     * 角色权限关系ID
     */
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 插入时间
     */
    @Column(name = "ctime")
    private Date ctime;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 权限ID
     */
    @Column(name = "permission_id")
    private Long permissionId;

    /**
     * 获取角色权限关系ID
     *
     * @return id - 角色权限关系ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置角色权限关系ID
     *
     * @param id 角色权限关系ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取插入时间
     *
     * @return ctime - 插入时间
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * 设置插入时间
     *
     * @param ctime 插入时间
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取权限ID
     *
     * @return permission_id - 权限ID
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * 设置权限ID
     *
     * @param permissionId 权限ID
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}