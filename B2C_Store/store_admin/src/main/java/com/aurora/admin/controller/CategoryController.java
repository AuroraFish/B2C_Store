package com.aurora.admin.controller;

import com.aurora.admin.service.CategoryService;
import com.aurora.parama.PageParam;
import com.aurora.pojo.Category;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ author AuroraCjt
 * @ date 2024/3/25 12:40
 * @ DESCRIPTION 后台管理 类别模块 的控制器类
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public R pageList(PageParam pageParam) {

        return categoryService.pageList(pageParam);
    }

    @PostMapping("save")
    public R save(Category category) {

        return categoryService.save(category);
    }

    @PostMapping("remove")
    public R remove(Integer categoryId) {

        return categoryService.remove(categoryId);
    }

    @PostMapping("update")
    public R update(Category category) {

        return categoryService.update(category);
    }
}
