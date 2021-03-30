package com.ruoyi.project.project.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

import java.util.Date;

public class Itemper {
    private int ppid;
    private String empid;
    private String itemid;
    private String item_level_id;
    private String emptype;
    private String delflag;
    private String startdate;
    private String enddate;
    private String updatetime;
    private Itemperlevel level;

    public int getPpid() {
        return ppid;
    }

    public void setPpid(int ppid) {
        this.ppid = ppid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItem_level_id() {
        return item_level_id;
    }

    public void setItem_level_id(String item_level_id) {
        this.item_level_id = item_level_id;
    }

    public String getEmptype() {
        return emptype;
    }

    public void setEmptype(String emptype) {
        this.emptype = emptype;
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag;
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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public Itemperlevel getLevel() {
        return level;
    }

    public void setLevel(Itemperlevel level) {
        this.level = level;
    }
}
