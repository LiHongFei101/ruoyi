package com.ruoyi.project.employee.controller;

import com.ruoyi.common.thread.EmpDocBindThread;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Encrypt;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.employee.domain.EducationInfo;
import com.ruoyi.project.employee.domain.EmpIdleTableExp;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.employee.service.DocBindBatchService;
import com.ruoyi.project.employee.service.EducationInfoService;
import com.ruoyi.project.employee.service.EmployeeInfoService;
import com.ruoyi.project.project.domain.Empidle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
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
@RequestMapping("/employee/info")
public class EmployeeInfoController extends BaseController{

    @Autowired
    private EmployeeInfoService employeeInfoService;
    @Autowired
    private DocBindBatchService docBindBatchService;
    @Autowired
    private EducationInfoService educationInfoService;

    @Encrypt(module = "员工查询")
    @GetMapping(value = "/list", name = "获取列表-")
    public TableDataInfo listEmployeeInfoCondition(EmployeeInfo record) {
        startPage();
        List<EmployeeInfo> list = employeeInfoService.selectEmpInfoPro(record);
        for (EmployeeInfo emp:list) {
            String empId = emp.getEmpId();
            List<EducationInfo> educationInfos = educationInfoService.selectByEmp(empId);
            if(educationInfos!=null){
                List<String> objects = new ArrayList<>();
                int i=0;
                for (EducationInfo l:educationInfos) {
                    int t=++i;
                    objects.add(l.getEduSchool());
                    if(t!=educationInfos.size()){
                     objects.add("，");
                    }
                    emp.setEduSchoolList(objects);
                    if(l.getIsLearnWeb().equals("Y") && educationInfos.size()==1){
                        emp.setIsLearnWeb("可查");
                    }
                    if(l.getIsLearnWeb().equals("Y") && educationInfos.size()!=1){
                        emp.setIsLearnWeb("均可查");
                    }
                    if(l.getIsLearnWeb().equals("N")){
                        emp.setIsLearnWeb("民教网可查");
                    }
                }
            }
        }
        return getDataTable(list);
    }


    @GetMapping(value = "/idle", name = "获取列表-")
    public TableDataInfo listIdleEmp(EmpIdleTableExp record) {
        startPage();
        List<EmpIdleTableExp> list = employeeInfoService.selectEmpIdle(record);
        return getDataTable(list);
    }

    @GetMapping(value = "/idleHistory", name = "获取列表-")
    public TableDataInfo listIdleEmpHistory(EmpIdleTableExp record) {
        startPage();
        List<EmpIdleTableExp> list = employeeInfoService.selectEmpIdleHistory(record);
        return getDataTable(list);
    }

    @GetMapping(value = "/idleStatistic/{empId}", name = "获取列表-")
    public TableDataInfo empIdleStatistic(@PathVariable String empId) {
        List<Empidle> list = employeeInfoService.empIdleStatistic(empId);
        return getDataTable(list);
    }

    @DeleteMapping(value = "/idleStatistic/{idleId}", name = "删除闲置-")
    public AjaxResult deleteEmpIdleData(@PathVariable String idleId) {
        return toAjax(employeeInfoService.deleteEmpIdleData(idleId));
    }


    @Log(title = "添加员工", businessType = BusinessType.INSERT)
    @PostMapping(name = "新增-")
    public AjaxResult createEmployeeInfo(@RequestBody  EmployeeInfo employeeInfo) {
        return toAjax(employeeInfoService.insert(employeeInfo));
    }

    @Log(title = "修改员工信息", businessType = BusinessType.UPDATE)
    @PutMapping(name = "修改-")
    public AjaxResult alterEmployeeInfo(@RequestBody EmployeeInfo employeeInfo) {
        if(employeeInfo.getEmpState().equals("1")||employeeInfo.getEmpState().equals("3")||employeeInfo.getEmpState().equals("4")){
            employeeInfo.setEmpQuitdate("");
        }
        if(employeeInfo.getEmpState().equals("2")){
            employeeInfo.setEmpQuitdate(DateUtils.getDate());
        }
        return toAjax(employeeInfoService.updateByPrimaryKeySelective(employeeInfo));
    }

    @Log(title = "员工离职(删除)", businessType = BusinessType.DELETE)
    @GetMapping(value="/del/{empId}", name = "删除-")
    public AjaxResult delEmp(@PathVariable String empId) {
        EmployeeInfo emp = new EmployeeInfo();
        emp.setEmpId(empId);
        emp.setEmpState("2");
        //emp.setEmpSepardate(DateUtils.getDate());
        emp.setEmpQuitdate(DateUtils.getDate());
        return toAjax(employeeInfoService.updateByPrimaryKeySelective(emp));
    }

