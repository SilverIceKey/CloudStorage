package com.silvericekey.cloudstorage.features.folder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.file.service.FileService;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import com.silvericekey.cloudstorage.features.folder.model.CreateFolderVo;
import com.silvericekey.cloudstorage.features.folder.model.MoveFolderVo;
import com.silvericekey.cloudstorage.features.folder.model.MoveFoldersVo;
import com.silvericekey.cloudstorage.features.folder.model.RenameFolderVo;

/**
 * @author SilverIceKey
 * @title: FolderService
 * @date 2022/3/1513:06
 */
public interface FolderService extends IService<FolderInfo> {
    /**
     * 创建文件夹
     * @param createFolderVo
     * @return
     */
    RestResponse createFolder(CreateFolderVo createFolderVo);

    /**
     * 移动文件夹
     * @param moveFolderVo
     * @param fileService
     * @return
     */
    boolean moveFolder(MoveFolderVo moveFolderVo, FileService fileService);

    /**
     * 重命名文件夹
     * @param renameFolderVo
     * @param fileService
     * @return
     */
    RestResponse renameFolder(RenameFolderVo renameFolderVo, FileService fileService);

    /**
     * 删除文件夹
     * @param folderId
     * @param fileService
     * @return
     */
    boolean deleteFolder(String folderId, FileService fileService);
}
