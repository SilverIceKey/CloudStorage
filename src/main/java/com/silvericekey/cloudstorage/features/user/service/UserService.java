package com.silvericekey.cloudstorage.features.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.user.entity.UserInfo;
import com.silvericekey.cloudstorage.features.user.model.RegisterVo;
import com.silvericekey.cloudstorage.features.user.model.UserVo;

/**
 * @author SilverIceKey
 * @title: LoginService
 * @date 2022/3/1513:07
 */
public interface UserService extends IService<UserInfo> {
    RestResponse login(UserVo userVo);

    RestResponse register(RegisterVo registerVo);

    RestResponse changePassword(UserVo userVo);
}
