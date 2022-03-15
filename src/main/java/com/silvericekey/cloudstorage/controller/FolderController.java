package com.silvericekey.cloudstorage.controller;

import com.silvericekey.cloudstorage.base.BaseController;
import com.silvericekey.cloudstorage.features.folder.model.CreateFolderVo;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件夹操作
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/folder")
public class FolderController extends BaseController {
    private final FolderService folderService;

    /**
     * 创建文件夹
     *
     * @param createFolderVo
     * @return
     */
    @PostMapping("createFolder")
    public RestResponse createFolder(@RequestBody CreateFolderVo createFolderVo) {
        if (!createFolderVo.getFolderName().endsWith("/")){
            createFolderVo.setFolderName(createFolderVo.getFolderName()+"/");
        }
        createFolderVo.setUserId(getUser().getId());
        return folderService.createFolder(createFolderVo);
    }

}
