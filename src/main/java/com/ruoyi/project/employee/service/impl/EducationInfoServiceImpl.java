package com.ruoyi.project.employee.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.employee.domain.EducationInfo;
import com.ruoyi.project.employee.domain.EmpCost;
import com.ruoyi.project.employee.domain.EmpDocument;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.employee.mapper.EducationInfoMapper;
import com.ruoyi.project.employee.mapper.EmpDocumentMapper;
import com.ruoyi.project.employee.mapper.EmployeeInfoMapper;
import com.ruoyi.project.employee.service.EducationInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * 服务实现
 * @author CodeGenerator
 * @since 2020-05-10
 */
@Service
public class EducationInfoServiceImpl implements EducationInfoService {

    @Autowired
    private EducationInfoMapper educationInfoMapper;
    @Autowired
    private EmployeeInfoMapper employeeInfoMapper;
    @Autowired
    private EmpDocumentMapper empDocumentMapper;

    @Override
    @Transactional
    public int deleteByIds(String[] eduIds) {
        for(String eduId : eduIds){
            List<EmpDocument> docs = empDocumentMapper.selectByItem("2",eduId);
            for(EmpDocument doc : docs){
                String path = doc.getDocPath();
                String docRealPath = RuoYiConfig.getProfile() + StringUtils.substringAfter(path, Constants.RESOURCE_PREFIX);
                FileUtils.deleteFile(docRealPath);
                empDocumentMapper.deleteByPrimaryKey(doc.getDocId());
            }
        }
        return educationInfoMapper.deleteByIds(eduIds);
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(String eduId) {
        return educationInfoMapper.deleteByPrimaryKey(eduId);
    }

    @Override
    @Transactional
    public int insert(EducationInfo record) {
        record.setUpdateTime(DateUtils.getNowDate());
        return educationInfoMapper.insert(record);
    }

    @Override
    @Transactional
    public int insertSelective(EducationInfo record) {
        record.setUpdateTime(DateUtils.getNowDate());
        return educationInfoMapper.insertSelective(record);
    }

    @Override
    public EducationInfo selectByPrimaryKey(String eduId) {
        return educationInfoMapper.selectByPrimaryKey(eduId);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(EducationInfo record) {
        record.setUpdateTime(DateUtils.getNowDate());
        return educationInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(EducationInfo record) {
        record.setUpdateTime(DateUtils.getNowDate());
        return educationInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<EducationInfo> selectByEmp(String empId) {
        return educationInfoMapper.selectByEmp(empId);
    }


    @Override
    @Transactional
    public String importEdu(List<EducationInfo> eduList, boolean updateSupport){
        if (StringUtils.isNull(eduList) || eduList.size() == 0) {
            throw new CustomException("导入教育经历数据不能为空！");
        }
        StringBuffer successMsg = new StringBuffer();
        StringBuffer failedMsg = new StringBuffer();
        int failed = 0;
        int success = 0;
        int index = 1;
        for(EducationInfo edu : eduList){
            index++;
            String idCard = edu.getEmpId();
            if(StringUtils.isBlank(idCard)){
                failed++;
                failedMsg.append("<br/>第 "+ index +" 行数据，证件号码为空，不可导入。");
                continue;
            }
            String school = edu.getEduSchool();
            if(StringUtils.isBlank(school)){
                failed++;
                failedMsg.append("<br/>第 "+ index +" 行数据，毕业院校为空，不可导入。");
                continue;
            }
            EmployeeInfo emp = new EmployeeInfo();
            emp.setIdCard(idCard);
            List<EmployeeInfo> empList = employeeInfoMapper.selectByWhere(emp);
            if(StringUtils.isNull(empList) || empList.size()==0){
                failed ++;
                failedMsg.append("<br/>" + idCard + "证件号码不存在");
                continue;
            }
            String empId = empList.get(0).getEmpId();
            edu.setEmpId(empId);
            List<EducationInfo> edus = educationInfoMapper.selectBySchool(edu);

            try{
                if(StringUtils.isNull(edus) || edus.size()==0
                            || StringUtils.isBlank(edus.get(0).getEduId())){
                    edu.setUpdateTime(DateUtils.getNowDate());
                    educationInfoMapper.insert(edu);
                    success++;
                    successMsg.append("<br/>"+ idCard + " " + edu.getEduSchool()+ " 导入成功。");
                }else{
                    if(updateSupport){
                        String eduId = edus.get(0).getEduId();
                        edu.setEduId(eduId);
                        educationInfoMapper.updateByPrimaryKeySelective(edu);
                        success++;
                        successMsg.append("<br/>"+ idCard + " " + edu.getEduSchool()+ " 教育经历更新成功。");
                    }else{
                        failed++;
                        failedMsg.append("<br/>"+ idCard + " " + edu.getEduSchool()  + " 已教育经历已存在。");
                        continue;
                    }

                }
            }catch (Exception e){
                failed++;
                failedMsg.append("<br/>"+ idCard + " " + edu.getEduSchool() + " 导入失败。");
                e.printStackTrace();
            }

        }
        StringBuffer resultMsg = new StringBuffer();
        if(failed>0){
            resultMsg.append("导入成功"+ success +"条。<br/>");
            resultMsg.append("导入失败"+ failed +"条。");
            resultMsg.append(failedMsg);
        }else{
            resultMsg.append("导入成功！本次共导入 "+ success +" 条");
        }
        return resultMsg.toString();
    }



}
