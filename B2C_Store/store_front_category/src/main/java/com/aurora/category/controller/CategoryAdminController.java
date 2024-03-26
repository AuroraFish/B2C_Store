package com.aurora.category.controller;

import com.aurora.category.service.CategoryService;
import com.aurora.parama.PageParam;
import com.aurora.pojo.Category;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ author AuroraCjt
 * @ date 2024/3/25 12:33
 * @ DESCRIPTION 被后台管理服务调用的控制器类
 */
@RestController
@RequestMapping("category")
public class CategoryAdminController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("admin/list")
    public R listPage(@RequestBody PageParam pageParam) {

        return categoryService.listPage(pageParam);
    }

    @PostMapping("admin/save")
    public R adminSave(@RequestBody Category category) {

        return categoryService.adminSave(category);
    }

    @PostMapping("admin/remove")
    public R adminRemove(@RequestBody Integer categoryId) {

        return categoryService.adminRemove(categoryId);
    }

    @PostMapping("admin/update")
    public R adminUpdate(@RequestBody Category category) {

        return categoryService.adminUpdate(category);
    }
}
