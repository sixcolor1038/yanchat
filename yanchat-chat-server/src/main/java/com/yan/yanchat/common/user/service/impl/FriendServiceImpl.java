package com.yan.yanchat.common.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.yan.yanchat.common.chat.domain.entity.RoomFriend;
import com.yan.yanchat.common.chat.service.ChatService;
import com.yan.yanchat.common.chat.service.RoomService;
import com.yan.yanchat.common.chat.service.adapter.MessageAdapter;
import com.yan.yanchat.common.infrastructure.domain.vo.req.CursorPageBaseReq;
import com.yan.yanchat.common.infrastructure.domain.vo.req.PageBaseReq;
import com.yan.yanchat.common.infrastructure.domain.vo.resp.CursorPageBaseResp;
import com.yan.yanchat.common.infrastructure.domain.vo.resp.PageBaseResp;
import com.yan.yanchat.common.infrastructure.utils.AssertUtil;
import com.yan.yanchat.common.user.dao.UserApplyDao;
import com.yan.yanchat.common.user.dao.UserDao;
import com.yan.yanchat.common.user.dao.UserFriendDao;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.entity.UserApply;
import com.yan.yanchat.common.user.domain.entity.UserFriend;
import com.yan.yanchat.common.user.domain.enums.ApplyStatusEnum;
import com.yan.yanchat.common.user.domain.vo.req.friend.FriendApplyReq;
import com.yan.yanchat.common.user.domain.vo.req.friend.FriendApproveReq;
import com.yan.yanchat.common.user.domain.vo.req.friend.FriendCheckReq;
import com.yan.yanchat.common.user.domain.vo.resp.friend.FriendApplyResp;
import com.yan.yanchat.common.user.domain.vo.resp.friend.FriendCheckResp;
import com.yan.yanchat.common.user.domain.vo.resp.friend.FriendResp;
import com.yan.yanchat.common.user.domain.vo.resp.friend.FriendUnreadResp;
import com.yan.yanchat.common.user.service.FriendService;
import com.yan.yanchat.common.user.service.adapter.FriendAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18
 * @Description:
 */
@Service
@Slf4j
public class FriendServiceImpl implements FriendService {
    @Autowired
    private UserFriendDao userFriendDao;
    @Autowired
    private UserApplyDao userApplyDao;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserDao userDao;

    @Override
    public void apply(Long uid, FriendApplyReq req) {
        //是否有好友关系
        UserFriend friend = userFriendDao.getByFriend(uid, req.getTargetUid());
        AssertUtil.isEmpty(friend, "你们已经是好友了");
        //是否有待审批的申请记录(自己的)
        UserApply selfApproving = userApplyDao.getFriendApproving(uid, req.getTargetUid());
        if (Objects.nonNull(selfApproving)) {
            log.info("已有好友申请记录,uid:{}, targetId:{}", uid, req.getTargetUid());
            return;
        }
        //是否有待审批的申请记录(别人请求自己的)
        UserApply friendApproving = userApplyDao.getFriendApproving(req.getTargetUid(), uid);
        if (Objects.nonNull(friendApproving)) {
            ((FriendService) AopContext.currentProxy()).applyApprove(uid, new FriendApproveReq(friendApproving.getId()));
            return;
        }

    }

    @Override
    public void applyApprove(Long uid, FriendApproveReq request) {
        UserApply userApply = userApplyDao.getById(request.getApplyId());
        AssertUtil.isNotEmpty(userApply, "不存在申请记录");
        AssertUtil.equal(userApply.getTargetId(), uid, "不存在申请记录");
        AssertUtil.equal(userApply.getStatus(), ApplyStatusEnum.WAIT_APPROVAL.getCode(), "已同意好友申请");
        //同意申请
        userApplyDao.agree(request.getApplyId());
        //创建双方好友关系
        createFriend(uid, userApply.getUid());
        //创建一个聊天房间
        RoomFriend roomFriend = roomService.createFriendRoom(Arrays.asList(uid, userApply.getUid()));
        //发送一条同意消息。。我们已经是好友了，开始聊天吧
        chatService.sendMsg(MessageAdapter.buildAgreeMsg(roomFriend.getRoomId()), uid);
    }

