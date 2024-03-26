package com.aurora.parama;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ author AuroraCjt
 * @ date 2024/3/20 14:25
 * @ DESCRIPTION 检查参数接收
 * TODO: 要使用jsr 303的注解 进行参数校验
 */
@Data
public class UserCheckParam {
    @NotBlank
    private String userName;    //参数名称要等于前端传递的JSON key的名称
}
