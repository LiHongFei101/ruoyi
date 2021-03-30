package com.ruoyi.project.project.mapper;

import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.project.domain.Empidle;

import java.util.List;

/**
 * Created by lenovo on 2020/8/27.
 */
public interface EmpIdleMapper {

    int deleteByIds(String[] idleIds);

    int deleteById(String idleId);

    List<Empidle> selectByEmp(String empId);

    EmployeeInfo selectByempId(String[] idleId);
}
