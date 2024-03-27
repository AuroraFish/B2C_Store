package com.aurora.order.mapper;

import com.aurora.pojo.Order;
import com.aurora.vo.AdminOrderVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {
    
    /**
     * @ author AuroraCjt
     * @ date 2024/3/27 11:37
     * @ param offset pageSize
     * @ return 
     * @ description 自定义方法 查询后台管理服务所需的数据
     */
    List<AdminOrderVo> selectAdminOrder(@Param("offset")int offset,@Param("pageSize") int pageSize);
}
