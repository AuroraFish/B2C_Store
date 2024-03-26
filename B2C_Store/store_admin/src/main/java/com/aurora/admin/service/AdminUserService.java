package com.aurora.admin.service;

import com.aurora.admin.param.AdminUserParam;
import com.aurora.admin.pojo.AdminUser;

/**
* @ author AuroraCjt
* @ date   2024/3/24 15:13
* @ DESCRIPTION 用户业务接口
*/
public interface AdminUserService {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 15:15
     * @ param adminUserParam 账号, 密码, 验证码
     * @ return AdminUser
     * @ description 登录业务方法
     */
    AdminUser login(AdminUserParam adminUserParam);
}
