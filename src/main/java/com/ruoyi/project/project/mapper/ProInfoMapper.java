package com.ruoyi.project.project.mapper;

import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.project.customer.domain.Contract;
import com.ruoyi.project.customer.domain.Customer;
import com.ruoyi.project.employee.domain.EducationInfo;
import com.ruoyi.project.employee.domain.EmpCost;
import com.ruoyi.project.employee.domain.EmpDocument;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.project.domain.*;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Map;

/**
 * 调度任务信息 数据层
 * 
 * @author ruoyi
 */
public interface ProInfoMapper
{
    /**
     * 查询项目列表
     * @return
     */
    public List<Item> selectProjectList(Item item);
    /**
     * 获取项目详情
     *
     * @return 调度任务集合
     */
    public Item selectProById(String item_id);

    /**
     * 写入项目列表
     * @return
     */
    public int insertProject(Item Item);

    /**
     * 写入历史表
     * @return
     */
    public int addProject(Integer item_id);
    /**
     * 删除项目信息列表
     * @return 结果
     */
    public int deleteProjectByIds(Integer[] item_ids);

    /**
     * 修改项目基本信息
     * @return
     */
    public int updateProject(Item Item);
    /**
     * 获取项目变更记录详情
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<Item> selectRecProById(String item_id);
    /**
     * 获取项目人员级别信息
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<Itemperlevel> selectPerLevelById(String item_id);
    /**
     * 新增项目人员级别
     *
     * @param itemperlevel 调度信息
     * @return 结果
     */
    public int insertPerLevel(Itemperlevel itemperlevel);
    /**
     * 写入人员级别历史表
     * @return
     */
//    public int addPerLevelhis(Itemperlevel itemperlevel);
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
    public int updatePerLevel(Itemperlevel itemperlevel) ;
    /**
     * 批量删除项目人员级别信息
     *
     * @param item_level_ids
     * @return 结果
     */
    public int delPerLevel(Integer[] item_level_ids) ;
    /**
     * 根据item_id获取项目人员级别信息
     *
     * @param item_id
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
     * 获取项目人员信息
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo>  selectEmpByItemId(String item_id);
    /**
     * 获取项目人员薪资变更信息
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public EmpCost selectEmpSalaryById(String item_id);
    /**
     * 获取项目人员薪资变更记录信息
     *
     * @param empId 调度信息
     * @return 调度任务集合
     */
    public EmpCost selectHisSalaryById(String empId);
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
     * 查询员工是否解绑
     *
     * @param Itemper 调度信息
     * @return 调度任务集合
     */
    public Itemper selectUnbundling(Itemper Itemper);
    /**
     * 根据项目id获取合同详细信息
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<Contract>  selectContractById(String item_id);
    /**
     * 获取合同附件信息
     */
    public List<EmpDocument> selectDocByContract(String itemId);
    /**
     * 获取合同信息
     */
    public Contract selectByPrimaryKey(String contract_id);
    /**
     * 获取客户信息数据
     *
     * @return 数据集合信息
     */
    public List<Customer> selectCustomer(String item_type);
    /**
     * 项目人员级别是否存在
     *
     * @param itemperlevel 调度信息
     * @return 调度任务集合
     */
    public List<Itemperlevel>  selectIsLevel(Itemperlevel itemperlevel);

    /**
     * 获取级别id获取人员信息
     *
     * @param item_level_id 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo>  selectPerById(String item_level_id);
    /**
     * 获取级别id获取外包人员信息
     *
     * @param item_level_id 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo>  selectOutPerById(String item_level_id);
    /**
     * 查询员工绑定信息
     *
     * @param Itemper 调度信息
     * @return 调度任务集合
     */
    public Itemper selectbundling(Itemper Itemper);

    /**
     * 获取外包人员绑定信息
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo>  listPerBinding(String item_id);

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
     * @param empidle
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
     * @return
     */
    public int updateEmpidle(Empidle empidle);
    /**
     * 查询员工做过的历史项目。
     * @param empId
     * @return
     */
    public List<Map<String, String>> selectHistoryPro(String empId);

    /**
     * 查询员工绑定信息
     *
     * @param Itemper 调度信息
     * @return 调度任务集合
     */
    public List<Itemper> selectbindling(Itemper Itemper);

    /**
     * 查询外包员工绑定信息
     *
     * @param Itemper 调度信息
     * @return 调度任务集合
     */
    public List<Itemper> selectPercostbundling(Itemper Itemper);
    /**
     * 查询外包员工是否解绑记录
     *
     * @param Itemper 调度信息
     * @return 调度任务集合
     */
    public List<Itemper> selectIsUnbundling(Itemper Itemper);
    /**
     * 查询外包员工级别ID-
     *
     * @param Itemper 调度信息
     * @return 结果
     */
    public String selectLevelId(Itemper Itemper);

    /**
     * 查询外包员工绑定级别信息
     * @return 调度任务集合
     */
    public List<Itemper> selectBinding();
    /**
     * 写入级别变更消息表
     *
     * @param levelChangeInfo 调度信息
     * @return 结果
     */
    public int addLevelChangeInfo(LevelChangeInfo levelChangeInfo);
    /**
     * 查询级别变更信息
     */
    public List<LevelChangeInfo> getLevelChangeInfo(LevelChangeInfo levelChangeInfo);

    /**
     * 修改外包人员更改级别状态
     *
     * @param levelChangeInfo
     * @return 结果
     */
    public int updateLevelChangeInfo(LevelChangeInfo levelChangeInfo);
    /**
     * 获取项目级别对应的人数
     *
     * @return 调度任务集合
     */
    public Itemperlevel selectLevelnum(Itemperlevel itemperlevel);
    /**
     * 获取人员id
     *
     * @return
     */
    public String selectEmpId(String IdCard);
}
