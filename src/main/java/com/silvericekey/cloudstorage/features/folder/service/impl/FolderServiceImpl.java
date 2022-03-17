package com.silvericekey.cloudstorage.features.folder.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.file.entity.FileInfo;
import com.silvericekey.cloudstorage.features.file.service.FileService;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import com.silvericekey.cloudstorage.features.folder.mapper.FolderInfoMapper;
import com.silvericekey.cloudstorage.features.folder.model.CreateFolderVo;
import com.silvericekey.cloudstorage.features.folder.model.MoveFolderVo;
import com.silvericekey.cloudstorage.features.folder.model.RenameFolderVo;
import com.silvericekey.cloudstorage.features.folder.service.FolderService;
import com.silvericekey.cloudstorage.util.PathUtils;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;

/**
 * @author SilverIceKey
 * @title: FolderServiceImpl
 * @date 2022/3/1514:10
 */
@RequiredArgsConstructor
@Service
public class FolderServiceImpl extends ServiceImpl<FolderInfoMapper, FolderInfo> implements FolderService {

    @Override
    public RestResponse createFolder(CreateFolderVo createFolderVo) {
        QueryWrapper<FolderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FolderInfo.FOLDER_NAME, createFolderVo.getFolderName())
                .eq(FolderInfo.FOLDER_PARENT_ID, createFolderVo.getFolderParentId())
                .eq(FolderInfo.USER_ID, createFolderVo.getUserId());
        FolderInfo folderInfo = getBaseMapper().selectOne(queryWrapper);
        if (folderInfo != null) {
            return RestUtil.error("文件夹已存在");
        }
        folderInfo = new FolderInfo();
        folderInfo.setFolderName(createFolderVo.getFolderName());
        folderInfo.setFolderParentId(createFolderVo.getFolderParentId());
        folderInfo.setUserId(createFolderVo.getUserId());
        getBaseMapper().insert(folderInfo);
        return RestUtil.ok("文件夹创建成功", folderInfo);
    }

    @Override
    public boolean moveFolder(MoveFolderVo moveFolderVo, FileService fileService) {
        QueryWrapper<FolderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FolderInfo.ID, moveFolderVo.getFolderId());
        FolderInfo folderInfo = getBaseMapper().selectOne(queryWrapper);
        if (folderInfo == null) {
            return false;
        }
        QueryWrapper<FolderInfo> queryParentWrapper = new QueryWrapper<>();
        queryParentWrapper.eq(FolderInfo.ID, moveFolderVo.getParentFolderId());
        FolderInfo parentFolderInfo = getBaseMapper().selectOne(queryWrapper);
        if (parentFolderInfo == null) {
            return false;
        }
        Long userId = folderInfo.getUserId();
        QueryWrapper<FileInfo> fileInfoQueryWrapper = new QueryWrapper<>();
        fileInfoQueryWrapper.eq(FileInfo.USER_ID, userId).eq(FileInfo.FOLDER_ID, moveFolderVo.getFolderId());
        List<FileInfo> fileInfoList = fileService.list(fileInfoQueryWrapper);
        String sourcePath = PathUtils.getPath(folderInfo, this);
        folderInfo.setFolderParentId(Long.valueOf(moveFolderVo.getParentFolderId()));
        String targetPath = PathUtils.getPath(folderInfo, this);
        FileUtil.move(Paths.get(sourcePath), Paths.get(targetPath), true);
        for (FileInfo fileInfo : fileInfoList) {
            fileInfo.setFilePath(fileInfo.getFilePath().replace(sourcePath, targetPath));
            fileInfo.setFileDownloadPath(fileInfo.getFileDownloadPath().replace(sourcePath, targetPath));
            fileService.updateById(fileInfo);
        }
        this.updateById(folderInfo);
        return true;
    }

    @Override
    public RestResponse renameFolder(RenameFolderVo renameFolderVo,FileService fileService) {
        QueryWrapper<FolderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FolderInfo.ID, renameFolderVo.getFolderId());
        FolderInfo folderInfo = getBaseMapper().selectOne(queryWrapper);
        if (folderInfo == null) {
            return RestUtil.error("文件夹不存在");
        }
        Long userId = folderInfo.getUserId();
        QueryWrapper<FileInfo> fileInfoQueryWrapper = new QueryWrapper<>();
        fileInfoQueryWrapper.eq(FileInfo.USER_ID, userId).eq(FileInfo.FOLDER_ID, renameFolderVo.getFolderId());
        List<FileInfo> fileInfoList = fileService.list(fileInfoQueryWrapper);
        for (FileInfo fileInfo : fileInfoList) {
            fileInfo.setFilePath(fileInfo.getFilePath().replace(folderInfo.getFolderName(), renameFolderVo.getFolderName()));
            fileInfo.setFileDownloadPath(fileInfo.getFileDownloadPath().replace(folderInfo.getFolderName(), renameFolderVo.getFolderName()));
            fileService.updateById(fileInfo);
        }
        String folderPath = PathUtils.getPath(folderInfo, this);
        FileUtil.move(Paths.get(folderPath), Paths.get(folderPath.replace(folderInfo.getFolderName(), renameFolderVo.getFolderName())), true);
        folderInfo.setFolderName(renameFolderVo.getFolderName());
        updateById(folderInfo);
        return RestUtil.ok("重命名成功");
    }

    @Override
    public boolean deleteFolder(String folderId,FileService fileService) {
        QueryWrapper<FolderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FolderInfo.ID, folderId);
        FolderInfo folderInfo = getBaseMapper().selectOne(queryWrapper);
        if (folderInfo == null) {
            return false;
        }
        Long userId = folderInfo.getUserId();
        QueryWrapper<FileInfo> fileInfoQueryWrapper = new QueryWrapper<>();
        fileInfoQueryWrapper.eq(FileInfo.USER_ID, userId).eq(FileInfo.FOLDER_ID, folderId);
        List<FileInfo> fileInfoList = fileService.list(fileInfoQueryWrapper);
        for (FileInfo fileInfo:fileInfoList) {
            fileService.getBaseMapper().deleteById(fileInfo);
        }
        FileUtil.del(PathUtils.getPath(folderInfo,this));
        return true;
    }
}
