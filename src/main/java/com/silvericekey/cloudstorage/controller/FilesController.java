package com.silvericekey.cloudstorage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.silvericekey.cloudstorage.dao.mapper.FileInfoMapper;
import com.silvericekey.cloudstorage.entity.FileInfo;
import com.silvericekey.cloudstorage.model.RestReponse;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/files")
public class FilesController {
    @Autowired
    FileInfoMapper fileInfoMapper;

    /**
     * 获取文件列表
     * @return
     */
    @GetMapping(path = "/getList")
    public RestReponse getFilesList(){
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        List<FileInfo> fileInfoList = fileInfoMapper.selectList(queryWrapper);
        return RestUtil.ok(fileInfoList);
    }
}
