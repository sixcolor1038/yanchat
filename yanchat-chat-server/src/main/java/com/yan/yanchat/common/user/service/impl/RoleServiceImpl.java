package com.yan.yanchat.common.user.service.impl;

import com.yan.yanchat.common.user.domain.enums.RoleEnum;
import com.yan.yanchat.common.user.service.RoleService;
import com.yan.yanchat.common.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18
 * @Description: 角色表 服务实现类
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private UserCache userCache;

    @Override
    public boolean hasPower(Long uid, RoleEnum roleEnum) {
        Set<Long> roleSet = userCache.getRoleSet(uid);
        return isAdmin(roleSet) || roleSet.contains(roleEnum.getId());
    }

    private boolean isAdmin(Set<Long> roleSet) {
        return roleSet.contains(RoleEnum.ADMIN.getId());
    }
}
