package com.silvericekey.cloudstorage.features.folder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import com.silvericekey.cloudstorage.features.folder.mapper.FolderInfoMapper;
import com.silvericekey.cloudstorage.features.folder.model.CreateFolderVo;
import com.silvericekey.cloudstorage.features.folder.service.FolderService;
import com.silvericekey.cloudstorage.util.RestUtil;
import org.springframework.stereotype.Service;

/**
 * @author SilverIceKey
 * @title: FolderServiceImpl
 * @date 2022/3/1514:10
 */
@Service
public class FolderServiceImpl extends ServiceImpl<FolderInfoMapper, FolderInfo> implements FolderService {

    @Override
    public RestResponse createFolder(CreateFolderVo createFolderVo) {
        QueryWrapper<FolderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("folder_name", createFolderVo.getFolderName())
                .eq("folder_parent_id", createFolderVo.getFolderParentId())
                .eq("user_id", createFolderVo.getUserId());
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
}
