package com.aurora.clients;

import com.aurora.parama.ProductSearchParam;
import com.aurora.pojo.Product;
import com.aurora.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 16:46
 * @ DESCRIPTION 搜索服务调用接口
 */
@FeignClient("search-service")
public interface SearchClient {

    @PostMapping("/search/product")
    R search(@RequestBody ProductSearchParam productSearchParam);

    @PostMapping("/search/save")
    R saveOrUpdate(@RequestBody Product product);

    @PostMapping("/search/remove")
    R remove(@RequestBody Integer productId);
}
