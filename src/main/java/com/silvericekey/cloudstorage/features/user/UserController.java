package com.silvericekey.cloudstorage.features.user;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.silvericekey.cloudstorage.base.BaseController;
import com.silvericekey.cloudstorage.features.folder.mapper.FolderInfoMapper;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import com.silvericekey.cloudstorage.features.user.entity.User;
import com.silvericekey.cloudstorage.features.user.model.LoginResponse;
import com.silvericekey.cloudstorage.features.user.model.RegisterVo;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.user.model.UserVo;
import com.silvericekey.cloudstorage.features.user.service.IUserService;
import com.silvericekey.cloudstorage.features.user.service.impl.UserServiceImpl;
import com.silvericekey.cloudstorage.util.JWTPackageUtil;
import com.silvericekey.cloudstorage.util.RestUtil;
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
    private final IUserService userService;
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
