package com.ruoyi.project.employee.mapper;

import com.ruoyi.project.employee.domain.DocBindBatch;

import java.util.List;

public interface DocBindBatchMapper {
    int deleteByPrimaryKey(String id);

    int insert(DocBindBatch record);

    int insertSelective(DocBindBatch record);

    DocBindBatch selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocBindBatch record);

    int updateByPrimaryKey(DocBindBatch record);

    List<String> selectAllZip();

    List<DocBindBatch> selectByWhere(DocBindBatch docBindBatch);
}