package com.ruoyi.project.tool.gen.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 校验是否是数字组成的
     * @param str
     * @return boolean值
     */
    public static final boolean isLetterAndNum(String str){
        String pattern = "[0-9]+$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str.trim());
        return m.matches();
    }
    /**
     * 校验是否是金额
     * @param str
     * @return boolean值
     */
    public static final boolean isNumeric(String str){
        String pattern = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str.trim());
        return m.matches();
    }
}
