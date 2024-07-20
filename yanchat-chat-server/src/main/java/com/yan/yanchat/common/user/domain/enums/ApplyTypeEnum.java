package com.yan.yanchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16
 * @Description: 申请类型枚举
 */
@Getter
@AllArgsConstructor
public enum ApplyTypeEnum {

    ADD_FRIEND(1, "加好友");


    private final Integer code;

    private final String desc;
}
