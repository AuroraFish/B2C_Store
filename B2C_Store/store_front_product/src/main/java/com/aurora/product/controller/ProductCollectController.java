package com.aurora.product.controller;

import com.aurora.parama.ProductCollectParam;
import com.aurora.product.service.ProductService;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 13:18
 * @ DESCRIPTION 被收藏服务调用的控制器类
 */
@RestController
@RequestMapping("product")
public class ProductCollectController {

    @Autowired
    private ProductService productService;

    @PostMapping("collect/list")
    public R productIds(@RequestBody @Validated ProductCollectParam productCollectParam, BindingResult result) {

        //参数校验
        if (result.hasErrors()) {
            return R.ok("没有收藏数据!");
        }

        return productService.ids(productCollectParam.getProductIds());
    }
}
