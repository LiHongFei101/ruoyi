package com.ruoyi.project.project.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

public class Perincome {
    @Excel(name = "员工姓名")
    private String empName;
//    @Excel(name = "级别", readConverterExp = "item_emp_level")
    private String empLevel;
//    @Excel(name = "级别工资(/元)")
    private String empSalary;
    @Excel(name = "开始日期")
    private String empStartDate;
    @Excel(name = "结束日期")
    private String empEndDate;
//    @Excel(name = "工作周期(/月)")
    private String empCycle;
    @Excel(name = "毛利费用率")
    private String empProfitrate;
    @Excel(name = "收入合计(/元)")
    private String empTotalSalary;

    private Monthname monthname;

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

    public String getEmpTotalSalary() {
        return empTotalSalary;
    }

    public void setEmpTotalSalary(String empTotalSalary) {
        this.empTotalSalary = empTotalSalary;
    }

    public String getEmpCycle() {
        return empCycle;
    }

    public void setEmpCycle(String empCycle) {
        this.empCycle = empCycle;
    }

    public Monthname getMonthname() {
        return monthname;
    }

    public void setMonthname(Monthname monthname) {
        this.monthname = monthname;
    }

    public String getEmpProfitrate() {
        return empProfitrate;
    }

    public void setEmpProfitrate(String empProfitrate) {
        this.empProfitrate = empProfitrate;
    }
}
