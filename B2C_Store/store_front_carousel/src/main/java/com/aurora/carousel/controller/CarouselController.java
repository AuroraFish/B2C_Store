package com.aurora.carousel.controller;

import com.aurora.carousel.service.CarouselService;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ author AuroraCjt
 * @ date 2024/3/21 15:53
 * @ DESCRIPTION 轮播图控制类
 */
@RestController
@RequestMapping("carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;
    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/21 15:54
     * @ description 查询轮播图数据, 优先级最高的六条
     */
    @PostMapping("list")
    public R list() {
        
        return carouselService.list();
    }
}
