package com.ruoyi.project.employee.service;

import com.ruoyi.project.employee.domain.EmpskillInfo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 服务接口
 * @author CodeGenerator
 * @since 2020-05-10
 */
public interface EmpskillInfoService {
    int deleteByIds(String[] skillIds);

    int insert(EmpskillInfo record);

    int insertSelective(EmpskillInfo record);

    EmpskillInfo selectByPrimaryKey(String skillId);

    int updateByPrimaryKeySelective(EmpskillInfo record);

    int updateByPrimaryKey(EmpskillInfo record);

    List<EmpskillInfo> selectByEmp(String empId);

    String importSkill(List<EmpskillInfo> skillList, boolean updateSupport);
}
