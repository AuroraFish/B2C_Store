package com.aurora.parama;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 12:46
 * @ DESCRIPTION 类别商品展示 提供给Product服务,查询类别方法调用
 */
@Data
public class ProductIdsParam extends PageParam{

    @NotNull
    private List<Integer> categoryID;
}
