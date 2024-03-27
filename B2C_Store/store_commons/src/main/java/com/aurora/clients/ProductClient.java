package com.aurora.clients;

import com.aurora.parama.ProductCollectParam;
import com.aurora.parama.ProductIdParam;
import com.aurora.parama.ProductSaveParam;
import com.aurora.pojo.Product;
import com.aurora.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 14:30
 * @ DESCRIPTION 商品服务调用接口
 */
@FeignClient("product-service")
public interface ProductClient {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 14:31
     * @ param none
     * @ return 商品数据集合
     * @ description 被搜索服务调用,获取全部商品数据,进行同步
     */
    @GetMapping("/product/list")
    List<Product> allList();

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 13:27
     * @ param productCollectParam 商品ID集合
     * @ return 商品信息集合
     * @ description 被收藏服务调用,根据商品ID集合,获取商品信息集合
     */
    @PostMapping("/product/collect/list")
    R productIds(@RequestBody ProductCollectParam productCollectParam);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 15:07
     * @ param productIdParam 商品ID
     * @ return 商品数据信息
     * @ description 被购物车服务调用,根据商品ID,获取商品信息
     */
    @PostMapping("/product/cart/detail")
    Product productDetail(@RequestBody ProductIdParam productIdParam);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 15:09
     * @ param productCollectParam 商品ID集合
     * @ return 商品信息集合
     * @ description 被购物车服务调用,根据商品ID集合,获取商品信息集合
     */
    @PostMapping("/product/cart/list")
    List<Product> cartList(@RequestBody ProductCollectParam productCollectParam);

    @PostMapping("/product/admin/count")
    Long adminCount(@RequestBody Integer categoryId);

    @PostMapping("/product/admin/save")
    R adminSave(@RequestBody ProductSaveParam productSaveParam);

    @PostMapping("/product/admin/update")
    R adminUpdate(@RequestBody Product product);

    @PostMapping("/product/admin/remove")
    R adminRemove(@RequestBody Integer productId);
}
