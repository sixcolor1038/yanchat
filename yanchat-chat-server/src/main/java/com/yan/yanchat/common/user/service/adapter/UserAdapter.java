package com.yan.yanchat.common.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.yan.yanchat.common.infrastructure.domain.enums.BaseEnum;
import com.yan.yanchat.common.user.domain.entity.ItemConfig;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.domain.entity.UserBackpack;
import com.yan.yanchat.common.user.domain.vo.resp.BadgeResp;
import com.yan.yanchat.common.user.domain.vo.resp.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: sixcolor
 * @Date: 2024-02-15 16:27
 * @Description: 用户适配器
 */
public class UserAdapter {
    public static User buildUser(String openId) {
        return User.builder().openId(openId).build();
    }

    public static User buildAuthorizeUser(Long uid, WxOAuth2UserInfo userInfo) {
        return User.builder()
                .id(uid)
                .name(userInfo.getNickname())
                .avatar(userInfo.getHeadImgUrl())
                .build();
    }

    public static UserInfoResp buildUserInfo(User user, Integer modifyNameCount) {
        UserInfoResp vo = new UserInfoResp();
        BeanUtil.copyProperties(user, vo);
        vo.setModifyNameChance(modifyNameCount);
        return vo;
    }

    public static List<BadgeResp> buildBadgeResp(List<ItemConfig> itemConfigs, List<UserBackpack> backpacks, User user) {
        Set<Long> obtainItemSet = backpacks.stream().map(UserBackpack::getItemId).collect(Collectors.toSet());
        return itemConfigs.stream().map(x -> {
                    BadgeResp resp = new BadgeResp();
                    BeanUtil.copyProperties(x, resp);
                    resp.setObtain(obtainItemSet.contains(x.getId()) ? BaseEnum.YES.getStatus() : BaseEnum.NO.getStatus());
                    resp.setWearing(Objects.equals(x.getId(), user.getItemId()) ? BaseEnum.YES.getStatus() : BaseEnum.NO.getStatus());
                    return resp;
                }).sorted(Comparator.comparing(BadgeResp::getWearing, Comparator.reverseOrder())
                        .thenComparing(BadgeResp::getObtain, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
