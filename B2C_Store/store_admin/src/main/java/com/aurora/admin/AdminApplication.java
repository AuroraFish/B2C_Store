package com.aurora.admin;

import com.aurora.clients.CategoryClient;
import com.aurora.clients.ProductClient;
import com.aurora.clients.SearchClient;
import com.aurora.clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ author AuroraCjt
 * @ date 2024/3/24 12:06
 * @ DESCRIPTION 后台管理模块启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "com.aurora.admin.mapper")
@EnableCaching
@EnableFeignClients(clients = {ProductClient.class,SearchClient.class,UserClient.class,CategoryClient.class})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
