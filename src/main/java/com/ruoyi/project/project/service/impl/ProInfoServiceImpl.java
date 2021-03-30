package com.ruoyi.project.project.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.customer.domain.Contract;
import com.ruoyi.project.customer.domain.Customer;
import com.ruoyi.project.employee.domain.EmpCost;
import com.ruoyi.project.employee.domain.EmpDocument;
import com.ruoyi.project.employee.domain.EmployeeInfo;
import com.ruoyi.project.employee.mapper.EmpCostMapper;
import com.ruoyi.project.employee.mapper.EmpDocumentMapper;
import com.ruoyi.project.employee.mapper.EmployeeInfoMapper;
import com.ruoyi.project.project.domain.*;
import com.ruoyi.project.project.mapper.ProInfoMapper;
import com.ruoyi.project.project.service.ProInfoService;
import com.ruoyi.project.tool.gen.util.DateDiffMonth;
import com.ruoyi.project.tool.gen.util.EmpcostUtil;
import com.ruoyi.project.tool.gen.util.GetDateUtils;
import com.ruoyi.project.tool.gen.util.PerCostAndIncomeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 服务层
 *
 * @author ruoyi
 */
@Service
public class ProInfoServiceImpl implements ProInfoService
{

    @Autowired
    private ProInfoMapper proInfoMapper;
    @Autowired
    private EmpDocumentMapper empDocumentMapper;
    @Autowired
    private EmpCostMapper empCostMapper;
    @Autowired
    private EmployeeInfoMapper employeeInfoMapper;
    /**
     * 查询项目信息列表
     * @return
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<Item> selectProjectList(Item item) {
        return proInfoMapper.selectProjectList(item);
    }
    /**
     * 查询项目详细信息列表
     * @return
     */
    @Override
    public Item selectProById(String item_id) {
        return proInfoMapper.selectProById(item_id);
    }

    /**
     * 新增项目信息列表
     * @return
     */
    @Override
    @Transactional
    public int insertProject(Item Item) {
        Item.setChange_num("0");
        if(Item.getIdCard() == null || Item.getIdCard().equals("")){
            EmployeeInfo employeeInfo = employeeInfoMapper.selectByPrimaryKey(Item.getEmpId());
            Item.setIdCard(employeeInfo.getIdCard());
        }
        if(Item.getEmpId() == null || Item.getEmpId().equals("")){
            EmployeeInfo employeeInfo = new EmployeeInfo();
            employeeInfo.setIdCard(Item.getIdCard());
            List<EmployeeInfo> list = employeeInfoMapper.selectByWhere(employeeInfo);
            employeeInfo = list.get(0);
            Item.setEmpId(employeeInfo.getEmpId());
        }
        return proInfoMapper.insertProject(Item);
    }

    /**
     * 批量删除项目信息
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteProjectByIds(Integer[] item_ids)
    {
        return proInfoMapper.deleteProjectByIds(item_ids);
    }

    /**
     * 修改项目信息列表
     * @return
     */
    @Override
    @Transactional
    public int updateProject(Item Item)  {
        String ischange = Item.getIschange();
        Integer item_id =  Item.getItem_id();
        String change_num = Item.getChange_num();
        if(change_num==null ||change_num.equals("")){
            change_num = "0";
        }
        int num = 0;
        if(("0").equals(ischange)){
            num = Integer.parseInt(change_num);
            num++;
            Item.setChange_num(String.valueOf(num));
            proInfoMapper.addProject(item_id);
            return proInfoMapper.updateProject(Item);
        }
            return proInfoMapper.updateProject(Item);
    }
    /**
     * 获取项目变更记录详情
     * @return
     */
    @Override
    public List<Item> selectRecProById(String item_id) {
        return proInfoMapper.selectRecProById(item_id);
    }
    /**
     * 获取项目人员级别信息
     * @return
     */
    @Override
    public List<Itemperlevel> selectPerLevelById(String item_id) {
        return proInfoMapper.selectPerLevelById(item_id);
    }

    /**
     * 新增项目人员级别
     * @return
     */
    @Override
    @Transactional
    public int insertPerLevel(Itemperlevel itemperlevel){
        itemperlevel.setDel_flag("0");
//        itemperlevel.setChange_num("0");
        return proInfoMapper.insertPerLevel(itemperlevel);
    }
    /**
     * 获取项目人员级别详情
     * @return
     */
    @Override
    public Itemperlevel selectLevelById(String item_level_id) {
        return proInfoMapper.selectLevelById(item_level_id);
    }

    /**
     * 修改项目人员级别信息列表
     * @return
     */
    @Override
    @Transactional
    public int updatePerLevel(Itemperlevel itemperlevel)  {
//        String ischange = itemperlevel.getIschange();
//        Integer item_id =  itemperlevel.getItem_id();
//        String change_num = itemperlevel.getChange_num();
//        if( change_num==null || change_num.equals("")){
//            change_num = "0";
//        }
//        int num = 0;
//        if(("0").equals(ischange)){
//            num = Integer.parseInt(change_num);
//            num++;
//            itemperlevel.setChange_num(String.valueOf(num));
//            proInfoMapper.addPerLevelhis(itemperlevel);
//            return proInfoMapper.updatePerLevel(itemperlevel);
//        }
        return proInfoMapper.updatePerLevel(itemperlevel);
    }

    /**
     * 批量删除项目人员级别信息
     * @return 结果
     */
    @Override
    @Transactional
    public int delPerLevel(Integer[] item_level_ids)
    {
        return proInfoMapper.delPerLevel(item_level_ids);
    }

    /**
     * 获取项目人员级别详情
     * @return
     */
    @Override
    public Integer queryLevelByitem_id(Integer item_id) {
        int count = proInfoMapper.queryLevelByitem_id(item_id);
        return count;
    }
    /**
     * 获取项目人员信息
     *
     * @param employeeInfo 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo> selectEmp(EmployeeInfo employeeInfo) {
        return proInfoMapper.selectEmp(employeeInfo);
    }

    /**
     * 新增项目人员绑定
     * @return
     */
    @Override
    @Transactional
    public int insertProper(Itemper Itemper) {
        Itemper.setDelflag("0");
        String item_level_id = Itemper.getItem_level_id();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startdate = sdf.format(new Date());
        Itemper.setStartdate(startdate);
        if(item_level_id != null){
            if(Integer.parseInt(item_level_id)>0){
                Itemper.setEmptype("2");
            }else{
                Itemper.setEmptype("1");
            }
        }else {
            Itemper.setEmptype("1");
        }
        Empidle empidle = new Empidle();
        empidle.setEmpId(Itemper.getEmpid());
        EmployeeInfo employeeInfo = new EmployeeInfo();
        List<Itemper> list = proInfoMapper.selectbindling(Itemper);
        Itemper itemper = new Itemper();
        if(list.size()>0) {
            itemper = list.get(0);
            empidle.setStartdate(itemper.getEnddate());
            empidle.setBeforeitemId(itemper.getItemid());
        }else {
            employeeInfo = employeeInfoMapper.selectByPrimaryKey(String.valueOf(Itemper.getEmpid()));
            empidle.setStartdate(employeeInfo.getEmpEntrydate());
        }
        empidle.setEnddate(startdate);
        empidle.setDelFlag("0");
        DateDiffMonth dateDiffMonth = new DateDiffMonth();
        int days = dateDiffMonth.nDaysBetweenTwoDate(empidle.getStartdate(),empidle.getEnddate());
        if(days>=2){
            proInfoMapper.insertEmpidle(empidle);
        }
        List<Itemper> list_unbind = proInfoMapper.selectIsUnbundling(Itemper);
        String start ="";
        if(list_unbind.size()>0){
            Itemper itemper1 = list_unbind.get(0);
             start = itemper1.getStartdate();
            Itemper.setStartdate(start);
        }
        //判断是否绑定人数超过此项目录入总人数
        Itemper itemper1 = new Itemper();
        itemper1.setDelflag("0");
        itemper1.setItemid(Itemper.getItemid());
        List<Itemper> list_b = proInfoMapper.selectIsBindEmpList(itemper1);
        Item item = proInfoMapper.selectProById(String.valueOf(Itemper.getItemid()));
        if(item!=null){
            String itemnum = item.getItem_num();
            if(Integer.parseInt(itemnum)<=list_b.size()){
                int num = Integer.parseInt(itemnum)+1;
                Item item1 = new Item();
                item1.setItem_id(Integer.parseInt(Itemper.getItemid()));
                item1.setItem_num(String.valueOf(num));
                proInfoMapper.updateProject(item1);
            }
        }
        //判断绑定人数是否超过此级别人数
        Itemper itemper2 = new Itemper();
        itemper2.setDelflag("0");
        itemper2.setItemid(Itemper.getItemid());
        itemper2.setItem_level_id(Itemper.getItem_level_id());
        List<Itemper> list_l = proInfoMapper.selectIsBindEmpList(itemper2);
        Itemperlevel itemperlevel = new Itemperlevel();
        itemperlevel.setItem_id(Itemper.getItemid());
        itemperlevel.setItem_level_id(Itemper.getItem_level_id());
        Itemperlevel itemperlevel1 = proInfoMapper.selectLevelnum(itemperlevel);
        if(itemperlevel1!=null){
            String levelnum = itemperlevel1.getLevel_num();
            if(Integer.parseInt(levelnum)<=list_l.size()){
                int num = Integer.parseInt(levelnum)+1;
                Itemperlevel itemperlevel2 = new Itemperlevel();
                itemperlevel2.setLevel_name(itemperlevel1.getLevel_name());
                itemperlevel2.setLevel_price(itemperlevel1.getLevel_price());
                itemperlevel2.setItem_level_id(Itemper.getItem_level_id());
                itemperlevel2.setLevel_num(String.valueOf(num));
                proInfoMapper.updatePerLevel(itemperlevel2);
            }
        }
        return proInfoMapper.insertProper(Itemper);
    }
    /**
     * 解绑项目人员
     *
     * @param Itemper
     * @return 结果
     */
    @Override
    @Transactional
    public int updateProPer(Itemper Itemper) {
        Itemper.setDelflag("2");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = sdf.format(new Date());
        Itemper.setEnddate(enddate);
        String item_level_id = proInfoMapper.selectLevelId(Itemper);
        if(item_level_id != null && !item_level_id.equals("")){
            Itemper.setItem_level_id(item_level_id);
            LevelChangeInfo levelChangeInfo = new LevelChangeInfo();
            levelChangeInfo.setItem_id(Itemper.getItemid());
            levelChangeInfo.setEmp_id(Itemper.getEmpid());
            levelChangeInfo.setItem_level_id(item_level_id);
            levelChangeInfo.setLcstatus("1");
            proInfoMapper.updateLevelChangeInfo(levelChangeInfo);
        }
        return proInfoMapper.updateProPer(Itemper);
    }
    /**
     * 修改项目人员绑定信息
     *
     * @param Itemper
     * @return 结果
     */
    @Override
    @Transactional
    public int updateBinding(Itemper Itemper)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startdate = sdf.format(new Date());
        String item_level_id = Itemper.getItem_level_id();
        Itemper.setStartdate(startdate);
        Itemper.setEnddate("");
        if(item_level_id != null){
            if(Integer.parseInt(item_level_id)>0){
                Itemper.setEmptype("2");
            }else{
                Itemper.setEmptype("1");
            }
        }else {
            Itemper.setEmptype("1");
        }
        Empidle empidle = new Empidle();
        empidle.setEmpId(Itemper.getEmpid());
        EmployeeInfo employeeInfo = new EmployeeInfo();
        List<Itemper> list = proInfoMapper.selectbindling(Itemper);
        Itemper itemper = new Itemper();
        if(list.size()>0) {
            itemper = list.get(0);
            empidle.setStartdate(itemper.getEnddate());
            empidle.setBeforeitemId(itemper.getItemid());
        }else {
            employeeInfo = employeeInfoMapper.selectByPrimaryKey(String.valueOf(Itemper.getEmpid()));
            empidle.setStartdate(employeeInfo.getEmpEntrydate());
        }
        empidle.setEnddate(startdate);
        empidle.setDelFlag("0");

