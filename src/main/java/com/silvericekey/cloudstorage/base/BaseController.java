package com.silvericekey.cloudstorage.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.silvericekey.cloudstorage.features.user.entity.UserInfo;
import com.silvericekey.cloudstorage.features.user.service.UserService;
import com.silvericekey.cloudstorage.util.JWTPackageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器
 */
@Slf4j
public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    private UserService userService;
    /**
     * 获取用户信息
     * @return
     */
    protected UserInfo getUser(){
        String token = request.getHeader("Authentication");
        String userId = JWTPackageUtil.fromJwtGetUserId(token);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserInfo.ID,userId);
        return userService.getBaseMapper().selectOne(queryWrapper);
    }
}
