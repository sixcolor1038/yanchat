package com.yan.yanchat.common.user.controller;

import com.yan.yanchat.common.infrastructure.domain.vo.resp.ApiResult;
import com.yan.yanchat.common.infrastructure.utils.AssertUtil;
import com.yan.yanchat.common.infrastructure.utils.RequestHolder;
import com.yan.yanchat.common.user.domain.enums.RoleEnum;
import com.yan.yanchat.common.user.domain.vo.req.user.BlackReq;
import com.yan.yanchat.common.user.domain.vo.req.user.ModifyNameReq;
import com.yan.yanchat.common.user.domain.vo.req.user.WearingBadgeReq;
import com.yan.yanchat.common.user.domain.vo.resp.BadgeResp;
import com.yan.yanchat.common.user.domain.vo.resp.UserInfoResp;
import com.yan.yanchat.common.user.service.RoleService;
import com.yan.yanchat.common.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14
 * @Description: 用户表 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/capi/user")
@Api(tags = "用户模块接口")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

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

    @GetMapping("/badges")
    @ApiOperation("可选徽章预览")
    public ApiResult<List<BadgeResp>> badges() {
        return ApiResult.success(userService.badges(RequestHolder.get().getUid()));
    }

    @PutMapping("/badge")
    @ApiOperation("佩戴徽章")
    public ApiResult<Void> wearingBadge(@Valid @RequestBody WearingBadgeReq req) {
        userService.wearingBadge(RequestHolder.get().getUid(), req.getItemId());
        return ApiResult.success();
    }

    @PutMapping("/black")
    @ApiOperation("拉黑用户")
    public ApiResult<Void> black(@Valid @RequestBody BlackReq req) {
        Long uid = RequestHolder.get().getUid();
        boolean hasPower = roleService.hasPower(uid, RoleEnum.ADMIN);
        AssertUtil.isTrue(hasPower,"该用户无拉黑权限");
        userService.black(req);
        return ApiResult.success();
    }
}

