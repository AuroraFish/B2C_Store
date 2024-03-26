package com.aurora.order.service.impl;

import com.aurora.clients.ProductClient;
import com.aurora.order.mapper.OrderMapper;
import com.aurora.order.service.OrderService;
import com.aurora.parama.OrderParam;
import com.aurora.parama.ProductCollectParam;
import com.aurora.pojo.Order;
import com.aurora.pojo.Product;
import com.aurora.to.OrderToProduct;
import com.aurora.utils.R;
import com.aurora.vo.CartVo;
import com.aurora.vo.OrderVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 16:16
 * @ DESCRIPTION 业务实现
 *
 * @ note 1.继承 extends ServiceImpl<OrderMapper, Order> MYBATIS提供的数据库批量操作方法
 *        2.可以不用再以@Autowired注入Mapper操作数据库, 可以直接使用BaseMapper操作数据库
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductClient productClient;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 16:21
     * @ param orderParam
     * @ return r
     * @ description 进行订单数据保存业务
     * @ note 1.将购物车数据转换成订单数据
     *        2.进行订单数据的批量插入
     *        3.发送商品库存修改消息
     *        4.发送购物车库存修改信息
     */
    @Transactional
    @Override
    public R save(OrderParam orderParam) {

        //要删除的购物车ID
        List<Integer> cartIds = new ArrayList<>();
        //要修改的商品 {product_id:商品ID,num:修改库存数}
        List<OrderToProduct> orderToProducts = new ArrayList<>();
        //订单信息
        List<Order> orderList = new ArrayList<>();

        //生成数据
        Integer userId = orderParam.getUserId();
        //orderId由当前时间生成
        long orderId = System.currentTimeMillis();

        for (CartVo cartVo : orderParam.getProducts()) {
            //保存要删除的购物车ID
            cartIds.add(cartVo.getId());

            //商品库存修改信息
            OrderToProduct orderToProduct = new OrderToProduct();
            orderToProduct.setNum(cartVo.getNum());
            orderToProduct.setProductId(cartVo.getProductID());
            orderToProducts.add(orderToProduct);

            //订单信息
            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderTime(orderId);
            order.setUserId(userId);
            order.setProductId(cartVo.getProductID());
            order.setProductNum(cartVo.getNum());
            order.setProductPrice(cartVo.getPrice());
            orderList.add(order);
        }

        //2.订单数据批量保存
        saveBatch(orderList);

        //3.发送购物车消息(购物车对应项删除)
        rabbitTemplate.convertAndSend("topic.ex","clear.cart",cartIds);

        //4.发送商品消息(对应商品库存减少)
        rabbitTemplate.convertAndSend("topic.ex","sub.number",orderToProducts);

        R ok = R.ok("订单生成成功!");
        log.info("OrderServiceImpl.save业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 17:18
     * @ param userId 用户ID
     * @ return 订单数据
     * @ description 分组查询订单数据
     * @ note 1.查询用户对应的全部订单项
     *        2.利用stream进行订单分组 orderId
     *        3.查询订单的全部商品集合, 组成map
     *        4.封装返回的orderVo对象
     * @ note 返回结果 层次比较繁琐, 由不同订单分组组成的集合 , 需仔细理清关系
     */
    @Override
    public R list(Integer userId) {

        //1.查询全部订单项
        //查询条件
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        //数据库操作
        List<Order> orderList = list(queryWrapper);

        //2.分组 以orderId
        Map<Long, List<Order>> orderMap = orderList.stream().collect(Collectors.groupingBy(Order::getOrderId));

        //3.查询商品数据
        //以STREAM流获取 商品ID集合
        List<Integer> productIds = orderList.stream().map(Order::getProductId).collect(Collectors.toList());

        //根据stream流获得的商品ID集合 构造ProductCollectParam参数 供商品服务调用
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        //商品服务调用 根据商品ID集合获取商品集合
        List<Product> productList = productClient.cartList(productCollectParam);

        //转会成map
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //4.结果封装 内层List:订单ID相同(也就是同一时刻下单的商品)的商品订单 , 外层:不同订单ID商品集合的集合
        List<List<OrderVo>> result = new ArrayList<>();

        //遍历订单项集合
        for (List<Order> orders : orderMap.values()) {
            //封装每一个订单
            List<OrderVo> orderVos = new ArrayList<>();

            for (Order order : orders) {
                OrderVo orderVo = new OrderVo();

                //因为存在继承关系 可以简化赋值
                BeanUtils.copyProperties(order, orderVo);

                //父类中没有的成员 由我们手动赋值
                Product product = productMap.get(order.getProductId());
                orderVo.setProductName(product.getProductName());
                orderVo.setProductPicture(product.getProductPicture());

                orderVos.add(orderVo);
            }
            result.add(orderVos);
        }

        R ok = R.ok("订单数据获取成功!", result);
        log.info("OrderServiceImpl.list业务结束, 结果{}",ok);
        return ok;
    }
}
