package com.ruoyi.framework.aspectj;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.AesEncryptUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 结果集加密
 * 
 * @author zyd
 */
@Aspect
@Component
public class EncryptAspect {

    @Value("${token.header}")
    private String header;

    private static final Logger log = LoggerFactory.getLogger(EncryptAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.ruoyi.framework.aspectj.lang.annotation.Encrypt)")
    public void securityPointCut() {}



    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "securityPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {

        HttpServletRequest request =  ServletUtils.getRequest();
        try{
            String token = request.getHeader(header);
            if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
                token = token.replace(Constants.TOKEN_PREFIX, "");
                if(jsonResult instanceof TableDataInfo){
                    TableDataInfo res = (TableDataInfo)jsonResult;
                    JSONObject json = JSONObject.fromObject(res);
                    String resDataStr = json.getString("rows");
//                    String resDataStr = JSON.toJSONString(res.getRows());
                    if(StringUtils.isNotBlank(resDataStr)){
                        String securityStr = AesEncryptUtils.encrypt(resDataStr, token.substring(0,16));
                        res.setRows(securityStr);
                        res.setIsEncrypt(true);
                        System.out.println(securityStr);
                    }
                }else if(jsonResult instanceof AjaxResult){
                    AjaxResult res = (AjaxResult)jsonResult;
                    JSONObject json = JSONObject.fromObject(res);
                    String resDataStr =json.getString(AjaxResult.DATA_TAG);
//                    String resDataStr = JSON.toJSONString(res.get(AjaxResult.DATA_TAG));
                    if(StringUtils.isNotBlank(resDataStr)){
                        String securityStr = AesEncryptUtils.encrypt(resDataStr, token.substring(0,16));
                        res.put(AjaxResult.DATA_TAG, securityStr);
                        res.put("isEncrypt",true);
                        System.out.println(securityStr);
                    }
                }else if(jsonResult instanceof String){
                    jsonResult = AesEncryptUtils.encrypt(String.valueOf(jsonResult), token.substring(0,16));
                }


            }else {
                return;
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        System.out.println(jsonResult.getClass());
    }


}
