package com.ruoyi.project.customer.mapper;

import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.project.customer.domain.Contract;
import com.ruoyi.project.customer.domain.Customer;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.project.domain.Itemper;
import com.ruoyi.project.project.domain.Itemperlevel;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 客户基本信息 数据层
 *
 * @author ruoyi
 */
public interface CustomerMapper {
    /**
     * 获取客户基本信息
     *
     * @param customer 客户基本信息
     * @return 客户基本信息集合
     */
    public List<Customer> selectCustomerList(Customer customer);
    /**
     * 新增任务
     *
     * @param customer 客户基本信息
     * @return 结果
     */
    public int insertCustomer(Customer customer);
    /**
     * 修改客户基本信息
     *
     * @param customer
     * @return 结果
     */
    public int updateCustomer(Customer customer) throws SchedulerException;
    /**
     * 根据ID获取客户基本信息
     *
     * @param customer_id
     * @return 集合
     */
    public Customer selectCusById(String customer_id);
    /**
     * 批量删除客户信息
     *
     * @param customerids 需要删除的任务ID
     * @return 结果
     */
    public int deleteCustomerByIds(Integer[] customerids) throws SchedulerException;
    /**
     * 根据客户id获取外包人员级别信息
     *
     * @param customer_id 客户ID
     * @return 调度任务集合
     */
    public List<Itemperlevel>  selectEmpLevelById(String customer_id);
    /**
     * 新增外包人员级别
     *
     * @param emplevel 人员级别
     * @return 结果
     */
    public int insertEmpLevel(Itemperlevel emplevel) throws SchedulerException, TaskException;
    /**
     * 获取外包人员级别信息
     *
     * @param item_level_id 级别ID
     * @return 集合
     */
    public Itemperlevel  selectLevelById(String item_level_id);
    /**
     * 修改外包人员级别
     *
     * @param emplevel
     * @return 结果
     */
    public int updateEmpLevel(Itemperlevel emplevel) throws SchedulerException;
    /**
     * 批量删除外包人员级别信息
     *
     * @param item_level_ids
     * @return 结果
     */
    public int delEmpLevel(Integer[] item_level_ids) throws SchedulerException;
    /**
     * 根据客户ID获取外包人员信息
     *
     * @param customer_id 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo>  selectEmpById(String customer_id);
    /**
     * 查询员工是否绑定客户
     *
     * @param Itemper 调度信息
     * @return 调度任务集合
     */
    public List<Itemper> selectIsBindEmpList(Itemper Itemper);
    /**
     * 根据customerids获取外包人员级别信息
     *
     * @param customerid 调度信息
     * @return 调度任务集合
     */
    public Integer  queryLevelBycusid(Integer customerid);
    /**
     * 根据客户ID获取外包人员信息
     *
     * @param customer_id 调度信息
     * @return 调度任务集合
     */
    public List<Contract>  selectContractById(String customer_id);
    /**
     * 新增合同信息
     *
     * @param contract 合同基本信息
     * @return 结果
     */
    public int insertContract(Contract contract);
    /**
     * 根据合同ID获取合同详细信息
     *
     * @param contract_id 合同ID
     * @return 调度任务集合
     */
    public Contract  selectContractIdById(String contract_id);
    /**
     * 修改合同信息
     *
     * @param contract
     * @return 结果
     */
    public int updateContract(Contract contract) throws SchedulerException;
    /**
     * 批量删除合同信息
     *
     * @param contract_ids
     * @return 结果
     */
    public int delContract(Integer[] contract_ids) throws SchedulerException;
}
