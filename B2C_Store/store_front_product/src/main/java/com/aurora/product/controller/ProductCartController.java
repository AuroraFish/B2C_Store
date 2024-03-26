package com.aurora.product.controller;

import com.aurora.parama.ProductCollectParam;
import com.aurora.parama.ProductIdParam;
import com.aurora.pojo.Product;
import com.aurora.product.service.ProductService;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 14:05
 * @ DESCRIPTION 被购物车服务调用的控制器类
 */
@RestController
@RequestMapping("product")
public class ProductCartController {

    @Autowired
    private ProductService productService;

    @PostMapping("cart/detail")
    public Product cdetail(@RequestBody @Validated ProductIdParam productIdParam, BindingResult result) {

        //参数校验
        if (result.hasErrors()) {
            return null;
        }

        R r = productService.detail(productIdParam.getProductID());
        Product product = (Product) r.getData();
        return product;
    }

    @PostMapping("cart/list")
    public List<Product> cartList(@RequestBody @Validated ProductCollectParam productCollectParam, BindingResult result) {

        //参数校验
        if (result.hasErrors()) {
            return new ArrayList<Product>();
        }

        return productService.cartList(productCollectParam.getProductIds());
    }
}
