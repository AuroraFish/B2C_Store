package com.aurora.admin.service.impl;

import com.aurora.admin.service.OrderService;
import com.aurora.clients.OrderClient;
import com.aurora.parama.PageParam;
import com.aurora.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ author AuroraCjt
 * @ date 2024/3/27 11:47
 * @ DESCRIPTION 订单业务实现类
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderClient orderClient;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/27 11:48
     * @ param pageParam 分页查询
     * @ return 订单数据
     * @ description 后台管理 查询订单数据
     */
    @Override
    public R list(PageParam pageParam) {

        R r = orderClient.check(pageParam);
        log.info("OrderServiceImpl.list业务结束, 结果{}",r);
        return r;
    }
}
