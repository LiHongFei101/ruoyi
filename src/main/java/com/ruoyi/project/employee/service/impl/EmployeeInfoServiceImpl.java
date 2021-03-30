package com.ruoyi.project.employee.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.BaseException;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.employee.domain.*;
import com.ruoyi.project.employee.mapper.*;
import com.ruoyi.project.employee.service.EmployeeInfoService;
import com.ruoyi.project.project.domain.Empidle;
import com.ruoyi.project.project.mapper.EmpIdleMapper;
import com.ruoyi.project.project.mapper.ProInfoMapper;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysPost;
import com.ruoyi.project.system.mapper.SysConfigMapper;
import com.ruoyi.project.system.mapper.SysDeptMapper;
import com.ruoyi.project.system.mapper.SysPostMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * 服务实现
 * @author CodeGenerator
 * @since 2020-05-10
 */
@Service
public class EmployeeInfoServiceImpl implements EmployeeInfoService {

    @Autowired
    EmployeeInfoMapper employeeInfoMapper;
    @Autowired
    EmpDocumentMapper empDocumentMapper;
    @Autowired
    EmpCostMapper empCostMapper;

    @Autowired
    SysDeptMapper deptMapper;
    @Autowired
    SysPostMapper postMapper;
    @Autowired
    ProInfoMapper proInfoMapper;

    @Autowired
    EmpIdleMapper empIdleMapper;
    @Autowired
    SysConfigMapper sysConfigMapper;

    @Override
    public int deleteByPrimaryKey(String empId) {
        return employeeInfoMapper.deleteByPrimaryKey(empId);
    }

    @Override
    @Transactional
    public int insert(EmployeeInfo record) {
        record.setEmpAge(record.getRealAge());//根据身份证号生成年龄
        record.setUpdateTime(DateUtils.getNowDate());
        if(StringUtils.isBlank(record.getEmpSepardate())){

        }

        return employeeInfoMapper.insert(record);
    }

    @Override
    @Transactional
    public int insertSelective(EmployeeInfo record) {
        record.setEmpAge(record.getRealAge());//根据身份证号生成年龄
        record.setUpdateTime(DateUtils.getNowDate());
        return employeeInfoMapper.insertSelective(record);
    }

    @Override
    public EmployeeInfo selectByPrimaryKey(String empId) {
        return employeeInfoMapper.selectByPrimaryKey(empId);
    }

    @Override
    public List<EmployeeInfo> selectByWhere(EmployeeInfo record) {
        return employeeInfoMapper.selectByWhere(record);
    }
    @Override
    public List<EmployeeInfo> selectEmpInfoPro(EmployeeInfo record) {
        return employeeInfoMapper.selectEmpInfoPro(record);
    }


