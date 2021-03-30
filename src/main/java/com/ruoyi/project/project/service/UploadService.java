package com.ruoyi.project.project.service;

import com.ruoyi.project.project.domain.FileInfo;
import com.ruoyi.project.project.domain.Item;

import java.util.List;

/**
 * 文件上传下载服务
 *
 * @author ruoyi
 */
public interface UploadService
{
    /**
     * 查询文件列表信息
     *
     */
    public List<FileInfo> selectFileList(FileInfo fileInfo);

    /**
     * 删除文件列表信息
     */
    public int deleteFileByIds(FileInfo fileInfo);

    /**
     * 新增文件信息
     */
    public int uploadFile(FileInfo fileInfo);
    /**
     * 导入项目数据
     *
     * @param itemList 项目数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    public String importItem(List<Item> itemList, Boolean isUpdateSupport);

}