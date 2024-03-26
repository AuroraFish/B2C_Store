package com.aurora.category.service.impl;

import com.aurora.category.mapper.CategoryMapper;
import com.aurora.category.service.CategoryService;
import com.aurora.clients.ProductClient;
import com.aurora.parama.PageParam;
import com.aurora.parama.ProductHotParam;
import com.aurora.pojo.Category;
import com.aurora.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/22 10:27
 * @ DESCRIPTION 类别服务类
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 10:26
     * @ param categoryName 类别名称
     * @ return
     * @ description 根据类别名称 查询类别对象
     */
    @Override
    public R byName(String categoryName) {

        //1.封装查询参数
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("category_name", categoryName);

        //2.查询数据库
        Category category = categoryMapper.selectOne(categoryQueryWrapper);

        //3.结果处理
        if (category == null) {
            log.info("CategoryServiceImpl.byName业务结束, 结果{}","类别查询失败!");
            return R.fail("类别查询失败!");
        }

        log.info("CategoryServiceImpl.byName业务结束, 结果{}","类别查询成功!");
        return R.ok("类别查询成功!",category);
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 11:51
     * @ param 热门类别名称
     * @ return 热门类别id集合
     * @ description 根据传入的热门类别名称集合 返回对应热门类别id集合
     */
    @Override
    public R hotsCategory(ProductHotParam productHotParam) {

        //1.封装查询参数
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("category_name", productHotParam.getCategoryName());
        //只查询category_id这一列
        queryWrapper.select("category_id");

        //2.查询数据库
        List<Object> ids = categoryMapper.selectObjs(queryWrapper);

        R ok = R.ok("类别id集合查询成功",ids);
        log.info("CategoryServiceImpl.hotsCategory业务结束, 结果{}",ok);
        return ok;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/22 12:30
     * @ param none
     * @ return r 类别数据集合
     * @ description 查询类别数据 进行返回
     */
    @Override
    public R list() {

        List<Category> categoryList = categoryMapper.selectList(null);

        R ok = R.ok("类别全部数据查询成功!", categoryList);
        log.info("CategoryServiceImpl.list业务结束, 结果{}",ok);
        return ok;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:35
     * @ param 分页查询
     * @ return
     * @ description 被后台管理服务调用,分页查询
     */
    @Override
    public R listPage(PageParam pageParam) {

        //查询条件
        IPage<Category> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        //数据库操作
        page = categoryMapper.selectPage(page,null);
        return R.ok("类别分页数据查询成功!",page.getRecords(),page.getTotal());
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:47
     * @ param category 添加的类别
     * @ return 状态码
     * @ description 被后台管理服务调用,添加类别信息
     */
    @Override
    public R adminSave(Category category) {

        //查询条件
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name",category.getCategoryName());

        //数据库操作
        Long count = categoryMapper.selectCount(queryWrapper);

        if (count > 0) {
            //类别已经存在
            return R.fail("类别已经存在,添加失败!");
        }

        int insert = categoryMapper.insert(category);
        log.info("CategoryServiceImpl.adminSave业务结束, 结果{}",insert);
        return R.ok("类别添加成功!");
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 13:53
     * @ param categoryId
     * @ return 状态码
     * @ description 被后台管理服务调用,删除类别信息
     */
    @Override
    public R adminRemove(Integer categoryId) {

        Long count = productClient.adminCount(categoryId);

        if (count > 0) {
            return R.fail("类别删除失败, 有:"+count+" 件商品仍在引用!");
        }

        int i = categoryMapper.deleteById(categoryId);
        log.info("CategoryServiceImpl.adminRemove业务结束, 结果{}",i);
        return R.ok("类别删除成功!");
    }


    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 14:09
     * @ param category
     * @ return 状态码
     * @ description 被后台管理服务调用,修改类别信息
     */
    @Override
    public R adminUpdate(Category category) {

        //查询条件
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name",category.getCategoryName());

        //数据库操作
        Long count = categoryMapper.selectCount(queryWrapper);

        if (count > 0) {
            //类别已经存在
            return R.fail("类别已经存在,修改失败!");
        }

        int i = categoryMapper.updateById(category);
        log.info("CategoryServiceImpl.adminUpdate业务结束, 结果{}",i);
        return R.ok("类别修改成功!");
    }
}
