package com.ruoyi.project.employee.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.employee.domain.EducationInfo;
import com.ruoyi.project.employee.domain.EmpDocument;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.employee.domain.EmpskillInfo;
import com.ruoyi.project.employee.mapper.EmpDocumentMapper;
import com.ruoyi.project.employee.mapper.EmployeeInfoMapper;
import com.ruoyi.project.employee.mapper.EmpskillInfoMapper;
import com.ruoyi.project.employee.service.EmpskillInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;


/**
 * 服务实现
 * @author CodeGenerator
 * @since 2020-05-10
 */
@Service
public class EmpskillInfoServiceImpl implements EmpskillInfoService {

    @Autowired
    private EmpskillInfoMapper empskillInfoMapper;
    @Autowired
    private EmployeeInfoMapper employeeInfoMapper;
    @Autowired
    private EmpDocumentMapper empDocumentMapper;

    @Override
    @Transactional
    public int deleteByIds(String[] skillIds) {
        for(String skillId : skillIds){
            List<EmpDocument> docs = empDocumentMapper.selectByItem("3", skillId);
            for(EmpDocument doc : docs){
                String path = doc.getDocPath();
                String docRealPath = RuoYiConfig.getProfile() + StringUtils.substringAfter(path, Constants.RESOURCE_PREFIX);
                FileUtils.deleteFile(docRealPath);
                empDocumentMapper.deleteByPrimaryKey(doc.getDocId());
            }
        }
        return empskillInfoMapper.deleteByIds(skillIds);
    }




    @Override
    @Transactional
    public int insert(EmpskillInfo record) {
        return empskillInfoMapper.insert(record);
    }

    @Override
    @Transactional
    public int insertSelective(EmpskillInfo record) {
        return empskillInfoMapper.insertSelective(record);
    }

    @Override
    public EmpskillInfo selectByPrimaryKey(String skillId) {
        return empskillInfoMapper.selectByPrimaryKey(skillId);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(EmpskillInfo record) {
        return empskillInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(EmpskillInfo record) {
        return empskillInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<EmpskillInfo> selectByEmp(String empId) {
        return empskillInfoMapper.selectByEmp(empId);
    }

    @Override
    @Transactional
    public String importSkill(List<EmpskillInfo> skillList, boolean updateSupport){
        if (StringUtils.isNull(skillList) || skillList.size() == 0) {
            throw new CustomException("导入技能证书数据不能为空！");
        }
        StringBuffer successMsg = new StringBuffer();
        StringBuffer failedMsg = new StringBuffer();
        int failed = 0;
        int success = 0;
        int index = 1;
        for(EmpskillInfo skill : skillList){
            index++;
            String idCard = skill.getEmpId();
            if(StringUtils.isBlank(idCard)){
                failed++;
                failedMsg.append("<br/>第 "+ index +" 行数据，证件号码为空，不可导入。");
                continue;
            }
            String skillName = skill.getSkillName();
            if(StringUtils.isBlank(skillName)){
                failed++;
                failedMsg.append("<br/>第 "+ index +" 行数据，证书名称为空，不可导入。");
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
            skill.setEmpId(empId);
            List<EmpskillInfo> skills = empskillInfoMapper.selectByName(skill);

            try{
                skill.setUpdateTime(DateUtils.getNowDate());
                if(StringUtils.isNull(skills) || skills.size()==0 || StringUtils.isBlank(skills.get(0).getSkillId())){
                    empskillInfoMapper.insert(skill);
                    success++;
                    successMsg.append("<br/>"+ idCard + " " + skill.getSkillName() + " 导入成功。");
                }else{
                    if(updateSupport){
                        String skillId = skills.get(0).getSkillId();
                        skill.setSkillId(skillId);
                        empskillInfoMapper.updateByPrimaryKeySelective(skill);
                        success++;
                        successMsg.append("<br/>"+ idCard + " " + skill.getSkillName()+ " 技能证书更新成功。");
                    }else{
                        failed++;
                        failedMsg.append("<br/>"+ idCard + " " + skill.getSkillName() + " 技能证书信息已存在。");
                        continue;
                    }

                }
            }catch (Exception e){
                failed++;
                failedMsg.append("<br/>"+ idCard + " " + skill.getSkillName() + " 导入失败。");
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
