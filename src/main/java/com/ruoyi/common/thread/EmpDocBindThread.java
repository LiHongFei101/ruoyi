package com.ruoyi.common.thread;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.employee.service.DocBindBatchService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zyd on 2020/8/7.
 */
public class EmpDocBindThread implements Runnable {

    //单线程，线程池
    private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    private static ThreadLocal<EmpDocBindThread> map = new ThreadLocal<EmpDocBindThread>();

    private DocBindBatchService service;

    private String userId;

    private EmpDocBindThread(DocBindBatchService service, String userId){
        this.service = service;
        this.userId = userId;
    }

    @Override
    public void run() {
        service.autoBindDocToEmp(userId);
    }

    private static EmpDocBindThread getEmpDocBindThread(DocBindBatchService service, String userId){
        EmpDocBindThread thread = map.get();
        if(StringUtils.isNull(thread)){
            thread = new EmpDocBindThread(service, userId);
            map.set(thread);
        }
        return thread;
    }

    public static void start(DocBindBatchService service, String userId){
        singleThreadExecutor.execute(getEmpDocBindThread(service, userId));
    }



}
