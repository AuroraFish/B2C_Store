package com.aurora.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ author AuroraCjt
 * @ date 2024/3/24 14:59
 * @ DESCRIPTION 后台管理用户实体类
 */
@Data
@TableName("admin_user")
public class AdminUser implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer userId;
    private String userName;
    private String userAccount;
    private String userPassword;
    private String userPhone;
    private Date createTime;
    private Integer userRole;
}
