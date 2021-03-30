package com.ruoyi.project.employee.domain;

import com.ruoyi.common.utils.DateUtils;

import java.util.Date;

public class DocBindBatch {
    private String id;

    private String zipname;

    private Long zipsize;

    private String empid;

    private String doctype;

    private Long docsize;

    private String itemId;

    private String itemName;

    private String docname;

    private String issuccess;

    private String msg;

    private String bindDate;

    private Date updatetime;

    private String userid;

    private String empInfo;



    public DocBindBatch(){}
    public DocBindBatch(String zipname, Long zipsize, String userid){
        this.zipname = zipname;
        this.zipsize = zipsize;
        this.userid = userid;
        this.updatetime = DateUtils.getNowDate();
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getZipname() {
        return zipname;
    }

    public void setZipname(String zipname) {
        this.zipname = zipname == null ? null : zipname.trim();
    }

    public Long getZipsize() {
        return zipsize;
    }

    public void setZipsize(Long zipsize) {
        this.zipsize = zipsize;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype == null ? null : doctype.trim();
    }

    public Long getDocsize() {
        return docsize;
    }

    public void setDocsize(Long docsize) {
        this.docsize = docsize;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname == null ? null : docname.trim();
    }

    public String getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(String issuccess) {
        this.issuccess = issuccess == null ? null : issuccess.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getBindDate() {
        return bindDate;
    }

    public void setBindDate(String bindDate) {
        this.bindDate = bindDate;
    }

    public String getEmpInfo() {
        return empInfo;
    }

    public void setEmpInfo(String empInfo) {
        this.empInfo = empInfo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}