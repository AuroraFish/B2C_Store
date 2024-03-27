package com.aurora.order.service;

import com.aurora.parama.OrderParam;
import com.aurora.parama.PageParam;
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

    /**
     * @ author AuroraCjt
     * @ date 2024/3/26 15:50
     * @ param productId
     * @ return
     * @ description 被后台管理服务调用 检查订单中是否有商品引用
     */
    R check(Integer productId);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/27 11:33
     * @ param pageParam 分页查询
     * @ return 订单Vo对象
     * @ description 被后台管理服务调用 查询订单数据
     */
    R adminList(PageParam pageParam);
}
