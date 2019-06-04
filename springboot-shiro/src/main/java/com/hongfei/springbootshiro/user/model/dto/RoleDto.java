package com.hongfei.springbootshiro.user.model.dto;

import lombok.Data;


import java.util.List;

/**
 * @author hongfei.shen
 * @date 2019/05/29
 */
@Data
public class RoleDto {
    private Long id;
    private String name;
    private String description;
    private int status;
    private List<Long> permissionIdList;
}
