package com.silvericekey.cloudstorage.model;

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
