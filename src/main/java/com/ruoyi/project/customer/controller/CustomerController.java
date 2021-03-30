package com.ruoyi.project.customer.controller;

import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.customer.domain.Contract;
import com.ruoyi.project.customer.domain.Customer;
import com.ruoyi.project.customer.service.CustomerService;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.project.domain.Itemper;
import com.ruoyi.project.project.domain.Itemperlevel;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 查询客户信息列表
 *
 * @author
 */
@RestController
@RequestMapping("/customer/info")
public class CustomerController extends BaseController {
    @Autowired
    private CustomerService customerService;
    /**
     * 查询客户列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping("/list")
    public TableDataInfo list(Customer customer)
    {
        System.out.println("---------------查询客户信息开始---------------" );
        startPage();
        List<Customer> list = customerService.selectCustomerList(customer);
        System.out.println("---------------查询客户信息完成---------------" );
        return getDataTable(list);
    }

    /**
     * 新增客户基本信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @PostMapping("/addCustomer")
    public AjaxResult addProject(@RequestBody Customer customer) throws TaskException, SchedulerException{
        System.out.println("---------------新增客户信息开始---------------" );
        AjaxResult AjaxResult=toAjax(customerService.insertCustomer(customer));
        System.out.println("---------------新增客户信息完成---------------" );
        return AjaxResult;
    }
    /**
     * 修改客户基本信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:edit')")
//    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProject(@RequestBody Customer customer) throws  SchedulerException {
        System.out.println("---------------修改项目信息开始---------------" );
        AjaxResult AjaxResult=toAjax(customerService.updateCustomer(customer));
        System.out.println("---------------修改项目信息结束---------------" );
        return AjaxResult;
    }
    /**
     * 根据客户id获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping(value = { "/{customer_id}" })
    public AjaxResult getPro(@PathVariable(value = "customer_id", required = false) String customer_id)
    {
        AjaxResult ajax = AjaxResult.success();
        /*ajax.put("roles", roleService.selectRoleAll());
        ajax.put("posts", postService.selectPostAll());*/
        if (StringUtils.isNotNull(customer_id))
        {
            ajax.put(AjaxResult.DATA_TAG, customerService.selectCusById(customer_id));
            /*ajax.put("postIds", postService.selectPostListByUserId(userId));l
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));*/
        }
        return ajax;
    }
    /**
     * 删除客户基本信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{customerids}")
    public AjaxResult remove(@PathVariable Integer[] customerids) throws SchedulerException, TaskException
    {
            for(int i=0;i<customerids.length;i++){
                if( customerService.queryLevelBycusid(customerids[i]) != 0 ){
                    return AjaxResult.error("请先删除外包人员级别配置信息！");
                }
            }
        return AjaxResult.success(customerService.deleteCustomerByIds(customerids));
    }
    /**
     * 根据客户id获取外包人员级别详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping(value = { "/emplevellist/{customer_id}" })
    public TableDataInfo emplevellist(@PathVariable(value = "customer_id", required = false) String customer_id)
    {
        System.out.println("---------------查询外包人员级别详细信息开始---------------" );
        startPage();
        List<Itemperlevel> list = customerService.selectEmpLevelById(customer_id);
        System.out.println("---------------查询外包人员级别详细信息完成---------------" );
        return getDataTable(list);
    }
    /**
     * 新增项目人员级别基本信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @PostMapping("/addEmpLevel")
    public AjaxResult addEmpLevel(@RequestBody Itemperlevel emplevel) throws TaskException, SchedulerException {
        System.out.println("---------------新增外包人员级别信息开始---------------" );
        AjaxResult AjaxResult=toAjax(customerService.insertEmpLevel(emplevel));
        System.out.println("---------------新增外包人员级别信息完成---------------" );
        return AjaxResult;
    }
    /**
     * 根据外包人员级别id获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping(value = { "/emplevel/{item_level_id}" })
    public AjaxResult getData(@PathVariable(value = "item_level_id", required = false) String item_level_id)
    {
        AjaxResult ajax = AjaxResult.success();
        /*ajax.put("roles", roleService.selectRoleAll());
        ajax.put("posts", postService.selectPostAll());*/
        if (StringUtils.isNotNull(item_level_id))
        {
            ajax.put(AjaxResult.DATA_TAG, customerService.selectLevelById(item_level_id));
            /*ajax.put("postIds", postService.selectPostListByUserId(userId));l
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));*/
        }
        return ajax;
    }
    /**
     * 修改外包人员级别基本信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:edit')")
//    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/updatedata")
    public AjaxResult updatePerLevel(@RequestBody Itemperlevel emplevel) throws  SchedulerException {
        System.out.println("---------------修改外包人员级别信息开始---------------" );
        AjaxResult AjaxResult=toAjax(customerService.updateEmpLevel(emplevel));
        System.out.println("---------------修改外包人员级别信息结束---------------" );
        return AjaxResult;
    }

    /**
     * 删除外包人员级别信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/deldata/{item_level_ids}")
    public AjaxResult delEmpLevel(@PathVariable Integer[] item_level_ids) throws SchedulerException, TaskException
    {
        return toAjax(customerService.delEmpLevel(item_level_ids));
    }
    /**
     * 根据客户id获取项目人员详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping(value = { "/assignemplist/{customer_id}" })
    public TableDataInfo assignemplist(@PathVariable(value = "customer_id", required = false) String customer_id)
    {
        System.out.println("---------------查询外包人员详细信息开始---------------" );
        startPage();
        List<EmployeeInfo> list = customerService.selectEmpById(customer_id);
        System.out.println("---------------查询外包人员详细信息完成---------------" );
        return getDataTable(list);
    }
    /**
     * 获取员工是否绑定客户
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/listIsBindEmp")
    public TableDataInfo listIsBindEmp(Itemper Itemper)
    {
        startPage();
        List<Itemper> list = customerService.selectIsBindEmpList(Itemper);
        return getDataTable(list);
    }
    /**
     * 根据客户id获取合同详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping(value = { "/contractlist/{customer_id}" })
    public TableDataInfo contractlist(@PathVariable(value = "customer_id", required = false) String customer_id)
    {
        System.out.println("---------------查询合同详细信息开始---------------" );
        startPage();
        List<Contract> list = customerService.selectContractById(customer_id);
        System.out.println("---------------查询合同详细信息完成---------------" );
        return getDataTable(list);
    }
    /**
     * 新增合同基本信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @PostMapping("/addContract")
    public AjaxResult addContract(@RequestBody Contract contract) throws TaskException, SchedulerException{
        System.out.println("---------------新增合同信息开始---------------" );
        AjaxResult AjaxResult=toAjax(customerService.insertContract(contract));
        System.out.println("---------------新增合同信息完成---------------" );
        return AjaxResult;
    }
    /**
     * 根据合同id获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping(value = { "/selectContract/{contract_id}" })
    public AjaxResult selectContract(@PathVariable(value = "contract_id", required = false) String contract_id)
    {
        AjaxResult ajax = AjaxResult.success();
        /*ajax.put("roles", roleService.selectRoleAll());
        ajax.put("posts", postService.selectPostAll());*/
        if (StringUtils.isNotNull(contract_id))
        {
            ajax.put(AjaxResult.DATA_TAG, customerService.selectContractIdById(contract_id));
            /*ajax.put("postIds", postService.selectPostListByUserId(userId));l
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));*/
        }
        return ajax;
    }
    /**
     * 修改合同基本信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:edit')")
//    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/updatecontract")
    public AjaxResult updateContract(@RequestBody Contract contract) throws  SchedulerException {
        System.out.println("---------------修改合同信息开始---------------" );
        AjaxResult AjaxResult=toAjax(customerService.updateContract(contract));
        System.out.println("---------------修改合同信息结束---------------" );
        return AjaxResult;
    }
    /**
     * 删除合同信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/delcontract/{contract_ids}")
    public AjaxResult delContract(@PathVariable Integer[] contract_ids) throws SchedulerException, TaskException
    {
        return toAjax(customerService.delContract(contract_ids));
    }
    /**
     * 根据客户id计算出项目成本
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping(value = { "/selectOutsourcingCost/{customerid}" })
    public AjaxResult selectOutsourcingCost(@PathVariable(value = "customerid", required = false) String customerid)
    {
        AjaxResult ajax = AjaxResult.success();
        /*ajax.put("roles", roleService.selectRoleAll());
        ajax.put("posts", postService.selectPostAll());*/
        if (StringUtils.isNotNull(customerid))
        {
            ajax.put(AjaxResult.DATA_TAG, customerService.selectCostById(customerid));
            /*ajax.put("postIds", postService.selectPostListByUserId(userId));l
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));*/
        }
        return ajax;
    }
    /**
     * 导出客户信息
     */
    @Log(title = "客户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/exportCustomer")
    public AjaxResult exportCustomer(Customer customer)
    {
        List<Customer> list = customerService.selectCustomerList(customer);
        ExcelUtil<Customer> util = new ExcelUtil<Customer>(Customer.class);
        return util.exportExcel(list, "客户数据");
    }
}
