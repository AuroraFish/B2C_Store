package com.aurora.parama;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 15:00
 * @ DESCRIPTION 购物车查询接收参数
 */
@Data
public class CartListParam {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;
}
