package com.silvericekey.cloudstorage.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.silvericekey.cloudstorage.features.user.mapper.UserMapper;
import com.silvericekey.cloudstorage.features.user.entity.User;
import com.silvericekey.cloudstorage.features.user.service.UserService;
import com.silvericekey.cloudstorage.util.JWTPackageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
    protected User getUser(){
        String token = request.getHeader("Authentication");
        String userId = JWTPackageUtil.fromJwtGetUserId(token);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        return userService.getBaseMapper().selectOne(queryWrapper);
    }
}
