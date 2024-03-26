package com.aurora.product.listener;

import com.aurora.product.service.ProductService;
import com.aurora.to.OrderToProduct;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 16:53
 * @ DESCRIPTION 监听MQ消息
 */
@Component
public class ProductRabbitMqListener {

    @Autowired
    private ProductService productService;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 16:54
     * @ param orderToProducts {product_id:商品ID,num:需减少库存数}
     * @ return none
     * @ description 监听 减少对应商品ID的商品库存
     */
    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue(name = "sub.queue"),
                            exchange = @Exchange("topic.ex"),
                            key = "sub.number"
                    )
            }
    )
    public void subNumber(List<OrderToProduct> orderToProducts) {
        productService.subNumber(orderToProducts);
    }
}
