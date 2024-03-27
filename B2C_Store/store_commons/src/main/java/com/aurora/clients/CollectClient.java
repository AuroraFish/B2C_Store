package com.aurora.clients;

import com.aurora.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @ author AuroraCjt
* @ date   2024/3/26 15:57
* @ DESCRIPTION 收藏服务调用接口
*/
@FeignClient("collect-service")
public interface CollectClient {

    @PostMapping("/collect/remove/product")
    R remove(@RequestBody Integer productId);
}
