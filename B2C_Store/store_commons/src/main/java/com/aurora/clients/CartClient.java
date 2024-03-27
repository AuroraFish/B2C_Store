package com.aurora.clients;

import com.aurora.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @ author AuroraCjt
* @ date   2024/3/26 15:48
* @ DESCRIPTION 购物车服务调用接口
*/
@FeignClient("cart-service")
public interface CartClient {

    @PostMapping("/cart/remove/check")
    R check(@RequestBody Integer productId);
}
