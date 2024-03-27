package com.aurora.collect.service;

import com.aurora.pojo.Collect;
import com.aurora.utils.R;

public interface CollectService {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 12:53
     * @ param collect
     * @ return 001 成功 , 004 失败
     * @ description 收藏添加的方法
     */
    R save(Collect collect);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 13:29
     * @ param userId 用户ID
     * @ return 商品信息集合
     * @ description 根据用户ID查询商品信息集合
     */
    R list(Integer userId);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/23 13:41
     * @ param collect {userID,productId}
     * @ return 001 004
     * @ description 根据用户ID和商品ID删除收藏数据
     */
    R remove(Collect collect);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/26 15:55
     * @ param productId
     * @ return 
     * @ description 被后台管理服务调用 根据商品ID删除收藏数据
     */
    R removeByPid(Integer productId);
}
