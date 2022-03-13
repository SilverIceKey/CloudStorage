package com.silvericekey.cloudstorage.model;

import lombok.Data;

/**
 * 创建文件夹请求
 */
@Data
public class CreateFolderVo {
    /**
     * 父文件夹id
     */
    private Long folderParentId;
    /**
     * 文件夹名称
     */
    private String folderName;
}
