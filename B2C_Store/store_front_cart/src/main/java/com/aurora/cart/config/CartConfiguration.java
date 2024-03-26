package com.aurora.cart.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 16:43
 * @ DESCRIPTION 购物车服务配置类
 */
@Configuration
public class CartConfiguration {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 16:44
     * @ param
     * @ return
     * @ description mq序列化方式,配置为JSON
     */
    @Bean
    public MessageConverter messageConverter(){

        return new Jackson2JsonMessageConverter();
    }
}
