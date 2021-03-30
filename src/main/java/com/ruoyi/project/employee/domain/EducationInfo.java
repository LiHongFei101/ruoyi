package com.ruoyi.project.employee.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

import java.util.Date;

public class EducationInfo {

    private String eduId;

    @Excel(name = "身份证号", type = Excel.Type.IMPORT, width = 20)
    private String empId;

    @Excel(name = "毕业院校", type = Excel.Type.IMPORT, width = 30)
    private String eduSchool;

    @Excel(name = "学历", type = Excel.Type.IMPORT, readConverterExp="emp_edu")
    private String empEdu;

    @Excel(name = "开始时间", type = Excel.Type.IMPORT, width = 15)
    private String eduBegin;

    @Excel(name = "结束时间", type = Excel.Type.IMPORT, width = 15)
    private String eduGraduation;

    @Excel(name = "是否有毕业证书", type = Excel.Type.IMPORT, readConverterExp="sys_yes_no", width = 20)
    private String isdisploma;

    @Excel(name = "是否有学位证书", type = Excel.Type.IMPORT, readConverterExp="sys_yes_no", width = 20)
    private String isdegree;

    @Excel(name = "学信网是否可查", type = Excel.Type.IMPORT, readConverterExp="sys_yes_no", width = 20)
    private String isLearnWeb;

    private String documentId;

    private Date updateTime;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getEduBegin() {
        return eduBegin;
    }

    public void setEduBegin(String eduBegin) {
        this.eduBegin = eduBegin;
    }

    public String getEduId() {
        return eduId;
    }

    public void setEduId(String eduId) {
        this.eduId = eduId == null ? null : eduId.trim();
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public String getEduSchool() {
        return eduSchool;
    }

    public void setEduSchool(String eduSchool) {
        this.eduSchool = eduSchool == null ? null : eduSchool.trim();
    }

    public String getEduGraduation() {
        return eduGraduation;
    }

    public void setEduGraduation(String eduGraduation) {
        this.eduGraduation = eduGraduation == null ? null : eduGraduation.trim();
    }

    public String getIsdisploma() {
        return isdisploma;
    }

    public void setIsdisploma(String isdisploma) {
        this.isdisploma = isdisploma == null ? null : isdisploma.trim();
    }

    public String getIsdegree() {
        return isdegree;
    }

    public void setIsdegree(String isdegree) {
        this.isdegree = isdegree == null ? null : isdegree.trim();
    }

    public String getEmpEdu() {
        return empEdu;
    }

    public void setEmpEdu(String empEdu) {
        this.empEdu = empEdu == null ? null : empEdu.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsLearnWeb() {
        return isLearnWeb;
    }

    public void setIsLearnWeb(String isLearnWeb) {
        this.isLearnWeb = isLearnWeb;
    }
}