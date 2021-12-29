package com.silvericekey.cloudstorage.model;

import lombok.Data;

/**
 * @author SilverIceKey
 * @title: BaseReponse
 * @date 2021/12/2817:04
 */
@Data
public class RestReponse {
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 携带数据
     */
    private Object data;
}
