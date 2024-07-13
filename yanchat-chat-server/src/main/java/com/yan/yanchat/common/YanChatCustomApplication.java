package com.yan.yanchat.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: sixcolor
 * @Date: 2024-02-13 18:58
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"com.yan.yanchat"})
public class YanChatCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(YanChatCustomApplication.class);
    }
}
