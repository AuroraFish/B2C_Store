package com.aurora.admin.controller;

import com.aurora.admin.service.ProductService;
import com.aurora.admin.utils.AliyunOSSUtils;
import com.aurora.parama.ProductSaveParam;
import com.aurora.parama.ProductSearchParam;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @ author AuroraCjt
 * @ date 2024/3/25 14:20
 * @ DESCRIPTION 后台管理 商品模块 的控制器类
 */
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @Autowired
    private ProductService productService;

    @GetMapping("list")
    public R adminList(ProductSearchParam productSearchParam) {

        return productService.adminList(productSearchParam);
    }

    @PostMapping("upload")
    public R adminUpload(@RequestParam("img") MultipartFile img) throws Exception {

        String filename = img.getOriginalFilename();
        filename = UUID.randomUUID().toString().replaceAll("-","")+filename;

        String contentType = img.getContentType();

        byte[] content = img.getBytes();

        int hours = 1000;

        String url = aliyunOSSUtils.uploadImage(filename, content, contentType, hours);
        System.out.println("url = "+url);

        return R.ok("图片上传成功!",url);
    }

    @PostMapping("save")
    public R adminSave(ProductSaveParam productSaveParam) {

        return productService.save(productSaveParam);
    }
}
