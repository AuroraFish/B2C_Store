package com.aurora.parama;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 9:59
 * @ DESCRIPTION 类别热门商品参数接收 提供给Product服务,查询单类别热门商品方法调用
 */
@Data
public class ProductPromoParam {

    @NotBlank
    private String categoryName;
}
