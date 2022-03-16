package com.silvericekey.cloudstorage.features.user.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import com.silvericekey.cloudstorage.features.folder.service.FolderService;
import com.silvericekey.cloudstorage.features.user.entity.UserInfo;
import com.silvericekey.cloudstorage.features.user.mapper.UserMapper;
import com.silvericekey.cloudstorage.features.user.model.LoginResponse;
import com.silvericekey.cloudstorage.features.user.model.RegisterVo;
import com.silvericekey.cloudstorage.features.user.model.UserVo;
import com.silvericekey.cloudstorage.features.user.service.UserService;
import com.silvericekey.cloudstorage.util.JWTPackageUtil;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author SilverIceKey
 * @title: LoginServiceImpl
 * @date 2022/3/1513:49
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements UserService {
    private final FolderService folderService;

    @Override
    public RestResponse login(UserVo userVo) {
        QueryWrapper<UserInfo> userWrapper = new QueryWrapper<>();
        userWrapper.eq(UserInfo.USERNAME, userVo.getUsername());
        UserInfo user = getBaseMapper().selectOne(userWrapper);
        if (user == null) {
            return RestUtil.error("账号不存在");
        } else if (!user.getPassword().equals(MD5.create().digestHex(userVo.getPassword()))) {
            return RestUtil.error("账号或密码错误");
        } else {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(JWTPackageUtil.createJWT(user));
            return RestUtil.ok("登录成功", loginResponse);
        }
    }

    @Override
    public RestResponse register(RegisterVo registerVo) {
        UserInfo user = new UserInfo();
        user.setUsername(registerVo.getUsername());
        user.setPassword(MD5.create().digestHex(registerVo.getPassword()));
        user.setPhone(registerVo.getPhone());
        QueryWrapper<UserInfo> userWrapper = new QueryWrapper<>();
        userWrapper.eq(UserInfo.USERNAME, registerVo.getUsername());
        boolean isInsert = getBaseMapper().selectOne(userWrapper) != null;
        if (!isInsert) {
            getBaseMapper().insert(user);
            UserInfo insertUser = getBaseMapper().selectOne(userWrapper);
            FolderInfo folderInfo = new FolderInfo();
            folderInfo.setFolderName("/"+user.getUsername());
            folderInfo.setFolderParentId(0L);
            folderInfo.setUserId(insertUser.getId());
            folderService.getBaseMapper().insert(folderInfo);
            return RestUtil.ok("注册成功");
        } else {
            return RestUtil.error("注册失败,该账号已存在");
        }
    }

    @Override
    public RestResponse changePassword(UserVo userVo) {
        QueryWrapper<UserInfo> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(UserInfo.USERNAME, userVo.getUsername());
        UserInfo user = getBaseMapper().selectOne(userQueryWrapper);
        if (user == null) {
            return RestUtil.error("用户不存在，请重新输入");
        } else {
            user.setPassword(MD5.create().digestHex(userVo.getPassword()));
            updateById(user);
            return RestUtil.ok("修改密码成功");
        }
    }

}
