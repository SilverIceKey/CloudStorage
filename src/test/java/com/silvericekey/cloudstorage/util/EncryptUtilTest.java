package com.silvericekey.cloudstorage.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SilverIceKey
 * @title: EncryptUtilTest
 * @description: 加密测试
 * @date 2021/12/2813:57
 */
class EncryptUtilTest {

    @Test
    void encrypt() {
        assertEquals(EncryptUtil.encrypt("123456"),"kvepcuu+yM2srPoYFjGySQ==");
    }

    @Test
    void decrypt() {
        assertEquals(EncryptUtil.decrypt("kvepcuu+yM2srPoYFjGySQ=="),"123456");
    }

    @Test
    void md5() {
        assertEquals(EncryptUtil.md5("123456"),"e10adc3949ba59abbe56e057f20f883e");
    }

    @Test
    void testMd5() {
        assertEquals(EncryptUtil.md5(new File("E:\\project\\涂鸦设备信息.txt")).toUpperCase(),"B9BC7F7C21760348D7B04C27A7ACED7B");
    }
}