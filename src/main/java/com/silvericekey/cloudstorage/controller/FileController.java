package com.silvericekey.cloudstorage.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.silvericekey.cloudstorage.base.BaseController;
import com.silvericekey.cloudstorage.features.file.model.*;
import com.silvericekey.cloudstorage.features.file.service.FileService;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {
    private final FileService fileService;

    /**
     * 获取文件列表
     *
     * @return
     */
    @GetMapping(path = "/getList")
    public RestResponse getFileList(@RequestBody FileListVo fileListVo) {
        fileListVo.setUserId(getUser().getId());
        if (fileListVo.getFolderId() == null) {
            fileListVo.setFolderId(0L);
        }
        return fileService.getFileList(fileListVo);
    }

    /**
     * 文件上传，通过判断md5避免重复文件
     *
     * @param folderId
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping(path = "/uploadFile")
    public RestResponse uploadFile(@RequestPart(value = "file") MultipartFile multipartFile,
                                   @RequestParam("folderId") Long folderId
    ) throws IOException {
        FileUploadVo fileUploadVo = new FileUploadVo();
        String filemd5 = MD5.create().digestHex(multipartFile.getInputStream()).toUpperCase();
        fileUploadVo.setFileMD5(filemd5);
        fileUploadVo.setFolderId(folderId);
        fileUploadVo.setUserId(getUser().getId());
        fileUploadVo.setMultipartFile(multipartFile);
        return fileService.uploadFile(fileUploadVo);
    }

    /**
     * 文件上传，通过判断md5避免重复文件
     *
     * @param folderId
     * @param multipartFiles
     * @return
     * @throws IOException
     */
    @PostMapping(path = "/uploadFiles")
    public RestResponse uploadFiles(@RequestPart(value = "files") MultipartFile[] multipartFiles,
                                   @RequestParam("folderId") Long folderId
    ) throws IOException {
        boolean isAllUpload = true;
        int uploadNum = 0;
        for (int i = 0; i < multipartFiles.length; i++) {
            FileUploadVo fileUploadVo = new FileUploadVo();
            String filemd5 = MD5.create().digestHex(multipartFiles[i].getInputStream()).toUpperCase();
            fileUploadVo.setFileMD5(filemd5);
            fileUploadVo.setFolderId(folderId);
            fileUploadVo.setUserId(getUser().getId());
            fileUploadVo.setMultipartFile(multipartFiles[i]);
            if (!fileService.uploadFile(fileUploadVo).isOk()){
                isAllUpload = false;
                uploadNum--;
            }
            uploadNum++;
        }
        return RestUtil.ok(isAllUpload ? "全部上传成功" : "上传" + uploadNum + "个文件成功");
    }

    /**
     * 删除文件，数据直接删除，不使用逻辑删除
     *
     * @param fileId
     * @return
     */
    @GetMapping(path = "/deleteFile")
    public RestResponse deleteFile(String fileId) {
        return fileService.deleteFile(fileId) ? RestUtil.ok("删除成功") : RestUtil.error("删除失败");
    }

    /**
     * 删除文件，数据直接删除，不使用逻辑删除
     *
     * @param fileIds
     * @return
     */
    @GetMapping(path = "/deleteFiles")
    public RestResponse deleteFiles(String[] fileIds) {
        boolean isAllDelete = true;
        int deleteNum = 0;
        for (int i = 0; i < fileIds.length; i++) {
            if (!fileService.deleteFile(fileIds[i])) {
                isAllDelete = false;
                deleteNum--;
            }
            deleteNum++;
        }
        return isAllDelete ? RestUtil.ok("全部删除成功") : RestUtil.ok("删除" + deleteNum + "个文件");
    }

    /**
     * 文件重命名
     *
     * @param renameFileVo
     * @return
     */
    @PostMapping(path = "/renameFile")
    public RestResponse renameFile(@RequestBody RenameFileVo renameFileVo) {
        return fileService.renameFile(renameFileVo);
    }

    /**
     * 移动文件
     *
     * @param moveFilesVo
     * @return
     */
    @PostMapping(path = "/moveFiles")
    public RestResponse moveFiles(@RequestBody MoveFilesVo moveFilesVo) {
        boolean isAllMove = true;
        int moveNum = 0;
        for (int i = 0; i < moveFilesVo.getFileIds().length; i++) {
            MoveFileVo vo = new MoveFileVo();
            vo.setFileId(moveFilesVo.getFileIds()[i]);
            vo.setFolderId(moveFilesVo.getFolderId());
            if (!fileService.MoveFile(vo)) {
                isAllMove = false;
                moveNum--;
            }
            moveNum++;
        }
        return RestUtil.ok(isAllMove ? "全部移动成功" : "移动" + moveNum + "个文件成功");
    }

    /**
     * 移动文件
     *
     * @param moveFileVo
     * @return
     */
    @PostMapping(path = "/moveFile")
    public RestResponse moveFiles(@RequestBody MoveFileVo moveFileVo) {
        return fileService.MoveFile(moveFileVo) ? RestUtil.ok("移动成功") : RestUtil.error("移动失败");
    }

}
