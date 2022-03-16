package com.silvericekey.cloudstorage.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.silvericekey.cloudstorage.base.BaseController;
import com.silvericekey.cloudstorage.features.file.model.FileUploadVo;
import com.silvericekey.cloudstorage.features.file.service.FileService;
import com.silvericekey.cloudstorage.features.file.model.FileListVo;
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
}
