package com.ruoyi.project.employee.mapper;

import com.ruoyi.project.employee.domain.EmpCost;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpCostMapper {
    int deleteByPrimaryKey(String costId);

    int insert(EmpCost record);

    int insertHistory(EmpCost record);

    int deleteHistoryById(String costId);

    int insertSelective(EmpCost record);

    EmpCost selectByPrimaryKey(Long costId);

    EmpCost selectByEmp(String empId);

    List<EmpCost> selectHistoryByEmp(String empId);

    int updateByPrimaryKeySelective(EmpCost record);

    int updateByPrimaryKey(EmpCost record);


}