package com.aurora.parama;

import com.aurora.vo.CartVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 16:07
 * @ DESCRIPTION 订单接收参数
 */
@Data
public class OrderParam implements Serializable {

    public static final Long serialVersionUID = 1L;


    @JsonProperty("user_id")
    private Integer userId;
    private List<CartVo> products;
}
