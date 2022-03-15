package com.silvericekey.cloudstorage.features.file.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author SilverIceKey
 * @title: FileUploadVo
 * @date 2022/3/1514:23
 */
@Data
public class FileUploadVo {
    private String fileMD5;
    private Integer folderId;
    private Integer userId;
    private MultipartFile multipartFile;
}
