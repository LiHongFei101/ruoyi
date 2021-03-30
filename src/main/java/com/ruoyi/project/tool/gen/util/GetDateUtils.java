package com.ruoyi.project.tool.gen.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetDateUtils {
    /**
     * 得到日期范围内的年月
     *
     * @param
     * @return
     */
    public static List<String> getYearMonth(String startDate, String endDate) throws ParseException {
        Date d1 = new SimpleDateFormat("yyyy-MM").parse(startDate);//定义bai起始日期

        Date d2 = new SimpleDateFormat("yyyy-MM").parse(endDate);//定义结束日期

        Calendar dd = Calendar.getInstance();//定义日期实例

        dd.setTime(d1);//设置日期起始时间
        List<String> list = new ArrayList<String>();
        while(dd.getTime().before(d2)){//判断是否到结束日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String str = sdf.format(dd.getTime());
            list.add(str);
            dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
        }
        return  list;
    }
    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }
    //获取某个日期在这个月第几天
    public static int getMonthday(String time){
        int days = 0;
        try{
            DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fm.parse(time);
            //Date date2 = new Date(System.currentTimeMillis());
            String str = String.format("%te",date);//得到time日期是在这月的第几天
            days = Integer.parseInt(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return days;
    }

    //获取某月的最大天数
    public static int getDaysOfMonth(String time) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    //添加一个月
    public static String addMonth(String time) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        String ymd = sdf.format(calendar.getTime());
        return ymd;
    }
    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getCurrYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }
    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getCurrYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

}
