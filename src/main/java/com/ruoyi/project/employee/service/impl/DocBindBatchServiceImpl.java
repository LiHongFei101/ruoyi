package com.ruoyi.project.employee.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.CommonFileOperation;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.project.employee.domain.*;
import com.ruoyi.project.employee.mapper.*;
import com.ruoyi.project.employee.service.DocBindBatchService;
import com.ruoyi.project.system.domain.SysConfig;
import com.ruoyi.project.system.mapper.SysConfigMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2020/8/11.
 */
@Service
public class DocBindBatchServiceImpl implements DocBindBatchService {

    @Autowired
    DocBindBatchMapper docBindBatchMapper;
    @Autowired
    EmployeeInfoMapper employeeInfoMapper;
    @Autowired
    EmpDocumentMapper empDocumentMapper;
    @Autowired
    EducationInfoMapper educationInfoMapper;
    @Autowired
    EmpskillInfoMapper empskillInfoMapper;
    @Autowired
    SysConfigMapper sysConfigMapper;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Override
    public List<String> selectAllZip() {
        return docBindBatchMapper.selectAllZip();
    }

    @Override
    public List<DocBindBatch> selectByWhere(DocBindBatch docBindBatch) {
        return docBindBatchMapper.selectByWhere(docBindBatch);
    }

    @Override
    public int deleteByPrimaryKey(String id){
        return docBindBatchMapper.deleteByPrimaryKey(id);
    }


    @Override
    @Transactional
    public void autoBindDocToEmp(String userId){
        //1.??????zip???
        File path = new File("D:/ruoyi/uploadPath/upload/empElement.zip");
        String uuid = IdUtils.simpleUUID().substring(0,10);
        String destPath = RuoYiConfig.getUploadPath()+ "/"+ uuid;
        FileUtils.unZip(path, destPath);
        File dest = new File(destPath);
        //2.??????zip???????????????
        //String userId = String.valueOf(SecurityUtils.getLoginUser().getUser().getUserId());
        DocBindBatch batch = new DocBindBatch(path.getName(), path.length(), userId);
        parseDestPath(dest, batch);
        //3.?????????????????????????????????
        deleteZipFile(dest);

    }


