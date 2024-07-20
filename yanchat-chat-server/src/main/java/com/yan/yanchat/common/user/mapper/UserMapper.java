package com.yan.yanchat.common.user.mapper;

import com.yan.yanchat.common.user.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: sixcolor
 * @Date: 2024-02-14
 * @Description: 用户表 Mapper 接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
