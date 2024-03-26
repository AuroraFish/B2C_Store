package com.aurora.admin.service;

import com.aurora.parama.PageParam;
import com.aurora.pojo.Category;
import com.aurora.utils.R;

public interface CategoryService {
    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:42
     * @ param pageParam 分页查询
     * @ return 类别
     * @ description 后台管理 类别分页查询业务
     */
    R pageList(PageParam pageParam);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:53
     * @ param category
     * @ return 状态码
     * @ description 后台管理 类别添加业务
     */
    R save(Category category);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 14:00
     * @ param categoryId
     * @ return 状态码
     * @ description 后台管理 类别删除业务
     */
    R remove(Integer categoryId);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 14:12
     * @ param category
     * @ return 状态码
     * @ description 后台管理,类别修改业务
     */
    R update(Category category);
}
