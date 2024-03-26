package com.aurora.product.config;

import com.aurora.config.CacheConfiguration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 11:19
 * @ DESCRIPTION 商品服务缓存配置类, 继承自通用模块的缓存配置类
 */
@Configuration
public class ProductConfiguration extends CacheConfiguration {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 16:52
     * @ param
     * @ return
     * @ description mq序列化方式,配置为JSON
     */
    @Bean
    public MessageConverter messageConverter(){

        return new Jackson2JsonMessageConverter();
    }
}
