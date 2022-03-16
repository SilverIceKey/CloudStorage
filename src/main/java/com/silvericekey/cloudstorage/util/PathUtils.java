package com.silvericekey.cloudstorage.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import com.silvericekey.cloudstorage.features.folder.service.FolderService;
import lombok.RequiredArgsConstructor;

/**
 * @author SilverIceKey
 * @title: PathUtils
 * @date 2022/3/1611:52
 */
public class PathUtils {
    /**
     * 获取文件夹路径
     *
     * @param folderInfo
     * @return
     */
    public static String getPath(FolderInfo folderInfo, FolderService folderService) {
        StringBuilder moveToPath = new StringBuilder(folderInfo.getFolderName());
        while (folderInfo.getFolderParentId() != 0) {
            QueryWrapper<FolderInfo> folderInfoQueryWrapper = new QueryWrapper<>();
            folderInfoQueryWrapper.eq(FolderInfo.ID, folderInfo.getFolderParentId());
            folderInfo = folderService.getOne(folderInfoQueryWrapper);
            moveToPath.insert(0, folderInfo.getFolderName());
        }
        moveToPath.deleteCharAt(0);
        return moveToPath.toString();
    }
}
