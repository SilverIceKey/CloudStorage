package com.silvericekey.cloudstorage.features.folder.model;

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

    /**
     * 用户id
     */
    private Long userId;
}
