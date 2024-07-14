package com.yan.yanchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yan.yanchat.common.user.domain.entity.User;
import com.yan.yanchat.common.user.mapper.UserMapper;
import com.yan.yanchat.common.user.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14 18:22
 * @Description: 用户表 服务实现类
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> implements IUserService {

}
