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
import com.eyoubika.sbc.dao.ExchangeBasicDao;
import com.eyoubika.sbc.dao.SbcBasicDao;
import com.eyoubika.sbc.dao.SbcQuotationHistDao;
import com.eyoubika.sbc.domain.ExchangeBasicDomain;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.sbc.domain.SbcQuotationHistDomain;
import com.eyoubika.spider.dao.NewsArticleDao;
import com.eyoubika.spider.dao.QuotesRedisDao;
import com.eyoubika.info.domain.QuotationsDomain;
import com.eyoubika.info.domain.QuotationsRefreshDomain;
import com.eyoubika.common.PageInfo;

import java.math.*;
/*==========================================================================================*
 * Description:	邮币卡行情模块
 * 				1. 提供品种行情列表，详细信息、龙虎榜的查询。
 * 				2. 生成分时图、分量图、K线图、龙虎榜。
 * 				3. 日终行情数据存储。
 * Class:		QuotationsAL
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-6-27 10:19:52
 *==========================================================================================*/
public class QuotationsBatchAL  {
	private QuotesRedisDao quotesRedisDao;

	public QuotesRedisDao getQuotesRedisDao() {
		return quotesRedisDao;
	}

	public void setQuotesRedisDao(QuotesRedisDao quotesRedisDao) {
		this.quotesRedisDao = quotesRedisDao;
	}
		

