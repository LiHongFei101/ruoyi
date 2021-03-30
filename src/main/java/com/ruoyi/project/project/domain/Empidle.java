package com.ruoyi.project.project.domain;

import java.util.Date;

public class Empidle {

    private int idleId;

    private String empId;

    private String beforeitemId;

    private String startdate;

    private String enddate;

    private String delFlag;

    private Date updateTime;

    private String empQuitdate;

    public String getEmpQuitdate() {
        return empQuitdate;
    }

    public void setEmpQuitdate(String empQuitdate) {
        this.empQuitdate = empQuitdate;
    }

    public int getIdleId() {
        return idleId;
    }

    public void setIdleId(int idleId) {
        this.idleId = idleId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getBeforeitemId() {
        return beforeitemId;
    }

    public void setBeforeitemId(String beforeitemId) {
        this.beforeitemId = beforeitemId;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
