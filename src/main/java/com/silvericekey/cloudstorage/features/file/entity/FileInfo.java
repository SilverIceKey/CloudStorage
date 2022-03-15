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
    private Integer id;

    /**
     * 文件名称
     */
    @TableField("filename")
    private String fileName;

    /**
     * 文件路径
     */
    @TableField("filepath")
    private String filePath;

    /**
     * 文件md5
     */
    @TableField("filemd5")
    private String fileMD5;
    /**
     * 文件夹id
     */
    @TableField("folder_id")
    private Integer folderId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

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

    private static final String ID = "id";

    private static final String FILENAME = "filename";

    private static final String FILEPATH = "filepath";

    private static final String FILEMD5 = "filemd5";

    private static final String FOLDER_ID = "folder_id";

    private static final String USER_ID = "user_id";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";
}
