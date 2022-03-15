package com.silvericekey.cloudstorage.features.file.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.common.Constants;
import com.silvericekey.cloudstorage.features.file.entity.FileInfo;
import com.silvericekey.cloudstorage.features.file.mapper.FileInfoMapper;
import com.silvericekey.cloudstorage.features.file.model.FileListVo;
import com.silvericekey.cloudstorage.features.file.model.FileUploadVo;
import com.silvericekey.cloudstorage.features.file.service.IFileService;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import com.silvericekey.cloudstorage.features.folder.service.IFolderService;
import com.silvericekey.cloudstorage.util.FileCalcUtil;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author SilverIceKey
 * @title: FileServiceImpl
 * @date 2022/3/1514:11
 */
@RequiredArgsConstructor
public class FileServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileService {
    private final IFolderService folderService;
    private final IFileService fileService;

    @Override
    public RestResponse getFileList(FileListVo fileListVo) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", fileListVo.getUserId());
        queryWrapper.eq("folder_id", fileListVo.getFolderId());
        List<FileInfo> fileInfoList = getBaseMapper().selectList(queryWrapper);
        fileInfoList.forEach(fileInfo -> fileInfo.setFileSizeStr(FileCalcUtil.calcFileSize(fileInfo.getFileSize())));
        QueryWrapper<FolderInfo> folderInfoQueryWrapper = new QueryWrapper<>();
        folderInfoQueryWrapper.eq("folder_parent_id", fileListVo.getFolderId());
        List<FolderInfo> folderInfos = folderService.getBaseMapper().selectList(folderInfoQueryWrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fileList", fileInfoList);
        hashMap.put("folderList", folderInfos);
        return RestUtil.ok(hashMap);
    }

    @Override
    public RestResponse uploadFile(FileUploadVo fileUploadVo) throws IOException {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FileInfo.FILE_MD5, fileUploadVo.getFileMD5().toUpperCase());
        FileInfo fileInfo = getBaseMapper().selectOne(queryWrapper);
        QueryWrapper<FolderInfo> folderInfoQueryWrapper = new QueryWrapper<>();
        folderInfoQueryWrapper.eq(FolderInfo.ID, fileUploadVo.getFolderId());
        FolderInfo folderInfo = folderService.getOne(folderInfoQueryWrapper);
        if (fileInfo != null) {
            return RestUtil.ok("极速上传成功", fileInfo);
        } else {
            OutputStream os = FileUtil.getOutputStream(Constants.FILE_SAVE_PATH + folderInfo.getFolderName() + fileUploadVo.getMultipartFile().getOriginalFilename());
            InputStream is = fileUploadVo.getMultipartFile().getInputStream();
            int readData = -1;
            while ((readData = is.read()) != -1) {
                os.write(readData);
            }
            os.close();
            is.close();
            fileInfo = new FileInfo();
            fileInfo.setFileMD5(fileUploadVo.getFileMD5().toUpperCase());
            fileInfo.setFileName(fileUploadVo.getMultipartFile().getOriginalFilename());
            fileInfo.setFilePath(Constants.FILE_SAVE_PATH + folderInfo.getFolderName() + fileUploadVo.getMultipartFile().getOriginalFilename());
            fileInfo.setFileSize(fileUploadVo.getMultipartFile().getSize());
            fileInfo.setFileDownloadPath(Constants.URL_PREFIX + folderInfo.getFolderName() + fileUploadVo.getMultipartFile().getOriginalFilename());
            fileInfo.setFolderId(fileUploadVo.getFolderId());
            fileInfo.setUserId(fileUploadVo.getUserId());
            fileService.getBaseMapper().insert(fileInfo);
            return RestUtil.ok("上传成功", fileInfo);
        }
    }
}
