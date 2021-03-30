package com.ruoyi.project.employee.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.aspectj.lang.annotation.Excels;

import java.util.Date;

public class EmpCost {

    private String costId;

    private String empId;

    /** 部门对象 */
    @Excels({
        @Excel(name = "姓名", targetAttr = "empName", type = Excel.Type.EXPORT),
        @Excel(name = "学历", targetAttr = "empEdu", type = Excel.Type.EXPORT, readConverterExp="emp_edu"),
        @Excel(name = "部门", targetAttr = "empDepartment", type = Excel.Type.EXPORT),
        @Excel(name = "岗位", targetAttr = "empJob", type = Excel.Type.EXPORT),
        @Excel(name = "职级", targetAttr = "empLevel", type = Excel.Type.EXPORT, readConverterExp="emp_level"),
        @Excel(name = "身份证号", targetAttr = "idCard", type = Excel.Type.IMPORT),
    })
    private EmployeeInfo employeeInfo;
    @Excel(name = "工资", cellType = Excel.ColumnType.NUMERIC)
    private Double empSalary;
    @Excel(name = "社保", cellType = Excel.ColumnType.NUMERIC)
    private Double empInsurance;
    @Excel(name = "公积金", cellType = Excel.ColumnType.NUMERIC)
    private Double empFund;
    @Excel(name = "补助1", cellType = Excel.ColumnType.NUMERIC)
    private Double subsidies1;
    @Excel(name = "补助2", cellType = Excel.ColumnType.NUMERIC)
    private Double subsidies2;
    @Excel(name = "补助3", cellType = Excel.ColumnType.NUMERIC)
    private Double subsidies3;
    @Excel(name = "补助4", cellType = Excel.ColumnType.NUMERIC)
    private Double subsidies4;

    private Double subsidies5;
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date updateTime;

    private String isHistory;

    public String getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(String isHistory) {
        this.isHistory = isHistory;
    }



    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public String getCostId() {
        return costId;
    }

    public void setCostId(String costId) {
        this.costId = costId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(Double empSalary) {
        this.empSalary = empSalary;
    }

    public Double getEmpInsurance() {
        return empInsurance;
    }

    public void setEmpInsurance(Double empInsurance) {
        this.empInsurance = empInsurance;
    }

    public Double getEmpFund() {
        return empFund;
    }

    public void setEmpFund(Double empFund) {
        this.empFund = empFund;
    }

    public Double getSubsidies1() {
        return subsidies1;
    }

    public void setSubsidies1(Double subsidies1) {
        this.subsidies1 = subsidies1;
    }

    public Double getSubsidies2() {
        return subsidies2;
    }

    public void setSubsidies2(Double subsidies2) {
        this.subsidies2 = subsidies2;
    }

    public Double getSubsidies3() {
        return subsidies3;
    }

    public void setSubsidies3(Double subsidies3) {
        this.subsidies3 = subsidies3;
    }

    public Double getSubsidies4() {
        return subsidies4;
    }

    public void setSubsidies4(Double subsidies4) {
        this.subsidies4 = subsidies4;
    }

    public Double getSubsidies5() {
        return subsidies5;
    }

    public void setSubsidies5(Double subsidies5) {
        this.subsidies5 = subsidies5;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}