package com.ruoyi.project.employee.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.project.project.domain.Item;
import com.ruoyi.project.project.domain.Itemper;
import com.ruoyi.project.project.domain.Itemperlevel;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysPost;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeInfo {
    private String empId;
    @Excel(name = "姓名")
    private String empName;
    @Excel(name = "性别" ,readConverterExp="sys_user_sex")
    private String empSex;
    @Excel(name = "年龄")
    private Integer empAge;
    @Excel(name = "身份证号", width = 30)
    private String idCard;
    @Excel(name = "部门")
    private String empDepartment;
    @Excel(name = "岗位")
    private String empJob;
    @Excel(name = "职级", readConverterExp="emp_level")
    private String empLevel;
    @Excel(name = "电话")
    private String empTel;
    @Excel(name = "学历", readConverterExp="emp_edu")
    private String empEdu;
    @Excel(name = "专业")
    private String major;
    @Excel(name = "邮箱")
    private String empEmail;
    @Excel(name = "住址",width = 40)
    private String empAdress;
    @Excel(name = "籍贯", width = 40)
    private String empFamadress;
    private String empEmecontact;
    private String empEmecontacttel;
    @Excel(name = "参加工作时间")
    private String empWorkdate;
    @Excel(name = "员工状态", readConverterExp="emp_state")
    private String empState;
    @Excel(name = "入职时间")
    private String empEntrydate;
    @Excel(name = "转正/离职日期", width = 30)
    private String empSepardate;
    @Excel(name = "离职日期", width = 30)
    private String empQuitdate;

    @Excel(name = "所在项目", type = Excel.Type.EXPORT, width = 30)
    private String projectName;

    private String remark;
    private Date updateTime;
    private String empNo;
    private SysDept dept;
    private SysPost post;
    private EmpCost cost;
    private Itemper binding;
    private EmpCost cost_history;
    private String item_level_id;
    private String item_id;
    private Itemperlevel level;

    //毕业院校
    private List<String> eduSchoolList;
    //学信网是否可查
    private String isLearnWeb;

    public String getIsLearnWeb() {
        return isLearnWeb;
    }

    public void setIsLearnWeb(String isLearnWeb) {
        this.isLearnWeb = isLearnWeb;
    }

    public List getEduSchoolList() {
        return eduSchoolList;
    }

    public void setEduSchoolList(List eduSchoolList) {
        this.eduSchoolList = eduSchoolList;
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

    public String getEmpFamadress() {
        return empFamadress;
    }

    public void setEmpFamadress(String empFamadress) {
        this.empFamadress = empFamadress == null ? null : empFamadress.trim();
    }

    public String getEmpEmecontact() {
        return empEmecontact;
    }

    public void setEmpEmecontact(String empEmecontact) {
        this.empEmecontact = empEmecontact == null ? null : empEmecontact.trim();
    }

    public String getEmpEmecontacttel() {
        return empEmecontacttel;
    }

    public void setEmpEmecontacttel(String empEmecontacttel) {
        this.empEmecontacttel = empEmecontacttel == null ? null : empEmecontacttel.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo == null ? null : empNo.trim();
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public SysPost getPost() {
        return post;
    }

    public void setPost(SysPost post) {
        this.post = post;
    }

    public EmpCost getCost() {
        return cost;
    }

    public void setCost(EmpCost cost) {
        this.cost = cost;
    }


    public Itemper getBinding() {
        return binding;
    }

    public void setBinding(Itemper binding) {
        this.binding = binding;
    }

    public EmpCost getCost_history() {
        return cost_history;
    }

    public void setCost_history(EmpCost cost_history) {
        this.cost_history = cost_history;
    }

    public String getItem_level_id() {
        return item_level_id;
    }

    public void setItem_level_id(String item_level_id) {
        this.item_level_id = item_level_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public Itemperlevel getLevel() {
        return level;
    }

    public void setLevel(Itemperlevel level) {
        this.level = level;
    }

    public Integer getRealAge(){
        Integer age = 0;
        try {
            if(idCard == null || "".equals(idCard) ){
                return age;
            }
            if (/*idCard.length() != 15 &&*/ idCard.length() != 18){
                return age;
            }

            Calendar cal = Calendar.getInstance();
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH)+1;
            int dayNow = cal.get(Calendar.DATE);

            int year = Integer.valueOf(idCard.substring(6, 10));
            int month = Integer.valueOf(idCard.substring(10,12));
            int day = Integer.valueOf(idCard.substring(12,14));

            if ((month < monthNow) || (month == monthNow && day<= dayNow) ){
                age = yearNow - year;
            }else {
                age = yearNow - year-1;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return age;
    }

    public String getEmpQuitdate() {
        return empQuitdate;
    }

    public void setEmpQuitdate(String empQuitdate) {
        this.empQuitdate = empQuitdate;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}