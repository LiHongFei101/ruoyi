package com.ruoyi.project.project.service;

import java.io.File;
import java.util.List;

import com.ruoyi.project.customer.domain.Contract;
import com.ruoyi.project.customer.domain.Customer;
import com.ruoyi.project.employee.domain.EmpCost;
import com.ruoyi.project.employee.domain.EmpDocument;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.project.domain.*;
import org.quartz.SchedulerException;
import com.ruoyi.common.exception.job.TaskException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 定时任务调度信息信息 服务层
 *
 * @author ruoyi
 */
public interface ProInfoService
{
    /**
     * 获取quartz调度器的计划任务
     *
     * @param item 调度信息
     * @return 调度任务集合
     */
    public List<Item> selectProjectList(Item item);

    /**
     * 获取项目详情
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public Item selectProById(String item_id);

    /**
     * 新增任务
     *
     * @param Item 调度信息
     * @return 结果
     */
    public int insertProject(Item Item);


    /**
     * 批量删除项目信息
     *
     * @param item_ids 需要删除的任务ID
     * @return 结果
     */
    public int deleteProjectByIds(Integer[] item_ids);

    /**
     * 修改删除项目信息
     *
     * @param Item 需要删除的任务ID
     * @return 结果
     */
    public int updateProject(Item Item);
    /**
     * 获取项目变更记录详情
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<Item>  selectRecProById(String item_id);
    /**
     * 获取项目人员级别信息
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<Itemperlevel>  selectPerLevelById(String item_id);
    /**
     * 新增项目人员级别
     *
     * @param itemperlevel 调度信息
     * @return 结果
     */
    public int insertPerLevel(Itemperlevel itemperlevel);
    /**
     * 获取项目人员级别详细信息
     *
     * @param item_level_id 调度信息
     * @return 调度任务集合
     */
    public Itemperlevel  selectLevelById(String item_level_id);

    /**
     * 修改项目人员级别
     *
     * @param itemperlevel
     * @return 结果
     */
    public int updatePerLevel(Itemperlevel itemperlevel);
    /**
     * 批量删除项目人员级别信息
     *
     * @param item_level_ids
     * @return 结果
     */
    public int delPerLevel(Integer[] item_level_ids);

    /**
     * 根据item_id获取项目人员级别信息
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public Integer  queryLevelByitem_id(Integer item_id);

    /**
     * 获取项目人员信息
     *
     * @param employeeInfo 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo>  selectEmp(EmployeeInfo employeeInfo);

    /**
     * 新增项目人员绑定
     *
     * @param Itemper 调度信息
     * @return 结果
     */
    public int insertProper(Itemper Itemper);

    /**
     * 解绑项目人员
     *
     * @param Itemper
     * @return 结果
     */
    public int updateProPer(Itemper Itemper);

    /**
     * 修改项目人员绑定信息
     *
     * @param Itemper
     * @return 结果
     */
    public int updateBinding(Itemper Itemper);

    /**
     * 获取员工列表
     *
     * @param employeeInfo 员工
     * @return 调度任务集合
     */
    public List<EmployeeInfo> selectEmpList(EmployeeInfo employeeInfo);
    /**
     * 查询员工是否绑定项目
     *
     * @param Itemper 调度信息
     * @return 调度任务集合
     */
    public List<Itemper> selectIsBindEmpList(Itemper Itemper);
    /**
     * 查询员工是解绑定此项目
     *
     * @param Itemper 调度信息
     * @return 调度任务集合
     */
    public List<Itemper> selectIsunBindEmp(Itemper Itemper);
    /**
     * 根据项目id获取合同详细信息
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<Contract>  selectContractById(String item_id);
    /**
     * 上传项目合同附件信息
     */
    public void uploadContractDoc(MultipartFile[] files, String empId, String itemId, String docType) throws Exception;
    /**
     * 获取合同附件信息
     */
    public List<EmpDocument> selectDocByContract(String itemId);
    /**
     * 下载合同附件信息
     */
    File downloadContractDoc(String contract_id) throws Exception;
    /**
     * 获取客户信息数据
     *
     * @return 数据集合信息
     */
    public List<Customer> selectCustomer(String item_type);
    /**
     * 根据项目ID获取人员成本
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<Percost> selectPerCostById(String item_id);
    /**
     * 项目人员级别是否存在
     *
     * @param itemperlevel 调度信息
     * @return 调度任务集合
     */
    public List<Itemperlevel>  selectIsLevel(Itemperlevel itemperlevel);

    /**
     * 根据项目ID获取外包人员收入
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<Perincome> selectPerIncomeById(String item_id);
    /**
     * 获取外包人员信息
     *
     * @param item_level_id 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo>  selectOutPerById(String item_level_id);
    /**
     * 获取外包人员绑定信息
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo>  listPerBinding(String item_id);
    /**
     * 判断项目开始日期是否大于当日
     *
     * @param Itemper 绑定
     * @return 调度任务集合
     */
    public Boolean  CompareItemdate(Itemper Itemper);

    /**
     * 查询员工是否设置工资信息
     *
     * @param empCost
     * @return
     */
    public List<EmpCost> selectEmpcost(EmpCost empCost);
    /**
     * 新增项目人员闲置信息
     *
     * @param empidle 调度信息
     * @return 结果
     */
    public int insertEmpidle(Empidle empidle);
    /**
     * 查询项目人员闲置信息
     *
     * @param empid
     * @return 结果
     */
    public Empidle selectEmpidle(int empid);

    /**
     * 修改闲置历史表
     *
     * @param empidle
     * @return 结果
     */
    public int updateEmpidle(Empidle empidle);
    /**
     * 根据项目ID获取人员预估成本
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<Percost> selectPerCostBudgetById(String item_id);
    /**
     * 根据项目ID获取人员预估收入成本
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<Perincome> selectPerIncomeBudgetById(String item_id);
    /**
     * 查询级别变更信息
     */
    public List<LevelChangeInfo> getLevelChangeInfo();
    /**
     * 查询级别变更历史
     */
    public List<LevelChangeInfo> getLevelChangeHisInfo();
    /**
     * 查询外包员工绑定人数
     *
     * @param Itemper 调度信息
     * @return 调度任务集合
     */
    public Boolean selectEmpNum(Itemper Itemper);
    /**
     * 查询项目绑定信息
     *
     * @param itemper
     * @return
     */
    public List<Itemper> listDataBindingEmp(Itemper itemper);
}