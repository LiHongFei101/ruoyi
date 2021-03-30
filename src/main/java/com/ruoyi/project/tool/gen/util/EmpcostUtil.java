package com.ruoyi.project.tool.gen.util;

import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.project.customer.domain.Contract;
import com.ruoyi.project.customer.domain.Customer;
import com.ruoyi.project.employee.domain.EmpCost;
import com.ruoyi.project.employee.domain.EmpDocument;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.employee.mapper.EmpCostMapper;
import com.ruoyi.project.project.domain.*;
import com.ruoyi.project.project.mapper.ProInfoMapper;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Component
public class EmpcostUtil {
    @Autowired
    private ProInfoMapper proInfoMapper;

    @Autowired
    private EmpCostMapper empCostMapper;
    public static EmpcostUtil empcostUtil;
    @PostConstruct
    public void init(){
        empcostUtil = this;
    }

    public Percost selectEmpCostById(EmployeeInfo employeeInfo,String item_id) {
            Percost percost = new Percost();
            DecimalFormat df = new DecimalFormat("#,##0.00");
            Item item = empcostUtil.proInfoMapper.selectProById(item_id);
            /*EmpSalaryinfo empSalaryinfo = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo_first = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo_end = new EmpSalaryinfo();*/
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN);
//            EmpSalaryinfo empSalaryinfo_change = new EmpSalaryinfo();

