package com.hongfei.springbootshiro.user.model.dto;

import lombok.Data;

/**
 * @author hongfei.shen
 * @date 2019/06/03
 */
@Data
public class PasswordDto {
    private Long id;
    private String newPassword;
    private String repeatNewPassword;

}
