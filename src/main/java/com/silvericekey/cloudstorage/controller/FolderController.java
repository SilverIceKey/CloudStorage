package com.silvericekey.cloudstorage.controller;

import com.silvericekey.cloudstorage.base.BaseController;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.features.file.service.FileService;
import com.silvericekey.cloudstorage.features.folder.model.CreateFolderVo;
import com.silvericekey.cloudstorage.features.folder.model.MoveFolderVo;
import com.silvericekey.cloudstorage.features.folder.model.MoveFoldersVo;
import com.silvericekey.cloudstorage.features.folder.model.RenameFolderVo;
import com.silvericekey.cloudstorage.features.folder.service.FolderService;
import com.silvericekey.cloudstorage.util.RestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 文件夹操作
 */
@Slf4j
@Api(tags = "文件夹操作")
@RequiredArgsConstructor
@RestController
@RequestMapping("/folder")
public class FolderController extends BaseController {
    private final FolderService folderService;
    private final FileService fileService;

    /**
     * 创建文件夹
     *
     * @param createFolderVo
     * @return
     */
    @ApiOperation("创建文件夹")
    @PostMapping("createFolder")
    public RestResponse createFolder(@RequestBody CreateFolderVo createFolderVo) {
        if (!createFolderVo.getFolderName().endsWith("/")) {
            createFolderVo.setFolderName(createFolderVo.getFolderName() + "/");
        }
        createFolderVo.setUserId(getUser().getId());
        return folderService.createFolder(createFolderVo);
    }

    /**
     * 删除多个文件夹
     *
     * @param folderIds
     * @return
     */
    @ApiOperation("删除多个文件夹")
    @GetMapping("deleteFolders")
    public RestResponse deleteFolders(String[] folderIds) {
        boolean isAllFolderDelete = true;
        int deleteFolderNum = 0;
        for (int i = 0; i < folderIds.length; i++) {
            if (!folderService.deleteFolder(folderIds[i],fileService)) {
                isAllFolderDelete = false;
                deleteFolderNum--;
            }
            deleteFolderNum++;
        }
        return RestUtil.ok(isAllFolderDelete ? "全部删除成功" : "删除" + deleteFolderNum + "个文件夹成功");
    }

    /**
     * 删除单个文件夹
     *
     * @param folderId
     * @return
     */
    @ApiOperation("删除单个文件夹")
    @GetMapping("deleteFolder")
    public RestResponse deleteFolder(String folderId) {
        boolean isDelete = folderService.deleteFolder(folderId,fileService);
        return isDelete ? RestUtil.ok("删除文件夹成功") : RestUtil.error("删除文件夹失败");
    }

    /**
     * 重命名文件夹
     *
     * @param renameFolderVo
     * @return
     */
    @ApiOperation("重命名文件夹")
    @PostMapping("renameFolder")
    public RestResponse renameFolder(@RequestBody RenameFolderVo renameFolderVo) {
        return folderService.renameFolder(renameFolderVo,fileService);
    }

    /**
     * 移动单个文件夹
     *
     * @param moveFolderVo
     * @return
     */
    @ApiOperation("移动单个文件夹")
    @PostMapping("moveFolder")
    public RestResponse moveFolder(@RequestBody MoveFolderVo moveFolderVo) {
        boolean isMoveed = folderService.moveFolder(moveFolderVo,fileService);
        return isMoveed ? RestUtil.ok("移动成功") : RestUtil.error("移动失败");
    }

    /**
     * 移动多个文件夹
     *
     * @param moveFoldersVo
     * @return
     */
    @ApiOperation("移动多个文件夹")
    @PostMapping("moveFolders")
    public RestResponse moveFolders(@RequestBody MoveFoldersVo moveFoldersVo) {
        boolean isAllFolderMove = true;
        int moveFolderNum = 0;
        for (int i = 0; i < moveFoldersVo.getFolderIds().length; i++) {
            MoveFolderVo moveFolderVo = new MoveFolderVo();
            moveFolderVo.setFolderId(moveFoldersVo.getFolderIds()[i]);
            moveFolderVo.setParentFolderId(moveFoldersVo.getParentFolderId());
            if (!folderService.moveFolder(moveFolderVo,fileService)) {
                isAllFolderMove = false;
                moveFolderNum--;
            }
            moveFolderNum++;
        }
        return RestUtil.ok(isAllFolderMove ? "全部移动成功" : "移动" + moveFolderNum + "个文件夹成功");
    }

}
