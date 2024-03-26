package com.aurora.admin.service.impl;

import com.aurora.admin.service.CategoryService;
import com.aurora.clients.CategoryClient;
import com.aurora.parama.PageParam;
import com.aurora.pojo.Category;
import com.aurora.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @ author AuroraCjt
 * @ date 2024/3/25 12:42
 * @ DESCRIPTION 类别业务实现类
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryClient categoryClient;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:42
     * @ param pageParam 分页查询
     * @ return 类别
     * @ description 后台管理 类别分页查询业务
     */
    @Cacheable(value = "list.category",key = "#pageParam.currentPage+'-'+#pageParam.pageSize")
    @Override
    public R pageList(PageParam pageParam) {

        R r = categoryClient.adminPageList(pageParam);
        log.info("CategoryServiceImpl.pageList业务结束, 结果{}",r);
        return r;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:53
     * @ param category
     * @ return 状态码
     * @ description 后台管理 类别添加业务
     */
    @CacheEvict(value = "list.category",allEntries = true)
    @Override
    public R save(Category category) {

        R r = categoryClient.adminSave(category);
        log.info("CategoryServiceImpl.save业务结束, 结果{}",r);
        return r;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 14:00
     * @ param categoryId
     * @ return 状态码
     * @ description 后台管理 类别删除业务
     */
    @CacheEvict(value = "list.category",allEntries = true)
    @Override
    public R remove(Integer categoryId) {

        R r = categoryClient.adminRemove(categoryId);
        log.info("CategoryServiceImpl.remove业务结束, 结果{}",r);
        return r;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 14:12
     * @ param category
     * @ return 状态码
     * @ description 后台管理,类别修改业务
     */
    @CacheEvict(value = "list.category",allEntries = true)
    @Override
    public R update(Category category) {

        R r = categoryClient.adminUpdate(category);
        log.info("CategoryServiceImpl.update业务结束, 结果{}",r);
        return r;
    }
}
