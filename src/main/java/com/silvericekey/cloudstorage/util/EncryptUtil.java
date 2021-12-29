package com.silvericekey.cloudstorage.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.silvericekey.cloudstorage.common.Constants;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author SilverIceKey
 * @title: EncryptUtil
 * @description: 加密工具类
 * @date 2021/12/2811:24
 */
public class EncryptUtil {
    /**
     * AES加密(ECB_PKCD5Padding)
     *
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, Constants.ENCRYPTKEY.getBytes(StandardCharsets.UTF_8));
        return aes.encryptBase64(content);
    }

    /**
     * AES解密(ECB_PKCD5Padding)
     *
     * @param content
     * @return
     */
    public static String decrypt(String content) {
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, Constants.ENCRYPTKEY.getBytes(StandardCharsets.UTF_8));
        return aes.decryptStr(content);
    }

    /**
     * md5字符串
     *
     * @param content
     * @return
     */
    public static String md5(String content) {
        return SecureUtil.md5(content);
    }

    /**
     * 获取文件MD5
     *
     * @param file
     * @return
     */
    public static String md5(File file) {
        return SecureUtil.md5(file);
    }
}
