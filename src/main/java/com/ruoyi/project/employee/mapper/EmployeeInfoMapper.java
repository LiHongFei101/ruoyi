package com.ruoyi.project.employee.mapper;


import com.ruoyi.project.employee.domain.EmpIdleTableExp;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeInfoMapper {
    int deleteByPrimaryKey(String empId);

    int insert(EmployeeInfo record);

    int insertSelective(EmployeeInfo record);

    EmployeeInfo selectByPrimaryKey(String empId);

    int updateByPrimaryKeySelective(EmployeeInfo record);

    int updateByPrimaryKey(EmployeeInfo record);

    List<EmployeeInfo> selectByWhere(EmployeeInfo record);

    List<EmployeeInfo> selectEmpInfoPro(EmployeeInfo record);

    List<Map<String, Object>> selectEmpCost(EmployeeInfo record);

    List<EmpIdleTableExp> selectEmpIdle(EmpIdleTableExp record);

    List<EmpIdleTableExp> selectEmpIdleHistory(EmpIdleTableExp record);

    Integer getGeneratedId(String tableName);

    EmployeeInfo selectByempId(String empId);
}