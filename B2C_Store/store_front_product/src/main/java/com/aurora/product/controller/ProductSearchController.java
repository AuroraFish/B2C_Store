package com.aurora.product.controller;

import com.aurora.pojo.Product;
import com.aurora.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 14:24
 * @ DESCRIPTION 被搜索服务调用的控制器类
 */
@RestController
@RequestMapping("product")
public class ProductSearchController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> allList() {
        return productService.allList();
    }

}
