package com.aurora.cart.service.impl;

import com.aurora.cart.mapper.CartMapper;
import com.aurora.cart.service.CartService;
import com.aurora.clients.ProductClient;
import com.aurora.parama.CartSaveParam;
import com.aurora.parama.ProductCollectParam;
import com.aurora.parama.ProductIdParam;
import com.aurora.pojo.Cart;
import com.aurora.pojo.Product;
import com.aurora.utils.R;
import com.aurora.vo.CartVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 14:24
 * @ DESCRIPTION
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartMapper cartMapper;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 14:24
     * @ param cartService {user_id, product_id}
     * @ return 001 成功 , 002 已经存在 , 003 没有库存
     * @ description 购物车数据添加方法
     * @ note 1.查询商品数据
     *        2.检查库存
     *        3.检查是否添加过
     *        4.添加购物车
     *        5.结果封装返回
     */
    @Override
    public R save(CartSaveParam cartSaveParam) {

        //1.查询商品数据
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cartSaveParam.getProductId());
        Product product = productClient.productDetail(productIdParam);

        if (product == null) {
            return R.fail("商品已经被删除, 无法添加到购物车!");
        }

        //2.检查库存
        if (product.getProductNum() == 0) {
            R ok = R.ok("没有库存数据! 无法购买");
            ok.setCode("003");
            log.info("CartServiceImpl.save业务结束, 结果{}",ok);
            return ok;
        }

        //3.检查是否添加过
        //查询条件
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartSaveParam.getUserId());
        queryWrapper.eq("product_id",cartSaveParam.getProductId());
        //数据库操作
        Cart cart = cartMapper.selectOne(queryWrapper);

        if (cart != null) {
            //购物车存在, 数量+1, 再次添加
            cart.setNum(cart.getNum()+1);
            cartMapper.updateById(cart);

            //返回002
            R ok = R.ok("购物车存在该商品, 数量+1");
            ok.setCode("002");
            log.info("CartServiceImpl.save业务结束, 结果{}",ok);
            return ok;
        }

        //4.添加购物车
        cart = new Cart();
        cart.setNum(1);
        cart.setUserId(cartSaveParam.getUserId());
        cart.setProductId(cartSaveParam.getProductId());
        int rows = cartMapper.insert(cart);
        log.info("CartServiceImpl.save业务结束, 结果{}",rows);

        //5.结果封装 创建1个VO对象返回
        CartVo cartVo = new CartVo(product, cart);
        return R.ok("购物车数据添加成功", cartVo);
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 15:14
     * @ param userId 用户ID
     * @ return 确保返回一个数组
     * @ description 返回购物车数据
     * @ note 1.根据用户ID, 查询购物车数据
     *        2.判断是否存在, 不存在返回一个空集合
     *        3.存在, 获取商品ID集合, 调用商品服务查询商品信息集合
     *        4.结果处理, 进行CartVo的封装
     */
    @Override
    public R list(Integer userId) {

        //1.根据用户ID, 查询购物车数据
        //查询条件
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        //数据库操作
        List<Cart> carts = cartMapper.selectList(queryWrapper);

        //2.判断是否存在, 不存在返回一个空集合
        if (carts == null || carts.size() == 0) {
            carts = new ArrayList<>();  //必须返回空数据
            return R.ok("购物车为空!",carts);
        }

        //3.存在, 获取商品ID集合, 调用商品服务查询商品数据集合
        List<Integer> productIds = new ArrayList<>();
        for (Cart cart : carts) {
            productIds.add(cart.getProductId());
        }

        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);

        //productList 转化为 map(key=productId, value=Product) 方便后续封装
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //4.结果处理, 进行CartVo的封装
        List<CartVo> cartVoList = new ArrayList<>();

        for (Cart cart : carts) {
            CartVo cartVo = new CartVo(productMap.get(cart.getProductId()), cart);
            cartVoList.add(cartVo);
        }
        R r = R.ok("数据库数据查询成功!", cartVoList);
        log.info("CartServiceImpl.list业务结束, 结果{}",r);
        return r;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 15:32
     * @ param cart
     * @ return r
     * @ description 更新购物车业务
     * @ note 1.查询商品数据
     *        2.判断库存是否可用
     *        3.正常修改
     */
    @Override
    public R update(Cart cart) {

        //1.查询商品数据
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cart.getProductId());
        //调用商品服务 根据商品ID查询商品数据
        Product product = productClient.productDetail(productIdParam);

        //2.判断库存
        if (cart.getNum() > product.getProductNum()) {
            log.info("CartServiceImpl.update业务结束, 结果{}", "修改失败, 商品库存不足!");
            return R.fail("修改失败, 商品库存不足!");
        }

        //3.修改
        //查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",cart.getUserId());
        queryWrapper.eq("product_id",cart.getProductId());
        //数据库操作
        Cart cart1 = cartMapper.selectOne(queryWrapper);

        //更新为新值
        cart1.setNum(cart.getNum());
        //插入数据库
        int rows = cartMapper.updateById(cart1);
        log.info("CartServiceImpl.update业务结束, 结果{}",rows);
        return R.ok("修改成功, 购物车数量更新!");
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 15:39
     * @ param cart
     * @ return r
     * @ description 删除购物车业务
     */
    @Override
    public R remove(Cart cart) {

        //查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",cart.getUserId());
        queryWrapper.eq("product_id",cart.getProductId());

        //数据库操作
        int rows = cartMapper.delete(queryWrapper);
        log.info("CartServiceImpl.remove业务结束, 结果{}",rows);
        return R.ok("删除购物车数据成功!");
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 16:50
     * @ param cartIds 购物车ID集合
     * @ return none
     * @ description 清空对应ID的购物车项
     */
    @Override
    public void clearIds(List<Integer> cartIds) {

        cartMapper.deleteBatchIds(cartIds);
        log.info("CartServiceImpl.clearIds业务结束, 结果{}",cartIds);
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/26 15:45
     * @ param productId
     * @ return 状态码
     * @ description 被后台管理服务调用 查询购物车项
     */
    @Override
    public R check(Integer productId) {

        //查询条件
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",productId);

        //数据库操作
        Long count = cartMapper.selectCount(queryWrapper);

        if (count > 0) {
            //购物车中有需要删除的商品 不允许删除
            return R.fail("有: "+count+" 件购物车商品引用! 删除失败!");
        }

        return R.ok("购物车无商品引用!");
    }
}
