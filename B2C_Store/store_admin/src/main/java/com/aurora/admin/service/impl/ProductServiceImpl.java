package com.aurora.admin.service.impl;

import com.aurora.admin.service.ProductService;
import com.aurora.clients.ProductClient;
import com.aurora.clients.SearchClient;
import com.aurora.parama.ProductSaveParam;
import com.aurora.parama.ProductSearchParam;
import com.aurora.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ author AuroraCjt
 * @ date 2024/3/25 14:23
 * @ DESCRIPTION 商品服务实现类
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SearchClient searchClient;

    @Autowired
    private ProductClient productClient;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 14:24
     * @ param productSearchParam {关键字,currentPage,pageSize}
     * @ return 商品
     * @ description 后台管理 全部商品查询以及搜索查询业务
     */
    @Override
    public R adminList(ProductSearchParam productSearchParam) {

        R r = searchClient.search(productSearchParam);
        log.info("ProductServiceImpl.adminList业务结束, 结果{}",r);
        return r;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 15:51
     * @ param productSaveParam 商品保存数据
     * @ return 状态码
     * @ description 进行商品数据保存
     */
    @Override
    public R save(ProductSaveParam productSaveParam) {

        R r = productClient.adminSave(productSaveParam);
        log.info("ProductServiceImpl.save业务结束, 结果{}",r);
        return r;
    }
}
