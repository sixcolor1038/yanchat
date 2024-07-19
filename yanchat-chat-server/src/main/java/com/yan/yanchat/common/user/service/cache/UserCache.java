package com.yan.yanchat.common.user.service.cache;

import com.yan.yanchat.common.user.dao.UserRoleDao;
import com.yan.yanchat.common.user.domain.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: sixcolor
 * @Date: 2024-02-16 15:57
 * @Description: 用户相关缓存
 */
@Component
public class UserCache {

    @Autowired
    private UserRoleDao userRoleDao;

    @Cacheable(cacheNames = "user", key = "'roles:'+#uid")
    public Set<Long> getRoleSet(Long uid) {
        List<UserRole> userRoles = userRoleDao.listByUid(uid);
        return userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toSet());
    }
}
