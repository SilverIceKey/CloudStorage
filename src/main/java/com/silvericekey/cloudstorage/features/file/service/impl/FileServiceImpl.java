package com.silvericekey.cloudstorage.features.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.common.Constants;
import com.silvericekey.cloudstorage.features.file.entity.FileInfo;
import com.silvericekey.cloudstorage.features.file.mapper.FileInfoMapper;
import com.silvericekey.cloudstorage.features.file.model.FileListVo;
import com.silvericekey.cloudstorage.features.file.model.FileUploadVo;
import com.silvericekey.cloudstorage.features.file.model.MoveFilesVo;
import com.silvericekey.cloudstorage.features.file.model.RenameFileVo;
import com.silvericekey.cloudstorage.features.file.service.FileService;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import com.silvericekey.cloudstorage.features.folder.service.FolderService;
import com.silvericekey.cloudstorage.util.FileCalcUtil;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
        if (folderInfo == null) {
            return RestUtil.error("文件夹不存在");
        }
        StringBuilder moveToPath = new StringBuilder(folderInfo.getFolderName());
        while (folderInfo.getFolderParentId() != 0) {
            folderInfoQueryWrapper = new QueryWrapper<>();
            folderInfoQueryWrapper.eq(FolderInfo.ID, folderInfo.getFolderParentId());
            folderInfo = folderService.getOne(folderInfoQueryWrapper);
            moveToPath.insert(0, folderInfo.getFolderName());
        }
        moveToPath.deleteCharAt(0);
        if (fileInfo != null) {
            if (fileInfo.getUserId() == fileUploadVo.getUserId()) {
                return RestUtil.ok("文件已存在", fileInfo);
            } else {
                FileInfo tmpFileInfo = new FileInfo();
                BeanUtil.copyProperties(fileInfo, tmpFileInfo, FileInfo.ID);
                tmpFileInfo.setFileName(fileUploadVo.getMultipartFile().getOriginalFilename());
                tmpFileInfo.setUserId(fileUploadVo.getUserId());
                tmpFileInfo.setFolderId(fileUploadVo.getFolderId());
                getBaseMapper().insert(tmpFileInfo);
                return RestUtil.ok("极速上传成功", tmpFileInfo);
            }
        } else {
            OutputStream os = FileUtil.getOutputStream(Constants.FILE_SAVE_PATH + moveToPath + fileUploadVo.getMultipartFile().getOriginalFilename());
            InputStream is = fileUploadVo.getMultipartFile().getInputStream();
            FileCopyUtils.copy(is, os);
            os.close();
            is.close();
            fileInfo = new FileInfo();
            fileInfo.setFileMD5(fileUploadVo.getFileMD5().toUpperCase());
            fileInfo.setFileName(fileUploadVo.getMultipartFile().getOriginalFilename());
            fileInfo.setFilePath(Constants.FILE_SAVE_PATH + moveToPath + fileUploadVo.getMultipartFile().getOriginalFilename());
            fileInfo.setFileSize(fileUploadVo.getMultipartFile().getSize());
            fileInfo.setFileDownloadPath(Constants.URL_PREFIX + moveToPath + fileUploadVo.getMultipartFile().getOriginalFilename());
            fileInfo.setFolderId(fileUploadVo.getFolderId());
            fileInfo.setFileSizeStr(FileCalcUtil.calcFileSize(fileInfo.getFileSize()));
            fileInfo.setUserId(fileUploadVo.getUserId());
            getBaseMapper().insert(fileInfo);
            return RestUtil.ok("上传成功", fileInfo);
        }
    }

    @Override
    public RestResponse deleteFile(String fileId) {
        QueryWrapper<FileInfo> fileInfoQueryWrapper = new QueryWrapper<>();
        fileInfoQueryWrapper.eq(FileInfo.ID, fileId);
        FileInfo queryFile = getBaseMapper().selectOne(fileInfoQueryWrapper);
        if (queryFile==null){
            return RestUtil.error("文件不存在");
        }
        QueryWrapper<FileInfo> sameMD5FilesQuery = new QueryWrapper<>();
        sameMD5FilesQuery.eq(FileInfo.FILE_MD5, queryFile.getFileMD5());
        List<FileInfo> sameMD5Files = getBaseMapper().selectList(sameMD5FilesQuery);
        if (sameMD5Files.size() == 1) {
            FileUtil.del(queryFile.getFilePath());
        }
        getBaseMapper().deleteById(queryFile);
        return RestUtil.ok("删除成功");
    }

    @Override
    public RestResponse renameFile(RenameFileVo renameFileVo) {
        QueryWrapper<FileInfo> fileInfoQueryWrapper = new QueryWrapper<>();
        fileInfoQueryWrapper.eq(FileInfo.ID, renameFileVo.getFileId());
        FileInfo fileInfo = getBaseMapper().selectOne(fileInfoQueryWrapper);
        if (fileInfo == null) {
            return RestUtil.error("文件不存在");
        }
        FileUtil.rename(FileUtil.file(fileInfo.getFileName()), renameFileVo.getFileName(), true);
        fileInfo.setFileName(renameFileVo.getFileName());
        fileInfo.setFilePath(fileInfo.getFilePath().replace(fileInfo.getFileName(), renameFileVo.getFileName()));
        fileInfo.setFileDownloadPath(fileInfo.getFileDownloadPath().replace(fileInfo.getFileName(), renameFileVo.getFileName()));
        fileInfo.setFileName(renameFileVo.getFileName());
        getBaseMapper().updateById(fileInfo);
        return RestUtil.ok("修改成功");
    }

    @Override
    public RestResponse MoveFiles(MoveFilesVo moveFilesVo) {
        List<FileInfo> fileInfoList = new ArrayList<>();
        for (int i = 0; i < moveFilesVo.getFileIds().length; i++) {
            QueryWrapper<FileInfo> fileInfoQueryWrapper = new QueryWrapper<>();
            fileInfoQueryWrapper.eq(FileInfo.ID, moveFilesVo.getFileIds()[i]);
            fileInfoList.add(getBaseMapper().selectOne(fileInfoQueryWrapper));
        }
        if (fileInfoList.size() == 0) {
            return RestUtil.error("文件不存在");
        }
        QueryWrapper<FolderInfo> folderInfoQueryWrapper = new QueryWrapper<>();
        folderInfoQueryWrapper.eq(FolderInfo.ID, moveFilesVo.getFolderId());
        FolderInfo folderInfo = folderService.getOne(folderInfoQueryWrapper);
        StringBuilder moveToPath = new StringBuilder(folderInfo.getFolderName());
        while (folderInfo.getFolderParentId() != 0) {
            folderInfoQueryWrapper = new QueryWrapper<>();
            folderInfoQueryWrapper.eq(FolderInfo.ID, folderInfo.getFolderParentId());
            folderInfo = folderService.getOne(folderInfoQueryWrapper);
            moveToPath.insert(0, folderInfo.getFolderName());
        }
        moveToPath.deleteCharAt(0);
        for (int i = 0; i < fileInfoList.size(); i++) {
            File sourceFile = FileUtil.file(fileInfoList.get(i).getFilePath());
            File targetFile = FileUtil.file(Constants.FILE_SAVE_PATH + moveToPath + fileInfoList.get(i).getFileName());
            FileUtil.move(sourceFile, targetFile, true);
            fileInfoList.get(i).setFilePath(targetFile.getAbsolutePath());
            fileInfoList.get(i).setFileDownloadPath(Constants.URL_PREFIX + targetFile.getAbsolutePath());
            getBaseMapper().updateById(fileInfoList.get(i));
        }
        return RestUtil.ok("移动成功");
    }
}
