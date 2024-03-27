package com.aurora.admin.controller;

import com.aurora.admin.service.OrderService;
import com.aurora.parama.PageParam;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ author AuroraCjt
 * @ date 2024/3/27 11:46
 * @ DESCRIPTION 后台管理 订单模块 的控制器类
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public R list(PageParam pageParam) {

        return orderService.list(pageParam);
    }
}
