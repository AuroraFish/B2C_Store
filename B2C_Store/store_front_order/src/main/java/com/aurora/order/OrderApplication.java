package com.aurora.order;

import com.aurora.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.swing.text.html.HTMLDocument;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 15:52
 * @ DESCRIPTION
 */
@MapperScan(basePackages = "com.aurora.order.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {ProductClient.class})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
