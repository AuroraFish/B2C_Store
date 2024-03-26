package com.aurora.parama;

import lombok.Data;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 15:57
 * @ DESCRIPTION 分页参数
 */
@Data
public class PageParam {

    private int currentPage = 1;

    private int pageSize = 15;
}
