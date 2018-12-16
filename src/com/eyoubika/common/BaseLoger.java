package com.eyoubika.common;  
  
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;  
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import com.eyoubika.util.CommonUtil;
  

/*==========================================================================================*
 * Description:	TODO
 * Class:		BaseLoger
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年5月28日 下午4:55:35
 *==========================================================================================*/
public class BaseLoger {  
	private Logger logger ;
    //private static Logger logger = Logger.getLogger();  //HelloLog4j.class
    public BaseLoger(String name){
    	 logger = Logger.getLogger(name);//this.getClass()
    	 //BasicConfigurator.configure();//默认配置   
    	// 如果需要，则打开上面这个配置。
    	 
    	 
        // PropertyConfigurator.configure(CommonUtil.CONF_DIR+ "log4j.properties"); 
        // DOMConfigurator.configure(CommonUtil.APP_DIR + "src/conf/log4j.xml");//XML配置文件   
         // 记录debug级别的信息  
        // logger.debug("This is debug message.");  
         // 记录info级别的信息  
        // logger.info("This is info message.");  
         // 记录error级别的信息  
         //logger.error("This is error message.");  
    }
    
    public void error(String log){
    	 logger.error(log);  
    }
    public void info(String log){
   	 logger.info(log);  
   }
    public void debug(String log){
      	 logger.debug(log);  
      }
    
    
}  