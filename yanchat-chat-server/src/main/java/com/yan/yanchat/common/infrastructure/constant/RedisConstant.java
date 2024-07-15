package com.yan.yanchat.common.infrastructure.constant;

/**
 * @Author: sixcolor
 * @Date: 2024-02-15 18:36
 * @Description: redis常量
 */
public class RedisConstant {

    private static final String BASE_KEY = "yanchat::chat";
    /**
     * 用户token的key
     */
    public static final String USER_TOKEN_STR = "usertoken:uid_%d";

    public static String getKey(String key, Object... o) {
        return BASE_KEY + String.format(key, o);
    }
}
