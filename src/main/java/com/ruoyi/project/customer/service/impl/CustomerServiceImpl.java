package com.ruoyi.project.customer.service.impl;

import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.project.customer.domain.Contract;
import com.ruoyi.project.customer.domain.Customer;
import com.ruoyi.project.customer.mapper.CustomerMapper;
import com.ruoyi.project.customer.service.CustomerService;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.project.domain.Itemper;
import com.ruoyi.project.project.domain.Itemperlevel;
import com.ruoyi.project.tool.gen.util.DateDiffMonth;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
/**
 * 客户基本信息 服务层
 *
 * @author ruoyi
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    /**
     * 查询客户信息列表
     * @return
     */
    @Override
    public List<Customer> selectCustomerList(Customer customer) {
        return customerMapper.selectCustomerList(customer);
    }
    /**
     * 新增客户信息列表
     * @return
     */
    @Override
    @Transactional
    public int insertCustomer(Customer customer) {
        customer.setDel_flag("0");
        return customerMapper.insertCustomer(customer);
    }
    /**
     * 修改项目信息列表
     * @return
     */
    @Override
    @Transactional
    public int updateCustomer(Customer customer) throws SchedulerException {
        return customerMapper.updateCustomer(customer);
    }
    /**
     * 查询客户详细信息列表
     * @return
     */
    @Override
    public Customer selectCusById(String customer_id) {
        return customerMapper.selectCusById(customer_id);
    }
    /**
     * 批量删除客户信息
     *
     * @param customerids 需要删除的任务ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteCustomerByIds(Integer[] customerids) throws SchedulerException {
        return customerMapper.deleteCustomerByIds(customerids);
    }
    /**
     * 根据客户id获取外包人员级别信息
     * @return
     */
    @Override
    public List<Itemperlevel> selectEmpLevelById(String customer_id) {
        return customerMapper.selectEmpLevelById(customer_id);
    }
    /**
     * 新增项目人员级别
     * @return
     */
    @Override
    public int insertEmpLevel(Itemperlevel emplevel) throws SchedulerException, TaskException {
        emplevel.setDel_flag("0");
        return customerMapper.insertEmpLevel(emplevel);
    }
    /**
     * 获取外包人员级别信息
     *
     * @param item_level_id 级别ID
     * @return 集合
     */
    @Override
    public Itemperlevel selectLevelById(String item_level_id) {
        return customerMapper.selectLevelById(item_level_id);
    }
    /**
     * 修改外包人员级别信息列表
     * @return
     */
    @Override
    public int updateEmpLevel(Itemperlevel emplevel) throws SchedulerException {
        return customerMapper.updateEmpLevel(emplevel);
    }
    /**
     * 批量删除外包人员级别信息
     * @return 结果
     */
    @Override
    @Transactional
    public int delEmpLevel(Integer[] item_level_ids) throws SchedulerException
    {
        return customerMapper.delEmpLevel(item_level_ids);
    }
    /**
     * 根据客户ID获取外包人员信息
     *
     * @param customer_id 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo> selectEmpById(String customer_id) {
        return customerMapper.selectEmpById(customer_id);
    }
    /**
     * 查询员工是否绑定客户
     * @return
     */
    @Override
    public List<Itemper> selectIsBindEmpList(Itemper Itemper) {
        Itemper.setDelflag("0");
        return customerMapper.selectIsBindEmpList(Itemper);
    }
    /**
     * 获取外包人员级别详情
     * @return
     */
    @Override
    public Integer queryLevelBycusid(Integer customerid) {
        int count = customerMapper.queryLevelBycusid(customerid);
        return count;
    }
    /**
     * 根据客户ID获取合同信息
     *
     * @param customer_id 调度信息
     * @return 调度任务集合
     */
    public List<Contract> selectContractById(String customer_id) {
        return customerMapper.selectContractById(customer_id);
    }
    /**
     * 新增合同信息
     * @return
     */
    @Override
    public int insertContract(Contract contract)  {
        contract.setDel_flag("0");
        return customerMapper.insertContract(contract);
    }
    /**
     * 根据合同ID获取合同详细信息
     *
     * @param contract_id 合同ID
     * @return 调度任务集合
     */
    @Override
    public Contract selectContractIdById(String contract_id) {
        return customerMapper.selectContractIdById(contract_id);
    }
    /**
     * 修改合同信息列表
     * @return
     */
    @Override
    public int updateContract(Contract contract) throws SchedulerException {
        return customerMapper.updateContract(contract);
    }
    /**
     * 批量删除合同信息
     * @return 结果
     */
    @Override
    public int delContract(Integer[] contract_ids) throws SchedulerException {
        return customerMapper.delContract(contract_ids);
    }
    /**
     * 查询客户详细信息列表，计算出成本信息
     * @return
     */
    @Override
    public Customer selectCostById(String customer_id) {
        Customer customer = customerMapper.selectCusById(customer_id);
        List<EmployeeInfo> list =customerMapper.selectEmpById(customer_id);
        customer.setEmp_num(list.size()+"");
        List<Itemperlevel> list_emplevel = customerMapper.selectEmpLevelById(customer_id);
        Itemperlevel emplevel = new Itemperlevel();
        String levelnum = "";
        String levelprice = "";
        BigDecimal cusTotal = new BigDecimal("0.0000");
        for(int i=0;i<list_emplevel.size();i++){
            emplevel = list_emplevel.get(i);
            levelnum = emplevel.getLevel_num();
            levelprice = emplevel.getLevel_price();
            BigDecimal levelcout = new BigDecimal(levelnum);//级别数量
            BigDecimal levelpri = new BigDecimal(levelprice);//级别定价
            BigDecimal fund = levelcout.multiply(levelpri);
            cusTotal = cusTotal.add(fund);
        }
        //获取合同信息
        List<Contract> list_contract = customerMapper.selectContractById(customer_id);
        Contract contract = new Contract();
        String startdate = "";
        String enddate = "";
        for(int i=0;i<list_contract.size();i++){
            contract = list_contract.get(i);
            startdate = contract.getStart_date();
            enddate = contract.getEnd_date();
        }
        String month = "";
        try {
            long monthday = DateDiffMonth.getMonthDiff(startdate,enddate);
            month = monthday + "";
            System.out.println("=========month==========="+month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BigDecimal itemcycle = new BigDecimal(month);
        BigDecimal contract_fund = cusTotal.multiply(itemcycle);
        customer.setContract_fund(contract_fund);//合同总资金
        //获取人员薪资
        List<EmployeeInfo> list_emp = customerMapper.selectEmpById(customer_id);
        BigDecimal empslaryTotal = new BigDecimal("0.0000");
      /*  if(list_emp.size() > 0){
            for(int i = 0; i < list_emp.size(); i++){
                EmployeeInfo employeeInfo = list_emp.get(i);
                BigDecimal empsalary = employeeInfo.getEmpSalary();
                //人员工资总和
                empslaryTotal = empslaryTotal.add(empsalary.multiply(new BigDecimal("0.0001")));
            }
        }*/
        BigDecimal emp_cost = empslaryTotal.multiply(itemcycle);
        customer.setEmp_cost(emp_cost);//人员成本
        BigDecimal empProfit = contract_fund.subtract(emp_cost);
        customer.setEmpProfit(empProfit);
        return customer;
    }
}
