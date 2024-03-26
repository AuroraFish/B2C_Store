package com.aurora.admin.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author AuroraCjt
 * @ date 2024/3/24 15:41
 * @ DESCRIPTION 登录保护拦截器
 * @ note 进来的 都需要拦截 检查session中是否有数据, 有则不拦截, 没有跳转登录页面
 */
@Component
public class LoginProtectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object userInfo = request.getSession().getAttribute("userInfo");

        if (userInfo != null) {
            //已登录, session中有数据
            return true;
        }

        //重定向到登陆页面 getContextPath{获取项目根路径}
        response.sendRedirect(request.getContextPath()+"/index.html");
        return false;
    }
}
