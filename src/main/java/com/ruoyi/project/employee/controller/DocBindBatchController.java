package com.ruoyi.project.employee.controller;

import com.ruoyi.common.thread.EmpDocBindThread;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.employee.domain.DocBindBatch;
import com.ruoyi.project.employee.service.DocBindBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lenovo on 2020/8/11.
 */
@RestController
@RequestMapping("/employee/batch")
public class DocBindBatchController extends BaseController {

    @Autowired
    DocBindBatchService docBindBatchService;

    @GetMapping(value = "/zipList")
    public AjaxResult selectZipName(){
        return AjaxResult.success(docBindBatchService.selectAllZip());
    }

    @GetMapping(value = "/bindZipList")
    public AjaxResult getBindZip(){
        return AjaxResult.success(docBindBatchService.getBindZip());
    }

    @GetMapping(value = "/batchList")
    public AjaxResult select(DocBindBatch docBindBatch){
        return AjaxResult.success(docBindBatchService.selectByWhere(docBindBatch));
    }

    @GetMapping("/bind")
    public AjaxResult autoBindDocToEmp(){
        String userId = String.valueOf(SecurityUtils.getLoginUser().getUser().getUserId());
        EmpDocBindThread.start(docBindBatchService, userId);
        return AjaxResult.success("附件开始挂接，请稍后查看结果。");
    }

    @DeleteMapping(value = "/delete/{id}")
    public AjaxResult deleteBindDocResult(@PathVariable String id){
        return toAjax(docBindBatchService.deleteByPrimaryKey(id));
    }

}
