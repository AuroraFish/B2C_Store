package com.aurora.cart.service;

import com.aurora.parama.CartSaveParam;
import com.aurora.pojo.Cart;
import com.aurora.utils.R;

import java.util.List;

public interface CartService {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 14:24
     * @ param cartService {user_id, product_id}
     * @ return 001 成功 , 002 已经存在 , 003 没有库存
     * @ description 购物车数据添加方法
     */
    R save(CartSaveParam cartSaveParam);


    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 15:14
     * @ param userId 用户ID
     * @ return 确保返回一个数组
     * @ description 返回购物车数据
     */
    R list(Integer userId);

    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 15:32
     * @ param cart
     * @ return r
     * @ description 更新购物车业务
     */
    R update(Cart cart);


    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 15:39
     * @ param cart
     * @ return r
     * @ description 删除购物车业务
     */
    R remove(Cart cart);

    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 16:50
     * @ param cartIds 购物车ID集合
     * @ return none
     * @ description 清空对应ID的购物车项
     */
    void clearIds(List<Integer> cartIds);

    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/26 15:45
     * @ param productId
     * @ return 状态码
     * @ description 被后台管理服务调用 查询购物车项
     */
    R check(Integer productId);
}
