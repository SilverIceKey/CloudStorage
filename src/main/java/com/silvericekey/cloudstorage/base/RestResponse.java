package com.silvericekey.cloudstorage.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.silvericekey.cloudstorage.common.ErrorCode;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.Data;

/**
 * @author SilverIceKey
 * @title: BaseReponse
 * @date 2021/12/2817:04
 */
@Data
public class RestResponse {
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

    /**
     * 是否成功
     * @return
     */
    @JsonIgnore
    public boolean isOk(){
        return code == ErrorCode.OK;
    }
}
