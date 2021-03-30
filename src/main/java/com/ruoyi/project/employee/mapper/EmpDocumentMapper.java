package com.ruoyi.project.employee.mapper;


import com.ruoyi.project.employee.domain.EmpDocument;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpDocumentMapper {
    int deleteByPrimaryKey(String docId);

    int deleteByIds(String[] docIds);

    int insert(EmpDocument record);

    int insertSelective(EmpDocument record);

    EmpDocument selectByPrimaryKey(String docId);

    int updateByPrimaryKeySelective(EmpDocument record);

    int updateByPrimaryKey(EmpDocument record);

    List<EmpDocument> selectByIds(String[] ids);

    List<EmpDocument> selectByEmp(String empId);

    List<EmpDocument> selectByItem(@Param(value = "docType") String docType,
                                   @Param(value = "itemId")String itemId);
}