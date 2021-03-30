package com.ruoyi.project.employee.service;

import com.ruoyi.project.employee.domain.DocBindBatch;


import java.util.List;

/**
 * Created by lenovo on 2020/8/11.
 */
public interface DocBindBatchService {

    List<String> selectAllZip();

    List<String> getBindZip();

    List<DocBindBatch> selectByWhere(DocBindBatch docBindBatch);

    void autoBindDocToEmp(String userId);

    int deleteByPrimaryKey(String id);

}
