package com.eyoubika.sbc.dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.sbc.domain.TradeCalendarDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-11-24 11:23:47
 *==========================================================================================*/
public class TradeCalendarDao extends BaseDao {
	String nameSpace = "tp_TradeCalendar";
	private TradeCalendarDomain tradeCalendarDomain;	//<<attrNote>>
	public TradeCalendarDomain getTradeCalendarDomain(){
		return tradeCalendarDomain;
	}
	public void setTradeCalendarDomain(TradeCalendarDomain tradeCalendarDomain){
		this.tradeCalendarDomain = tradeCalendarDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:23:47
	 *--------------------------------------------------------------------------------------*/
	public void addTradeCalendar(TradeCalendarDomain tradeCalendarDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertTradeCalendar", tradeCalendarDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:23:47
	 *--------------------------------------------------------------------------------------*/
	public int deleteTradeCalendar(String date){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteTradeCalendar", date);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:23:47
	 *--------------------------------------------------------------------------------------*/
	public int deleteAll(){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteAll");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:23:47
	 *--------------------------------------------------------------------------------------*/
	public void modifyTradeCalendar(TradeCalendarDomain tradeCalendarDomain){
		try {
			sqlMapClient.update(nameSpace +".updateTradeCalendar", tradeCalendarDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:23:47
	 *--------------------------------------------------------------------------------------*/
	public TradeCalendarDomain findTradeCalendar(String date){
		try {
			return (TradeCalendarDomain) sqlMapClient.queryForObject(nameSpace +".selectTradeCalendar", date);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:23:47
	 *--------------------------------------------------------------------------------------*/
	public TradeCalendarDomain findTradeCalendarByDomain(TradeCalendarDomain tradeCalendarDomain){
		try {
			return (TradeCalendarDomain) sqlMapClient.queryForObject(nameSpace +".selectTradeCalendarByDomain", tradeCalendarDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:23:47
	 *--------------------------------------------------------------------------------------*/
	public List<TradeCalendarDomain> queryTradeCalendar(TradeCalendarDomain tradeCalendarDomain){
		try {
			return (List<TradeCalendarDomain>) sqlMapClient.queryForList(nameSpace +".selectTradeCalendarList", tradeCalendarDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:23:47
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(TradeCalendarDomain tradeCalendarDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", tradeCalendarDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取上周的最后一天交易日
	 * Method:		findLastWeekDay
	 * Author:		1.0 created by lijiaxuan at 2015年12月1日 下午7:18:06
	 *--------------------------------------------------------------------------------------*/
	public String findLastWeekDay(String date, String type){
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> typeList = new ArrayList<String>();
		typeList.add("01");
		typeList.add("05");
		typeList.add("03");
		typeList.add("07");
		//对于周六收盘的交易所，则昨日计算的时候，需要加上周六
		if (type.equals("04") || type.equals("08")) {
			typeList.add("04");
			typeList.add("08");
		}
	
		map.put("date", date);			
		map.put("typeList", typeList);	
		try {
			return (String)sqlMapClient.queryForObject(nameSpace +".selectLastWeekDay", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取上一个交易日
	 * Method:		findYesterday
	 * Author:		1.0 created by lijiaxuan at 2015年12月1日 下午8:25:22
	 *--------------------------------------------------------------------------------------*/
	public String findYesterday(String date, String type){
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> typeList = new ArrayList<String>();
		typeList.add("01");
		typeList.add("05");
		typeList.add("03");
		typeList.add("07");
		//对于周六收盘的交易所，则昨日计算的时候，需要加上周六
		if (type.equals("04") || type.equals("08")) {
			typeList.add("04");
			typeList.add("08");
		}
	
		map.put("date", date);			
		map.put("typeList", typeList);	
		try {
			return (String)sqlMapClient.queryForObject(nameSpace +".selectYesterday", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取上个月的最后一天交易日
	 * Method:		findLastMonthDay
	 * Author:		1.0 created by lijiaxuan at 2015年12月2日 下午3:29:34
	 *--------------------------------------------------------------------------------------*/
	public String findLastMonthDay(String date, String type){
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> typeList = new ArrayList<String>();
		typeList.add("01");
		typeList.add("05");
		typeList.add("03");
		typeList.add("07");
		//对于周六收盘的交易所，则昨日计算的时候，需要加上周六
		if (type.equals("04") || type.equals("08")) {
			typeList.add("04");
			typeList.add("08");
		}
	
		map.put("date", date);			
		map.put("typeList", typeList);	
		try {
			return (String)sqlMapClient.queryForObject(nameSpace +".selectLastMonthDay", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取每周第一天，目前没判断该日是否是交易日
	 * Method:		findWeekFirstDay
	 * Author:		1.0 created by lijiaxuan at 2015年12月2日 下午3:29:31
	 *--------------------------------------------------------------------------------------*/
	public String findWeekFirstDay(String date, String type){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("date", date);			
		map.put("type", type);
		try {
			return (String)sqlMapClient.queryForObject(nameSpace +".selectWeekFirstDay", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取每月第一天，目前没判断该日是否是交易日
	 * Method:		findMonthFirstDay
	 * Author:		1.0 created by lijiaxuan at 2015年12月2日 下午3:29:29
	 *--------------------------------------------------------------------------------------*/
	public String findMonthFirstDay(String date, String type){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("date", date);			
		map.put("type", type);
		try {
			return (String)sqlMapClient.queryForObject(nameSpace +".selectMonthFirstDay", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	//>. <CustomFileTag>
}
