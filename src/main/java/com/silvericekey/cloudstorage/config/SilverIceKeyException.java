package com.silvericekey.cloudstorage.config;


/**
 * 自定义异常类
 * @author myq
 */
public class SilverIceKeyException extends RuntimeException {

    public SilverIceKeyException() {
        super();
    }

    public SilverIceKeyException(String message) {
        super(message);
    }

    public SilverIceKeyException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
