package com.silvericekey.cloudstorage.features.folder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.silvericekey.cloudstorage.base.BaseController;
import com.silvericekey.cloudstorage.features.folder.mapper.FolderInfoMapper;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import com.silvericekey.cloudstorage.features.folder.model.CreateFolderVo;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.folder.service.IFolderService;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FolderController extends BaseController {
    private final IFolderService folderService;

    /**
     * 创建文件夹
     *
     * @param createFolderVo
     * @return
     */
    @PostMapping("createFolder")
    public RestResponse createFolder(@RequestBody CreateFolderVo createFolderVo) {
        createFolderVo.setUserId(getUser().getId());
        return folderService.createFolder(createFolderVo);
    }

}
