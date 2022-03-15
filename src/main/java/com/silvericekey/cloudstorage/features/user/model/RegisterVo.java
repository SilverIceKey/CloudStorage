package com.silvericekey.cloudstorage.features.user.model;

import lombok.Data;

/**
 * @author SilverIceKey
 * @title: RegisterVo
 * @date 2021/12/2816:52
 */
@Data
public class RegisterVo {
    //用户名
    private String username;
    //密码
    private String password;
    //手机号
    private String phone;
}
