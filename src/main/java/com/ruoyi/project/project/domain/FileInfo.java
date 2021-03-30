package com.ruoyi.project.project.domain;



/**
 * 定时任务调度表 sys_job
 * 
 * @author ruoyi
 */
public class FileInfo
{
    private int id;
    private int item_id;//项目id或人员id
    private String filename;
    private String uuidname;
    private String length;
    private String remark;

    public int getId() {
        return id;
    }

    public int getItem_id() {
        return item_id;
    }

    public String getFilename() {
        return filename;
    }

    public String getUuidname() {
        return uuidname;
    }

    public String getLength() {
        return length;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setUuidname(String uuidname) {
        this.uuidname = uuidname;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}