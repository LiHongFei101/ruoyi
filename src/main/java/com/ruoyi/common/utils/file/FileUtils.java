package com.ruoyi.common.utils.file;

import com.ruoyi.common.exception.BaseException;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.framework.config.RuoYiConfig;

import java.io.*;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;

/**
 * 文件处理工具类
 * 
 * @author ruoyi
 */
public class FileUtils
{
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename)
    {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     *
     * @param request 请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException
    {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE"))
        {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        }
        else if (agent.contains("Firefox"))
        {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        }
        else if (agent.contains("Chrome"))
        {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        else
        {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 将目录打包压缩成zip
     * @param sourceFilePath
     * @param zipFilePath
     * @param fileName
     * @return
     * @throws Exception
     */
    public static File pathToZip(String sourceFilePath, String zipFilePath,String fileName) throws Exception{
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        if(!sourceFile.exists()){
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");
            return null;
        }
        File zipPath = new File(zipFilePath);
        if(!zipPath.exists()){
            zipPath.mkdirs();
        }
        File zipFile = new File(zipFilePath + "/" + fileName +".zip");
        if(zipFile.exists()){
            zipFile.delete();
        }
        File[] sourceFiles = sourceFile.listFiles();
        if(null == sourceFiles || sourceFiles.length<1) {
            System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
            return null;
        }
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
            byte[] bufs = new byte[1024*10];
            for(int i=0;i<sourceFiles.length;i++){
                //创建ZIP实体，并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                zos.putNextEntry(zipEntry);
                //读取待压缩的文件并写进压缩包里
                fis = new FileInputStream(sourceFiles[i]);
                bis = new BufferedInputStream(fis, 1024*10);
                int read = 0;
                while((read=bis.read(bufs, 0, 1024*10)) != -1){
                    zos.write(bufs,0,read);
                }
            }

        }catch (Exception e){
            throw e;
        }finally{
            //关闭流
            try {
                if(null != bis) bis.close();
                if(null != zos) zos.close();
            } catch (IOException e) {
            }
        }
        return zipFile;
    }

    /**
     * zip解压
     * @param srcFile        zip源文件
     * @param destDirPath     解压后的目标文件夹
     * @throws RuntimeException 解压失败会抛出运行时异常
     */
    public static void unZip(File srcFile, String destDirPath) throws BaseException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new BaseException(srcFile.getPath() + "所指文件不存在");
        }
        String filePath = srcFile.getPath();
        if ("zip".equals(filePath.substring(filePath.lastIndexOf("1")+1))){
            throw new BaseException("压缩包格式不正确，格式必须为 zip。");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if(!targetFile.getParentFile().exists()){
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[BUFFER_SIZE];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("解压完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new BaseException("解压失败，请稍后重试！");
        } finally {
            if(zipFile != null){
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

     /*public static void main(String[] args){
        File zipPath = new File("D:/ruoyi/uploadPath/upload/empElement.zip");
        String destPath =RuoYiConfig.getUploadPath()+ "/"+ IdUtils.simpleUUID().substring(0,10);
        unZip(zipPath, destPath);
     }*/


}
