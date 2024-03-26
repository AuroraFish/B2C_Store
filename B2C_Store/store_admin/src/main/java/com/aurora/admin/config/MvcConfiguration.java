package com.aurora.admin.config;

import com.aurora.admin.interceptor.LoginProtectInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ author AuroraCjt
 * @ date 2024/3/24 15:46
 * @ DESCRIPTION Spring Mvc 配置类 (登录保护拦截配置)
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 15:49
     * @ return none
     * @ description 用户注册拦截器
     * @ note addPathPatterns("/**") : 拦截所有
     *        excludePathPatterns("/","/index.html","/index","/static/**",
     *                         "/user/login", "/user/logout",
     *                         "/api/**", "/css/**", "/images/**",
     *                         "/js/**", "/lib/**","/captcha"
     *                 ) : 所有里面这些放行(登录/退出/静态资源...)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截后台管理模块的路径  排除登录和资源路径
        registry.addInterceptor(new LoginProtectInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/","/index.html","/index","/static/**",
                        "/user/login", "/user/logout",
                        "/api/**", "/css/**", "/images/**",
                        "/js/**", "/lib/**","/captcha"
                );
    }
}
