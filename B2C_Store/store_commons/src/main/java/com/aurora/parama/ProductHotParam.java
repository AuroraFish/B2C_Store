package com.aurora.parama;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 11:45
 * @ DESCRIPTION 热门商品参数接收对象 提供给Product服务,查询多类别热门商品方法调用
 */
@Data
public class ProductHotParam {

    @NotEmpty
    private List<String> categoryName;
}
