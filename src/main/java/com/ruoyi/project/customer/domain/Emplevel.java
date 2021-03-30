package com.ruoyi.project.customer.domain;

public class Emplevel {
    private int emp_level_id;
    private int customer_id;
    private String level_name;
    private String level_num;
    private String level_price;
    private String del_flag;
    private String update_time;

    public int getEmp_level_id() {
        return emp_level_id;
    }

    public void setEmp_level_id(int emp_level_id) {
        this.emp_level_id = emp_level_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getLevel_num() {
        return level_num;
    }

    public void setLevel_num(String level_num) {
        this.level_num = level_num;
    }

    public String getLevel_price() {
        return level_price;
    }

    public void setLevel_price(String level_price) {
        this.level_price = level_price;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
