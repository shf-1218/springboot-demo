package com.hongfei.springbootshiro.user.model.dto;

import lombok.Data;


/**
 * @author hongfei.shen
 * @date 2019/05/29
 */
@Data
public class OrganizationDto {
    private Long id;
    private String name;
    private String fullName;
    private String description;
    private Long parentId;
    private int status;
}
