package com.ruoyi.project.employee.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;


public class EmpIdleTableExp {

    private String empId;

    @Excel(name = "姓名", type = Excel.Type.EXPORT)
    private String empName;

    @Excel(name = "性别", readConverterExp="sys_user_sex", type = Excel.Type.EXPORT)
    private String empSex;

    @Excel(name = "年龄", type = Excel.Type.EXPORT)
    private Integer empAge;

    @Excel(name = "身份证号", width = 30, type = Excel.Type.EXPORT)
    private String idCard;

    @Excel(name = "部门", type = Excel.Type.EXPORT)
    private String empDepartment;

    @Excel(name = "岗位", type = Excel.Type.EXPORT)
    private String empJob;

    @Excel(name = "职级", readConverterExp="emp_level", type = Excel.Type.EXPORT)
    private String empLevel;

    @Excel(name = "电话", type = Excel.Type.EXPORT)
    private String empTel;

    @Excel(name = "学历", readConverterExp="emp_edu", type = Excel.Type.EXPORT)
    private String empEdu;

    @Excel(name = "专业", type = Excel.Type.EXPORT)
    private String major;

    @Excel(name = "邮箱", type = Excel.Type.EXPORT)
    private String empEmail;

    @Excel(name = "住址",width = 40, type = Excel.Type.EXPORT)
    private String empAdress;

    @Excel(name = "参加工作时间", type = Excel.Type.EXPORT)
    private String empWorkdate;

    @Excel(name = "员工状态", readConverterExp="emp_state", type = Excel.Type.EXPORT)
    private String empState;

    @Excel(name = "入职时间", type = Excel.Type.EXPORT)
    private String empEntrydate;

    @Excel(name = "转正日期", width = 30, type = Excel.Type.EXPORT)
    private String empSepardate;

    private String empQuitdate;

    private String empNo;

    @Excel(name = "开始闲置时间", width = 30, type = Excel.Type.EXPORT)
    private String idleStartDate;

    public String getEmpQuitdate() {
        return empQuitdate;
    }

    public void setEmpQuitdate(String empQuitdate) {
        this.empQuitdate = empQuitdate;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getEmpSex() {
        return empSex;
    }

    public void setEmpSex(String empSex) {
        this.empSex = empSex == null ? null : empSex.trim();
    }

    public String getIdCard(){
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getEmpDepartment() {
        return empDepartment;
    }

    public void setEmpDepartment(String empDepartment) {
        this.empDepartment = empDepartment == null ? null : empDepartment.trim();
    }

    public String getEmpJob() {
        return empJob;
    }

    public void setEmpJob(String empJob) {
        this.empJob = empJob == null ? null : empJob;
    }

    public String getEmpLevel() {
        return empLevel;
    }

    public void setEmpLevel(String empLevel) {
        this.empLevel = empLevel == null ? null : empLevel.trim();
    }

    public String getEmpTel() {
        return empTel;
    }

    public void setEmpTel(String empTel) {
        this.empTel = empTel == null ? null : empTel.trim();
    }

    public Integer getEmpAge() {
        return empAge;
    }

    public void setEmpAge(Integer empAge) {
        this.empAge = empAge;
    }

    public String getEmpEdu(){
        return empEdu;
    }

    public void setEmpEdu(String empEdu){
        this.empEdu = empEdu == null ? null : empEdu.trim();
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpAdress() {
        return empAdress;
    }

    public void setEmpAdress(String empAdress) {
        this.empAdress = empAdress == null ? null : empAdress.trim();
    }

    public String getEmpWorkdate() {
        return empWorkdate;
    }

    public void setEmpWorkdate(String empWorkdate) {
        this.empWorkdate = empWorkdate == null ? null : empWorkdate.trim();
    }

    public String getEmpState() {
        return empState;
    }

    public void setEmpState(String empState) {
        this.empState = empState == null ? null : empState.trim();
    }

    public String getEmpEntrydate() {
        return empEntrydate;
    }

    public void setEmpEntrydate(String empEntrydate) {
        this.empEntrydate = empEntrydate == null ? null : empEntrydate.trim();
    }

    public String getEmpSepardate() {
        return empSepardate;
    }

    public void setEmpSepardate(String empSepardate) {
        this.empSepardate = empSepardate == null ? null : empSepardate.trim();
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo == null ? null : empNo.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getIdleStartDate() {
        return idleStartDate;
    }

    public void setIdleStartDate(String idleStartDate) {
        this.idleStartDate = idleStartDate;
    }
}