package com.silvericekey.cloudstorage.config;

import com.alibaba.fastjson.JSON;
import com.silvericekey.cloudstorage.common.ErrorCode;
import com.silvericekey.cloudstorage.util.JWTPackageUtil;
import com.silvericekey.cloudstorage.util.RestUtil;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT拦截器
 */
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authentication");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        if (!JWTPackageUtil.verifyJWT(token)) {
            response.getWriter().append(JSON.toJSONString(RestUtil.error(ErrorCode.TOKEN_ERROR,"请先登录")));
            return false;
        }else {
            return true;
        }
    }
}
