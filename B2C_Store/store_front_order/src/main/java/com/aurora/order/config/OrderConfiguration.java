package com.aurora.order.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 15:56
 * @ DESCRIPTION 订单配置类
 */
@Configuration
public class OrderConfiguration {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 15:58
     * @ param
     * @ return
     * @ description mq序列化方式,配置为JSON
     */
    @Bean
    public MessageConverter messageConverter(){

        return new Jackson2JsonMessageConverter();
    }
}