	/*--------------------------------------------------------------------------------------*
	 * Description:	生成每个品种的波动率
	 * Method:		inputVolatility
	 * Author:		1.0 created by lijiaxuan at 2015年10月23日 下午2:39:03
	 *--------------------------------------------------------------------------------------*/
	public void inputVolatility(){
		String[] exIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };
		int exIdsSize  = exIds.length;
		int quotesSize = 0;
		String highestPrice = null;
		String lowestPrice = null;
		String closePrice = null;
		String sbcId = null;
		double volatilityD = 0;
		int sleepTime = 30000; 		//每隔30s生成一次
		int waitSleepTime = 5000; 	//每5s循环检查生成器是否运行
		Map<String, String> map = new HashMap<String, String>();	
		while (true)//
		 {
			if (CommonUtil.isInTime(CommonVariables.TRADESHARE_BEG_AM, CommonVariables.TRADEHOUR_END_AM) //分时图生成必须要晚于行情数据的抓取
				|| CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_PM, CommonVariables.TRADEHOUR_END_PM)){
			
				for (int i = 0 ; i < exIdsSize; i++){
					List<String> quotes = quotesRedisDao.hvals(CommonVariables.REDIS_KEY_QUOTS + exIds[i]);
					quotesSize = quotes.size();
					for (int j = 0 ; j < quotesSize; j++) {
						Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotes.get(j));	
						if (quotMap!=null){
							sbcId = quotMap.get("sbcId");
							highestPrice = quotMap.get("highestPrice");
							lowestPrice = quotMap.get("lowestPrice");
							closePrice = quotesRedisDao.getDayClosePrice(sbcId);
							//if (closePrice == null || sbcName.contains("指数")) { 	//没有收盘价或者是指数则跳过
							if (closePrice == null || !CommonUtil.isStringDigit(closePrice) || quotMap.get("sbcName").contains("指数") ) { 	//没有收盘价则跳过
								continue;
							}
							if (CommonUtil.isStringDigit(highestPrice) && CommonUtil.isStringDigit(lowestPrice) &&  ! CommonUtil.isStringZero(closePrice)){
								volatilityD = ((Double.parseDouble(highestPrice) - Double.parseDouble(lowestPrice)) / Double.parseDouble(closePrice))*100;
								map.put("volatility", ""+volatilityD);
								//System.out.println("volatilityD " + volatilityD);
								quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcId, map);
							}
						}						
					}		
				}
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}else {
						if (CommonUtil.isBeforeTime(CommonVariables.TRADEHOUR_BEG_AM) || CommonUtil.isInTime(CommonVariables.TRADEHOUR_END_AM, CommonVariables.TRADEHOUR_BEG_PM)){
							//如果早于时间则等待或者，中午时分。
							 try {
								Thread.sleep(waitSleepTime);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (CommonUtil.isAfterTime(CommonVariables.TRADEHOUR_END_PM)) {
							//晚于时间则跳出。
							break;
						}
					}
				
		 }
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	生成每个交易所的总市值
	 * Method:		inputMarketValue
	 * Author:		1.0 created by lijiaxuan at 2015年11月12日 下午2:39:03
	 *--------------------------------------------------------------------------------------*/
	public void inputMarketValue(){
		String[] exIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };
		int exIdsSize  = exIds.length;
		int quotesSize = 0;
		String marketValue = null;
		String sbcId = null;
		double marketValueD = 0;	
		int sleepTime = 5000; 		//每隔5s生成一次
		int waitSleepTime = 5000;	//每5s循环检查生成器是否运行
		Map<String, String> map = new HashMap<String, String>();	
		while (true)//
		 {
			if (CommonUtil.isInTime(CommonVariables.TRADESHARE_BEG_AM, CommonVariables.TRADEHOUR_END_AM) //分时图生成必须要晚于行情数据的抓取
				|| CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_PM, CommonVariables.TRADEHOUR_END_PM)){
			
				for (int i = 0 ; i < exIdsSize; i++){
					List<String> quotes = quotesRedisDao.hvals(CommonVariables.REDIS_KEY_QUOTS + exIds[i]);
					quotesSize = quotes.size();
					marketValueD = 0;
					for (int j = 0 ; j < quotesSize; j++) {
						Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotes.get(j));	
						if (quotMap!=null){		
							//如果是普通品种，则累加其市值
							if ( !quotMap.get("sbcName").contains("指数") && CommonUtil.isStringDigit(quotMap.get("marketValue")) ){
								marketValue = quotMap.get("marketValue");
								marketValueD += Double.parseDouble(marketValue);				
							} else if (quotMap.get("sbcName").equals("综合指数") || quotMap.get("sbcName").equals("邮币综合指数")) {
								//获取综合指数的SbcId
							System.out.println("quotes.get(j) "+quotes.get(j));
								sbcId = quotMap.get("sbcId");
							}		
						}						
					}	
					map.put("marketValue", CommonUtil.getFormatDouble(marketValueD, 2));
					quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcId, map);
				}
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}else {
						if (CommonUtil.isBeforeTime(CommonVariables.TRADEHOUR_BEG_AM) || CommonUtil.isInTime(CommonVariables.TRADEHOUR_END_AM, CommonVariables.TRADEHOUR_BEG_PM)){
							//如果早于时间则等待或者，中午时分。
							 try {
								Thread.sleep(waitSleepTime);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (CommonUtil.isAfterTime(CommonVariables.TRADEHOUR_END_PM)) {
							//晚于时间则跳出。
							break;
						}
					}
				
		 }
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	生成五分钟涨幅数据
	 * Method:		inputFiveMinRise
	 * Author:		1.0 created by lijiaxuan at 2015年10月23日 下午3:02:31
	 *--------------------------------------------------------------------------------------*/
	public void inputFiveMinRise(){
		String[] exIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };
		int exIdsSize  = exIds.length;
		int quotesSize = 0;
		String currentPrice = null;
		String closePrice = null;
		String sbcId = null;
		List<String> fiveQuotes = null;
		String fivePrice = null;			
		double fiveMinRiseD = 0;
		int lrangeSize = 0;
		int sleepTime = 30000; 		//每隔30s生成一次
		int waitSleepTime = 5000; 	//每5s循环检查生成器是否运行
		
		Map<String, String> map = new HashMap<String, String>();
		while (true)//
		 {
			if (CommonUtil.isInTime(CommonVariables.TRADESHARE_BEG_AM, CommonVariables.TRADEHOUR_END_AM) //分时图生成必须要晚于行情数据的抓取
				|| CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_PM, CommonVariables.TRADEHOUR_END_PM)){
			
				for (int i = 0 ; i < exIdsSize; i++){
					List<String> quotes = quotesRedisDao.hvals(CommonVariables.REDIS_KEY_QUOTS + exIds[i]);
					quotesSize = quotes.size();
					for (int j = 0 ; j < quotesSize; j++) {
						Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotes.get(j));	
						if (quotMap!=null){
							sbcId = quotMap.get("sbcId");
							currentPrice = quotMap.get("currentPrice");
							fiveQuotes = quotesRedisDao.lrange(CommonVariables.REDIS_KEY_TIMESHARE + sbcId, 4, 4);
							lrangeSize = fiveQuotes.size();
							if (lrangeSize > 0){
								fivePrice = fiveQuotes.get(0).split(",")[0];
							}
							closePrice = quotesRedisDao.getDayClosePrice(sbcId);
							//if (closePrice == null || sbcName.contains("指数")) { 	//没有收盘价或者是指数则跳过
							if (closePrice == null || !CommonUtil.isStringDigit(closePrice) || quotMap.get("sbcName").contains("指数") ) { 	//没有收盘价则跳过
								continue;
							}
							if (CommonUtil.isStringDigit(fivePrice) && CommonUtil.isStringDigit(currentPrice) &&  ! CommonUtil.isStringZero(closePrice)){
								fiveMinRiseD = ((Double.parseDouble(currentPrice) - Double.parseDouble(fivePrice)) / Double.parseDouble(closePrice))*100;
								map.put("fiveMinRise", ""+fiveMinRiseD);
						
								quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcId, map);
							}
						}						
					}				
				}
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
					if (CommonUtil.isBeforeTime(CommonVariables.TRADEHOUR_BEG_AM) || CommonUtil.isInTime(CommonVariables.TRADEHOUR_END_AM, CommonVariables.TRADEHOUR_BEG_PM)){
						//如果早于时间则等待或者，中午时分。
						 try {
							Thread.sleep(waitSleepTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (CommonUtil.isAfterTime(CommonVariables.TRADEHOUR_END_PM)) {
						//晚于时间则跳出。
						break;
					}
				}
		 }
	}
	

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	计算换手率数据
	 * Method:		inputTurnoverRate
	 * Author:		1.0 created by lijiaxuan at 2015年11月3日 下午3:49:44
	 *--------------------------------------------------------------------------------------*/
	public void inputTurnoverRate(){
		String[] exIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };
		int exIdsSize  = exIds.length;
		int quotesSize = 0;
		String turnoverRate = null;
		String sbcId = null;		
		int sleepTime = 30000; 		//每30s生成一次
		int waitSleepTime = 5000;   //每5s循环检查生成器是否运行
		String closePrice = null;
		Map<String, String> map = new HashMap<String, String>();
		while (true)//
		 {
			if (CommonUtil.isInTime(CommonVariables.TRADESHARE_BEG_AM, CommonVariables.TRADEHOUR_END_AM) //分时图生成必须要晚于行情数据的抓取
				|| CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_PM, CommonVariables.TRADEHOUR_END_PM)){
			
				for (int i = 0 ; i < exIdsSize; i++){
					List<String> quotes = quotesRedisDao.hvals(CommonVariables.REDIS_KEY_QUOTS + exIds[i]);
					quotesSize = quotes.size();
					for (int j = 0 ; j < quotesSize; j++) {
						
						Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotes.get(j));	
						if (quotMap!=null){
							sbcId = quotMap.get("sbcId");
							turnoverRate = quotMap.get("turnoverRate");
							closePrice = quotesRedisDao.getDayClosePrice(sbcId);
							if (quotMap.get("sbcName").contains("指数") ) { 	//没有收盘价则跳过
								continue;
							}
							
							if ( CommonUtil.isStringDigit(turnoverRate)){
								map.put("turnoverRate", turnoverRate);	
								quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcId, map);
							}
						}						
					}				
				}
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
					if (CommonUtil.isBeforeTime(CommonVariables.TRADEHOUR_BEG_AM) || CommonUtil.isInTime(CommonVariables.TRADEHOUR_END_AM, CommonVariables.TRADEHOUR_BEG_PM)){
						//如果早于时间则等待或者，中午时分。
						 try {
							Thread.sleep(waitSleepTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (CommonUtil.isAfterTime(CommonVariables.TRADEHOUR_END_PM)) {
						//晚于时间则跳出。
						break;
					}
				}
		 }
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	生成三日涨幅数据
	 * Method:		inputThreeDayRise
	 * Author:		1.0 created by lijiaxuan at 2015年10月23日 下午3:02:31
	 *--------------------------------------------------------------------------------------*/
	public void inputThreeDayRise(){
		String[] exIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };
		int exIdsSize  = exIds.length;
		int quotesSize = 0;
		int sleepTime = 60000; 		//每1m生成三日涨幅数据
		int waitSleepTime = 5000;   //每5s循环检查生成器是否运行
		String currentPrice = null;
		String closePrice = null;
		String sbcId = null;
		double threeDayRiseD = 0;
		Map<String, String> map = new HashMap<String, String>();
		while (true)//
		 {
			if (CommonUtil.isInTime(CommonVariables.TRADESHARE_BEG_AM, CommonVariables.TRADEHOUR_END_AM) //分时图生成必须要晚于行情数据的抓取
				|| CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_PM, CommonVariables.TRADEHOUR_END_PM)){
		
				for (int i = 0 ; i < exIdsSize; i++){
					List<String> quotes = quotesRedisDao.hvals(CommonVariables.REDIS_KEY_QUOTS + exIds[i]);
					quotesSize = quotes.size();
					for (int j = 0 ; j < quotesSize; j++) {
						Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotes.get(j));	
						if (quotMap!=null){
							sbcId = quotMap.get("sbcId");
							currentPrice = quotMap.get("currentPrice");
							closePrice = quotesRedisDao.hget(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId, "threeDayPrice");	
										
							//if (closePrice == null || sbcName.contains("指数")) { 	//没有收盘价或者是指数则跳过
							if (closePrice == null || !CommonUtil.isStringDigit(closePrice) || quotMap.get("sbcName").contains("指数") ) { 	//没有收盘价则跳过
								continue;
							}
							if (CommonUtil.isStringDigit(currentPrice) && ! CommonUtil.isStringZero(closePrice)){
								threeDayRiseD = ((Double.parseDouble(currentPrice) - Double.parseDouble(closePrice)) / Double.parseDouble(closePrice))*100;
								
								map.put("threeDayRise",  CommonUtil.getFormatDouble(threeDayRiseD, 5));
								quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcId, map);
							}
						}						
					}	
					
				}
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
					if (CommonUtil.isBeforeTime(CommonVariables.TRADEHOUR_BEG_AM) || CommonUtil.isInTime(CommonVariables.TRADEHOUR_END_AM, CommonVariables.TRADEHOUR_BEG_PM)){
						//如果早于时间则等待或者，中午时分。
						 try {
							Thread.sleep(waitSleepTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (CommonUtil.isAfterTime(CommonVariables.TRADEHOUR_END_PM)) {
						//晚于时间则跳出。
						break;
					}
				}
		 }
	}
}

