package com.silvericekey.cloudstorage.features.user.model;

import lombok.Data;

/**
 * 登录返回
 */
@Data
public class LoginResponse {
    /**
     * JWT-token
     */
    private String token;
}
