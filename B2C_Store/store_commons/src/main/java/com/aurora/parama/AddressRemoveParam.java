package com.aurora.parama;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ author AuroraCjt
 * @ date 2024/3/21 14:34
 * @ DESCRIPTION 地址移除参数实体
 */
@Data
public class AddressRemoveParam {

    @NotNull
    private Integer id;
}
