package com.silvericekey.cloudstorage.controller;

import cn.hutool.core.util.StrUtil;
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
        if (fileListVo.getFolderId()==null){
            fileListVo.setFolderId(0L);
        }
        return fileService.getFileList(fileListVo);
    }

    /**
     * 文件上传，通过判断md5避免重复文件
     *
     * @param filemd5
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadFile")
    public RestResponse uploadFile(@RequestPart("filemd5") String filemd5,
                                   @RequestPart("folderId") Long folderId,
                                   @RequestParam(value = "file",required = false) MultipartFile multipartFile) throws IOException {
        FileUploadVo fileUploadVo = new FileUploadVo();
        fileUploadVo.setFileMD5(filemd5);
        fileUploadVo.setFolderId(folderId);
        fileUploadVo.setUserId(getUser().getId());
        fileUploadVo.setMultipartFile(multipartFile);
        return fileService.uploadFile(fileUploadVo);
    }
}
