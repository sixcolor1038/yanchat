package com.yan.yanchat.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: sixcolor
 * @Date: 2024-02-13
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"com.yan.yanchat"})
@MapperScan({"com.yan.yanchat.common.**.mapper"})
public class YanChatCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(YanChatCustomApplication.class);
    }
}
