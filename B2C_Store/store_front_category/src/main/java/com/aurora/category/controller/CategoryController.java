package com.aurora.category.controller;

import com.aurora.category.service.CategoryService;
import com.aurora.parama.ProductHotParam;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 10:22
 * @ DESCRIPTION 类别控制器类
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/promo/{categoryName}")
    public R byName(@PathVariable String categoryName) {

        if (StringUtils.isEmpty(categoryName)) {
            return R.fail("类别名为空, 无法查询数据!");
        }

        return categoryService.byName(categoryName);
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 11:49
     * @ param productHotParam 类别名
     * @ return 热门类别id集合
     * @ description 热门类别id查询
     */
    @PostMapping("hots")
    public R hotsCategory(@RequestBody @Validated ProductHotParam productHotParam, BindingResult result) {

        if (result.hasErrors()) {
            return R.fail("参数异常, 类别集合查询失败");
        }

        return categoryService.hotsCategory(productHotParam);
    }

    @GetMapping("list")
    public R list() {

        return categoryService.list();
    }
}
