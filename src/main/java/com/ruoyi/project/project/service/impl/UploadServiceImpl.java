package com.ruoyi.project.project.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.project.domain.FileInfo;
import com.ruoyi.project.project.domain.Item;
import com.ruoyi.project.project.mapper.ProInfoMapper;
import com.ruoyi.project.project.mapper.UploadMapper;
import com.ruoyi.project.project.service.UploadService;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysDeptMapper;
import com.ruoyi.project.system.service.ISysConfigService;
import com.ruoyi.project.tool.gen.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定时任务调度信息 服务层
 *
 * @author ruoyi
 */
@Service
public class UploadServiceImpl implements UploadService
{
    private static final Logger log = LoggerFactory.getLogger(UploadServiceImpl.class);
    @Autowired
    private UploadMapper UploadMapper;
    @Autowired
    private ProInfoMapper proInfoMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * 查询文件信息列表
     * @return
     */
    @Override
    public List<FileInfo> selectFileList(FileInfo fileInfo) {
        return UploadMapper.selectFileList(fileInfo);
    }

    /**
     * 删除文件信息列表
     * @return
     */
    @Override
    public int deleteFileByIds(FileInfo fileInfo) {
        return UploadMapper.deleteFileByIds(fileInfo);
    }

    /**
     * 新增文件信息
     * @return
     */
    @Override
    public int uploadFile(FileInfo fileInfo) {
        return UploadMapper.uploadFile(fileInfo);
    }
    /**
     * 导入项目数据
     *
     * @param itemList 项目数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    @Transactional
    public String importItem(List<Item> itemList, Boolean isUpdateSupport)
    {
        if (StringUtils.isNull(itemList) || itemList.size() == 0)
        {
            throw new CustomException("导入项目数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        int index = 1;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
//        String password = configService.selectConfigByKey("sys.user.initPassword");
        SysDept sysDept = new SysDept();
        for (Item item : itemList)
        {
            index++;
            String item_name = item.getItem_name();
            if(StringUtils.isBlank(item_name)){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目名称不能为空！");
                continue;
            }
            String deptName = item.getDept_id();
            if(StringUtils.isBlank(deptName)){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 部门不能为空！");
                continue;
            }
            String item_numbering = item.getItem_numbering();
            if(StringUtils.isBlank(item_numbering)){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目编号不能为空！");
                continue;
            }
            String item_type = item.getItem_type();
            if(StringUtils.isBlank(item_type)){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目类型不能为空！");
                continue;
            }
            String item_phase = item.getItem_phase();
            if(StringUtils.isBlank(item_phase)){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目阶段不能为空！");
                continue;
            }
            if(StringUtils.isBlank(item.getItem_num())){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目人数不能为空！");
                continue;
            }
            if(!StringUtil.isLetterAndNum(item.getItem_num()) && !StringUtils.isBlank(item.getItem_num())){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目人数应为正整数！");
                continue;
            }
            String item_startdate = item.getItem_startdate();
            if(StringUtils.isBlank(item_startdate)){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目开始日期不能为空！");
                continue;
            }
            if(item_startdate.length()>10){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目开始日期应为文本格式！例如：XXXX-XX-XX");
                continue;
            }
            String item_enddate = item.getItem_enddate();
            if(StringUtils.isBlank(item_enddate)){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目结束日期不能为空！");
                continue;
            }
            if(StringUtils.isBlank(item.getIdCard())){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目负责人身份证号不能为空！");
                continue;
            }
            if(item_startdate.length()>10){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目结束日期应为文本格式！例如：XXXX-XX-XX");
                continue;
            }
            if(item_type.equals("0")){
                if(StringUtils.isBlank(item.getItem_fund())){
                    failureNum++;
                    failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目资金不能为空！");
                    continue;
                }
                if(!StringUtil.isNumeric(item.getItem_fund()) && !StringUtils.isBlank(item.getItem_fund())){
                    failureNum++;
                    failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目资金格式不正确(正数，保留2位小数)！");
                    continue;
                }
            }else{
                if(!StringUtil.isNumeric(item.getItem_fund()) && !StringUtils.isBlank(item.getItem_fund())){
                    failureNum++;
                    failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目资金格式不正确(正数，保留2位小数)！");
                    continue;
                }
            }
            if(StringUtils.isBlank(item.getWorkcost())){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目实施费用不能为空！");
                continue;
            }
            if(!StringUtil.isNumeric(item.getWorkcost()) && !StringUtils.isBlank(item.getWorkcost())){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目实施费用格式不正确(正数，保留2位小数)！");
                continue;
            }
            if(StringUtils.isBlank(item.getTaxrate())){
                failureNum++;
                failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目税率不能为空！");
                continue;
            }else {
                if(!item.getTaxrate().equals("0") && !item.getTaxrate().equals("0.06")){
                    failureNum++;
                    failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目税率录入项，例：有税率或无税率");
                    continue;
                }
            }
            sysDept.setDeptName(deptName);
            List<SysDept> list_dept = sysDeptMapper.selectDeptList(sysDept);
            if(list_dept.size()==1){
                sysDept=  list_dept.get(0);
            }
            item.setDept_id( String.valueOf(sysDept.getDeptId()));
            try
            {
                // 验证是否存在这个项目
                List<Item> list= proInfoMapper.selectProjectList(item);
                if (list.size()==0)
                {
                    String empid = proInfoMapper.selectEmpId(item.getIdCard());
                    if(empid==null) {
                        failureNum++;
                        failureMsg.append("<br/>Excel第"+ index+ "行，项目：" + item.getItem_name() + " 项目负责人不存在！");
                        continue;
                    }else {
                        item.setEmpId(empid);
                    }
                        item.setChange_num("0");
                        proInfoMapper.insertProject(item);
                        successNum++;
                        successMsg.append("<br/>" + successNum  + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    proInfoMapper.updateProject(item);
                    successNum++;
                    successMsg.append("<br/>" + successNum + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、项目 "  + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、项目 "  + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}