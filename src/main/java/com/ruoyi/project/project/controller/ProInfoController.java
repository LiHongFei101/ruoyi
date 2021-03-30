package com.ruoyi.project.project.controller;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Encrypt;
import com.ruoyi.project.customer.domain.Contract;
import com.ruoyi.project.customer.service.CustomerService;
import com.ruoyi.project.employee.domain.EmpCost;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.employee.service.EmployeeInfoService;
import com.ruoyi.project.project.domain.*;
import com.ruoyi.project.project.service.ProInfoService;
import com.ruoyi.project.tool.gen.util.ExcelItemUtils;
import com.ruoyi.project.tool.gen.util.GetDateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 调度任务信息操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/project/info")
public class ProInfoController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(ProInfoController.class);
    @Autowired
    private ProInfoService proInfoService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeInfoService employeeInfoService;

    /**
     * 查询项目列表
     */
    @PreAuthorize("@ss.hasPermi('module:item:query')")
    @Encrypt(module = "项目信息列表")
    @GetMapping("/list")
    public TableDataInfo list(Item item)
    {
        log.info("---------------查询项目信息开始---------------" );
        startPage();
        List<Item> list = proInfoService.selectProjectList(item);
        log.info("---------------查询项目信息完成---------------" );
        return getDataTable(list);
    }

    /**
     * 新增项目基本信息
     */
    @PreAuthorize("@ss.hasPermi('module:item:add')")
    @PostMapping("/addProject")
    public AjaxResult addProject(@RequestBody Item Item){
        log.info("---------------新增项目信息开始---------------" );
        AjaxResult AjaxResult=toAjax(proInfoService.insertProject(Item));
        log.info("---------------新增项目信息完成---------------" );
        return AjaxResult;
    }

    /**
     * 根据项目id获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @Encrypt(module = "项目信息列表")
    @GetMapping(value = { "/{item_id}" })
    public AjaxResult getPro(@PathVariable(value = "item_id", required = false) String item_id)
    {
        log.info("---------------根据项目ID查询项目详细信息开始---------------" );
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(item_id))
        {
            ajax.put(AjaxResult.DATA_TAG, proInfoService.selectProById(item_id));
        }
        log.info("---------------根据项目ID查询项目详细信息结束---------------" );
        return ajax;
    }

    /**
     * 删除项目基本信息
     */
    @PreAuthorize("@ss.hasPermi('module:item:remove')")
    @DeleteMapping("/{item_ids}")
    public AjaxResult remove(@PathVariable Integer[] item_ids)
    {
        log.info("---------------根据项目ID删除项目信息开始---------------" );
        for(int i=0;i<item_ids.length;i++){
            if( proInfoService.queryLevelByitem_id(item_ids[i]) != 0 ){
                return AjaxResult.error("请先删除项目人员级别配置信息！");
            }
        }
        log.info("---------------根据项目ID删除项目信息结束---------------" );
        return AjaxResult.success(proInfoService.deleteProjectByIds(item_ids));
    }

    /**
     * 修改项目基本信息
     */
    @PreAuthorize("@ss.hasPermi('module:item:edit')")
    @Encrypt(module = "修改项目信息列表")
    @PutMapping
    public AjaxResult updateProject(@RequestBody Item Item) {
        log.info("---------------修改项目信息开始---------------" );
        AjaxResult AjaxResult=toAjax(proInfoService.updateProject(Item));
        log.info("---------------修改项目信息结束---------------" );
        return AjaxResult;
    }
    /**
     * 根据项目id获取项目变更详细信息
     */
    @PreAuthorize("@ss.hasPermi('module:item:changeinfo')")
    @Encrypt(module = "项目变更详细信息列表")
    @GetMapping(value = { "/recordlist/{item_id}" })
    public TableDataInfo getRecordPro(@PathVariable(value = "item_id", required = false) String item_id)
    {
        log.info("---------------查询项目变更信息开始---------------" );
        startPage();
        List<Item> list = proInfoService.selectRecProById(item_id);
        log.info("---------------查询项目变更信息完成---------------" );
        return getDataTable(list);
    }

    /**
     * 根据项目id获取项目级别详细信息
     */
    @PreAuthorize("@ss.hasPermi('module:item:levelsettings')")
    @Encrypt(module = "项目变更详细信息列表")
    @GetMapping(value = { "/perlevellist/{item_id}" })
    public TableDataInfo perlevellist(@PathVariable(value = "item_id", required = false) String item_id)
    {
        log.info("---------------查询项目级别详细信息开始---------------" );
        startPage();
        List<Itemperlevel> list = proInfoService.selectPerLevelById(item_id);
        log.info("---------------查询项目级别详细信息完成---------------" );
        return getDataTable(list);
    }
    /**
     * 新增项目人员级别基本信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @PostMapping("/addPerLevel")
    public AjaxResult addPerLevel(@RequestBody Itemperlevel itemperlevel) {
        log.info("---------------新增人员级别信息开始---------------" );
        AjaxResult AjaxResult=toAjax(proInfoService.insertPerLevel(itemperlevel));
        log.info("---------------新增人员级别信息完成---------------" );
        return AjaxResult;
    }
    /**
     * 根据项目人员级别id获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @Encrypt(module = "项目人员级别详细信息列表")
    @GetMapping(value = { "/perlevel/{item_level_id}" })
    public AjaxResult getData(@PathVariable(value = "item_level_id", required = false) String item_level_id)
    {
        log.info("---------------根据项目人员级别id获取详细信息开始---------------" );
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(item_level_id))
        {
            ajax.put(AjaxResult.DATA_TAG, proInfoService.selectLevelById(item_level_id));
        }
        log.info("---------------根据项目人员级别id获取详细信息结束---------------" );
        return ajax;
    }
    /**
     * 修改项目人员级别信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:edit')")
//    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @Encrypt(module = "修改项目人员级别信息列表")
    @PutMapping("/updatedata")
    public AjaxResult updatePerLevel(@RequestBody Itemperlevel itemperlevel){
        log.info("---------------修改项目人员级别信息开始---------------" );
        AjaxResult AjaxResult=toAjax(proInfoService.updatePerLevel(itemperlevel));
        log.info("---------------修改项目人员级别信息结束---------------" );
        return AjaxResult;
    }

    /**
     * 删除项目人员级别信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
    @DeleteMapping("/deldata/{item_level_ids}")
    public AjaxResult delPerLevel(@PathVariable Integer[] item_level_ids){
        log.info("---------------删除项目人员级别信息---------------" );
        return toAjax(proInfoService.delPerLevel(item_level_ids));
    }

    /**
     * 查询绑定员工信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    /*@Encrypt(module = "绑定员工信息列表")*/
    @GetMapping(value = { "/assignperlist" })
    public TableDataInfo assignperlist(EmployeeInfo employeeInfo)
    {
        log.info("---------------查询绑定员工详细信息开始---------------" );
        startPage();
        List<EmployeeInfo> list = proInfoService.selectEmp(employeeInfo);
        log.info("---------------查询绑定员工详细信息完成---------------" );
        return getDataTable(list);
    }

    /**
     * 新增项目人员绑定信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping("/addProPer")
    public AjaxResult addProPer(Itemper Itemper) {
        log.info("---------------新增项目人员绑定开始---------------" );
        AjaxResult AjaxResult=toAjax(proInfoService.insertProper(Itemper));
        log.info("---------------新增项目人员绑定完成---------------" );
        return AjaxResult;
    }
    /**
     * 解绑项目人员
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:edit')")
//    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/updateProPer")
    public AjaxResult updateProPer(@RequestBody Itemper Itemper) {
        log.info("---------------解绑项目人员信息开始---------------" );
        AjaxResult AjaxResult=toAjax(proInfoService.updateProPer(Itemper));
        log.info("---------------解绑项目人员信息结束---------------" );
        return AjaxResult;
    }

    /**
     * 修改项目人员绑定信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:edit')")
//    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/updateBinding")
    public AjaxResult updateBinding(@RequestBody Itemper Itemper) {
        log.info("---------------修改项目人员绑定信息开始---------------" );
        AjaxResult AjaxResult=toAjax(proInfoService.updateBinding(Itemper));
        log.info("---------------修改项目人员绑定信息结束---------------" );
        return AjaxResult;
    }
    /**
     * 获取员工列表
     */
    @PreAuthorize("@ss.hasPermi('module:item:assignemp')")
    @Encrypt(module = "人员信息列表")
    @GetMapping("/listEmp")
    public TableDataInfo listEmp(EmployeeInfo employeeInfo)
    {
        log.info("---------------获取员工列表信息开始---------------" );
        startPage();
        List<EmployeeInfo> list = proInfoService.selectEmpList(employeeInfo);
        log.info("---------------获取员工列表信息开始---------------" );
        return getDataTable(list);
    }

    /**
     * 获取员工是否绑定项目
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @Encrypt(module = "获取员工是否绑定项目")
    @GetMapping("/listIsBindEmp")
    public TableDataInfo listIsBindEmp(Itemper Itemper)
    {
        log.info("---------------获取员工是否绑定项目开始---------------" );
        startPage();
        List<Itemper> list = proInfoService.selectIsBindEmpList(Itemper);
        log.info("---------------获取员工是否绑定项目结束---------------" );
        return getDataTable(list);
    }
    /**
     * 获取员工是否解绑此项目
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @Encrypt(module = "获取员工是否解绑定项目")
    @GetMapping("/unBindEmp")
    public TableDataInfo unBindEmp(Itemper Itemper)
    {
        log.info("---------------获取员工是否解绑项目开始---------------" );
        startPage();
        List<Itemper> list = proInfoService.selectIsunBindEmp(Itemper);
        log.info("---------------获取员工是否解绑项目结束---------------" );
        return getDataTable(list);
    }

    /**
     * 根据项目id获取合同详细信息
     */
    @PreAuthorize("@ss.hasPermi('module:item:contract')")
    @Encrypt(module = "获取项目合同信息")
    @GetMapping(value = { "/contractlist/{item_id}" })
    public TableDataInfo contractlist(@PathVariable(value = "item_id", required = false) String item_id)
    {
        log.info("---------------根据项目id查询合同详细信息开始---------------" );
        startPage();
        List<Contract> list = proInfoService.selectContractById(item_id);
        log.info("---------------根据项目id查询合同详细信息完成---------------" );
        return getDataTable(list);
    }
    /**
     * 新增合同基本信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @PostMapping("/addContract")
    public AjaxResult addContract(@RequestBody Contract contract){
        log.info("---------------新增合同信息开始---------------" );
        AjaxResult AjaxResult=toAjax(customerService.insertContract(contract));
        log.info("---------------新增合同信息完成---------------" );
        return AjaxResult;
    }
    /**
     * 导出项目信息
     */
    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('module:item:export')")
    @GetMapping("/exportProject")
    public AjaxResult exportProject(Item Item)
    {
        log.info("---------------导出项目信息开始---------------" );
        List<Item> list = proInfoService.selectProjectList(Item);
        ExcelUtil<Item> util = new ExcelUtil<Item>(Item.class);
        log.info("---------------导出项目信息结束---------------" );
        return util.exportExcel(list, "项目数据");
    }
    /**
     * 上传项目合同附件信息
     */
    @PostMapping("/upload")
    public AjaxResult upload (@RequestParam("file") MultipartFile[] file,
                              @RequestParam("empId") String empId,
                              @RequestParam("itemId") String itemId,
                              @RequestParam("docType") String docType
                              ) {

        log.info("---------------上传项目合同附件开始---------------" );
        if(StringUtils.isBlank(itemId)){
            return AjaxResult.error("附件对应合同信息获取失败，请稍后重试。");
        }
        if(StringUtils.isBlank(docType)){//docType为项目合同类型"10"
            return AjaxResult.error("获取上传附件类型失败，请稍后重试。");
        }

        try {
            proInfoService.uploadContractDoc(file, empId, itemId, docType);
            log.info("---------------上传项目合同附件结束---------------" );
            return AjaxResult.success("上传成功！");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
    /**
     * 获取合同附件信息
     */
    @GetMapping(value = "/doc/{contract_id}", name = "获取列表-")
    public TableDataInfo listContractDoc(@PathVariable String contract_id) {
        log.info("---------------获取合同附件信息---------------" );
        return getDataTable(proInfoService.selectDocByContract(contract_id));
    }
    /**
     * 下载合同附件信息
     */
    @GetMapping(value = "/downLoad/{contract_id}", name = "下载附件-")
    public void downLoadDoc(@PathVariable String contract_id, HttpServletResponse response, HttpServletRequest request)
            throws Exception{
        File zip = proInfoService.downloadContractDoc(contract_id);
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

    /**
     * 删除附件
     * @param docIds
     * @return
     */
    @DeleteMapping(value = "/doc/{docIds}", name = "删除附件-")
    public AjaxResult deleteEmpDoc(@PathVariable String[] docIds) {
        log.info("---------------删除附件信息---------------" );
        return toAjax(employeeInfoService.deleteDocByPrimaryKey(docIds));
    }

    /**
     * 获取客户数据信息
     */
    @Encrypt(module = "获取客户数据信息")
    @GetMapping(value = "/getCustomer/{item_type}")
    public AjaxResult getCustomer(@PathVariable(value = "item_type", required = false) String item_type)
    {
        log.info("---------------获取客户数据信息---------------" );
        return AjaxResult.success(proInfoService.selectCustomer(item_type));
    }
    /**
     * 根据项目id计算出项目人员成本
     */
    @PreAuthorize("@ss.hasPermi('module:item:cost')")
    @Encrypt(module = "项目人员成本信息")
    @GetMapping(value = { "/selectPerCost/{item_id}" })
    public TableDataInfo selectPerCost(@PathVariable(value = "item_id", required = false) String item_id)
    {
        log.info("---------------根据项目id计算出项目人员成本开始---------------" );
        List<Percost> list = proInfoService.selectPerCostById(item_id);
        log.info("---------------根据项目id计算出项目人员成本结束---------------" );
        return getDataTable(list);
    }
    /**
     * 导出人员成本信息
     */
    @Log(title = "人员支出", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/exportData/{item_id}")
    public AjaxResult exportData(@PathVariable(value = "item_id", required = false) String item_id,
                           HttpServletRequest request, HttpServletResponse response)
    {
        /*List<Percost> list_cost = proInfoService.selectPerCostById(item_id);
        ExcelUtil<Percost> util = new ExcelUtil<Percost>(Percost.class);
        return util.exportExcel(list_cost, "人员支出数据");*/
        List<Percost> list = new ArrayList<Percost>();
        ExcelItemUtils excelItemUtils = new ExcelItemUtils();
        ExcelUtil excelUtil =new ExcelUtil();
        String year = GetDateUtils.getSysYear();
        String filename ="";
        log.info("---------------导出项目人员支出信息开始---------------" );
        try{
            list =  proInfoService.selectPerCostById(item_id);
            Workbook workbook = excelItemUtils.PercostDataExcel(list);
            OutputStream outStream = null;
            try {
                filename = new String("项目人员"+year+"年度成本报表")+ ".xlsx";
                outStream = response.getOutputStream();
                outStream = new FileOutputStream(excelUtil.getAbsoluteFile(filename));
                workbook.write(outStream);
            } catch (Exception e) {
                log.error("导出Excel异常{}", e.getMessage());
                throw new Exception("导出Excel失败，请联系网站管理员！");
            }finally{
                if (outStream != null) {
                    try {
                        outStream.close();
                    } catch (IOException e) {
                        System.out.println("Err : "+e);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("---------------导出项目人员支出信息结束---------------" );
        return AjaxResult.success(filename);
    }
    /**
     * 导出项目变更记录信息
     */
    @Log(title = "人员支出", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/exportHistory/{item_id}")
    public AjaxResult exportHistory(@PathVariable(value = "item_id", required = false) String item_id)
    {
        log.info("---------------导出项目变更记录信息开始---------------" );
        List<Item> list = proInfoService.selectRecProById(item_id);
        ExcelUtil<Item> util = new ExcelUtil<Item>(Item.class);
        log.info("---------------导出项目变更记录信息结束---------------" );
        return util.exportExcel(list, "项目历史变更数据");
    }
    /**
     * 查询项目人员级别是否存在
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @Encrypt(module = "项目人员级别信息")
    @GetMapping("/selectIsLevel")
    public TableDataInfo selectIsLevel(Itemperlevel itemperlevel)
    {
        log.info("---------------查询项目人员级别是否存在开始---------------" );
        startPage();
        List<Itemperlevel> list = proInfoService.selectIsLevel(itemperlevel);
        log.info("---------------查询项目人员级别是否存在结束---------------" );
        return getDataTable(list);
    }
    /**
     * 根据项目id计算出外包人员收入
     */
    @PreAuthorize("@ss.hasPermi('module:item:income')")
    @Encrypt(module = "外包人员收入信息")
    @GetMapping(value = { "/selectPerIncome/{item_id}" })
    public TableDataInfo selectPerIncome(@PathVariable(value = "item_id", required = false) String item_id)
    {
        log.info("---------------根据项目id计算出外包人员收入开始---------------" );
        startPage();
        List<Perincome> list =  proInfoService.selectPerIncomeById(item_id);
        log.info("---------------根据项目id计算出外包人员收入结束---------------" );
        return getDataTable(list);
    }
    /**
     * 根据级别id获取外包人员详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @Encrypt(module = "外包人员详细信息")
    @GetMapping(value = { "/outsourcingperlist/{item_level_id}" })
    public TableDataInfo outsourcingperlist(@PathVariable(value = "item_level_id", required = false) String item_level_id)
    {
        log.info("---------------查询外包人员详细信息开始---------------" );
        startPage();
        List<EmployeeInfo> list = proInfoService.selectOutPerById(item_level_id);
        log.info("---------------查询外包人员详细信息完成---------------" );
        return getDataTable(list);
    }
    /**
     * 导出人员收入信息
     */
    @Log(title = "人员收入", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/exportIncomeData/{item_id}")
    public AjaxResult exportIncomeData(@PathVariable(value = "item_id", required = false) String item_id,
                                       HttpServletRequest request, HttpServletResponse response)
    {
        /*List<Perincome> list =  proInfoService.selectPerIncomeById(item_id);
        ExcelUtil<Perincome> util = new ExcelUtil<Perincome>(Perincome.class);
        return util.exportExcel(list, "外包人员收入数据");*/
        List<Perincome> list = new ArrayList<Perincome>();
        ExcelItemUtils excelItemUtils = new ExcelItemUtils();
        ExcelUtil excelUtil = new ExcelUtil();
        String year = GetDateUtils.getSysYear();
        String filename ="";
        log.info("---------------导出外包人员收入信息开始---------------" );
        try{
            list =  proInfoService.selectPerIncomeById(item_id);
            Workbook workbook = excelItemUtils.PerincomeDataExcel(list);
            OutputStream outStream = null;
            try {
                filename = new String("外包人员"+year+"年度预计收入报表")+ ".xlsx";
                outStream = response.getOutputStream();
                outStream = new FileOutputStream(excelUtil.getAbsoluteFile(filename));
                workbook.write(outStream);
            } catch (Exception e) {
                log.error("导出Excel异常{}", e.getMessage());
                throw new Exception("导出Excel失败，请联系网站管理员！");
            }finally{
                if (outStream != null) {
                    try {
                        outStream.close();
                    } catch (IOException e) {
                        System.out.println("Err : "+e);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("---------------导出外包人员收入信息结束---------------" );
        return AjaxResult.success(filename);

    }
    /**
     * 根据项目id获取外包人员绑定详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @Encrypt(module = "外包人员绑定信息")
    @GetMapping(value = { "/listPerBinding/{item_id}" })
    public TableDataInfo listPerBinding(@PathVariable(value = "item_id", required = false) String item_id)
    {
        log.info("---------------查询外包人员绑定详细信息开始---------------" );
        startPage();
        List<EmployeeInfo> list = proInfoService.listPerBinding(item_id);
        log.info("---------------查询外包人员绑定详细信息完成---------------" );
        return getDataTable(list);
    }
    /**
     * 判断项目开始日期是否大于当日
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/CompareItemdate")
    public AjaxResult CompareItemdate(Itemper Itemper)
    {
        Boolean bool = proInfoService.CompareItemdate(Itemper);
        if(bool == true){
            return AjaxResult.success();
        }
        return AjaxResult.error("项目开始日期不可小于当前日期！");
    }

    /**
     * 查询员工是否设置工资信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @Encrypt(module = "员工工资信息")
    @GetMapping("/selectEmpcost")
    public TableDataInfo selectEmpcost(EmpCost empCost)
    {
        log.info("---------------查询员工是否设置工资信息开始---------------" );
        startPage();
        List<EmpCost> list = proInfoService.selectEmpcost(empCost);
        log.info("---------------查询员工是否设置工资信息结束---------------" );
        return getDataTable(list);
    }
    @Encrypt(module = "员工信息")
    @GetMapping(value = "/listEmpinfo", name = "获取列表-")
    public TableDataInfo listEmployeeInfoCondition(EmployeeInfo record) {
        log.info("---------------查询员工信息开始---------------" );
        startPage();
        List<EmployeeInfo> list = employeeInfoService.selectEmpInfoPro(record);
        log.info("---------------查询员工信息结束---------------" );
        return getDataTable(list);
    }
    /**
     * 根据项目id计算出项目人员预估成本
     */
    @Encrypt(module = "项目人员预估成本信息")
    @PreAuthorize("@ss.hasPermi('module:item:costbudget')")
    @GetMapping(value = { "/selectPerCostBudget/{item_id}" })
    public TableDataInfo selectPerCostBudget(@PathVariable(value = "item_id", required = false) String item_id)
    {
        log.info("---------------根据项目id计算出项目人员预估成本开始---------------" );
        List<Percost> list = proInfoService.selectPerCostBudgetById(item_id);
        log.info("---------------根据项目id计算出项目人员预估成本结束---------------" );
        return getDataTable(list);
    }
    /**
     * 根据项目id计算出项目人员预估收入
     */
    @Encrypt(module = "项目人员预估收入信息")
    @PreAuthorize("@ss.hasPermi('module:item:incomebudget')")
    @GetMapping(value = { "/selectPerIncomeBudget/{item_id}" })
    public TableDataInfo selectPerIncomeBudget(@PathVariable(value = "item_id", required = false) String item_id)
    {
        log.info("---------------根据项目id计算出项目人员预估收入开始---------------" );
        List<Perincome> list = proInfoService.selectPerIncomeBudgetById(item_id);
        log.info("---------------根据项目id计算出项目人员预估收入结束---------------" );
        return getDataTable(list);
    }
    /**
     * 导出项目人员成本预估
     */
    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/exportBudgetCostData/{item_id}")
    public AjaxResult exportBudgetCostData(@PathVariable(value = "item_id", required = false) String item_id)
    {
        List<Percost> list = proInfoService.selectPerCostBudgetById(item_id);
        ExcelUtil<Percost> util = new ExcelUtil<Percost>(Percost.class);
        return util.exportExcel(list, "项目人员成本预估");
    }
    /**
     * 导出项目人员收入预估
     */
    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/exportBudgetIncomeData/{item_id}")
    public AjaxResult exportBudgetIncomeData(@PathVariable(value = "item_id", required = false) String item_id)
    {
        List<Perincome> list = proInfoService.selectPerIncomeBudgetById(item_id);
        ExcelUtil<Perincome> util = new ExcelUtil<Perincome>(Perincome.class);
        return util.exportExcel(list, "项目人员收入预估");
    }
    /**
     * 查询级别变更信息
     */
    @Encrypt(module = "外包人员级别变更信息")
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping(value = { "/getLevelChangeInfo" })
    public TableDataInfo getLevelChangeInfo()
    {
        log.info("---------------查询级别变更信息开始---------------" );
        List<LevelChangeInfo> list = proInfoService.getLevelChangeInfo();
        log.info("---------------查询级别变更信息结束---------------" );

        return getDataTable(list);
    }
    /**
     * 查询级别变更历史信息
     */
    @Encrypt(module = "外包人员级别变更记录")
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping(value = { "/selectLevelChangeHisInfo" })
    public TableDataInfo selectLevelChangeHisInfo()
    {
        log.info("---------------查询级别变更历史信息开始---------------" );
        List<LevelChangeInfo> list = proInfoService.getLevelChangeHisInfo();
        log.info("---------------查询级别变更历史信息结束---------------" );

        return getDataTable(list);
    }

    /**
     * 查询外包人员人数
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
//    @Encrypt(module = "项目信息列表")
    @GetMapping("/selectEmpNum")
    public AjaxResult selectEmpNum(Itemper itemper)
    {
        AjaxResult ajax = AjaxResult.success();
        log.info("---------------查询外包人员人数信息开始---------------" );
        startPage();
        Boolean aBoolean = proInfoService.selectEmpNum(itemper);
        ajax.put("binding",aBoolean);
        log.info("---------------查询外包人员人数信息完成---------------" );
        return ajax;
    }
    /**
     * 获取绑定信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @Encrypt(module = "获取绑定信息")
    @GetMapping("/listDataBindingEmp")
    public TableDataInfo listDataBindingEmp(Itemper Itemper)
    {
        log.info("---------------获取绑定信息开始---------------" );
        startPage();
        List<Itemper> list = proInfoService.listDataBindingEmp(Itemper);
        log.info("---------------获取绑定信息结束---------------" );
        return getDataTable(list);
    }
}
