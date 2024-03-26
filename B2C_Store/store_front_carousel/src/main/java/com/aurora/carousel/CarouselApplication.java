package com.aurora.carousel;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @ author AuroraCjt
 * @ date 2024/3/21 15:42
 * @ DESCRIPTION 轮播图服务启动类
 */
@MapperScan("com.aurora.carousel.mapper")
@SpringBootApplication
@EnableCaching
public class CarouselApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarouselApplication.class,args);
    }
}
