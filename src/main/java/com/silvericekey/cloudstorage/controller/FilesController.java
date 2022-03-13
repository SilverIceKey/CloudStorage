package com.silvericekey.cloudstorage.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.silvericekey.cloudstorage.common.Constants;
import com.silvericekey.cloudstorage.dao.mapper.FileInfoMapper;
import com.silvericekey.cloudstorage.dao.mapper.FolderInfoMapper;
import com.silvericekey.cloudstorage.entity.FileInfo;
import com.silvericekey.cloudstorage.entity.FolderInfo;
import com.silvericekey.cloudstorage.model.FileListVo;
import com.silvericekey.cloudstorage.model.RestResponse;
import com.silvericekey.cloudstorage.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FilesController extends BaseController {
    @Autowired
    FileInfoMapper fileInfoMapper;

    @Autowired
    FolderInfoMapper folderInfoMapper;

    /**
     * 获取文件列表
     *
     * @return
     */
    @GetMapping(path = "/getList")
    public RestResponse getFilesList(@RequestBody FileListVo fileListVo) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", getUser().getId());
        if (fileListVo != null) {
            queryWrapper.eq("folder_id", fileListVo.getFolderId());
        }
        List<FileInfo> fileInfoList = fileInfoMapper.selectList(queryWrapper);
        QueryWrapper<FolderInfo> folderInfoQueryWrapper = new QueryWrapper<>();
        folderInfoQueryWrapper.eq("folder_parent_id", fileListVo.getFolderId());
        List<FolderInfo> folderInfos = folderInfoMapper.selectList(folderInfoQueryWrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fileList", fileInfoList);
        hashMap.put("folderList", folderInfos);
        return RestUtil.ok(hashMap);
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
    public RestResponse uploadFile(@RequestPart("filemd5") String filemd5, @RequestPart("folderId") Long folderId, @RequestPart("file") MultipartFile multipartFile) throws IOException {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("filemd5", filemd5.toUpperCase());
        FileInfo fileInfo = fileInfoMapper.selectOne(queryWrapper);
        if (fileInfo != null) {
            return RestUtil.ok("极速上传成功", fileInfo);
        } else {
            OutputStream os = FileUtil.getOutputStream(Constants.FILE_SAVE_PATH + multipartFile.getOriginalFilename());
            InputStream is = multipartFile.getInputStream();
            int readData = -1;
            while ((readData = is.read()) != -1) {
                os.write(readData);
            }
            os.close();
            is.close();
            fileInfo = new FileInfo();
            fileInfo.setFileMD5(filemd5.toUpperCase());
            fileInfo.setFileName(multipartFile.getOriginalFilename());
            fileInfo.setFilePath(Constants.FILE_SAVE_PATH + multipartFile.getOriginalFilename());
            fileInfo.setFolder_id(folderId);
            fileInfo.setUserId(getUser().getId());
            fileInfoMapper.insert(fileInfo);
            return RestUtil.ok("上传成功", fileInfo);
        }
    }
}
