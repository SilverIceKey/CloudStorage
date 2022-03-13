package com.silvericekey.cloudstorage.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件夹信息
 */
@Data
@TableName("folder_info")
public class FolderInfo {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 文件夹名称
     */
    @TableField("folder_name")
    private String folderName;
    /**
     * 父文件夹id
     */
    @TableField("folder_parent_id")
    private Long folderParentId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;


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
