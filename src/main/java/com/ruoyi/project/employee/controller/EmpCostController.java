package com.ruoyi.project.employee.controller;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.employee.domain.EmpCost;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.employee.service.EmployeeInfoService;
import com.ruoyi.project.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-05-10
 */
@RestController
@RequestMapping("/employee/cost")
public class EmpCostController extends BaseController{

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @GetMapping(value = "/list", name = "获取员工成本列表-")
    public TableDataInfo listEmpCost(EmployeeInfo record) {
        startPage();
        List<Map<String, Object>> list = employeeInfoService.selectEmpCost(record);
        return getDataTable(list);
    }

    @GetMapping(value = "/history/{empId}", name = "获取员工成本列表-")
    public TableDataInfo listEmpCostHistory(@PathVariable String empId) {
        List<EmpCost> list = employeeInfoService.selectEmpCostHistory(empId);
        return getDataTable(list);
    }

    @Log(title = "设置员工成本", businessType = BusinessType.INSERT)
    @PostMapping(name = "新增-")
    public AjaxResult createEmpCost(@RequestBody EmpCost empCost) {
        return toAjax(employeeInfoService.insertEmpCost(empCost));
    }

    @Log(title = "修改员工成本", businessType = BusinessType.UPDATE)
    @PutMapping(name = "修改-")
    public AjaxResult alterEmpCost(@RequestBody EmpCost empCost) {
        return toAjax(employeeInfoService.updateEmpCost(empCost));
    }

    @GetMapping(value = "/{empId}")
    public AjaxResult getEmpCost(@PathVariable String empId) {
        return AjaxResult.success(employeeInfoService.getEmpCost(empId));
    }

    @Log(title = "删除人员成本历史记录", businessType = BusinessType.DELETE)
    @DeleteMapping( value="/delHistory/{costId}" ,name="删除历史")
    public AjaxResult deleteCostHistory(@PathVariable String costId){
        return toAjax(employeeInfoService.delCostHistory(costId));
    }

    /**
     * 人员成本导出
     */
    @Log(title = "人员成本导出", businessType = BusinessType.EXPORT)
    @GetMapping("/exportCost")
    public AjaxResult exportEmpCost(EmployeeInfo record) {
        List<Map<String, Object>> list =  employeeInfoService.selectEmpCost(record);
        ExcelUtil util = new ExcelUtil();
        return util.exportEmpCostExcel(list, "人员成本");
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<EmpCost> util = new ExcelUtil<EmpCost>(EmpCost.class);
        return util.importTemplateExcel("人员成本模板");
    }

    @Log(title = "人员成本导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importCost")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, boolean isHistory) {
        try{
            ExcelUtil util = new ExcelUtil();
            //List<EmpCost> costList = util.importExcel(file.getInputStream());
            List<EmpCost> costList = util.importEmpCostExcel(file.getInputStream());
            String message = employeeInfoService.importCost(costList, updateSupport, isHistory);
            return AjaxResult.success(message);
        }catch (IOException e){
            return AjaxResult.error("Excel文件异常，请稍后重试。");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.success("导入失败，请稍后重试。");
        }
    }

}

