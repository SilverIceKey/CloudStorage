package com.silvericekey.cloudstorage.features.file;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.silvericekey.cloudstorage.base.BaseController;
import com.silvericekey.cloudstorage.common.Constants;
import com.silvericekey.cloudstorage.features.file.model.FileUploadVo;
import com.silvericekey.cloudstorage.features.file.service.IFileService;
import com.silvericekey.cloudstorage.features.file.entity.FileInfo;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import com.silvericekey.cloudstorage.features.file.model.FileListVo;
import com.silvericekey.cloudstorage.base.RestResponse;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController extends BaseController {
    private final IFileService fileService;

    /**
     * 获取文件列表
     *
     * @return
     */
    @GetMapping(path = "/getList")
    public RestResponse getFileList(@RequestBody FileListVo fileListVo) {
        fileListVo.setUserId(getUser().getId());
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
                                   @RequestPart("folderId") Integer folderId,
                                   @RequestPart("file") MultipartFile multipartFile) throws IOException {
        FileUploadVo fileUploadVo = new FileUploadVo();
        fileUploadVo.setFileMD5(filemd5);
        fileUploadVo.setFolderId(folderId);
        fileUploadVo.setUserId(getUser().getId());
        fileUploadVo.setMultipartFile(multipartFile);
        return fileService.uploadFile(fileUploadVo);
    }
}
