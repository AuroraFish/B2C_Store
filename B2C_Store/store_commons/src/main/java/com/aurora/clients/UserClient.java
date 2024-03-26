package com.aurora.clients;

import com.aurora.parama.CartListParam;
import com.aurora.parama.PageParam;
import com.aurora.pojo.User;
import com.aurora.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @ author AuroraCjt
* @ date   2024/3/25 11:33
* @ DESCRIPTION 用户服务调用接口
*/
@FeignClient("user-service")
public interface UserClient {

    @PostMapping("/user/admin/list")
    R adminListPage(@RequestBody PageParam pageParam);

    @PostMapping("/user/admin/remove")
    R adminRemove(@RequestBody CartListParam cartListParam);

    @PostMapping("user/admin/update")
    R adminUpdate(@RequestBody User user);

    @PostMapping("user/admin/save")
    R adminSave(@RequestBody User user);
}
