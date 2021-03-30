package com.ruoyi.project.employee.service;

import com.ruoyi.project.employee.domain.EducationInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 服务接口
 * @author CodeGenerator
 * @since 2020-05-10
 */
public interface EducationInfoService {

    int deleteByPrimaryKey(String eduId);

    int deleteByIds(String[] eduIds);

    int insert(EducationInfo record);

    int insertSelective(EducationInfo record);

    EducationInfo selectByPrimaryKey(String eduId);

    int updateByPrimaryKeySelective(EducationInfo record);

    int updateByPrimaryKey(EducationInfo record);

    List<EducationInfo> selectByEmp(String empId);

    String importEdu(List<EducationInfo> eduList, boolean updateSupport);

}
