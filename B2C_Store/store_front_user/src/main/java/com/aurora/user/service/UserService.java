package com.aurora.user.service;

import com.aurora.parama.PageParam;
import com.aurora.parama.UserCheckParam;
import com.aurora.parama.UserLoginParam;
import com.aurora.pojo.User;
import com.aurora.utils.R;

/**
* @ author: AuroraCjt
* @ date:   2024/3/20 15:00
* @ DESCRIPTION :
*/
public interface UserService {

    /**
     * @author AuroraCjt
     * @date 2024/3/20 15:02
     * @param userCheckParam 账号参数 已经校验完毕
     * @return 检查结果 001 004
     * @description 检查账号是否可用业务
     */
    R check(UserCheckParam userCheckParam);

    /**
     * @author AuroraCjt
     * @date 2024/3/20 15:19
     * @param user 参数已经校验, 密码还是明文, 需加密
     * @return 结果 001 004
     * @description 注册业务
     */
    R register(User user);

    /**
     * @author AuroraCjt
     * @date 2024/3/20 15:57
     * @param userLoginParam 登录账号, 需进行密文验证
     * @return 结果 001 004
     * @description
     */
    R login(UserLoginParam userLoginParam);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 11:27
     * @ param pageParam 分页查询
     * @ return 全部用户数据
     * @ description 被后台管理服务调用, 查询全部用户数据
     */
    R listPage(PageParam pageParam);


    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 11:54
     * @ param userId 用户ID
     * @ return 状态码
     * @ description 被后台管理服务调用,根据用户ID删除数据
     */
    R remove(Integer userId);

    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:09
     * @ param user 需要修改的用户
     * @ return 
     * @ description 被后台管理服务调用,修改用户信息
     */
    R update(User user);


    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:22
     * @ param user
     * @ return
     * @ description 被后台管理服务调用,添加用户信息
     */
    R save(User user);
}
