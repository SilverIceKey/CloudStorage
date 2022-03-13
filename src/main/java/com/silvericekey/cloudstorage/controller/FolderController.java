package com.silvericekey.cloudstorage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.silvericekey.cloudstorage.dao.mapper.FolderInfoMapper;
import com.silvericekey.cloudstorage.entity.FolderInfo;
import com.silvericekey.cloudstorage.model.CreateFolderVo;
import com.silvericekey.cloudstorage.model.RestResponse;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件夹操作
 */
@Slf4j
@RestController
@RequestMapping("/folder")
public class FolderController extends BaseController{

    @Autowired
    FolderInfoMapper folderInfoMapper;

    /**
     * 创建文件夹
     *
     * @param createFolderVo
     * @return
     */
    @PostMapping("createFolder")
    public RestResponse createFolder(@RequestBody CreateFolderVo createFolderVo) {
        QueryWrapper<FolderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("folder_name", createFolderVo.getFolderName())
                .eq("folder_parent_id", createFolderVo.getFolderParentId())
                .eq("user_id", getUser().getId());
        FolderInfo folderInfo = folderInfoMapper.selectOne(queryWrapper);
        if (folderInfo != null) {
            return RestUtil.error("文件夹已存在");
        }
        folderInfo = new FolderInfo();
        folderInfo.setFolderName(createFolderVo.getFolderName());
        folderInfo.setFolderParentId(createFolderVo.getFolderParentId());
        folderInfo.setUserId(getUser().getId());
        folderInfoMapper.insert(folderInfo);
        return RestUtil.ok("文件夹创建成功", folderInfo);
    }

}
