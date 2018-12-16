package com.eyoubika.batch.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.CommonMaps;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.HeapSort;
import com.eyoubika.common.RankData;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ContentUtil;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.sbc.application.TradeCalendarAL;
import com.eyoubika.sbc.dao.ExchangeBasicDao;
import com.eyoubika.sbc.dao.SbcBasicDao;
import com.eyoubika.sbc.dao.SbcQuotationHistDao;
import com.eyoubika.sbc.dao.TradeCalendarDao;
import com.eyoubika.sbc.domain.ExchangeBasicDomain;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.sbc.domain.SbcQuotationHistDomain;
import com.eyoubika.sbc.domain.TradeCalendarDomain;
import com.eyoubika.spider.dao.NewsArticleDao;
import com.eyoubika.spider.dao.QuotesRedisDao;
import com.eyoubika.info.domain.QuotationsDomain;
import com.eyoubika.info.domain.QuotationsRefreshDomain;
import com.eyoubika.common.PageInfo;

import java.math.*;
/*==========================================================================================*
 * Description:	TODO
 * Class:		SbcBatchAL
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年12月1日 上午9:15:24
 *==========================================================================================*/
public class SbcBatchAL  {
	private QuotesRedisDao quotesRedisDao;
	private ExchangeBasicDao exchangeBasicDao;
	private SbcQuotationHistDao sbcQuotationHistDao;
//	private TradeCalendarDao tradeCalendarDao;
	private TradeCalendarAL tradeCalendarAL;
	

	public TradeCalendarAL getTradeCalendarAL() {
		return tradeCalendarAL;
	}

	public void setTradeCalendarAL(TradeCalendarAL tradeCalendarAL) {
		this.tradeCalendarAL = tradeCalendarAL;
	}

	public SbcQuotationHistDao getSbcQuotationHistDao() {
		return sbcQuotationHistDao;
	}

	public void setSbcQuotationHistDao(SbcQuotationHistDao sbcQuotationHistDao) {
		this.sbcQuotationHistDao = sbcQuotationHistDao;
	}

	public ExchangeBasicDao getExchangeBasicDao() {
		return exchangeBasicDao;
	}

	public void setExchangeBasicDao(ExchangeBasicDao exchangeBasicDao) {
		this.exchangeBasicDao = exchangeBasicDao;
	}

	public QuotesRedisDao getQuotesRedisDao() {
		return quotesRedisDao;
	}

