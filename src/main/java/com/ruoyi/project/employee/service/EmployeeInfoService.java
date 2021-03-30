package com.ruoyi.project.employee.service;

import com.ruoyi.project.employee.domain.EmpCost;
import com.ruoyi.project.employee.domain.EmpDocument;
import com.ruoyi.project.employee.domain.EmpIdleTableExp;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.project.domain.Empidle;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * 服务接口
 * @author CodeGenerator
 * @since 2020-05-10
 */
public interface EmployeeInfoService {

    int deleteByPrimaryKey(String empId);

    int deleteDocByPrimaryKey(String[] empIds);

    int insert(EmployeeInfo record);

    int insertSelective(EmployeeInfo record);

    EmployeeInfo selectByPrimaryKey(String empId);

    int updateByPrimaryKeySelective(EmployeeInfo record);

    int updateByPrimaryKey(EmployeeInfo record);

    List<EmployeeInfo> selectByWhere(EmployeeInfo record);

    List<EmployeeInfo> selectEmpInfoPro(EmployeeInfo record);

    void uploadEduDoc(MultipartFile[] files, String empId, String itemId, String docType) throws Exception;

    List<EmpDocument> selectDocByEmp(String empId);

    File downloadEmpDoc(String empId) throws Exception;

    List<Map<String, Object>> selectEmpCost(EmployeeInfo record);

    List<EmpCost> selectEmpCostHistory(String empId);

    int insertEmpCost(EmpCost record);

    int updateEmpCost(EmpCost record);

    EmpCost getEmpCost(String empId);

    List<EmpIdleTableExp> selectEmpIdle(EmpIdleTableExp record);

    List<EmpIdleTableExp> selectEmpIdleHistory(EmpIdleTableExp record);

    int delCostHistory(String costId);

    String importCost(List<EmpCost> costList, boolean updateSupport, boolean isHistory);

    String importEmpInfo(List<EmployeeInfo> empList, boolean updateSupport);

    List<Map<String, String>> selectHistoryPro(String empId);

    List<Empidle> empIdleStatistic(String empId);

    int deleteEmpIdleData(String idleId);

    List<EmployeeInfo> selectEmpInfoProId(String[] empId);
}

