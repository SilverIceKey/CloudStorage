package com.silvericekey.cloudstorage.features.folder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silvericekey.cloudstorage.base.RestResponse;
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
    RestResponse createFolder(CreateFolderVo createFolderVo);

    boolean moveFolder(MoveFolderVo moveFolderVo);

    RestResponse renameFolder(RenameFolderVo renameFolderVo);

    boolean deleteFolder(String folderId);
}
