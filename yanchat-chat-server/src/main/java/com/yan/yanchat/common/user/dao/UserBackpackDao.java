package com.yan.yanchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yan.yanchat.common.infrastructure.domain.enums.BaseEnum;
import com.yan.yanchat.common.user.domain.entity.UserBackpack;
import com.yan.yanchat.common.user.mapper.UserBackpackMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16 08:16
 * @Description: 用户背包表 服务实现类
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> {

    public Integer getCountByValidItemId(Long uid, Long itemId) {
        return lambdaQuery().eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, BaseEnum.NO.getStatus())
                .count();
    }

    public UserBackpack getFirstValidItem(Long uid, Long itemId) {
        return lambdaQuery().eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, BaseEnum.NO.getStatus())
                .orderByAsc(UserBackpack::getId)
                .last("limit 1")
                .one();
    }

    public boolean useItem(UserBackpack item) {
        return lambdaUpdate().eq(UserBackpack::getId, item.getId())
                .eq(UserBackpack::getStatus, BaseEnum.NO.getStatus())
                .set(UserBackpack::getStatus, BaseEnum.YES.getStatus())
                .update();


    }

    public List<UserBackpack> getByItemIds(Long uid, List<Long> itemId) {
        return lambdaQuery().eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getStatus, BaseEnum.NO.getStatus())
                .in(UserBackpack::getItemId, itemId)
                .list();
    }

    public UserBackpack getByIdempotent(String idempotent) {
        return lambdaQuery()
                .eq(UserBackpack::getId, idempotent)
                .one();
    }
}
