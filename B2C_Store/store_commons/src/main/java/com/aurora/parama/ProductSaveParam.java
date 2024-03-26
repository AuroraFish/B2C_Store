package com.aurora.parama;

import com.aurora.pojo.Product;
import lombok.Data;

/**
 * @ author AuroraCjt
 * @ date 2024/3/25 15:19
 * @ DESCRIPTION 商品数据保存接收参数
 */
@Data
public class ProductSaveParam extends Product {

    //保存商品详情的图片地址, 图片之间使用+号拼接
    private String pictures;
}
