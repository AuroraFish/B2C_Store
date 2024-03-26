package com.aurora.user.service.impl;

import com.aurora.pojo.Address;
import com.aurora.user.mapper.AddressMapper;
import com.aurora.user.service.AddressService;
import com.aurora.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/21 14:14
 * @ DESCRIPTION 地址业务实现类
 */
@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/21 14:15
     * @ param userId 用户id 已经校验完毕
     * @ return 001 004
     * @ description 1.直接进行数据库查询 2.结果封装即可
     */
    @Override
    public R list(Integer userId) {

        //1.封装查询参数
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Address> addressList = addressMapper.selectList(queryWrapper);

        //2.结果封装
        R ok = R.ok("查询成功",addressList);
        log.info("AddressServiceImpl.list业务结束, 结果{}",ok);
        return ok;
    }


    /**
     * @ author AuroraCjt
     * @ date 2024/3/21 14:30
     * @ param address
     * @ return 数据集合
     * @ description 1.插入数据 2.返回结果
     */
    @Override
    public R save(Address address) {

        //1.插入数据
        int rows = addressMapper.insert(address);

        //2.插入判断
        if (rows == 0) {
            log.info("AddressServiceImpl.list业务结束, 结果{}","插入地址失败");
            return R.fail("插入地址失败");
        }

        //3.复用查询业务
        return list(address.getUserId());
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/21 14:38
     * @ param id 删除地址id
     * @ return 001 004
     * @ description 根据id 删除地址数据
     */
    @Override
    public R remove(Integer id) {

        int rows = addressMapper.deleteById(id);

        if (rows == 0) {
            log.info("AddressServiceImpl.list业务结束, 结果{}","删除地址数据失败");
            return R.fail("删除地址数据失败");
        }

        log.info("AddressServiceImpl.list业务结束, 结果{}","删除成功");
        return R.ok("地址删除成功");
    }
}
