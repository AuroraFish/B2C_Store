package com.aurora.clients;

import com.aurora.parama.PageParam;
import com.aurora.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @ author AuroraCjt
* @ date   2024/3/26 15:53
* @ DESCRIPTION 订单服务调用接口
*/
@FeignClient("order-service")
public interface OrderClient {

    @PostMapping("/order/remove/check")
    R check(@RequestBody Integer product_Id);

    @PostMapping("/order/admin/list")
    R check(@RequestBody PageParam pageParam);
}