	public void setQuotesRedisDao(QuotesRedisDao quotesRedisDao) {
		this.quotesRedisDao = quotesRedisDao;
	}

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	保存K线图数据
	 * Method:		inputSbcQuotation
	 * Author:		1.0 created by lijiaxuan at 2015年10月18日 下午4:09:38
	 *--------------------------------------------------------------------------------------*/
	public void calSbcKQuotation(){
		calSbcQuotationDayDetail(null);
		//每周周一生成周K图
		String today = CommonUtil.getNowDate();	
		TradeCalendarDomain tradeCalendarDomain = tradeCalendarAL.getTradeDay(today);
		if (tradeCalendarDomain.getStatus().equals(CommonVariables.COMMON_LEVEL_02) || tradeCalendarDomain.getType().equals(CommonVariables.COMMON_LEVEL_04)){
			calAllWeekDetail(null, null);
			//calSbcQuotationWeekDetail(null, null);
		} 
		//月首生成月K图
		if (tradeCalendarDomain.getStatus().equals(CommonVariables.COMMON_LEVEL_03) || tradeCalendarDomain.getType().equals(CommonVariables.COMMON_LEVEL_04)){
			calAllMonthDetail(null, null);
			//calSbcQuotationMonthDetail(null, null);
		} 
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	以日为单位，存取行情到数据库
	 * Method:		inputSbcQuotationDayDetail
	 * Author:		1.0 created by lijiaxuan at 2015年8月31日 下午5:15:03
	 *--------------------------------------------------------------------------------------*/
	public void calSbcQuotationDayDetail(String exIdList){
		SbcQuotationHistDomain sbcQuotationHistDomain = new SbcQuotationHistDomain();
		
		
		String exchangeIds[]  = null;
		//> 1. 遍历每个交易所
		if (exIdList == null){
			exchangeIds  = CommonMaps.exchangeIds;
		} else {
			String tmpList[] = exIdList.split(",");
			exchangeIds = tmpList;
		}

		int listSize = exchangeIds.length;
		String exId = null;
		String quotation = null;
		String sbcId = null;
		String dealValue = null;
		String dealVolume = null;
		String today = CommonUtil.getNowDate();
		String yesterday = "";
		//> 1. 遍历每个交易所
		for (int i = 0; i < listSize; i++){
			exId = exchangeIds[i];
			yesterday = tradeCalendarAL.getYesterday(today, exId); //获取上一个交易日
			if (yesterday == null){ //由于exId不在指定的抓取列表中，所以跳过
				continue;
			}
			Map<String,String> sbcQuotesMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_QUOTS + exId);
			Iterator<Map.Entry<String, String>> it = sbcQuotesMap.entrySet().iterator();
			//遍历每个品种
			while (it.hasNext()) {
				sbcQuotationHistDomain.init();
				
				//> 2. 每个品种，抽取分时行情，存入redis
				Map.Entry<String, String> entry = it.next();			
				quotation = entry.getValue();
				
				Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotation);
				sbcId = quotMap.get("sbcId");
				sbcQuotationHistDomain.setClosePrice(quotMap.get("currentPrice"));	//当天最后的现价就是收盘价
				sbcQuotationHistDomain.setOpeningPrice(quotMap.get("openingPrice"));	//开盘价
				sbcQuotationHistDomain.setHighestPrice(quotMap.get("highestPrice"));	//最高价
				sbcQuotationHistDomain.setLowestPrice(quotMap.get("lowestPrice"));		//最低价
				sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_DAY);		//类型：日，周，月
				dealVolume = quotesRedisDao.getDealVolume(sbcId);
				dealValue = quotesRedisDao.getDealValue(sbcId);
				sbcQuotationHistDomain.setDealValue(dealValue);
				sbcQuotationHistDomain.setDealVolume(dealVolume);
				sbcQuotationHistDomain.setDate(yesterday);		//日期
				//涨幅 = 现价-上一个交易日收盘价/上一个交易日收盘价*100%
				
				if (CommonUtil.isStringDigit(quotMap.get("riseScope")) ){
					sbcQuotationHistDomain.setRiseScope(quotMap.get("riseScope"));
				} else {
					if ( CommonUtil.isStringDigit(quotMap.get("riseValue")) && CommonUtil.isStringDigit(quotMap.get("yesterClPrice"))){
						Float riseScope = Float.parseFloat(quotMap.get("riseValue"))/Float.parseFloat(quotMap.get("yesterClPrice")) * 100;
						sbcQuotationHistDomain.setRiseScope(riseScope.toString());
					} else{
						sbcQuotationHistDomain.setRiseScope("0");
					}
				}	
				sbcQuotationHistDomain.setRiseValue(quotMap.get("riseValue"));		//涨跌额
				sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));			//sbcId		
				sbcQuotationHistDao.addSbcQuotationHist(sbcQuotationHistDomain, exId);
			}
		}
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	计算某个品种的周K图
	 * Method:		calSbcKData
	 * Author:		1.0 created by lijiaxuan at 2015年12月21日 下午1:50:31
	 *--------------------------------------------------------------------------------------*/
	public void calSbcWeekData(String exId, String sbcId, String date) {
		String lastWeekDay = null;
		String lastWeekLastWeekDay =  null;
		String today =  null;
		SbcQuotationHistDomain sbcQuotationHistDomain = new SbcQuotationHistDomain();
		CommonUtil.toLog("----------------------sbcId " + sbcId+ "----------------------\n");
		CommonUtil.toLog("date " + date+ "\n");
		
		for (today = CommonUtil.getNowDate() ; today.compareTo(date) > 0; today = lastWeekDay){
			CommonUtil.toLog("today " + today + "\n");
			String openPrice = "";
			String closePrice = "";
			double dealValueD = 0;
			double dealVolumeD = 0;
			double tmpPriceD = 0;
			double highestPriceD = 0;
			double lowestPriceD = 0;
			lastWeekDay = tradeCalendarAL.getLastWeekDay(today, exId);	//上周末
			if (lastWeekDay == null){ //由于exId不在指定的抓取列表中，所以跳过
				break;
			}
			CommonUtil.toLog("lastWeekDay " + lastWeekDay+ "\n");
			lastWeekLastWeekDay = tradeCalendarAL.getLastWeekDay(lastWeekDay, exId); //上上周末
			CommonUtil.toLog("lastWeekLastWeekDay " + lastWeekLastWeekDay+ "\n");
			List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationBetweenDate(exId, sbcId, CommonVariables.TIME_INTERVAL_DAY, lastWeekLastWeekDay, lastWeekDay);
            //获取本周的所有日K信息
     		//List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationAfterDate( exId, sbcId,  CommonVariables.TIME_INTERVAL_DAY, histList.get(j).getDate(), CommonUtil.getNowYear());
     		//不是本周第一天
			CommonUtil.toLog("afterDomains.size() " + afterDomains.size()+ "\n");
     		if (afterDomains != null && afterDomains.size() > 0){				
     			for (int x = 0 ; x < afterDomains.size(); x++){
     				if (x==0) {		
     					openPrice =  afterDomains.get(x).getOpeningPrice();
     					//closePrice =  afterDomains.get(x).getClosePrice();
     				}
     				
     				if (CommonUtil.isStringDigit(afterDomains.get(x).getClosePrice())) {
     					if (! CommonUtil.isStringZero(afterDomains.get(x).getClosePrice())){
     						closePrice =  afterDomains.get(x).getClosePrice();
     					}
     				}/**/
     				//品种本周截至昨日交易额
     				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealValue())){
    					dealValueD += Double.parseDouble(afterDomains.get(x).getDealValue());					
    				}     				
    				//品种本周截至昨日交易量
    				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealVolume())){
    					dealVolumeD += Double.parseDouble(afterDomains.get(x).getDealVolume());    				
    				}
    				
    				//品种本周截至昨日最高价
    				if (CommonUtil.isStringDigit(afterDomains.get(x).getHighestPrice())){
    					tmpPriceD = Double.parseDouble(afterDomains.get(x).getHighestPrice());
    					if (x == 0 ) highestPriceD = tmpPriceD;
    					if (highestPriceD < tmpPriceD ){
    						highestPriceD = tmpPriceD;
    					}
    					
    				}
    				//品种本周截至昨日最低价
    				if (CommonUtil.isStringDigit(afterDomains.get(x).getLowestPrice())){
    					tmpPriceD = Double.parseDouble(afterDomains.get(x).getLowestPrice());
    					if (x == 0 ) lowestPriceD = tmpPriceD;
    					if (lowestPriceD > tmpPriceD ){
    						lowestPriceD = tmpPriceD;
    					}    					
    				}
     			}
     			
     			sbcQuotationHistDomain.setClosePrice(closePrice);
				sbcQuotationHistDomain.setOpeningPrice(openPrice);
				sbcQuotationHistDomain.setHighestPrice(CommonUtil.getFormatDouble(highestPriceD, 2));
				sbcQuotationHistDomain.setLowestPrice(CommonUtil.getFormatDouble(lowestPriceD, 2));
				sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_WEK);
				sbcQuotationHistDomain.setDealValue(CommonUtil.getFormatDouble(dealValueD, 2));
				sbcQuotationHistDomain.setDealVolume(CommonUtil.getFormatDouble(dealVolumeD, 2));
				sbcQuotationHistDomain.setDate(lastWeekDay);//因为是次日批量，所以日期应当为昨天
				
			
				//CommonUtil.toLog(lastWeekDay + "\n");
				SbcQuotationHistDomain yesterDomain = sbcQuotationHistDao.findSbcQuotationHist(exId, sbcId, CommonVariables.TIME_INTERVAL_DAY, lastWeekLastWeekDay);
				
				//System.out.println(yesterDomain.toString());
				//上上一周周末交易日，无数据，表示该品种是新品种,涨跌幅则以开盘价为基准计算
				if (yesterDomain == null) {
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice())  && !CommonUtil.isStringZero(sbcQuotationHistDomain.getOpeningPrice())){
						double riseValue = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
						double riseScope = (riseValue/Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice())) * 100;
						sbcQuotationHistDomain.setRiseValue(CommonUtil.getFormatDouble(riseValue, 2));
						sbcQuotationHistDomain.setRiseScope( CommonUtil.getFormatDouble(riseScope, 2));
         			} else {
         				
         				sbcQuotationHistDomain.setRiseValue("0");
						sbcQuotationHistDomain.setRiseScope( "0");
         			}
				} else {
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(yesterDomain.getClosePrice())  && !CommonUtil.isStringZero(yesterDomain.getClosePrice())){
						double riseValue = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(yesterDomain.getClosePrice());
						double riseScope = (riseValue/Double.parseDouble(yesterDomain.getClosePrice())) * 100;
						sbcQuotationHistDomain.setRiseValue(CommonUtil.getFormatDouble(riseValue, 2));
						sbcQuotationHistDomain.setRiseScope( CommonUtil.getFormatDouble(riseScope, 2));
         			} else if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice())  && !CommonUtil.isStringZero(sbcQuotationHistDomain.getOpeningPrice())){
						double riseValue = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
						double riseScope = (riseValue/Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice())) * 100;
						sbcQuotationHistDomain.setRiseValue(CommonUtil.getFormatDouble(riseValue, 2));
						sbcQuotationHistDomain.setRiseScope( CommonUtil.getFormatDouble(riseScope, 2));
         			}else {
         				
         				sbcQuotationHistDomain.setRiseValue("0");
						sbcQuotationHistDomain.setRiseScope( "0");
         			}
				}
				
				sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));
				sbcQuotationHistDao.addSbcQuotationHist(sbcQuotationHistDomain, exId);	
     		}
     
		} 
     		sbcQuotationHistDomain = null;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	根据交易所和回溯截止日期计算：某个交易所下的所有周K图
	 * Method:		calAllWeekDetail
	 * Author:		1.0 created by lijiaxuan at 2015年12月17日 上午8:56:30
	 *--------------------------------------------------------------------------------------*/
	public void calAllWeekDetail(String exIdList, String date){
		String exchangeIds[]  = null;
		//> 1. 遍历每个交易所
		if (exIdList == null){
			exchangeIds  = CommonMaps.exchangeIds;
		} else {
			String tmpList[] = exIdList.split(",");
			exchangeIds = tmpList;
		}
		//日期为空，则默认为昨日（自然日）
		if (date == null){
			date = CommonUtil.getYesterDate();
		} 
		int listSize = exchangeIds.length;
		String exId = null;
		String quotation = null;
		String sbcId = null;
		
		for (int i = 0; i < listSize; i++){
			exId = exchangeIds[i];
			Map<String,String> sbcQuotesMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_QUOTS + exId);//查询该交易所下的所有品种行情
			Iterator<Map.Entry<String, String>> it = sbcQuotesMap.entrySet().iterator();
			while (it.hasNext()) {							
				//> 2. 每个品种，抽取分时行情
				Map.Entry<String, String> entry = it.next();			
				quotation = entry.getValue();				
				Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotation);		//查询品种的ID
				sbcId = quotMap.get("sbcId");
				calSbcWeekData(exId, sbcId, date);
			}		
		}	
	}
	public void calAllMonthDetail(String exIdList, String date){
		String exchangeIds[]  = null;
		//> 1. 遍历每个交易所
		if (exIdList == null){
			exchangeIds  = CommonMaps.exchangeIds;
		} else {
			String tmpList[] = exIdList.split(",");
			exchangeIds = tmpList;
		}
		//日期为空，则默认为昨日（自然日）
		if (date == null){
			date = CommonUtil.getYesterDate();
		} 
		int listSize = exchangeIds.length;
		String exId = null;
		String quotation = null;
		String sbcId = null;
		
		for (int i = 0; i < listSize; i++){
			exId = exchangeIds[i];
			Map<String,String> sbcQuotesMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_QUOTS + exId);//查询该交易所下的所有品种行情
			Iterator<Map.Entry<String, String>> it = sbcQuotesMap.entrySet().iterator();
			while (it.hasNext()) {							
				//> 2. 每个品种，抽取分时行情
				Map.Entry<String, String> entry = it.next();			
				quotation = entry.getValue();				
				Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotation);		//查询品种的ID
				sbcId = quotMap.get("sbcId");
				calSbcMonthData(exId, sbcId, date);
			}		
		}	
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		calSbcMonthData
	 * Author:		1.0 created by lijiaxuan at 2015年12月21日 下午4:53:13
	 *--------------------------------------------------------------------------------------*/
	public void calSbcMonthData(String exId, String sbcId, String date) {
		String lastMonthDay = null;
		String lastMonthMonthDay =  null;
		String today =  null;
		SbcQuotationHistDomain sbcQuotationHistDomain = new SbcQuotationHistDomain();
		
		CommonUtil.toLog("----------------------sbcId " + sbcId+ "----------------------\n");
		CommonUtil.toLog("date " + date+ "\n");
		for (today = CommonUtil.getNowDate() ; today.compareTo(date) > 0; today = lastMonthDay){
			CommonUtil.toLog("today " + today+ "\n");
			String openPrice = "";
			String closePrice = "";
			double dealValueD = 0;
			double dealVolumeD = 0;
			double tmpPriceD = 0;
			double highestPriceD = 0;
			double lowestPriceD = 0;
			lastMonthDay = tradeCalendarAL.getLastMonthDay(today, exId);	//上月末
			if (lastMonthDay == null){ //由于exId不在指定的抓取列表中，所以跳过
				break;
			}
			CommonUtil.toLog("lastMonthDay " + lastMonthDay+ "\n");
			lastMonthMonthDay = tradeCalendarAL.getLastMonthDay(lastMonthDay, exId); //上上月末
			CommonUtil.toLog("lastMonthMonthDay " + lastMonthMonthDay+ "\n");
			List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationBetweenDate(exId, sbcId, CommonVariables.TIME_INTERVAL_DAY, lastMonthMonthDay, lastMonthDay);
            //获取本周的所有日K信息
     		//List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationAfterDate( exId, sbcId,  CommonVariables.TIME_INTERVAL_DAY, histList.get(j).getDate(), CommonUtil.getNowYear());
     		//不是本周第一天
     		if (afterDomains != null && afterDomains.size() > 0){				
     			for (int x = 0 ; x < afterDomains.size(); x++){
     				if (x==0) {
     					openPrice =  afterDomains.get(x).getOpeningPrice();
     				}
     				if (CommonUtil.isStringDigit(afterDomains.get(x).getClosePrice())) {
     					if (! CommonUtil.isStringZero(afterDomains.get(x).getClosePrice())){
     						closePrice =  afterDomains.get(x).getClosePrice();
     					}
     					
     				}
     				//品种本周截至昨日交易额
     				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealValue())){
    					dealValueD += Double.parseDouble(afterDomains.get(x).getDealValue());					
    				}     				
    				//品种本周截至昨日交易量
    				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealVolume())){
    					dealVolumeD += Double.parseDouble(afterDomains.get(x).getDealVolume());
    				}
    				
    				//品种本周截至昨日最高价
    				if (CommonUtil.isStringDigit(afterDomains.get(x).getHighestPrice())){
    					tmpPriceD = Double.parseDouble(afterDomains.get(x).getHighestPrice());
    					if (x == 0 ) highestPriceD = tmpPriceD;
    					if (highestPriceD < tmpPriceD ){
    						highestPriceD = tmpPriceD;
    					}
    					
    				}
    				//品种本周截至昨日最低价
    				if (CommonUtil.isStringDigit(afterDomains.get(x).getLowestPrice())){
    					tmpPriceD = Double.parseDouble(afterDomains.get(x).getLowestPrice());
    					if (x == 0 ) lowestPriceD = tmpPriceD;
    					if (lowestPriceD > tmpPriceD ){
    						lowestPriceD = tmpPriceD;
    					}    					
    				}
     			}
     			
     			sbcQuotationHistDomain.setClosePrice(closePrice);
				sbcQuotationHistDomain.setOpeningPrice(openPrice);
				sbcQuotationHistDomain.setHighestPrice(CommonUtil.getFormatDouble(highestPriceD, 2));
				sbcQuotationHistDomain.setLowestPrice(CommonUtil.getFormatDouble(lowestPriceD, 2));
				sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_MON);
				sbcQuotationHistDomain.setDealValue(CommonUtil.getFormatDouble(dealValueD, 2));
				sbcQuotationHistDomain.setDealVolume(CommonUtil.getFormatDouble(dealVolumeD, 2));
				sbcQuotationHistDomain.setDate(lastMonthDay);//因为是次日批量，所以日期应当为昨天
				SbcQuotationHistDomain yesterDomain = sbcQuotationHistDao.findSbcQuotationHist(exId, sbcId, CommonVariables.TIME_INTERVAL_DAY, lastMonthMonthDay);
				//System.out.println(yesterDomain.toString());
				//上上一周周末交易日，无数据，表示该品种是新品种,涨跌幅则以开盘价为基准计算
				if (yesterDomain == null) {
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice())  && !CommonUtil.isStringZero(sbcQuotationHistDomain.getOpeningPrice())){
						double riseValue = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
						double riseScope = (riseValue/Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice())) * 100;
						sbcQuotationHistDomain.setRiseValue(CommonUtil.getFormatDouble(riseValue, 2));
						sbcQuotationHistDomain.setRiseScope( CommonUtil.getFormatDouble(riseScope, 2));
         			} else {
         				sbcQuotationHistDomain.setRiseValue("0");
						sbcQuotationHistDomain.setRiseScope( "0");
         			}
				} else {
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(yesterDomain.getClosePrice())  && !CommonUtil.isStringZero(yesterDomain.getClosePrice())){
						double riseValue = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(yesterDomain.getClosePrice());
						double riseScope = (riseValue/Double.parseDouble(yesterDomain.getClosePrice())) * 100;
						sbcQuotationHistDomain.setRiseValue(CommonUtil.getFormatDouble(riseValue, 2));
						sbcQuotationHistDomain.setRiseScope( CommonUtil.getFormatDouble(riseScope, 2));
         			} else if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice())  && !CommonUtil.isStringZero(sbcQuotationHistDomain.getOpeningPrice())){
						double riseValue = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
						double riseScope = (riseValue/Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice())) * 100;
						sbcQuotationHistDomain.setRiseValue(CommonUtil.getFormatDouble(riseValue, 2));
						sbcQuotationHistDomain.setRiseScope( CommonUtil.getFormatDouble(riseScope, 2));
         			} else {
         				sbcQuotationHistDomain.setRiseValue("0");
						sbcQuotationHistDomain.setRiseScope( "0");
         			}
				}
				
				sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));
				sbcQuotationHistDao.addSbcQuotationHist(sbcQuotationHistDomain, exId);	
     		}
		} 
     		sbcQuotationHistDomain = null;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	保存周K图（日后完善）周一去生成上周的K图
	 * Method:		inputSbcQuotationWeekDetail
	 * Author:		1.0 created by lijiaxuan at 2015年10月18日 下午4:14:25
	 *--------------------------------------------------------------------------------------
	public void calSbcQuotationWeekDetail(String exIdList, String date){	
		SbcQuotationHistDomain sbcQuotationHistDomain = new SbcQuotationHistDomain();
		String exchangeIds[]  = null;
		//> 1. 遍历每个交易所
		if (exIdList == null){
			exchangeIds  = CommonMaps.exchangeIds;
		} else {
			String tmpList[] = exIdList.split(",");
			exchangeIds = tmpList;
		}

		int listSize = exchangeIds.length;
		String exId = null;
		String quotation = null;
		String sbcId = null;
		double dealValueD = 0;
		double dealVolumeD = 0;
		double tmpPriceD = 0;
		double highestPriceD = 0;
		double lowestPriceD = 0;
		
		String openPrice = "";
		String closePrice = "";
		String today = "";
		if (date!=null){
			 today = date;
		} else {
			 today = CommonUtil.getNowDate();
		}
		
		String lastWeekDay = null;
		String lastWeekLastWeekDay = null;
		for (int i = 0; i < listSize; i++){
			exId = exchangeIds[i];		//获取交易所ID
			lastWeekDay = tradeCalendarAL.getLastWeekDay(today, exId);	//上周末
			if (lastWeekDay == null){ //由于exId不在指定的抓取列表中，所以跳过
				continue;
			}
			lastWeekLastWeekDay = tradeCalendarAL.getLastWeekDay(lastWeekDay, exId); //上上周末
			Map<String,String> sbcQuotesMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_QUOTS + exId);//查询该交易所下的所有品种行情
			Iterator<Map.Entry<String, String>> it = sbcQuotesMap.entrySet().iterator();
			while (it.hasNext()) {				
				sbcQuotationHistDomain.init();	
				//> 2. 每个品种，抽取分时行情
				Map.Entry<String, String> entry = it.next();			
				quotation = entry.getValue();				
				Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotation);		//查询品种的ID
				sbcId = quotMap.get("sbcId");	
			
				List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationAfterDate(exId, sbcId, CommonVariables.TIME_INTERVAL_DAY, lastWeekLastWeekDay);
			
	                //获取本周的所有日K信息
	         		//List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationAfterDate( exId, sbcId,  CommonVariables.TIME_INTERVAL_DAY, histList.get(j).getDate(), CommonUtil.getNowYear());
	         		//不是本周第一天
	         		if (afterDomains != null && afterDomains.size() > 0){				
	         			for (int x = 0 ; x < afterDomains.size(); x++){
	         				if (x==0) {
	         					openPrice =  afterDomains.get(x).getOpeningPrice();
	         				}
	         				if (CommonUtil.isStringDigit(afterDomains.get(x).getClosePrice())) {
	         					closePrice =  afterDomains.get(x).getClosePrice();
	         				}
	         				//品种本周截至昨日交易额
	         				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealValue())){
	        					dealValueD += Double.parseDouble(afterDomains.get(x).getDealValue());					
	        				}     				
	        				//品种本周截至昨日交易量
	        				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealVolume())){
	        					dealVolumeD += Double.parseDouble(afterDomains.get(x).getDealVolume());
	        				}
	        				
	        				//品种本周截至昨日最高价
	        				if (CommonUtil.isStringDigit(afterDomains.get(x).getHighestPrice())){
	        					tmpPriceD = Double.parseDouble(afterDomains.get(x).getHighestPrice());
	        					if (x == 0 ) highestPriceD = tmpPriceD;
	        					if (highestPriceD < tmpPriceD ){
	        						highestPriceD = tmpPriceD;
	        					}
	        					
	        				}
	        				//品种本周截至昨日最低价
	        				if (CommonUtil.isStringDigit(afterDomains.get(x).getLowestPrice())){
	        					tmpPriceD = Double.parseDouble(afterDomains.get(x).getLowestPrice());
	        					if (x == 0 ) lowestPriceD = tmpPriceD;
	        					if (lowestPriceD > tmpPriceD ){
	        						lowestPriceD = tmpPriceD;
	        					}    					
	        				}
	         			}
	         			
	         			sbcQuotationHistDomain.setClosePrice(openPrice);
						sbcQuotationHistDomain.setOpeningPrice(closePrice);
						sbcQuotationHistDomain.setHighestPrice(CommonUtil.getFormatDouble(highestPriceD, 2));
						sbcQuotationHistDomain.setLowestPrice(CommonUtil.getFormatDouble(lowestPriceD, 2));
						sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_WEK);
						sbcQuotationHistDomain.setDealValue(CommonUtil.getFormatDouble(dealValueD, 2));
						sbcQuotationHistDomain.setDealVolume(CommonUtil.getFormatDouble(dealVolumeD, 2));
						sbcQuotationHistDomain.setDate(lastWeekDay);//因为是次日批量，所以日期应当为昨天

						SbcQuotationHistDomain yesterDomain = sbcQuotationHistDao.findSbcQuotationHist(exId, sbcId, CommonVariables.TIME_INTERVAL_DAY, lastWeekLastWeekDay);
						
						//System.out.println(yesterDomain.toString());
						//上上一周周末交易日，无数据，表示该品种是新品种,涨跌幅则以开盘价为基准计算
						if (yesterDomain == null) {
							if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice())  && !CommonUtil.isStringZero(sbcQuotationHistDomain.getOpeningPrice())){
								double riseValue = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
								double riseScope = (riseValue/Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice())) * 100;
								sbcQuotationHistDomain.setRiseValue(CommonUtil.getFormatDouble(riseValue, 2));
								sbcQuotationHistDomain.setRiseScope( CommonUtil.getFormatDouble(riseScope, 2));
		         			} else {
		         				sbcQuotationHistDomain.setRiseValue("0");
								sbcQuotationHistDomain.setRiseScope( "0");
		         			}
						} else {
							if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(yesterDomain.getClosePrice())  && !CommonUtil.isStringZero(yesterDomain.getClosePrice())){
								double riseValue = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(yesterDomain.getClosePrice());
								double riseScope = (riseValue/Double.parseDouble(yesterDomain.getClosePrice())) * 100;
								sbcQuotationHistDomain.setRiseValue(CommonUtil.getFormatDouble(riseValue, 2));
								sbcQuotationHistDomain.setRiseScope( CommonUtil.getFormatDouble(riseScope, 2));
		         			} else {
		         				sbcQuotationHistDomain.setRiseValue("0");
								sbcQuotationHistDomain.setRiseScope( "0");
		         			}
						}
						
						sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));
						sbcQuotationHistDao.addSbcQuotationHist(sbcQuotationHistDomain, exId);
	         		} 	
			}
		}
	}
	
	--------------------------------------------------------------------------------------*
	 * Description:	保存月K图（日后完善）
	 * Method:		inputSbcQuotationMonthDetail
	 * Author:		1.0 created by lijiaxuan at 2015年10月18日 下午4:15:10
	 *--------------------------------------------------------------------------------------
	public void calSbcQuotationMonthDetail(String exIdList, String date){
		SbcQuotationHistDomain sbcQuotationHistDomain = new SbcQuotationHistDomain();
		//> 1. 遍历每个交易所
	
		String exchangeIds[]  = null;
		//> 1. 遍历每个交易所
		if (exIdList == null){
			exchangeIds  = CommonMaps.exchangeIds;
		} else {
			String tmpList[] = exIdList.split(",");
			exchangeIds = tmpList;
		}

		int listSize = exchangeIds.length;
		String exId = null;
		String quotation = null;
		String sbcId = null;
		double dealValueD = 0;
		double dealVolumeD = 0;
		double tmpPriceD = 0;
		double highestPriceD = 0;
		double lowestPriceD = 0;
		
		String openPrice = "";
		String closePrice = "";
		String today = null;
		if (date!=null){
			 today = date;
		} else {
			 today = CommonUtil.getNowDate();
		}
		String lastMonthDay = null;
		//String lastMonthFristDay = null;
		String lastMonthMonthDay = null;
		for (int i = 0; i < listSize; i++){
			exId = exchangeIds[i];		//获取交易所ID	
			lastMonthDay = tradeCalendarAL.getLastMonthDay(today, exId);	//上月末
			if (lastMonthDay == null){ //由于exId不在指定的抓取列表中，所以跳过
				continue;
			}
			lastMonthMonthDay = tradeCalendarAL.getLastMonthDay(lastMonthDay, exId); //上上月末
			Map<String,String> sbcQuotesMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_QUOTS + exId);//查询该交易所下的所有品种行情
			Iterator<Map.Entry<String, String>> it = sbcQuotesMap.entrySet().iterator();
			while (it.hasNext()) {				
				sbcQuotationHistDomain.init();	
				//> 2. 每个品种，抽取分时行情
				Map.Entry<String, String> entry = it.next();			
				quotation = entry.getValue();				
				Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotation);		//查询品种的ID
				sbcId = quotMap.get("sbcId");
				
				List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationAfterDate(exId, sbcId, CommonVariables.TIME_INTERVAL_DAY, lastMonthMonthDay);
			
	                //获取本周的所有日K信息
	         		//List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationAfterDate( exId, sbcId,  CommonVariables.TIME_INTERVAL_DAY, histList.get(j).getDate(), CommonUtil.getNowYear());
	         		//不是本周第一天
	         		if (afterDomains != null && afterDomains.size() > 0){				
	         			for (int x = 0 ; x < afterDomains.size(); x++){
	         				if (x==0) {
	         					openPrice =  afterDomains.get(x).getOpeningPrice();
	         				}
	         				if (CommonUtil.isStringDigit(afterDomains.get(x).getClosePrice())) {
	         					closePrice =  afterDomains.get(x).getClosePrice();
	         				}
	         				//品种本周截至昨日交易额
	         				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealValue())){
	        					dealValueD += Double.parseDouble(afterDomains.get(x).getDealValue());					
	        				}     				
	        				//品种本周截至昨日交易量
	        				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealVolume())){
	        					dealVolumeD += Double.parseDouble(afterDomains.get(x).getDealVolume());
	        				}
	        				
	        				//品种本周截至昨日最高价
	        				if (CommonUtil.isStringDigit(afterDomains.get(x).getHighestPrice())){
	        					tmpPriceD = Double.parseDouble(afterDomains.get(x).getHighestPrice());
	        					if (x == 0 ) highestPriceD = tmpPriceD;
	        					if (highestPriceD < tmpPriceD ){
	        						highestPriceD = tmpPriceD;
	        					}
	        					
	        				}
	        				//品种本周截至昨日最低价
	        				if (CommonUtil.isStringDigit(afterDomains.get(x).getLowestPrice())){
	        					tmpPriceD = Double.parseDouble(afterDomains.get(x).getLowestPrice());
	        					if (x == 0 ) lowestPriceD = tmpPriceD;
	        					if (lowestPriceD > tmpPriceD ){
	        						lowestPriceD = tmpPriceD;
	        					}    					
	        				}
	         			}
	         			
	         			sbcQuotationHistDomain.setClosePrice(openPrice);
						sbcQuotationHistDomain.setOpeningPrice(closePrice);
						sbcQuotationHistDomain.setHighestPrice(CommonUtil.getFormatDouble(highestPriceD, 2));
						sbcQuotationHistDomain.setLowestPrice(CommonUtil.getFormatDouble(lowestPriceD, 2));
						sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_MON);
						sbcQuotationHistDomain.setDealValue(CommonUtil.getFormatDouble(dealValueD, 2));
						sbcQuotationHistDomain.setDealVolume(CommonUtil.getFormatDouble(dealVolumeD, 2));
						sbcQuotationHistDomain.setDate(lastMonthDay);//因为是次日批量，所以日期应当为昨天
						
						SbcQuotationHistDomain yesterDomain = sbcQuotationHistDao.findSbcQuotationHist(exId, sbcId, CommonVariables.TIME_INTERVAL_DAY, lastMonthMonthDay);
						
						//上上一周周末交易日，无数据，表示该品种是新品种,涨跌幅则以开盘价为基准计算
						if (yesterDomain == null) {
							if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && !CommonUtil.isStringZero(sbcQuotationHistDomain.getOpeningPrice())){
								double riseValue = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
								double riseScope = (riseValue/Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice())) * 100;
								sbcQuotationHistDomain.setRiseValue(CommonUtil.getFormatDouble(riseValue, 2));
								sbcQuotationHistDomain.setRiseScope( CommonUtil.getFormatDouble(riseScope, 2));
		         			} else {
		         				sbcQuotationHistDomain.setRiseValue("0");
								sbcQuotationHistDomain.setRiseScope( "0");
		         			}
						} else {
							if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(yesterDomain.getClosePrice())  && !CommonUtil.isStringZero(yesterDomain.getClosePrice())) {
								double riseValue = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(yesterDomain.getClosePrice());
								double riseScope = (riseValue/Double.parseDouble(yesterDomain.getClosePrice())) * 100;
								sbcQuotationHistDomain.setRiseValue(CommonUtil.getFormatDouble(riseValue, 2));
								sbcQuotationHistDomain.setRiseScope( CommonUtil.getFormatDouble(riseScope, 2));
		         			} else {
		         				sbcQuotationHistDomain.setRiseValue("0");
								sbcQuotationHistDomain.setRiseScope( "0");
		         			}
						}
						
						sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));
						sbcQuotationHistDao.addSbcQuotationHist(sbcQuotationHistDomain, exId);
	         		} 	
			}
		}
	}
	
	*/
	/*--------------------------------------------------------------------------------------*
	 * Description:	删除指定日期的K线图数据
	 * Method:		deleteSbcQuotationDetail
	 * Author:		1.0 created by lijiaxuan at 2015年10月6日 上午9:28:26
	 *--------------------------------------------------------------------------------------*/
	public void deleteSbcQuotationDetail(String date, String type){
		SbcQuotationHistDomain sbcQuotationHistDomain = new SbcQuotationHistDomain();
		int size = CommonMaps.exchangeIds.length;
		String realDate = "";
		if (type.equals(CommonVariables.TIME_INTERVAL_DAY)) {
			 realDate = date;		 
		} else if (type.equals(CommonVariables.TIME_INTERVAL_WEK)) {
			 realDate = date;
		} else if (type.equals(CommonVariables.TIME_INTERVAL_MON)) {
			 realDate = date;
		} 
		
		if (type.equals("00")){
			sbcQuotationHistDomain.setDate(realDate);		
			sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_DAY);
			for (int i = 0 ; i < size; i++){			
				sbcQuotationHistDao.deleteSbcQuotationHist(sbcQuotationHistDomain, CommonMaps.exchangeIds[i]);
			}	
			sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_WEK);
			for (int i = 0 ; i < size; i++){			
				sbcQuotationHistDao.deleteSbcQuotationHist(sbcQuotationHistDomain, CommonMaps.exchangeIds[i]);
			}
			sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_MON);
			for (int i = 0 ; i < size; i++){			
				sbcQuotationHistDao.deleteSbcQuotationHist(sbcQuotationHistDomain, CommonMaps.exchangeIds[i]);
			}
			
		} else {
			sbcQuotationHistDomain.setDate(realDate);		
			sbcQuotationHistDomain.setType(type);
			for (int i = 0 ; i < size; i++){			
				sbcQuotationHistDao.deleteSbcQuotationHist(sbcQuotationHistDomain, CommonMaps.exchangeIds[i]);
			}		
		}
	
	}
}

