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
public class QuotationsAL  {
	private QuotesRedisDao quotesRedisDao;
	private SbcBasicDao sbcBasicDao;
	private ExchangeBasicDao exchangeBasicDao;
	private SbcQuotationHistDao sbcQuotationHistDao;
	
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
		
	public SbcBasicDao getSbcBasicDao() {
		return sbcBasicDao;
	}

	public void setSbcBasicDao(SbcBasicDao sbcBasicDao) {
		this.sbcBasicDao = sbcBasicDao;
	}

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取所有交易所的品种行情列表
	 * Method:		getQuotationList
	 * Author:		1.0 created by lijiaxuan at 2015年8月12日 下午4:55:35
	 *--------------------------------------------------------------------------------------*/
	public List<String> getQuotationList(){
		String exIds = CommonVariables.EXCHANGE_NANJING + "," + CommonVariables.EXCHANGE_NANFANG + "," +
				CommonVariables.EXCHANGE_FULITE + "," + 
				CommonVariables.EXCHANGE_JINMAJIA + "," + CommonVariables.EXCHANGE_SHANGHAI + "," +
				CommonVariables.EXCHANGE_YIJIAO + "," + CommonVariables.EXCHANGE_ZHONGNAN ;
		/*String exIds = CommonVariables.EXCHANGE_NANJING + "," + CommonVariables.EXCHANGE_NANFANG + "," +
				CommonVariables.EXCHANGE_FULITE + "," + CommonVariables.EXCHANGE_HUAXIA + "," +
				CommonVariables.EXCHANGE_HUAZHONG + "," + CommonVariables.EXCHANGE_JIANGSU + "," +
				CommonVariables.EXCHANGE_JINMAJIA + "," + CommonVariables.EXCHANGE_SHANGHAI + "," +
				CommonVariables.EXCHANGE_YIJIAO + "," + CommonVariables.EXCHANGE_ZHONGNAN ;
				*/
		return getQuotationList(exIds);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取指定交易所的品种行情列表
	 * Method:		getQuotationList
	 * Author:		1.0 created by lijiaxuan at 2015年8月12日 下午2:31:11
	 *--------------------------------------------------------------------------------------*/
	public List<String> getQuotationList(String exId){
		List<String> quotationsList = new  ArrayList<String>();	
		String[] exIds = exId.split(",");
		int size = exIds.length;
		for (int i = 0; i < size; i++){
			exId = exIds[i].trim();	
			List<String> quotations =  quotesRedisDao.queryQuotations(exId);
			quotationsList.addAll(quotations); 
		}		
		return quotationsList;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取单个品种的行情
	 * Method:		getSbcQuotation
	 * Author:		1.0 created by lijiaxuan at 2015年8月25日 下午2:43:35
	 *--------------------------------------------------------------------------------------*/
	public String getSbcQuotation(String exId, String sbcId){
		//> 2. 获取sbc的交易所ID
		if (CommonUtil.isStringNull(exId)){
			List<String> tmp = quotesRedisDao.hmget(CommonVariables.REDIS_KEY_SBCEXIDMAP, sbcId);
			if (tmp!=null && tmp.size() > 0){
				exId = tmp.get(0);
			}
		}
		String quotations =  quotesRedisDao.queryQuotations(exId, sbcId);	
		return quotations;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	一次性获取一个交易所下的所有品种的行情
	 * Method:		getAllExSbcQuotations
	 * Author:		1.0 created by lijiaxuan at 2015年10月21日 下午3:30:08
	 *--------------------------------------------------------------------------------------*/
	public List<String> getAllExSbcQuotations(){
		List<String> res = new ArrayList<String>();
		String[] exIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	
		int exIdsSize  = exIds.length;
		for (int i = 0 ; i < exIdsSize; i++){
			res.addAll(quotesRedisDao.hvals(exIds[i]));
		}
		return res;
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
							closePrice = quotesRedisDao.getClosePrice(sbcId);
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
							closePrice = quotesRedisDao.getClosePrice(sbcId);
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
	 * Description:	一次性导入品种三日前收盘价到redis
	 * Method:		migrateThreeDayPrice
	 * Author:		1.0 created by lijiaxuan at  2015年10月23日 下午2:49:44
	 *--------------------------------------------------------------------------------------*/
	public void migrateThreeDayPrice(){
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
				String table = "tp_Quotation_" + CommonUtil.getNowYear() + "_" + exIds[i];
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
		migrateThreeDayPrice();
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
	
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	龙虎榜查询
	 * Method:		getBillboard
	 * Author:		1.0 created by lijiaxuan at 2015年10月16日 下午5:47:05
	 *--------------------------------------------------------------------------------------*/
	public List<String> getBillboard( String exId, String type){
		List<String> quList =  new ArrayList<String>();
		int exIdsSize = 0;
		String sortType = null;
		List<RankData> rankDataList = null;
		List<String> exIdList = new ArrayList<String>();
		//> 2. 获取sbc的交易所ID
		if (CommonUtil.isStringNull(exId)){
				exId = CommonVariables.EXCHANGE_NANJING + "," + CommonVariables.EXCHANGE_NANFANG + "," +
					CommonVariables.EXCHANGE_FULITE + "," + 
					CommonVariables.EXCHANGE_JINMAJIA + "," + CommonVariables.EXCHANGE_SHANGHAI + "," +
					CommonVariables.EXCHANGE_YIJIAO + "," + CommonVariables.EXCHANGE_ZHONGNAN ;
		
		}
		String[] exIds = exId.split(",");
		exIdsSize = exIds.length;
		HeapSort heapSort = new HeapSort();
		int sbcIdsSize = 0;
		List<String> sbcIds =  null;//new ArrayList<String>();
		switch (type){		
			case CommonVariables.RANK_VOLATILITY: {		//当日波动
				sortType = "volatility";
				break;
			}
			case CommonVariables.RANK_RISE_FIVEMIN: {	//五分钟涨幅
				sortType = "fiveMinRise";
				break;
			}
			case CommonVariables.RANK_RISE_THREEDAY: {	//三日涨幅
				sortType = "threeDayRise";
				break;
			}
			case CommonVariables.RANK_TURNOVER_RATE: {	//换手率
				sortType = "turnoverRate";
				break;
			}			
		}
		
		for ( int i =0; i < exIdsSize; i++){
			sbcIds =  quotesRedisDao.hvals(CommonVariables.REDIS_KEY_SBCCODEMAP + exIds[i]);
			sbcIdsSize = sbcIds.size();
			for (int j = 0; j < sbcIdsSize; j++){
				String volatility = quotesRedisDao.hmget(CommonVariables.REDIS_KEY_QUOTSMORE + sbcIds.get(j), sortType).get(0);
				if (CommonUtil.isStringDigit(volatility)) {
					heapSort.init(sbcIds.get(j), Double.parseDouble(volatility), exIds[i], type);
				}		
			}			
		}
		
		//如果是停盘的则不参加比较
		rankDataList = heapSort.sort(type);
		String jsonString = "";
		String res = "";
		int keyValueSize = 0;
		int rankDataListSize = rankDataList.size();	
		for (int i = 0 ; i< rankDataListSize; i++){	
			res = "";
			List<String> quote = quotesRedisDao.hmget(CommonVariables.REDIS_KEY_QUOTS + rankDataList.get(i).getDataM(), rankDataList.get(i).getId());		
			if (quote != null ){
				jsonString = quote.get(0);
				String[] keyValues = jsonString.split(",");
				keyValueSize = keyValues.length;
				res = jsonString;
				/*for (int j = 0 ; j< keyValueSize; j++ ){			
					if (j==10){			//涨幅字段		
						String[] keyValue = keyValues[j].split(":");
						keyValue[1] = CommonUtil.getFormatDouble(rankDataList.get(i).getDataD(),2);
						res += "," + keyValue[0] + ":\"" + keyValue[1] +"\"";
					} else if (j==0){
						res += keyValues[j];
					} else {
						res += "," +keyValues[j];
					}
				}*/
				res = res.replace("}", "");
				res += "," + "\"extraInfo\":\"" + CommonUtil.getFormatDouble(rankDataList.get(i).getDataD(),2)+ "\"}";
				quList.add(res);
			}
		}
		return quList;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取交易所综合指数
	 * Method:		getExQuotations
	 * Author:		1.0 created by lijiaxuan at 2015年8月25日 下午2:59:00
	 *--------------------------------------------------------------------------------------*/
	public List<String> getExQuotations(){
		List<String> quotationsList = new  ArrayList<String>();	
		SbcBasicDomain sbcBasicDomain = new SbcBasicDomain();
		sbcBasicDomain.setSbcType(CommonVariables.SBC_INDEX);
		List<SbcBasicDomain> resDomains = sbcBasicDao.querySbcBasic(sbcBasicDomain);
		int size = resDomains.size();
		for (int i = 0; i < size; i++){				
			String quotation =  this.getSbcQuotation(resDomains.get(i).getExId(), resDomains.get(i).getSbcId().toString());
			if (quotation!=null){
				quotationsList.add(quotation); //addAll
			}		
		}
			
		return quotationsList;
	}
		
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取分时图
	 * Method:		getTChart
	 * Author:		1.0 created by lijiaxuan at 2015年8月27日 下午4:50:05
	 *--------------------------------------------------------------------------------------*/
	public List<String> getTChart(String exId ,String sbcId){
		String currentItem = "";
		//> 1. 读取历史价格信息
		List<String> tChart = quotesRedisDao.querySbcTendency(sbcId);
		//> 2. 读取当前价格信息:现价，均价，涨幅，成交量，成交额，最高价，最低价，时间戳
		String quotation = this.getSbcQuotation( exId, sbcId);
		Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotation);
		String lastDealVolume = quotMap.get("dealVolume");
		String nowDealVolume = quotesRedisDao.getDealVolume(sbcId);
		String lastDealValue = quotMap.get("dealValue");
		String nowDealValue = quotesRedisDao.getDealValue(sbcId);
		String resDealVolume = "0";
		String resDealValue = "0";
		if (! CommonUtil.isStringDigit(lastDealVolume)){
			if (CommonUtil.isStringDigit(nowDealVolume)){
				resDealVolume = nowDealVolume;
			}
		} else {
			if (CommonUtil.isStringDigit(nowDealVolume)){
				//当前的成交量 = 当前分钟的品种总成交量 - 上一分钟的品种总成交量
				int resDealVolumeI = Integer.parseInt(nowDealVolume) - Integer.parseInt(lastDealVolume);
				if (resDealVolumeI > 0) {
					resDealVolume = ""+resDealVolumeI;
				}
				
			}
		}
		
		if (! CommonUtil.isStringDigit(lastDealValue)){
			if (CommonUtil.isStringDigit(nowDealValue)){
				resDealValue = nowDealValue;
			}
		} else {
			if ( CommonUtil.isStringDigit(nowDealValue)){
				//当前的成交额 = 当前分钟的品种总成交额 - 上一分钟的品种总成交额
				double resDealValueD = Double.parseDouble(nowDealValue) - Double.parseDouble(lastDealValue);
				if (resDealValueD > 0) {
					resDealValue = ""+resDealValueD;
				}
				
			}
		}
		
		currentItem += quotMap.get("currentPrice") + "," + quotMap.get("averagePrice") + ","  + quotMap.get("riseScope") + 
				"," + resDealVolume + "," + resDealValue + "," + quotMap.get("highestPrice") + ","  + quotMap.get("lowestPrice") + "," +
				CommonUtil.getNowTimeStamp();
		//> 3. 拼接
		tChart.add(0, currentItem);
		return tChart;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	行情详情刷新
	 * Method:		getRefreshQuotations
	 * Author:		1.0 created by lijiaxuan at 2015年8月27日 下午5:50:05
	 *--------------------------------------------------------------------------------------*/
	public List<QuotationsRefreshDomain> getRefreshQuotations(String exId ,String sbcId){
		//> 1. 读取历史价格信息
		String lastTendency = quotesRedisDao.querySbcLastTendency(sbcId);
		List<QuotationsRefreshDomain> list = new ArrayList<QuotationsRefreshDomain>();
		QuotationsRefreshDomain quotationsRefreshDomain =new QuotationsRefreshDomain();
		if (lastTendency != null){
			String[] ts = lastTendency.split(",");
			quotationsRefreshDomain.setSbcId(Integer.parseInt(sbcId));
			quotationsRefreshDomain.setCurrentPrice(ts[0]);
			quotationsRefreshDomain.setAveragePrice(ts[1]);
			quotationsRefreshDomain.setRiseScope(ts[2]);
			quotationsRefreshDomain.setDealVolume(ts[3]);
			/*if (CommonUtil.isStringDigit(ts[3]) && CommonUtil.isStringDigit(ts[0]) ){
				Float dealValue = Float.parseFloat(ts[0]) * Integer.parseInt(ts[3]);
				quotationsRefreshDomain.setDealValue(dealValue.toString());
			} */
			quotationsRefreshDomain.setDealValue(ts[4]);
			quotationsRefreshDomain.setHighestPrice(ts[5]);
			quotationsRefreshDomain.setLowestPrice(ts[6]);
			quotationsRefreshDomain.setRecordTime(ts[7]);
		}
		String quotation = this.getSbcQuotation( exId, sbcId);
		Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotation);
		list.add(quotationsRefreshDomain);
		
		//当前行情
		QuotationsRefreshDomain quotationsRefreshDomain2 =new QuotationsRefreshDomain();
		if (quotMap != null){
			quotationsRefreshDomain2.setSbcId(Integer.parseInt(sbcId));
			quotationsRefreshDomain2.setCurrentPrice(quotMap.get("currentPrice"));
			quotationsRefreshDomain2.setAveragePrice(quotMap.get("averagePrice"));
			quotationsRefreshDomain2.setRiseScope(quotMap.get("riseScope"));
			
			quotationsRefreshDomain2.setDealVolume(quotMap.get("dealVolume") );
			quotationsRefreshDomain2.setDealValue(quotMap.get("dealValue"));
			quotationsRefreshDomain2.setHighestPrice(quotMap.get("highestPrice"));
			quotationsRefreshDomain2.setLowestPrice(quotMap.get("lowestPrice"));
			quotationsRefreshDomain2.setRecordTime(CommonUtil.getNowTimeStamp());
			
			String dealVolume = "0";
			String dealValue = "0";
			if (CommonUtil.isStringNull(lastTendency)){
				dealVolume = quotMap.get("dealVolume");
				dealValue = quotMap.get("dealValue");
				
			} else {
				int dealVolumeD = 0;
				double dealValueD  = 0;
				int dealVolumeChangeD = 0;
				double dealValueChangeD  = 0;
				String lastTotalVolume = quotesRedisDao.getDealVolume(sbcId);
				String lastTotalValue = quotesRedisDao.getDealValue(sbcId);		
				if (CommonUtil.isStringDigit(quotMap.get("dealVolume"))) {
					dealVolumeD = Integer.parseInt(quotMap.get("dealVolume")) - Integer.parseInt(lastTotalVolume);
				} 
				if (CommonUtil.isStringDigit(quotMap.get("dealValue"))) {
					dealValueD = Double.parseDouble(quotMap.get("dealValue")) - Double.parseDouble(lastTotalValue);
				}
				dealVolume = "" + dealVolumeD;
				dealValue = CommonUtil.getFormatDouble(dealValueD, 2);
			}
			
			quotationsRefreshDomain2.setDealVolume(dealVolume);
			quotationsRefreshDomain2.setDealValue(dealValue);
			quotationsRefreshDomain2.setDealVolumeChange(dealVolume);
			quotationsRefreshDomain2.setDealValueChange(dealValue);
		}
		list.add(quotationsRefreshDomain2);
		return list;
	}
	

	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取品种更多详情(总交易量，总交易额)
	 * Method:		getSbcQuotationMore
	 * Author:		1.0 created by lijiaxuan at 2015年8月27日 下午5:17:04
	 *--------------------------------------------------------------------------------------*/
	public List<String> getSbcQuotationMore(String sbcId){
		return quotesRedisDao.hvals(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取品种详情
	 * Method:		getSbcQuotationDetail
	 * Author:		1.0 created by lijiaxuan at 2015年8月31日 下午3:32:06
	 *--------------------------------------------------------------------------------------*/
	public List<SbcQuotationHistDomain> getSbcQuotationDetail(String sbcId, String interval){
		String exId = null;
		List<String> tmp = quotesRedisDao.hmget(CommonVariables.REDIS_KEY_SBCEXIDMAP, sbcId);
		if (tmp!=null && tmp.size() > 0){
			exId = tmp.get(0);
		} else {
			throw new YbkException(YbkException.CODE020003, YbkException.DESC020003);
		}
		return sbcQuotationHistDao.querySbcQuotationHistory( sbcId,  exId,  interval);
	}
	
	
	
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNowSbcQuotationHistDomain
	 * Author:		1.0 created by lijiaxuan at 2015年11月23日 上午11:00:11
	 *--------------------------------------------------------------------------------------*/
	public SbcQuotationHistDomain getNowSbcQuotationHistDomain( String sbcId, String interval){	
		String exId = null;
		List<String> tmp = quotesRedisDao.hmget(CommonVariables.REDIS_KEY_SBCEXIDMAP, sbcId);
		if (tmp!=null && tmp.size() > 0){
			exId = tmp.get(0);
		} else {
			throw new YbkException(YbkException.CODE020003, YbkException.DESC020003);
		}
		SbcQuotationHistDomain sbcQuotationHistDomain = new SbcQuotationHistDomain();
		double dealValueD = 0;
		double dealVolumeD = 0;
		double riseScopeD = 0;
		//double riseValueD = 0;
		String openingPrice = "";
		String riseScope = "";
		
		String closePrice = "";
		String lastClosePrice = "";
		String highestPrice = "";
		String lowestPrice = "";
		String dealVolume = "";
		String dealValue = "";
		String todayOpeningPrice = "";
		String todayHighestPrice = "";
		String todayLowestPrice = "";
		String todayDealVolume = "";
		String todayDealValue = "";
		String todayCurrentPrice = "";
		String todayRiseScope = "";
		
		String quote =  getSbcQuotation(exId, sbcId);
		if (quote != null){
			Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quote);
			todayOpeningPrice = quotMap.get("openingPrice");
			todayLowestPrice = quotMap.get("lowestPrice");
			todayHighestPrice = quotMap.get("highestPrice");
			todayDealValue = quotMap.get("dealValue");
			todayDealVolume = quotMap.get("dealVolume");
			todayCurrentPrice = quotMap.get("currentPrice");
			todayRiseScope = quotMap.get("riseScope");
			if (interval.equals(CommonVariables.TIME_INTERVAL_WEK)) {
				openingPrice = quotesRedisDao.getWeekOpenPrice(sbcId);
				lowestPrice = quotesRedisDao.getWeekLowestPrice(sbcId);
				highestPrice = quotesRedisDao.getWeekHighestPrice(sbcId);
				dealValue = quotesRedisDao.getWeekDealValue(sbcId);
				dealVolume = quotesRedisDao.getWeekDealVolume(sbcId);
				lastClosePrice = quotesRedisDao.getWeekClosePrice(sbcId);
				
			} else if (interval.equals(CommonVariables.TIME_INTERVAL_MON)) {
				openingPrice = quotesRedisDao.getMonthOpenPrice(sbcId);
				lowestPrice = quotesRedisDao.getMonthLowestPrice(sbcId);
				highestPrice = quotesRedisDao.getMonthHighestPrice(sbcId);
				dealValue = quotesRedisDao.getMonthDealValue(sbcId);
				dealVolume = quotesRedisDao.getMonthDealVolume(sbcId);
				lastClosePrice = quotesRedisDao.getMonthClosePrice(sbcId);
			}
			//日K当日数据，或者周K月K当日之前的开盘价是"-"或者null。
			if (! CommonUtil.isStringDigit(openingPrice)){
				openingPrice = todayOpeningPrice;
			} 
			//当日现价如果为"-"或null，则收盘价应当是昨日收盘价
			if (! CommonUtil.isStringDigit(todayCurrentPrice)){
				closePrice = quotMap.get("yesterClPrice");;
			}
			//最高价比价
			if (CommonUtil.isStringDigit(todayHighestPrice) && CommonUtil.isStringDigit(highestPrice)){
				if (Double.parseDouble(todayHighestPrice) > Double.parseDouble(highestPrice)){
					highestPrice = todayHighestPrice;
				}
			} else {
				highestPrice = todayHighestPrice;
			}
			//最低价比价
			if (CommonUtil.isStringDigit(todayLowestPrice) && CommonUtil.isStringDigit(lowestPrice)){
				if (Double.parseDouble(todayLowestPrice) < Double.parseDouble(lowestPrice)){
					lowestPrice = todayLowestPrice;
				}
			} else {
				lowestPrice = todayLowestPrice;
			}
			if (CommonUtil.isStringDigit(todayDealValue) && CommonUtil.isStringDigit(dealValue)){
				dealValueD = Double.parseDouble(todayDealValue) + Double.parseDouble(dealValue);
				dealValue = CommonUtil.getFormatDouble(dealValueD, 2);
			} else {
				dealValue = todayDealValue;
			}
			if (CommonUtil.isStringDigit(todayDealVolume) && CommonUtil.isStringDigit(dealVolume)){
				dealVolumeD = Double.parseDouble(todayDealVolume) + Double.parseDouble(dealVolume);
				dealVolume = CommonUtil.getFormatDouble(dealVolumeD, 2);
			} else {
				dealVolume = todayDealVolume;
			}
			if (CommonUtil.isStringDigit(lastClosePrice) && CommonUtil.isStringDigit(todayCurrentPrice)){
				riseScopeD = ((Double.parseDouble(todayCurrentPrice) -Double.parseDouble(lastClosePrice)) /Double.parseDouble(lastClosePrice)) * 100 ;
				riseScope = CommonUtil.getFormatDouble(riseScopeD, 2);
			} else {
				riseScope = todayRiseScope;
			}
				
	
		}
		sbcQuotationHistDomain.setClosePrice(closePrice);
		sbcQuotationHistDomain.setDate(CommonUtil.getNowDate());	
		sbcQuotationHistDomain.setDealValue(dealValue);
		sbcQuotationHistDomain.setDealVolume(dealVolume);
		sbcQuotationHistDomain.setHighestPrice(highestPrice);
		sbcQuotationHistDomain.setLowestPrice(lowestPrice);
		sbcQuotationHistDomain.setOpeningPrice(openingPrice);
		sbcQuotationHistDomain.setRiseScope(riseScope);
		//sbcQuotationHistDomain.setRiseValue(CommonUtil.getFormatDouble(riseValueD, 2));
		sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));
		sbcQuotationHistDomain.setType(interval);
		return sbcQuotationHistDomain;
	}
	
	
	

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getExSbcQuotations
	 * Author:		1.0 created by lijiaxuan at 2015年10月21日 下午3:32:44
	 *--------------------------------------------------------------------------------------*/
	public List<String> getExSbcQuotations(String exId){
		return quotesRedisDao.hvals(exId);
	}
}

