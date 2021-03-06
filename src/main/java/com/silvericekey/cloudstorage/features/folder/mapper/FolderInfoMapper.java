package com.silvericekey.cloudstorage.features.folder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.silvericekey.cloudstorage.features.folder.entity.FolderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 文件信息操作
 */
public interface FolderInfoMapper extends BaseMapper<FolderInfo> {
    
}
