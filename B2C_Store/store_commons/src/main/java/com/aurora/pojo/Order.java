package com.aurora.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 16:10
 * @ DESCRIPTION 订单实体类
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@TableName("orders")
public class Order implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonProperty("order_id")
    private Long    orderId;
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("product_num")
    private Integer productNum;
    @JsonProperty("product_price")
    private Double  productPrice;
    @JsonProperty("order_time")
    private Long    orderTime;
}
