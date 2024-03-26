package com.aurora.admin.controller;

import com.aurora.admin.service.UserService;
import com.aurora.parama.CartListParam;
import com.aurora.parama.PageParam;
import com.aurora.pojo.User;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;

/**
 * @ author AuroraCjt
 * @ date 2024/3/25 11:36
 * @ DESCRIPTION 后台管理 用户模块 的控制器类  /admin/user/list
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public R userList(PageParam pageParam) {

        return userService.userList(pageParam);
    }

    @PostMapping("remove")
    public R userRemove(CartListParam cartListParam) {

        return userService.userRemove(cartListParam);
    }

    @PostMapping("update")
    public R userUpdate(User user) {

        return userService.userUpdate(user);
    }

    @PostMapping("save")
    public R userSave(User user) {

        return userService.userSave(user);
    }
}
