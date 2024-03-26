package com.aurora.admin.controller;

import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ author AuroraCjt
 * @ date 2024/3/24 14:52
 * @ DESCRIPTION 后台管理服务 验证码 控制器类
 */
@Controller
@RequestMapping
public class CaptchaController {

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //自动生成验证码图片写回
        //并且将验证码图片存储到session key = captcha 默认4字母
        CaptchaUtil.out(request,response);
    }
}
