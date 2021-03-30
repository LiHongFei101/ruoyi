package com.ruoyi.project.employee.service.impl;

import com.ruoyi.project.employee.domain.EmpDocument;
import com.ruoyi.project.employee.mapper.EmpDocumentMapper;
import com.ruoyi.project.employee.service.EmpDocumentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lenovo on 2020/5/22.
 */
public class EmpDocumentServiceImpl implements EmpDocumentService {

    @Autowired
    EmpDocumentMapper empDocumentMapper;

    @Override
    public int deleteByPrimaryKey(String docId) {
        return empDocumentMapper.deleteByPrimaryKey(docId);
    }

    @Override
    public int deleteByIds(String[] docIds){
        return empDocumentMapper.deleteByIds(docIds);
    };

    @Override
    public int insert(EmpDocument record) {
        return empDocumentMapper.insert(record);
    }

    @Override
    public int insertSelective(EmpDocument record) {
        return empDocumentMapper.insertSelective(record);
    }

    @Override
    public EmpDocument selectByPrimaryKey(String docId) {
        return empDocumentMapper.selectByPrimaryKey(docId);
    }

    @Override
    public int updateByPrimaryKeySelective(EmpDocument record) {
        return empDocumentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(EmpDocument record) {
        return empDocumentMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<EmpDocument> selectByIds(String[] ids) {
        return empDocumentMapper.selectByIds(ids);
    }

    @Override
    public List<EmpDocument> selectByEmp(String empId) {
        return empDocumentMapper.selectByEmp(empId);
    }
}
