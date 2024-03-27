package com.aurora.admin.service;

import com.aurora.parama.PageParam;
import com.aurora.utils.R;

public interface OrderService {
    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/27 11:48
     * @ param pageParam 分页查询
     * @ return 订单数据
     * @ description 后台管理 查询订单数据
     */
    R list(PageParam pageParam);
}
