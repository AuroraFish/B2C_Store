package com.aurora.user.controller;

import com.aurora.parama.CartListParam;
import com.aurora.parama.PageParam;
import com.aurora.pojo.User;
import com.aurora.user.service.UserService;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ author AuroraCjt
 * @ date 2024/3/25 11:20
 * @ DESCRIPTION 被后台管理服务调用的控制器类
 */
@RestController
@RequestMapping("user")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @PostMapping("admin/list")
    public R listPage(@RequestBody PageParam pageParam) {

        return userService.listPage(pageParam);
    }

    @PostMapping("admin/remove")
    public R remove(@RequestBody CartListParam cartListParam) {

        return userService.remove(cartListParam.getUserId());
    }

    @PostMapping("admin/update")
    public R update(@RequestBody User user) {

        return userService.update(user);
    }

    @PostMapping("admin/save")
    public R save(@RequestBody User user) {

        return userService.save(user);
    }
}
