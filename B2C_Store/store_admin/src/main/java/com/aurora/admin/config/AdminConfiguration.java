package com.aurora.admin.config;

import com.aurora.config.CacheConfiguration;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ author AuroraCjt
 * @ date 2024/3/24 12:08
 * @ DESCRIPTION 配置类
 * @ note 1.继承缓存配置
 *        2.MQ消息队列配置
 *        3.MYBATIS分页插件
 */
@Configuration
public class AdminConfiguration extends CacheConfiguration {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:11
     * @ description MQ序列化方式, 配置为JSON
     */
    @Bean
    public MessageConverter messageConverter() {

        return new Jackson2JsonMessageConverter();
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:12
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
