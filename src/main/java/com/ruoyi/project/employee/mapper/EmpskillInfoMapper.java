package com.ruoyi.project.employee.mapper;


import com.ruoyi.project.employee.domain.EmpskillInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpskillInfoMapper {

    int deleteByPrimaryKey(String skillId);

    int deleteByIds(String[] skillIds);

    int insert(EmpskillInfo record);

    int insertSelective(EmpskillInfo record);

    EmpskillInfo selectByPrimaryKey(String skillId);

    int updateByPrimaryKeySelective(EmpskillInfo record);

    int updateByPrimaryKey(EmpskillInfo record);

    List<EmpskillInfo> selectByEmp(String empId);

    List<EmpskillInfo> selectByName(EmpskillInfo record);

}