    @Override
    @Transactional
    public int updateByPrimaryKeySelective(EmployeeInfo record) {
        if(StringUtils.isNotBlank(record.getIdCard()) && !"2".equals(record.getEmpState())){
            record.setEmpAge(record.getRealAge());//根据身份证号生成年龄
        }
        record.setUpdateTime(DateUtils.getNowDate());
        return employeeInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(EmployeeInfo record) {
        if(StringUtils.isNotBlank(record.getIdCard()) && !"2".equals(record.getEmpState())){
            record.setEmpAge(record.getRealAge());//根据身份证号生成年龄
        }
        record.setUpdateTime(DateUtils.getNowDate());
        return employeeInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional
    public void uploadEduDoc(MultipartFile[] files, String empId, String itemId, String docType)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException {

        String filePath = RuoYiConfig.getUploadPath()+"/employee/"+empId;
        StringBuffer docIds =  new StringBuffer("");
        for (MultipartFile file : files){
            String path = FileUploadUtils.upload(filePath, file);
            EmpDocument doc = new EmpDocument();
            doc.setDocId(IdUtils.simpleUUID());
            doc.setEmpId(empId);
            doc.setDocName(file.getOriginalFilename());
            doc.setDocPath(path);
            doc.setDocSize(file.getSize());
            doc.setUpdateTime(DateUtils.getNowDate());
            doc.setDocType(docType);
            doc.setItemId(itemId);
            empDocumentMapper.insert(doc);
        }
    }

    @Override
    public File downloadEmpDoc(String empId) throws Exception{
        String filePath = RuoYiConfig.getUploadPath()+"/employee/"+empId;
        String zipPath = RuoYiConfig.getDownloadPath()+"/employee/"+empId;
        EmployeeInfo emp = this.selectByPrimaryKey(empId);
        return FileUtils.pathToZip(filePath,zipPath,emp.getIdCard());
    }

    @Override
    @Transactional
    public int deleteDocByPrimaryKey(String[] docIds){

        try {
            for(String docId : docIds){
                EmpDocument doc = empDocumentMapper.selectByPrimaryKey(docId);
                String path = doc.getDocPath();
                String docRealPath = RuoYiConfig.getProfile() + StringUtils.substringAfter(path, Constants.RESOURCE_PREFIX);
                File docFile = new File(docRealPath);
                if(docFile.exists()){
                    docFile.delete();
                }
            }
            return empDocumentMapper.deleteByIds(docIds);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("附件删除失败，请稍后重试！");
        }
    }


    @Override
    public List<EmpDocument> selectDocByEmp(String empId) {
        return empDocumentMapper.selectByEmp(empId);
    }

    @Override
    public List<Map<String, Object>> selectEmpCost(EmployeeInfo record) {
        return employeeInfoMapper.selectEmpCost(record);
    }


    @Override
    @Transactional
    public int insertEmpCost(EmpCost record) {
        record.setUpdateTime(DateUtils.getNowDate());
        return empCostMapper.insert(record);
    }

    @Override
    @Transactional
    public int updateEmpCost(EmpCost record) {
        if("Y".equals(record.getIsHistory())){
            EmpCost history = empCostMapper.selectByEmp(record.getEmpId());
            empCostMapper.insertHistory(history);
        }
        record.setUpdateTime(DateUtils.getNowDate());
        return empCostMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public EmpCost getEmpCost(String empId){
        return empCostMapper.selectByEmp(empId);
    }

    @Override
    public List<EmpIdleTableExp> selectEmpIdle(EmpIdleTableExp record) {
        return employeeInfoMapper.selectEmpIdle(record);
    }

    @Override
    public List<Empidle> empIdleStatistic(String empId) {
        return empIdleMapper.selectByEmp(empId);
    }

    @Override
    public int deleteEmpIdleData(String idleId) {
        return empIdleMapper.deleteById(idleId);
    }


    @Override
    public List<EmployeeInfo> selectEmpInfoProId(String[] idleId) {
        List<EmployeeInfo> list=new ArrayList<>();
        for (String s : idleId) {
            EmployeeInfo emp=employeeInfoMapper.selectByempId(s);
            list.add(emp);
        }
        return list;
    }


    @Override
    public List<EmpIdleTableExp> selectEmpIdleHistory(EmpIdleTableExp record) {
        return employeeInfoMapper.selectEmpIdleHistory(record);
    }

    @Override
    public List<EmpCost> selectEmpCostHistory(String empId) {
        return empCostMapper.selectHistoryByEmp(empId);
    }

    @Override
    @Transactional
    public int delCostHistory(String costId){
       return empCostMapper.deleteHistoryById(costId);
    }

    @Override
    @Transactional
    public String importCost(List<EmpCost> costList, boolean updateSupport, boolean isHistory){
        if (StringUtils.isNull(costList) || costList.size() == 0) {
            throw new CustomException("导入员工成本数据不能为空！");
        }
        StringBuffer successMsg = new StringBuffer();
        StringBuffer failedMsg = new StringBuffer();
        int failed = 0;
        int success = 0;
        int index = 1;
        for(EmpCost cost : costList){
            index++;
            EmployeeInfo empInfo = cost.getEmployeeInfo();
            if(StringUtils.isNull(empInfo) || StringUtils.isBlank(empInfo.getIdCard())){
                failed++;
                failedMsg.append("<br/>第 "+ index +" 行数据，证件号码为空，不可导入。");
                continue;
            }
            List<EmployeeInfo> empList = employeeInfoMapper.selectByWhere(empInfo);
            if(StringUtils.isNull(empList) || empList.size()==0){
                failed ++;
                failedMsg.append("<br/>" + empInfo.getIdCard() + "证件号码不存在");
                continue;
            }

            String empId = empList.get(0).getEmpId();
            String empName = empList.get(0).getEmpName();
            EmpCost empCost = empCostMapper.selectByEmp(empId);

            try{
                if(StringUtils.isNull(empCost)){
                    cost.setEmpId(empId);
                    cost.setUpdateTime(DateUtils.getNowDate());
                    empCostMapper.insert(cost);
                    success++;
                    successMsg.append("<br/>" + empInfo.getIdCard()+ " " +empName + "导入成功。");
                }else{
                    if(updateSupport){
                        if(isHistory){
                            EmpCost history = new EmpCost();
                            BeanUtils.copyProperties(empCost, history);
                            empCostMapper.insertHistory(history);
                        }
                        empCost.setEmpSalary(cost.getEmpSalary());
                        empCost.setEmpInsurance(cost.getEmpInsurance());
                        empCost.setEmpFund(cost.getEmpFund());
                        empCost.setSubsidies1(cost.getSubsidies1());
                        empCost.setSubsidies2(cost.getSubsidies2());
                        empCost.setSubsidies3(cost.getSubsidies3());
                        empCost.setSubsidies4(cost.getSubsidies4());
                        empCost.setSubsidies5(cost.getSubsidies5());
                        empCost.setUpdateTime(DateUtils.getNowDate());
                        empCostMapper.updateByPrimaryKeySelective(empCost);
                        success++;
                        successMsg.append("<br/>" + empInfo.getIdCard()+ " " +empName + "更新成功。");
                    }else{
                        failed++;
                        failedMsg.append("<br/>" + empInfo.getIdCard()+ " " +empName + "已设置过成本。");
                        continue;
                    }

                }
            }catch (Exception e){
                failed++;
                failedMsg.append("<br/>" + empInfo.getIdCard()+ " " +empName + "导入失败。");
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

    @Transactional
    @Override
    public String importEmpInfo(List<EmployeeInfo> empList, boolean updateSupport){
        if (StringUtils.isNull(empList) || empList.size() == 0) {
            throw new CustomException("导入员工信息数据不能为空！");
        }
        StringBuffer successMsg = new StringBuffer();
        StringBuffer failedMsg = new StringBuffer();
        int failed = 0;
        int success = 0;
        int index = 1;
        for(EmployeeInfo emp : empList) {
            index++;

            //校验身份证号
            String idCard = emp.getIdCard();
            if(StringUtils.isBlank(idCard)){
                failed++;
                failedMsg.append("<br/>Excel第"+ index+ "行，员工：" + emp.getEmpName() + " 身份证号不能为空。");
                continue;
            }
            EmployeeInfo empInfo = new EmployeeInfo();
            empInfo.setIdCard(idCard);
            List<EmployeeInfo> isExist = employeeInfoMapper.selectByWhere(empInfo);
            if(!updateSupport && StringUtils.isNotNull(isExist) && isExist.size()>0){
                failed++;
                failedMsg.append("<br/>Excel第"+ index+ "行，员工：" + emp.getEmpName() + " 已存在，不可被覆盖。");
                continue;
            }

            String deptName = emp.getEmpDepartment();
            String msg = "";
            if (StringUtils.isBlank(deptName)) {
                msg = "<br/>员工：" + emp.getEmpName() + " 的部门为空，请稍后重新设置。";
            } else {
                //解析员工部门
                try{
                    String[] depts = deptName.split("/");
                    int length = depts.length;
                    Long parentId = 0L;
                    for(int i=0; i<depts.length; i++){
                        String dept = depts[i];
                        SysDept sysDept = new SysDept();
                        sysDept.setDeptName(dept);
                        sysDept.setParentId(parentId);
                        List<SysDept> list = deptMapper.selectDeptList(sysDept);
                        if(StringUtils.isNull(list) || list.size() ==0){
                            msg = "<br/>员工：" + emp.getEmpName() + " 未找到对应部门，请稍后重新设置。";
                            parentId = 0L;
                            break;
                        }
                        if(parentId==0 && list.size() > 1){
                            msg = "<br/>员工：" + emp.getEmpName() + " 未找到对应部门，请稍后重新设置。";
                            parentId = 0L;
                            break;
                        }
                        parentId = list.get(0).getDeptId();
                    }
                    if(parentId != 0){
                        emp.setEmpDepartment(String.valueOf(parentId));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    msg = "<br/>员工：" + emp.getEmpName() + " 未找到对应部门，请稍后重新设置。";
                }
            }

            try{
                String job = emp.getEmpJob();
                if(StringUtils.isBlank(job)){
                    msg = StringUtils.isNotBlank(msg)? msg : "<br/>员工：" + emp.getEmpName() + " 的岗位为空，请稍后重新设置。";
                }else{
                    SysPost sysPost = new SysPost();
                    sysPost.setPostName(job);
                    List<SysPost> jobs = postMapper.selectPostList(sysPost);
                    if(StringUtils.isNull(jobs) || jobs.size()==0){
                        msg = StringUtils.isNotBlank(msg)? msg : "<br/>员工：" + emp.getEmpName() + " 的岗位未找到，请稍后重新设置。";
                    }else{
                        emp.setEmpJob(String.valueOf(jobs.get(0).getPostId()));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                msg = StringUtils.isNotBlank(msg)? msg : "<br/>员工：" + emp.getEmpName() + " 的岗位未找到，请稍后重新设置。";
            }

            //插入员工信息。
            try {
                if(updateSupport && StringUtils.isNotNull(isExist) && isExist.size()>0){
                    String empId = isExist.get(0).getEmpId();
                    emp.setEmpId(empId);
                    employeeInfoMapper.updateByPrimaryKeySelective(emp);
                }else{
                    employeeInfoMapper.insert(emp);
                }
                successMsg.append(msg);
                success ++;
            } catch (Exception e) {
                failed++;
                failedMsg.append("<br/>Excel第"+ index+ "行，员工：" + emp.getEmpName() + "数据导入失败，请稍后重试。");
                e.printStackTrace();
            }
        }
        StringBuffer resultMsg = new StringBuffer();
        if(failed>0){
            resultMsg.append("导入成功"+ success +"条。");
            resultMsg.append(successMsg);
            resultMsg.append("<br/>导入失败"+ failed +"条。");
            resultMsg.append(failedMsg);
        }else{
            resultMsg.append("导入成功！本次共导入 "+ success +" 条");
            resultMsg.append(successMsg);
        }
        return resultMsg.toString();
    }

    @Override
    public List<Map<String, String>> selectHistoryPro(String empId) {
        return proInfoMapper.selectHistoryPro(empId);
    }



}
