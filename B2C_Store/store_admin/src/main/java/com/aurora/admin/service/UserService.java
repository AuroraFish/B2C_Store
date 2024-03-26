package com.aurora.admin.service;

import com.aurora.parama.CartListParam;
import com.aurora.parama.PageParam;
import com.aurora.pojo.User;
import com.aurora.utils.R;

public interface UserService {

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 11:39
     * @ param 分页查询
     * @ return 全部用户
     * @ description 后台管理 用户展示业务
     */
    R userList(PageParam pageParam);

    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 11:59
     * @ param cartListParam {user_id}
     * @ return 状态码
     * @ description 后台管理 用户删除业务
     */
    R userRemove(CartListParam cartListParam);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:17
     * @ param user
     * @ return 状态码
     * @ description 后台管理 用户修改业务
     */
    R userUpdate(User user);


    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:27
     * @ param user
     * @ return 状态码
     * @ description 后台管理 用户添加业务
     */
    R userSave(User user);
}
