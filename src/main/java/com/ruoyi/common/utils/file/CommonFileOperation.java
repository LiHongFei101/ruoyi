package com.ruoyi.common.utils.file;

import java.io.*;


/**
 * Created by lenovo on 2020/7/30.
 */
public class CommonFileOperation {

    /**
     * 对文件进行加密
     * 加密规则，对文件前1kb大小进行二进制取反操作，对小于1kb的文件全部取反
     * @param file 待加密文件
     * @param keepSourceFile 是否保留源文件
     * @return file 加密后的文件，若keepSourceFile==true，则文件名=[源文件名]-encr.[文件格式]
     * @throws Exception
     */
    public static File encryptFile(File file,boolean keepSourceFile) throws Exception{

        File retFile=file;
        if(keepSourceFile){
            String fileName=file.getName();
            String fileFormat=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")):"";
            fileName=fileName.indexOf(".")!=-1?fileName.substring(0,fileName.lastIndexOf(".")):fileName;
            fileName+="-encr";
            File targetFile=new File(file.getParent()+File.separator+fileName+fileFormat);
            copyFile(file, targetFile);
            retFile=targetFile;
        }
        encryptF(retFile);
        return retFile;
    }

    /**
     * 对文件进行加密，不保留源文件
     * 加密规则，对文件前1kb大小进行二进制取反操作，对小于1kb的文件全部取反
     * @param file 待加密文件
     * @return file 加密后的文件
     * @throws Exception
     */
    public static File encryptFile(File file) throws Exception{
        return encryptFile(file,false);
    }

    /**
     * 对文件进行解密
     * 加密与解密方法一致
     * @param file 待解密文件
     * @param keepSourceFile 是否保留源文件
     * @return file 解密后的文件，若keepSourceFile==true，则文件名=[源文件名]-decr.[文件格式]
     * @throws Exception
     */
    public static File decryptFile(File file,boolean keepSourceFile) throws Exception{

        File retFile=file;
        if(keepSourceFile){
            String fileName=file.getName();
            String fileFormat=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")):"";
            fileName=fileName.indexOf(".")!=-1?fileName.substring(0,fileName.lastIndexOf(".")):fileName;
            if(fileName.endsWith("-encr")){
                fileName=fileName.substring(0,fileName.length()-5);
            }
            fileName+="-decr";
            File targetFile=new File(file.getParent()+File.separator+fileName+fileFormat);
            copyFile(file, targetFile);
            retFile=targetFile;
        }
        encryptF(retFile);
        return retFile;
    }

    /**
     * 对文件进行取反操作
     * @param file 传入文件
     * @throws Exception 异常
     */
    private static void encryptF(File file) throws Exception{
        byte bts[]=new byte[1024];
        RandomAccessFile raf=new RandomAccessFile(file, "rw");
        int readLength=raf.read(bts);
        raf.seek(0L);
        getAntiBytes(bts,readLength);
        raf.write(bts, 0, readLength);
        raf.close();
        bts=null;
    }

    /**
     * 位数组的取反操作
     * @param bytes 输入位数组
     * @param len 取反数组的长度
     */
    static void getAntiBytes(byte[] bytes,int len){
        getAntiBytes(bytes,0,len);
    }

    /**
     * 位数组的取反操作
     * @param bytes 输入位数组
     * @param off 数组起始位置
     * @param len 取反数组的长度
     */
    static void getAntiBytes(byte[] bytes,int off,int len){
        len=Math.min(bytes.length, off+len);
        for(int i=off;i<len;i++){
            bytes[i]=(byte) ~bytes[i];
        }

    }


    /**
     * 复制文件
     *
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) {
        try {
            if (!sourceFile.exists()) {
                System.out.println("源文件不存在！已跳过！");
                return;
            }
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            // 新建文件输入流并对它进行缓冲
            FileInputStream input = new FileInputStream(sourceFile);
            BufferedInputStream inBuff = new BufferedInputStream(input);

            // 新建文件输出流并对它进行缓冲
            FileOutputStream output = new FileOutputStream(targetFile);
            BufferedOutputStream outBuff = new BufferedOutputStream(output);

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();

            // 关闭流
            inBuff.close();
            outBuff.close();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
