package com.aurora.carousel.service.Impl;

import com.aurora.carousel.mapper.CarouselMapper;
import com.aurora.pojo.Carousel;
import com.aurora.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ author AuroraCjt
 * @ date 2024/3/21 15:57
 * @ DESCRIPTION
 */
@Service
@Slf4j
public class CarouselServiceImpl implements com.aurora.carousel.service.CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/21 15:58
     * @ description 按照优先级查询数据库数据 使用stream流进行数据切割保存6条数据
     * @ note  1.@Cacheable 注解 : cacheManager 配置缓存时间 , 自缓存配置类中选择配置时间
     *         2.value : 缓存分区 , key : 缓存key
     */
    @Cacheable(value = "list.carousel",key = "#root.methodName",cacheManager = "cacheManagerDay")
    @Override
    public R list() {

        QueryWrapper<Carousel> carouselQueryWrapper = new QueryWrapper<>();
        carouselQueryWrapper.orderByDesc("priority");

        List<Carousel> list = carouselMapper.selectList(carouselQueryWrapper);

        //jdk 1.8 stream
        List<Carousel> collect = list.stream().limit(6).collect(Collectors.toList());
        R ok = R.ok(collect);

        return ok;
    }
}