            String empid = employeeInfo.getEmpId();
            EmpCost empcost=empcostUtil.empCostMapper.selectByEmp(empid);
            if(empcost==null){
                return percost;
            }
            EmpCost empcost_his = empcostUtil.proInfoMapper.selectHisSalaryById(empid);
            if(empcost_his == null){
                //工资无变更人员
                EmpCost empCost = employeeInfo.getCost();
                Itemper itemper = employeeInfo.getBinding();

                Double salary = empCost.getEmpSalary();
                BigDecimal emp_Salary = new BigDecimal(salary);
                BigDecimal empSalary = new BigDecimal("0");
                if(employeeInfo.getEmpState().equals("3")){
                    empSalary = emp_Salary.multiply(new BigDecimal("0.8"));
                }else{
                    empSalary = emp_Salary;
                }
                Double insurance = empCost.getEmpInsurance();
                BigDecimal empInsurance = new BigDecimal(insurance);
                Double Fund = empCost.getEmpFund();
                BigDecimal empFund = new BigDecimal(Fund);
                Itemper emper =new Itemper();
                if(item.getItem_type().equals("2")){
                    itemper.setItemid(item_id);
                    itemper.setItem_level_id(employeeInfo.getItem_level_id());
                    itemper.setEmpid(employeeInfo.getEmpId());
                    emper =empcostUtil.proInfoMapper.selectbundling(itemper);
                }else{
                    itemper.setItemid(item_id);
                    itemper.setEmpid(employeeInfo.getEmpId());
                    emper = empcostUtil.proInfoMapper.selectUnbundling(itemper);
                }

                String startdate = emper.getStartdate();
                String endDate = "";
                if(emper.getEnddate() == null || emper.getEnddate().equals("")){
                    endDate = sdf.format(new Date());
                }else {
                    endDate = emper.getEnddate();
                }
                //获取日期范围内的月份
                List<String> list =new ArrayList<>();
                try {
                    list = GetDateUtils.getYearMonth(startdate,endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                BigDecimal empTotalSalary = new BigDecimal("0");
                //开始日期和结束日期相隔天数
                int daysapart = DateDiffMonth.nDaysBetweenTwoDate(startdate,endDate);
                if(daysapart<=1){
                    return  percost;
                }
                String yearmonth_first = startdate.substring(0,7);
                String yearmonth_end = endDate.substring(0,7);
                if(yearmonth_first.equals(yearmonth_end)){
                    //获取开始日期当月的最大天数
                    int maxday = 0;
                    try {
                        maxday = GetDateUtils.getDaysOfMonth(startdate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BigDecimal maxdays = new BigDecimal(maxday);
                    //计算出天数百分比
                    BigDecimal workday = new BigDecimal(daysapart);
                    BigDecimal Percentage = workday.divide(maxdays,mc);
                    //当月天数对应的工资
                    BigDecimal empSalary_month = empSalary.multiply(Percentage);
                    //当月天数对应的五险一金
                    BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                    //当月五险和税前工资合计
                    BigDecimal empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                    empTotalSalary = empMonthSalary_Total;

                }else if(list.size()==1){
                    //获取开始日期是当月的第几天
                    int numday = GetDateUtils.getMonthday(startdate);
                    BigDecimal numdays = new BigDecimal(numday);
                    //获取开始日期当月的最大天数
                    int maxday = 0;
                    try {
                        maxday = GetDateUtils.getDaysOfMonth(startdate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BigDecimal maxdays = new BigDecimal(maxday);
                    //计算出天数百分比
                    BigDecimal workday = maxdays.subtract(numdays);
                    BigDecimal Percentage = workday.divide(maxdays,mc);
                    //当月天数对应的工资
                    BigDecimal empSalary_month = empSalary.multiply(Percentage);
                    //当月天数对应的五险一金
                    BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                    //当月五险和税前工资合计
                    BigDecimal empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);

                    //获取结束日期是当月的第几天
                    int numday_end = GetDateUtils.getMonthday(endDate);
                    BigDecimal numdays_end = new BigDecimal(numday_end);
                    //获取开始日期当月的最大天数
                    int maxday_end = 0;
                    try {
                        maxday_end = GetDateUtils.getDaysOfMonth(endDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BigDecimal maxdays_end = new BigDecimal(maxday_end);
                    //计算出天数百分比
                    BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                    //当月天数对应的工资
                    BigDecimal empSalary_month_end = empSalary.multiply(Percentage_end);
                    //当月天数对应的五险一金
                    BigDecimal InsuranceFund_month_end = (empFund.add(empInsurance)).multiply(Percentage_end);
                    //当月五险和税前工资合计
                    BigDecimal empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);
                    //总合计
                    empTotalSalary = empMonthSalary_Total.add(empMonthSalary_Total_end);
                }else if(list.size()>1){
                    //获取开始日期是当月的第几天
                    int numday = GetDateUtils.getMonthday(startdate);
                    BigDecimal numdays = new BigDecimal(numday);
                    //获取开始日期当月的最大天数
                    int maxday = 0;
                    try {
                        maxday = GetDateUtils.getDaysOfMonth(startdate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BigDecimal maxdays = new BigDecimal(maxday);
                    //计算出天数百分比
                    BigDecimal workday = maxdays.subtract(numdays);
                    BigDecimal Percentage = workday.divide(maxdays,mc);
                    //当月天数对应的工资
                    BigDecimal empSalary_month = empSalary.multiply(Percentage);
                    //当月天数对应的五险一金
                    BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                    //当月五险和税前工资合计
                    BigDecimal empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);

                    //获取结束日期是当月的第几天
                    int numday_end = GetDateUtils.getMonthday(endDate);
                    BigDecimal numdays_end = new BigDecimal(numday_end);
                    //获取开始日期当月的最大天数
                    int maxday_end = 0;
                    try {
                        maxday_end = GetDateUtils.getDaysOfMonth(endDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BigDecimal maxdays_end = new BigDecimal(maxday_end);
                    //计算出天数百分比
                    BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                    //当月天数对应的工资
                    BigDecimal empSalary_month_end = empSalary.multiply(Percentage_end);
                    //当月天数对应的五险一金
                    BigDecimal InsuranceFund_month_end = (empFund.add(empInsurance)).multiply(Percentage_end);
                    //当月五险和税前工资合计
                    BigDecimal empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);
                    BigDecimal itemcycle = new BigDecimal(list.size()-1);
                    BigDecimal fund = empFund.multiply(itemcycle);
                    BigDecimal Insurance = empInsurance.multiply(itemcycle);
                    BigDecimal empInsuranceFund = fund.add(Insurance);
                    BigDecimal Salary = empSalary.multiply(itemcycle);
                    empTotalSalary = Salary.add(empInsuranceFund).add(empMonthSalary_Total).add(empMonthSalary_Total_end);

                }
                percost.setEmpTotalSalary(String.valueOf(empTotalSalary));
            }else {
                //工资有变更人员,变更前工资
                Double salary1 = empcost_his.getEmpSalary();
                BigDecimal hsalary = new BigDecimal(salary1);
                //变更前工资
                BigDecimal hissalary = new BigDecimal("0");
                if(employeeInfo.getEmpState().equals("3")){
                    hissalary = hsalary.multiply(new BigDecimal("0.8"));
                }else{
                    hissalary = hsalary;
                }
                //变更前五险一金
                Double insurance1 = empcost_his.getEmpInsurance();
                BigDecimal hisinsurance = new BigDecimal(insurance1);
                Double Fund1 = empcost_his.getEmpFund();
                BigDecimal hisfund = new BigDecimal(Fund1);

                Date hisupdateTime = empcost_his.getUpdateTime();
                String end = sdf.format(hisupdateTime);
                String[] enddatehis = end.split(" ");
                //工资变更日期
                String endDatehis = enddatehis[0];
                String enddatechange = endDatehis.replace("-", "");

                EmpCost empCost = employeeInfo.getCost();
                Itemper itemper = employeeInfo.getBinding();
                Double salary = empCost.getEmpSalary();
                //变更后工资
                BigDecimal emp_Salary = new BigDecimal(salary);
                BigDecimal empSalary = new BigDecimal("0");
                if(employeeInfo.getEmpState().equals("3")){
                    empSalary = emp_Salary.multiply(new BigDecimal("0.8"));
                }else{
                    empSalary = emp_Salary;
                }
                //变更后五险一金
                Double insurance = empCost.getEmpInsurance();
                BigDecimal empInsurance = new BigDecimal(insurance);
                Double Fund = empCost.getEmpFund();
                BigDecimal empFund = new BigDecimal(Fund);
                //是否解绑，解绑日期
                Itemper itemper1 = new Itemper();
                if(item.getItem_type().equals("2")){
                    itemper.setItemid(item_id);
                    itemper.setItem_level_id(employeeInfo.getItem_level_id());
                    itemper.setEmpid(employeeInfo.getEmpId());
                    //人员外包项目
                    itemper1 =empcostUtil.proInfoMapper.selectbundling(itemper);
                }else{
                    itemper.setItemid(item_id);
                    itemper.setEmpid(employeeInfo.getEmpId());
                    //项目外包
                    itemper1 = empcostUtil.proInfoMapper.selectUnbundling(itemper);
                }

                String startdate = itemper1.getStartdate();
//                    if(Integer.parseInt(enddatechange)<=Integer.parseInt(updatechange)){
                String endDate_bs = "";//变更日
                String endDate_be = "";//到当前日
                if (itemper1.getEnddate() == null || itemper1.getEnddate().equals("")) {//无解绑
                    endDate_bs = sdf.format(new Date());
                    String endDatebs = endDate_bs.replace("-", "");
                    String startdate1 = startdate.replace("-", "");
                    if (Integer.parseInt(endDatebs) > Integer.parseInt(enddatechange)) {//当前日期大于变更日期
                        endDate_bs = endDatehis;
                        endDate_be = sdf.format(new Date());
                        if(Integer.parseInt(startdate1) > Integer.parseInt(enddatechange) ){//绑定日期大于变更日期
                            endDate_bs = sdf.format(new Date());
                            endDate_be = "";
                        }
                    }
                } else {//解绑
                    String endDatebs = itemper1.getEnddate().replace("-", "");
                    endDate_bs = itemper1.getEnddate();
                    String startdate1 = startdate.replace("-", "");
                    if (Integer.parseInt(endDatebs) > Integer.parseInt(enddatechange)) {//解绑日期大于变更日期
                        endDate_bs = endDatehis;
                        endDate_be = itemper1.getEnddate();
                        if(Integer.parseInt(startdate1) > Integer.parseInt(enddatechange) ){//绑定日期大于变更日期
                            endDate_bs = itemper1.getEnddate();
                            endDate_be = "";
                        }
                    }
                }

                //获取日期范围内的月份(变更前)
                List<String> list_c =new ArrayList<>();
                try {
                    list_c = GetDateUtils.getYearMonth(startdate,endDate_bs);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //获取日期范围内的月份(变更后)
                List<String> list =new ArrayList<>();
                if(!endDate_be.equals("")) {
                    try {
                        list = GetDateUtils.getYearMonth(endDate_bs, endDate_be);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                BigDecimal itemcycle_bs = new BigDecimal("0");
                BigDecimal itemcycle_be = new BigDecimal("0");
                if(list_c.size()>0){
                    itemcycle_bs = new BigDecimal(list_c.size()-1);
                }
                //变更前的工资
                BigDecimal empMonthInsuranceFund_c = hisinsurance.add(hisfund);
                BigDecimal empMonthTotalSalary_c  = hissalary.add(empMonthInsuranceFund_c );
                //变更后工资
                BigDecimal empMonthInsuranceFund = empInsurance.add(empFund);
                BigDecimal empMonthTotalSalary = empSalary.add(empMonthInsuranceFund);

                BigDecimal empMonthSalary_Total_end = new BigDecimal("0");
                BigDecimal empMonthSalary_Total = new BigDecimal("0");
                BigDecimal empTotalSalary = new BigDecimal("0");
                //绑定日期
                int ksr = Integer.parseInt(startdate.replace("-",""));
                //变更日期
                int bgr = Integer.parseInt(endDate_bs.replace("-",""));

                if(!endDate_be.equals("")){
                    //变更月当月还是变更前工资
                    //获取开始日期是当月的第几天
                    int numday = GetDateUtils.getMonthday(startdate);
                    BigDecimal numdays = new BigDecimal(numday);
                    //获取开始日期当月的最大天数
                    int maxday = 0;
                    try {
                        maxday = GetDateUtils.getDaysOfMonth(startdate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BigDecimal maxdays = new BigDecimal(maxday);
                    //计算出天数百分比
                    BigDecimal workday = maxdays.subtract(numdays);
                    BigDecimal Percentage = workday.divide(maxdays,mc);
                    //当月天数对应的工资
                    BigDecimal empSalary_month = hissalary.multiply(Percentage);
                    //当月天数对应的五险一金
                    BigDecimal InsuranceFund_month = (hisfund.add(hisinsurance)).multiply(Percentage);
                    //当月五险和税前工资合计
                    empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                    //获取结束日期是当月的第几天
                    int numday_end = GetDateUtils.getMonthday(endDate_be);
                    BigDecimal numdays_end = new BigDecimal(numday_end);
                    //获取开始日期当月的最大天数
                    int maxday_end = 0;
                    try {
                        maxday_end = GetDateUtils.getDaysOfMonth(endDate_be);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BigDecimal maxdays_end = new BigDecimal(maxday_end);
                    //计算出天数百分比
                    BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                    BigDecimal empSalary_month_end = new BigDecimal("0");
                    BigDecimal InsuranceFund_month_end = new BigDecimal("0");
                    String start_bs = endDate_bs.substring(0,7);
                    String end_be = endDate_be.substring(0,7);
                    if(start_bs.equals(end_be)){
                        //当月天数对应的工资
                        empSalary_month_end = hissalary.multiply(Percentage_end);
                        //当月天数对应的五险一金
                        InsuranceFund_month_end = (hisfund.add(hisinsurance)).multiply(Percentage_end);
                    }else{
                        //当月天数对应的工资
                        empSalary_month_end = empSalary.multiply(Percentage_end);
                        //当月天数对应的五险一金
                        InsuranceFund_month_end = (empFund.add(empInsurance)).multiply(Percentage_end);
                    }

                    //当月五险和税前工资合计
                    empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);

                    itemcycle_be = new BigDecimal(list.size()-1);
                    BigDecimal total_bs = itemcycle_bs.multiply(empMonthTotalSalary_c);
                    BigDecimal total_be = itemcycle_be.multiply(empMonthTotalSalary).add(empMonthTotalSalary_c);
                    BigDecimal total = total_bs.add(total_be).add(empMonthSalary_Total).add(empMonthSalary_Total_end);
                    percost.setEmpTotalSalary(String.valueOf(total));

                }else {
                    //开始日期和结束日期相隔天数
                    int daysapart = DateDiffMonth.nDaysBetweenTwoDate(startdate,endDate_bs);
                    if(daysapart<=1){
                        return  percost;
                    }
                    if (Integer.parseInt(enddatechange) < ksr) {
                        String yearmonth_start = startdate.substring(0,7);
                        String yearmonth_End = endDate_bs.substring(0,7);
                        if(yearmonth_start.equals(yearmonth_End)){
                            //按当前工资
                            String year_end = GetDateUtils.getSysYear();
                            //获取开始日期是当月的第几天
                            int numday = GetDateUtils.getMonthday(startdate);
                            BigDecimal numdays = new BigDecimal(numday);
                            //获取开始日期当月的最大天数
                            int maxday = 0;
                            try {
                                maxday = GetDateUtils.getDaysOfMonth(startdate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays = new BigDecimal(maxday);
                            //计算出天数百分比
                            BigDecimal workday = new BigDecimal(daysapart);
                            BigDecimal Percentage = workday.divide(maxdays,mc);
                            //当月天数对应的工资
                            BigDecimal empSalary_month = empSalary.multiply(Percentage);
                            //当月天数对应的五险一金
                            BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                            //当月五险和税前工资合计
                            empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);

                            empTotalSalary = empMonthSalary_Total;
                            percost.setEmpTotalSalary(String.valueOf(empTotalSalary));
                        }else if(list_c.size()==1){
                            //按当前工资
                            //获取开始日期是当月的第几天
                            int numday = GetDateUtils.getMonthday(startdate);
                            BigDecimal numdays = new BigDecimal(numday);
                            //获取开始日期当月的最大天数
                            int maxday = 0;
                            try {
                                maxday = GetDateUtils.getDaysOfMonth(startdate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays = new BigDecimal(maxday);
                            //计算出天数百分比
                            BigDecimal workday = maxdays.subtract(numdays);
                            BigDecimal Percentage = workday.divide(maxdays,mc);
                            //当月天数对应的工资
                            BigDecimal empSalary_month = empSalary.multiply(Percentage);
                            //当月天数对应的五险一金
                            BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                            //当月五险和税前工资合计
                            empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                            //获取结束日期是当月的第几天
                            int numday_end = GetDateUtils.getMonthday(endDate_bs);
                            BigDecimal numdays_end = new BigDecimal(numday_end);
                            //获取开始日期当月的最大天数
                            int maxday_end = 0;
                            try {
                                maxday_end = GetDateUtils.getDaysOfMonth(endDate_bs);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays_end = new BigDecimal(maxday_end);
                            //计算出天数百分比
                            BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                            BigDecimal empSalary_month_end = new BigDecimal("0");
                            BigDecimal InsuranceFund_month_end = new BigDecimal("0");
                            //当月天数对应的工资
                            empSalary_month_end = empSalary.multiply(Percentage_end);
                            //当月天数对应的五险一金
                            InsuranceFund_month_end = (empFund.add(empInsurance)).multiply(Percentage_end);
                            //当月五险和税前工资合计
                            empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);

                            empTotalSalary = empMonthSalary_Total.add(empMonthSalary_Total_end);
                            percost.setEmpTotalSalary(String.valueOf(empTotalSalary));
                        }else if(list_c.size()>1){
                            //按当前工资
                            //获取开始日期是当月的第几天
                            int numday = GetDateUtils.getMonthday(startdate);
                            BigDecimal numdays = new BigDecimal(numday);
                            //获取开始日期当月的最大天数
                            int maxday = 0;
                            try {
                                maxday = GetDateUtils.getDaysOfMonth(startdate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays = new BigDecimal(maxday);
                            //计算出天数百分比
                            BigDecimal workday = maxdays.subtract(numdays);
                            BigDecimal Percentage = workday.divide(maxdays,mc);
                            //当月天数对应的工资
                            BigDecimal empSalary_month = empSalary.multiply(Percentage);
                            //当月天数对应的五险一金
                            BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                            //当月五险和税前工资合计
                            empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);

                            //获取结束日期是当月的第几天
                            int numday_end = GetDateUtils.getMonthday(endDate_bs);
                            BigDecimal numdays_end = new BigDecimal(numday_end);
                            //获取开始日期当月的最大天数
                            int maxday_end = 0;
                            try {
                                maxday_end = GetDateUtils.getDaysOfMonth(endDate_bs);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays_end = new BigDecimal(maxday_end);
                            //计算出天数百分比
                            BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                            BigDecimal empSalary_month_end = new BigDecimal("0");
                            BigDecimal InsuranceFund_month_end = new BigDecimal("0");
                            //当月天数对应的工资
                            empSalary_month_end = empSalary.multiply(Percentage_end);
                            //当月天数对应的五险一金
                            InsuranceFund_month_end = (empFund.add(empInsurance)).multiply(Percentage_end);
                            //当月五险和税前工资合计
                            empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);

                            BigDecimal itemcycle = new BigDecimal(list_c.size()-1);
                            BigDecimal fund = empFund.multiply(itemcycle);
                            BigDecimal Insurance = empInsurance.multiply(itemcycle);
                            BigDecimal empInsuranceFund = fund.add(Insurance);
                            BigDecimal Salary = empSalary.multiply(itemcycle);
                            empTotalSalary = Salary.add(empInsuranceFund).add(empMonthSalary_Total).add(empMonthSalary_Total_end);

                            percost.setEmpTotalSalary(String.valueOf(empTotalSalary));
                        }

                    } else if (Integer.parseInt(enddatechange) > bgr) {
                        //按历史工资
                        String yearmonth_start = startdate.substring(0,7);
                        String yearmonth_End = endDate_bs.substring(0,7);
                        if(yearmonth_start.equals(yearmonth_End)){
                            //按当前工资
                            //获取开始日期是当月的第几天
                            int numday = GetDateUtils.getMonthday(startdate);
                            BigDecimal numdays = new BigDecimal(numday);
                            //获取开始日期当月的最大天数
                            int maxday = 0;
                            try {
                                maxday = GetDateUtils.getDaysOfMonth(startdate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays = new BigDecimal(maxday);
                            //计算出天数百分比
                            BigDecimal workday = new BigDecimal(daysapart);
                            BigDecimal Percentage = workday.divide(maxdays,mc);
                            //当月天数对应的工资
                            BigDecimal empSalary_month = hissalary.multiply(Percentage);
                            //当月天数对应的五险一金
                            BigDecimal InsuranceFund_month = (hisfund.add(hisinsurance)).multiply(Percentage);
                            //当月五险和税前工资合计
                            empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);

                            empTotalSalary = empMonthSalary_Total;
                            percost.setEmpTotalSalary(String.valueOf(empTotalSalary));

                        }else if(list_c.size()==1){
                            //按当前工资
                            //获取开始日期是当月的第几天
                            int numday = GetDateUtils.getMonthday(startdate);
                            BigDecimal numdays = new BigDecimal(numday);
                            //获取开始日期当月的最大天数
                            int maxday = 0;
                            try {
                                maxday = GetDateUtils.getDaysOfMonth(startdate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays = new BigDecimal(maxday);
                            //计算出天数百分比
                            BigDecimal workday = maxdays.subtract(numdays);
                            BigDecimal Percentage = workday.divide(maxdays,mc);
                            //当月天数对应的工资
                            BigDecimal empSalary_month = hissalary.multiply(Percentage);
                            //当月天数对应的五险一金
                            BigDecimal InsuranceFund_month = (hisfund.add(hisinsurance)).multiply(Percentage);
                            //当月五险和税前工资合计
                            empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);

                            //获取结束日期是当月的第几天
                            int numday_end = GetDateUtils.getMonthday(endDate_bs);
                            BigDecimal numdays_end = new BigDecimal(numday_end);
                            //获取开始日期当月的最大天数
                            int maxday_end = 0;
                            try {
                                maxday_end = GetDateUtils.getDaysOfMonth(endDate_bs);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays_end = new BigDecimal(maxday_end);
                            //计算出天数百分比
                            BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                            BigDecimal empSalary_month_end = new BigDecimal("0");
                            BigDecimal InsuranceFund_month_end = new BigDecimal("0");
                            //当月天数对应的工资
                            empSalary_month_end = hissalary.multiply(Percentage_end);
                            //当月天数对应的五险一金
                            InsuranceFund_month_end = (hisfund.add(hisinsurance)).multiply(Percentage_end);
                            //当月五险和税前工资合计
                            empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);


                            empTotalSalary = empMonthSalary_Total.add(empMonthSalary_Total_end);
                            percost.setEmpTotalSalary(String.valueOf(empTotalSalary));
                        }else if(list_c.size()>1){
                            //按当前工资
                            //获取开始日期是当月的第几天
                            int numday = GetDateUtils.getMonthday(startdate);
                            BigDecimal numdays = new BigDecimal(numday);
                            //获取开始日期当月的最大天数
                            int maxday = 0;
                            try {
                                maxday = GetDateUtils.getDaysOfMonth(startdate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays = new BigDecimal(maxday);
                            //计算出天数百分比
                            BigDecimal workday = maxdays.subtract(numdays);
                            BigDecimal Percentage = workday.divide(maxdays,mc);
                            //当月天数对应的工资
                            BigDecimal empSalary_month = hissalary.multiply(Percentage);
                            //当月天数对应的五险一金
                            BigDecimal InsuranceFund_month = (hisfund.add(hisinsurance)).multiply(Percentage);
                            //当月五险和税前工资合计
                            empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                            //获取结束日期是当月的第几天
                            int numday_end = GetDateUtils.getMonthday(endDate_bs);
                            BigDecimal numdays_end = new BigDecimal(numday_end);
                            //获取开始日期当月的最大天数
                            int maxday_end = 0;
                            try {
                                maxday_end = GetDateUtils.getDaysOfMonth(endDate_bs);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays_end = new BigDecimal(maxday_end);
                            //计算出天数百分比
                            BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                            BigDecimal empSalary_month_end = new BigDecimal("0");
                            BigDecimal InsuranceFund_month_end = new BigDecimal("0");
                            //当月天数对应的工资
                            empSalary_month_end = hissalary.multiply(Percentage_end);
                            //当月天数对应的五险一金
                            InsuranceFund_month_end = (hisfund.add(hisinsurance)).multiply(Percentage_end);
                            //当月五险和税前工资合计
                            empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);

                            BigDecimal itemcycle = new BigDecimal(list_c.size()-1);
                            BigDecimal fund = hisfund.multiply(itemcycle);
                            BigDecimal Insurance = hisinsurance.multiply(itemcycle);
                            BigDecimal empInsuranceFund = fund.add(Insurance);
                            BigDecimal Salary = hissalary.multiply(itemcycle);
                            empTotalSalary = Salary.add(empInsuranceFund).add(empMonthSalary_Total).add(empMonthSalary_Total_end);

                            percost.setEmpTotalSalary(String.valueOf(empTotalSalary));
                        }

                    }
                }

            }
        return percost;
    }
}
