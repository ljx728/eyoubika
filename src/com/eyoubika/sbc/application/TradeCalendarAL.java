package com.eyoubika.sbc.application;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.eyoubika.common.CommonMaps;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.common.YbkException;
import com.eyoubika.sbc.domain.TradeCalendarDomain;
import com.eyoubika.sbc.dao.TradeCalendarDao;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class TradeCalendarAL{
	private TradeCalendarDao tradeCalendarDao;	//
	public TradeCalendarDao getTradeCalendarDao(){
		return tradeCalendarDao;
	}
	public void setTradeCalendarDao(TradeCalendarDao tradeCalendarDao){
		this.tradeCalendarDao = tradeCalendarDao;
	}
	public static void main(String args[]){
		
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		initTradeDay
	 * Author:		1.0 created by lijiaxuan at 2015年11月25日 下午3:49:48
	 *--------------------------------------------------------------------------------------*/
	public void initTradeDay( ){
		String date = null ;
		String weekOfYearStr = "";
		String monthOfYearStr = "";
		date = CommonUtil.getYesterDate();
		//Date yesterday =CommonUtil.getDate(2016, 12, 31);// CommonUtil.getPreDate(CommonUtil.getDate(2016, 12, 31));
		Date yesterday =CommonUtil.getDate(2015, 12, 31);
		date =CommonUtil.getFormatDate(yesterday);
		TradeCalendarDomain tradeCalendarDomain = new TradeCalendarDomain();
		while (date.compareTo("20150101") >= 0){
			tradeCalendarDomain.init();
			tradeCalendarDomain.setDate(date);
			int weekOfYear = CommonUtil.getWeekInYearOfDate(date);
			int monthOfYear = CommonUtil.getMonthInYearOfDate(date);
			if (weekOfYear<10){
				weekOfYearStr = "0"+weekOfYear;
			} else {
				weekOfYearStr = "" + weekOfYear;
			}
			if (monthOfYear<10){
				monthOfYearStr = "0"+monthOfYear;
			} else {
				monthOfYearStr = "" + monthOfYear;
			}
			//状态：K图生成状态
			//批量是周一，或者是月第一天跑，则生成上周或上月K图
			if (CommonUtil.getWeekdayFromDate(date).equals("星期一")&&!date.substring(date.length()-2).equals("01")){
				tradeCalendarDomain.setStatus("02"); //周一，生成上周周K图
			} else if (date.substring(date.length()-2).equals("01")&&!CommonUtil.getWeekdayFromDate(date).equals("星期一")){
				tradeCalendarDomain.setStatus("03"); //月首，生成上月月K图
			}else if (date.substring(date.length()-2).equals("01")&&CommonUtil.getWeekdayFromDate(date).equals("星期一")){
				tradeCalendarDomain.setStatus("04"); //既是周一又是月首，都生成。
			} else {
				tradeCalendarDomain.setStatus("01"); //普通日子
			}
			
			//类型：交易日类型
			//月的最后一天，
			if (CommonUtil.isLastDayOfMonth(date)){
				if (CommonUtil.getWeekdayFromDate(date).equals("星期六")){
					tradeCalendarDomain.setType("08");//当日部分交易计算
				} else if (CommonUtil.getWeekdayFromDate(date).equals("星期日")){
					tradeCalendarDomain.setType("06");//当日没有交易
				} else if (CommonUtil.getWeekdayFromDate(date).equals("星期五")) {
					tradeCalendarDomain.setType("07"); //当日部分交易结算
				} else {
					tradeCalendarDomain.setType("05"); //普通日子
				}
			} else {
				if (CommonUtil.getWeekdayFromDate(date).equals("星期六")){
					tradeCalendarDomain.setType("04");//当日部分交易计算
				} else if (CommonUtil.getWeekdayFromDate(date).equals("星期日")){
					tradeCalendarDomain.setType("02");//当日没有交易
				} else if (CommonUtil.getWeekdayFromDate(date).equals("星期五")) {
					tradeCalendarDomain.setType("03"); //当日部分交易结算
				} else {
					tradeCalendarDomain.setType("01"); //普通日子
				}
			}
			
			tradeCalendarDomain.setWeek(weekOfYearStr);
			tradeCalendarDomain.setMonth(monthOfYearStr);
			System.out.println(tradeCalendarDomain);
			tradeCalendarDao.addTradeCalendar(tradeCalendarDomain);
			yesterday = CommonUtil.getPreDate(yesterday);
			date = CommonUtil.getFormatDate(yesterday);
		}
		
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public void addTradeDay( ){
		
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public TradeCalendarDomain getTradeDay(String date){
		TradeCalendarDomain tradeCalendarDomain = tradeCalendarDao.findTradeCalendar(date);
		return tradeCalendarDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public void deleteTradeDay(TradeCalendarDomain tradeCalendarDomain){
		
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public void modifyTradeDay(TradeCalendarDomain tradeCalendarDomain){
		
	}
	//>. <CustomFileTag>
	public String  getYesterday(String date, String exId){
		String type = CommonMaps.tradeDayMap.get(exId);
		if (type==null){
			return null;
		}
		return tradeCalendarDao.findYesterday(date, type);
	}
	
	public String  getLastWeekDay(String date, String exId){
		String type = CommonMaps.tradeDayMap.get(exId);
		if (type==null){
			return null;
		}
		return tradeCalendarDao.findLastWeekDay(date, type);
	}
	
	public String  getWeekFirstDay(String date, String exId){
		String type = CommonMaps.tradeDayMap.get(exId);
		if (type==null){
			return null;
		}
		return tradeCalendarDao.findLastWeekDay(date, type);
	} 
	
	public String  getLastMonthDay(String date, String exId){
		String type = CommonMaps.tradeDayMap.get(exId);
		if (type==null){
			return null;
		}
		return tradeCalendarDao.findLastMonthDay(date, type);
	}
	
	//>. <CustomFileTag>
}
