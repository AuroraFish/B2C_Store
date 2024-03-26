package com.aurora.parama;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ author AuroraCjt
 * @ date 2024/3/20 15:38
 * @ DESCRIPTION 用户登录参数接收
 */
@Data
public class UserLoginParam {

    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
