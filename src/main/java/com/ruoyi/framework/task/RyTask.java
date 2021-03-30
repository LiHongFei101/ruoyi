package com.ruoyi.framework.task;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.employee.domain.EducationInfo;
import com.ruoyi.project.employee.mapper.EducationInfoMapper;
import com.ruoyi.project.employee.mapper.EmployeeInfoMapper;
import com.ruoyi.project.project.domain.Itemper;
import com.ruoyi.project.project.domain.Itemperlevel;
import com.ruoyi.project.project.domain.LevelChangeInfo;
import com.ruoyi.project.project.mapper.ProInfoMapper;
import com.ruoyi.project.tool.gen.util.DateDiffMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask {
    @Autowired
    private ProInfoMapper proInfoMapper;
    @Autowired
    private EducationInfoMapper educationInfoMapper;

    public static RyTask ryTask;
    @PostConstruct
    public void init(){
        ryTask = this;
    }
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }
    @Transactional
    public void ryNoParams() {
        Itemper itemper = new Itemper();
        EducationInfo educationInfo = new EducationInfo();
        Itemperlevel itemperlevel = new Itemperlevel();
        LevelChangeInfo lci = new LevelChangeInfo();
        DateDiffMonth ddm = new DateDiffMonth();
        List<EducationInfo> list_edu = new ArrayList<EducationInfo>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String createdate = sdf.format(new Date());//创建日期
        String empId = "";
        String item_level_id = "";
        String levelname = "";
        String startdate = "";//绑定日期
        String edugraduation = "";//毕业日期
        long month = 1L;
        String itemid = "";
        System.out.println("===============执行定时任务开始==================");
        List<Itemper> list = proInfoMapper.selectBinding();
        if(list.size()>0){
        for (int i = 0; i < list.size(); i++) {
            itemper = list.get(i);
            startdate = itemper.getStartdate();
            empId = itemper.getEmpid();
            itemid = itemper.getItemid();
            list_edu = educationInfoMapper.selectByEmp(String.valueOf(empId));
            item_level_id = itemper.getItem_level_id();
            itemperlevel = proInfoMapper.selectLevelById(String.valueOf(item_level_id));
            levelname = itemperlevel.getLevel_name();//级别名称
            if (levelname.equals("3")) {
                if(list_edu.size()>0) {
                    for (int x = 0; x < list_edu.size(); x++) {
                        educationInfo = list_edu.get(x);
                        if (educationInfo.getEmpEdu().equals("5") || educationInfo.getEmpEdu().equals("6")) {
                            continue;
                        } else if (educationInfo.getEmpEdu().equals("4")) {
                            edugraduation = educationInfo.getEduGraduation();
                            try {
                                month = ddm.getMonthDiff(edugraduation, startdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int num = (int) month;
                            if (num >= 23) {
                                lci.setEmp_id(empId);
                                lci.setItem_level_id(item_level_id);
                                lci.setCreatedate(createdate);
                                lci.setDel_flag("0");
                                lci.setItem_id(itemid);
                                lci.setLcstatus("0");
                                lci.setEmp_edu(educationInfo.getEmpEdu());
                                lci.setEdu_graduation(educationInfo.getEduGraduation());
                                lci.setMessage("此员工已满足级别调整条件，请申请更新级别！");
                                System.out.println("===============入级别变更消息表开始==================");
                                proInfoMapper.addLevelChangeInfo(lci);
                                System.out.println("===============入级别变更消息表结束==================");
                            }
                        } else if (educationInfo.getEmpEdu().equals("3")) {
                            edugraduation = educationInfo.getEduGraduation();
                            try {
                                month = ddm.getMonthDiff(edugraduation, startdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int num = (int) month;
                            if (num >= 11) {
                                lci.setEmp_id(empId);
                                lci.setItem_level_id(item_level_id);
                                lci.setCreatedate(createdate);
                                lci.setDel_flag("0");
                                lci.setItem_id(itemid);
                                lci.setEmp_edu(educationInfo.getEmpEdu());
                                lci.setEdu_graduation(educationInfo.getEduGraduation());
                                lci.setLcstatus("0");
                                lci.setMessage("此员工已满足级别调整条件，请申请更新级别！");
                                System.out.println("===============入级别变更消息表开始==================");
                                proInfoMapper.addLevelChangeInfo(lci);
                                System.out.println("===============入级别变更消息表结束==================");
                            }
                        }
                    }
                }
            } else if (levelname.equals("2")) {
                if(list_edu.size()>0) {
                    for (int x = 0; x < list_edu.size(); x++) {
                        educationInfo = list_edu.get(x);
                        if (educationInfo.getEmpEdu().equals("5") || educationInfo.getEmpEdu().equals("6")) {
                            continue;
                        } else if (educationInfo.getEmpEdu().equals("4")) {
                            edugraduation = educationInfo.getEduGraduation();
                            try {
                                month = ddm.getMonthDiff(edugraduation, startdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int num = (int) month;
                            if (num >= 47) {
                                lci.setEmp_id(empId);
                                lci.setItem_level_id(item_level_id);
                                lci.setCreatedate(createdate);
                                lci.setDel_flag("0");
                                lci.setItem_id(itemid);
                                lci.setEmp_edu(educationInfo.getEmpEdu());
                                lci.setEdu_graduation(educationInfo.getEduGraduation());
                                lci.setLcstatus("0");
                                lci.setMessage("此员工已满足级别调整条件，请申请更新级别！");
                                System.out.println("===============入级别变更消息表开始==================");
                                proInfoMapper.addLevelChangeInfo(lci);
                                System.out.println("===============入级别变更消息表结束==================");
                            }
                        } else if (educationInfo.getEmpEdu().equals("3")) {
                            edugraduation = educationInfo.getEduGraduation();
                            try {
                                month = ddm.getMonthDiff(edugraduation, startdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int num = (int) month;
                            if (num >= 35) {
                                lci.setEmp_id(empId);
                                lci.setItem_level_id(item_level_id);
                                lci.setCreatedate(createdate);
                                lci.setDel_flag("0");
                                lci.setItem_id(itemid);
                                lci.setEmp_edu(educationInfo.getEmpEdu());
                                lci.setEdu_graduation(educationInfo.getEduGraduation());
                                lci.setLcstatus("0");
                                lci.setMessage("此员工已满足级别调整条件，请申请更新级别！");
                                System.out.println("===============入级别变更消息表开始==================");
                                proInfoMapper.addLevelChangeInfo(lci);
                                System.out.println("===============入级别变更消息表结束==================");
                            }
                        } else if (educationInfo.getEmpEdu().equals("2")) {
                            edugraduation = educationInfo.getEduGraduation();
                            try {
                                month = ddm.getMonthDiff(edugraduation, startdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int num = (int) month;
                            if (num >= 23) {
                                lci.setEmp_id(empId);
                                lci.setItem_level_id(item_level_id);
                                lci.setCreatedate(createdate);
                                lci.setDel_flag("0");
                                lci.setItem_id(itemid);
                                lci.setEmp_edu(educationInfo.getEmpEdu());
                                lci.setEdu_graduation(educationInfo.getEduGraduation());
                                lci.setLcstatus("0");
                                lci.setMessage("此员工已满足级别调整条件，请申请更新级别！");
                                System.out.println("===============入级别变更消息表开始==================");
                                proInfoMapper.addLevelChangeInfo(lci);
                                System.out.println("===============入级别变更消息表结束==================");
                            }
                        }
                    }
                }
            } else if (levelname.equals("1")) {
                if(list_edu.size()>0) {
                    for (int x = 0; x < list_edu.size(); x++) {
                        educationInfo = list_edu.get(x);
                        if (educationInfo.getEmpEdu().equals("5") || educationInfo.getEmpEdu().equals("6")) {
                            continue;
                        } else if (educationInfo.getEmpEdu().equals("4")) {
                            edugraduation = educationInfo.getEduGraduation();
                            try {
                                month = ddm.getMonthDiff(edugraduation, startdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int num = (int) month;
                            if (num >= 71) {
                                lci.setEmp_id(empId);
                                lci.setItem_level_id(item_level_id);
                                lci.setCreatedate(createdate);
                                lci.setDel_flag("0");
                                lci.setItem_id(itemid);
                                lci.setEmp_edu(educationInfo.getEmpEdu());
                                lci.setEdu_graduation(educationInfo.getEduGraduation());
                                lci.setLcstatus("0");
                                lci.setMessage("此员工已满足级别调整条件，请申请更新级别！");
                                System.out.println("===============入级别变更消息表开始==================");
                                proInfoMapper.addLevelChangeInfo(lci);
                                System.out.println("===============入级别变更消息表结束==================");
                            }
                        } else if (educationInfo.getEmpEdu().equals("3")) {
                            edugraduation = educationInfo.getEduGraduation();
                            try {
                                month = ddm.getMonthDiff(edugraduation, startdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int num = (int) month;
                            if (num >= 59) {
                                lci.setEmp_id(empId);
                                lci.setItem_level_id(item_level_id);
                                lci.setCreatedate(createdate);
                                lci.setDel_flag("0");
                                lci.setItem_id(itemid);
                                lci.setEmp_edu(educationInfo.getEmpEdu());
                                lci.setEdu_graduation(educationInfo.getEduGraduation());
                                lci.setLcstatus("0");
                                lci.setMessage("此员工已满足级别调整条件，请申请更新级别！");
                                System.out.println("===============入级别变更消息表开始==================");
                                proInfoMapper.addLevelChangeInfo(lci);
                                System.out.println("===============入级别变更消息表结束==================");
                            }
                        } else if (educationInfo.getEmpEdu().equals("2")) {
                            edugraduation = educationInfo.getEduGraduation();
                            try {
                                month = ddm.getMonthDiff(edugraduation, startdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int num = (int) month;
                            if (num >= 47) {
                                lci.setEmp_id(empId);
                                lci.setItem_level_id(item_level_id);
                                lci.setCreatedate(createdate);
                                lci.setDel_flag("0");
                                lci.setItem_id(itemid);
                                lci.setEmp_edu(educationInfo.getEmpEdu());
                                lci.setEdu_graduation(educationInfo.getEduGraduation());
                                lci.setLcstatus("0");
                                lci.setMessage("此员工已满足级别调整条件，请申请更新级别！");
                                System.out.println("===============入级别变更消息表开始==================");
                                proInfoMapper.addLevelChangeInfo(lci);
                                System.out.println("===============入级别变更消息表结束==================");
                            }
                        }
                    }
                }
            }

        }
    }
        System.out.println("===============执行定时任务结束==================");
    }
}
