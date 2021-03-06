package com.ruoyi.framework.web.page;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 * 
 * @author ruoyi
 */
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    //private List<?> rows;
    private Object rows;

    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private int msg;

    /** 是否加密 */
    private boolean isEncrypt = false;

    /**
     * 表格数据对象
     */
    public TableDataInfo()
    {
    }

    /**
     * 分页
     * 
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, int total)
    {
        this.rows = (Object) list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public Object getRows()
    {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public int getMsg()
    {
        return msg;
    }

    public void setMsg(int msg)
    {
        this.msg = msg;
    }

    public boolean getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(boolean isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

}