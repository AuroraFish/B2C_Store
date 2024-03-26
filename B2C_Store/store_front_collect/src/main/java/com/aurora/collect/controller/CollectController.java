package com.aurora.collect.controller;

import com.aurora.collect.service.CollectService;
import com.aurora.pojo.Collect;
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
 * @ date 2024/3/23 12:50
 * @ DESCRIPTION 收藏服务的控制器类
 */
@RestController
@RequestMapping("collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @PostMapping("save")
    public R save(@RequestBody Collect collect) {
        return collectService.save(collect);
    }

    @PostMapping("list")
    public R list(@RequestBody Collect collect) {

        return collectService.list(collect.getUserId());
    }

    @PostMapping("remove")
    public R remove(@RequestBody Collect collect) {

        return collectService.remove(collect);
    }
}
