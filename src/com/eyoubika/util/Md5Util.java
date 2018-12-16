package com.eyoubika.util;
import java.security.MessageDigest;
/*==========================================================================================*
 * Description:	定义了MD5工具包
 * Class:		ConverterUtil
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-17 20:10:32
 *==========================================================================================*/
public class Md5Util{
	private static MessageDigest md5 = null;
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /*--------------------------------------------------------------------------------------*
	 * Description:	getMd5
	 * Method:		getMd5
	 * Parameters:	String str
	 * History:		1.0 created by lijiaxuan at 2015-5-17 20:14:28
	 *--------------------------------------------------------------------------------------*/
    public static String getMd5(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
            if((x & 0xff)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

}