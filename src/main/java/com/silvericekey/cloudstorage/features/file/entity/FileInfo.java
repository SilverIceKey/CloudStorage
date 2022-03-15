package com.silvericekey.cloudstorage.features.file.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文件信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("file_info")
public class FileInfo {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件md5
     */
    @TableField("file_md5")
    private String fileMD5;

    /**
     * 文件大小
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件大小转为单位
     */
    @TableField(exist = false)
    private String fileSizeStr;

    /**
     * 文件下载路径
     */
    @TableField("file_download_path")
    private String fileDownloadPath;

    /**
     * 文件夹id
     */
    @TableField("folder_id")
    private Long folderId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建和更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public static final String ID = "id";

    public static final String FILE_NAME = "file_name";

    public static final String FILE_PATH = "file_path";

    public static final String FILE_MD5 = "file_md5";

    public static final String FILE_SIZE = "file_size";

    public static final String FILE_DOWNLOAD_PATH = "file_download_path";

    public static final String FOLDER_ID = "folder_id";

    public static final String USER_ID = "user_id";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";
}
