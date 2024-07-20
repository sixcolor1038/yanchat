package com.yan.yanchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16
 * @Description: 消息状态枚举
 */
@Getter
@AllArgsConstructor
public enum ApplyReadStatusEnum {

    UNREAD(1, "未读"),

    READ(2, "已读");

    private final Integer code;

    private final String desc;
}
