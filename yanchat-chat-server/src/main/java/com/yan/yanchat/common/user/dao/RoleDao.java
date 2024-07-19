package com.yan.yanchat.common.user.dao;

import com.yan.yanchat.common.user.domain.entity.Role;
import com.yan.yanchat.common.user.mapper.RoleMapper;
import com.yan.yanchat.common.user.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: sixcolor
 * @Date: 2024-02-18 12:16
 * @Description: 角色表 服务实现类
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
