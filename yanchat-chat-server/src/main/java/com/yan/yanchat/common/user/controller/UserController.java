package com.yan.yanchat.common.user.controller;

import com.yan.yanchat.common.infrastructure.domain.dto.RequestInfo;
import com.yan.yanchat.common.infrastructure.domain.vo.resp.ApiResult;
import com.yan.yanchat.common.infrastructure.utils.RequestHolder;
import com.yan.yanchat.common.user.domain.vo.req.ModifyNameReq;
import com.yan.yanchat.common.user.domain.vo.resp.UserInfoResp;
import com.yan.yanchat.common.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ApiResult<UserInfoResp> getUserInfo() {
        return ApiResult.success(userService.getUserInfo(RequestHolder.get().getUid()));
    }

    @PutMapping("/name")
    @ApiOperation("修改用户名称")
    public ApiResult<Void> modifyName(@Valid @RequestBody ModifyNameReq req) {
        userService.modifyName(RequestHolder.get().getUid(), req.getName());
        return ApiResult.success();
    }
}

