package com.ruoyi.project.project.mapper;

import com.ruoyi.project.project.domain.FileInfo;

import java.util.List;

/**
 * 调度任务信息 数据层
 * 
 * @author ruoyi
 */
public interface UploadMapper
{
    /**
     * 查询文件列表
     * @return
     */
    public List<FileInfo> selectFileList(FileInfo fileInfo);

    /**
     * 删除文件列表
     * @return
     */
    public int deleteFileByIds(FileInfo fileInfo);

    /**
     * 新增文件列表
     * @return
     */
    public int uploadFile(FileInfo fileInfo);

}
