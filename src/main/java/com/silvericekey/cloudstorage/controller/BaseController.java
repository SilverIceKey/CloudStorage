package com.silvericekey.cloudstorage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.silvericekey.cloudstorage.dao.mapper.UserMapper;
import com.silvericekey.cloudstorage.entity.User;
import com.silvericekey.cloudstorage.util.JWTPackageUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private UserMapper userMapper;
    /**
     * 获取用户信息
     * @return
     */
    protected User getUser(){
        String token = request.getHeader("Authentication");
        String userId = JWTPackageUtil.fromJwtGetUserId(token);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        return userMapper.selectOne(queryWrapper);
    }
}
