package com.yan.yanchat.common.user.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 18:22
 * @Description: 用户表 前端控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public String get(){
        System.out.println("啊呀呀");
        return "success";
    }

}

