package com.yan.yanchat.common.user.dao;

import com.yan.yanchat.common.user.domain.entity.UserRole;
import com.yan.yanchat.common.user.mapper.UserRoleMapper;
import com.yan.yanchat.common.user.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18 12:16
 * @Description: 用户角色关系表 服务实现类
 */
@Service
public class UserRoleDao extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
