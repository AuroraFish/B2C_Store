package com.aurora.user.controller;

import com.aurora.parama.UserCheckParam;
import com.aurora.parama.UserLoginParam;
import com.aurora.pojo.User;
import com.aurora.user.service.UserService;
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
 * @ date 2024/3/20 14:32
 * @ DESCRIPTION 用户模块的控制器类
 * @ RestController 代表返回的是JSON数据
 * @ RequestBody 接收JSON数据
 * @ Validated 加上校验注解
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userservice;    //业务接口
    
    @PostMapping("check")
    /**
     * @author AuroraCjt
     * @date 2024/3/20 14:51
     * @param userCheckParam 接收检查的账号实体 内部有参数校验注解
     * @param result 获取校验结果的实体
     * @return com.aurora.utils.R 返回封装结果R对象
     * @description 检查
     */
    public R check(@RequestBody @Validated UserCheckParam userCheckParam, BindingResult result) {

        // 检查是否符合检验注解的规则 符合 false 不符合 true
        boolean b = result.hasErrors();

        if (b) {

            return R.fail("账号为空, 不可使用!");
        }

        return userservice.check(userCheckParam);
    }
    
    
    @PostMapping("register")
    /**
     * @author AuroraCjt
     * @date 2024/3/20 15:44
     * @param user 注册的账号实体
     * @param result 结果 001 , 004
     * @return com.aurora.utils.R
     * @description 注册
     */
    public R register(@RequestBody @Validated User user, BindingResult result) {

        if (result.hasErrors()) {
            //如果存在异常, 证明请求参数不符合注解要求
            return R.fail("参数异常, 不可注册!");
        }

        return userservice.register(user);
    }

    @PostMapping("login")
    /**
     * @author AuroraCjt
     * @date 2024/3/20 15:54
     * @param userLoginParam
     * @param result
     * @return com.aurora.utils.R
     * @description 登录
     */
    public R login(@RequestBody @Validated UserLoginParam userLoginParam, BindingResult result) {

        //参数校验
        if (result.hasErrors()) {
            //如果存在异常, 证明请求参数不符合校验注解要求
            return R.fail("参数异常, 登录失败!");
        }

        return userservice.login(userLoginParam);
    }
}
