package com.aurora.parama;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ author AuroraCjt
 * @ date 2024/3/21 14:07
 * @ DESCRIPTION 地址集合参数接收
 */
@Data
public class AddressListParam {

    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
}
