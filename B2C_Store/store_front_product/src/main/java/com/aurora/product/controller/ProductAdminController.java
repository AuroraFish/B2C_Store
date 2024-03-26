package com.aurora.product.controller;

import com.aurora.parama.ProductSaveParam;
import com.aurora.product.service.ProductService;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ author AuroraCjt
 * @ date 2024/3/25 13:47
 * @ DESCRIPTION 被后台管理服务调用的控制器类
 */
@RestController
@RequestMapping("product")
public class ProductAdminController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/count")
    public Long adminCount(@RequestBody Integer categoryId) {

        return productService.adminCount(categoryId);
    }

    @PostMapping("/admin/save")
    public R adminSave(@RequestBody ProductSaveParam productSaveParam) {

        return productService.adminSave(productSaveParam);
    }
}
