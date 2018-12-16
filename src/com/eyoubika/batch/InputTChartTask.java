package com.eyoubika.batch;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

import com.eyoubika.common.ApplicationUtil;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.info.application.QuotationsAL;
import com.eyoubika.util.CommonUtil;

/*==========================================================================================*
 * Description:	分时图定时任务
 * Class:		InputTChartTask
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年7月18日 下午4:39:05
 *==========================================================================================*/
public class InputTChartTask extends TimerTask{
	private static boolean isRunning = false;
	
	public static boolean isRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		InputTChartTask.isRunning = isRunning;
	}

	@Override
	public void run() {
		if (CommonUtil.isInTime(CommonVariables.TRADESHARE_BEG_AM, CommonVariables.TRADEHOUR_END_AM) 
				|| CommonUtil.isInTime(CommonVariables.TRADESHARE_BEG_PM, CommonVariables.TRADEHOUR_END_PM)){
			QuotationsAL quotationsAL = (QuotationsAL) ApplicationUtil.getBean("quotationsAL");
			// TODO Auto-generated method stub
			 if (!isRunning) {
		            isRunning = true;
		            //System.out.println("开始执行指定任务");
		            if (quotationsAL == null) {
		            	isRunning = false;
		            	//System.out.println("指定任务执行结束");
		            	
		            } else {
		            	quotationsAL.inputTChart(); //生成分时图
		 	            isRunning = false;
		 	            //System.out.println("指定任务执行结束");
		            }
		           
		        } else {
		            //System.out.println("上一次任务执行还未结束");
		        }
		}	
	}
	
	
	
	
}