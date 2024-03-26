package com.aurora.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @ author: AuroraCjt
* @ date:   2024/3/20 14:05
* @ DESCRIPTION : 用户服务的启动类
*/
@MapperScan(basePackages = "com.aurora.user.mapper")
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
