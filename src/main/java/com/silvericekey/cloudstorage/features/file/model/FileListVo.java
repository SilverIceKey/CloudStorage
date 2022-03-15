package com.silvericekey.cloudstorage.features.file.model;

import lombok.Data;

/**
 * 获取文件列表请求
 */
@Data
public class FileListVo {
    /**
     * 文件夹id
     */
    private Integer folderId = 0;

    /**
     * 用户id
     */
    private Integer userId;
}
