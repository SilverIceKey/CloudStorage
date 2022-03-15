package com.silvericekey.cloudstorage.controller;

import com.silvericekey.cloudstorage.base.BaseController;
import com.silvericekey.cloudstorage.features.user.model.RegisterVo;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.user.model.UserVo;
import com.silvericekey.cloudstorage.features.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SilverIceKey
 * 用户相关
 */
@Slf4j
@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserService userService;
    /**
     * 登录
     *
     * @param userVo
     * @return
     */
    @PostMapping(path = "/login")
    public RestResponse login(@RequestBody UserVo userVo) {
        return userService.login(userVo);
    }

    /**
     * 注册
     *
     * @param registerVo
     * @return
     */
    @PostMapping(path = "/register")
    public RestResponse register(@RequestBody RegisterVo registerVo) {
        return userService.register(registerVo);
    }

    /**
     * 修改密码
     *
     * @param userVo
     * @return
     */
    @PostMapping(path = "/changePassword")
    public RestResponse changePassword(@RequestBody UserVo userVo) {
        return userService.changePassword(userVo);
    }
}
