package com.silvericekey.cloudstorage.features.folder.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文件夹信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("folder_info")
public class FolderInfo {
    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
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

    public static final String FOLDER_NAME = "folder_name";

    public static final String FOLDER_PARENT_ID = "folder_parent_id";

    public static final String USER_ID = "user_id";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";
}