        DateDiffMonth dateDiffMonth = new DateDiffMonth();
        int days = dateDiffMonth.nDaysBetweenTwoDate(empidle.getStartdate(),empidle.getEnddate());
        if(days>=2){
            proInfoMapper.insertEmpidle(empidle);
        }
        //判断是否绑定人数超过录入总人数
        Itemper itemper1 = new Itemper();
        itemper1.setDelflag("0");
        itemper1.setItemid(Itemper.getItemid());
        List<Itemper> list_b = proInfoMapper.selectIsBindEmpList(itemper1);
        Item item = proInfoMapper.selectProById(String.valueOf(Itemper.getItemid()));
        String itemnum = item.getItem_num();
        if(Integer.parseInt(itemnum)<=list_b.size()){
            int num = Integer.parseInt(itemnum)+1;
            Item item1 = new Item();
            item1.setItem_id(Integer.parseInt(Itemper.getItemid()));
            item1.setItem_num(String.valueOf(num));
            proInfoMapper.updateProject(item1);
        }
        //判断绑定人数是否超过此级别人数
        Itemper itemper2 = new Itemper();
        itemper2.setDelflag("0");
        itemper2.setItemid(Itemper.getItemid());
        itemper2.setItem_level_id(Itemper.getItem_level_id());
        List<Itemper> list_l = proInfoMapper.selectIsBindEmpList(itemper2);
        Itemperlevel itemperlevel = new Itemperlevel();
        itemperlevel.setItem_id(Itemper.getItemid());
        itemperlevel.setItem_level_id(Itemper.getItem_level_id());
        Itemperlevel itemperlevel1 = proInfoMapper.selectLevelnum(itemperlevel);
        if(itemperlevel1 != null){
            String levelnum = itemperlevel1.getLevel_num();
            if(Integer.parseInt(levelnum)<=list_l.size()){
                int num = Integer.parseInt(levelnum)+1;
                Itemperlevel itemperlevel2 = new Itemperlevel();
                itemperlevel2.setLevel_name(itemperlevel1.getLevel_name());
                itemperlevel2.setLevel_price(itemperlevel1.getLevel_price());
                itemperlevel2.setItem_level_id(Itemper.getItem_level_id());
                itemperlevel2.setLevel_num(String.valueOf(num));
                proInfoMapper.updatePerLevel(itemperlevel2);
            }
        }
        return proInfoMapper.updateBinding(Itemper);
    }

    /**
     * 查询员工信息列表
     * @return
     */
    @Override
    public List<EmployeeInfo> selectEmpList(EmployeeInfo employeeInfo) {
        return proInfoMapper.selectEmpList(employeeInfo);
    }

    /**
     * 查询员工是否绑定项目
     * @return
     */
    @Override
    public List<Itemper> selectIsBindEmpList(Itemper Itemper) {
        Itemper.setDelflag("0");
        return proInfoMapper.selectIsBindEmpList(Itemper);
    }

    /**
     * 查询员工是否解绑绑定此项目
     * @return
     */
    @Override
    public List<Itemper> selectIsunBindEmp(Itemper Itemper) {
        Itemper.setDelflag("2");
        return proInfoMapper.selectIsBindEmpList(Itemper);
    }
   /* public Item selectProCostById(String item_id) {
        Item item = proInfoMapper.selectProById(item_id);
        String itemfund = item.getItem_fund();//项目资金
        String itemnum = item.getItem_num();//项目总人数
        String startDate = item.getItem_startdate();//开始日期
        String endDate = item.getItem_enddate();//结束日期
        String month = "";
        try {
            long monthday = DateDiffMonth.getMonthDiff(startDate, endDate);
            month = monthday + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        item.setItem_cycle(month);
        BigDecimal itemcycle = new BigDecimal(month);
        BigDecimal Total = new BigDecimal(itemfund);
        BigDecimal empslaryTotal = new BigDecimal("0.0000");
        List<EmployeeInfo> listEmp = proInfoMapper.selectEmpById(item_id);
        //        if(listEmp.size()>0 && Integer.parseInt(itemnum)==listEmp.size()){
       *//* if(listEmp.size()>0){
            for(int i = 0; i<listEmp.size(); i++){
                EmployeeInfo employeeInfo = listEmp.get(i);
                BigDecimal empsalary = employeeInfo.getEmpSalary();
                empslaryTotal = empslaryTotal.add(empsalary.multiply(new BigDecimal("0.0001")));
            }
        }*//*
        if (("1").equals(item.getItem_type())) {
            //成本
            BigDecimal itemCost = empslaryTotal.multiply(itemcycle);
            item.setItemCost(itemCost);
            //营收
            item.setItemProfit(new BigDecimal("0.0000"));
        } else {
            //成本
            BigDecimal itemCost = empslaryTotal.multiply(itemcycle);
            item.setItemCost(itemCost);
            //营收
            BigDecimal itemProfit = Total.subtract(itemCost);
            item.setItemProfit(itemProfit);
        }
        return item;
    }*/
    /*public Item selectProCostById(String item_id) {
        Item item = proInfoMapper.selectProById(item_id);
        String  itemfund = item.getItem_fund();//项目资金
        String  itemnum = item.getItem_num();//项目总人数
        String startDate = item.getItem_startdate();//开始日期
        String endDate  = item.getItem_enddate();//结束日期
        String month = "";
        try {
            long monthday = DateDiffMonth.getMonthDiff(startDate,endDate);
            month = monthday + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        item.setItem_cycle(month);
        BigDecimal itemcycle = new BigDecimal(month);
        BigDecimal Total = new BigDecimal(itemfund);
        BigDecimal empslaryTotal = new BigDecimal("0.0000");
        List<EmployeeInfo> listEmp = proInfoMapper.selectEmpById(item_id);
        //        if(listEmp.size()>0 && Integer.parseInt(itemnum)==listEmp.size()){
       *//* if(listEmp.size()>0){
            for(int i = 0; i<listEmp.size(); i++){
                EmployeeInfo employeeInfo = listEmp.get(i);
                //BigDecimal empsalary = employeeInfo.getEmpSalary();
                BigDecimal empsalary = new BigDecimal(0);
                empslaryTotal = empslaryTotal.add(empsalary.multiply(new BigDecimal("0.0001")));
            }
        }*//*
        if(("1").equals(item.getItem_type())){
            //成本
            BigDecimal itemCost = empslaryTotal.multiply(itemcycle);
            item.setItemCost(itemCost);
            //营收
            item.setItemProfit(new BigDecimal("0.0000"));
        }else{
            //成本
            BigDecimal itemCost = empslaryTotal.multiply(itemcycle);
            item.setItemCost(itemCost);
            //营收
            BigDecimal itemProfit = Total.subtract(itemCost);
            item.setItemProfit(itemProfit);
            }
        return item;
    }*/


    /**
     * 根据项目ID获取合同信息
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<Contract> selectContractById(String item_id) {
        return proInfoMapper.selectContractById(item_id);
    }
    /**
     * 上传合同信息附件
     *
     *
     */
    @Override
    @Transactional
    public void uploadContractDoc(MultipartFile[] files, String empId, String itemId, String docType)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException {

        String filePath = RuoYiConfig.getUploadPath()+"/itemContract/"+itemId;
        StringBuffer docIds =  new StringBuffer("");
        for (MultipartFile file : files){
            String path = FileUploadUtils.upload(filePath, file);
            EmpDocument doc = new EmpDocument();
            doc.setDocId(IdUtils.simpleUUID());
            doc.setEmpId(empId);
            doc.setDocName(file.getOriginalFilename());
            doc.setDocPath(path);
            doc.setDocSize(file.getSize());
            doc.setUpdateTime(DateUtils.getNowDate());
            doc.setDocType(docType);
            doc.setItemId(itemId);
            empDocumentMapper.insert(doc);
        }
    }
    /**
     * 获取合同附件信息
     */
    @Override
    public List<EmpDocument> selectDocByContract(String itemId) {
        return proInfoMapper.selectDocByContract(itemId);
    }
    /**
     * 下载合同附件信息
     */
    @Override
    public File downloadContractDoc(String contract_id) throws Exception{
        String filePath = RuoYiConfig.getUploadPath()+"/itemContract/"+contract_id;
        String zipPath = RuoYiConfig.getDownloadPath()+"/itemContract/"+contract_id;
        Contract contract = this.selectByPrimaryKey(contract_id);
        return FileUtils.pathToZip(filePath,zipPath,contract.getContract_num());
    }
    /**
     * 获取合同信息
     */
    public Contract selectByPrimaryKey(String contract_id) {
        return proInfoMapper.selectByPrimaryKey(contract_id);
    }

    /**
     * 获取客户信息数据
     *
     * @return 数据集合信息
     */
    @Override
    public List<Customer> selectCustomer(String item_type)
    {
        return proInfoMapper.selectCustomer(item_type);
    }
    /**
     * 根据项目ID查询项目详细信息计算人员成本
     * @return
     */
    @Override
    public List<Percost> selectPerCostById(String item_id) {
        List<Percost> listCost = new ArrayList<Percost>();
        Percost percost = new Percost();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        DecimalFormat df1 = new DecimalFormat("0.0000%");
        Monthname monthname = new Monthname();
        EmpSalaryinfo empSalaryinfo = new EmpSalaryinfo();
        EmpSalaryinfo empSalaryinfo_first = new EmpSalaryinfo();
        EmpSalaryinfo empSalaryinfo_end = new EmpSalaryinfo();
        EmpSalaryinfo empSalaryinfo_null = new EmpSalaryinfo();
        Itemper itempercome = new Itemper();
        //外包人员收入
        BigDecimal totalEmpIncome = new BigDecimal("0");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        MathContext mc = new MathContext(6, RoundingMode.HALF_DOWN);
        PerCostAndIncomeUtil pcai = new PerCostAndIncomeUtil();

        BigDecimal no = new BigDecimal("0");
        empSalaryinfo_null.setEmpInsuranceFund(df.format(no.doubleValue()));
        empSalaryinfo_null.setEmpSalary(df.format(no.doubleValue()));
        empSalaryinfo_null.setEmpMonthSalary(df.format(no.doubleValue()));
        EmpSalaryinfo empSalaryinfo_change = new EmpSalaryinfo();
        EmployeeInfo employeeInfo = new EmployeeInfo();
        Item item = proInfoMapper.selectProById(item_id);
        BigDecimal TotalProject = new BigDecimal("0");
        //获取人员
        List<EmployeeInfo> listEmp= new ArrayList<EmployeeInfo>();
        Itemperlevel itemperlevel = new Itemperlevel();
        if(item.getItem_type().equals("2")){
            List<Itemperlevel> listLevel = proInfoMapper.selectPerLevelById(item_id);
            for(int x=0;x<listLevel.size();x++){
                itemperlevel = listLevel.get(x);
                List<EmployeeInfo> listlevel = proInfoMapper.selectPerById(String.valueOf(itemperlevel.getItem_level_id()));
                for(int y=0;y<listlevel.size();y++){
                    EmployeeInfo employeeInfo1 = listlevel.get(y);
                    employeeInfo1.setItem_level_id(itemperlevel.getItem_level_id());
                    listEmp.add(employeeInfo1);
                    employeeInfo1 = new EmployeeInfo();
                }
            }
        }else {
            listEmp = proInfoMapper.selectEmpByItemId(item_id);
        }
        if(listEmp.size()>0){
            for(int i = 0; i<listEmp.size(); i++){
                employeeInfo = listEmp.get(i);
                monthname.setJanuary(empSalaryinfo_null);
                monthname.setFebruary(empSalaryinfo_null);
                monthname.setMarch(empSalaryinfo_null);
                monthname.setApril(empSalaryinfo_null);
                monthname.setMay(empSalaryinfo_null);
                monthname.setJune(empSalaryinfo_null);
                monthname.setJuly(empSalaryinfo_null);
                monthname.setAugust(empSalaryinfo_null);
                monthname.setSeptember(empSalaryinfo_null);
                monthname.setOctober(empSalaryinfo_null);
                monthname.setNovember(empSalaryinfo_null);
                monthname.setDecember(empSalaryinfo_null);
                String empid = employeeInfo.getEmpId();
                EmpCost empcost=empCostMapper.selectByEmp(empid);
                if(empcost==null){
                    continue;
                }
                EmpCost empcost_his = proInfoMapper.selectHisSalaryById(empid);
                if(empcost_his == null){
                    //工资无变更人员
                    EmpCost empCost = employeeInfo.getCost();
                    Itemper itemper = employeeInfo.getBinding();

                    Double salary = empCost.getEmpSalary();
                    BigDecimal emp_Salary = new BigDecimal(salary);
                    BigDecimal empSalary = new BigDecimal("0");
                    if(employeeInfo.getEmpState().equals("3")){
                        empSalary = emp_Salary.multiply(new BigDecimal("0.8"));
                    }else{
                        empSalary = emp_Salary;
                    }
                    Double insurance = empCost.getEmpInsurance();
                    BigDecimal empInsurance = new BigDecimal(insurance);
                    Double Fund = empCost.getEmpFund();
                    BigDecimal empFund = new BigDecimal(Fund);
                    Itemper emper =new Itemper();
                    if(item.getItem_type().equals("2")){
                        itemper.setItemid(item_id);
                        itemper.setItem_level_id(employeeInfo.getItem_level_id());
                        itemper.setEmpid(employeeInfo.getEmpId());
                        emper =proInfoMapper.selectbundling(itemper);
                    }else{
                        itemper.setItemid(item_id);
                        itemper.setEmpid(employeeInfo.getEmpId());
                        emper = proInfoMapper.selectUnbundling(itemper);
                    }
                    String startdate = emper.getStartdate();
                    String endDate = "";
                    if(emper.getEnddate() == null || emper.getEnddate().equals("")){
                        endDate = sdf.format(new Date());
                    }else {
                        endDate = emper.getEnddate();
                    }
                   /* String month = "";
                    try {
                        long monthday = DateDiffMonth.getMonthDiff(startdate,endDate);
                        month = monthday + "";
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(month.equals("0")){
                        continue;;
                    }*/
                    //获取日期范围内的月份
                    List<String> list =new ArrayList<>();
                    try {
                        list = GetDateUtils.getYearMonth(startdate,endDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    BigDecimal empTotalSalary = new BigDecimal("0");
                    //开始日期和结束日期相隔天数
                    int daysapart = DateDiffMonth.nDaysBetweenTwoDate(startdate,endDate);
                    if(daysapart<=1){
                        continue;
                    }
                    String yearmonth_first = startdate.substring(0,7);
                    String yearmonth_end = endDate.substring(0,7);
                    if(yearmonth_first.equals(yearmonth_end)){
                        //获取开始日期当月的最大天数
                        int maxday = 0;
                        try {
                            maxday = GetDateUtils.getDaysOfMonth(startdate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        BigDecimal maxdays = new BigDecimal(maxday);
                        //计算出天数百分比
                        BigDecimal workday = new BigDecimal(daysapart);
                        BigDecimal Percentage = workday.divide(maxdays,mc);
                        //当月天数对应的工资
                        BigDecimal empSalary_month = empSalary.multiply(Percentage);
                        //当月天数对应的五险一金
                        BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                        //当月五险和税前工资合计
                        BigDecimal empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                        empTotalSalary = empMonthSalary_Total;
                        empSalaryinfo_first.setEmpInsuranceFund(df.format(InsuranceFund_month.doubleValue()));
                        empSalaryinfo_first.setEmpSalary(df.format(empSalary_month.doubleValue()));
                        empSalaryinfo_first.setEmpMonthSalary(df.format(empMonthSalary_Total.doubleValue()));
                        //调用公共方法
                        monthname = pcai.SetMonthValue(yearmonth_first,empSalaryinfo_first,monthname);

                    }else if(list.size()==1){
                         //获取开始日期是当月的第几天
                        int numday = GetDateUtils.getMonthday(startdate);
                        BigDecimal numdays = new BigDecimal(numday);
                        //获取开始日期当月的最大天数
                        int maxday = 0;
                        try {
                            maxday = GetDateUtils.getDaysOfMonth(startdate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        BigDecimal maxdays = new BigDecimal(maxday);
                        //计算出天数百分比
                        BigDecimal workday = maxdays.subtract(numdays);
                        BigDecimal Percentage = workday.divide(maxdays,mc);
                        //当月天数对应的工资
                        BigDecimal empSalary_month = empSalary.multiply(Percentage);
                        //当月天数对应的五险一金
                        BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                        //当月五险和税前工资合计
                        BigDecimal empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                        empSalaryinfo_first.setEmpInsuranceFund(df.format(InsuranceFund_month.doubleValue()));
                        empSalaryinfo_first.setEmpSalary(df.format(empSalary_month.doubleValue()));
                        empSalaryinfo_first.setEmpMonthSalary(df.format(empMonthSalary_Total.doubleValue()));
                        //调用公共方法
                        monthname = pcai.SetMonthValue(yearmonth_first,empSalaryinfo_first,monthname);

                        //获取结束日期是当月的第几天
                        int numday_end = GetDateUtils.getMonthday(endDate);
                        BigDecimal numdays_end = new BigDecimal(numday_end);
                        //获取开始日期当月的最大天数
                        int maxday_end = 0;
                        try {
                            maxday_end = GetDateUtils.getDaysOfMonth(endDate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        BigDecimal maxdays_end = new BigDecimal(maxday_end);
                        //计算出天数百分比
                        BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                        //当月天数对应的工资
                        BigDecimal empSalary_month_end = empSalary.multiply(Percentage_end);
                        //当月天数对应的五险一金
                        BigDecimal InsuranceFund_month_end = (empFund.add(empInsurance)).multiply(Percentage_end);
                        //当月五险和税前工资合计
                        BigDecimal empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);
                        //总合计
                        empTotalSalary = empMonthSalary_Total.add(empMonthSalary_Total_end);
                        empSalaryinfo_end.setEmpInsuranceFund(df.format(InsuranceFund_month_end.doubleValue()));
                        empSalaryinfo_end.setEmpSalary(df.format(empSalary_month_end.doubleValue()));
                        empSalaryinfo_end.setEmpMonthSalary(df.format(empMonthSalary_Total_end.doubleValue()));
                        //调用公共方法
                        monthname = pcai.SetMonthValue(yearmonth_end,empSalaryinfo_end,monthname);
                    }else if(list.size()>1){
                        //获取开始日期是当月的第几天
                        int numday = GetDateUtils.getMonthday(startdate);
                        BigDecimal numdays = new BigDecimal(numday);
                        //获取开始日期当月的最大天数
                        int maxday = 0;
                        try {
                            maxday = GetDateUtils.getDaysOfMonth(startdate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        BigDecimal maxdays = new BigDecimal(maxday);
                        //计算出天数百分比
                        BigDecimal workday = maxdays.subtract(numdays);
                        BigDecimal Percentage = workday.divide(maxdays,mc);
                        //当月天数对应的工资
                        BigDecimal empSalary_month = empSalary.multiply(Percentage);
                        //当月天数对应的五险一金
                        BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                        //当月五险和税前工资合计
                        BigDecimal empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                        empSalaryinfo_first.setEmpInsuranceFund(df.format(InsuranceFund_month.doubleValue()));
                        empSalaryinfo_first.setEmpSalary(df.format(empSalary_month.doubleValue()));
                        empSalaryinfo_first.setEmpMonthSalary(df.format(empMonthSalary_Total.doubleValue()));
                        //调用公共方法
                        monthname = pcai.SetMonthValue(yearmonth_first,empSalaryinfo_first,monthname);

                        //获取结束日期是当月的第几天
                        int numday_end = GetDateUtils.getMonthday(endDate);
                        BigDecimal numdays_end = new BigDecimal(numday_end);
                        //获取开始日期当月的最大天数
                        int maxday_end = 0;
                        try {
                            maxday_end = GetDateUtils.getDaysOfMonth(endDate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        BigDecimal maxdays_end = new BigDecimal(maxday_end);
                        //计算出天数百分比
                        BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                        //当月天数对应的工资
                        BigDecimal empSalary_month_end = empSalary.multiply(Percentage_end);
                        //当月天数对应的五险一金
                        BigDecimal InsuranceFund_month_end = (empFund.add(empInsurance)).multiply(Percentage_end);
                        //当月五险和税前工资合计
                        BigDecimal empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);
                        empSalaryinfo_end.setEmpInsuranceFund(df.format(InsuranceFund_month_end.doubleValue()));
                        empSalaryinfo_end.setEmpSalary(df.format(empSalary_month_end.doubleValue()));
                        empSalaryinfo_end.setEmpMonthSalary(df.format(empMonthSalary_Total_end.doubleValue()));
                        //调用公共方法
                        monthname = pcai.SetMonthValue(yearmonth_end,empSalaryinfo_end,monthname);
                       BigDecimal itemcycle = new BigDecimal(list.size()-1);
//                    BigDecimal itemcycle = new BigDecimal(month);
                       BigDecimal fund = empFund.multiply(itemcycle);
                       BigDecimal Insurance = empInsurance.multiply(itemcycle);
                       BigDecimal empInsuranceFund = fund.add(Insurance);
                       BigDecimal Salary = empSalary.multiply(itemcycle);
                       empTotalSalary = Salary.add(empInsuranceFund).add(empMonthSalary_Total).add(empMonthSalary_Total_end);
                       //社保和公积金单月合计
                       BigDecimal empMonthInsuranceFund = empFund.add(empInsurance);
                       //单月总合计
                       BigDecimal empMonthTotalSalary = empSalary.add(empMonthInsuranceFund);

                       empSalaryinfo.setEmpInsuranceFund(df.format(empMonthInsuranceFund.doubleValue()));
                       empSalaryinfo.setEmpSalary(df.format(empSalary.doubleValue()));
                       empSalaryinfo.setEmpMonthSalary(df.format(empMonthTotalSalary.doubleValue()));
                    }
                    //公共方法
                    monthname = pcai.SetMonthListValue(list,empSalaryinfo,monthname);

                    //---------计算外包人员收入------------------
                     itempercome.setItemid(item_id);
                     itempercome.setEmpid(empid);
                     List<Itemper> list_percost = proInfoMapper.selectPercostbundling(itempercome);
                     Itemperlevel itemperlevel1 = new Itemperlevel();
                     BigDecimal total = new BigDecimal("0");
                     BigDecimal empIncome_month = new BigDecimal("0");
                     BigDecimal cycle = new BigDecimal("0");
                     BigDecimal empIncome_month_end = new BigDecimal("0");
                     BigDecimal empProfitrate = new BigDecimal("0");
                     String date = "";
                  if(item.getItem_type().equals("2")) {
                      int number = 0;
                     for (int c = 0; c < list_percost.size(); c++) {
                         Itemper it = list_percost.get(c);
                         itemperlevel1 = it.getLevel();
                         String start = it.getStartdate();//开始日期
                         String end = it.getEnddate();//结束日期
                         String price = itemperlevel1.getLevel_price();
                         BigDecimal price_level = new BigDecimal(price);
                         if (end==null || end.equals("")) {
                             end = sdf.format(new Date());
                         }
                         //开始日期和结束日期相隔天数
                         int dayapart = DateDiffMonth.nDaysBetweenTwoDate(start,end);
                         if(dayapart<=1){
                             number++;
                             continue;
                         }
                         List<String> list_se = new ArrayList<>();
                         try {
                             list_se = GetDateUtils.getYearMonth(start, end);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                         String first = start.substring(0,7);//绑定日期
                         String stp = end.substring(0,7);//解绑日期或当前日期
                         if(first.equals(stp)){
                             //获取开始日期是当月的第几天
                             int numday = GetDateUtils.getMonthday(start);
                             BigDecimal numdays = new BigDecimal(numday);
                             //获取开始日期当月的最大天数
                             int maxday = 0;
                             try {
                                 maxday = GetDateUtils.getDaysOfMonth(start);
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                             BigDecimal maxdays = new BigDecimal(maxday);
                             //计算出天数百分比
                             BigDecimal workday = new BigDecimal(dayapart);
                             BigDecimal Percentage = workday.divide(maxdays, mc);
                             //当月天数对应的收入
                             empIncome_month = price_level.multiply(Percentage);
                             total = total.add(empIncome_month);
                             empIncome_month = new BigDecimal("0");
                             date=end;
                         }else if(list_se.size()==1){
                             int numday = GetDateUtils.getMonthday(start);
                             BigDecimal numdays = new BigDecimal(numday);
                             //获取开始日期当月的最大天数
                             int maxday = 0;
                             try {
                                 maxday = GetDateUtils.getDaysOfMonth(start);
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                             BigDecimal maxdays = new BigDecimal(maxday);
                             //计算出天数百分比
                             BigDecimal workday = maxdays.subtract(numdays);
                             BigDecimal Percentage = workday.divide(maxdays, mc);
                             //当月天数对应的收入
                             empIncome_month = price_level.multiply(Percentage);

                             int numday_end = GetDateUtils.getMonthday(end);
                             BigDecimal numdays_end = new BigDecimal(numday_end);
                             //获取开始日期当月的最大天数
                             int maxday_end = 0;
                             try {
                                 maxday_end = GetDateUtils.getDaysOfMonth(end);
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                             BigDecimal maxdays_end = new BigDecimal(maxday_end);
                             //计算出天数百分比
                             BigDecimal Percentage_end = numdays_end.divide(maxdays_end, mc);
                             //当月天数对应的收入
                             empIncome_month_end = price_level.multiply(Percentage_end);
                             BigDecimal total1 = empIncome_month.add(empIncome_month_end);
                             total = total.add(total1);
                             empIncome_month = new BigDecimal("0");
                             empIncome_month_end = new BigDecimal("0");
                             date=end;
                         }else if(list_se.size()>1){
                             //获取开始日期是当月的第几天
                             if (c == number) {
                                 int numday = GetDateUtils.getMonthday(start);
                                 BigDecimal numdays = new BigDecimal(numday);
                                 //获取开始日期当月的最大天数
                                 int maxday = 0;
                                 try {
                                     maxday = GetDateUtils.getDaysOfMonth(start);
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }
                                 BigDecimal maxdays = new BigDecimal(maxday);
                                 //计算出天数百分比
                                 BigDecimal workday = maxdays.subtract(numdays);
                                 BigDecimal Percentage = workday.divide(maxdays, mc);
                                 //当月天数对应的收入
                                 empIncome_month = price_level.multiply(Percentage);
                             }
                             //获取日期范围内的月份(变更后)
                             List<String> list_date = new ArrayList<>();
                             if (c == number) {
                                 try {
                                     list_date = GetDateUtils.getYearMonth(start, end);
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }
                                 cycle = new BigDecimal(list_date.size() );
                             } else {
                                 try {
                                     list_date = GetDateUtils.getYearMonth(date, end);
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }
                                 cycle = new BigDecimal(list_date.size()- 1);
                             }
                             BigDecimal Incomecycle = price_level.multiply(cycle);
                             if (c == number) {
                                 empIncome_month_end = new BigDecimal("0");
                             } else {
                                 int numday_end = GetDateUtils.getMonthday(end);
                                 BigDecimal numdays_end = new BigDecimal(numday_end);
                                 //获取开始日期当月的最大天数
                                 int maxday_end = 0;
                                 try {
                                     maxday_end = GetDateUtils.getDaysOfMonth(end);
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }
                                 BigDecimal maxdays_end = new BigDecimal(maxday_end);
                                 //计算出天数百分比
                                 BigDecimal Percentage_end = numdays_end.divide(maxdays_end, mc);
                                 //当月天数对应的收入
                                 empIncome_month_end = price_level.multiply(Percentage_end);
                             }
                             BigDecimal total1 = empIncome_month.add(Incomecycle).add(empIncome_month_end);
                             total = total.add(total1);
                             date=end;
                             empIncome_month = new BigDecimal("0");
                             Incomecycle = new BigDecimal("0");
                             empIncome_month_end = new BigDecimal("0");
                         }
                     }
                      System.out.println("===========total============="+total);
                      totalEmpIncome = totalEmpIncome.add(total);
                      empProfitrate = empTotalSalary.divide(total,mc);
                  }
                    percost.setMonthname(monthname);
                    percost.setEmpName(employeeInfo.getEmpName());
                    percost.setEmpStartDate(startdate);
                    percost.setEmpEndDate(endDate);
                    percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                    percost.setEmpTotalSalary(df.format(empTotalSalary.doubleValue()));
                    TotalProject = TotalProject.add(empTotalSalary);
//                   percost.setUpdatetime(itemper.getUpdatetime());
                    listCost.add(percost);
                    percost = new Percost();
                    monthname = new Monthname();
                    empSalaryinfo = new EmpSalaryinfo();
                    empSalaryinfo_first = new EmpSalaryinfo();
                    empSalaryinfo_end = new EmpSalaryinfo();
                }else {
                    //工资有变更人员,变更前工资
                    Double salary1 = empcost_his.getEmpSalary();
                    BigDecimal hsalary = new BigDecimal(salary1);
                    //变更前工资
                    BigDecimal hissalary = new BigDecimal("0");
                    if(employeeInfo.getEmpState().equals("3")){
                        hissalary = hsalary.multiply(new BigDecimal("0.8"));
                    }else{
                        hissalary = hsalary;
                    }
                    //变更前五险一金
                    Double insurance1 = empcost_his.getEmpInsurance();
                    BigDecimal hisinsurance = new BigDecimal(insurance1);
                    Double Fund1 = empcost_his.getEmpFund();
                    BigDecimal hisfund = new BigDecimal(Fund1);

                    Date hisupdateTime = empcost_his.getUpdateTime();
                    String end = sdf.format(hisupdateTime);
                    String[] enddatehis = end.split(" ");
                    //工资变更日期
                    String endDatehis = enddatehis[0];
                    String enddatechange = endDatehis.replace("-", "");

                    EmpCost empCost = employeeInfo.getCost();
                    Itemper itemper = employeeInfo.getBinding();
                    Double salary = empCost.getEmpSalary();
                    //变更后工资
                    BigDecimal emp_Salary = new BigDecimal(salary);
                    BigDecimal empSalary = new BigDecimal("0");
                    if(employeeInfo.getEmpState().equals("3")){
                        empSalary = emp_Salary.multiply(new BigDecimal("0.8"));
                    }else{
                        empSalary = emp_Salary;
                    }
                    //变更后五险一金
                    Double insurance = empCost.getEmpInsurance();
                    BigDecimal empInsurance = new BigDecimal(insurance);
                    Double Fund = empCost.getEmpFund();
                    BigDecimal empFund = new BigDecimal(Fund);
                    //是否解绑，解绑日期
                    Itemper itemper1 = new Itemper();
                    if(item.getItem_type().equals("2")){
                        itemper.setItemid(item_id);
                        itemper.setItem_level_id(employeeInfo.getItem_level_id());
                        itemper.setEmpid(employeeInfo.getEmpId());
                        //人员外包项目
                        itemper1 =proInfoMapper.selectbundling(itemper);
                    }else{
                        itemper.setItemid(item_id);
                        itemper.setEmpid(employeeInfo.getEmpId());
                        //项目外包
                        itemper1 = proInfoMapper.selectUnbundling(itemper);
                    }
                    String startdate = itemper1.getStartdate();
//                    if(Integer.parseInt(enddatechange)<=Integer.parseInt(updatechange)){
                    String endDate_bs = "";//变更日
                    String endDate_be = "";//到当前日
                    if (itemper1.getEnddate() == null || itemper1.getEnddate().equals("")) {//无解绑
                        endDate_bs = sdf.format(new Date());
                        String endDatebs = endDate_bs.replace("-", "");
                        String startdate1 = startdate.replace("-", "");
                        if (Integer.parseInt(endDatebs) > Integer.parseInt(enddatechange)) {//当前日期大于变更日期
                            endDate_bs = endDatehis;
                            endDate_be = sdf.format(new Date());
                            if(Integer.parseInt(startdate1) > Integer.parseInt(enddatechange) ){//绑定日期大于变更日期
                                endDate_bs = sdf.format(new Date());
                                endDate_be = "";
                            }
                        }
                    } else {//解绑
                        String endDatebs = itemper1.getEnddate().replace("-", "");
                        endDate_bs = itemper1.getEnddate();
                        String startdate1 = startdate.replace("-", "");
                        if (Integer.parseInt(endDatebs) > Integer.parseInt(enddatechange)) {//解绑日期大于变更日期
                            endDate_bs = endDatehis;
                            endDate_be = itemper1.getEnddate();
                            if(Integer.parseInt(startdate1) > Integer.parseInt(enddatechange) ){//绑定日期大于变更日期
                                endDate_bs = itemper1.getEnddate();
                                endDate_be = "";
                            }
                        }
                    }
                   /* String month_bs = "";//开始到变更
                    String month_be = "";//变更到当前或者到解绑
                    try {
                        long monthday_bs = DateDiffMonth.getMonthDiff(startdate, endDate_bs);
                        month_bs = monthday_bs + "";
                        if (!endDate_be.equals("")) {
                            long monthday_be = DateDiffMonth.getMonthDiff(endDate_bs, endDate_be);
                            String month = monthday_be + "";
                            int month2 = Integer.parseInt(month) -1;
                            month_be = String.valueOf(month2);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(month_bs.equals("0")){
                        continue;;
                    }*/

                    //获取日期范围内的月份(变更前)
                    List<String> list_c =new ArrayList<>();
                    try {
                        list_c = GetDateUtils.getYearMonth(startdate,endDate_bs);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //获取日期范围内的月份(变更后)
                    List<String> list =new ArrayList<>();
                    if(!endDate_be.equals("")) {
                        try {
                            list = GetDateUtils.getYearMonth(endDate_bs, endDate_be);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    BigDecimal itemcycle_bs = new BigDecimal("0");
                    BigDecimal itemcycle_be = new BigDecimal("0");
                    if(list_c.size()>0){
                        itemcycle_bs = new BigDecimal(list_c.size()-1);
                    }
                    //变更前的工资
                    BigDecimal empMonthInsuranceFund_c = hisinsurance.add(hisfund);
                    BigDecimal empMonthTotalSalary_c  = hissalary.add(empMonthInsuranceFund_c );
                    empSalaryinfo_change.setEmpInsuranceFund(df.format(empMonthInsuranceFund_c .doubleValue()));
                    empSalaryinfo_change.setEmpSalary(df.format(hissalary.doubleValue()));
                    empSalaryinfo_change.setEmpMonthSalary(df.format(empMonthTotalSalary_c.doubleValue()));
                    //变更后工资
                    BigDecimal empMonthInsuranceFund = empInsurance.add(empFund);
                    BigDecimal empMonthTotalSalary = empSalary.add(empMonthInsuranceFund);
                    empSalaryinfo.setEmpInsuranceFund(df.format(empMonthInsuranceFund.doubleValue()));
                    empSalaryinfo.setEmpSalary(df.format(empSalary.doubleValue()));
                    empSalaryinfo.setEmpMonthSalary(df.format(empMonthTotalSalary.doubleValue()));

                    BigDecimal empMonthSalary_Total_end = new BigDecimal("0");
                    BigDecimal empMonthSalary_Total = new BigDecimal("0");
                    BigDecimal empTotalSalary = new BigDecimal("0");
                    //绑定日期
                    int ksr = Integer.parseInt(startdate.replace("-",""));
                    //变更日期
                    int bgr = Integer.parseInt(endDate_bs.replace("-",""));

                    if(!endDate_be.equals("")){
                        //变更月当月还是变更前工资
//                        String year_end = GetDateUtils.getSysYear();
                        String yearmonth_change = endDate_bs.substring(0,7);
                        //调用公共方法
                        monthname = pcai.SetMonthValue(yearmonth_change,empSalaryinfo_change,monthname);
                        //获取开始日期是当月的第几天
                        int numday = GetDateUtils.getMonthday(startdate);
                        BigDecimal numdays = new BigDecimal(numday);
                        //获取开始日期当月的最大天数
                        int maxday = 0;
                        try {
                            maxday = GetDateUtils.getDaysOfMonth(startdate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        BigDecimal maxdays = new BigDecimal(maxday);
                        //计算出天数百分比
                        BigDecimal workday = maxdays.subtract(numdays);
                        BigDecimal Percentage = workday.divide(maxdays,mc);
                        //当月天数对应的工资
                        BigDecimal empSalary_month = hissalary.multiply(Percentage);
                        //当月天数对应的五险一金
                        BigDecimal InsuranceFund_month = (hisfund.add(hisinsurance)).multiply(Percentage);
                        //当月五险和税前工资合计
                        empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                        empSalaryinfo_first.setEmpInsuranceFund(df.format(InsuranceFund_month.doubleValue()));
                        empSalaryinfo_first.setEmpSalary(df.format(empSalary_month.doubleValue()));
                        empSalaryinfo_first.setEmpMonthSalary(df.format(empMonthSalary_Total.doubleValue()));
//                        String year_first = GetDateUtils.getSysYear();
                        String yearmonth_first = startdate.substring(0,7);
                        //调用公共方法
                        monthname = pcai.SetMonthValue(yearmonth_first,empSalaryinfo_first,monthname);
                        //获取结束日期是当月的第几天
                        int numday_end = GetDateUtils.getMonthday(endDate_be);
                        BigDecimal numdays_end = new BigDecimal(numday_end);
                        //获取开始日期当月的最大天数
                        int maxday_end = 0;
                        try {
                            maxday_end = GetDateUtils.getDaysOfMonth(endDate_be);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        BigDecimal maxdays_end = new BigDecimal(maxday_end);
                        //计算出天数百分比
                        BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                        BigDecimal empSalary_month_end = new BigDecimal("0");
                        BigDecimal InsuranceFund_month_end = new BigDecimal("0");
                        String start_bs = endDate_bs.substring(0,7);
                        String end_be = endDate_be.substring(0,7);
                        if(start_bs.equals(end_be)){
                            //当月天数对应的工资
                            empSalary_month_end = hissalary.multiply(Percentage_end);
                            //当月天数对应的五险一金
                            InsuranceFund_month_end = (hisfund.add(hisinsurance)).multiply(Percentage_end);
                        }else{
                            //当月天数对应的工资
                            empSalary_month_end = empSalary.multiply(Percentage_end);
                            //当月天数对应的五险一金
                            InsuranceFund_month_end = (empFund.add(empInsurance)).multiply(Percentage_end);
                        }

                        //当月五险和税前工资合计
                        empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);
                        empSalaryinfo_end.setEmpInsuranceFund(df.format(InsuranceFund_month_end.doubleValue()));
                        empSalaryinfo_end.setEmpSalary(df.format(empSalary_month_end.doubleValue()));
                        empSalaryinfo_end.setEmpMonthSalary(df.format(empMonthSalary_Total_end.doubleValue()));

                        String yearmonth_end = endDate_be.substring(0,7);
                        //调用公共方法
                        monthname = pcai.SetMonthValue(yearmonth_end,empSalaryinfo_end,monthname);
                        //调用公共方法
                        monthname = pcai.SetMonthListValue(list_c,empSalaryinfo_change,monthname);
                        //调用公共方法
                        monthname = pcai.SetMonthListValue(list,empSalaryinfo,monthname);

                        percost.setMonthname(monthname);
                        percost.setEmpName(employeeInfo.getEmpName());
                        percost.setEmpStartDate(startdate);
                        percost.setEmpEndDate(endDate_be);

                        itemcycle_be = new BigDecimal(list.size()-1);
                        BigDecimal total_bs = itemcycle_bs.multiply(empMonthTotalSalary_c);
                        BigDecimal total_be = itemcycle_be.multiply(empMonthTotalSalary).add(empMonthTotalSalary_c);
                        BigDecimal total = total_bs.add(total_be).add(empMonthSalary_Total).add(empMonthSalary_Total_end);
                        percost.setEmpTotalSalary(df.format(total.doubleValue()));
                        //---------计算外包人员收入------------------
                        itempercome.setItemid(item_id);
                        itempercome.setEmpid(empid);
                        List<Itemper> list_percost = proInfoMapper.selectPercostbundling(itempercome);
                        Itemperlevel itemperlevel1 = new Itemperlevel();
                        BigDecimal totalIncome = new BigDecimal("0");
                        BigDecimal empIncome_month = new BigDecimal("0");
                        BigDecimal cycle = new BigDecimal("0");
                        BigDecimal empIncome_month_end = new BigDecimal("0");
                        BigDecimal empProfitrate = new BigDecimal("0");
                        String date = "";
                        if(item.getItem_type().equals("2")) {
                            int number = 0;
                            for (int c = 0; c < list_percost.size(); c++) {
                                Itemper it = list_percost.get(c);
                                itemperlevel1 = it.getLevel();
                                String start = it.getStartdate();//开始日期
                                String end1 = it.getEnddate();//结束日期
                                String price = itemperlevel1.getLevel_price();
                                BigDecimal price_level = new BigDecimal(price);
                                if (end1==null || end1.equals("")) {
                                    end1 = sdf.format(new Date());
                                }
                                //开始日期和结束日期相隔天数
                                int dayapart = DateDiffMonth.nDaysBetweenTwoDate(start,end1);
                                if(dayapart<=1){
                                    number++;
                                    continue;
                                }
                                List<String> list_se = new ArrayList<>();
                                try {
                                    list_se = GetDateUtils.getYearMonth(start, end1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                String first = start.substring(0,7);//绑定日期
                                String stp = end1.substring(0,7);//解绑日期或当前日期
                                if(first.equals(stp)){
                                    //获取开始日期是当月的第几天
                                    int numday1 = GetDateUtils.getMonthday(start);
                                    BigDecimal numdays1 = new BigDecimal(numday1);
                                    //获取开始日期当月的最大天数
                                    int maxday1 = 0;
                                    try {
                                        maxday1 = GetDateUtils.getDaysOfMonth(start);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    BigDecimal maxdays1 = new BigDecimal(maxday1);
                                    //计算出天数百分比
                                    BigDecimal workday1 = new BigDecimal(dayapart);
                                    BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                    //当月天数对应的收入
                                    empIncome_month = price_level.multiply(Percentage1);
                                    totalIncome = totalIncome.add(empIncome_month);
                                    empIncome_month = new BigDecimal("0");
                                    date=end1;
                                }else if(list_se.size()==1){
                                    int numday1 = GetDateUtils.getMonthday(start);
                                    BigDecimal numdays1 = new BigDecimal(numday1);
                                    //获取开始日期当月的最大天数
                                    int maxday1 = 0;
                                    try {
                                        maxday1 = GetDateUtils.getDaysOfMonth(start);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    BigDecimal maxdays1 = new BigDecimal(maxday1);
                                    //计算出天数百分比
                                    BigDecimal workday1 = maxdays1.subtract(numdays1);
                                    BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                    //当月天数对应的收入
                                    empIncome_month = price_level.multiply(Percentage1);

                                    int numday_end1 = GetDateUtils.getMonthday(end1);
                                    BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                    //获取开始日期当月的最大天数
                                    int maxday_end1 = 0;
                                    try {
                                        maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                    //计算出天数百分比
                                    BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                    //当月天数对应的收入
                                    empIncome_month_end = price_level.multiply(Percentage_end1);
                                    BigDecimal total1 = empIncome_month.add(empIncome_month_end);
                                    totalIncome = totalIncome.add(total1);
                                    date=end1;
                                    empIncome_month = new BigDecimal("0");
                                    empIncome_month_end = new BigDecimal("0");
                                }else if(list_se.size()>1){
                                    //获取开始日期是当月的第几天
                                    if (c == number) {
                                        int numday1 = GetDateUtils.getMonthday(start);
                                        BigDecimal numdays1 = new BigDecimal(numday1);
                                        //获取开始日期当月的最大天数
                                        int maxday1 = 0;
                                        try {
                                            maxday1 = GetDateUtils.getDaysOfMonth(start);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        BigDecimal maxdays1 = new BigDecimal(maxday1);
                                        //计算出天数百分比
                                        BigDecimal workday1 = maxdays1.subtract(numdays1);
                                        BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                        //当月天数对应的收入
                                        empIncome_month = price_level.multiply(Percentage1);
                                    }
                                    //获取日期范围内的月份(变更后)
                                    List<String> list_date = new ArrayList<>();
                                    if (c == number) {
                                        try {
                                            list_date = GetDateUtils.getYearMonth(start, end1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        cycle = new BigDecimal(list_date.size() );
                                    } else {
                                        try {
                                            list_date = GetDateUtils.getYearMonth(date, end1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        cycle = new BigDecimal(list_date.size()- 1);
                                    }
                                    BigDecimal Incomecycle = price_level.multiply(cycle);
                                    if (c == number) {
                                        empIncome_month_end = new BigDecimal("0");
                                    } else {
                                        int numday_end1 = GetDateUtils.getMonthday(end1);
                                        BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                        //获取开始日期当月的最大天数
                                        int maxday_end1 = 0;
                                        try {
                                            maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                        //计算出天数百分比
                                        BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                        //当月天数对应的收入
                                        empIncome_month_end = price_level.multiply(Percentage_end1);
                                    }
                                    BigDecimal total1 = empIncome_month.add(Incomecycle).add(empIncome_month_end);
                                    totalIncome = totalIncome.add(total1);
                                    date=end1;
                                    empIncome_month = new BigDecimal("0");
                                    Incomecycle = new BigDecimal("0");
                                    empIncome_month_end = new BigDecimal("0");
                                }
                            }
                            System.out.println("===========totalIncome============="+totalIncome);
                            empProfitrate = total.divide(totalIncome,mc);
                            percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                            totalEmpIncome = totalEmpIncome.add(totalIncome);
                        }
                            TotalProject = TotalProject.add(total);
                            percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                            listCost.add(percost);
                            percost = new Percost();
                            monthname = new Monthname();
                            empSalaryinfo = new EmpSalaryinfo();
                            empSalaryinfo_change = new EmpSalaryinfo();
                            empSalaryinfo_first = new EmpSalaryinfo();
                            empSalaryinfo_end = new EmpSalaryinfo();
                    }else {
                        //开始日期和结束日期相隔天数
                        int daysapart = DateDiffMonth.nDaysBetweenTwoDate(startdate,endDate_bs);
                        if(daysapart<=1){
                            continue;
                        }
                        if (Integer.parseInt(enddatechange) < ksr) {
                            String yearmonth_start = startdate.substring(0,7);
                            String yearmonth_End = endDate_bs.substring(0,7);
                            if(yearmonth_start.equals(yearmonth_End)){
                                //按当前工资
                                String year_end = GetDateUtils.getSysYear();
                                //获取开始日期是当月的第几天
                                int numday = GetDateUtils.getMonthday(startdate);
                                BigDecimal numdays = new BigDecimal(numday);
                                //获取开始日期当月的最大天数
                                int maxday = 0;
                                try {
                                    maxday = GetDateUtils.getDaysOfMonth(startdate);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BigDecimal maxdays = new BigDecimal(maxday);
                                //计算出天数百分比
                                BigDecimal workday = new BigDecimal(daysapart);
                                BigDecimal Percentage = workday.divide(maxdays,mc);
                                //当月天数对应的工资
                                BigDecimal empSalary_month = empSalary.multiply(Percentage);
                                //当月天数对应的五险一金
                                BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                                //当月五险和税前工资合计
                                empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                                empSalaryinfo_first.setEmpInsuranceFund(df.format(InsuranceFund_month.doubleValue()));
                                empSalaryinfo_first.setEmpSalary(df.format(empSalary_month.doubleValue()));
                                empSalaryinfo_first.setEmpMonthSalary(df.format(empMonthSalary_Total.doubleValue()));
                                String yearmonth_first = startdate.substring(0,7);
                                //调用公共方法
                                monthname = pcai.SetMonthValue(yearmonth_first,empSalaryinfo_first,monthname);
                                //调用公共方法
                                monthname = pcai.SetMonthListValue(list_c,empSalaryinfo,monthname);
                                //---------计算外包人员收入------------------
                                itempercome.setItemid(item_id);
                                itempercome.setEmpid(empid);
                                List<Itemper> list_percost = proInfoMapper.selectPercostbundling(itempercome);
                                Itemperlevel itemperlevel1 = new Itemperlevel();
                                BigDecimal totalIncome = new BigDecimal("0");
                                BigDecimal empIncome_month = new BigDecimal("0");
                                BigDecimal cycle = new BigDecimal("0");
                                BigDecimal empIncome_month_end = new BigDecimal("0");
                                BigDecimal empProfitrate = new BigDecimal("0");
                                String date = "";
                                if(item.getItem_type().equals("2")) {
                                    int number = 0;
                                    for (int c = 0; c < list_percost.size(); c++) {
                                        Itemper it = list_percost.get(c);
                                        itemperlevel1 = it.getLevel();
                                        String start = it.getStartdate();//开始日期
                                        String end1 = it.getEnddate();//结束日期
                                        String price = itemperlevel1.getLevel_price();
                                        BigDecimal price_level = new BigDecimal(price);
                                        if (end1==null || end1.equals("")) {
                                            end1 = sdf.format(new Date());
                                        }
                                        //开始日期和结束日期相隔天数
                                        int dayapart = DateDiffMonth.nDaysBetweenTwoDate(start,end1);
                                        if(dayapart<=1){
                                            number++;
                                            continue;
                                        }
                                        List<String> list_se = new ArrayList<>();
                                        try {
                                            list_se = GetDateUtils.getYearMonth(start, end1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        String first = start.substring(0,7);//绑定日期
                                        String stp = end1.substring(0,7);//解绑日期或当前日期
                                        if(first.equals(stp)){
                                            //获取开始日期是当月的第几天
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = new BigDecimal(dayapart);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);
                                            totalIncome = totalIncome.add(empIncome_month);
                                            empIncome_month = new BigDecimal("0");
                                            date=end1;
                                        }else if(list_se.size()==1){
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = maxdays1.subtract(numdays1);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);

                                            int numday_end1 = GetDateUtils.getMonthday(end1);
                                            BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                            //获取开始日期当月的最大天数
                                            int maxday_end1 = 0;
                                            try {
                                                maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                            //计算出天数百分比
                                            BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                            //当月天数对应的收入
                                            empIncome_month_end = price_level.multiply(Percentage_end1);
                                            BigDecimal total1 = empIncome_month.add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }else if(list_se.size()>1){
                                            //获取开始日期是当月的第几天
                                            if (c == number) {
                                                int numday1 = GetDateUtils.getMonthday(start);
                                                BigDecimal numdays1 = new BigDecimal(numday1);
                                                //获取开始日期当月的最大天数
                                                int maxday1 = 0;
                                                try {
                                                    maxday1 = GetDateUtils.getDaysOfMonth(start);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays1 = new BigDecimal(maxday1);
                                                //计算出天数百分比
                                                BigDecimal workday1 = maxdays1.subtract(numdays1);
                                                BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                                //当月天数对应的收入
                                                empIncome_month = price_level.multiply(Percentage1);
                                            }
                                            //获取日期范围内的月份(变更后)
                                            List<String> list_date = new ArrayList<>();
                                            if (c == number) {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(start, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size() );
                                            } else {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(date, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size()- 1);
                                            }
                                            BigDecimal Incomecycle = price_level.multiply(cycle);
                                            if (c == number) {
                                                empIncome_month_end = new BigDecimal("0");
                                            } else {
                                                int numday_end1 = GetDateUtils.getMonthday(end1);
                                                BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                                //获取开始日期当月的最大天数
                                                int maxday_end1 = 0;
                                                try {
                                                    maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                                //计算出天数百分比
                                                BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                                //当月天数对应的收入
                                                empIncome_month_end = price_level.multiply(Percentage_end1);
                                            }
                                            BigDecimal total1 = empIncome_month.add(Incomecycle).add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            Incomecycle = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }
                                    }
                                    empProfitrate = empTotalSalary.divide(totalIncome,mc);
                                    percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                    totalEmpIncome = totalEmpIncome.add(totalIncome);
                                }

                                percost.setMonthname(monthname);
                                percost.setEmpName(employeeInfo.getEmpName());
                                percost.setEmpStartDate(startdate);
                                percost.setEmpEndDate(endDate_bs);
                                empTotalSalary = empMonthSalary_Total;
                                percost.setEmpTotalSalary(df.format(empTotalSalary.doubleValue()));
//                   percost.setUpdatetime(itemper.getUpdatetime());
                                TotalProject = TotalProject.add(empTotalSalary);
                                percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                listCost.add(percost);
                                percost = new Percost();
                                monthname = new Monthname();
                                empSalaryinfo = new EmpSalaryinfo();
                                empSalaryinfo_first = new EmpSalaryinfo();
                                empSalaryinfo_end = new EmpSalaryinfo();
                            }else if(list_c.size()==1){
                                //按当前工资
                                //获取开始日期是当月的第几天
                                int numday = GetDateUtils.getMonthday(startdate);
                                BigDecimal numdays = new BigDecimal(numday);
                                //获取开始日期当月的最大天数
                                int maxday = 0;
                                try {
                                    maxday = GetDateUtils.getDaysOfMonth(startdate);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BigDecimal maxdays = new BigDecimal(maxday);
                                //计算出天数百分比
                                BigDecimal workday = maxdays.subtract(numdays);
                                BigDecimal Percentage = workday.divide(maxdays,mc);
                                //当月天数对应的工资
                                BigDecimal empSalary_month = empSalary.multiply(Percentage);
                                //当月天数对应的五险一金
                                BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                                //当月五险和税前工资合计
                                empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                                empSalaryinfo_first.setEmpInsuranceFund(df.format(InsuranceFund_month.doubleValue()));
                                empSalaryinfo_first.setEmpSalary(df.format(empSalary_month.doubleValue()));
                                empSalaryinfo_first.setEmpMonthSalary(df.format(empMonthSalary_Total.doubleValue()));
                                String yearmonth_first = startdate.substring(0,7);
                                //调用公共方法
                                monthname = pcai.SetMonthValue(yearmonth_first,empSalaryinfo_first,monthname);

                                //获取结束日期是当月的第几天
                                int numday_end = GetDateUtils.getMonthday(endDate_bs);
                                BigDecimal numdays_end = new BigDecimal(numday_end);
                                //获取开始日期当月的最大天数
                                int maxday_end = 0;
                                try {
                                    maxday_end = GetDateUtils.getDaysOfMonth(endDate_bs);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BigDecimal maxdays_end = new BigDecimal(maxday_end);
                                //计算出天数百分比
                                BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                                BigDecimal empSalary_month_end = new BigDecimal("0");
                                BigDecimal InsuranceFund_month_end = new BigDecimal("0");
                                //当月天数对应的工资
                                empSalary_month_end = empSalary.multiply(Percentage_end);
                                //当月天数对应的五险一金
                                InsuranceFund_month_end = (empFund.add(empInsurance)).multiply(Percentage_end);
                                //当月五险和税前工资合计
                                empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);
                                empSalaryinfo_end.setEmpInsuranceFund(df.format(InsuranceFund_month_end.doubleValue()));
                                empSalaryinfo_end.setEmpSalary(df.format(empSalary_month_end.doubleValue()));
                                empSalaryinfo_end.setEmpMonthSalary(df.format(empMonthSalary_Total_end.doubleValue()));

                                String yearmonth_end = endDate_bs.substring(0,7);
                                //调用公共方法
                                monthname = pcai.SetMonthValue(yearmonth_end,empSalaryinfo_end,monthname);
                                //调用公共方法
                                monthname = pcai.SetMonthListValue(list_c,empSalaryinfo,monthname);

                                //---------计算外包人员收入------------------
                                itempercome.setItemid(item_id);
                                itempercome.setEmpid(empid);
                                List<Itemper> list_percost = proInfoMapper.selectPercostbundling(itempercome);
                                Itemperlevel itemperlevel1 = new Itemperlevel();
                                BigDecimal totalIncome = new BigDecimal("0");
                                BigDecimal empIncome_month = new BigDecimal("0");
                                BigDecimal cycle = new BigDecimal("0");
                                BigDecimal empIncome_month_end = new BigDecimal("0");
                                BigDecimal empProfitrate = new BigDecimal("0");
                                String date = "";
                                if(item.getItem_type().equals("2")) {
                                    int number = 0;
                                    for (int c = 0; c < list_percost.size(); c++) {
                                        Itemper it = list_percost.get(c);
                                        itemperlevel1 = it.getLevel();
                                        String start = it.getStartdate();//开始日期
                                        String end1 = it.getEnddate();//结束日期
                                        String price = itemperlevel1.getLevel_price();
                                        BigDecimal price_level = new BigDecimal(price);
                                        if (end1==null || end1.equals("")) {
                                            end1 = sdf.format(new Date());
                                        }
                                        //开始日期和结束日期相隔天数
                                        int dayapart = DateDiffMonth.nDaysBetweenTwoDate(start,end1);
                                        if(dayapart<=1){
                                            number++;
                                            continue;
                                        }
                                        List<String> list_se = new ArrayList<>();
                                        try {
                                            list_se = GetDateUtils.getYearMonth(start, end1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        String first = start.substring(0,7);//绑定日期
                                        String stp = end1.substring(0,7);//解绑日期或当前日期
                                        if(first.equals(stp)){
                                            //获取开始日期是当月的第几天
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = new BigDecimal(dayapart);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);
                                            totalIncome = totalIncome.add(empIncome_month);
                                            empIncome_month = new BigDecimal("0");
                                            date=end1;
                                        }else if(list_se.size()==1){
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = maxdays1.subtract(numdays1);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);

                                            int numday_end1 = GetDateUtils.getMonthday(end1);
                                            BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                            //获取开始日期当月的最大天数
                                            int maxday_end1 = 0;
                                            try {
                                                maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                            //计算出天数百分比
                                            BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                            //当月天数对应的收入
                                            empIncome_month_end = price_level.multiply(Percentage_end1);
                                            BigDecimal total1 = empIncome_month.add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }else if(list_se.size()>1){
                                            //获取开始日期是当月的第几天
                                            if (c == number) {
                                                int numday1 = GetDateUtils.getMonthday(start);
                                                BigDecimal numdays1 = new BigDecimal(numday1);
                                                //获取开始日期当月的最大天数
                                                int maxday1 = 0;
                                                try {
                                                    maxday1 = GetDateUtils.getDaysOfMonth(start);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays1 = new BigDecimal(maxday1);
                                                //计算出天数百分比
                                                BigDecimal workday1 = maxdays1.subtract(numdays1);
                                                BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                                //当月天数对应的收入
                                                empIncome_month = price_level.multiply(Percentage1);
                                            }
                                            //获取日期范围内的月份(变更后)
                                            List<String> list_date = new ArrayList<>();
                                            if (c == number) {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(start, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size() );
                                            } else {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(date, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size()- 1);
                                            }
                                            BigDecimal Incomecycle = price_level.multiply(cycle);
                                            if (c == number) {
                                                empIncome_month_end = new BigDecimal("0");
                                            } else {
                                                int numday_end1 = GetDateUtils.getMonthday(end1);
                                                BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                                //获取开始日期当月的最大天数
                                                int maxday_end1 = 0;
                                                try {
                                                    maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                                //计算出天数百分比
                                                BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                                //当月天数对应的收入
                                                empIncome_month_end = price_level.multiply(Percentage_end1);
                                            }
                                            BigDecimal total1 = empIncome_month.add(Incomecycle).add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            Incomecycle = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }
                                    }
                                    empProfitrate = empTotalSalary.divide(totalIncome,mc);
                                    percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                    totalEmpIncome = totalEmpIncome.add(totalIncome);
                                }

                                percost.setMonthname(monthname);
                                percost.setEmpName(employeeInfo.getEmpName());
                                percost.setEmpStartDate(startdate);
                                percost.setEmpEndDate(endDate_bs);
                                empTotalSalary = empMonthSalary_Total.add(empMonthSalary_Total_end);
                                percost.setEmpTotalSalary(df.format(empTotalSalary.doubleValue()));
//                   percost.setUpdatetime(itemper.getUpdatetime());
                                TotalProject = TotalProject.add(empTotalSalary);
                                percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                listCost.add(percost);
                                percost = new Percost();
                                monthname = new Monthname();
                                empSalaryinfo = new EmpSalaryinfo();
                                empSalaryinfo_first = new EmpSalaryinfo();
                                empSalaryinfo_end = new EmpSalaryinfo();
                            }else if(list_c.size()>1){
                                //按当前工资
                                //获取开始日期是当月的第几天
                                int numday = GetDateUtils.getMonthday(startdate);
                                BigDecimal numdays = new BigDecimal(numday);
                                //获取开始日期当月的最大天数
                                int maxday = 0;
                                try {
                                    maxday = GetDateUtils.getDaysOfMonth(startdate);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BigDecimal maxdays = new BigDecimal(maxday);
                                //计算出天数百分比
                                BigDecimal workday = maxdays.subtract(numdays);
                                BigDecimal Percentage = workday.divide(maxdays,mc);
                                //当月天数对应的工资
                                BigDecimal empSalary_month = empSalary.multiply(Percentage);
                                //当月天数对应的五险一金
                                BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                                //当月五险和税前工资合计
                                empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                                empSalaryinfo_first.setEmpInsuranceFund(df.format(InsuranceFund_month.doubleValue()));
                                empSalaryinfo_first.setEmpSalary(df.format(empSalary_month.doubleValue()));
                                empSalaryinfo_first.setEmpMonthSalary(df.format(empMonthSalary_Total.doubleValue()));
                                String yearmonth_first = startdate.substring(0,7);
                                //调用公共方法
                                monthname = pcai.SetMonthValue(yearmonth_first,empSalaryinfo_first,monthname);

                                //获取结束日期是当月的第几天
                                int numday_end = GetDateUtils.getMonthday(endDate_bs);
                                BigDecimal numdays_end = new BigDecimal(numday_end);
                                //获取开始日期当月的最大天数
                                int maxday_end = 0;
                                try {
                                    maxday_end = GetDateUtils.getDaysOfMonth(endDate_bs);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BigDecimal maxdays_end = new BigDecimal(maxday_end);
                                //计算出天数百分比
                                BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                                BigDecimal empSalary_month_end = new BigDecimal("0");
                                BigDecimal InsuranceFund_month_end = new BigDecimal("0");
                                //当月天数对应的工资
                                empSalary_month_end = empSalary.multiply(Percentage_end);
                                //当月天数对应的五险一金
                                InsuranceFund_month_end = (empFund.add(empInsurance)).multiply(Percentage_end);
                                //当月五险和税前工资合计
                                empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);
                                empSalaryinfo_end.setEmpInsuranceFund(df.format(InsuranceFund_month_end.doubleValue()));
                                empSalaryinfo_end.setEmpSalary(df.format(empSalary_month_end.doubleValue()));
                                empSalaryinfo_end.setEmpMonthSalary(df.format(empMonthSalary_Total_end.doubleValue()));

                                String yearmonth_end = endDate_bs.substring(0,7);
                                //调用公共方法
                                monthname = pcai.SetMonthValue(yearmonth_end,empSalaryinfo_end,monthname);
                                //调用公共方法
                                monthname = pcai.SetMonthListValue(list_c,empSalaryinfo,monthname);
                                BigDecimal itemcycle = new BigDecimal(list_c.size()-1);
//                    BigDecimal itemcycle = new BigDecimal(month);
                                BigDecimal fund = empFund.multiply(itemcycle);
                                BigDecimal Insurance = empInsurance.multiply(itemcycle);
                                BigDecimal empInsuranceFund = fund.add(Insurance);
                                BigDecimal Salary = empSalary.multiply(itemcycle);
                                empTotalSalary = Salary.add(empInsuranceFund).add(empMonthSalary_Total).add(empMonthSalary_Total_end);

                                //---------计算外包人员收入------------------
                                itempercome.setItemid(item_id);
                                itempercome.setEmpid(empid);
                                List<Itemper> list_percost = proInfoMapper.selectPercostbundling(itempercome);
                                Itemperlevel itemperlevel1 = new Itemperlevel();
                                BigDecimal totalIncome = new BigDecimal("0");
                                BigDecimal empIncome_month = new BigDecimal("0");
                                BigDecimal cycle = new BigDecimal("0");
                                BigDecimal empIncome_month_end = new BigDecimal("0");
                                BigDecimal empProfitrate = new BigDecimal("0");
                                String date = "";
                                if(item.getItem_type().equals("2")) {
                                    int number = 0;
                                    for (int c = 0; c < list_percost.size(); c++) {
                                        Itemper it = list_percost.get(c);
                                        itemperlevel1 = it.getLevel();
                                        String start = it.getStartdate();//开始日期
                                        String end1 = it.getEnddate();//结束日期
                                        String price = itemperlevel1.getLevel_price();
                                        BigDecimal price_level = new BigDecimal(price);
                                        if (end1==null || end1.equals("")) {
                                            end1 = sdf.format(new Date());
                                        }
                                        //开始日期和结束日期相隔天数
                                        int dayapart = DateDiffMonth.nDaysBetweenTwoDate(start,end1);
                                        if(dayapart<=1){
                                            number++;
                                            continue;
                                        }
                                        List<String> list_se = new ArrayList<>();
                                        try {
                                            list_se = GetDateUtils.getYearMonth(start, end1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        String first = start.substring(0,7);//绑定日期
                                        String stp = end1.substring(0,7);//解绑日期或当前日期
                                        if(first.equals(stp)){
                                            //获取开始日期是当月的第几天
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = new BigDecimal(dayapart);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);
                                            totalIncome = totalIncome.add(empIncome_month);
                                            empIncome_month = new BigDecimal("0");
                                            date=end1;
                                        }else if(list_se.size()==1){
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = maxdays1.subtract(numdays1);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);

                                            int numday_end1 = GetDateUtils.getMonthday(end1);
                                            BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                            //获取开始日期当月的最大天数
                                            int maxday_end1 = 0;
                                            try {
                                                maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                            //计算出天数百分比
                                            BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                            //当月天数对应的收入
                                            empIncome_month_end = price_level.multiply(Percentage_end1);
                                            BigDecimal total1 = empIncome_month.add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }else if(list_se.size()>1){
                                            //获取开始日期是当月的第几天
                                            if (c == number) {
                                                int numday1 = GetDateUtils.getMonthday(start);
                                                BigDecimal numdays1 = new BigDecimal(numday1);
                                                //获取开始日期当月的最大天数
                                                int maxday1 = 0;
                                                try {
                                                    maxday1 = GetDateUtils.getDaysOfMonth(start);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays1 = new BigDecimal(maxday1);
                                                //计算出天数百分比
                                                BigDecimal workday1 = maxdays1.subtract(numdays1);
                                                BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                                //当月天数对应的收入
                                                empIncome_month = price_level.multiply(Percentage1);
                                            }
                                            //获取日期范围内的月份(变更后)
                                            List<String> list_date = new ArrayList<>();
                                            if (c == number) {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(start, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size() );
                                            } else {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(date, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size()- 1);
                                            }
                                            BigDecimal Incomecycle = price_level.multiply(cycle);
                                            if (c == number) {
                                                empIncome_month_end = new BigDecimal("0");
                                            } else {
                                                int numday_end1 = GetDateUtils.getMonthday(end1);
                                                BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                                //获取开始日期当月的最大天数
                                                int maxday_end1 = 0;
                                                try {
                                                    maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                                //计算出天数百分比
                                                BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                                //当月天数对应的收入
                                                empIncome_month_end = price_level.multiply(Percentage_end1);
                                            }
                                            BigDecimal total1 = empIncome_month.add(Incomecycle).add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            Incomecycle = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }
                                    }
                                    empProfitrate = empTotalSalary.divide(totalIncome,mc);
                                    percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                    totalEmpIncome = totalEmpIncome.add(totalIncome);
                                }
                                percost.setMonthname(monthname);
                                percost.setEmpName(employeeInfo.getEmpName());
                                percost.setEmpStartDate(startdate);
                                percost.setEmpEndDate(endDate_bs);
                                //empTotalSalary = empMonthSalary_Total.add(empMonthSalary_Total_end);
                                percost.setEmpTotalSalary(df.format(empTotalSalary.doubleValue()));
//                   percost.setUpdatetime(itemper.getUpdatetime());
                                TotalProject = TotalProject.add(empTotalSalary);
                                percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                listCost.add(percost);
                                percost = new Percost();
                                monthname = new Monthname();
                                empSalaryinfo = new EmpSalaryinfo();
                                empSalaryinfo_first = new EmpSalaryinfo();
                                empSalaryinfo_end = new EmpSalaryinfo();
                            }

                        } else if (Integer.parseInt(enddatechange) > bgr) {
                            //按历史工资
                            String yearmonth_start = startdate.substring(0,7);
                            String yearmonth_End = endDate_bs.substring(0,7);
                            if(yearmonth_start.equals(yearmonth_End)){
                                //按当前工资
                                //获取开始日期是当月的第几天
                                int numday = GetDateUtils.getMonthday(startdate);
                                BigDecimal numdays = new BigDecimal(numday);
                                //获取开始日期当月的最大天数
                                int maxday = 0;
                                try {
                                    maxday = GetDateUtils.getDaysOfMonth(startdate);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BigDecimal maxdays = new BigDecimal(maxday);
                                //计算出天数百分比
                                BigDecimal workday = new BigDecimal(daysapart);
                                BigDecimal Percentage = workday.divide(maxdays,mc);
                                //当月天数对应的工资
                                BigDecimal empSalary_month = hissalary.multiply(Percentage);
                                //当月天数对应的五险一金
                                BigDecimal InsuranceFund_month = (hisfund.add(hisinsurance)).multiply(Percentage);
                                //当月五险和税前工资合计
                                empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                                empSalaryinfo_first.setEmpInsuranceFund(df.format(InsuranceFund_month.doubleValue()));
                                empSalaryinfo_first.setEmpSalary(df.format(empSalary_month.doubleValue()));
                                empSalaryinfo_first.setEmpMonthSalary(df.format(empMonthSalary_Total.doubleValue()));
                                String yearmonth_first = startdate.substring(0,7);
                                //调用公共方法
                                monthname = pcai.SetMonthValue(yearmonth_first,empSalaryinfo_first,monthname);
                                //调用公共方法
                                monthname = pcai.SetMonthListValue(list_c,empSalaryinfo,monthname);
                                //---------计算外包人员收入------------------
                                itempercome.setItemid(item_id);
                                itempercome.setEmpid(empid);
                                List<Itemper> list_percost = proInfoMapper.selectPercostbundling(itempercome);
                                Itemperlevel itemperlevel1 = new Itemperlevel();
                                BigDecimal totalIncome = new BigDecimal("0");
                                BigDecimal empIncome_month = new BigDecimal("0");
                                BigDecimal cycle = new BigDecimal("0");
                                BigDecimal empIncome_month_end = new BigDecimal("0");
                                BigDecimal empProfitrate = new BigDecimal("0");
                                String date = "";
                                if(item.getItem_type().equals("2")) {
                                    int number = 0;
                                    for (int c = 0; c < list_percost.size(); c++) {
                                        Itemper it = list_percost.get(c);
                                        itemperlevel1 = it.getLevel();
                                        String start = it.getStartdate();//开始日期
                                        String end1 = it.getEnddate();//结束日期
                                        String price = itemperlevel1.getLevel_price();
                                        BigDecimal price_level = new BigDecimal(price);
                                        if (end1==null || end1.equals("")) {
                                            end1 = sdf.format(new Date());
                                        }
                                        //开始日期和结束日期相隔天数
                                        int dayapart = DateDiffMonth.nDaysBetweenTwoDate(start,end1);
                                        if(dayapart<=1){
                                            number++;
                                            continue;
                                        }
                                        List<String> list_se = new ArrayList<>();
                                        try {
                                            list_se = GetDateUtils.getYearMonth(start, end1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        String first = start.substring(0,7);//绑定日期
                                        String stp = end1.substring(0,7);//解绑日期或当前日期
                                        if(first.equals(stp)){
                                            //获取开始日期是当月的第几天
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = new BigDecimal(dayapart);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);
                                            totalIncome = totalIncome.add(empIncome_month);
                                            empIncome_month = new BigDecimal("0");
                                            date=end1;
                                        }else if(list_se.size()==1){
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = maxdays1.subtract(numdays1);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);

                                            int numday_end1 = GetDateUtils.getMonthday(end1);
                                            BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                            //获取开始日期当月的最大天数
                                            int maxday_end1 = 0;
                                            try {
                                                maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                            //计算出天数百分比
                                            BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                            //当月天数对应的收入
                                            empIncome_month_end = price_level.multiply(Percentage_end1);
                                            BigDecimal total1 = empIncome_month.add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }else if(list_se.size()>1){
                                            //获取开始日期是当月的第几天
                                            if (c == number) {
                                                int numday1 = GetDateUtils.getMonthday(start);
                                                BigDecimal numdays1 = new BigDecimal(numday1);
                                                //获取开始日期当月的最大天数
                                                int maxday1 = 0;
                                                try {
                                                    maxday1 = GetDateUtils.getDaysOfMonth(start);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays1 = new BigDecimal(maxday1);
                                                //计算出天数百分比
                                                BigDecimal workday1 = maxdays1.subtract(numdays1);
                                                BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                                //当月天数对应的收入
                                                empIncome_month = price_level.multiply(Percentage1);
                                            }
                                            //获取日期范围内的月份(变更后)
                                            List<String> list_date = new ArrayList<>();
                                            if (c == number) {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(start, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size() );
                                            } else {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(date, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size()- 1);
                                            }
                                            BigDecimal Incomecycle = price_level.multiply(cycle);
                                            if (c == number) {
                                                empIncome_month_end = new BigDecimal("0");
                                            } else {
                                                int numday_end1 = GetDateUtils.getMonthday(end1);
                                                BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                                //获取开始日期当月的最大天数
                                                int maxday_end1 = 0;
                                                try {
                                                    maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                                //计算出天数百分比
                                                BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                                //当月天数对应的收入
                                                empIncome_month_end = price_level.multiply(Percentage_end1);
                                            }
                                            BigDecimal total1 = empIncome_month.add(Incomecycle).add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            Incomecycle = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }
                                    }
                                    empProfitrate = empTotalSalary.divide(totalIncome,mc);
                                    percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                    totalEmpIncome = totalEmpIncome.add(totalIncome);
                                }

                                percost.setMonthname(monthname);
                                percost.setEmpName(employeeInfo.getEmpName());
                                percost.setEmpStartDate(startdate);
                                percost.setEmpEndDate(endDate_bs);
                                empTotalSalary = empMonthSalary_Total;
                                percost.setEmpTotalSalary(df.format(empTotalSalary.doubleValue()));
//                   percost.setUpdatetime(itemper.getUpdatetime());
                                TotalProject = TotalProject.add(empTotalSalary);
                                percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                listCost.add(percost);
                                percost = new Percost();
                                monthname = new Monthname();
                                empSalaryinfo = new EmpSalaryinfo();
                                empSalaryinfo_first = new EmpSalaryinfo();
                                empSalaryinfo_end = new EmpSalaryinfo();
                            }else if(list_c.size()==1){
                                //按当前工资
                                //获取开始日期是当月的第几天
                                int numday = GetDateUtils.getMonthday(startdate);
                                BigDecimal numdays = new BigDecimal(numday);
                                //获取开始日期当月的最大天数
                                int maxday = 0;
                                try {
                                    maxday = GetDateUtils.getDaysOfMonth(startdate);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BigDecimal maxdays = new BigDecimal(maxday);
                                //计算出天数百分比
                                BigDecimal workday = maxdays.subtract(numdays);
                                BigDecimal Percentage = workday.divide(maxdays,mc);
                                //当月天数对应的工资
                                BigDecimal empSalary_month = hissalary.multiply(Percentage);
                                //当月天数对应的五险一金
                                BigDecimal InsuranceFund_month = (hisfund.add(hisinsurance)).multiply(Percentage);
                                //当月五险和税前工资合计
                                empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                                empSalaryinfo_first.setEmpInsuranceFund(df.format(InsuranceFund_month.doubleValue()));
                                empSalaryinfo_first.setEmpSalary(df.format(empSalary_month.doubleValue()));
                                empSalaryinfo_first.setEmpMonthSalary(df.format(empMonthSalary_Total.doubleValue()));
                                String yearmonth_first = startdate.substring(0,7);
                                //调用公共方法
                                monthname = pcai.SetMonthValue(yearmonth_first,empSalaryinfo_first,monthname);
                                //获取结束日期是当月的第几天
                                int numday_end = GetDateUtils.getMonthday(endDate_bs);
                                BigDecimal numdays_end = new BigDecimal(numday_end);
                                //获取开始日期当月的最大天数
                                int maxday_end = 0;
                                try {
                                    maxday_end = GetDateUtils.getDaysOfMonth(endDate_bs);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BigDecimal maxdays_end = new BigDecimal(maxday_end);
                                //计算出天数百分比
                                BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                                BigDecimal empSalary_month_end = new BigDecimal("0");
                                BigDecimal InsuranceFund_month_end = new BigDecimal("0");
                                //当月天数对应的工资
                                empSalary_month_end = hissalary.multiply(Percentage_end);
                                //当月天数对应的五险一金
                                InsuranceFund_month_end = (hisfund.add(hisinsurance)).multiply(Percentage_end);
                                //当月五险和税前工资合计
                                empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);
                                empSalaryinfo_end.setEmpInsuranceFund(df.format(InsuranceFund_month_end.doubleValue()));
                                empSalaryinfo_end.setEmpSalary(df.format(empSalary_month_end.doubleValue()));
                                empSalaryinfo_end.setEmpMonthSalary(df.format(empMonthSalary_Total_end.doubleValue()));

                                String yearmonth_end = endDate_bs.substring(0,7);
                                //调用公共方法
                                monthname = pcai.SetMonthValue(yearmonth_end,empSalaryinfo_end,monthname);
                                //调用公共方法
                                monthname = pcai.SetMonthListValue(list_c,empSalaryinfo,monthname);

                                //---------计算外包人员收入------------------
                                itempercome.setItemid(item_id);
                                itempercome.setEmpid(empid);
                                List<Itemper> list_percost = proInfoMapper.selectPercostbundling(itempercome);
                                Itemperlevel itemperlevel1 = new Itemperlevel();
                                BigDecimal totalIncome = new BigDecimal("0");
                                BigDecimal empIncome_month = new BigDecimal("0");
                                BigDecimal cycle = new BigDecimal("0");
                                BigDecimal empIncome_month_end = new BigDecimal("0");
                                BigDecimal empProfitrate = new BigDecimal("0");
                                String date = "";
                                if(item.getItem_type().equals("2")) {
                                    int number = 0;
                                    for (int c = 0; c < list_percost.size(); c++) {
                                        Itemper it = list_percost.get(c);
                                        itemperlevel1 = it.getLevel();
                                        String start = it.getStartdate();//开始日期
                                        String end1 = it.getEnddate();//结束日期
                                        String price = itemperlevel1.getLevel_price();
                                        BigDecimal price_level = new BigDecimal(price);
                                        if (end1==null || end1.equals("")) {
                                            end1 = sdf.format(new Date());
                                        }
                                        //开始日期和结束日期相隔天数
                                        int dayapart = DateDiffMonth.nDaysBetweenTwoDate(start,end1);
                                        if(dayapart<=1){
                                            number++;
                                            continue;
                                        }
                                        List<String> list_se = new ArrayList<>();
                                        try {
                                            list_se = GetDateUtils.getYearMonth(start, end1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        String first = start.substring(0,7);//绑定日期
                                        String stp = end1.substring(0,7);//解绑日期或当前日期
                                        if(first.equals(stp)){
                                            //获取开始日期是当月的第几天
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = new BigDecimal(dayapart);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);
                                            totalIncome = totalIncome.add(empIncome_month);
                                            empIncome_month = new BigDecimal("0");
                                            date=end1;
                                        }else if(list_se.size()==1){
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = maxdays1.subtract(numdays1);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);

                                            int numday_end1 = GetDateUtils.getMonthday(end1);
                                            BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                            //获取开始日期当月的最大天数
                                            int maxday_end1 = 0;
                                            try {
                                                maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                            //计算出天数百分比
                                            BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                            //当月天数对应的收入
                                            empIncome_month_end = price_level.multiply(Percentage_end1);
                                            BigDecimal total1 = empIncome_month.add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }else if(list_se.size()>1){
                                            //获取开始日期是当月的第几天
                                            if (c == number) {
                                                int numday1 = GetDateUtils.getMonthday(start);
                                                BigDecimal numdays1 = new BigDecimal(numday1);
                                                //获取开始日期当月的最大天数
                                                int maxday1 = 0;
                                                try {
                                                    maxday1 = GetDateUtils.getDaysOfMonth(start);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays1 = new BigDecimal(maxday1);
                                                //计算出天数百分比
                                                BigDecimal workday1 = maxdays1.subtract(numdays1);
                                                BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                                //当月天数对应的收入
                                                empIncome_month = price_level.multiply(Percentage1);
                                            }
                                            //获取日期范围内的月份(变更后)
                                            List<String> list_date = new ArrayList<>();
                                            if (c == number) {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(start, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size() );
                                            } else {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(date, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size()- 1);
                                            }
                                            BigDecimal Incomecycle = price_level.multiply(cycle);
                                            if (c == number) {
                                                empIncome_month_end = new BigDecimal("0");
                                            } else {
                                                int numday_end1 = GetDateUtils.getMonthday(end1);
                                                BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                                //获取开始日期当月的最大天数
                                                int maxday_end1 = 0;
                                                try {
                                                    maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                                //计算出天数百分比
                                                BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                                //当月天数对应的收入
                                                empIncome_month_end = price_level.multiply(Percentage_end1);
                                            }
                                            BigDecimal total1 = empIncome_month.add(Incomecycle).add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            Incomecycle = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }
                                    }
                                    empProfitrate = empTotalSalary.divide(totalIncome,mc);
                                    percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                    totalEmpIncome = totalEmpIncome.add(totalIncome);
                                }
                                percost.setMonthname(monthname);
                                percost.setEmpName(employeeInfo.getEmpName());
                                percost.setEmpStartDate(startdate);
                                percost.setEmpEndDate(endDate_bs);
                                empTotalSalary = empMonthSalary_Total.add(empMonthSalary_Total_end);
                                percost.setEmpTotalSalary(df.format(empTotalSalary.doubleValue()));
//                   percost.setUpdatetime(itemper.getUpdatetime());
                                TotalProject = TotalProject.add(empTotalSalary);
                                percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                listCost.add(percost);
                                percost = new Percost();
                                monthname = new Monthname();
                                empSalaryinfo = new EmpSalaryinfo();
                                empSalaryinfo_first = new EmpSalaryinfo();
                                empSalaryinfo_end = new EmpSalaryinfo();
                            }else if(list_c.size()>1){
                                //按当前工资
                                //获取开始日期是当月的第几天
                                int numday = GetDateUtils.getMonthday(startdate);
                                BigDecimal numdays = new BigDecimal(numday);
                                //获取开始日期当月的最大天数
                                int maxday = 0;
                                try {
                                    maxday = GetDateUtils.getDaysOfMonth(startdate);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BigDecimal maxdays = new BigDecimal(maxday);
                                //计算出天数百分比
                                BigDecimal workday = maxdays.subtract(numdays);
                                BigDecimal Percentage = workday.divide(maxdays,mc);
                                //当月天数对应的工资
                                BigDecimal empSalary_month = hissalary.multiply(Percentage);
                                //当月天数对应的五险一金
                                BigDecimal InsuranceFund_month = (hisfund.add(hisinsurance)).multiply(Percentage);
                                //当月五险和税前工资合计
                                empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);
                                empSalaryinfo_first.setEmpInsuranceFund(df.format(InsuranceFund_month.doubleValue()));
                                empSalaryinfo_first.setEmpSalary(df.format(empSalary_month.doubleValue()));
                                empSalaryinfo_first.setEmpMonthSalary(df.format(empMonthSalary_Total.doubleValue()));
                                String yearmonth_first = startdate.substring(0,7);
                                //调用公共方法
                                monthname = pcai.SetMonthValue(yearmonth_first,empSalaryinfo_first,monthname);
                                //获取结束日期是当月的第几天
                                int numday_end = GetDateUtils.getMonthday(endDate_bs);
                                BigDecimal numdays_end = new BigDecimal(numday_end);
                                //获取开始日期当月的最大天数
                                int maxday_end = 0;
                                try {
                                    maxday_end = GetDateUtils.getDaysOfMonth(endDate_bs);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BigDecimal maxdays_end = new BigDecimal(maxday_end);
                                //计算出天数百分比
                                BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                                BigDecimal empSalary_month_end = new BigDecimal("0");
                                BigDecimal InsuranceFund_month_end = new BigDecimal("0");
                                //当月天数对应的工资
                                empSalary_month_end = hissalary.multiply(Percentage_end);
                                //当月天数对应的五险一金
                                InsuranceFund_month_end = (hisfund.add(hisinsurance)).multiply(Percentage_end);
                                //当月五险和税前工资合计
                                empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);
                                empSalaryinfo_end.setEmpInsuranceFund(df.format(InsuranceFund_month_end.doubleValue()));
                                empSalaryinfo_end.setEmpSalary(df.format(empSalary_month_end.doubleValue()));
                                empSalaryinfo_end.setEmpMonthSalary(df.format(empMonthSalary_Total_end.doubleValue()));

                                String yearmonth_end = endDate_bs.substring(0,7);
                                //调用公共方法
                                monthname = pcai.SetMonthValue(yearmonth_end,empSalaryinfo_end,monthname);
                                //调用公共方法
                                monthname = pcai.SetMonthListValue(list_c,empSalaryinfo,monthname);
                                BigDecimal itemcycle = new BigDecimal(list_c.size()-1);
//                    BigDecimal itemcycle = new BigDecimal(month);
                                BigDecimal fund = hisfund.multiply(itemcycle);
                                BigDecimal Insurance = hisinsurance.multiply(itemcycle);
                                BigDecimal empInsuranceFund = fund.add(Insurance);
                                BigDecimal Salary = hissalary.multiply(itemcycle);
                                empTotalSalary = Salary.add(empInsuranceFund).add(empMonthSalary_Total).add(empMonthSalary_Total_end);
                                //---------计算外包人员收入------------------
                                itempercome.setItemid(item_id);
                                itempercome.setEmpid(empid);
                                List<Itemper> list_percost = proInfoMapper.selectPercostbundling(itempercome);
                                Itemperlevel itemperlevel1 = new Itemperlevel();
                                BigDecimal totalIncome = new BigDecimal("0");
                                BigDecimal empIncome_month = new BigDecimal("0");
                                BigDecimal cycle = new BigDecimal("0");
                                BigDecimal empIncome_month_end = new BigDecimal("0");
                                BigDecimal empProfitrate = new BigDecimal("0");
                                String date = "";
                                if(item.getItem_type().equals("2")) {
                                    int number = 0;
                                    for (int c = 0; c < list_percost.size(); c++) {
                                        Itemper it = list_percost.get(c);
                                        itemperlevel1 = it.getLevel();
                                        String start = it.getStartdate();//开始日期
                                        String end1 = it.getEnddate();//结束日期
                                        String price = itemperlevel1.getLevel_price();
                                        BigDecimal price_level = new BigDecimal(price);
                                        if (end1==null || end1.equals("")) {
                                            end1 = sdf.format(new Date());
                                        }
                                        //开始日期和结束日期相隔天数
                                        int dayapart = DateDiffMonth.nDaysBetweenTwoDate(start,end1);
                                        if(dayapart<=1){
                                            number++;
                                            continue;
                                        }
                                        List<String> list_se = new ArrayList<>();
                                        try {
                                            list_se = GetDateUtils.getYearMonth(start, end1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        String first = start.substring(0,7);//绑定日期
                                        String stp = end1.substring(0,7);//解绑日期或当前日期
                                        if(first.equals(stp)){
                                            //获取开始日期是当月的第几天
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = new BigDecimal(dayapart);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);
                                            totalIncome = totalIncome.add(empIncome_month);
                                            empIncome_month = new BigDecimal("0");
                                            date=end1;
                                        }else if(list_se.size()==1){
                                            int numday1 = GetDateUtils.getMonthday(start);
                                            BigDecimal numdays1 = new BigDecimal(numday1);
                                            //获取开始日期当月的最大天数
                                            int maxday1 = 0;
                                            try {
                                                maxday1 = GetDateUtils.getDaysOfMonth(start);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays1 = new BigDecimal(maxday1);
                                            //计算出天数百分比
                                            BigDecimal workday1 = maxdays1.subtract(numdays1);
                                            BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                            //当月天数对应的收入
                                            empIncome_month = price_level.multiply(Percentage1);

                                            int numday_end1 = GetDateUtils.getMonthday(end1);
                                            BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                            //获取开始日期当月的最大天数
                                            int maxday_end1 = 0;
                                            try {
                                                maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                            //计算出天数百分比
                                            BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                            //当月天数对应的收入
                                            empIncome_month_end = price_level.multiply(Percentage_end1);
                                            BigDecimal total1 = empIncome_month.add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }else if(list_se.size()>1){
                                            //获取开始日期是当月的第几天
                                            if (c == number) {
                                                int numday1 = GetDateUtils.getMonthday(start);
                                                BigDecimal numdays1 = new BigDecimal(numday1);
                                                //获取开始日期当月的最大天数
                                                int maxday1 = 0;
                                                try {
                                                    maxday1 = GetDateUtils.getDaysOfMonth(start);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays1 = new BigDecimal(maxday1);
                                                //计算出天数百分比
                                                BigDecimal workday1 = maxdays1.subtract(numdays1);
                                                BigDecimal Percentage1 = workday1.divide(maxdays1, mc);
                                                //当月天数对应的收入
                                                empIncome_month = price_level.multiply(Percentage1);
                                            }
                                            //获取日期范围内的月份(变更后)
                                            List<String> list_date = new ArrayList<>();
                                            if (c == number) {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(start, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size() );
                                            } else {
                                                try {
                                                    list_date = GetDateUtils.getYearMonth(date, end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cycle = new BigDecimal(list_date.size()- 1);
                                            }
                                            BigDecimal Incomecycle = price_level.multiply(cycle);
                                            if (c == number) {
                                                empIncome_month_end = new BigDecimal("0");
                                            } else {
                                                int numday_end1 = GetDateUtils.getMonthday(end1);
                                                BigDecimal numdays_end1 = new BigDecimal(numday_end1);
                                                //获取开始日期当月的最大天数
                                                int maxday_end1 = 0;
                                                try {
                                                    maxday_end1 = GetDateUtils.getDaysOfMonth(end1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                BigDecimal maxdays_end1 = new BigDecimal(maxday_end1);
                                                //计算出天数百分比
                                                BigDecimal Percentage_end1 = numdays_end1.divide(maxdays_end1, mc);
                                                //当月天数对应的收入
                                                empIncome_month_end = price_level.multiply(Percentage_end1);
                                            }
                                            BigDecimal total1 = empIncome_month.add(Incomecycle).add(empIncome_month_end);
                                            totalIncome = totalIncome.add(total1);
                                            date=end1;
                                            empIncome_month = new BigDecimal("0");
                                            Incomecycle = new BigDecimal("0");
                                            empIncome_month_end = new BigDecimal("0");
                                        }
                                    }
                                    empProfitrate = empTotalSalary.divide(totalIncome,mc);
                                    percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                    totalEmpIncome = totalEmpIncome.add(totalIncome);
                                }
                                percost.setMonthname(monthname);
                                percost.setEmpName(employeeInfo.getEmpName());
                                percost.setEmpStartDate(startdate);
                                percost.setEmpEndDate(endDate_bs);
                                //empTotalSalary = empMonthSalary_Total.add(empMonthSalary_Total_end);
                                percost.setEmpTotalSalary(df.format(empTotalSalary.doubleValue()));
//                   percost.setUpdatetime(itemper.getUpdatetime());
                                TotalProject = TotalProject.add(empTotalSalary);
                                percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                                listCost.add(percost);
                                percost = new Percost();
                                monthname = new Monthname();
                                empSalaryinfo = new EmpSalaryinfo();
                                empSalaryinfo_first = new EmpSalaryinfo();
                                empSalaryinfo_end = new EmpSalaryinfo();
                            }

                        }
                    }

                }
            }
        }
        monthname.setJanuary(empSalaryinfo_null);
        monthname.setFebruary(empSalaryinfo_null);
        monthname.setMarch(empSalaryinfo_null);
        monthname.setApril(empSalaryinfo_null);
        monthname.setMay(empSalaryinfo_null);
        monthname.setJune(empSalaryinfo_null);
        monthname.setJuly(empSalaryinfo_null);
        monthname.setAugust(empSalaryinfo_null);
        monthname.setSeptember(empSalaryinfo_null);
        monthname.setOctober(empSalaryinfo_null);
        monthname.setNovember(empSalaryinfo_null);
        monthname.setDecember(empSalaryinfo_null);
        if(!item.getItem_type().equals("2") && !TotalProject.equals(new BigDecimal("0"))){
            percost.setMonthname(monthname);
            percost.setEmpName("项目成本汇总");
            String startDate = item.getItem_startdate();
            String endDate =item.getItem_enddate();
            String currentdate = sdf.format(new Date());
            BigDecimal Percentage = new BigDecimal("0");
            BigDecimal empProjectfitrate = new BigDecimal("0");
            int days = 0;
            int daysTotal =  DateDiffMonth.nDaysBetweenTwoDate(startDate,endDate);
            if(Integer.parseInt(endDate.replace("-",""))>Integer.parseInt(currentdate.replace("-",""))){
                endDate = sdf.format(new Date());
                //获取日期范围内间隔天数
                days = DateDiffMonth.nDaysBetweenTwoDate(startDate,endDate);
                BigDecimal dayspart = new BigDecimal(days);
                //计算出天数百分比
                Percentage = dayspart.divide(new BigDecimal(daysTotal), mc);
            }else{
                Percentage = new BigDecimal("1");
            }
            percost.setEmpStartDate(item.getItem_startdate());
            percost.setEmpEndDate(endDate);
            BigDecimal  curfund= new BigDecimal("0");
            //项目总资金
            String fund = item.getItem_fund();
            if(fund !=null && !fund.equals("") && !fund.equals("0.00")){
                BigDecimal itemfund = new BigDecimal(fund);
                //截止当前日期的项目资金
                curfund= itemfund.multiply(Percentage);
            }
        if(!curfund.equals(new BigDecimal("0"))){
            BigDecimal  curworkcost= new BigDecimal("0");
            //项目实施费用
            String workcost = item.getWorkcost();
            if(workcost!=null && !workcost.equals("")){
                BigDecimal Workcost = new BigDecimal(workcost);
                //截止当前日期的项目实施费用
                curworkcost= Workcost.multiply(Percentage);
            }
            String taxrate = item.getTaxrate();
            //税务支出
            BigDecimal Grosscost = (curfund.divide(((new BigDecimal("1")).add(new BigDecimal(taxrate))),mc)).multiply(new BigDecimal(taxrate)).multiply(new BigDecimal("1.12"));
            //项目毛利
            BigDecimal Grossprofit = curfund.subtract(Grosscost);

            //项目毛利费用率
            empProjectfitrate = (TotalProject.add(curworkcost)).divide(Grossprofit,mc);
            }
            percost.setEmpProfitrate(df1.format(empProjectfitrate.doubleValue()));
            percost.setEmpTotalSalary(df.format(TotalProject.doubleValue()));
            listCost.add(percost);
        }else if(!TotalProject.equals(new BigDecimal("0"))){
            percost.setMonthname(monthname);
            percost.setEmpName("项目成本汇总");
            String startDate = item.getItem_startdate();
            String endDate =item.getItem_enddate();
            String currentdate = sdf.format(new Date());
            BigDecimal Percentage = new BigDecimal("0");
            int days = 0;
            int daysTotal =  DateDiffMonth.nDaysBetweenTwoDate(startDate,endDate);
            if(Integer.parseInt(endDate.replace("-",""))>Integer.parseInt(currentdate.replace("-",""))){
                endDate = sdf.format(new Date());
                //获取日期范围内间隔天数
                days = DateDiffMonth.nDaysBetweenTwoDate(startDate,endDate);
                BigDecimal dayspart = new BigDecimal(days);
                //计算出天数百分比
                Percentage = dayspart.divide(new BigDecimal(daysTotal), mc);
            }else{
                Percentage = new BigDecimal("1");
            }
            percost.setEmpStartDate(item.getItem_startdate());
            percost.setEmpEndDate(endDate);

            BigDecimal  curworkcost= new BigDecimal("0");
            //项目实施费用
            String workcost = item.getWorkcost();
            if(workcost!=null && !workcost.equals("")){
                BigDecimal Workcost = new BigDecimal(workcost);
                //截止当前日期的项目实施费用
                curworkcost= Workcost.multiply(Percentage);
            }
            String taxrate = item.getTaxrate();
            //税务支出
            BigDecimal Grosscost = (totalEmpIncome.divide(((new BigDecimal("1")).add(new BigDecimal(taxrate))),mc)).multiply(new BigDecimal(taxrate)).multiply(new BigDecimal("1.12"));
            //项目毛利
            BigDecimal Grossprofit = totalEmpIncome.subtract(Grosscost);
            //项目毛利费用率
            BigDecimal empProjectfitrate = (TotalProject.add(curworkcost)).divide(Grossprofit,mc);
            percost.setEmpProfitrate(df1.format(empProjectfitrate.doubleValue()));
            percost.setEmpTotalSalary(df.format(TotalProject.doubleValue()));
            listCost.add(percost);
        }
        return listCost;
    }
    /*@Override
    public List<Percost> selectPerCostById(String item_id) {
        Item item = proInfoMapper.selectProById(item_id);
        EmployeeInfo employeeInfo = new EmployeeInfo();
        List<Percost> listCost = new ArrayList<Percost>();
        Percost percost = new Percost();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String startDate = item.getItem_startdate();//开始日期
        String EndDate = item.getItem_enddate();
        String month1 = "";
        try {
            long monthday = DateDiffMonth.getMonthDiff(startDate,EndDate);
            month1 = monthday + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (month1.equals("0")){
            month1 = "1";
        }
        item.setItem_cycle(month1);
        BigDecimal itemcycle1 = new BigDecimal(month1);
        String itemtype = item.getItem_type();//项目类型
        //人员外包计算项目总金额
        if(itemtype.equals("2")){
            List<Itemperlevel> list = proInfoMapper.selectPerLevelById(item_id);
            Itemperlevel level = new Itemperlevel();
            String levelnum = "";
            String levelprice = "";
            BigDecimal Totalfund = new BigDecimal("0");
            for(int i=0;i<list.size();i++){
                level = list.get(i);
                levelnum = level.getLevel_num();
                BigDecimal num = new BigDecimal(levelnum);
                levelprice = level.getLevel_price();
                BigDecimal price = new BigDecimal(levelprice);
                BigDecimal total = price.multiply(num).multiply(itemcycle1);
                Totalfund = Totalfund.add(total);
            }
            item.setItem_fund(Totalfund.toString());
            proInfoMapper.updateProject(item);
        }
        List<EmployeeInfo> listEmp= new ArrayList<EmployeeInfo>();
        Itemperlevel itemperlevel = new Itemperlevel();
        if(item.getItem_type().equals("2")){
            List<Itemperlevel> listLevel = proInfoMapper.selectPerLevelById(item_id);
            for(int x=0;x<listLevel.size();x++){
                itemperlevel = listLevel.get(x);
                List<EmployeeInfo> listlevel = proInfoMapper.selectPerById(String.valueOf(itemperlevel.getItem_level_id()));
                for(int y=0;y<listlevel.size();y++){
                    EmployeeInfo employeeInfo1 = listlevel.get(y);
                    employeeInfo1.setItem_level_id(itemperlevel.getItem_level_id());
                    listEmp.add(employeeInfo1);
                    employeeInfo1 = new EmployeeInfo();
                }
            }
        }else {
            listEmp = proInfoMapper.selectEmpByItemId(item_id);
        }
        //        if(listEmp.size()>0 && Integer.parseInt(itemnum)==listEmp.size()){
            if(listEmp.size()>0){
                for(int i = 0; i<listEmp.size(); i++){
                    employeeInfo = listEmp.get(i);
                    String empid = employeeInfo.getEmpId();
                    EmpCost empcost_his = proInfoMapper.selectHisSalaryById(empid);
                    if(empcost_his == null){
                        //工资无变更人员
                        EmpCost empCost = employeeInfo.getCost();
                        Itemper itemper = employeeInfo.getBinding();

                        Double salary = empCost.getEmpSalary();
                        BigDecimal emp_Salary = new BigDecimal(salary);
                        BigDecimal empSalary = new BigDecimal("0");
                        if(employeeInfo.getEmpState().equals("3")){
                            empSalary = emp_Salary.multiply(new BigDecimal("0.8"));
                        }else{
                            empSalary = emp_Salary;
                        }
                        Double insurance = empCost.getEmpInsurance();
                        BigDecimal empInsurance = new BigDecimal(insurance);
                        Double Fund = empCost.getEmpFund();
                        BigDecimal empFund = new BigDecimal(Fund);
                        Itemper emper =new Itemper();
                        if(item.getItem_type().equals("2")){
                             itemper.setItem_level_id(employeeInfo.getItem_level_id());
                             itemper.setEmpid(Integer.parseInt(employeeInfo.getEmpId()));
                             emper =proInfoMapper.selectbundling(itemper);
                        }else{
                             itemper.setItemid(Integer.parseInt(item_id));
                             itemper.setEmpid(Integer.parseInt(employeeInfo.getEmpId()));
                             emper = proInfoMapper.selectUnbundling(itemper);
                        }
                        String startdate = emper.getStartdate();
                        String endDate = "";
                        if(emper.getEnddate() == null || emper.getEnddate().equals("")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            endDate = sdf.format(new Date());
                        }else {
                            endDate = emper.getEnddate();
                        }
                        String month = "";
                        try {
                            long monthday = DateDiffMonth.getMonthDiff(startdate,endDate);
                            month = monthday + "";
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        BigDecimal itemcycle = new BigDecimal(month);

                        BigDecimal fund = empFund.multiply(itemcycle);
                        BigDecimal Insurance = empInsurance.multiply(itemcycle);
                        BigDecimal empInsuranceFund = fund.add(Insurance);
                        BigDecimal Salary = empSalary.multiply(itemcycle);
                        BigDecimal empTotalSalary = Salary.add(empInsuranceFund);
                        //变更前社保和公积金单月合计
                        BigDecimal empMonthInsuranceFund = empFund.add(empInsurance);
                        //变更前单月合计
                        BigDecimal empMonthTotalSalary = empSalary.add(empMonthInsuranceFund);

                        percost.setEmpInsuranceFund(df.format(empMonthInsuranceFund.doubleValue()));
                        percost.setEmpSalary(df.format(empSalary.doubleValue()));
                        percost.setEmpMonthSalary(df.format(empMonthTotalSalary.doubleValue()));
                        percost.setEmpName(employeeInfo.getEmpName());
                        percost.setEmpLevel(employeeInfo.getEmpLevel());
                        percost.setEmpStartDate(startdate);
                        percost.setEmpEndDate(endDate);
                        percost.setEmpTotalSalary(df.format(empTotalSalary.doubleValue()));
                        percost.setEmpCycle(month);
//                        percost.setUpdatetime(itemper.getUpdatetime());
                        listCost.add(percost);
                        percost = new Percost();
                    }else {
                        //工资有变更人员,变更前工资
                        Double salary1 = empcost_his.getEmpSalary();
                        BigDecimal hsalary = new BigDecimal(salary1);
                        BigDecimal hissalary = new BigDecimal("0");
                        if(employeeInfo.getEmpState().equals("3")){
                            hissalary = hsalary.multiply(new BigDecimal("0.8"));
                        }else{
                            hissalary = hsalary;
                        }
                        Double insurance1 = empcost_his.getEmpInsurance();
                        BigDecimal hisinsurance = new BigDecimal(insurance1);
                        Double Fund1 = empcost_his.getEmpFund();
                        BigDecimal hisfund = new BigDecimal(Fund1);

                        Date hisupdateTime = empcost_his.getUpdateTime();
                        SimpleDateFormat sdfhis = new SimpleDateFormat("yyyy-MM-dd");
                        String end = sdfhis.format(hisupdateTime);
                        String[] enddatehis = end.split("T");
                        //工资变更日期
                        String endDatehis = enddatehis[0];
                        String enddatechange = endDatehis.replace("-", "");

                        EmpCost empCost = employeeInfo.getCost();
                        Itemper itemper = employeeInfo.getBinding();
                        Double salary = empCost.getEmpSalary();//变更后工资
                        BigDecimal emp_Salary = new BigDecimal(salary);
                        BigDecimal empSalary = new BigDecimal("0");
                        if(employeeInfo.getEmpState().equals("3")){
                            empSalary = emp_Salary.multiply(new BigDecimal("0.8"));
                        }else{
                            empSalary = emp_Salary;
                        }
                        Double insurance = empCost.getEmpInsurance();
                        BigDecimal empInsurance = new BigDecimal(insurance);
                        Double Fund = empCost.getEmpFund();
                        BigDecimal empFund = new BigDecimal(Fund);
                        //是否解绑，解绑日期
                        Itemper itemper1 = new Itemper();
                        if(item.getItem_type().equals("2")){
                            itemper.setItem_level_id(employeeInfo.getItem_level_id());
                            itemper.setEmpid(Integer.parseInt(employeeInfo.getEmpId()));
                            itemper1 =proInfoMapper.selectbundling(itemper);
                        }else{
                            itemper.setItemid(Integer.parseInt(item_id));
                            itemper.setEmpid(Integer.parseInt(employeeInfo.getEmpId()));
                            itemper1 = proInfoMapper.selectUnbundling(itemper);
                        }
                        String startdate = itemper1.getStartdate();
//                    if(Integer.parseInt(enddatechange)<=Integer.parseInt(updatechange)){
                        String endDate_bs = "";//变更日
                        String endDate_be = "";//到当前日
                        if (itemper1.getEnddate() == null || itemper1.getEnddate().equals("")) {//无解绑
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            endDate_bs = sdf.format(new Date());
                            String endDatebs = endDate_bs.replace("-", "");
                            if (Integer.parseInt(endDatebs) > Integer.parseInt(enddatechange)) {//当前日期大于变更日期
                                endDate_bs = endDatehis;
                                endDate_be = sdf.format(new Date());
                            }
                        } else {//解绑
                            endDate_bs = itemper1.getEnddate().replace("-", "");
                            if (Integer.parseInt(endDate_bs) > Integer.parseInt(enddatechange)) {//解绑日期大于变更日期
                                endDate_bs = endDatehis;
                                endDate_be = itemper1.getEnddate();
                            }
                        }
                        String month_bs = "";//开始到变更
                        String month_be = "";//变更到当前或者到解绑
                        try {
                            long monthday_bs = DateDiffMonth.getMonthDiff(startdate, endDate_bs);
                            month_bs = monthday_bs + "";
                            if (!endDate_be.equals("")) {
                                long monthday_be = DateDiffMonth.getMonthDiff(endDate_bs, endDate_be);
                                String month = monthday_be + "";
                                int month2 = Integer.parseInt(month) -1;
                                month_be = String.valueOf(month2);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        BigDecimal itemcycle_bs = new BigDecimal(month_bs);
                        BigDecimal itemcycle_be = new BigDecimal("0");

                        if (!month_be.equals("")) {
                            itemcycle_be = new BigDecimal(month_be);

                            BigDecimal fund_bs = hisfund.multiply(itemcycle_bs);
                            BigDecimal Insurance_bs = hisinsurance.multiply(itemcycle_bs);
                            BigDecimal empInsuranceFund_bs = fund_bs.add(Insurance_bs);
                            BigDecimal Salary_bs = hissalary.multiply(itemcycle_bs);
                            BigDecimal empTotalSalary_bs = Salary_bs.add(empInsuranceFund_bs);
                           *//* //变更前社保和公积金单月合计
                            BigDecimal empMonthInsuranceFund_bs = hisfund.add(hisinsurance);
                            //变更前单月合计
                            BigDecimal empMonthTotalSalary_bs = hissalary.add(empMonthInsuranceFund_bs);*//*

                            BigDecimal fund_be = empFund.multiply(itemcycle_be);
                            BigDecimal Insurance_be = empInsurance.multiply(itemcycle_be);
                            BigDecimal empInsuranceFund_be = fund_be.add(Insurance_be);
                            BigDecimal Salary_be = empSalary.multiply(itemcycle_be);
                            BigDecimal empTotalSalary_be = Salary_be.add(empInsuranceFund_be);
                            //变更后社保和公积金单月合计
                            BigDecimal empMonthInsuranceFund_be = empFund.add(empInsurance);
                            //变更后单月合计
                            BigDecimal empMonthTotalSalary_be = empSalary.add(empMonthInsuranceFund_be);

//                            BigDecimal a = empInsuranceFund_bs.add(empInsuranceFund_be);
                            percost.setEmpInsuranceFund(df.format(empMonthInsuranceFund_be.doubleValue()));
//                            BigDecimal b = Salary_bs.add(Salary_be);
                            percost.setEmpSalary(df.format(empSalary.doubleValue()));
                            percost.setEmpName(employeeInfo.getEmpName());
                            percost.setEmpLevel(employeeInfo.getEmpLevel());
                            BigDecimal c = empTotalSalary_bs.add(empTotalSalary_be);
                            //总合计
                            percost.setEmpTotalSalary(df.format(c.doubleValue()));
                            //单月工资总计
                            percost.setEmpMonthSalary(df.format(empMonthTotalSalary_be.doubleValue()));
                            percost.setEmpChangeBeforeSalary(df.format(hissalary.doubleValue()));
                            percost.setEmpChangeBeforeCycle(month_bs);
                            percost.setEmpChangeBeforeStartDate(startDate);
                            percost.setEmpChangeBeforeEndDate(endDate_bs);
                            percost.setEmpChangeAfterSalary(df.format(empSalary.doubleValue()));
                            percost.setEmpChangeAfterCycle(month_be);
                            percost.setEmpChangeAfterStartDate(endDate_bs);
                            percost.setEmpChangeAfterEndDate(endDate_be);
                            percost.setEmpStartDate(startDate);
                            percost.setEmpEndDate(endDate_be);
                            percost.setEmpCycle(String.valueOf(Integer.parseInt(month_bs)+Integer.parseInt(month_be)));
//                            percost.setUpdatetime(itemper.getUpdatetime());
                            listCost.add(percost);
                            percost = new Percost();
                        } else {
                            BigDecimal fund = empFund.multiply(itemcycle_bs);
                            BigDecimal Insurance = empInsurance.multiply(itemcycle_bs);
                            BigDecimal empInsuranceFund = fund.add(Insurance);
                            BigDecimal Salary = empSalary.multiply(itemcycle_bs);
                            BigDecimal empTotalSalary = Salary.add(empInsuranceFund);
                            //变更前社保和公积金单月合计
                            BigDecimal empMonthInsuranceFund = empFund.add(empInsurance);
                            //变更前单月合计
                            BigDecimal empMonthTotalSalary = empSalary.add(empMonthInsuranceFund);

                            percost.setEmpInsuranceFund(df.format(empMonthInsuranceFund.doubleValue()));
                            percost.setEmpSalary(df.format(empSalary.doubleValue()));
                            percost.setEmpMonthSalary(df.format(empMonthTotalSalary.doubleValue()));
                            percost.setEmpName(employeeInfo.getEmpName());
                            percost.setEmpLevel(employeeInfo.getEmpLevel());
                            percost.setEmpStartDate(startDate);
                            percost.setEmpEndDate(endDate_bs);
                            percost.setEmpTotalSalary(df.format(empTotalSalary.doubleValue()));
                            percost.setEmpCycle(month_bs);
//                            percost.setUpdatetime(itemper.getUpdatetime());
                            listCost.add(percost);
                            percost = new Percost();
                        }
                    }
                }
            }

        return listCost;
    }*/
    /**
     * 获取项目人员级别信息
     * @return
     */
    @Override
    public List<Itemperlevel> selectIsLevel(Itemperlevel itemperlevel) {
        return proInfoMapper.selectIsLevel(itemperlevel);
    }
    /**
     * 根据项目ID查询项目详细信息计算外包人员收入
     * @return
     */
    /*@Override
    public List<Perincome> selectPerIncomeById(String item_id) {
        List<Perincome> listIncome = new ArrayList<Perincome>();
        Perincome perincome = new Perincome();
//        Item item = proInfoMapper.selectProById(item_id);
        EmployeeInfo employeeInfo = new EmployeeInfo();
//        String startDate = item.getItem_startdate();//开始日期
//        String EndDate = item.getItem_enddate();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        List<Itemperlevel> listLevel = proInfoMapper.selectPerLevelById(item_id);
        if(listLevel.size()>0){
            for(int i = 0; i<listLevel.size(); i++){
                Itemperlevel itemperlevel = listLevel.get(i);
                String itemlevelid = String.valueOf(itemperlevel.getItem_level_id());
                String level_price = itemperlevel.getLevel_price();
                BigDecimal levelprice = new BigDecimal(level_price);
                String level_name = itemperlevel.getLevel_name();
                List<EmployeeInfo> listEmp = proInfoMapper.selectPerById(itemlevelid);
            if(listEmp.size()>0){
                for(int a = 0; a<listEmp.size(); a++){
                    employeeInfo = listEmp.get(a);
                    Itemper itemper = employeeInfo.getBinding();
                    itemper.setItem_level_id(Integer.parseInt(itemlevelid));
                    itemper.setEmpid(Integer.parseInt(employeeInfo.getEmpId()));
                    //解绑日期
                    Itemper emper = proInfoMapper.selectbundling(itemper);
                    String startdate = emper.getStartdate();
                    String enddate = "";
                    if(emper.getEnddate() == null || emper.getEnddate().equals("")){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        enddate = sdf.format(new Date());
                    }else{
                        enddate = emper.getEnddate();
                    }
                    String month = "";
                    try {
                        long monthday = DateDiffMonth.getMonthDiff(startdate,enddate);
                        month = monthday + "";
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    BigDecimal itemcycle = new BigDecimal(month);//工作周期
                    BigDecimal SalaryTotal = levelprice.multiply(itemcycle);
                    perincome.setEmpTotalSalary(df.format(SalaryTotal.doubleValue()));
                    perincome.setEmpName(employeeInfo.getEmpName());
                    perincome.setEmpSalary(level_price);
                    perincome.setEmpStartDate(startdate);
                    perincome.setEmpEndDate(enddate);
                    perincome.setEmpCycle(month);
                    perincome.setEmpLevel(level_name);
                    listIncome.add(perincome);
                    perincome = new Perincome();
                }
            }
           }
        }
        return listIncome;
    }*/
    /**
     * 获取级别id获取外包人员信息
     *
     * @param item_level_id 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo> selectOutPerById(String item_level_id) {
        return proInfoMapper.selectOutPerById(item_level_id);
    }

    /**
     * 获取外包人员绑定信息
     *
     * @param item_id 调度信息
     * @return 调度任务集合
     */
    public List<EmployeeInfo> listPerBinding(String item_id) {
        return proInfoMapper.listPerBinding(item_id);
    }
    /**
     * 判断项目开始日期是否大于当日
     *
     * @param Itemper 调度信息
     * @return 调度任务集合
     */
    @Override
    public Boolean CompareItemdate(Itemper Itemper) {
        String itemid = Itemper.getItemid();
        Item item = proInfoMapper.selectProById(itemid);
        String startdate = item.getItem_startdate().replace("-","");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curdate = sdf.format(new Date()).replace("-","");
        if(Integer.parseInt(startdate)>Integer.parseInt(curdate)){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 根据项目ID查询项目详细信息计算外包人员收入
     * @return
     */
    @Override
    public List<Perincome> selectPerIncomeById(String item_id) {
        List<Perincome> listIncome = new ArrayList<Perincome>();
        Perincome perincome = new Perincome();
        Percost percost = new Percost();
        EmpcostUtil ecu = new EmpcostUtil();
        BigDecimal totalcost = new BigDecimal("0");
        DecimalFormat df = new DecimalFormat("#,##0.00");
        DecimalFormat df1 = new DecimalFormat("0.0000%");
        Item item = proInfoMapper.selectProById(item_id);
        EmployeeInfo employeeInfo = new EmployeeInfo();
        EmpSalaryinfo empSalaryinfo = new EmpSalaryinfo();
        EmpSalaryinfo empSalaryinfo_null = new EmpSalaryinfo();
        BigDecimal no = new BigDecimal("0.00");
        empSalaryinfo_null.setEmpSalary(df.format(no.doubleValue()));
        empSalaryinfo_null.setEmpLevel("");
//        String startDate = item.getItem_startdate();//开始日期
//        String EndDate = item.getItem_enddate();
        Monthname monthname = new Monthname();
        List<EmployeeInfo> listEmp= new ArrayList<EmployeeInfo>();
        Itemperlevel itemperlevel = new Itemperlevel();
        Itemperlevel itemperlevel1 = new Itemperlevel();
        BigDecimal total = new BigDecimal("0");
        //收入合计
        BigDecimal empIncometotal = new BigDecimal("0");
        //收入合计
        BigDecimal empCosttotal = new BigDecimal("0");
        BigDecimal empIncome_month = new BigDecimal("0");
        BigDecimal empProfitrate = new BigDecimal("0");
        BigDecimal empIncome_month_end = new BigDecimal("0");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        MathContext mc = new MathContext(6, RoundingMode.HALF_DOWN);
        PerCostAndIncomeUtil pcai = new PerCostAndIncomeUtil();
        String date = "";
        Itemper itempercome = new Itemper();
        List<Itemperlevel> listLevel = proInfoMapper.selectPerLevelById(item_id);
        if(listLevel.size()>0){
            for(int x=0;x<listLevel.size();x++){
                itemperlevel = listLevel.get(x);
                List<EmployeeInfo> listlevel = proInfoMapper.selectPerById(String.valueOf(itemperlevel.getItem_level_id()));
                for(int y=0;y<listlevel.size();y++){
                    EmployeeInfo employeeInfo1 = listlevel.get(y);
                    employeeInfo1.setItem_level_id(itemperlevel.getItem_level_id());
                    listEmp.add(employeeInfo1);
                    employeeInfo1 = new EmployeeInfo();
                }
            }
            if(listEmp.size()>0) {
                for (int a = 0; a < listEmp.size(); a++) {
                    employeeInfo = listEmp.get(a);
                    String empId = employeeInfo.getEmpId();
                    EmpCost empcost = empCostMapper.selectByEmp(empId);
                    if (empcost == null) {
                        continue;
                    }
                    monthname.setJanuary(empSalaryinfo_null);
                    monthname.setFebruary(empSalaryinfo_null);
                    monthname.setMarch(empSalaryinfo_null);
                    monthname.setApril(empSalaryinfo_null);
                    monthname.setMay(empSalaryinfo_null);
                    monthname.setJune(empSalaryinfo_null);
                    monthname.setJuly(empSalaryinfo_null);
                    monthname.setAugust(empSalaryinfo_null);
                    monthname.setSeptember(empSalaryinfo_null);
                    monthname.setOctober(empSalaryinfo_null);
                    monthname.setNovember(empSalaryinfo_null);
                    monthname.setDecember(empSalaryinfo_null);
                    String empid = employeeInfo.getEmpId();
                    itempercome.setItemid(item_id);
                    itempercome.setEmpid(empid);
                    List<Itemper> list_percost = proInfoMapper.selectPercostbundling(itempercome);
                    String start = "";//开始日期
                    String end = "";//结束日期
                    if(list_percost.size() == 1){
                        Itemper it = list_percost.get(0);
                        itemperlevel1 = it.getLevel();
                        start = it.getStartdate();//开始日期
                        end = it.getEnddate();//结束日期
                        String price = itemperlevel1.getLevel_price();
                        String level_name = itemperlevel1.getLevel_name();
                        BigDecimal price_level = new BigDecimal(price);
                        if (end == null || end.equals("")) {
                            end = sdf.format(new Date());
                        }
                        //开始日期和结束日期相隔天数
                        int dayapart = DateDiffMonth.nDaysBetweenTwoDate(start, end);
                        if (dayapart <= 1) {
                            continue;
                        }
                        List<String> list_se = new ArrayList<>();
                        try {
                            list_se = GetDateUtils.getYearMonth(start, end);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String first = start.substring(0, 7);//绑定日期
                        String stp = end.substring(0, 7);//解绑日期或当前日期
                        if (first.equals(stp) ){
                            //获取开始日期是当月的第几天
                            int numday = GetDateUtils.getMonthday(start);
                            BigDecimal numdays = new BigDecimal(numday);
                            //获取开始日期当月的最大天数
                            int maxday = 0;
                            try {
                                maxday = GetDateUtils.getDaysOfMonth(start);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays = new BigDecimal(maxday);
                            //计算出天数百分比
                            BigDecimal workday = new BigDecimal(dayapart);
                            BigDecimal Percentage = workday.divide(maxdays, mc);
                            //当月天数对应的收入
                            empIncome_month = price_level.multiply(Percentage);
                            total = total.add(empIncome_month);
                            empSalaryinfo.setEmpSalary(df.format(empIncome_month.doubleValue()));
                            empSalaryinfo.setEmpLevel(level_name);
                            monthname = pcai.SetMonthValue(first, empSalaryinfo, monthname);
                            empSalaryinfo = new EmpSalaryinfo();
                            empIncome_month = new BigDecimal("0");
                        }else {
                            int numday = GetDateUtils.getMonthday(start);
                            BigDecimal numdays = new BigDecimal(numday);
                            //获取开始日期当月的最大天数
                            int maxday = 0;
                            try {
                                maxday = GetDateUtils.getDaysOfMonth(start);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays = new BigDecimal(maxday);
                            //计算出天数百分比
                            BigDecimal workday = maxdays.subtract(numdays);
                            BigDecimal Percentage = workday.divide(maxdays, mc);
                            //当月天数对应的收入
                            empIncome_month = price_level.multiply(Percentage);
                            //开始月收入
                            empSalaryinfo.setEmpSalary(df.format(empIncome_month.doubleValue()));
                            empSalaryinfo.setEmpLevel(level_name);
                            monthname = pcai.SetMonthValue(first, empSalaryinfo, monthname);
                            empSalaryinfo = new EmpSalaryinfo();

                            //计算区间收入
                            BigDecimal Incomecycle = price_level.multiply(new BigDecimal(list_se.size()-1));
                            empSalaryinfo.setEmpSalary(df.format(price_level.doubleValue()));
                            empSalaryinfo.setEmpLevel(level_name);
                            monthname = pcai.SetMonthListValue(list_se, empSalaryinfo, monthname);
                            empSalaryinfo = new EmpSalaryinfo();

                            int numday_end = GetDateUtils.getMonthday(end);
                            BigDecimal numdays_end = new BigDecimal(numday_end);
                            //获取开始日期当月的最大天数
                            int maxday_end = 0;
                            try {
                                maxday_end = GetDateUtils.getDaysOfMonth(end);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            BigDecimal maxdays_end = new BigDecimal(maxday_end);
                            //计算出天数百分比
                            BigDecimal Percentage_end = numdays_end.divide(maxdays_end, mc);
                            //当月天数对应的收入
                            empIncome_month_end = price_level.multiply(Percentage_end);
                            BigDecimal total1 = empIncome_month.add(Incomecycle).add(empIncome_month_end);
                            total = total.add(total1);
                            //结束月收入
                            empSalaryinfo.setEmpSalary(df.format(empIncome_month_end.doubleValue()));
                            empSalaryinfo.setEmpLevel(level_name);
                            monthname = pcai.SetMonthValue(stp, empSalaryinfo, monthname);
                            empIncome_month = new BigDecimal("0");
                            empIncome_month_end = new BigDecimal("0");
                            empSalaryinfo = new EmpSalaryinfo();
                        }
                    }else{
                        int number = 0;
                        for (int c = 0; c < list_percost.size(); c++) {
                            Itemper it = list_percost.get(c);
                            itemperlevel1 = it.getLevel();
                            start = it.getStartdate();//开始日期
                            end = it.getEnddate();//结束日期
                            String price = itemperlevel1.getLevel_price();
                            String level_name = itemperlevel1.getLevel_name();
                            BigDecimal price_level = new BigDecimal(price);
                            if (end == null || end.equals("")) {
                                end = sdf.format(new Date());
                            }
                            //开始日期和结束日期相隔天数
                            int dayapart = DateDiffMonth.nDaysBetweenTwoDate(start, end);
                            if (dayapart <= 1) {
                                number++;
                                continue;
                            }
                            List<String> list_se = new ArrayList<>();
                            try {
                                list_se = GetDateUtils.getYearMonth(start, end);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String first = start.substring(0, 7);//绑定日期
                            String stp = end.substring(0, 7);//解绑日期或当前日期
                            if (first.equals(stp) ){
                                //获取开始日期是当月的第几天
                                int numday = GetDateUtils.getMonthday(start);
                                BigDecimal numdays = new BigDecimal(numday);
                                //获取开始日期当月的最大天数
                                int maxday = 0;
                                try {
                                    maxday = GetDateUtils.getDaysOfMonth(start);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BigDecimal maxdays = new BigDecimal(maxday);
                                //计算出天数百分比
                                BigDecimal workday = new BigDecimal(dayapart);
                                BigDecimal Percentage = workday.divide(maxdays, mc);
                                //当月天数对应的收入
                                empIncome_month = price_level.multiply(Percentage);
                                total = total.add(empIncome_month);
                                empSalaryinfo.setEmpSalary(df.format(empIncome_month.doubleValue()));
                                empSalaryinfo.setEmpLevel(level_name);
                                monthname = pcai.SetMonthValue(first, empSalaryinfo, monthname);
                                empSalaryinfo = new EmpSalaryinfo();
                                empIncome_month = new BigDecimal("0");
                            }else {
                                if(c==number){
                                    int numday = GetDateUtils.getMonthday(start);
                                    BigDecimal numdays = new BigDecimal(numday);
                                    //获取开始日期当月的最大天数
                                    int maxday = 0;
                                    try {
                                        maxday = GetDateUtils.getDaysOfMonth(start);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    BigDecimal maxdays = new BigDecimal(maxday);
                                    //计算出天数百分比
                                    BigDecimal workday = maxdays.subtract(numdays);
                                    BigDecimal Percentage = workday.divide(maxdays, mc);
                                    //当月天数对应的收入
                                    empIncome_month = price_level.multiply(Percentage);
                                    //开始月收入
                                    empSalaryinfo.setEmpSalary(df.format(empIncome_month.doubleValue()));
                                    empSalaryinfo.setEmpLevel(level_name);
                                    monthname = pcai.SetMonthValue(first, empSalaryinfo, monthname);
                                    empSalaryinfo = new EmpSalaryinfo();
                                    //计算区间收入
                                    BigDecimal Incomecycle = price_level.multiply(new BigDecimal(list_se.size()));
                                    empSalaryinfo.setEmpSalary(df.format(price_level.doubleValue()));
                                    empSalaryinfo.setEmpLevel(level_name);
                                    monthname = pcai.SetMonthListValue(list_se, empSalaryinfo, monthname);
                                    String centerdate = end.substring(0,7);
                                    monthname = pcai.SetMonthValue(centerdate, empSalaryinfo, monthname);
                                    total = total.add(Incomecycle).add(empIncome_month);
                                    empSalaryinfo = new EmpSalaryinfo();
                                    date=end;
                                }else{
                                    List<String> list_c = new ArrayList<>();
                                    try {
                                        list_c = GetDateUtils.getYearMonth(date, end);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    BigDecimal Incomecycle = new BigDecimal("0");
                                    //计算区间收入
                                    if(list_c.size()!=0){
                                        Incomecycle = price_level.multiply(new BigDecimal(list_c.size()-1));
                                    }
                                    empSalaryinfo.setEmpSalary(df.format(price_level.doubleValue()));
                                    empSalaryinfo.setEmpLevel(level_name);
                                    monthname = pcai.SetMonthListValue(list_c, empSalaryinfo, monthname);
                                    String centerdate = end.substring(0,7);
                                    monthname = pcai.SetMonthValue(centerdate, empSalaryinfo, monthname);
                                    if(list_percost.size()-1!=c){
                                        total = total.add(price_level);
                                    }
                                    total = total.add(Incomecycle);
                                    empSalaryinfo = new EmpSalaryinfo();
                                    if(list_percost.size()-1==c){
                                        //结束月
                                        int numday_end = GetDateUtils.getMonthday(end);
                                        BigDecimal numdays_end = new BigDecimal(numday_end);
                                        //获取开始日期当月的最大天数
                                        int maxday_end = 0;
                                        try {
                                            maxday_end = GetDateUtils.getDaysOfMonth(end);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        BigDecimal maxdays_end = new BigDecimal(maxday_end);
                                        //计算出天数百分比
                                        BigDecimal Percentage_end = numdays_end.divide(maxdays_end, mc);
                                        //当月天数对应的收入
                                        if(date.substring(0,7).equals(end.substring(0,7))){
                                            Itemper ite = list_percost.get(c-1);
                                            Itemperlevel itemperlevel2 = ite.getLevel();
                                            String levelIncome = itemperlevel2.getLevel_price();
                                            empIncome_month_end = new BigDecimal(levelIncome).multiply(Percentage_end);
                                            total = total.subtract(new BigDecimal(levelIncome));
                                        }else{
                                            empIncome_month_end = price_level.multiply(Percentage_end);
                                        }
                                        total = total.add(empIncome_month_end);
                                        //结束月收入
                                        empSalaryinfo.setEmpSalary(df.format(empIncome_month_end.doubleValue()));
                                        empSalaryinfo.setEmpLevel(level_name);
                                        monthname = pcai.SetMonthValue(stp, empSalaryinfo, monthname);
                                        empIncome_month_end = new BigDecimal("0");
                                        empSalaryinfo = new EmpSalaryinfo();
                                    }
                                    date=end;
                                }
                            }
                        }
                    }
                    perincome.setEmpName(employeeInfo.getEmpName());
                    perincome.setMonthname(monthname);
                    perincome.setEmpStartDate(start);
                    perincome.setEmpEndDate(end);
                    percost = ecu.selectEmpCostById(employeeInfo,item_id);
                    String cost = percost.getEmpTotalSalary();
                    if(cost==null ||cost.equals("")){
                        continue;
                    }
                    totalcost = new BigDecimal(cost);
                    empCosttotal = empCosttotal.add(totalcost);
                    empIncometotal = empIncometotal.add(total);
                    empProfitrate = totalcost.divide(total,mc);
                    perincome.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                    perincome.setEmpTotalSalary(df.format(total.doubleValue()));

                    listIncome.add(perincome);
                    total = new BigDecimal("0");
                    perincome = new Perincome();
                    monthname = new Monthname();
                    empSalaryinfo = new EmpSalaryinfo();
                    date="";
                }
            }
        }
        monthname.setJanuary(empSalaryinfo_null);
        monthname.setFebruary(empSalaryinfo_null);
        monthname.setMarch(empSalaryinfo_null);
        monthname.setApril(empSalaryinfo_null);
        monthname.setMay(empSalaryinfo_null);
        monthname.setJune(empSalaryinfo_null);
        monthname.setJuly(empSalaryinfo_null);
        monthname.setAugust(empSalaryinfo_null);
        monthname.setSeptember(empSalaryinfo_null);
        monthname.setOctober(empSalaryinfo_null);
        monthname.setNovember(empSalaryinfo_null);
        monthname.setDecember(empSalaryinfo_null);
        perincome.setEmpName("项目收入汇总");
        perincome.setMonthname(monthname);

        String startDate = item.getItem_startdate();
        String endDate =item.getItem_enddate();
        String currentdate = sdf.format(new Date());
        BigDecimal Percentage = new BigDecimal("0");
        int days = 0;
        int daysTotal =  DateDiffMonth.nDaysBetweenTwoDate(startDate,endDate);
        if(Integer.parseInt(endDate.replace("-",""))>Integer.parseInt(currentdate.replace("-",""))){
            endDate = sdf.format(new Date());
            //获取日期范围内间隔天数
            days = DateDiffMonth.nDaysBetweenTwoDate(startDate,endDate);
            BigDecimal dayspart = new BigDecimal(days);
            //计算出天数百分比
            Percentage = dayspart.divide(new BigDecimal(daysTotal), mc);
        }else{
            Percentage = new BigDecimal("1");
        }
        perincome.setEmpStartDate(item.getItem_startdate());
        perincome.setEmpEndDate(endDate);

        BigDecimal  curworkcost= new BigDecimal("0");
        //项目实施费用
        String workcost = item.getWorkcost();
        if(workcost!=null && !workcost.equals("")){
            BigDecimal Workcost = new BigDecimal(workcost);
            //截止当前日期或结束日期的项目实施费用
            curworkcost= Workcost.multiply(Percentage);
        }
        String taxrate = item.getTaxrate();
//        int res = empIncometotal.compareTo(new BigDecimal("0"));
        if(!empIncometotal.equals(new BigDecimal("0"))){
            //税务支出
            BigDecimal Grosscost = (empIncometotal.divide(((new BigDecimal("1")).add(new BigDecimal(taxrate))),mc)).multiply(new BigDecimal(taxrate)).multiply(new BigDecimal("1.12"));
            //项目毛利
            BigDecimal Grossprofit = empIncometotal.subtract(Grosscost);
            //项目毛利费用率
            BigDecimal empProjectfitrate = (empCosttotal.add(curworkcost)).divide(Grossprofit,mc);
            perincome.setEmpProfitrate(df1.format(empProjectfitrate.doubleValue()));
        }
        perincome.setEmpTotalSalary(df.format(empIncometotal.doubleValue()));
        listIncome.add(perincome);
        Item Item = new Item();
        Item.setItem_fund(empIncometotal.toString());
        Item.setItem_id(Integer.parseInt(item_id));
        //更新项目总资金
        proInfoMapper.updateProject(Item);

        return listIncome;
    }
    /**
     * 查询员工是否设置工资信息
     * @param empCost
     * @return
     */
    @Override
    public List<EmpCost> selectEmpcost(EmpCost empCost) {
        return proInfoMapper.selectEmpcost(empCost);
    }

    /**
     * 新增项目人员闲置信息
     * @return
     */
    @Override
    @Transactional
    public int insertEmpidle(Empidle empidle) {
        return proInfoMapper.insertEmpidle(empidle);
    }
    /**
     * 查询项目人员闲置信息
     * @return
     */
    @Override
    public Empidle selectEmpidle(int empid) {
        return proInfoMapper.selectEmpidle(empid);
    }


    /**
     * 修改闲置历史表
     * @return
     */
    @Override
    @Transactional
    public int updateEmpidle(Empidle empidle)  {
        return proInfoMapper.updateEmpidle(empidle);
    }
    /**
     * 根据项目ID查询项目详细信息计算人员预估成本
     * @return
     */
    @Override
    public List<Percost> selectPerCostBudgetById(String item_id) {
        List<Percost> listCost = new ArrayList<Percost>();
        Percost percost = new Percost();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        DecimalFormat df1 = new DecimalFormat("0.0000%");
        Itemper itempercome = new Itemper();
        GetDateUtils getDateUtils = new GetDateUtils();
        //外包人员收入
        BigDecimal totalEmpIncome = new BigDecimal("0");
        //单人合计
        BigDecimal TotalEmp = new BigDecimal("0");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        MathContext mc = new MathContext(6, RoundingMode.HALF_DOWN);
        EmployeeInfo employeeInfo = new EmployeeInfo();
        Item item = proInfoMapper.selectProById(item_id);
        String startDate = item.getItem_startdate();
        String endDate = item.getItem_enddate();
        String year = GetDateUtils.getSysYear();
        String start =sdf.format(getDateUtils.getCurrYearFirst(Integer.parseInt(year)));
        String end = sdf.format(getDateUtils.getCurrYearLast(Integer.parseInt(year)));

        BigDecimal TotalProject = new BigDecimal("0");
        BigDecimal empProfitrate = new BigDecimal("0");
        //获取人员
        List<EmployeeInfo> listEmp= new ArrayList<EmployeeInfo>();
        Itemperlevel itemperlevel = new Itemperlevel();
        if(item.getItem_type().equals("2")){
            List<Itemperlevel> listLevel = proInfoMapper.selectPerLevelById(item_id);
            for(int x=0;x<listLevel.size();x++){
                itemperlevel = listLevel.get(x);
                List<EmployeeInfo> listlevel = proInfoMapper.selectPerById(String.valueOf(itemperlevel.getItem_level_id()));
                for(int y=0;y<listlevel.size();y++){
                    EmployeeInfo employeeInfo1 = listlevel.get(y);
                    employeeInfo1.setItem_level_id(itemperlevel.getItem_level_id());
                    listEmp.add(employeeInfo1);
                    employeeInfo1 = new EmployeeInfo();
                }
            }
        }else {
            listEmp = proInfoMapper.selectEmpByItemId(item_id);
        }
        if(listEmp.size()>0){
            for(int i = 0; i<listEmp.size(); i++) {
                employeeInfo = listEmp.get(i);
                String empid = employeeInfo.getEmpId();
                EmpCost empcost=empCostMapper.selectByEmp(empid);
                if(empcost==null){
                    continue;
                }
                EmpCost empCost = employeeInfo.getCost();

                Double salary = empCost.getEmpSalary();
                BigDecimal emp_Salary = new BigDecimal(salary);
                BigDecimal empSalary = new BigDecimal("0");
                if(employeeInfo.getEmpState().equals("3")){
                    empSalary = emp_Salary.multiply(new BigDecimal("0.8"));
                }else{
                    empSalary = emp_Salary;
                }
                Double insurance = empCost.getEmpInsurance();
                BigDecimal empInsurance = new BigDecimal(insurance);
                Double Fund = empCost.getEmpFund();
                BigDecimal empFund = new BigDecimal(Fund);
                if(item.getItem_type().equals("2")){
                    //---------计算外包人员收入------------------
                    itempercome.setItemid(item_id);
                    itempercome.setEmpid(empid);
                    List<Itemper> list_percost = proInfoMapper.selectPercostbundling(itempercome);
                    Itemperlevel itemperlevel1 = new Itemperlevel();
                    BigDecimal totalIncome = new BigDecimal("0");
                    Itemper it = list_percost.get(list_percost.size()-1);
                    itemperlevel1 = it.getLevel();
                    String price = itemperlevel1.getLevel_price();
                    BigDecimal price_level = new BigDecimal(price);
                    totalIncome = price_level.multiply(new BigDecimal("12"));
                    TotalEmp = (empSalary.add(empInsurance).add(empFund)).multiply(new BigDecimal("12"));
                    empProfitrate = TotalEmp.divide(totalIncome,mc);
                    percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                    TotalProject = TotalProject.add(TotalEmp);
                    totalEmpIncome = totalEmpIncome.add(totalIncome);
                    percost.setEmpName(employeeInfo.getEmpName());
                    percost.setEmpStartDate(start);
                    percost.setEmpEndDate(end);
                    percost.setEmpTotalSalary(df.format(TotalEmp.doubleValue()));
                    listCost.add(percost);
                    percost = new Percost();
                }else{
                    //获取开始日期是当月的第几天
                    int numday = GetDateUtils.getMonthday(startDate);
                    BigDecimal numdays = new BigDecimal(numday);
                    //获取开始日期当月的最大天数
                    int maxday = 0;
                    try {
                        maxday = GetDateUtils.getDaysOfMonth(startDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BigDecimal maxdays = new BigDecimal(maxday);
                    //计算出天数百分比
                    BigDecimal workday = maxdays.subtract(numdays);
                    BigDecimal Percentage = workday.divide(maxdays,mc);
                    //首月天数对应的工资
                    BigDecimal empSalary_month = empSalary.multiply(Percentage);
                    //首月天数对应的五险一金
                    BigDecimal InsuranceFund_month = (empFund.add(empInsurance)).multiply(Percentage);
                    //首月五险和税前工资合计
                    BigDecimal empMonthSalary_Total = empSalary_month.add(InsuranceFund_month);

                    //获取结束日期是当月的第几天
                    int numday_end = GetDateUtils.getMonthday(endDate);
                    BigDecimal numdays_end = new BigDecimal(numday_end);
                    //获取开始日期当月的最大天数
                    int maxday_end = 0;
                    try {
                        maxday_end = GetDateUtils.getDaysOfMonth(endDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BigDecimal maxdays_end = new BigDecimal(maxday_end);
                    //计算出天数百分比
                    BigDecimal Percentage_end = numdays_end.divide(maxdays_end,mc);
                    //当月天数对应的工资
                    BigDecimal empSalary_month_end = empSalary.multiply(Percentage_end);
                    //当月天数对应的五险一金
                    BigDecimal InsuranceFund_month_end = (empFund.add(empInsurance)).multiply(Percentage_end);
                    //当月五险和税前工资合计
                    BigDecimal empMonthSalary_Total_end = empSalary_month_end.add(InsuranceFund_month_end);
                    //获取日期范围内的月份
                    List<String> list =new ArrayList<>();
                    try {
                        list = GetDateUtils.getYearMonth(startDate,endDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    BigDecimal itemcycle = new BigDecimal(list.size()-1);
                    BigDecimal fund = empFund.multiply(itemcycle);
                    BigDecimal Insurance = empInsurance.multiply(itemcycle);
                    BigDecimal empInsuranceFund = fund.add(Insurance);
                    BigDecimal Salary = empSalary.multiply(itemcycle);
                    //人员支出总合计
                    TotalEmp = Salary.add(empInsuranceFund).add(empMonthSalary_Total).add(empMonthSalary_Total_end);
                    TotalProject = TotalProject.add(TotalEmp);
                    percost.setEmpName(employeeInfo.getEmpName());
                    percost.setEmpStartDate(startDate);
                    percost.setEmpEndDate(endDate);
                    percost.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                    percost.setEmpTotalSalary(df.format(TotalEmp.doubleValue()));
                    listCost.add(percost);
                    percost = new Percost();
                }
            }
            BigDecimal  curfund= new BigDecimal("0");
            //项目总资金
            String totalfund = item.getItem_fund();
            if(!item.getItem_type().equals("2") && !totalfund.equals("0.00")){
                if(totalfund !=null && !totalfund.equals("")){
                    curfund= new BigDecimal(totalfund);
                }
                percost.setEmpStartDate(startDate);
                percost.setEmpEndDate(endDate);
            }else{
                curfund = totalEmpIncome;
                percost.setEmpStartDate(start);
                percost.setEmpEndDate(end);
            }
            BigDecimal empProjectfitrate = new BigDecimal("0");
            if(!curfund.equals(new BigDecimal("0"))) {
                BigDecimal curworkcost = new BigDecimal("0");
                //项目实施费用
                String workcost = item.getWorkcost();
                if (workcost != null && !workcost.equals("")) {
                    curworkcost = new BigDecimal(workcost);
                }
                String taxrate = item.getTaxrate();
                //税务支出
                BigDecimal Grosscost = (curfund.divide(((new BigDecimal("1")).add(new BigDecimal(taxrate))), mc)).multiply(new BigDecimal(taxrate)).multiply(new BigDecimal("1.12"));
                //项目毛利
                BigDecimal Grossprofit = curfund.subtract(Grosscost);

                //项目毛利费用率
                 empProjectfitrate = (TotalProject.add(curworkcost)).divide(Grossprofit, mc);
            }
            percost.setEmpProfitrate(df1.format(empProjectfitrate.doubleValue()));
            percost.setEmpName("项目预估合计");
            percost.setEmpTotalSalary(df.format(TotalProject.doubleValue()));
            listCost.add(percost);
        }

        return  listCost;
    }
    /**
     * 根据项目ID查询项目详细信息计算人员预估收入
     * @return
     */
    @Override
    public List<Perincome> selectPerIncomeBudgetById(String item_id) {
        List<Perincome> listIncome = new ArrayList<Perincome>();
        Perincome perincome = new Perincome();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        DecimalFormat df1 = new DecimalFormat("0.0000%");
        Itemper itempercome = new Itemper();
        GetDateUtils getDateUtils = new GetDateUtils();
        //外包人员收入
        BigDecimal totalEmpIncome = new BigDecimal("0");
        //单人合计
        BigDecimal TotalEmp = new BigDecimal("0");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        MathContext mc = new MathContext(6, RoundingMode.HALF_DOWN);
        EmployeeInfo employeeInfo = new EmployeeInfo();
        Item item = proInfoMapper.selectProById(item_id);
        String year = GetDateUtils.getSysYear();
        String start =sdf.format(getDateUtils.getCurrYearFirst(Integer.parseInt(year)));
        String end = sdf.format(getDateUtils.getCurrYearLast(Integer.parseInt(year)));

        BigDecimal TotalProject = new BigDecimal("0");
        BigDecimal empProfitrate = new BigDecimal("0");
        //获取人员
        List<EmployeeInfo> listEmp= new ArrayList<EmployeeInfo>();
        Itemperlevel itemperlevel = new Itemperlevel();
        List<Itemperlevel> listLevel = proInfoMapper.selectPerLevelById(item_id);
        for(int x=0;x<listLevel.size();x++){
            itemperlevel = listLevel.get(x);
            List<EmployeeInfo> listlevel = proInfoMapper.selectPerById(String.valueOf(itemperlevel.getItem_level_id()));
            for(int y=0;y<listlevel.size();y++){
                EmployeeInfo employeeInfo1 = listlevel.get(y);
                employeeInfo1.setItem_level_id(itemperlevel.getItem_level_id());
                listEmp.add(employeeInfo1);
                employeeInfo1 = new EmployeeInfo();
            }
        }
        if(listEmp.size()>0){
            for(int i = 0; i<listEmp.size(); i++) {
                employeeInfo = listEmp.get(i);
                String empid = employeeInfo.getEmpId();
                EmpCost empcost=empCostMapper.selectByEmp(empid);
                if(empcost==null){
                    continue;
                }
                EmpCost empCost = employeeInfo.getCost();

                Double salary = empCost.getEmpSalary();
                BigDecimal emp_Salary = new BigDecimal(salary);
                BigDecimal empSalary = new BigDecimal("0");
                if(employeeInfo.getEmpState().equals("3")){
                    empSalary = emp_Salary.multiply(new BigDecimal("0.8"));
                }else{
                    empSalary = emp_Salary;
                }
                Double insurance = empCost.getEmpInsurance();
                BigDecimal empInsurance = new BigDecimal(insurance);
                Double Fund = empCost.getEmpFund();
                BigDecimal empFund = new BigDecimal(Fund);
                //---------计算外包人员收入------------------
                itempercome.setItemid(item_id);
                itempercome.setEmpid(empid);
                List<Itemper> list_percost = proInfoMapper.selectPercostbundling(itempercome);
                Itemperlevel itemperlevel1 = new Itemperlevel();
                BigDecimal totalIncome = new BigDecimal("0");
                Itemper it = list_percost.get(list_percost.size()-1);
                itemperlevel1 = it.getLevel();
                String price = itemperlevel1.getLevel_price();
                BigDecimal price_level = new BigDecimal(price);
                totalIncome = price_level.multiply(new BigDecimal("12"));
                TotalEmp = (empSalary.add(empInsurance).add(empFund)).multiply(new BigDecimal("12"));
                empProfitrate = TotalEmp.divide(totalIncome,mc);
                perincome.setEmpProfitrate(df1.format(empProfitrate.doubleValue()));
                TotalProject = TotalProject.add(TotalEmp);
                totalEmpIncome = totalEmpIncome.add(totalIncome);
                perincome.setEmpName(employeeInfo.getEmpName());
                perincome.setEmpStartDate(start);
                perincome.setEmpEndDate(end);
                perincome.setEmpTotalSalary(df.format(totalIncome.doubleValue()));
                listIncome.add(perincome);
                perincome = new Perincome();
            }
            BigDecimal  curfund= new BigDecimal("0");
            //项目总资金
            curfund = totalEmpIncome;
            perincome.setEmpStartDate(start);
            perincome.setEmpEndDate(end);
            BigDecimal  curworkcost= new BigDecimal("0");
            //项目实施费用
            String workcost = item.getWorkcost();
            if(workcost!=null && !workcost.equals("")){
                curworkcost= new BigDecimal(workcost);
            }
            String taxrate = item.getTaxrate();
            //税务支出
            BigDecimal Grosscost = (curfund.divide(((new BigDecimal("1")).add(new BigDecimal(taxrate))),mc)).multiply(new BigDecimal(taxrate)).multiply(new BigDecimal("1.12"));
            //项目毛利
            BigDecimal Grossprofit = curfund.subtract(Grosscost);

            //项目毛利费用率
            BigDecimal empProjectfitrate = (TotalProject.add(curworkcost)).divide(Grossprofit,mc);
            perincome.setEmpProfitrate(df1.format(empProjectfitrate.doubleValue()));
            perincome.setEmpName("项目预估合计");
            perincome.setEmpTotalSalary(df.format(totalEmpIncome.doubleValue()));
            listIncome.add(perincome);
        }
        return  listIncome;
    }
    /**
     * 查询级别变更信息
     */
    @Override
    public List<LevelChangeInfo> getLevelChangeInfo() {
        LevelChangeInfo levelChangeInfo = new LevelChangeInfo();
        levelChangeInfo.setLcstatus("0");
        return proInfoMapper.getLevelChangeInfo(levelChangeInfo);
    }
    /**
     * 查询级别变更历史
     */
    @Override
    public List<LevelChangeInfo> getLevelChangeHisInfo() {
        LevelChangeInfo levelChangeInfo = new LevelChangeInfo();
        levelChangeInfo.setLcstatus("1");
        return proInfoMapper.getLevelChangeInfo(levelChangeInfo);
    }
    /**
     * 查询外包员工绑定人数
     * @return
     */
    @Override
    public Boolean selectEmpNum(Itemper Itemper) {
        Itemper itemper1 = new Itemper();
        itemper1.setDelflag("0");
        itemper1.setItemid(Itemper.getItemid());
        itemper1.setItem_level_id(Itemper.getItem_level_id());
        List<Itemper> list = proInfoMapper.selectIsBindEmpList(itemper1);
        Itemperlevel itemperlevel = new Itemperlevel();
        itemperlevel.setItem_id(Itemper.getItemid());
        itemperlevel.setItem_level_id(Itemper.getItem_level_id());
        Itemperlevel itemperlevel1 = proInfoMapper.selectLevelnum(itemperlevel);
        String levelnum = itemperlevel1.getLevel_num();
        Boolean b = false;
        if(Integer.parseInt(levelnum)>list.size()){
            return b;
        }else {
            b = true;
            return b;
        }
    }

    @Override
    public List<Itemper> listDataBindingEmp(Itemper itemper) {
        return proInfoMapper.selectIsBindEmpList(itemper);
    }
}