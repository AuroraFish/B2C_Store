package com.aurora.cart.controller;

import com.aurora.cart.service.CartService;
import com.aurora.parama.CartListParam;
import com.aurora.parama.CartSaveParam;
import com.aurora.pojo.Cart;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 14:18
 * @ DESCRIPTION 购物车模块控制器类
 */
@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("save")
    public R save(@RequestBody @Validated CartSaveParam cartSaveParam, BindingResult result) {

        //参数校验
        if (result.hasErrors()) {
            return R.fail("参数异常!参数为空!购物车添加失败!");
        }

        return cartService.save(cartSaveParam);
    }

    @PostMapping("list")
    public R list(@RequestBody @Validated CartListParam cartListParam, BindingResult result) {

        //参数校验
        if (result.hasErrors()) {
            return R.fail("购物车数据查询失败!");
        }

        return cartService.list(cartListParam.getUserId());
    }

    @PostMapping("update")
    public R update(@RequestBody Cart cart) {

        return cartService.update(cart);
    }

    @PostMapping("remove")
    public R remove(@RequestBody Cart cart) {

        return cartService.remove(cart);
    }
}
