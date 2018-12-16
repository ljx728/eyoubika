package com.eyoubika.util;
import com.eyoubika.util.Md5Util;

import java.util.Random;
import com.eyoubika.common.YbkException;
/*==========================================================================================*
 * Description:	定义了内容工具包
 * Class:		ContentUtil
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-19 19:23:55
 *==========================================================================================*/
public class ContentUtil{
	/*--------------------------------------------------------------------------------------*
	 * Description:	密码生成器
	 * Method:		generatePassword
	 * Parameters:	String originPwd, String salt
	 * History:		1.0 created by lijiaxuan at 2015-5-19 19:24:56
	 *--------------------------------------------------------------------------------------*/
	public static  String generatePassword(String originPwd, String salt) {
		String password = null;
		try {
		password = Md5Util.getMd5(originPwd + salt);
		
		} catch(Exception e) {
			throw new YbkException(YbkException.CODE000010, YbkException.DESC000010);
		}
		return password;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	生成随机字符串
	 * Method:		generateRandomString
	 * Parameters:	String base, int length
	 * History:		1.0 created by lijiaxuan at 2015-5-19 19:54:43
	 *--------------------------------------------------------------------------------------*/
	public static String generateRandomString(String base, int length){
		if (base == null) {
			 base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";     
		}	
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();  
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	生成加盐
	 * Method:		generateSalt
	 * Parameters:	String base, int length
	 * History:		1.0 created by lijiaxuan at 2015-5-19 19:59:31
	 *--------------------------------------------------------------------------------------*/
	public static String generateSalt(){
		String base = null;//"abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ+=}{][";
		return generateRandomString(base, 6);//.toUpperCase();
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	生成验证码
	 * Method:		generateAuthcode
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-19 20:02:28
	 *--------------------------------------------------------------------------------------*/
	public static String generateAuthcode(){
		return generateRandomString(null, 6);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	生成token
	 * Method:		generateToken
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-19 20:03:01
	 *--------------------------------------------------------------------------------------*/
	public static String generateToken() {
		 //String token = "";	
		 return generateRandomString(null, 32);
		 //token = new BigInteger(165, new Random()).toString(32).toUpperCase();	    
	    
	}
	
}
