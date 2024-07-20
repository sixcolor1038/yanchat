package com.yan.yanchat.common.user.domain.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16
 * @Description:
 */
@Data
@ApiModel(value = "用户信息")
public class UserInfoResp {
    @ApiModelProperty(value = "用户id")
    private Long id;
    @ApiModelProperty(value = "用户名称")
    private String name;
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    @ApiModelProperty(value = "用户性别")
    private Integer sex;
    @ApiModelProperty(value = "剩余改名次数")
    private Integer modifyNameChance;
}
