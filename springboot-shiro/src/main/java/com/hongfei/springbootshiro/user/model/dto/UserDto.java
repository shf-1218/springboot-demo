package com.hongfei.springbootshiro.user.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;




import java.util.List;

/**
 * @author hongfei.shen
 * @date 2019/05/30
 */
@Data
public class UserDto {
    private Long id;
    private String userName;
    private String nickName;
    private String password;
    @JsonIgnore
    private String salt;
    private String email;
    private String phone;
    private String mobile;
    private int status;
    private List<Long> permissionIdList;
    private List<Long> jobIdList;

}
