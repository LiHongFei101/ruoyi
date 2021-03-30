package com.ruoyi.project.tool.gen.util;

import com.ruoyi.project.project.domain.EmpSalaryinfo;
import com.ruoyi.project.project.domain.Monthname;

import java.util.List;

public class PerCostAndIncomeUtil {
    //年份
    private static String year = GetDateUtils.getSysYear();

    public  Monthname SetMonthValue(String yearmonth, EmpSalaryinfo empSalaryinfo,Monthname monthname){
        if((year+"-01").equals(yearmonth) ){
            monthname.setJanuary(empSalaryinfo);
        }else if((year+"-02").equals(yearmonth)  ){
            monthname.setFebruary(empSalaryinfo);
        }else if((year+"-03").equals(yearmonth)  ){
            monthname.setMarch(empSalaryinfo);
        }else if((year+"-04").equals(yearmonth)  ){
            monthname.setApril(empSalaryinfo);
        }else if((year+"-05").equals(yearmonth) ){
            monthname.setMay(empSalaryinfo);
        }else if((year+"-06").equals(yearmonth) ){
            monthname.setJune(empSalaryinfo);
        }else if((year+"-07").equals(yearmonth) ){
            monthname.setJuly(empSalaryinfo);
        }else if((year+"-08").equals(yearmonth)  ){
            monthname.setAugust(empSalaryinfo);
        }else if((year+"-09").equals(yearmonth)  ){
            monthname.setSeptember(empSalaryinfo);
        }else if((year+"-10").equals(yearmonth)  ){
            monthname.setOctober(empSalaryinfo);
        }else if((year+"-11").equals(yearmonth)  ){
            monthname.setNovember(empSalaryinfo);
        }else if((year+"-12").equals(yearmonth)  ){
            monthname.setDecember(empSalaryinfo);
        }
        return monthname;
    }
    public  Monthname SetMonthListValue(List<String> list, EmpSalaryinfo empSalaryinfo,Monthname monthname){
        for(int a = 1;a<list.size();a++){
            String yearmonth = list.get(a);
            if((year+"-01").equals(yearmonth) ){
                monthname.setJanuary(empSalaryinfo);
            }else if((year+"-02").equals(yearmonth)  ){
                monthname.setFebruary(empSalaryinfo);
            }else if((year+"-03").equals(yearmonth)  ){
                monthname.setMarch(empSalaryinfo);
            }else if((year+"-04").equals(yearmonth)  ){
                monthname.setApril(empSalaryinfo);
            }else if((year+"-05").equals(yearmonth) ){
                monthname.setMay(empSalaryinfo);
            }else if((year+"-06").equals(yearmonth) ){
                monthname.setJune(empSalaryinfo);
            }else if((year+"-07").equals(yearmonth) ){
                monthname.setJuly(empSalaryinfo);
            }else if((year+"-08").equals(yearmonth)  ){
                monthname.setAugust(empSalaryinfo);
            }else if((year+"-09").equals(yearmonth)  ){
                monthname.setSeptember(empSalaryinfo);
            }else if((year+"-10").equals(yearmonth)  ){
                monthname.setOctober(empSalaryinfo);
            }else if((year+"-11").equals(yearmonth)  ){
                monthname.setNovember(empSalaryinfo);
            }else if((year+"-12").equals(yearmonth)  ){
                monthname.setDecember(empSalaryinfo);
            }
        }
        return monthname;
    }
    public  Monthname SetMonthListEndValue(List<String> list, EmpSalaryinfo empSalaryinfo,Monthname monthname){
        for(int a = 0;a<list.size();a++){
            String yearmonth = list.get(a);
            if((year+"-01").equals(yearmonth) ){
                monthname.setJanuary(empSalaryinfo);
            }else if((year+"-02").equals(yearmonth)  ){
                monthname.setFebruary(empSalaryinfo);
            }else if((year+"-03").equals(yearmonth)  ){
                monthname.setMarch(empSalaryinfo);
            }else if((year+"-04").equals(yearmonth)  ){
                monthname.setApril(empSalaryinfo);
            }else if((year+"-05").equals(yearmonth) ){
                monthname.setMay(empSalaryinfo);
            }else if((year+"-06").equals(yearmonth) ){
                monthname.setJune(empSalaryinfo);
            }else if((year+"-07").equals(yearmonth) ){
                monthname.setJuly(empSalaryinfo);
            }else if((year+"-08").equals(yearmonth)  ){
                monthname.setAugust(empSalaryinfo);
            }else if((year+"-09").equals(yearmonth)  ){
                monthname.setSeptember(empSalaryinfo);
            }else if((year+"-10").equals(yearmonth)  ){
                monthname.setOctober(empSalaryinfo);
            }else if((year+"-11").equals(yearmonth)  ){
                monthname.setNovember(empSalaryinfo);
            }else if((year+"-12").equals(yearmonth)  ){
                monthname.setDecember(empSalaryinfo);
            }
        }
        return monthname;
    }
}
