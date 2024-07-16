package com.yan.yanchat.common.infrastructure.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16 17:26
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum BaseEnum {
    YES(1, "是"),
    NO(0, "否");


    private final Integer status;
    private final String desc;


}
