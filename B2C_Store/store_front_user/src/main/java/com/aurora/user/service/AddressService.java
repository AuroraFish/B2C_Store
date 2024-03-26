package com.aurora.user.service;

import com.aurora.pojo.Address;
import com.aurora.utils.R;

public interface AddressService {
    
    /**
     * @author AuroraCjt
     * @date 2024/3/21 14:13
     * @param userId 已经校验完毕
     * @return 001 004
     * @description 根据用户ID查询地址数据
     */
    R list(Integer userId);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/21 14:29
     * @ param address 地址数据已经校验完毕
     * @ return 数据集合
     * @ description 插入地址数据, 插入成功后, 返回新的数据集合
     */
    R save(Address address);

    /**
     * @ author AuroraCjt
     * @ date 2024/3/21 14:37
     * @ param id 地址id
     * @ return 001 004
     * @ description  根据id 删除地址数据
     */
    R remove(Integer id);
}
