package com.yan.yanchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16 17:26
 * @Description: 拉黑枚举
 */
@AllArgsConstructor
@Getter
public enum BlackTypeEnum {
    UID(1, "UID"),
    IP(2, "IP"),
    ;

    private final Integer type;
    private final String desc;

    private static Map<Integer, BlackTypeEnum> cache;

    static {
        cache = Arrays.stream(BlackTypeEnum.values()).collect(Collectors.toMap(BlackTypeEnum::getType, Function.identity()));
    }

    public static BlackTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
