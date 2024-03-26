package com.aurora.clients;

import com.aurora.parama.PageParam;
import com.aurora.parama.ProductHotParam;
import com.aurora.pojo.Category;
import com.aurora.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @ author AuroraCjt
* @ date   2024/3/22 10:39
* @ DESCRIPTION  类别服务调用接口
*/
@FeignClient("category-service")
public interface CategoryClient {

    @GetMapping("/category/promo/{categoryName}")
    R byName(@PathVariable String categoryName);

    @PostMapping("/category/hots")
    R hots(@RequestBody ProductHotParam productHotParam);

    @GetMapping("/category/list")
    R list();

    @PostMapping("/category/admin/list")
    R adminPageList(@RequestBody PageParam pageParam);

    @PostMapping("/category/admin/save")
    R adminSave(@RequestBody Category category);

    @PostMapping("/category/admin/remove")
    R adminRemove(@RequestBody Integer categoryId);

    @PostMapping("/category/admin/update")
    R adminUpdate(@RequestBody Category category);
}
