package com.silvericekey.cloudstorage.features.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.silvericekey.cloudstorage.features.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * mybatis-plus 用户
 */
public interface UserMapper extends BaseMapper<User> {
    
}
