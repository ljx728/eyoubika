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
 * Class:		IniterBatchAL
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年12月1日 上午11:16:24
 *==========================================================================================*/
public class RedisIniterBatchAL  {
	private QuotesRedisDao quotesRedisDao;
	private SbcBasicDao sbcBasicDao;
	private SbcQuotationHistDao sbcQuotationHistDao;
	private TradeCalendarAL tradeCalendarAL;
	

	public QuotesRedisDao getQuotesRedisDao() {
		return quotesRedisDao;
	}


	public void setQuotesRedisDao(QuotesRedisDao quotesRedisDao) {
		this.quotesRedisDao = quotesRedisDao;
	}


	public SbcBasicDao getSbcBasicDao() {
		return sbcBasicDao;
	}


	public void setSbcBasicDao(SbcBasicDao sbcBasicDao) {
		this.sbcBasicDao = sbcBasicDao;
	}


	public SbcQuotationHistDao getSbcQuotationHistDao() {
		return sbcQuotationHistDao;
	}


	public void setSbcQuotationHistDao(SbcQuotationHistDao sbcQuotationHistDao) {
		this.sbcQuotationHistDao = sbcQuotationHistDao;
	}


	public TradeCalendarAL getTradeCalendarAL() {
		return tradeCalendarAL;
	}


	public void setTradeCalendarAL(TradeCalendarAL tradeCalendarAL) {
		this.tradeCalendarAL = tradeCalendarAL;
	}


