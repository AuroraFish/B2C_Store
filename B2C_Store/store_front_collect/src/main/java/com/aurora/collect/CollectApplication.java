package com.aurora.collect;

import com.aurora.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 12:40
 * @ DESCRIPTION 收藏服务启动类
 */
@MapperScan(basePackages = "com.aurora.collect.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {ProductClient.class})
public class CollectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollectApplication.class,args);
    }
}
