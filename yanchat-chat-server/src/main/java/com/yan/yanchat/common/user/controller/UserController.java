package com.yan.yanchat.common.user.controller;

import com.yan.yanchat.common.user.domain.vo.resp.UserInfoResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 18:22
 * @Description: 用户表 前端控制器
 */
@RestController
@RequestMapping("/capi/user")
@Api(value = "用户模块接口")
public class UserController {

    @GetMapping()
    public String test() {
        System.out.println("啊呀呀");
        return "success";
    }

    @GetMapping("/userinfo")
    @ApiOperation("获取用户信息")
    public UserInfoResp getUserInfo(@RequestParam Long uid){

        return null;
    }

}

