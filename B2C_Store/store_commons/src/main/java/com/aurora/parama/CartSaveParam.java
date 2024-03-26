package com.aurora.parama;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 14:19
 * @ DESCRIPTION 购物车添加参数接收
 */
@Data
public class CartSaveParam {

    @JsonProperty("product_id")
    @NotNull
    private Integer productId;

    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
}