    private void parseDestPath(File dest, DocBindBatch batch){
        String regex = "\\d{15}(\\d{2}[0-9xX])?";
        for(File file : dest.listFiles()){
            if(file.isDirectory() && StringUtils.isNotBlank(file.getName()) && file.getName().matches(regex)){
                String idCard = file.getName();
                EmployeeInfo empInfo = new EmployeeInfo();
                empInfo.setIdCard(idCard);
                List<EmployeeInfo> isExist = employeeInfoMapper.selectByWhere(empInfo);
                if(StringUtils.isNull(isExist) || isExist.size()==0){
                    DocBindBatch docBindBatch = getBatchInstance(batch.getZipname(),batch.getZipsize(),batch.getUserid(),null);
                    docBindBatch.setDocname(batch.getZipname()+"/"+idCard);
                    docBindBatch.setMsg(idCard + "????????????????????????");
                    docBindBatch.setIssuccess("N");
                    docBindBatchMapper.insert(docBindBatch);
                    continue;
                }
                EmployeeInfo info = isExist.get(0);
                for(File f : file.listFiles()){
                    if(f.isDirectory() && StringUtils.isNotBlank(f.getName())){
                        if("info".equals(f.getName())){
                            parseEmpInfoDoc(f , info, batch);
                        }
                        if("edu".equals(f.getName())){
                            parseEmpEduDoc(f, info, batch);
                        }
                        if("skill".equals(f.getName())){
                            parseEmpSkillDoc(f, info, batch);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param file ????????????info??????
     * @param emp ????????????
     * @param batch ???????????????
     */
    private void parseEmpInfoDoc(File file, EmployeeInfo emp, DocBindBatch batch){
        String empId = emp.getEmpId();
        String filePath = RuoYiConfig.getUploadPath()+"/employee/"+empId;
        for(File f : file.listFiles()){
            if (f.isDirectory()) {
                String[] children = f.list();
                //????????????????????????????????????
                for (int i=0; i<children.length; i++) {
                    parseEmpInfoDoc(new File(f, children[i]), emp, batch);
                }
            }else{
                //???????????????????????????
                DocBindBatch docBindBatch = getBatchInstanceData(batch.getZipname(),
                        batch.getZipsize(), batch.getUserid(), empId, empId, "1", f);
                EmpDocument doc = getDocInstanceData(empId,"1",empId,f);

                String fileName = empDocName(f);
                File destPath = new File(filePath, fileName);
                try {
                    docBindBatch.setItemName("??????????????????");
                    if(!checkFileSize(f,docBindBatch) || !checkFileType(f, docBindBatch)){
                        docBindBatchMapper.insert(docBindBatch);
                        continue;
                    }
                    CommonFileOperation.copyFile(f, destPath);
                    doc.setDocPath(FileUploadUtils.getPathFileName(filePath, fileName));
                    empDocumentMapper.insert(doc);
                    docBindBatch.setIssuccess("Y");
                    docBindBatch.setMsg("???????????????????????? "+f.getName()+" ???????????????");
                    docBindBatchMapper.insert(docBindBatch);
                } catch (Exception e) {
                    if(destPath.exists()){
                        destPath.delete();
                    }
                    docBindBatch.setIssuccess("N");
                    docBindBatch.setMsg("???????????????????????? "+f.getName()+" ???????????????????????????????????????");
                    docBindBatchMapper.insert(docBindBatch);
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }
    private void parseEmpEduDoc(File file, EmployeeInfo emp, DocBindBatch batch){
        String empId =  emp.getEmpId();
        String filePath = RuoYiConfig.getUploadPath()+"/employee/"+empId;
        for(File f : file.listFiles()){
            if(f.isDirectory()){
                String eduName = f.getName();
                DocBindBatch docBindBatch = getBatchInstance(batch.getZipname(),batch.getZipsize(),batch.getUserid(),empId);
                if(StringUtils.isBlank(eduName)){
                    docBindBatch.setDocname(batch.getZipname()+"/"+emp.getIdCard()+"/"+eduName);
                    docBindBatch.setMsg("????????????????????????");
                    docBindBatch.setIssuccess("N");
                    docBindBatchMapper.insert(docBindBatch);
                }
                EducationInfo edu = new EducationInfo();
                edu.setEmpId(empId);
                edu.setEduSchool(eduName);
                List<EducationInfo> edus = educationInfoMapper.selectBySchool(edu);
                String itemId = null;
                if(StringUtils.isNull(edus) || edus.size()==0
                        || StringUtils.isBlank(edus.get(0).getEduId())){
                    edu.setIsdegree("Y");
                    edu.setIsdisploma("Y");
                    Integer eduId = employeeInfoMapper.getGeneratedId("education_info");
                    itemId = eduId==null?null : String.valueOf(eduId);
                    edu.setEduId(itemId);
                    educationInfoMapper.insert(edu);
                }else{
                    itemId = edus.get(0).getEduId();
                }
                for(File eduDoc : f.listFiles()){
                    if(eduDoc.isDirectory()){
                        continue;
                    }
                    EmpDocument doc = getDocInstanceData(empId,"2", itemId, eduDoc);
                    docBindBatch.setId(IdUtils.simpleUUID());
                    docBindBatch.setItemId(itemId);
                    docBindBatch.setItemName(eduName);
                    docBindBatch.setDoctype("2");
                    docBindBatch.setDocsize(eduDoc.length());
                    docBindBatch.setDocname(eduDoc.getName());

                    String fileName = empDocName(eduDoc);
                    File destPath = new File(filePath, fileName);
                    try {
                        if(!checkFileSize(eduDoc,docBindBatch) || !checkFileType(eduDoc, docBindBatch)){
                            docBindBatchMapper.insert(docBindBatch);
                            continue;
                        }
                        CommonFileOperation.copyFile(eduDoc, destPath);
                        doc.setDocPath(FileUploadUtils.getPathFileName(filePath, fileName));
                        empDocumentMapper.insert(doc);
                        docBindBatch.setIssuccess("Y");
                        docBindBatch.setMsg("???????????????????????? "+eduName+":"+eduDoc.getName()+" ???????????????");
                        docBindBatchMapper.insert(docBindBatch);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if(destPath.exists()){
                            destPath.delete();
                        }
                        docBindBatch.setIssuccess("N");
                        docBindBatch.setMsg("???????????????????????? "+eduName+":"+eduDoc.getName()+" ???????????????????????????????????????");
                        docBindBatchMapper.insert(docBindBatch);
                        continue;
                    }
                }
            }
        }
    }
    private void parseEmpSkillDoc(File file, EmployeeInfo emp, DocBindBatch batch){
        String empId =  emp.getEmpId();
        String filePath = RuoYiConfig.getUploadPath()+"/employee/"+empId;
        for(File f : file.listFiles()){
            if(f.isDirectory()){
                String skillName = f.getName();
                DocBindBatch docBindBatch = getBatchInstance(batch.getZipname(),batch.getZipsize(),batch.getUserid(),empId);
                if(StringUtils.isBlank(skillName)){
                    docBindBatch.setDocname(batch.getZipname()+"/"+emp.getIdCard()+"/"+skillName);
                    docBindBatch.setMsg("????????????????????????");
                    docBindBatch.setIssuccess("N");
                    docBindBatchMapper.insert(docBindBatch);
                }
                EmpskillInfo skill = new EmpskillInfo();
                skill.setEmpId(empId);
                skill.setSkillName(skillName);
                List<EmpskillInfo> skills = empskillInfoMapper.selectByName(skill);
                String itemId = null;
                if(StringUtils.isNull(skills) || skills.size()==0
                        || StringUtils.isBlank(skills.get(0).getSkillId())){
                    Integer skillId = employeeInfoMapper.getGeneratedId("empskill_info");
                    itemId = skillId==null?null : String.valueOf(skillId);
                    skill.setSkillId(itemId);
                    empskillInfoMapper.insert(skill);
                }else{
                    itemId = skills.get(0).getSkillId();
                }
                for(File skillDoc : f.listFiles()){
                    if(skillDoc.isDirectory()){
                        continue;
                    }
                    EmpDocument doc = getDocInstanceData(empId,"3", itemId, skillDoc);
                    docBindBatch.setId(IdUtils.simpleUUID());
                    docBindBatch.setItemId(itemId);
                    docBindBatch.setItemName(skillName);
                    docBindBatch.setDoctype("3");
                    docBindBatch.setDocsize(skillDoc.length());
                    docBindBatch.setDocname(skillDoc.getName());

                    String fileName = empDocName(skillDoc);
                    File destPath = new File(filePath, fileName);
                    try {
                        if(!checkFileSize(skillDoc,docBindBatch) || !checkFileType(skillDoc, docBindBatch)){
                            docBindBatchMapper.insert(docBindBatch);
                            continue;
                        }
                        CommonFileOperation.copyFile(skillDoc, destPath);
                        doc.setDocPath(FileUploadUtils.getPathFileName(filePath, fileName));
                        empDocumentMapper.insert(doc);
                        docBindBatch.setIssuccess("Y");
                        docBindBatch.setMsg("???????????????????????? "+skillName+":"+skillDoc.getName()+" ???????????????");
                        docBindBatchMapper.insert(docBindBatch);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if(destPath.exists()){
                            destPath.delete();
                        }
                        docBindBatch.setIssuccess("N");
                        docBindBatch.setMsg("???????????????????????? "+skillName+":"+skillDoc.getName()+" ???????????????????????????????????????");
                        docBindBatchMapper.insert(docBindBatch);
                        continue;
                    }
                }
            }
        }
    }

    //?????????????????????
    private boolean deleteZipFile(File dest){
        if (dest.isDirectory()) {
            String[] children = dest.list();
            //????????????????????????????????????
            for (int i=0; i<children.length; i++) {
                boolean success = deleteZipFile(new File(dest, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dest.delete();
    }
    //??????????????????????????????
    private boolean checkFileSize(File f, DocBindBatch docBindBatch){
        if (f.length() != -1 && f.length() > 50*1024*1024) {
            docBindBatch.setIssuccess("N");
            docBindBatch.setMsg("???????????????????????? "+ f.getName() +" ??????????????????"+ maxFileSize);
            return false;
        }
        return true;
    }
    //??????????????????????????????
    private boolean checkFileType(File f, DocBindBatch docBindBatch){
        if(!FileUploadUtils.isAllowedExtension(FilenameUtils.getExtension(f.getName()),
                MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION)){

            docBindBatch.setIssuccess("N");
            docBindBatch.setMsg("???????????????????????? "+ f.getName() +" ??????????????????????????????");
            return false;
        }
        return true;
    }
    //????????????????????????????????????
    private String empDocName(File f){
        String extension = FilenameUtils.getExtension(f.getName()).toLowerCase();
        return IdUtils.simpleUUID()+"."+extension;
    }
    //??????????????????????????????
    private DocBindBatch getBatchInstance(String zipName, Long zipSize, String userId, String empId){
        DocBindBatch docBindBatch = new DocBindBatch(zipName, zipSize, userId);
        docBindBatch.setId(IdUtils.simpleUUID());
        docBindBatch.setEmpid(empId);
        docBindBatch.setItemId(empId);
        docBindBatch.setBindDate(DateUtils.getTime());
        return docBindBatch;
    }
    //??????????????????????????????
    private DocBindBatch getBatchInstanceData(String zipName, Long zipSize, String userId,
                                              String empId, String itemId, String docType, File f){
        DocBindBatch docBindBatch = getBatchInstance(zipName, zipSize, userId, empId);
        docBindBatch.setItemId(itemId);
        docBindBatch.setDoctype(docType);
        docBindBatch.setDocsize(f.length());
        docBindBatch.setDocname(f.getName());
        return docBindBatch;
    }
    //??????????????????
    private EmpDocument getDocInstanceData(String empId, String docType, String itemId, File f){
        EmpDocument doc = new EmpDocument();
        doc.setDocId(IdUtils.simpleUUID());
        doc.setEmpId(empId);
        doc.setDocType(docType);
        doc.setItemId(itemId);
        doc.setDocSize(f.length());
        doc.setDocName(f.getName());
        doc.setUpdateTime(DateUtils.getNowDate());
        return doc;
    }

    @Override
    public List<String> getBindZip() {
        List<String> zips = new ArrayList<String>();
        SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigKey("sys.emp.doc.batch");
        //SysConfig result = sysConfigMapper.selectConfig(sysConfig);
        //String path = StringUtils.isBlank(result.getConfigValue()) ? "/usr/local/empDocBind/" : result.getConfigValue();
        String path = "D:/QQ_DownLoad";
        File file = new File(path);
        if(file.isDirectory()){
            File [] files = file.listFiles();
            for(File f : files){
                String fileName = f.getName();
                String type = fileName.substring(fileName.lastIndexOf(".")+1);
                if("zip".equalsIgnoreCase(type)){
                    zips.add(fileName);
                }
            }
        }
        return zips;
    }


}
