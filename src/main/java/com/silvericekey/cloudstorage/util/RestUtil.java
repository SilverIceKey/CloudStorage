package com.silvericekey.cloudstorage.util;

import com.silvericekey.cloudstorage.common.ErrorCode;
import com.silvericekey.cloudstorage.model.RestReponse;

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
    public static RestReponse ok() {
        return ok(null,null);
    }

    /**
     *
     * 正常返回带字符串
     * @param msg
     * @return
     */
    public static RestReponse ok(String msg) {
        return ok(msg,null);
    }

    /**
     * 正常返回带数据
     *
     * @param data
     * @return
     */
    public static RestReponse ok(Object data) {
        return ok(null,data);
    }

    /**
     * 正常返回带字符串和数据
     * @param msg
     * @return
     */
    public static RestReponse ok(String msg, Object data) {
        RestReponse restReponse = new RestReponse();
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
    public static RestReponse error() {
        RestReponse restReponse = new RestReponse();
        restReponse.setCode(ErrorCode.COMMON_ERROR);
        restReponse.setMsg("数据处理异常");
        return restReponse;
    }

    /**
     * 通用错误返回，自定义错误提示
     *
     * @return
     */
    public static RestReponse error(String msg) {
        RestReponse restReponse = new RestReponse();
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
    public static RestReponse error(int code, String msg) {
        RestReponse restReponse = new RestReponse();
        restReponse.setCode(code);
        restReponse.setMsg(msg);
        return restReponse;
    }
}
