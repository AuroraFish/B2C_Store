package com.aurora.admin.service.impl;

import com.aurora.admin.mapper.AdminUserMapper;
import com.aurora.admin.param.AdminUserParam;
import com.aurora.admin.pojo.AdminUser;
import com.aurora.admin.service.AdminUserService;
import com.aurora.utils.MD5Util;
import com.aurora.utils.constants.UserConstants;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ author AuroraCjt
 * @ date 2024/3/24 15:12
 * @ DESCRIPTION 用户业务实现类
 */
@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 15:15
     * @ param adminUserParam 账号, 密码, 验证码
     * @ return AdminUser
     * @ description 登录业务方法
     */
    @Override
    public AdminUser login(AdminUserParam adminUserParam) {

        //1.查询条件
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",adminUserParam.getUserAccount());
        //密码 加密加盐
        queryWrapper.eq("user_password", MD5Util.encode(adminUserParam.getUserPassword()+ UserConstants.USER_SLAT));

        //2.数据库操作
        AdminUser user = adminUserMapper.selectOne(queryWrapper);

        //3.结果处理
        log.info("AdminUserServiceImpl.login业务结束, 结果{}",user);
        return user;
    }
}
