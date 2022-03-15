package com.silvericekey.cloudstorage.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.silvericekey.cloudstorage.common.Constants;
import com.silvericekey.cloudstorage.features.user.entity.UserInfo;
import jodd.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT封装工具
 */
public class JWTPackageUtil {
    /**
     * 生成JWT-token字符串
     * @param user
     * @return
     */
    public static String createJWT(UserInfo user){
        DateTime now = DateTime.now();
        DateTime ExpTime = now.offset(DateField.MILLISECOND, Constants.TOKEN_EXP);
        Map<String,Object> payload = new HashMap<>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT,now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT,ExpTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE,now);
        payload.put("userId",user.getId());
        payload.put("username",user.getUsername());
        return JWTUtil.createToken(payload,Constants.ENCRYPTKEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 验证token是否有效
     * @param token
     * @return
     */
    public static boolean verifyJWT(String token){
        if (StringUtil.isEmpty(token)){
            return false;
        }
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.setKey(Constants.ENCRYPTKEY.getBytes(StandardCharsets.UTF_8)).verify();
    }

    /**
     * 从jwt获取用户id
     * @param token
     * @return
     */
    public static String fromJwtGetUserId(String token){
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.getPayload("userId").toString();
    }
}
