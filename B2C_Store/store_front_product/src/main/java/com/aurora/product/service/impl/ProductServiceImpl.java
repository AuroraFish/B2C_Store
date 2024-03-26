package com.aurora.product.service.impl;

import com.aurora.clients.CategoryClient;
import com.aurora.clients.SearchClient;
import com.aurora.parama.ProductHotParam;
import com.aurora.parama.ProductIdsParam;
import com.aurora.parama.ProductSaveParam;
import com.aurora.parama.ProductSearchParam;
import com.aurora.pojo.Category;
import com.aurora.pojo.Picture;
import com.aurora.pojo.Product;
import com.aurora.product.mapper.PictureMapper;
import com.aurora.product.mapper.ProductMapper;
import com.aurora.product.service.ProductService;
import com.aurora.to.OrderToProduct;
import com.aurora.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 10:55
 * @ DESCRIPTION 商品服务业务类
 * @ note 继承 extends ServiceImpl<ProductMapper,Product> MYBATIS提供数据库的批量操作 供subNumber使用
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements ProductService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private SearchClient searchClient;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 10:54
     * @ param categoryName 类别名
     * @ return 热门商品数据
     * @ description 根据单类别名称 查询热门商品数据 至多7条
     * @ note  1.根据类别名称 调用feign客户端访问类别服务获取类别数据
     *         2.成功 根据类别id查询商品数据 [热门 销售量倒序 查询7条]
     *         3.结果封装
     *
     * @ note  1.@Cacheable 注解 : cacheManager 配置缓存时间 , 自缓存配置类中选择配置时间
     *         2.value : 缓存分区 , key : 缓存key
     */
    @Cacheable(value = "list.product",key = "#categoryName", cacheManager = "cacheManagerDay")
    @Override
    public R promo(String categoryName) {

        R r = categoryClient.byName(categoryName);

        if (r.getCode().equals(R.FAIL_CODE)) {
            log.info("ProductServiceImpl.promo业务结束，结果:{}","类别查询失败!");
            return r;
        }

        //数据转换过程:自CategoryServiceImpl.byName -> Feign客户端 -> ProductServiceImpl.promo
        //data{Category} -> feign{json} -> promo{LinkedHashMap}

        LinkedHashMap<String,Object>  map = (LinkedHashMap<String, Object>) r.getData();

        Integer categoryId = (Integer) map.get("category_id");
        //封装查询参数
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId);
        queryWrapper.orderByDesc("product_sales");

        IPage<Product> page = new Page<>(1,7);

        //返回的是包装数据! 内部有对应的商品集合,也有分页的参数 例如: 总条数 总页数等等
        page = productMapper.selectPage(page, queryWrapper);

        List<Product> productList = page.getRecords(); //指定页的数据
        long total = page.getTotal(); //获取总条数

        log.info("ProductServiceImpl.promo业务结束，结果:{}",productList);

        return R.ok("数据查询成功",productList);
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 12:01
     * @ param productHotParam 类别名称集合
     * @ return r
     * @ description 多类别热门商品查询 根据类别名称集合 至多7条
     * @ note 1.调用类别服务
     *        2.类别集合id查询商品
     *        3.结果集合封装即可
     *
     * @ note 1.@Cacheable 注解 : value : 缓存分区 , key : 缓存key
     */
    @Cacheable(value = "list.product",key = "#productHotParam.categoryName")
    @Override
    public R hots(ProductHotParam productHotParam) {

        R r = categoryClient.hots(productHotParam);

        if (r.getCode().equals(R.FAIL_CODE)) {
            log.info("ProductServiceImpl.hots业务结束, 结果{}",r.getMsg());
            return r;
        }

        List<Object> ids = (List<Object>) r.getData();

        //进行商品数据查询
        //封装查询参数
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("category_id",ids);
        queryWrapper.orderByDesc("product_sales");

        //分页查询
        IPage<Product> page = new Page<>(1,7);
        page = productMapper.selectPage(page,queryWrapper);
        List<Product> records = page.getRecords();

        R ok = R.ok("多类别热门商品查询成功!",records);
        log.info("ProductServiceImpl.hots业务结束, 结果{}",ok);
        return ok;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 12:34
     * @ param none
     * @ return 类别商品集合
     * @ description 查询类别商品集合
     */
    @Override
    public R clist() {

        R r = categoryClient.list();
        log.info("ProductServiceImpl.clist业务结束, 结果{}",r);

        return r;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 12:50
     * @ param productIdsParam 类别ID 可以为空
     * @ return 类别
     * @ description 通用业务
     * @ note 1.传入类别ID 根据ID查询并且分页
     *        2.没有传入类别ID 查询全部
     *
     * @ note 1.@Cacheable 注解 : value : 缓存分区 , key : 缓存key
     */
    @Cacheable(value = "list.product",key = "#productIdsParam.categoryID+'-'+#productIdsParam.currentPage+'-'+#productIdsParam.pageSize")
    @Override
    public R byCategory(ProductIdsParam productIdsParam) {

        List<Integer> categoryID = productIdsParam.getCategoryID();

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if (!categoryID.isEmpty()) {
            queryWrapper.in("category_id", categoryID);
        }

        //分页查询
        IPage<Product> page = new Page<>(productIdsParam.getCurrentPage(), productIdsParam.getPageSize());
        page = productMapper.selectPage(page, queryWrapper);


        //结果封装
        R ok = R.ok("查询成功!", page.getRecords(), page.getTotal());
        log.info("ProductServiceImpl.byCategory业务结束, 结果{}", ok);
        return ok;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 13:11
     * @ param productID 商品ID
     * @ return 商品详情信息
     * @ description 根据商品ID查询商品详情信息
     *
     * @ note  1.@Cacheable 注解 : value : 缓存分区 , key : 缓存key
     */
    @Cacheable(value = "product",key = "#productID")
    @Override
    public R detail(Integer productID) {

        //数据库查询
        Product product = productMapper.selectById(productID);

        //结果封装
        R ok = R.ok(product);
        log.info("ProductServiceImpl.detail业务结束, 结果:{}",ok);
        return ok;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 13:49
     * @ param productID 商品ID
     * @ return 图片详情集合
     * @ description 查询商品对应的图片详情集合
     * @ note  1.@Cacheable 注解 : value : 缓存分区 , key : 缓存key
     */
    @Cacheable(value = "picture", key = "#productID")
    @Override
    public R pictures(Integer productID) {

        //封装查询条件
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productID);

        //数据库查询
        List<Picture> pictureList = pictureMapper.selectList(queryWrapper);

        //结果封装
        R ok = R.ok(pictureList);
        log.info("ProductServiceImpl.picture业务结束, 结果{}",ok);
        return ok;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 14:26
     * @ param none
     * @ return 商品数据集合
     * @ description 搜索服务调用,获取全部商品数据,进行同步
     * @ note  1.@Cacheable 注解 : value : 缓存分区 , key : 缓存key
     *         2.root.methodName : key为方法名
     */
    @Cacheable(value = "list.category",key = "#root.methodName",cacheManager = "cacheManagerDay")
    @Override
    public List<Product> allList() {

        //数据库查询
        List<Product> productList = productMapper.selectList(null);

        //结果处理
        log.info("ProductServiceImpl.allList业务结束, 结果{}",productList.size());
        return productList;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 16:48
     * @ param productSearchParam
     * @ return r
     * @ description 搜索业务 需要调用搜索服务(feign)
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {

        R r = searchClient.search(productSearchParam);
        log.info("ProductServiceImpl.search业务结束, 结果{}",r);
        return r;
    }


    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 13:21
     * @ param productIds 商品ID集合
     * @ return 商品信息
     * @ description 被收藏服务调用,根据商品ID集合,查询商品信息
     * @note  1.@Cacheable 注解 : value : 缓存分区 , key : 缓存key
     *      *         2.root.methodName : key为方法名
     */
    @Cacheable(value = "list.product",key = "#productIds")
    @Override
    public R ids(List<Integer> productIds) {

        //1.查询条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id",productIds);

        //2.数据库查询
        List<Product> productList = productMapper.selectList(queryWrapper);

        //3.结果封装
        R ok = R.ok("类别信息查询成功",productList);
        log.info("ProductServiceImpl.ids业务结束, 结果{}",ok);
        return ok;
    }


    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 15:05
     * @ param productIds 商品ID集合
     * @ return 商品数据集合
     * @ description 根据商品ID集合, 查询商品数据集合
     */
    @Override
    public List<Product> cartList(List<Integer> productIds) {

        //查询条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id",productIds);

        //数据库操作
        List<Product> productList = productMapper.selectList(queryWrapper);
        log.info("ProductServiceImpl.cartList业务结束, 结果{}",productList);
        return productList;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 16:57
     * @ param orderToProducts {product_id:商品ID,num:需减少库存数}
     * @ return none
     * @ description 修改对应商品库存以及增加销售量
     */
    @Override
    public void subNumber(List<OrderToProduct> orderToProducts) {

        //1.将集合转为Map, map{key=productId,value=orderToProduct}
        Map<Integer, OrderToProduct> map = orderToProducts.stream().collect(Collectors.toMap(OrderToProduct::getProductId, v -> v));

        //2.获取商品的ID集合
        Set<Integer> productIds = map.keySet();

        //3.查询集合对应的商品信息
        List<Product> productList = productMapper.selectBatchIds(productIds);

        //4.修改商品信息
        for (Product product : productList) {
            Integer num = map.get(product.getProductId()).getNum();
            //减库存
            product.setProductNum(product.getProductNum()-num);
            //加销量
            product.setProductSales(product.getProductSales()+num);
        }

        //5.批量更新
        this.updateBatchById(productList);
        log.info("ProductServiceImpl.subNumber业务结束, 结果{}", "库存以及销售量的修改完成!");
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 13:48
     * @ param categoryId 类别ID
     * @ return 商品数量
     * @ description 被后台管理服务调用 类别对应的商品数量查询
     */
    @Override
    public Long adminCount(Integer categoryId) {

        //查询条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId);

        //数据库操作
        Long count = baseMapper.selectCount(queryWrapper);
        log.info("ProductServiceImpl.adminCount业务结束, 结果{}",count);
        return count;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 15:21
     * @ param productSaveParam
     * @ return
     * @ description 被后台管理服务调用 保存商品
     * @ note 1.商品数据保存
     *        2.商品图片详情切割保存
     *        3.ES搜索数据库的数据同步
     *        4.清空商品相关的缓存数据
     */
    @CacheEvict(value = "list.product",allEntries = true)
    @Override
    public R adminSave(ProductSaveParam productSaveParam) {

        //1.商品数据保存
        //查询条件
        Product product = new Product();
        BeanUtils.copyProperties(productSaveParam,product);

        //数据库操作
        int rows = productMapper.insert(product);
        log.info("ProductServiceImpl.adminSave业务结束，结果:{}",rows);

        //商品图片获取 urls 问题:生成的url太长了, 接近数据库存储的上限
        String pictures = productSaveParam.getPictures();
        log.info("商品图片urs集合为 {}",pictures);

        //2.商品图片切割保存
        if (!StringUtils.isEmpty(pictures)){
            //截取特殊字符串的时候 \\ [] 包含 $ + * | ?
            String[] urls = pictures.split("\\+");
            for (String url : urls) {
                Picture picture = new Picture();
                picture.setProductId(product.getProductId());
                picture.setProductPicture(url);
                log.info("每个拆分出来的url为 {}",url);
                log.info("url长度 {}", url.length());
                pictureMapper.insert(picture); //插入商品的图片
            }
        }

        //3.同步搜索服务的数据
        searchClient.saveOrUpdate(product);

        return R.ok("商品数据添加成功!");
    }
}
