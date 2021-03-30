package com.ruoyi.project.project.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Percost {
    @Excel(name = "员工姓名")
    private String empName;
//    @Excel(name = "员工级别", readConverterExp = "emp_level")
    private String empLevel;
//    @Excel(name = "工资(/元)")
    private String empSalary;
//    @Excel(name = "社保和公积金(/元)")
    private String empInsuranceFund;
//    @Excel(name = "每月合计(/元)")
    private String empMonthSalary;
    @Excel(name = "开始日期")
    private String empStartDate;
    @Excel(name = "结束日期")
    private String empEndDate;
   /* @Excel(name = "变更前工资(/元)")
    private String empChangeBeforeSalary;
    @Excel(name = "变更前周期(/月)")
    private String empChangeBeforeCycle;
    @Excel(name = "变更前开始日期")
    private String empChangeBeforeStartDate;
    @Excel(name = "变更前结束日期")
    private String empChangeBeforeEndDate;
    @Excel(name = "变更后工资(/元)")
    private String empChangeAfterSalary;
    @Excel(name = "变更后周期(/月)")
    private String empChangeAfterCycle;
    @Excel(name = "变更后开始日期")
    private String empChangeAfterStartDate;
    @Excel(name = "变更后结束日期")
    private String empChangeAfterEndDate;
    @Excel(name = "工作周期(/月)")
    private String empCycle;*/
    @Excel(name = "毛利费用率")
    private String empProfitrate;
    @Excel(name = "成本合计(/元)")
    private String empTotalSalary;

    private Monthname monthname;
    private int item_id;
   /* @Excel(name = "创建时间")
    private String updatetime;*/

    public String getEmpMonthSalary() {
        return empMonthSalary;
    }

    public void setEmpMonthSalary(String empMonthSalary) {
        this.empMonthSalary = empMonthSalary;
    }

    public String getEmpStartDate() {
        return empStartDate;
    }

    public void setEmpStartDate(String empStartDate) {
        this.empStartDate = empStartDate;
    }

    public String getEmpEndDate() {
        return empEndDate;
    }

    public void setEmpEndDate(String empEndDate) {
        this.empEndDate = empEndDate;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpLevel() {
        return empLevel;
    }

    public void setEmpLevel(String empLevel) {
        this.empLevel = empLevel;
    }

    public String getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(String empSalary) {
        this.empSalary = empSalary;
    }

    public String getEmpInsuranceFund() {
        return empInsuranceFund;
    }

    public void setEmpInsuranceFund(String empInsuranceFund) {
        this.empInsuranceFund = empInsuranceFund;
    }

    public String getEmpTotalSalary() {
        return empTotalSalary;
    }

    public void setEmpTotalSalary(String empTotalSalary) {
        this.empTotalSalary = empTotalSalary;
    }

    public Monthname getMonthname() {
        return monthname;
    }

    public void setMonthname(Monthname monthname) {
        this.monthname = monthname;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getEmpProfitrate() {
        return empProfitrate;
    }

    public void setEmpProfitrate(String empProfitrate) {
        this.empProfitrate = empProfitrate;
    }

    /* public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }*/

    @Override
    public String toString() {
        return "Percost{" +
                "empName='" + empName + '\'' +
                ", empLevel='" + empLevel + '\'' +
                ", empSalary='" + empSalary + '\'' +
                ", empInsuranceFund='" + empInsuranceFund + '\'' +
                ", empMonthSalary='" + empMonthSalary + '\'' +
                ", empStartDate='" + empStartDate + '\'' +
                ", empEndDate='" + empEndDate + '\'' +
                ", empProfitrate='" + empProfitrate + '\'' +
                ", empTotalSalary='" + empTotalSalary + '\'' +
                ", monthname=" + monthname +
                ", item_id=" + item_id +
                '}';
    }
}
