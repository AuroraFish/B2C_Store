package com.aurora.order.service;

import com.aurora.parama.OrderParam;
import com.aurora.pojo.Order;
import com.aurora.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<Order> {
    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 16:21
     * @ param orderParam
     * @ return r
     * @ description 进行订单数据保存业务
     */
    R save(OrderParam orderParam);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 17:18
     * @ param userId 用户ID
     * @ return 订单数据
     * @ description 分组查询订单数据
     */
    R list(Integer userId);
}
