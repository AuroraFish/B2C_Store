package com.aurora.cart;

import com.aurora.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 13:55
 * @ DESCRIPTION 购物车服务启动类
 */
@MapperScan(basePackages = "com.aurora.cart.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {ProductClient.class})
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class,args);
    }
}
