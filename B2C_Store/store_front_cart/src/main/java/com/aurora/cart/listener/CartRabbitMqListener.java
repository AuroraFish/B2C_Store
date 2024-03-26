package com.aurora.cart.listener;

import com.aurora.cart.service.CartService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 16:45
 * @ DESCRIPTION 监听MQ消息
 */
@Component
public class CartRabbitMqListener {

    @Autowired
    private CartService cartService;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 16:47
     * @ param cartIds 购物车ID集合
     * @ return none
     * @ description 监听 删除cart表中对应购物车ID项
     */
    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue(name = "clear.queue"),
                            exchange = @Exchange("topic.ex"),
                            key = "clear.cart"
                    )
            }
    )
    public void clear(List<Integer> cartIds) {
        cartService.clearIds(cartIds);
    }
}
