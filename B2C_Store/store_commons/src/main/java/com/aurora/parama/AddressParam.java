package com.aurora.parama;

import com.aurora.pojo.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ author AuroraCjt
 * @ date 2024/3/24 11:28
 * @ DESCRIPTION 地址接受值参数
 */
@Data
public class AddressParam {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    private Address add;

}
