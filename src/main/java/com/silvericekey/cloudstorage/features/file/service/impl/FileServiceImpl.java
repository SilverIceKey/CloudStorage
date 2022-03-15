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
import com.silvericekey.cloudstorage.features.file.service.FileService;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import com.silvericekey.cloudstorage.features.folder.service.FolderService;
import com.silvericekey.cloudstorage.util.FileCalcUtil;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @author SilverIceKey
 * @title: FileServiceImpl
 * @date 2022/3/1514:11
 */
@RequiredArgsConstructor
@Service
public class FileServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileService {
    private final FolderService folderService;

    @Override
    public RestResponse getFileList(FileListVo fileListVo) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FileInfo.USER_ID, fileListVo.getUserId());
        queryWrapper.eq(FileInfo.FOLDER_ID, fileListVo.getFolderId());
        List<FileInfo> fileInfoList = getBaseMapper().selectList(queryWrapper);
        fileInfoList.forEach(fileInfo -> fileInfo.setFileSizeStr(FileCalcUtil.calcFileSize(fileInfo.getFileSize())));
        QueryWrapper<FolderInfo> folderInfoQueryWrapper = new QueryWrapper<>();
        folderInfoQueryWrapper.eq(FolderInfo.FOLDER_PARENT_ID, fileListVo.getFolderId());
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
            getBaseMapper().insert(fileInfo);
            return RestUtil.ok("上传成功", fileInfo);
        }
    }
}
