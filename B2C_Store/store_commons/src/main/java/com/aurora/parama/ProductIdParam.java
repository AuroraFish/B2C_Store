package com.aurora.parama;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 13:05
 * @ DESCRIPTION 商品Id参数接收 提供给Product服务,查询商品详情/商品图片详情方法调用
 */
@Data
public class ProductIdParam {

    @NotNull
    private Integer productID;
}
