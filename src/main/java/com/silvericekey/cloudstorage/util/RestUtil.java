package com.silvericekey.cloudstorage.util;

import com.silvericekey.cloudstorage.common.ErrorCode;
import com.silvericekey.cloudstorage.model.RestResponse;

/**
 * @author SilverIceKey
 * @title: RestUtil
 * @date 2021/12/2817:10
 */
public class RestUtil {
    /**
     * 通用正常返回
     *
     * @return
     */
    public static RestResponse ok() {
        return ok(null,null);
    }

    /**
     *
     * 正常返回带字符串
     * @param msg
     * @return
     */
    public static RestResponse ok(String msg) {
        return ok(msg,null);
    }

    /**
     * 正常返回带数据
     *
     * @param data
     * @return
     */
    public static RestResponse ok(Object data) {
        return ok("获取成功",data);
    }

    /**
     * 正常返回带字符串和数据
     * @param msg
     * @return
     */
    public static RestResponse ok(String msg, Object data) {
        RestResponse restReponse = new RestResponse();
        restReponse.setCode(ErrorCode.OK);
        restReponse.setMsg(msg);
        restReponse.setData(data);
        return restReponse;
    }


    /**
     * 通用错误返回
     *
     * @return
     */
    public static RestResponse error() {
        RestResponse restReponse = new RestResponse();
        restReponse.setCode(ErrorCode.COMMON_ERROR);
        restReponse.setMsg("数据处理异常");
        return restReponse;
    }

    /**
     * 通用错误返回，自定义错误提示
     *
     * @return
     */
    public static RestResponse error(String msg) {
        RestResponse restReponse = new RestResponse();
        restReponse.setCode(ErrorCode.COMMON_ERROR);
        restReponse.setMsg(msg);
        return restReponse;
    }

    /**
     * 自定义错误代码，自定义错误提示
     *
     * @param code
     * @param msg
     * @return
     */
    public static RestResponse error(int code, String msg) {
        RestResponse restReponse = new RestResponse();
        restReponse.setCode(code);
        restReponse.setMsg(msg);
        return restReponse;
    }
}
