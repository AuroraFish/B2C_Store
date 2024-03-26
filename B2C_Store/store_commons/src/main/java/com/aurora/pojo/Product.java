package com.aurora.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 10:01
 * @ DESCRIPTION 商品实体类
 */
@TableName("product")
@Data
@AllArgsConstructor     //注解:生成有参构造
@NoArgsConstructor      //     生成无参构造
@JsonIgnoreProperties(ignoreUnknown = true) //忽略没有的属性
public class Product implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("category_id")
    private String categoryId;
    // 手机title
    @JsonProperty("product_title")
    private String productTitle;
    // 手机描述信息
    @JsonProperty("product_intro")
    private String productIntro;
    @JsonProperty("product_picture")
    private String productPicture;
    // 商品价格
    @JsonProperty("product_price")
    private Double productPrice;
    // 售卖价格
    @JsonProperty("product_selling_price")
    private Double productSellingPrice;
    // 商品库存
    @JsonProperty("product_num")
    private int productNum;
    // 已卖数量
    @JsonProperty("product_sales")
    private int productSales;
}
