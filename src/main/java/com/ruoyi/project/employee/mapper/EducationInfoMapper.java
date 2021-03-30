package com.ruoyi.project.employee.mapper;

import com.ruoyi.project.employee.domain.EducationInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationInfoMapper {
    int deleteByPrimaryKey(String eduId);

    int deleteByIds(String[] eduIds);

    int insert(EducationInfo record);

    int insertSelective(EducationInfo record);

    EducationInfo selectByPrimaryKey(String eduId);

    int updateByPrimaryKeySelective(EducationInfo record);

    int updateByPrimaryKey(EducationInfo record);

    List<EducationInfo> selectByEmp(String empId);

    List<EducationInfo> selectBySchool(EducationInfo record);

}