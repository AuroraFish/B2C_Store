package com.aurora.category.service;

import com.aurora.parama.PageParam;
import com.aurora.parama.ProductHotParam;
import com.aurora.pojo.Category;
import com.aurora.utils.R;

public interface CategoryService {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 10:26
     * @ param categoryName 类别名称
     * @ return
     * @ description 根据类别名称 查询类别对象
     */
    R byName(String categoryName);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 11:51
     * @ param 热门类别名称
     * @ return 热门类别id集合
     * @ description 根据传入的热门类别名称集合 返回对应热门类别id集合
     */
    R hotsCategory(ProductHotParam productHotParam);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 12:30
     * @ param none
     * @ return r 类别数据集合
     * @ description 查询类别数据 进行返回
     */
    R list();


    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:35
     * @ param 分页查询
     * @ return
     * @ description 被后台管理服务调用,分页查询
     */
    R listPage(PageParam pageParam);


    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:47
     * @ param category 添加的类别
     * @ return 状态码
     * @ description 被后台管理服务调用,添加类别信息
     */
    R adminSave(Category category);

    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 13:53
     * @ param categoryId
     * @ return 状态码
     * @ description 被后台管理服务调用,删除类别信息
     */
    R adminRemove(Integer categoryId);

    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 14:09
     * @ param category
     * @ return 状态码
     * @ description 被后台管理服务调用,修改类别信息
     */
    R adminUpdate(Category category);
}
