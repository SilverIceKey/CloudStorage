package com.silvericekey.cloudstorage.features.file.model;

import lombok.Data;

/**
 * @author SilverIceKey
 * @title: MoveFilesVo
 * @date 2022/3/169:27
 */
@Data
public class MoveFileVo {
    private String fileId;
    private Long folderId;
}
