package com.aurora.product.service;

import com.aurora.parama.ProductHotParam;
import com.aurora.parama.ProductIdsParam;
import com.aurora.parama.ProductSaveParam;
import com.aurora.parama.ProductSearchParam;
import com.aurora.pojo.Product;
import com.aurora.to.OrderToProduct;
import com.aurora.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ProductService extends IService<Product> {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 10:54
     * @ param categoryName 类别名
     * @ return 热门商品数据
     * @ description 根据单类别名称 查询热门商品数据 至多7条
     */
    R promo(String categoryName);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 12:01
     * @ param productHotParam 类别名称集合
     * @ return 
     * @ description 多类别热门商品查询 根据类别名称集合 至多7条
     */
    R hots(ProductHotParam productHotParam);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 12:34
     * @ param none
     * @ return 类别商品集合
     * @ description 查询类别商品集合
     */
    R clist();


    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 12:50
     * @ param productIdsParam 类别ID 可以为空
     * @ return 类别
     * @ description 通用业务
     * @ note 1.传入类别ID 根据ID查询并且分页
     *        2.没有传入类别ID 查询全部
     */
    R byCategory(ProductIdsParam productIdsParam);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 13:11
     * @ param productID 商品ID
     * @ return 商品详情信息
     * @ description 根据商品ID查询商品详情信息
     */
    R detail(Integer productID);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 13:49
     * @ param productID 商品ID
     * @ return 图片详情集合
     * @ description 查询商品对应的图片详情集合
     */
    R pictures(Integer productID);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 14:26
     * @ param none
     * @ return 商品数据集合
     * @ description 被搜索服务调用,获取全部商品数据,进行同步
     */
    List<Product> allList();

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 16:48
     * @ param productSearchParam
     * @ return r
     * @ description 搜索业务 需要调用搜索服务(feign)
     */
    R search(ProductSearchParam productSearchParam);

    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 13:21
     * @ param productIds 商品ID集合
     * @ return 商品信息
     * @ description 被收藏服务调用,根据商品ID集合,查询商品信息
     */
    R ids(List<Integer> productIds);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 15:05
     * @ param productIds 商品ID集合
     * @ return 商品数据集合
     * @ description 根据商品ID集合, 查询商品数据集合
     */
    List<Product> cartList(List<Integer> productIds);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 16:57
     * @ param orderToProducts {product_id:商品ID,num:需减少库存数}
     * @ return none
     * @ description 修改对应商品库存以及增加销售量
     */
    void subNumber(List<OrderToProduct> orderToProducts);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 13:48
     * @ param categoryId 类别ID
     * @ return 商品数量
     * @ description 被后台管理服务调用 类别对应的商品数量查询
     */
    Long adminCount(Integer categoryId);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 15:21
     * @ param productSaveParam
     * @ return
     * @ description 被后台管理服务调用 保存商品
     */
    R adminSave(ProductSaveParam productSaveParam);
}
