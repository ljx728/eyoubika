package com.eyoubika.common;
import java.util.Map;
/**
 * 错误码号段分配规则：ooxxxx
 * 00 平台
 * 01 用户
 * 02 查询
 * 03 资讯
 * 04 普通业务
 * 05 核心业务
 * 06 工具
 *
 */
/*==========================================================================================*
 * Description:	定义了本系统的所有异常信息
 * Class:		YbkException
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-15 20:14:13
 *==========================================================================================*/
public class YbkException extends RuntimeException {
	 // 平台
	public static final String CODE000001 = "000001";
	public static final String DESC000001 = "session过期";
	public static final String CODE000002 = "000002";
	public static final String DESC000002 = "参数错误";
	public static final String CODE000003 = "000003";
	public static final String DESC000003 = "接口参数不能为空";
	public static final String CODE000010 = "000010";
	public static final String DESC000010 = "密码生成器错误";
	public static final String CODE000011 = "000011";
	public static final String DESC000011 = "Request对象转换VO出错";
	public static final String CODE000013 = "000013";
	public static final String DESC000013 = "Domain转换VO出错，向属性里写入错误对参数类型";
	public static final String CODE000014 = "000014";
	public static final String DESC000014 = "Object转换Map出错，向属性里写入错误对参数类型";
	public static final String CODE000015 = "000015";
	public static final String DESC000015 = "日期转化错误";
	
	public static final String CODE000018 = "000018";
	public static final String DESC000018 = "URL字符编码转码错误";
	
	public static final String CODE000019 = "000019";
	public static final String DESC000019 = "参数空指针";
	public static final String CODE000020 = "000020";
	public static final String DESC000020 = "sql出现问题";
	
	public static final String CODE010001 = "010001";
	public static final String DESC010001 = "用户不存在";
	public static final String CODE010002 = "010002";
	public static final String DESC010002 = "验证码已失效，请重新获取";
	public static final String CODE010003 = "010003";
	public static final String DESC010003 = "验证码输入错误";
	public static final String CODE010004 = "010004";
	public static final String DESC010004 = "手机号重复注册";
	public static final String CODE010005 = "010005";
	public static final String DESC010005 = "用户昵称已注册";
	public static final String CODE010006 = "010006";
	public static final String DESC010006 = "用户昵称不合法";
	public static final String CODE010007 = "010007";
	public static final String DESC010007 = "用户昵称含有敏感词";

	public static final String CODE010010= "010010";
	public static final String DESC010010 = "密码输入错误";
	public static final String CODE010012= "010012";
	public static final String DESC010012 = "密码不能为空";
	public static final String DESC010014 = "010014";
	public static final String CODE010014 = "手机号不合法";
	public static final String CODE010011= "010011";
	public static final String DESC010011 = "token无效或已过期";

	public static final String CODE020001 = "020001";
	public static final String DESC020001 = "邮币卡品种不存在";
	public static final String CODE020002 = "020002";
	public static final String DESC020002 = "无法找到该品种所在交易所";
	public static final String CODE020003 = "020003";
	public static final String DESC020003 = "邮币卡品种无法匹配ID";
	public static final String CODE020004 = "020004";
	public static final String DESC020004 = "邮币卡品种数据错误";
	
	public static final String CODE030001 = "030001";
	public static final String DESC030001 = "交易所不存在";
	public static final String CODE030002 = "030002";
	public static final String DESC030002 = "交易所ID必须是数字";
	public static final String CODE030003 = "030003";
	public static final String DESC030003 = "交易所数据错误";

	public static final String CODE900001= "900001";
	public static final String DESC900001 = "返回数目过多，已取消";
	public static final String CODE900002= "900002";
	public static final String DESC900002 = "传入数目过多，已取消";
	
	public static final String CODE970001= "970001";
	public static final String DESC970001 = "无法打开本地文件";
	public static final String CODE970002= "970002";
	public static final String DESC970002 = "无法打开配置文件";
	public static final String CODE970003= "970003";
	public static final String DESC970003 = "无法读取配置文件";
	
	public static final String CODE970010= "970010";
	public static final String DESC970010 = "文件已经存在";
	public static final String CODE970011= "970011";
	public static final String DESC970011 = "文件不存在";
	public static final String CODE970012= "970012";
	public static final String DESC970012= "新文件名和旧文件名相同";
	
	public static final String CODE980001= "980001";
	public static final String DESC980001 = "无法打开远程URL";
	public static final String CODE980005= "980005";
	public static final String DESC980005 = "远程URL无法获取文章标题";
	
	public static final String CODE980011= "980011";
	public static final String DESC980011 = "解析网页最大页面数出错";
	public static final String CODE980012= "980012";
	public static final String DESC980012 = "网页交易表格为空";
	public static final String CODE980013= "980013";
	public static final String DESC980013 = "网页交易数据项格式混乱";
	
	
	public static final String CODE999998 = "999998";
	public static final String DESC999998 = "系统错误";
	public static final String CODE999999 = "999999";
	public static final String DESC999999 = "未知错误";
	
	private String code;
	private String desc;
	private int userId;
	private Map<String, Object> args;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Map<String, Object> getArgs() {
		return args;
	}
	public void setArgs(Map<String, Object> args) {
		this.args = args;
	}
	
	
	public YbkException(){
		super();
	}
	
	public YbkException(String message){
		super(message);
	}
	public YbkException(Throwable cause){
		super(cause);
	}
	public YbkException(String code, String desc) {
		super(desc);
		this.code = code;
		this.desc = desc;
		this.args = null;
	}
	public YbkException(String code, String desc, int userId) {
		super(desc);
		this.code = code;
		this.desc = desc;
		this.userId = userId;
		this.args = null;
	}
	
	public YbkException(String code, String desc, int userId, Map<String, Object> args) {
		super(desc);
		this.code = code;
		this.desc = desc;
		this.userId = userId;
		this.args = args;
	}
	
}