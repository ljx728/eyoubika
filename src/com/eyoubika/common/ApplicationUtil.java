package com.eyoubika.common;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/*==========================================================================================*
 * Description:	TODO
 * Class:		ApplicationUtil
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年10月27日 下午5:34:18
 *==========================================================================================*/
public class ApplicationUtil implements ApplicationContextAware{
	
	 private static ApplicationContext applicationContext;  
	  	@Override
	 	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	  		ApplicationUtil.applicationContext = applicationContext;
	  	}
	 	public static Object getBean(String name){
	 		if  (applicationContext == null) {
	 			return null;
	 		} else {
	 			return applicationContext.getBean(name);
	 		}
	 	     
	 	}
	
}