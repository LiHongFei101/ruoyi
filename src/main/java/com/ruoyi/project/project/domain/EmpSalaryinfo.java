package com.ruoyi.project.project.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

public class EmpSalaryinfo {
    @Excel(name = "工资(/元)")
    private String empSalary;
    @Excel(name = "社保和公积金(/元)")
    private String empInsuranceFund;
    @Excel(name = "每月合计(/元)")
    private String empMonthSalary;
    @Excel(name = "员工级别", readConverterExp = "emp_level")
    private String empLevel;

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

    public String getEmpMonthSalary() {
        return empMonthSalary;
    }

    public void setEmpMonthSalary(String empMonthSalary) {
        this.empMonthSalary = empMonthSalary;
    }

    public String getEmpLevel() {
        return empLevel;
    }

    public void setEmpLevel(String empLevel) {
        this.empLevel = empLevel;
    }
}
