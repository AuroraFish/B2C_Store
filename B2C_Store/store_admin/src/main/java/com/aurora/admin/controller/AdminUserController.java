package com.aurora.admin.controller;

import com.aurora.admin.param.AdminUserParam;
import com.aurora.admin.pojo.AdminUser;
import com.aurora.admin.service.AdminUserService;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ author AuroraCjt
 * @ date 2024/3/24 15:04
 * @ DESCRIPTION 后台管理服务 登录模块 控制器类
 */
@RestController
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/user/login")
    public R login(@Validated AdminUserParam adminUserParam, BindingResult result, HttpSession session) {

        //参数校验
        if (result.hasErrors()) {
            return R.fail("参数异常, 登录失败");
        }

        //验证码校验 验证码存于session类
        String captcha = (String)session.getAttribute("captcha");

        if (!adminUserParam.getVerCode().equalsIgnoreCase(captcha)) {
            return R.fail("验证码错误");
        }

        AdminUser user = adminUserService.login(adminUserParam);

        if (user == null) {
            return R.fail("登录失败! 账号密码错误!");
        }

        //key = userInfo 前端页面所规定
        session.setAttribute("userInfo",user);

        return R.ok("登录成功!");
    }

    @GetMapping("user/logout")
    public R logout(HttpSession session) {

        //清空session
        session.invalidate();

        return R.ok("退出登录成功!");
    }
}
