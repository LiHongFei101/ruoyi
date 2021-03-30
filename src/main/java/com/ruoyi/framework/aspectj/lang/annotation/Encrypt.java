package com.ruoyi.framework.aspectj.lang.annotation;

import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.aspectj.lang.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 自定义数据加密注解
 * 
 * @author ruoyi
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt
{
    /**
     * 模块 
     */
    public String module() default "";

    /**
     * 返回值类型
     */
    public String returnType() default "";

}
