package com.silvericekey.cloudstorage.controller;

import com.silvericekey.cloudstorage.base.BaseController;
import com.silvericekey.cloudstorage.features.user.model.RegisterVo;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.user.model.UserVo;
import com.silvericekey.cloudstorage.features.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author SilverIceKey
 * 用户相关
 */
@Slf4j
@Api(tags = "用户信息")
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
    @ApiOperation("登录")
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
    @ApiOperation("注册")
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
    @ApiOperation("修改密码")
    @PostMapping(path = "/changePassword")
    public RestResponse changePassword(@RequestBody UserVo userVo) {
        return userService.changePassword(userVo);
    }
}
