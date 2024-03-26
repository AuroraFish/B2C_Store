package com.aurora.parama;

import lombok.Data;


/**
 * @ author AuroraCjt
 * @ date 2024/3/22 15:56
 * @ DESCRIPTION 搜索关键字和分页参数
 */
@Data
public class ProductSearchParam extends PageParam{

    private String search;
}
