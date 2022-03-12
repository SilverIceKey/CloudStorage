package com.silvericekey.cloudstorage.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件信息
 */
@Data
@TableName("file_info")
public class FileInfo {
    
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建和更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
