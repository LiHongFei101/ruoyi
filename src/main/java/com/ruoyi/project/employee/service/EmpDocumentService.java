package com.ruoyi.project.employee.service;

import com.ruoyi.project.employee.domain.EmpDocument;

import java.util.List;

/**
 * Created by lenovo on 2020/5/21.
 */
public interface EmpDocumentService {

    int deleteByPrimaryKey(String docId);

    int deleteByIds(String[] docIds);

    int insert(EmpDocument record);

    int insertSelective(EmpDocument record);

    EmpDocument selectByPrimaryKey(String docId);

    int updateByPrimaryKeySelective(EmpDocument record);

    int updateByPrimaryKey(EmpDocument record);

    List<EmpDocument> selectByIds(String[] ids);

    List<EmpDocument> selectByEmp(String empId);

}