    @Override
    public void deleteFriend(Long uid, Long friendUid) {
        //查询用户好友关系
        List<UserFriend> userFriends = userFriendDao.getUserFriend(uid, friendUid);
        if (CollectionUtil.isEmpty(userFriends)) {
            log.info("没有好友关系：{},{}", uid, friendUid);
            return;
        }
        List<Long> friendRecordIds = userFriends.stream().map(UserFriend::getId).collect(Collectors.toList());
        userFriendDao.removeByIds(friendRecordIds);
        //禁用房间
        roomService.disableFriendRoom(Arrays.asList(uid, friendUid));
    }

    @Override
    public CursorPageBaseResp<FriendResp> friendList(Long uid, CursorPageBaseReq request) {
        CursorPageBaseResp<UserFriend> friendPage = userFriendDao.getFriendPage(uid, request);
        if (CollectionUtils.isEmpty(friendPage.getList())) {
            return CursorPageBaseResp.empty();
        }
        List<Long> friendUids = friendPage.getList()
                .stream().map(UserFriend::getFriendUid)
                .collect(Collectors.toList());
        List<User> userList = userDao.getFriendList(friendUids);
        return CursorPageBaseResp.init(friendPage, FriendAdapter.buildFriend(friendPage.getList(), userList));

    }

    @Override
    public PageBaseResp<FriendApplyResp> pageApplyFriend(Long uid, PageBaseReq request) {
        IPage<UserApply> userApplyIPage = userApplyDao.friendApplyPage(uid, request.plusPage());
        if (CollectionUtil.isEmpty(userApplyIPage.getRecords())) {
            return PageBaseResp.empty();
        }
        //将这些申请列表设为已读
        readApples(uid, userApplyIPage);
        //返回消息
        return PageBaseResp.init(userApplyIPage, FriendAdapter.buildFriendApplyList(userApplyIPage.getRecords()));

    }

    @Override
    public FriendUnreadResp unread(Long uid) {
        return new FriendUnreadResp(userApplyDao.getUnReadCount(uid));
    }

    @Override
    public FriendCheckResp check(Long uid, FriendCheckReq request) {
        List<UserFriend> friendList = userFriendDao.getByFriends(uid, request.getUidList());

        Set<Long> friendUidSet = friendList.stream()
                .map(UserFriend::getFriendUid)
                .collect(Collectors.toSet());
        List<FriendCheckResp.FriendCheck> friendCheckList = request
                .getUidList()
                .stream()
                .map(friendUid -> {
                    FriendCheckResp.FriendCheck friendCheck = new FriendCheckResp.FriendCheck();
                    friendCheck.setUid(friendUid);
                    friendCheck.setIsFriend(friendUidSet.contains(friendUid));
                    return friendCheck;
                }).collect(Collectors.toList());
        return new FriendCheckResp(friendCheckList);
    }

    private void readApples(Long uid, IPage<UserApply> userApplyIPage) {
        List<Long> applyIds = userApplyIPage.getRecords()
                .stream().map(UserApply::getId)
                .collect(Collectors.toList());
        userApplyDao.readApples(uid, applyIds);
    }

    private void createFriend(Long uid, Long targetUid) {
        UserFriend userFriend1 = new UserFriend();
        userFriend1.setUid(uid);
        userFriend1.setFriendUid(targetUid);
        UserFriend userFriend2 = new UserFriend();
        userFriend2.setUid(targetUid);
        userFriend2.setFriendUid(uid);
        userFriendDao.saveBatch(Lists.newArrayList(userFriend1, userFriend2));
    }
}
