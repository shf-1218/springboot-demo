package com.hongfei.springbootshiro.user.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_role")
public class UserRole {
    /**
     * 用户角色关系ID
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
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 获取用户角色关系ID
     *
     * @return id - 用户角色关系ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置用户角色关系ID
     *
     * @param id 用户角色关系ID
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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
}