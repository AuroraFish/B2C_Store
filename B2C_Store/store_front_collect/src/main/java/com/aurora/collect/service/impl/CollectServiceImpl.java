package com.aurora.collect.service.impl;

import com.aurora.clients.ProductClient;
import com.aurora.collect.mapper.CollectMapper;
import com.aurora.collect.service.CollectService;
import com.aurora.parama.ProductCollectParam;
import com.aurora.pojo.Collect;
import com.aurora.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/23 12:53
 * @ DESCRIPTION
 */
@Service
@Slf4j
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private ProductClient productClient;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 12:53
     * @ param collect
     * @ return 001 成功 , 004 失败
     * @ description 收藏添加的方法
     * @ note 1.查询是否存在
     *        2.不存在则进行添加
     */
    @Override
    public R save(Collect collect) {

        //1.查询是否存在
        //查询条件
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",collect.getUserId());
        queryWrapper.eq("product_id",collect.getProductId());

        //数据库操作
        Long count = collectMapper.selectCount(queryWrapper);

        if (count > 0) {
            log.info("CollectServiceImpl.save业务结束, 结果{}","收藏已经添加, 无需再次添加");
            return R.fail("收藏已经添加, 无需再次添加");
        }

        //2.不存在则进行添加
        //补充收藏时间
        collect.setCollectTime(System.currentTimeMillis());
        int rows = collectMapper.insert(collect);
        log.info("CollectServiceImpl.save业务结束, 结果{}",rows);
        return R.ok("收藏添加成功");
    }


    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 13:29
     * @ param userId 用户ID
     * @ return 商品信息集合
     * @ description 根据用户ID查询商品信息集合
     */
    @Override
    public R list(Integer userId) {

        //1.查询条件
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.select("product_id");

        //2.数据库操作
        List<Object> idsObject = collectMapper.selectObjs(queryWrapper);

        //将idsObject 类型转换成Integer类型的列表
        List<Integer> ids = new ArrayList<>();
        for (Object o : idsObject) {
            ids.add((Integer) o);
        }

        //放入参数列表 准备调用Product服务获取商品信息集合
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(ids);

        //调用商品服务
        R r = productClient.productIds(productCollectParam);
        log.info("CollectServiceImpl.list业务结束, 结果{}",r);
        return r;
    }


    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 13:41
     * @ param collect {userID,productId}
     * @ return 001 004
     * @ description 根据用户ID和商品ID删除收藏数据
     */
    @Override
    public R remove(Collect collect) {

        //1.查询条件
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",collect.getUserId());
        queryWrapper.eq("product_id",collect.getProductId());

        //2.数据库操作
        int rows = collectMapper.delete(queryWrapper);
        log.info("CollectServiceImpl.remove业务结束, 结果{}",rows);
        return R.ok("收藏删除成功!");
    }
}
