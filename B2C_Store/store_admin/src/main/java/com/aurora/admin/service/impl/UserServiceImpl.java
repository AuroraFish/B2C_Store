package com.aurora.admin.service.impl;

import com.aurora.admin.service.UserService;
import com.aurora.clients.UserClient;
import com.aurora.parama.CartListParam;
import com.aurora.parama.PageParam;
import com.aurora.pojo.User;
import com.aurora.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @ author AuroraCjt
 * @ date 2024/3/25 11:38
 * @ DESCRIPTION 用户业务实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserClient userClient;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 11:39
     * @ param 分页查询
     * @ return 全部用户
     * @ description 后台管理 用户展示业务
     * @ note Cacheable 启动缓存
     */
    @Cacheable(value = "list.user",key = "#pageParam.currentPage+'-'+#pageParam.pageSize")
    @Override
    public R userList(PageParam pageParam) {

        R r = userClient.adminListPage(pageParam);
        log.info("UserServiceImpl.userList业务结束, 结果{}",r);
        return r;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 11:59
     * @ param userId
     * @ return 状态码
     * @ description 后台管理 用户删除业务
     * @ note CacheEvict 清空缓存
     */
    @CacheEvict(value = "list.user", allEntries = true)
    @Override
    public R userRemove(CartListParam cartListParam) {

        R r = userClient.adminRemove(cartListParam);
        log.info("UserServiceImpl.userRemove业务结束, 结果{}",r);
        return r;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:17
     * @ param user
     * @ return 状态码
     * @ description 后台管理 用户修改业务
     */
    @CacheEvict(value = "list.user", allEntries = true)
    @Override
    public R userUpdate(User user) {

        R r = userClient.adminUpdate(user);
        log.info("UserServiceImpl.userUpdate业务结束, 结果{}",r);
        return r;
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:27
     * @ param user
     * @ return 状态码
     * @ description 后台管理 用户添加业务
     */
    @CacheEvict(value = "list.user", allEntries = true)
    @Override
    public R userSave(User user) {

        R r = userClient.adminSave(user);
        log.info("UserServiceImpl.userSave业务结束, 结果{}",r);
        return r;
    }
}
