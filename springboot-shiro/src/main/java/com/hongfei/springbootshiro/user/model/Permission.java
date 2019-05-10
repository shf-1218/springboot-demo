package com.hongfei.springbootshiro.user.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "permission")
public class Permission {
    /**
     * 权限ID
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
     * 权限名
     */
    @Column(name = "name")
    private String name;

    /**
     * 权限编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 权限描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 获取权限ID
     *
     * @return id - 权限ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置权限ID
     *
     * @param id 权限ID
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
     * 获取权限名
     *
     * @return name - 权限名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置权限名
     *
     * @param name 权限名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取权限编码
     *
     * @return code - 权限编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置权限编码
     *
     * @param code 权限编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取权限描述
     *
     * @return description - 权限描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置权限描述
     *
     * @param description 权限描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}