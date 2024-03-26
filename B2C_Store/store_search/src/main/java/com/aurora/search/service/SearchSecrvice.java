package com.aurora.search.service;

import com.aurora.parama.ProductSearchParam;
import com.aurora.pojo.Product;
import com.aurora.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
* @ author AuroraCjt
* @ date   2024/3/22 16:06
* @ DESCRIPTION 搜索业务
*/
public interface SearchSecrvice {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 16:04
     * @ param
     * @ return
     * @ description 根据关键字和分页进行数据库数据查询
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 15:37
     * @ param product 商品
     * @ return 
     * @ description 商品同步: 插入和更新时
     */
    R save(Product product) throws IOException;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 15:42
     * @ param productId
     * @ return
     * @ description 进行ES数据库的商品删除
     */
    R remove(Integer productId) throws IOException;
}
