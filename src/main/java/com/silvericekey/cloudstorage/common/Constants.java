package com.silvericekey.cloudstorage.common;

/**
 * @author SilverIceKey
 * 常量配置
 */
public class Constants {
    /**
     * 加密密钥(请自行替换UUID)
     */
    public static final String ENCRYPTKEY = "971f40b2a1985a6589b2f0dec93538ae";

    /**
     * token过期时间
     */
    public static final int TOKEN_EXP = 1000 * 60 * 60 * 24;

    /**
     * 文件保存路径
     */
    public static final String FILE_SAVE_PATH = "./upload/";

}
