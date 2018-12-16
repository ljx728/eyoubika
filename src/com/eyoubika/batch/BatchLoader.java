package com.eyoubika.batch;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.eyoubika.util.CommonUtil;

/*==========================================================================================*
 * Description:	日终批量框架
 * Class:		BatchLoader
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年6月23日 下午4:35:40
 *==========================================================================================*/
public class BatchLoader implements ServletContextListener{
	private Properties eybkConfig;
	private Timer timer = null;
	private Calendar cal=Calendar.getInstance();
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		loadConfig(arg0);
		//System.out.println("loading....");
		loadTask();
		
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	加载全局的配置文件
	 * Method:		loadConfig
	 * Author:		1.0 created by lijiaxuan at 2015年6月23日 下午7:20:11
	 *--------------------------------------------------------------------------------------*/
	public void loadConfig(ServletContextEvent arg0){
		this.eybkConfig = CommonUtil.getConfig("Eyoubika.properties");
		ServletContext context = arg0.getServletContext();
		context.setAttribute("eybkConfig", this.eybkConfig);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	加载全局的定时任务
	 * Method:		loadTask
	 * Author:		1.0 created by lijiaxuan at 2015年6月23日 下午7:25:14
	 *--------------------------------------------------------------------------------------*/
	public void loadTask(){
		timer = new java.util.Timer(true);	
        /*** 定制每日09：31：00执行方法 ***/      
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.HOUR_OF_DAY, 9);  //上午9点 
        calendar.set(Calendar.MINUTE, 31);		//31分  
        calendar.set(Calendar.SECOND, 0);  
        Date date=calendar.getTime(); //第一次执行定时任务的时间  
   
        //多个定时器会有bug，目前只留生成分时图的定时器
        timer.schedule(new InputTChartTask(), date, Integer.parseInt(this.eybkConfig.getProperty("inputTChartTimeInterval")));
        //timer.schedule(new InputVolatilityTask(), date, Integer.parseInt(this.eybkConfig.getProperty("inputVolatilityTimeInterval"))); 
        //timer.schedule(new InputFiveMinRiseTask(), date, Integer.parseInt(this.eybkConfig.getProperty("inputVolatilityTimeInterval")));
        //timer.schedule(new InputThreeDayRiseTask(), date, Integer.parseInt(this.eybkConfig.getProperty("inputTChartTimeInterval"))); 	
        //timer.schedule(new InputTChartTask(), date, 60000); 		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//ServletContext context = arg0.getServletContext();
		//context.setAttribute("redisConfig", null);
		//context.removeAttribute("redisConfig");
	
	}
}