	/*--------------------------------------------------------------------------------------*
	 * Description:	一次性导入品种三日前收盘价到redis
	 * Method:		migrateThreeDayPrice
	 * Author:		1.0 created by lijiaxuan at  2015年10月23日 下午2:49:44
	 *--------------------------------------------------------------------------------------*/
	public void initThreeDayPrice(){
		String[] exIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };
		int exIdsSize  = exIds.length;
		int sbcIdsSize = 0;
		String sbcId = null;
		SbcQuotationHistDomain resDomain = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> redisMap = new HashMap<String, String>();
		for (int i = 0 ; i < exIdsSize; i++){	
			List<String> sbcIds = quotesRedisDao.hvals(CommonVariables.REDIS_KEY_SBCCODEMAP + exIds[i]);
			sbcIdsSize = sbcIds.size();
			
			for (int j = 0 ; j < sbcIdsSize; j++) {			
				if (sbcIds!=null){
					sbcId = sbcIds.get(j);
				}
				String table = "tp_Quotation_"  + exIds[i];
				map.put("table", table);			
				map.put("sbcId", sbcId);
				map.put("type", CommonVariables.TIME_INTERVAL_DAY);
				map.put("num", "2");
				resDomain = sbcQuotationHistDao.findLastNIntvalSbcQuotation(map);
				if (resDomain != null){
					redisMap.put("threeDayPrice", resDomain.getClosePrice());		
					quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcId, redisMap);
				}			
				
			}
		}
	}

	 
	/*--------------------------------------------------------------------------------------*
	 * Description:	上个交易日的收盘价存入redis
	 * Method:		inputClosePrice
	 * Author:		1.0 created by lijiaxuan at 2015年10月20日 下午9:53:33
	 *--------------------------------------------------------------------------------------
	public void inputClosePrice(){
		 String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
					CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	
		 int listSize = marketIds.length;
		 int histListSize = 0;
         String exId = null;
         String sbcId = null;
         String closePrice = null;
         String date = CommonUtil.getYesterDate();
         Map<String, String> map = new HashMap<String, String>();
         for (int i = 0; i < listSize; i++){
                 exId = marketIds[i];//exchangeBasicDomainList.get(i).getExId();
                 List<SbcQuotationHistDomain> histList = sbcQuotationHistDao.queryDateQuotationHistory(exId, CommonVariables.TIME_INTERVAL_DAY, date);
                 histListSize = histList.size();
                 for (int j = 0; j < histListSize; j++){
                	 closePrice = histList.get(j).getClosePrice();
                	 sbcId = ""+histList.get(j).getSbcId();
                	 //存入到品种详情Redis数据库
                     if (!CommonUtil.isStringNull(closePrice) && !CommonUtil.isStringNull(sbcId)){
                             map.put("closePrice", closePrice);
                             quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId, map);

                     }
                 }
         }
	}*/

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	品种库存量存入redis
	 * Method:		inputSbcVolume
	 * Author:		1.0 created by lijiaxuan at 2015年11月2日 下午5:24:20
	 *--------------------------------------------------------------------------------------*/
	public void inputSbcVolume(){	
		//String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
		//		CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	
	     String sbcId = null; 
	     String sbcVolume = null;
		 Map<String,String> sbcExIdMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_SBCEXIDMAP);
		 Iterator<Map.Entry<String, String>> it = sbcExIdMap.entrySet().iterator();
		 Map<String, String> map = new HashMap<String, String>();
		 while (it.hasNext()) {
			 Map.Entry<String, String> entry = it.next();
				sbcId = entry.getKey();
				SbcBasicDomain domain = sbcBasicDao.findSbcBasic(Integer.parseInt(sbcId));
				sbcVolume = domain.getVolume();
				if (!CommonUtil.isStringNull(sbcVolume)){
                    map.put("sbcVolume", sbcVolume);
                    quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId, map);

            }
		 }
		map = null;
	}
	

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		inputSbcHistory
	 * Author:		1.0 created by lijiaxuan at 2015年11月24日 上午8:55:15
	 *--------------------------------------------------------------------------------------*/
	public void initSbcHistory(){
		// String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
		//			CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };
		initSbcDayHistory(CommonMaps.exchangeIds);
		initSbcWeekHistory(CommonMaps.exchangeIds);
		initSbcMonthHistory(CommonMaps.exchangeIds);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		inputSbcDayHistory
	 * Author:		1.0 created by lijiaxuan at 2015年11月23日 上午10:54:35
	 *--------------------------------------------------------------------------------------*/
	public void initSbcDayHistory( String[] marketIds){
		int listSize = marketIds.length;
		 int histListSize = 0;
	     String exId = null;
	     String sbcId = null;
	     String closePrice = null;
	     String today = CommonUtil.getNowDate();
	  
	   
	     Map<String, String> map = new HashMap<String, String>();
	    //品种上一日收盘价
	     for (int i = 0; i < listSize; i++){
            exId = marketIds[i];
           List<SbcQuotationHistDomain> histList = sbcQuotationHistDao.queryDateQuotationHistory(exId, CommonVariables.TIME_INTERVAL_DAY, tradeCalendarAL.getYesterday(today, exId));
            histListSize = histList.size();
            for (int j = 0; j < histListSize; j++){
           	 closePrice = histList.get(j).getClosePrice();
           	 sbcId = ""+histList.get(j).getSbcId();
           	 //存入到品种详情Redis数据库
                if (!CommonUtil.isStringNull(closePrice) && !CommonUtil.isStringNull(sbcId)){
                        map.put("dayClosePrice", closePrice);
                        quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId, map);                  
                }
              
            } /**/
	     }
	     map = null;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		initSbcVolume
	 * Author:		1.0 created by lijiaxuan at 2015年12月3日 上午9:53:17
	 *--------------------------------------------------------------------------------------*/
	public void initSbcVolume(){	
	     String sbcId = null; 
	     String sbcVolume = null;
		 Map<String,String> sbcExIdMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_SBCEXIDMAP);
		 Iterator<Map.Entry<String, String>> it = sbcExIdMap.entrySet().iterator();
		 Map<String, String> map = new HashMap<String, String>();
		 while (it.hasNext()) {
			 Map.Entry<String, String> entry = it.next();
				sbcId = entry.getKey();
				SbcBasicDomain domain = sbcBasicDao.findSbcBasic(Integer.parseInt(sbcId));
				sbcVolume = domain.getVolume();
				if (!CommonUtil.isStringNull(sbcVolume)){
                    map.put("sbcVolume", sbcVolume);
                    quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId, map);
            }
		 }
		map = null;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		inputSbcWeekHistory
	 * Author:		1.0 created by lijiaxuan at 2015年11月23日 上午10:55:58
	 *--------------------------------------------------------------------------------------*/
	public void initSbcWeekHistory( String[] marketIds){
		//交易所列表
		int listSize = marketIds.length;
		 
	     
		String today = CommonUtil.getNowDate();
		String lastWeekDay ="";
		Map<String, String> map = new HashMap<String, String>();
	     //遍历交易所
	     for (int i = 0; i < listSize; i++){
	    	 int histListSize = 0;
		     String exId = null;
		     String sbcId = null;
		   
             exId = marketIds[i];
            
             lastWeekDay = tradeCalendarAL.getLastWeekDay(today, exId);
             //获取上一周的K图数据， K图生成日期是上周最后一天
             List<SbcQuotationHistDomain> histList = sbcQuotationHistDao.queryDateQuotationHistory(exId, CommonVariables.TIME_INTERVAL_WEK, lastWeekDay);
             histListSize = histList.size();
             //遍历品种
             for (int j = 0; j < histListSize; j++){
            	  String closePrice = null;
     	    	 double dealValueD = 0;
     			double dealVolumeD = 0;
     			double highestPriceD = 0;
     			double lowestPriceD = 0;
     			double tmpPriceD = 0;
            	 closePrice = histList.get(j).getClosePrice();
            	 sbcId = ""+histList.get(j).getSbcId();
            	 //品种上一周收盘价存入到品种详情Redis数据库
                 if (!CommonUtil.isStringNull(closePrice) && !CommonUtil.isStringNull(sbcId)){
                         map.put("weekClosePrice", closePrice);
                         quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId, map);

                 }
                //获取本周的所有日K信息
                 List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationAfterDate( exId, sbcId,  CommonVariables.TIME_INTERVAL_DAY, lastWeekDay);
         		//List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationAfterDate( exId, sbcId,  CommonVariables.TIME_INTERVAL_DAY, histList.get(j).getDate(), CommonUtil.getNowYear());
         		//不是本周第一天
                
         		if (afterDomains != null && afterDomains.size() > 0){				
         			for (int x = 0 ; x < afterDomains.size(); x++){
         				if (x==0) {
         					map.put("weekOpenPrice", afterDomains.get(x).getOpeningPrice());
         				}
         				//品种本周截至昨日交易额
         				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealValue())){
        					dealValueD += Double.parseDouble(afterDomains.get(x).getDealValue());
        					
        				}     				
        				//品种本周截至昨日交易量
        				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealVolume())){
        					dealVolumeD += Double.parseDouble(afterDomains.get(x).getDealVolume());
        					 System.out.println("dealVolumeD: " + dealVolumeD);
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
         			map.put("weekDealValue", CommonUtil.getFormatDouble(dealValueD, 2));
         			map.put("weekDealVolume", CommonUtil.getFormatDouble(dealVolumeD, 2));
         			map.put("weekHighestPrice", CommonUtil.getFormatDouble(highestPriceD, 2));
         			map.put("weekLowestPrice", CommonUtil.getFormatDouble(lowestPriceD, 2));	
         			quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId, map);      				
         			} 
         		  System.out.println("++==============================");
             }
	     }
	     map = null;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		inputSbcMonthHistory
	 * Author:		1.0 created by lijiaxuan at 2015年11月23日 上午10:56:30
	 *--------------------------------------------------------------------------------------*/
	public void initSbcMonthHistory( String[] marketIds){
		//交易所列表
		int listSize = marketIds.length;
		
	     String today = CommonUtil.getNowDate();
	    
				  Map<String, String> map = new HashMap<String, String>();
	     //遍历交易所
	     for (int i = 0; i < listSize; i++){
	    	 int histListSize = 0;
		     String exId = null;
		     String sbcId = null;
		     String lastMonthDay = "";
             exId = marketIds[i];
             lastMonthDay = tradeCalendarAL.getLastMonthDay(today, exId);
             List<SbcQuotationHistDomain> histList = sbcQuotationHistDao.queryDateQuotationHistory(exId, CommonVariables.TIME_INTERVAL_MON, lastMonthDay);
             histListSize = histList.size();
             //遍历品种
             for (int j = 0; j < histListSize; j++){
            	 String closePrice = null;
    		     double dealValueD = 0;
    		    
    					double dealVolumeD = 0;
    					double highestPriceD = 0;
    					double lowestPriceD = 0;
    					double tmpPriceD = 0;
            	 closePrice = histList.get(j).getClosePrice();
            	 sbcId = ""+histList.get(j).getSbcId();
            	 //品种上一周收盘价存入到品种详情Redis数据库
                 if (!CommonUtil.isStringNull(closePrice) && !CommonUtil.isStringNull(sbcId)){
                         map.put("monthClosePrice", closePrice);
                         quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId, map);

                 }
                //获取本月的所有日K信息
                 
                 List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationAfterDate( exId, sbcId,  CommonVariables.TIME_INTERVAL_DAY, tradeCalendarAL.getLastMonthDay(today, exId));
                 
                 //List<SbcQuotationHistDomain> afterDomains = sbcQuotationHistDao.querySbcQuotationAfterDate( exId, sbcId,  CommonVariables.TIME_INTERVAL_DAY, histList.get(j).getDate(), CommonUtil.getNowYear());
         		//不是本月第一天
         		if (afterDomains != null && afterDomains.size() > 0){				
         			for (int x = 0 ; x < afterDomains.size(); x++){
         				if (x==0) {
         					map.put("monthOpenPrice", afterDomains.get(x).getOpeningPrice());
         				}
         				//品种本月截至昨日交易额
         				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealValue())){
        					dealValueD += Double.parseDouble(afterDomains.get(x).getDealValue());					
        				}     				
        				//品种本月截至昨日交易量
        				if (CommonUtil.isStringDigit(afterDomains.get(x).getDealVolume())){
        					dealVolumeD += Double.parseDouble(afterDomains.get(x).getDealVolume());
        				}
        				
        				//品种本月截至昨日最高价
        				if (CommonUtil.isStringDigit(afterDomains.get(x).getHighestPrice())){
        					tmpPriceD = Double.parseDouble(afterDomains.get(x).getHighestPrice());
        					if (x == 0 ) highestPriceD = tmpPriceD;
        					if (highestPriceD < tmpPriceD ){
        						highestPriceD = tmpPriceD;
        					}
        					
        				}
        				//品种本月截至昨日最低价
        				if (CommonUtil.isStringDigit(afterDomains.get(x).getLowestPrice())){
        					tmpPriceD = Double.parseDouble(afterDomains.get(x).getLowestPrice());
        					if (x == 0 ) lowestPriceD = tmpPriceD;
        					if (lowestPriceD > tmpPriceD ){
        						lowestPriceD = tmpPriceD;
        					}    					
        				}
         			}
         			map.put("monthDealValue", CommonUtil.getFormatDouble(dealValueD, 2));
         			map.put("monthDealVolume", CommonUtil.getFormatDouble(dealVolumeD, 2));
         			map.put("monthHighestPrice", CommonUtil.getFormatDouble(highestPriceD, 2));
         			map.put("monthLowestPrice", CommonUtil.getFormatDouble(lowestPriceD, 2));	
         			quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId, map);      				
         			} 
             }
	     } 
	     map = null;
	}
	



	/*--------------------------------------------------------------------------------------*
	 * Description:	清空内存数据
	 * Method:		cleanTChart
	 * Author:		1.0 created by lijiaxuan at 2015年9月17日 下午2:51:21
	 *--------------------------------------------------------------------------------------*/
	public void cleanRedis(){
		Map<String,String> sbcExIdMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_SBCEXIDMAP);
		Iterator<Map.Entry<String, String>> it = sbcExIdMap.entrySet().iterator();
		String sbcId = null;	
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			sbcId = entry.getKey();
			quotesRedisDao.del(CommonVariables.REDIS_KEY_TIMESHARE + sbcId);  
			quotesRedisDao.del(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId);  
		}	
		int size = CommonMaps.exchangeIds.length;
		for (int i = 0; i < size; i++){
			quotesRedisDao.del(CommonVariables.REDIS_KEY_QUOTS + CommonMaps.exchangeIds[i]);   
			quotesRedisDao.del(CommonVariables.REDIS_KEY_SBCCODEMAP+ CommonMaps.exchangeIds[i]);  
		}
		quotesRedisDao.del(CommonVariables.REDIS_KEY_SBCEXIDMAP); 
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	清空redis的品种数据，保留了qu:xxxxxx和tm:xxxxxx
	 * Method:		cleanRedis
	 * Author:		1.0 created by lijiaxuan at 2015年10月20日 下午5:04:29
	 *--------------------------------------------------------------------------------------*/
	public void cleanRedisSbc(){
		//String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
		//		CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	
		Map<String,String> sbcExIdMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_SBCEXIDMAP); //交易所列表
		Iterator<Map.Entry<String, String>> it = sbcExIdMap.entrySet().iterator();
		String sbcId = null;
		String exId = null;
		
		int size = CommonMaps.exchangeIds.length; // marketIds.length;
		TradeCalendarDomain	tradeCalendarDomain = tradeCalendarAL.getTradeDay(CommonUtil.getNowDate());
		String type = tradeCalendarDomain.getType(); //获取当天的日期的类型
		 //周一到周五要全部清空
		if (CommonVariables.COMMON_LEVEL_01.equals(type) ||CommonVariables.COMMON_LEVEL_03.equals(type) 
			|| CommonVariables.COMMON_LEVEL_05.equals(type) ||CommonVariables.COMMON_LEVEL_07.equals(type) 	){
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				sbcId = entry.getKey();	
				exId = entry.getValue();
				quotesRedisDao.del(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId);  
			}			
			for (int i = 0; i < size; i++){
				//quotesRedisDao.del(CommonVariables.REDIS_KEY_QUOTS + marketIds[i]);  
				quotesRedisDao.del(CommonVariables.REDIS_KEY_SBCCODEMAP+ CommonMaps.exchangeIds[i]);  
			}
			quotesRedisDao.del(CommonVariables.REDIS_KEY_SBCEXIDMAP); 			
		} else if (CommonVariables.COMMON_LEVEL_04.equals(type) ||CommonVariables.COMMON_LEVEL_08.equals(type) ){
			//周六只清空部分
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				sbcId = entry.getKey();	
				exId = entry.getValue();			
				if (CommonMaps.tradeDayMap.get(exId).equals(CommonVariables.COMMON_LEVEL_04)){
					quotesRedisDao.del(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId);  
				}
				
			}			
			for (int i = 0; i < size; i++){
				if (CommonMaps.tradeDayMap.get( CommonMaps.exchangeIds[i]).equals(CommonVariables.COMMON_LEVEL_04)){
					quotesRedisDao.del(CommonVariables.REDIS_KEY_SBCCODEMAP+ CommonMaps.exchangeIds[i]);  
				}
				
			}
			quotesRedisDao.del(CommonVariables.REDIS_KEY_SBCEXIDMAP); 
		}	
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		cleanRedisTChart
	 * Author:		1.0 created by lijiaxuan at 2015年12月1日 下午2:52:58
	 *--------------------------------------------------------------------------------------*/
	public void cleanRedisTChart(){
		//String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
		//		CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	
		Map<String,String> sbcExIdMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_SBCEXIDMAP); //交易所列表
		Iterator<Map.Entry<String, String>> it = sbcExIdMap.entrySet().iterator();
		String sbcId = null;
		String exId = null;
		int sleepTime = 60000; 		//每隔60s检查一次
		int size = CommonMaps.exchangeIds.length; // marketIds.length;
		TradeCalendarDomain	tradeCalendarDomain = tradeCalendarAL.getTradeDay(CommonUtil.getNowDate());
		String type = tradeCalendarDomain.getType(); //获取当天的日期的类型
		boolean notDone = true;
		while (notDone)//
		 {
			System.out.println("1.......");
			//if (1==1){ 
			if (CommonUtil.isInTime(CommonVariables.BATCH_EXE_AM, CommonVariables.TRADEHOUR_BEG_AM)){ //分时图生成必须要晚于行情数据的抓取)
			//if (CommonUtil.isInTime("10:01:00", "10:45:00")){ //分时图生成必须要晚于行情数据的抓取)
				//普通交易日：周一-周五
				System.out.println("2.......");
				
				if (CommonVariables.COMMON_LEVEL_01.equals(type) ||CommonVariables.COMMON_LEVEL_03.equals(type) 
						|| CommonVariables.COMMON_LEVEL_05.equals(type) ||CommonVariables.COMMON_LEVEL_07.equals(type) 	){
					System.out.println("in putong...");
					while (it.hasNext()) {
						Map.Entry<String, String> entry = it.next();
						System.out.println("entry.getKey()..." +entry.getKey());
						sbcId = entry.getKey();	
						exId = entry.getValue();
						//删除所有交易所的分时图
						quotesRedisDao.del(CommonVariables.REDIS_KEY_TIMESHARE + sbcId);  
					}	
					notDone  = false;
				//特殊交易日：周六
				} else if (CommonVariables.COMMON_LEVEL_04.equals(type) ||CommonVariables.COMMON_LEVEL_08.equals(type) ){
					System.out.println("in teshu...");
					while (it.hasNext()) {
						Map.Entry<String, String> entry = it.next();
						sbcId = entry.getKey();	
						exId = entry.getValue();
						//只删除部分交易所的分时图
						if (CommonMaps.tradeDayMap.get(exId).equals(CommonVariables.COMMON_LEVEL_04)){
							quotesRedisDao.del(CommonVariables.REDIS_KEY_TIMESHARE + sbcId);  
						}				
					}	
				}
					
			} else if (CommonUtil.isAfterTime(CommonVariables.TRADEHOUR_BEG_AM)) {
				notDone  = false; //如果开盘之后，则强制成功清除，跳出循环
			} else {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		 }
	}
}

