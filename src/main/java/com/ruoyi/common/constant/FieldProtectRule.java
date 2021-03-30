package com.ruoyi.common.constant;

/**
 * 
 * 数据加解密、及脱敏规则定义
 * 
 * @author zhoujijun
 * @date 2020/04/09
 *
 */
public class FieldProtectRule {

	// 加密：身份证号
	public static final String ENCRYPT_IDNO = "encrypt_idno";
	// 加密：银行账号
	public static final String ENCRYPT_ACCOUNT = "encrypt_account";

	// 解密：身份证号
	public static final String DECRYPT_IDNO = "decrypt_idno";
	// 解密：银行账号
	public static final String DECRYPT_ACCOUNT = "decrypt_account";

	// 解密&脱敏：身份证号
	public static final String SHADOW_DECRYPT_IDNO = "shadow_decrypt_idno";
	// 解密&脱敏：银行账号
	public static final String SHADOW_DECRYPT_ACCOUNT = "shadow_decrypt_account";

	// 脱敏：手机号码
	public static final String SHADOW_MOBILE = "shadow_mobile";

}
