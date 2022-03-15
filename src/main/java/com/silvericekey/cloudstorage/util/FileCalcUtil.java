package com.silvericekey.cloudstorage.util;

/**
 * 文件工具
 */
public class FileCalcUtil {

    private static String[] fileSizeUnit = {"B","KB","MB","GB","TB"};

    /**
     * 计算文件大小
     * @param fileSize
     * @return
     */
    public static String calcFileSize(Long fileSize){
        float tmpFileSize = fileSize;
        for (int i = 0; i < fileSizeUnit.length; i++) {
            tmpFileSize = tmpFileSize/1024f;
            if (tmpFileSize<1){
                return String.format("%.2f", tmpFileSize*1024f) +fileSizeUnit[i];
            }
        }
        return "未知大小";
    }
}
