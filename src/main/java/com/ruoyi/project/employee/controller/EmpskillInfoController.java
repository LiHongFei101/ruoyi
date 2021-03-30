package com.ruoyi.project.employee.controller;


import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.employee.domain.EducationInfo;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.employee.domain.EmpskillInfo;
import com.ruoyi.project.employee.service.EmpskillInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-05-10
 */
@RestController
@RequestMapping("/empSkill/info")
public class EmpskillInfoController extends BaseController {

    @Autowired
    private EmpskillInfoService empskillInfoService;

    @GetMapping(value = "/list/{empId}", name = "获取技能证书列表-")
    public TableDataInfo listEmpSkill(@PathVariable String empId) {
        List<EmpskillInfo> list = empskillInfoService.selectByEmp(empId);
        return getDataTable(list);
    }

    @GetMapping(value = "/{skillId}")
    public AjaxResult getEmpSkill(@PathVariable String skillId){
        return AjaxResult.success(empskillInfoService.selectByPrimaryKey(skillId));
    }

    @PostMapping(name = "新增-")
    public AjaxResult addEmpSkill(@RequestBody EmpskillInfo skill){
        return toAjax(empskillInfoService.insert(skill));
    }

    @PutMapping(name = "修改-")
    public AjaxResult updateEmpSkill(@RequestBody  EmpskillInfo skill){
        return toAjax(empskillInfoService.updateByPrimaryKeySelective(skill));
    }

    @DeleteMapping(value ="/{skillIds}", name ="删除")
    public AjaxResult deleteEmpSkill(@PathVariable String[] skillIds){
        return toAjax(empskillInfoService.deleteByIds(skillIds));
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<EmpskillInfo> util = new ExcelUtil<EmpskillInfo>(EmpskillInfo.class);
        return util.importTemplateExcel("技能证书导入模板");
    }

    @Log(title = "技能证书导入", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) {
        try{
            ExcelUtil<EmpskillInfo> util = new ExcelUtil<EmpskillInfo>(EmpskillInfo.class);
            List<EmpskillInfo> skillist = util.importExcel(file.getInputStream());
            String message = empskillInfoService.importSkill(skillist, updateSupport);
            return AjaxResult.success(message);
        }catch (IOException e){
            return AjaxResult.error("Excel文件异常，请稍后重试。");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.success("导入失败，请稍后重试。");
        }
    }

}

