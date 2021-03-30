package com.ruoyi.project.project.domain;


import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.aspectj.lang.annotation.Excels;
import com.ruoyi.framework.web.domain.BaseEntity;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysRole;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 定时任务调度表 sys_job
 * 
 * @author ruoyi
 */
public class Item extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /** 项目ID */
    private int item_id;
    /** 部门ID */
    @Excel(name = "部门名称", type = Excel.Type.IMPORT)
    private String dept_id;
    /** 项目名称 */
    @Excel(name = "项目名称", type = Excel.Type.ALL)
    private String item_name;
    /** 项目编号 */
    @Excel(name = "项目编号", type = Excel.Type.ALL)
    private String item_numbering;
    /** 项目负责人 */
    @Excel(name = "项目负责人" )
    private String empId;
    /** 项目负责人身份证号 */
    @Excel(name = "负责人身份证号" ,type = Excel.Type.IMPORT)
    private String idCard;
    /** 项目负责人电话 */
    @Excel(name = "项目负责人电话")
    private String item_leadertel;
    /*@Excel(name = "项目级别", readConverterExp = "item_level")
    private String item_level;*/
    /** 项目类别 */
    @Excel(name = "项目类别", readConverterExp = "item_type", type = Excel.Type.ALL)
    private String item_type;
    /** 项目阶段 */
    @Excel(name = "项目阶段", readConverterExp = "item_phase", type = Excel.Type.ALL)
    private String item_phase;
    /*@Excel(name = "项目级别", readConverterExp = "0=高,1=中,2=低")
    private String item_level;*/
    /*@Excel(name = "项目类别", readConverterExp = "0=项目外包,1=自主研发")
    private String item_type;*/
/*    @Excel(name = "项目阶段", readConverterExp = "0=竞标阶段,1=开发阶段,2=测试阶段,3=已验收")
    private String item_phase;*/
    /** 项目人数 */
    @Excel(name = "项目人数")
    private String item_num;
    /** 项目开始时间 */
    @Excel(name = "项目开始时间", type = Excel.Type.ALL)
    private String item_startdate;
    /** 项目结束时间 */
    @Excel(name = "项目结束时间", type = Excel.Type.ALL)
    private String item_enddate;
    /** 项目资金 */
    @Excel(name = "项目资金" , type = Excel.Type.ALL)
    private String item_fund;
    /** 项目实施费用 */
    @Excel(name = "项目实施费用" , type = Excel.Type.ALL)
    private String workcost;
    /** 项目税率 */
    @Excel(name = "项目税率", readConverterExp = "item_taxrate", type = Excel.Type.ALL)
    private String taxrate;
    private String item_cycle;
    /** 客户名称 */
    @Excel(name = "客户名称", type = Excel.Type.EXPORT)
    private String customer_id;
    /** 客户地址 */
    /*@Excel(name = "客户地址")
    private String item_adress;*/
    private String ischange;
    /** 变更批次 */
    @Excel(name = "变更批次",type = Excel.Type.EXPORT)
    private String change_num;
    /** 创建时间 */
    @Excel(name = "创建时间",type = Excel.Type.EXPORT)
    private String update_time;
    /** 备注 */
    @Excel(name = "备注",type = Excel.Type.EXPORT)
    private String remark;

    /** 部门对象 */
    @Excels({
            @Excel(name = "所属部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT)
    })
    private SysDept dept;
    private BigDecimal itemCost;//成本
    private BigDecimal itemProfit;//利润

    /** 角色对象 */
    private List<SysRole> roles;

    /** 角色组 */
    private Long[] roleIds;

    /** 岗位组 */
    private Long[] postIds;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_numbering() {
        return item_numbering;
    }

    public void setItem_numbering(String item_numbering) {
        this.item_numbering = item_numbering;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getItem_leadertel() {
        return item_leadertel;
    }

    public void setItem_leadertel(String item_leadertel) {
        this.item_leadertel = item_leadertel;
    }

    /*public String getItem_level() {
        return item_level;
    }

    public void setItem_level(String item_level) {
        this.item_level = item_level;
    }*/

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getItem_phase() {
        return item_phase;
    }

    public void setItem_phase(String item_phase) {
        this.item_phase = item_phase;
    }

    public String getItem_num() {
        return item_num;
    }

    public void setItem_num(String item_num) {
        this.item_num = item_num;
    }

    public String getItem_startdate() {
        return item_startdate;
    }

    public void setItem_startdate(String item_startdate) {
        this.item_startdate = item_startdate;
    }

    public String getItem_enddate() {
        return item_enddate;
    }

    public void setItem_enddate(String item_enddate) {
        this.item_enddate = item_enddate;
    }

    public String getItem_fund() {
        return item_fund;
    }

    public void setItem_fund(String item_fund) {
        this.item_fund = item_fund;
    }

    public String getWorkcost() {
        return workcost;
    }

    public void setWorkcost(String workcost) {
        this.workcost = workcost;
    }

    public String getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(String taxrate) {
        this.taxrate = taxrate;
    }

    public String getItem_cycle() {
        return item_cycle;
    }

    public void setItem_cycle(String item_cycle) {
        this.item_cycle = item_cycle;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getIschange() {
        return ischange;
    }

    public void setIschange(String ischange) {
        this.ischange = ischange;
    }

    public String getChange_num() {
        return change_num;
    }

    public void setChange_num(String change_num) {
        this.change_num = change_num;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public BigDecimal getItemProfit() {
        return itemProfit;
    }

    public void setItemProfit(BigDecimal itemProfit) {
        this.itemProfit = itemProfit;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds() {
        return postIds;
    }

    public void setPostIds(Long[] postIds) {
        this.postIds = postIds;
    }

}