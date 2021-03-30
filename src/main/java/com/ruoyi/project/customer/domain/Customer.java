package com.ruoyi.project.customer.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

import java.math.BigDecimal;

/**
 * 客户信息实体类
 *
 * @author
 */
public class Customer {
    /** 客户ID */
    private String customer_id;
    /** 项目名称 */
    @Excel(name = "客户名称")
    private String customer_name;
    @Excel(name = "客户类型",readConverterExp = "item_type")
    private String customer_type;
    private String customer_tel;
    @Excel(name = "客户邮箱")
    private String customer_email;
    @Excel(name = "客户地址")
    private String customer_adress;
    private String del_flag;
    private String remark;
    @Excel(name = "创建时间")
    private String update_time;
    private String emp_num;
    private BigDecimal contract_fund;
    private BigDecimal emp_cost;
    private BigDecimal empProfit;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    public String getCustomer_tel() {
        return customer_tel;
    }

    public void setCustomer_tel(String customer_tel) {
        this.customer_tel = customer_tel;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_adress() {
        return customer_adress;
    }

    public void setCustomer_adress(String customer_adress) {
        this.customer_adress = customer_adress;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getEmp_num() {
        return emp_num;
    }

    public void setEmp_num(String emp_num) {
        this.emp_num = emp_num;
    }

    public BigDecimal getContract_fund() {
        return contract_fund;
    }

    public void setContract_fund(BigDecimal contract_fund) {
        this.contract_fund = contract_fund;
    }

    public BigDecimal getEmp_cost() {
        return emp_cost;
    }

    public void setEmp_cost(BigDecimal emp_cost) {
        this.emp_cost = emp_cost;
    }

    public BigDecimal getEmpProfit() {
        return empProfit;
    }

    public void setEmpProfit(BigDecimal empProfit) {
        this.empProfit = empProfit;
    }
}
