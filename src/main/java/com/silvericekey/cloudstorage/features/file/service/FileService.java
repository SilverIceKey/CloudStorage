package com.silvericekey.cloudstorage.features.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.file.entity.FileInfo;
import com.silvericekey.cloudstorage.features.file.model.*;
import com.silvericekey.cloudstorage.features.folder.service.FolderService;

import java.io.IOException;

/**
 * @author SilverIceKey
 * @title: FilesService
 * @date 2022/3/1513:05
 */
public interface FileService extends IService<FileInfo> {
    /**
     * 获取文件列表
     *
     * @param fileListVo
     * @param folderService
     * @return
     */
    RestResponse getFileList(FileListVo fileListVo, FolderService folderService);

    /**
     * 上传文件
     *
     * @param fileUploadVo
     * @param folderService
     * @return
     * @throws IOException
     */
    RestResponse uploadFile(FileUploadVo fileUploadVo, FolderService folderService) throws IOException;

    /**
     * 删除文件
     *
     * @param fileId
     * @return
     */
    boolean deleteFile(String fileId);

    /**
     * 重命名文件
     *
     * @param renameFileVo
     * @return
     */
    RestResponse renameFile(RenameFileVo renameFileVo);

    /**
     * 移动文件
     *
     * @param moveFilesVo
     * @param folderService
     * @return
     */
    boolean MoveFile(MoveFileVo moveFilesVo, FolderService folderService);
}
