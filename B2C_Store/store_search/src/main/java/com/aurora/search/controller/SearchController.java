package com.aurora.search.controller;

import com.aurora.parama.ProductSearchParam;
import com.aurora.pojo.Product;
import com.aurora.search.service.SearchSecrvice;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 16:00
 * @ DESCRIPTION 搜索服务控制器类
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchSecrvice searchService;

    @PostMapping("product")
    public R searchProduct(@RequestBody ProductSearchParam productSearchParam) {

        return searchService.search(productSearchParam);
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 15:36
     * @ return 
     * @ description 被后台管理添加商品调用,同步调用MQ消息队列, 通知ES数据库进行更新
     */
    @PostMapping("save")
    public R saveProduct(@RequestBody Product product) throws IOException {

        return searchService.save(product);
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 15:41
     * @ return
     * @ description 被后台管理添加商品调用,同步调用MQ消息队列, 通知ES数据库进行更新
     */
    @PostMapping("remove")
    public R removeProduct(@RequestBody Integer productId) throws IOException {

        return searchService.remove(productId);
    }
}
