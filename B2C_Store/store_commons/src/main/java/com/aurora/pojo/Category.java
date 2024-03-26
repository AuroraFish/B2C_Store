package com.aurora.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 10:09
 * @ DESCRIPTION
 */
@TableName("category")
@Data
public class Category implements Serializable {
    public static final Long serialVersionUID = 1L;

    @JsonProperty("category_id")
    @TableId(type = IdType.AUTO)
    private Integer categoryId;
    @JsonProperty("category_name")
    private String categoryName;
}
