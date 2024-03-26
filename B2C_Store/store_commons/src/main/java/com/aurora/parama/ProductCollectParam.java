package com.aurora.parama;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 13:16
 * @ DESCRIPTION 收藏服务调用商品服务传递的参数
 */
@Data
public class ProductCollectParam {

    @NotEmpty
    private List<Integer> productIds;
}
