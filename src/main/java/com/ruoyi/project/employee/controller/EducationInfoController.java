package com.ruoyi.project.employee.controller;

/*import com.ruoyi.common.baseRule.ResultBody;
import com.ruoyi.common.baseRule.ResultUtil;*/
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.employee.domain.EducationInfo;
import com.ruoyi.project.employee.domain.EmpCost;
import com.ruoyi.project.employee.service.EducationInfoService;
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
@RequestMapping("/education/info")
public class EducationInfoController extends BaseController {

    @Autowired
    private EducationInfoService educationInfoService;

    @GetMapping(value = "/eduList/{empId}")
    public TableDataInfo listEmpEdu(@PathVariable String empId){
        return getDataTable(educationInfoService.selectByEmp(empId));
    }

    @GetMapping(value = "/{eduId}")
    public AjaxResult getEduInfo(@PathVariable String eduId){
        return AjaxResult.success(educationInfoService.selectByPrimaryKey(eduId));
    }

    @Log(title = "新增员工教育信息", businessType = BusinessType.INSERT)
    @PostMapping(name = "新增-")
    public AjaxResult createEducationInfo(@RequestBody EducationInfo educationInfo) {
        return toAjax(educationInfoService.insert(educationInfo));
    }

    @Log(title = "修改员工教育信息", businessType = BusinessType.UPDATE)
    @PutMapping(name= " 修改- ")
    public AjaxResult updateEducationInfo(@RequestBody EducationInfo educationInfo){
        return toAjax(educationInfoService.updateByPrimaryKeySelective(educationInfo));
    }

    @Log(title = "删除员工教育信息", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{eduIds}", name = "删除")
    public AjaxResult deleteEducationInfo(@PathVariable String[] eduIds){
        return toAjax(educationInfoService.deleteByIds(eduIds));
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<EducationInfo> util = new ExcelUtil<EducationInfo>(EducationInfo.class);
        return util.importTemplateExcel("教育经历模板");
    }

    @Log(title = "教育经历导入", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) {
        try{
            ExcelUtil<EducationInfo> util = new ExcelUtil<EducationInfo>(EducationInfo.class);
            List<EducationInfo> empList = util.importExcel(file.getInputStream());
            String message = educationInfoService.importEdu(empList, updateSupport);
            return AjaxResult.success(message);
        }catch (IOException e){
            return AjaxResult.error("Excel文件异常，请稍后重试。");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.success("导入失败，请稍后重试。");
        }
    }


}

