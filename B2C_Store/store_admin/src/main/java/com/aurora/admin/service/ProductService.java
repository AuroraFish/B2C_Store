package com.aurora.admin.service;

import com.aurora.parama.ProductSaveParam;
import com.aurora.parama.ProductSearchParam;
import com.aurora.utils.R;

/**
* @ author AuroraCjt
* @ date   2024/3/25 14:24
* @ DESCRIPTION 商品业务接口
*/
public interface ProductService {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 14:24
     * @ param productSearchParam {关键字,currentPage,pageSize}
     * @ return 商品
     * @ description 后台管理 全部商品查询以及搜索查询业务
     */
    R adminList(ProductSearchParam productSearchParam);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 15:51
     * @ param productSaveParam 商品保存数据
     * @ return 状态码
     * @ description 进行商品数据保存
     */
    R save(ProductSaveParam productSaveParam);
}
