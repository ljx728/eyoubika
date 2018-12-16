package com.eyoubika.common;
import java.io.IOException; 
import javax.servlet.http.HttpServletRequest; 
import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest;


/*==========================================================================================*
 * Description:	不能删除此类。用以解决图片上传的bug
 * Class:		MyRequestParser
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年8月23日 下午5:06:25
 *==========================================================================================*/
public class MyRequestParser extends JakartaMultiPartRequest { 

public void parse(HttpServletRequest servletRequest, String saveDir) throws IOException { 


} 

} 