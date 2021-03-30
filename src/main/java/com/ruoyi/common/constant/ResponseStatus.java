package com.ruoyi.common.constant;

/**
 * @ClassName: ResponseStatus
 * @Author: cj
 * @Date: 2020-03-19 17:53
 * @Description: ${description}
 */
public enum ResponseStatus {

    /**
     * @Author cj
     * @Date 2020-03-19 18:01
     * @Description 成功
     */
    SUCCESS(200, "SUCCESS"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:01
     * @Description 系统异常
     */
    SYSTEM_ERROR(500, "SYSTEM ERROR"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:03
     * @Description 限流
     */
    RATE_LIMIT(429, "RATE LIMIT"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:05
     * @Description 请求资源不存在
     */
    NOT_FOUND(500, "NOT FOUND"),

    /**
     * @Author cj
     * @Date 2020-03-19 18:05
     * @Description 无效参数
     */
    PARAM_INVALID(1000, "PARAM INVALID"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:07
     * @Description 密码不正确
     */
    PASSWORD_INVALID(1001, "PASSWORD INVALID"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:07
     * @Description 验证码验证失败
     */
    VERIFICATION_CODE_FAILURE(1002, "VERIFICATION CODE FAILURE"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:08
     * @Description
     */
    ACCOUNT_UNAUTHENTICATION(1003, "ACCOUNT UNAUTHENTICATION"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:08
     * @Description 账号被锁定
     */
    ACCOUNT_LOCKING(1004, "ACCOUNT LOCKING"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:10
     * @Description 账号被禁用
     */
    ACCOUNT_DISABLED(1005, "ACCOUNT DISABLED"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:10
     * @Description 账号已注销
     */
    ACCOUNT_CANCELLATION(1006, "ACCOUNT CANCELLATION"),

    /**
     * @Author cj
     * @Date 2020-03-19 18:11
     * @Description 账号不存在
     */
    ACCOUNT_NOT_EXIST(1007, "ACCOUNT NOT EXIST"),

    /**
     * @Author cj
     * @Date 2020-03-19 18:11
     * @Description 账号已存在
     */
    ACCOUNT_EXIST(1008, "ACCOUNT EXIST"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:12
     * @Description TOKEN 无效
     */
    TOKEN_INVALID(1009, "TOKEN INVALID"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 时间戳不正确
     */
    TIME_INVALID(1010, "TIME INVALID"),

    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 没有权限
     */
    NO_AUTHORITY(1011, "NO AUTHORITY"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 未登陆
     */
    USER_LOGOUT(1012, "USER LOGOUT"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 手机验证码发送失败
     */
    PHONE_VERIFICATION_SEND_ERROR(1013, "PHONE VERIFICATION SEND ERROR"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 未同意协议
     */
    DISAGREE_PROTOCOL(1014, "DISAGREE PROTOCOL"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 已认证不能修改
     */
    MODIFICATION_PROHIBITED_DUE_TO_CERTIFICATION(1015, "MODIFICATION PROHIBITED DUE TO CERTIFICATION"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 新旧密码相同
     */
    SAME_OLD_AND_NEW(1016, "SAME OLD AND NEW PASSWORDS"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 密码不符合规范
     */
    PASSWORD_DOES_NOT_MEET(1017, "PASSWORD DOES NOT MEET SPECIFICATIONS"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 不支持的短信服务商
     */
    MOBILE_DOES_NOT_MATCH(1018, "MOBILE DOES NOT MATCH ACCOUNT"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 验证码失效
     */
    VERIFICATION_INVALID(1019, "VERIFICATION INVALID"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 新购订单重复
     */
    ORDER_NEW_DUPLICATE(1020, "NEW ORDER DUPLICATE"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 重复短信模板
     */
    SMS_TEMPLATE_DUPLICATE(1021, "SMS TEMPLATE DUPLICATE"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 没有合适的短信服务
     */
    SMS_NO_SUITABLE_INFO(1022, "NO SUITABLE SMS INFO"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 重复短信模板
     */
    SMS_SIGN_DUPLICATE(1023, "SMS SIGN TYPE DUPLICATE"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 短信服务商重复
     */
    SMS_DUPLICATE(1024, "SMS DUPLICATE"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 短信服务商重复
     */
    SMS_IS_THE_DEFAULT(1025, "SMS IS THE DEFAULT"),
    /**
     * @Author cj
     * @Date  2020-03-19 18:13
     * @Description 验证码失效
     */
    OSS_FILE_NOT_NULL(1100, "OSS FILE NOT NULL"),

    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 短信服务商不存在
     */
    SMS_PROVIDER_NOT_EXIST(1101, "SMS PROVIDER DOES NOT EXIST"),
    /**
     * @Author cj
     * @Date 2020-03-19 18:13
     * @Description 短信发送失败
     */
    SMS_SEND_FAILURE(1102, "SMS SEND FAILURE");;


    private int code;

    private String msg;

    public int code() {
        return this.code;
    }

    ;

    public String msg() {
        return this.msg;
    }

    ;

    ResponseStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getName(Integer code) {
        for (ResponseStatus c : ResponseStatus.values()) {
            if (c.code == code) {
                return c.msg;
            }
        }
        return null;
    }
}
