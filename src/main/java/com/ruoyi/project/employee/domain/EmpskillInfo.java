package com.ruoyi.project.employee.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

import java.util.Date;

public class EmpskillInfo {

    private String skillId;

    @Excel(name = "身份证号", type = Excel.Type.IMPORT, width = 30)
    private String empId;

    @Excel(name = "证书编号", type = Excel.Type.IMPORT, width = 30)
    private String skillNo;

    @Excel(name = "证书名称", type = Excel.Type.IMPORT, width = 20)
    private String skillName;

    @Excel(name = "证书级别", type = Excel.Type.IMPORT, readConverterExp="emp_skill_level")
    private String skillLevel;

    @Excel(name = "认证机构", type = Excel.Type.IMPORT, width = 20)
    private String certificationOrg;

    private String documentId;

    @Excel(name = "获得日期", type = Excel.Type.IMPORT, width = 15)
    private String skillDate;

    private Date updateTime;

    public String getSkillDate() {
        return skillDate;
    }

    public void setSkillDate(String skillDate) {
        this.skillDate = skillDate;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId == null ? null : skillId.trim();
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public String getSkillNo() {
        return skillNo;
    }

    public void setSkillNo(String skillNo) {
        this.skillNo = skillNo == null ? null : skillNo.trim();
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName == null ? null : skillName.trim();
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel == null ? null : skillLevel.trim();
    }

    public String getCertificationOrg() {
        return certificationOrg;
    }

    public void setCertificationOrg(String certificationOrg) {
        this.certificationOrg = certificationOrg == null ? null : certificationOrg.trim();
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId == null ? null : documentId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}