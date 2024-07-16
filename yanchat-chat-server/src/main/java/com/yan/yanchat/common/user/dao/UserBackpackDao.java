package com.yan.yanchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yan.yanchat.common.infrastructure.domain.enums.BaseEnum;
import com.yan.yanchat.common.user.domain.entity.UserBackpack;
import com.yan.yanchat.common.user.mapper.UserBackpackMapper;
import org.springframework.stereotype.Service;

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
}
