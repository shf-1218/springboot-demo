package com.hongfei.springbootshiro.user.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "role")
public class Role {
    /**
     * 角色ID
     */
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "ctime")
    private Date ctime;

    /**
     * 修改时间
     */
    @Column(name = "mtime")
    private Date mtime;

    /**
     * 启用状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 角色名
     */
    @Column(name = "name")
    private String name;

    /**
     * 角色描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 获取角色ID
     *
     * @return id - 角色ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置角色ID
     *
     * @param id 角色ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取创建时间
     *
     * @return ctime - 创建时间
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * 设置创建时间
     *
     * @param ctime 创建时间
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 获取修改时间
     *
     * @return mtime - 修改时间
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * 设置修改时间
     *
     * @param mtime 修改时间
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * 获取启用状态
     *
     * @return status - 启用状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置启用状态
     *
     * @param status 启用状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取角色名
     *
     * @return name - 角色名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名
     *
     * @param name 角色名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取角色描述
     *
     * @return description - 角色描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置角色描述
     *
     * @param description 角色描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}