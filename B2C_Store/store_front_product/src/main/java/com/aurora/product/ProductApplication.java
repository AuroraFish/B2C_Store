package com.aurora.product;

import com.aurora.clients.CategoryClient;
import com.aurora.clients.SearchClient;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.swing.*;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 9:35
 * @ DESCRIPTION 商品服务启动类
 */
@MapperScan(basePackages = "com.aurora.product.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {CategoryClient.class, SearchClient.class})
@EnableCaching                  //使redis缓存配置生效
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 11:19
     * @ description 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     *               避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
