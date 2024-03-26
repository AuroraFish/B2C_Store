package com.aurora.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 15:02
 * @ DESCRIPTION 配置类
 */
@Configuration      //配置类固定注解 有该注解 @Bean才会生效
public class SearchConfiguration {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 15:15
     * @ description MQ序列化方式，配置为JSON
     */
    @Bean           //将方法的返回值加入ioc容器 外部才可以通过注入方法使用
    public MessageConverter messageConverter(){

        return new Jackson2JsonMessageConverter();
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 15:16
     * @ description es客户端添加到ioc容器 将方法的返回值加入ioc容器 外部才可以通过注入方法使用
     */
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client =
                new RestHighLevelClient(
                        RestClient.builder(HttpHost.create("43.136.135.58:9200")));

        return client;
    }
}
