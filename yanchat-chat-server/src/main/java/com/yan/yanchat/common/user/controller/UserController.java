package com.yan.yanchat.common.user.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.yan.yanchat.common.infrastructure.domain.dto.RequestInfo;
import com.yan.yanchat.common.infrastructure.domain.vo.resp.ApiResult;
import com.yan.yanchat.common.infrastructure.utils.RequestHolder;
import com.yan.yanchat.common.user.domain.vo.resp.UserInfoResp;
import com.yan.yanchat.common.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 18:22
 * @Description: 用户表 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/capi/user")
@Api(tags = "用户模块接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String test() {
        System.out.println("啊呀呀");
        return "success";
    }

    @GetMapping("/userinfo")
    @ApiOperation("获取用户信息")
    public ApiResult<UserInfoResp> getUserInfo(){
        RequestInfo requestInfo = RequestHolder.get();
        return ApiResult.success(userService.getUserInfo(RequestHolder.get().getUid()));
    }

}

