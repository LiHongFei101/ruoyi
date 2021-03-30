package com.ruoyi.common.utils.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.framework.config.RuoYiConfig;

/**
 * 获取地址类
 * 
 * @author ruoyi
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

//    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    public static String getRealAddressByIP(String ip)
    {
        boolean json =true;
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip))
        {
            return "内网IP";
        }
        if (RuoYiConfig.isAddressEnabled())
        {
            String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip,"&json="+json);
            if (StringUtils.isEmpty(rspStr))
            {
                log.error("获取地理位置异常 {}", ip);
                return address;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
//            JSONObject data = obj.getObject("data", JSONObject.class);
//            String region = obj.getString("region");
//            String city = obj.getString("city");
            String addr = obj.getString("addr");
//            address = region + " " + city;
            address = addr;
        }
        return address;
    }
}
