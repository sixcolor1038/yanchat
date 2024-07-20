package com.yan.yanchat.common.user.controller;

import com.yan.yanchat.common.infrastructure.domain.vo.resp.ApiResult;
import com.yan.yanchat.common.infrastructure.utils.RequestHolder;
import com.yan.yanchat.common.user.domain.vo.req.FriendApplyReq;
import com.yan.yanchat.common.user.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18 15:18
 * @Description:
 */
@RestController
@RequestMapping("/capi/user/friend")
@Slf4j
@Api(tags = "好友模块接口")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("apply")
    @ApiOperation("申请好友")
    public ApiResult<Void> apply(@Valid @RequestBody FriendApplyReq req) {
        friendService.apply(RequestHolder.get().getUid(), req);
        return ApiResult.success();
    }

}
