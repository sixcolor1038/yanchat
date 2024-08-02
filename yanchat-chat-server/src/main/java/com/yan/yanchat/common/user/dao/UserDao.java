package com.yan.yanchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yan.yanchat.common.infrastructure.domain.enums.YesOrNoEnum;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14
 * @Description: 用户表 服务实现类
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> {

    public User getByOpenId(String openId) {
        return lambdaQuery()
                .eq(User::getOpenId, openId)
                .one();
    }

    public void modifyName(Long uid, String name) {
        updateById(User.builder().id(uid).name(name).build());
    }

    public User getByName(String name) {
        return lambdaQuery()
                .eq(User::getName, name)
                .one();
    }

    public void wearingBadge(Long uid, Long itemId) {
        lambdaUpdate()
                .eq(User::getId, uid)
                .set(User::getItemId, itemId)
                .update();
    }

    public void invalidUid(Long uid) {
        lambdaUpdate()
                .eq(User::getId, uid)
                .set(User::getStatus, YesOrNoEnum.YES.getStatus())
                .update();
    }

    public List<User> getFriendList(List<Long> uidList) {
        return lambdaQuery()
                .in(User::getId, uidList)
                .select(User::getId, User::getActiveStatus, User::getName, User::getAvatar)
                .list();
    }
}
