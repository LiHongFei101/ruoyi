package com.ruoyi.project.project.controller;


import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;

import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.project.domain.FileInfo;
import com.ruoyi.project.project.domain.Item;
import com.ruoyi.project.project.service.UploadService;
import com.ruoyi.project.system.domain.SysUser;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

/**
 * 用户信息
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/file/load")
public class UploadController extends BaseController
{
    @Autowired
    private UploadService uploadService;
    @Autowired
    private TokenService tokenService;
    String filePath = "d:\\upload\\";

    @Log(title = "文件上传", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/upload")
    public String upload (@RequestParam("file") MultipartFile file,int item_id) {
        System.out.println("---------------开始进行文件上传---------------" );
        FileInfo fileInfo=new FileInfo();
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        fileInfo.setFilename(fileName);
        fileInfo.setItem_id(item_id);
        // 获取后缀名
        // String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件保存路径

        // 文件重命名，防止重复
        fileName = filePath + UUID.randomUUID() + fileName;
        fileInfo.setUuidname(fileName);
        // 文件对象
        File dest = new File(fileName);
        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            System.out.println("---------------文件上传成功---------------" );
            //保存文件信息


            uploadService.uploadFile(fileInfo);
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }







    @Log(title = "文件下载", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @GetMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletResponse response) throws Exception {
        System.out.println("---------------开始进行文件下载---------------" );




        // 文件地址，真实环境是存放在数据库中的
        File file = new File("D:\\upload\\a.txt");
        // 穿件输入对象
        FileInputStream fis = new FileInputStream(file);
        // 设置相关格式
        //response.setContentType("multipart/form-data;charset=utf-8");
        // 设置下载后的文件名以及header
        //response.addHeader("Content-disposition", "attachment;fileName=" + "a.txt");
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        //response.setCharacterEncoding("UTF-8");
        // 创建输出对象
        //OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment","D:\\upload\\a.txt");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        System.out.println("---------------文件下载结束---------------" );
        return new ResponseEntity<byte[]>( buf, headers, HttpStatus.CREATED);



    }


    /**
     * 查询文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping("/fileList")
    public TableDataInfo list(FileInfo fileInfo)
    {
        System.out.println("---------------查询项目信息开始---------------" +fileInfo.getItem_id());
        startPage();

        List<FileInfo> list =uploadService.selectFileList(fileInfo);
        System.out.println("---------------查询项目信息开始---------------" );
        return getDataTable(list);
    }

    /**
     * 删除项目基本信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fileId}")
    public AjaxResult remove(@PathVariable int[] fileId) throws SchedulerException, TaskException
    {
        System.out.println("---------------删除项目信息开始---------------" );
        //查询项目信息
        FileInfo fileInfo=new FileInfo();
        fileInfo.setId(fileId[0]);
        List<FileInfo> list =uploadService.selectFileList(fileInfo);
        //根据uuidname删除文件
        this.deleteFiles(new File(filePath+list.get(0).getUuidname()));

        //删除数据库文件信息
        uploadService.deleteFileByIds(fileInfo);

        System.out.println("---------------删除项目信息结束---------------" );
        return AjaxResult.success();
    }

    /*
     * 删除整个文件夹：
     *
     * 当为目录时，递归调用删除子文件
     *                      最后删除自己
     * 当为文件执行最后一行，直接删除，
     */
    public void deleteFiles(File file){
        if (file.isDirectory()) {
            File[] files=file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteFiles(files[i]);
                }else {
                    System.out.println("里层文件："+files[i].getName()+"--------"+files[i].delete());
                }
            }
        }
        System.out.println("file.isFile()："+file.isFile());
        System.out.println("file.exists()："+file.exists());
        System.out.println("外层文件："+file.getName()+"----"+file.getPath()+"----"+file.delete());
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<Item> util = new ExcelUtil<Item>(Item.class);
        return util.importTemplateExcel("项目数据");
    }
    @Log(title = "项目管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('module:item:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Item> util = new ExcelUtil<Item>(Item.class);
        List<Item> itemList = util.importExcel(file.getInputStream());
        String message = uploadService.importItem(itemList, updateSupport);
        return AjaxResult.success(message);
    }

}