    @GetMapping(value = "/{empId}")
    public AjaxResult getEmployeeInfo(@PathVariable String empId) {
        return AjaxResult.success(employeeInfoService.selectByPrimaryKey(empId));
    }


    //@Log(title = "上传员工附件", businessType = BusinessType.UPLOAD)
    @PostMapping("/upload")
    public AjaxResult upload (@RequestParam("file") MultipartFile[] file,
                          @RequestParam("empId") String empId,
                          @RequestParam("itemId") String itemId,
                          @RequestParam("docType") String docType,
                            HttpServletResponse response) {

        if (StringUtils.isBlank(empId)){
            return AjaxResult.error("获取员工信息失败，请稍后重试。");
        }
        if(StringUtils.isBlank(itemId)){
            return AjaxResult.error("附件对应条目信息获取失败，请稍后重试。");
        }
        if(StringUtils.isBlank(docType)){
            return AjaxResult.error("获取上传附件类型失败，请稍后重试。");
        }

        try {
            employeeInfoService.uploadEduDoc(file, empId, itemId, docType);
            return AjaxResult.success("上传成功。");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    @GetMapping(value = "/doc/{empId}", name = "获取列表-")
    public TableDataInfo listEmpDoc(@PathVariable String empId) {
       return getDataTable(employeeInfoService.selectDocByEmp(empId));
    }

    @GetMapping(value = "/downLoad/{empId}", name = "下载附件-")
    public void downLoadDoc(@PathVariable String empId, HttpServletResponse response, HttpServletRequest request)
    throws Exception{
        File zip = employeeInfoService.downloadEmpDoc(empId);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename="+zip.getName());
        response.addHeader("Content-Length", "" + zip.length());
        response.setContentType("application/octet-stream; charset=UTF-8");
        InputStream in = new FileInputStream(zip);
        int len = 0;
        byte[] buffer = new byte[1024];
        OutputStream out = response.getOutputStream();
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer,0,len);
        }
        in.close();
        out.close();
    }

    @Log(title = "删除员工附件", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/doc/{docIds}", name = "删除附件-")
    public AjaxResult deleteEmpDoc(@PathVariable String[] docIds) {
        return toAjax(employeeInfoService.deleteDocByPrimaryKey(docIds));
    }

    /**
     * 导出闲置人员列表
     */
    @Log(title = "闲置人员导出", businessType = BusinessType.EXPORT)
    @GetMapping("/exportIdle")
    public AjaxResult exportEmpIdle(EmpIdleTableExp record) {
        List<EmpIdleTableExp> list = employeeInfoService.selectEmpIdle(record);
        ExcelUtil<EmpIdleTableExp> util = new ExcelUtil<EmpIdleTableExp>(EmpIdleTableExp.class);
        return util.exportExcel(list, "闲置人员");
    }

    @GetMapping("/idleEmpPro/{empId}")
    public TableDataInfo idleEmpPro(@PathVariable String empId){
        return getDataTable(employeeInfoService.selectHistoryPro(empId));
    }


    /**
     * 导出员工基本信息
     */
    @Log(title = "员工基本信息导出", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult exportEmpInfo(EmployeeInfo record) {
        List<EmployeeInfo> list = employeeInfoService.selectEmpInfoPro(record);
        ExcelUtil<EmployeeInfo> util = new ExcelUtil<EmployeeInfo>(EmployeeInfo.class);
        return util.exportExcel(list, "员工基本信息");
    }

    /**
     * 选中员工基本信息导出
     */
    @Log(title = "选中员工基本信息导出", businessType = BusinessType.DELETE)
    @GetMapping(value="/exportId/{empId}", name = "导出-")
    public AjaxResult exportId(@PathVariable String[] empId) {
        List<EmployeeInfo> list = employeeInfoService.selectEmpInfoProId(empId);
        ExcelUtil<EmployeeInfo> util = new ExcelUtil<EmployeeInfo>(EmployeeInfo.class);
        return util.exportExcel(list, "员工基本信息");
    }

    @Log(title = "员工基本信息导入", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) {
        try{
            ExcelUtil<EmployeeInfo> util = new ExcelUtil<EmployeeInfo>(EmployeeInfo.class);
            List<EmployeeInfo> empList = util.importExcel(file.getInputStream());
            String msg = employeeInfoService.importEmpInfo(empList, updateSupport);
            return AjaxResult.success(msg);
        }catch (IOException e){
            return AjaxResult.error("Excel文件异常，请稍后重试。");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.success("导入失败，请稍后重试。");
        }
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<EmployeeInfo> util = new ExcelUtil<EmployeeInfo>(EmployeeInfo.class);
        return util.importTemplateExcel("人员成本模板");
    }




}

