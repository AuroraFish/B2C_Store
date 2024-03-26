package com.aurora.user.controller;

import com.aurora.parama.AddressListParam;
import com.aurora.parama.AddressParam;
import com.aurora.parama.AddressRemoveParam;
import com.aurora.pojo.Address;
import com.aurora.user.service.AddressService;
import com.aurora.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Binding;

/**
 * @ author AuroraCjt
 * @ date 2024/3/21 14:09
 * @ DESCRIPTION 地址Controller
 */
@RestController
@RequestMapping("user/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * @author AuroraCjt
     * @date 2024/3/21 14:10
     * @param addressListParam
     * @return
     * @description
     */
    @PostMapping("list")
    public R list(@RequestBody @Validated AddressListParam addressListParam, BindingResult result){

        //参数校验
        if (result.hasErrors()) {

            return R.fail("参数异常, 查询失败!");
        }

        return addressService.list(addressListParam.getUserId());
    }

    @PostMapping("save")
    public R save(@RequestBody @Validated AddressParam addressParam, BindingResult result) {

        //参数校验
        if (result.hasErrors()) {

            return R.fail("参数异常, 保存失败!");
        }

        Address address = addressParam.getAdd();
        address.setUserId(addressParam.getUserId());

        return addressService.save(address);
    }

    @PostMapping("remove")
    public R remove(@RequestBody @Validated AddressRemoveParam addressRemoveParam, BindingResult result) {

        //参数校验
        if (result.hasErrors()) {
            return R.fail("参数异常, 删除失败!");
        }

        return addressService.remove(addressRemoveParam.getId());
    }
}
