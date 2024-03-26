package com.aurora.user.service.impl;

import com.aurora.parama.PageParam;
import com.aurora.parama.UserCheckParam;
import com.aurora.parama.UserLoginParam;
import com.aurora.pojo.User;
import com.aurora.user.mapper.UserMapper;
import com.aurora.user.service.UserService;
import com.aurora.utils.MD5Util;
import com.aurora.utils.R;
import com.aurora.utils.constants.UserConstants;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/20 15:04
 * @ DESCRIPTION 用户业务实现类
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public R check(UserCheckParam userCheckParam) {

        //参数封装
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userCheckParam.getUserName());

        //数据库查询
        Long total = userMapper.selectCount(queryWrapper);

        //查询结果处理
        if (total == 0) {
            //数据库不存在, 可用
            log.info("UserServiceImpl.check业务结束, 结果:{}", "账号可以使用!");
            return R.ok("账号存在, 可以使用!");
        }

        log.info("UserServiceImpl.check业务结束, 结果:{}", "账号不可使用!");
        return R.fail("账号已经存在, 不可注册!");
    }

    /**
     * @author AuroraCjt
     * @date 2024/3/20 15:20
     * @param user 参数及已经校验 但是密码仍是明文
     * @return 结果 001 004
     * @description 注册业务 1.检查账号是否存在 2.密码加密处理 3.插入数据库数据 4.返回结果封装
     */
    @Override
    public R register(User user) {
        
        //1.检查账号是否存在
        //参数封装
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());

        //数据库查询
        Long total = userMapper.selectCount(queryWrapper);

        if (total > 0) {
            log.info("UserServiceImpl.register业务结束, 结果:{}","账号存在,注册失败!");
            return R.fail("账号已经存在, 不可注册!");
        }

        //2.密码加密处理, 注意加密 md5加密
        //md5 固定的明文加密后的密文是固定的
        //注册加密之后, 往后登录也用密文和数据库里的密文进行对比
        //加盐处理 : 在用户的密码后加上我们提供的字符串, 提高密码复杂度

        String newPWD = MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT);
        user.setPassword(newPWD);

        //3.数据库插入数据
        //影响行数等于1插入成功, 等于0插入失败
        int rows = userMapper.insert(user);

        //4.返回封装结果
        if (rows == 0) {
            log.info("UserServiceImpl.register业务结束, 结果:{}","数据插入失败!注册失败");
            return R.fail("注册失败, 请稍后再试!");
        }

        log.info("UserServiceImpl.register业务结束, 结果:{}","注册成功");
        return R.ok("注册成功!");
    }

    @Override
    /**
     * @author AuroraCjt
     * @date 2024/3/20 15:59
     * @param userLoginParam 登录传入的明文
     * @return com.aurora.utils.R
     * @description 登录业务 1.密码加密加盐处理 2.账号和密码进行数据库查询 3.判断返回结果
     */
    public R login(UserLoginParam userLoginParam) {

        //1.密码处理
        String newPWD = MD5Util.encode(userLoginParam.getPassword()+UserConstants.USER_SLAT);

        //2.数据库查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userLoginParam.getUserName());
        queryWrapper.eq("password",newPWD);

        User user = userMapper.selectOne(queryWrapper);

        //3.结果处理
        if (user == null) {
            log.info("UserServiceImpl.login业务结束，结果:{}","账号和密码错误!");
            return R.fail("账号密码错误");
        }

        log.info("UserServiceImpl.login业务结束，结果:{}","登录成功!");
        user.setPassword(null);
        return R.ok("登录成功",user);
    }


    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 11:27
     * @ param pageParam 分页查询
     * @ return 全部用户数据
     * @ description 被后台管理服务调用, 查询全部用户数据
     */
    @Override
    public R listPage(PageParam pageParam) {

        //查询条件
        IPage<User> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());

        //数据库操作
        page = userMapper.selectPage(page, null);

        List<User> records = page.getRecords();
        long total = page.getTotal();
        log.info("UserServiceImpl.listPage业务结束，结果:{}",records);
        return R.ok("用户管理查询成功!",records,total);
    }


    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 11:54
     * @ param userId 用户ID
     * @ return 状态码
     * @ description 被后台管理服务调用,根据用户ID删除数据
     */
    @Override
    public R remove(Integer userId) {

        int i = userMapper.deleteById(userId);
        log.info("UserServiceImpl.remove业务结束，结果:{}",i);
        return R.ok("用户数据删除成功!");
    }


    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:09
     * @ param user 需要修改的用户
     * @ return 状态码
     * @ description 被后台管理服务调用,修改用户信息
     * @ note 1.账号和ID不修改
     *        2.密码判断 如果是原来的密码 则不修改
     *        3.如果是新密码 加密后修改
     *        4.修改用户信息
     */
    @Override
    public R update(User user) {

        //1.判断密码是否变更
        //查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getUserId());
        queryWrapper.eq("password",user.getPassword());

        //数据库操作
        Long count = userMapper.selectCount(queryWrapper);

        //判断
        if (count == 0) {
            //新密码 需加密
            user.setPassword(MD5Util.encode(user.getPassword()+UserConstants.USER_SLAT));
        }

        int i = userMapper.updateById(user);
        log.info("UserServiceImpl.update业务结束，结果:{}",i);
        return R.ok("用户信息修改完成!");
    }


    /**
     * @ author AuroraCjt
     * @ date 2024/3/25 12:22
     * @ param user
     * @ return
     * @ description 被后台管理服务调用,添加用户信息
     */
    @Override
    public R save(User user) {

        //1.检查账号是否存在
        //参数封装
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());

        //数据库查询
        Long total = userMapper.selectCount(queryWrapper);

        if (total > 0) {
            log.info("UserServiceImpl.save业务结束, 结果:{}","账号存在,添加失败!");
            return R.fail("账号已经存在, 不可添加!");
        }

        //2.密码加密处理, 注意加密 md5加密
        //md5 固定的明文加密后的密文是固定的
        //注册加密之后, 往后登录也用密文和数据库里的密文进行对比
        //加盐处理 : 在用户的密码后加上我们提供的字符串, 提高密码复杂度

        String newPWD = MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT);
        user.setPassword(newPWD);

        //3.数据库插入数据
        //影响行数等于1插入成功, 等于0插入失败
        int rows = userMapper.insert(user);

        //4.返回封装结果
        if (rows == 0) {
            log.info("UserServiceImpl.save业务结束, 结果:{}","数据插入失败!添加失败");
            return R.fail("添加失败, 请稍后再试!");
        }

        log.info("UserServiceImpl.save业务结束, 结果:{}","添加成功");
        return R.ok("添加成功!");
    }
}
