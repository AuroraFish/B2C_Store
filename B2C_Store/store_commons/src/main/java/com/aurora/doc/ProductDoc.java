package com.aurora.doc;

import com.aurora.pojo.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 14:46
 * @ DESCRIPTION ES数据库中存储商品搜索数据的实体类
 */
@Data
@NoArgsConstructor      //避免序列化时出现问题
public class ProductDoc extends Product {

    //商品名称, 商品标题, 商品描述的综合
    private String all;

    public ProductDoc(Product product) {
        //super:调用父类的构造函数
        super(product.getProductId(),product.getProductName(),product.getCategoryId(),
                product.getProductTitle(),product.getProductIntro(),product.getProductPicture(),
                product.getProductPrice(),product.getProductSellingPrice(),product.getProductNum(),
                product.getProductSales());

        this.all = product.getProductName() + product.getProductIntro() + product.getProductTitle();
    }
}
