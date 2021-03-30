package com.ruoyi.project.project.domain;


public class Itemperlevel  {
//    private static final long serialVersionUID = 1L;
    private String item_level_id;
    private String item_id;
    private String customer_id;
    private String level_name;
    private String level_num;
    private String level_price;
/*    private String ischange;
    private String change_num;*/
    private String del_flag;
    private String update_time;

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

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
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

   /* public String getIschange() {
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
    }*/

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
