package com.ruoyi.project.tool.gen.util;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.security.AesEncryptUtils;
import springfox.documentation.spring.web.json.Json;

import java.util.Date;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.PrintStream;
import java.util.Scanner;

public class DateDiffMonth {

    public static void main(String [] argv) throws Exception {
//        System.out.println("=============="+getMonthDiff("2019-01-01", "2019-12-31")+"");
        System.out.println("=====nDaysBetweenTwoDate========="+nDaysBetweenTwoDate("2019-01-09", "2019-01-10")+"");
    }


    /**
     * 得到两日期相差几个月
     *
     * @param
     * @return
     */
    public static long getMonthDiff(String startDate, String endDate) throws ParseException {
        long monthday;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate1 = fmt.parse(startDate);

        Calendar starCal = Calendar.getInstance();
        starCal.setTime(startDate1);

        int sYear  = starCal.get(Calendar.YEAR);
        int sMonth = starCal.get(Calendar.MONTH);
        int sDay   = starCal.get(Calendar.DAY_OF_MONTH);

        Date endDate1 = fmt.parse(endDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate1);
        int eYear  = endCal.get(Calendar.YEAR);
        int eMonth = endCal.get(Calendar.MONTH);
        int eDay   = endCal.get(Calendar.DAY_OF_MONTH);

        monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

        //这里计算零头的情况，根据实际确定是否要加1 还是要减1
        if (sDay < eDay) {
            monthday = monthday + 1;
        }
        return monthday;
    }
    // 计算两个日期相隔的天数
    public static int nDaysBetweenTwoDate(String firstString, String secondString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date firstDate = null;
        Date secondDate = null;
        try {
            firstDate = df.parse(firstString);
            secondDate = df.parse(secondString);
        } catch (Exception e) {
            // 日期型字符串格式错误
            System.out.println("日期型字符串格式错误");
        }
        int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
        return nDay;
    }
}
