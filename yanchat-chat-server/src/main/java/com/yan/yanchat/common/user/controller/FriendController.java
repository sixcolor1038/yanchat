package com.yan.yanchat.common.user.controller;

import com.yan.yanchat.common.infrastructure.domain.vo.req.CursorPageBaseReq;
import com.yan.yanchat.common.infrastructure.domain.vo.req.PageBaseReq;
import com.yan.yanchat.common.infrastructure.domain.vo.resp.ApiResult;
import com.yan.yanchat.common.infrastructure.domain.vo.resp.CursorPageBaseResp;
import com.yan.yanchat.common.infrastructure.domain.vo.resp.PageBaseResp;
import com.yan.yanchat.common.infrastructure.utils.RequestHolder;
import com.yan.yanchat.common.user.domain.vo.req.friend.FriendApplyReq;
import com.yan.yanchat.common.user.domain.vo.req.friend.FriendApproveReq;
import com.yan.yanchat.common.user.domain.vo.req.friend.FriendCheckReq;
import com.yan.yanchat.common.user.domain.vo.req.friend.FriendDeleteReq;
import com.yan.yanchat.common.user.domain.vo.resp.friend.FriendCheckResp;
import com.yan.yanchat.common.user.domain.vo.resp.friend.FriendResp;
import com.yan.yanchat.common.user.domain.vo.resp.friend.FriendApplyResp;
import com.yan.yanchat.common.user.domain.vo.resp.friend.FriendUnreadResp;
import com.yan.yanchat.common.user.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18
 * @Description:
 */
@RestController
@RequestMapping("/capi/user/friend")
@Slf4j
@Api(tags = "好友模块接口")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/page")
    @ApiOperation("联系人列表")
    public ApiResult<CursorPageBaseResp<FriendResp>> friendList(@Valid CursorPageBaseReq request) {
        return ApiResult.success(friendService.friendList(RequestHolder.get().getUid(), request));
    }

    @PostMapping("apply")
    @ApiOperation("申请好友")
    public ApiResult<Void> apply(@Valid @RequestBody FriendApplyReq req) {
        friendService.apply(RequestHolder.get().getUid(), req);
        return ApiResult.success();
    }

    @GetMapping("/apply/page")
    @ApiOperation("好友申请列表")
    public ApiResult<PageBaseResp<FriendApplyResp>> page(@Valid PageBaseReq request) {
        return ApiResult.success(friendService.pageApplyFriend(RequestHolder.get().getUid(), request));
    }

    @GetMapping("/apply/unread")
    @ApiOperation("申请未读数")
    public ApiResult<FriendUnreadResp> unread() {
        return ApiResult.success(friendService.unread(RequestHolder.get().getUid()));
    }

    @PutMapping("/apply")
    @ApiOperation("审批同意")
    public ApiResult<Void> applyApprove(@Valid @RequestBody FriendApproveReq request) {
        friendService.applyApprove(RequestHolder.get().getUid(), request);
        return ApiResult.success();
    }

    @DeleteMapping()
    @ApiOperation("删除好友")
    public ApiResult<Void> delete(@Valid @RequestBody FriendDeleteReq request) {
        friendService.deleteFriend(RequestHolder.get().getUid(), request.getTargetUid());
        return ApiResult.success();
    }

    @GetMapping("/check")
    @ApiOperation("批量判断是否是自己好友")
    public ApiResult<FriendCheckResp> check(@Valid FriendCheckReq request) {
        return ApiResult.success(friendService.check(RequestHolder.get().getUid(), request));
    }

}
