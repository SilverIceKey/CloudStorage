package com.silvericekey.cloudstorage.features.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.file.entity.FileInfo;
import com.silvericekey.cloudstorage.features.file.model.*;

import java.io.IOException;

/**
 * @author SilverIceKey
 * @title: FilesService
 * @date 2022/3/1513:05
 */
public interface FileService extends IService<FileInfo> {
    RestResponse getFileList(FileListVo fileListVo);

    RestResponse uploadFile(FileUploadVo fileUploadVo) throws IOException;

    boolean deleteFile(String fileId);

    RestResponse renameFile(RenameFileVo renameFileVo);

    boolean MoveFile(MoveFileVo moveFilesVo);
}
