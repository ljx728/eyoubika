package com.eyoubika.info.application;

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
			} else {
				lastClosePrice = quotesRedisDao.getDayClosePrice(sbcId);
			}
			//日K当日数据，或者周K月K当日之前的开盘价是"-"或者null。
			if (! CommonUtil.isStringDigit(openingPrice)){
				openingPrice = todayOpeningPrice;
			} 
			//当日现价如果为"-"或null，则收盘价应当是昨日收盘价
			if (! CommonUtil.isStringDigit(todayCurrentPrice)){
				closePrice = quotMap.get("yesterClPrice");;
			} else {
				closePrice = todayCurrentPrice;
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

	/*--------------------------------------------------------------------------------------*
	 * Description:	批量删除分时数据
	 * Method:		cleanTChart
	 * Author:		1.0 created by lijiaxuan at 2015年9月17日 下午2:51:21
	 *--------------------------------------------------------------------------------------*/
	public void cleanTChart(){
		String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	
		Map<String,String> sbcExIdMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_SBCEXIDMAP);
		Iterator<Map.Entry<String, String>> it = sbcExIdMap.entrySet().iterator();
		String sbcId = null;	
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			sbcId = entry.getKey();
			quotesRedisDao.del(CommonVariables.REDIS_KEY_TIMESHARE + sbcId);  
			quotesRedisDao.del(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId);  
		}	
		int size = marketIds.length;
		for (int i = 0; i < size; i++){
			quotesRedisDao.del(CommonVariables.REDIS_KEY_QUOTS + marketIds[i]);   
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	清空redis，保留了qu:xxxxxx
	 * Method:		cleanRedis
	 * Author:		1.0 created by lijiaxuan at 2015年10月20日 下午5:04:29
	 *--------------------------------------------------------------------------------------*/
	public void cleanRedis(){
		String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	
		Map<String,String> sbcExIdMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_SBCEXIDMAP);
		Iterator<Map.Entry<String, String>> it = sbcExIdMap.entrySet().iterator();
		String sbcId = null;	
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			sbcId = entry.getKey();
			quotesRedisDao.del(CommonVariables.REDIS_KEY_TIMESHARE + sbcId);  
			quotesRedisDao.del(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId);  
		}	
		int size = marketIds.length;
		for (int i = 0; i < size; i++){
			//quotesRedisDao.del(CommonVariables.REDIS_KEY_QUOTS + marketIds[i]);  
			quotesRedisDao.del(CommonVariables.REDIS_KEY_SBCCODEMAP+ marketIds[i]);  
		}
		quotesRedisDao.del(CommonVariables.REDIS_KEY_SBCEXIDMAP); 
		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	为分时图做数据准备，把数据以特定时间间隔写入redis
	 * Method:		inputTChart
	 * Author:		1.0 created by lijiaxuan at 2015年8月27日 下午3:42:39
	 *--------------------------------------------------------------------------------------*/
	public void inputTChart(){
		Map<String,String> sbcExIdMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_SBCEXIDMAP);
		Iterator<Map.Entry<String, String>> it = sbcExIdMap.entrySet().iterator();
		String sbcId = null;
		String exId = null;			
		while (it.hasNext()) {
				String currentItem = "";
				//> 2. 每个品种，抽取分时行情，存入redis
				Map.Entry<String, String> entry = it.next();
				sbcId = entry.getKey();
				exId = entry.getValue();
				String quotation = this.getSbcQuotation( exId, sbcId);
				Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotation);	
				String currentPrice = null;
				String averagePrice = null;
				String riseScope = null;
				String highestPrice = null;
				String lowestPrice = null;
				
				String closePrice = quotesRedisDao.getDayClosePrice(sbcId);
				if (quotMap==null){
					
					currentItem += closePrice + "," + closePrice + ","  + "0" + 
							"," + "0" + "," +"0" + "," + closePrice + ","  + closePrice + "," +
							CommonUtil.getNowTimeStamp();
				
					quotesRedisDao.lpush(CommonVariables.REDIS_KEY_TIMESHARE + sbcId, currentItem);  
					
					//写入当前总成交额，成交量到redis；用于分时图计算。
					Map<String, String> map = new HashMap<String, String>();
					map.put("dealVolume","0");
					map.put("dealValue", "0");
					quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcId, map);

					continue;
				}
				//> 2. 读取当前价格信息:现价，均价，涨幅，成交量，成交额，最高价，最低价，时间戳 //均价＝开盘时到这个时点的所有成交价格×相应的成交股数/开盘时到这个时点总成交量
				//time = CommonUtil.getNowTimeStamp();
				String req = quotesRedisDao.lgetlast(CommonVariables.REDIS_KEY_TIMESHARE + sbcId);
				String dealVolume = "";
				String dealValue = "";
				if (CommonUtil.isStringNull(req)){
					//dealVolume = "0"; //quotMap.get("dealVolume");
					//dealValue = "0"; //quotMap.get("dealValue");
					
					dealVolume = quotMap.get("dealVolume").equals("-") ? "0" :  quotMap.get("dealVolume");
					dealValue = quotMap.get("dealValue").equals("-") ? "0" :  quotMap.get("dealValue");
					
				} else {
					int dealVolumeD = 0;
					double dealValueD  = 0;
					String lastTotalVolume = quotesRedisDao.getDealVolume(sbcId);
					String lastTotalValue = quotesRedisDao.getDealValue(sbcId);		
					
					if (CommonUtil.isStringDigit(quotMap.get("dealVolume"))) {
						if (!CommonUtil.isStringDigit(lastTotalVolume)){
							lastTotalVolume = "0";
						}
						dealVolumeD = Integer.parseInt(quotMap.get("dealVolume")) - Integer.parseInt(lastTotalVolume);
						if (dealVolumeD < 0) {
							dealVolumeD = 0;
						}
					} 
					if (CommonUtil.isStringDigit(quotMap.get("dealValue"))) {
						if (!CommonUtil.isStringDigit(lastTotalValue)){
							lastTotalValue = "0";
						}
						dealValueD = Double.parseDouble(quotMap.get("dealValue")) - Double.parseDouble(lastTotalValue);
						if (dealValueD < 0) {
							dealValueD = 0;
						}
					}
					dealVolume = "" + dealVolumeD;
					dealValue = CommonUtil.getFormatDouble(dealValueD, 2);
				}				
				currentPrice =  CommonUtil.isStringDigit(quotMap.get("currentPrice")) ? quotMap.get("currentPrice"): closePrice ;
				averagePrice = CommonUtil.isStringDigit(quotMap.get("averagePrice")) ? quotMap.get("averagePrice") : closePrice ;
				riseScope = CommonUtil.isStringDigit(quotMap.get("riseScope")) ? quotMap.get("riseScope"):  "0" ;
				highestPrice = CommonUtil.isStringDigit(quotMap.get("highestPrice")) ? quotMap.get("highestPrice") : closePrice ;
				lowestPrice = CommonUtil.isStringDigit(quotMap.get("lowestPrice")) ? quotMap.get("lowestPrice") : closePrice;
				
				if (quotMap.get("sbcName").contains("指数")){
					averagePrice = "0";
				}
				currentItem += currentPrice + "," + averagePrice + ","  + riseScope + 
						"," + dealVolume + "," +dealValue + "," + highestPrice + ","  + lowestPrice + "," +
						CommonUtil.getNowTimeStamp();
			
				quotesRedisDao.lpush(CommonVariables.REDIS_KEY_TIMESHARE + sbcId, currentItem);  
				
				//写入当前总成交额，成交量到redis；用于分时图计算。
				Map<String, String> map = new HashMap<String, String>();
				map.put("dealVolume", quotMap.get("dealVolume").equals("-") ? "0" :  quotMap.get("dealVolume"));
				map.put("dealValue", quotMap.get("dealValue").equals("-") ? "0" :  quotMap.get("dealValue"));
				quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcId, map);
				
			}
			it = null;		
	}	
	
/*	
	--------------------------------------------------------------------------------------*
	 * Description:	离线批量处理K线图，日终跑批写入磁盘文件：次日凌晨1点
	 * Method:		inputKChartData
	 * Author:		1.0 created by lijiaxuan at 2015年8月29日 上午9:23:19
	 *--------------------------------------------------------------------------------------
	public void inputKChartData(){
		List<ExchangeBasicDomain> exchangeBasicDomainList =exchangeBasicDao.queryAllExchangeBasic();
		int listSize = exchangeBasicDomainList.size();
		String exId = null;
		String quotation = null;
		String fileName = null;
		String sbcId = null;
		String currentItem = null;
		for (int i = 0; i < listSize; i++){
			exId = exchangeBasicDomainList.get(i).getExId();
			Map<String,String> sbcQuotesMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_QUOTS + exId);
			Iterator<Map.Entry<String, String>> it = sbcQuotesMap.entrySet().iterator();
			while (it.hasNext()) {
	
				currentItem = "";
				//> 2. 每个品种，抽取分时行情，存入redis
				Map.Entry<String, String> entry = it.next();			
				quotation = entry.getValue();
				Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotation);
				currentItem += quotMap.get("openingPrice") + "," + quotMap.get("currentPrice") + "," +quotMap.get("highestPrice") + "," +quotMap.get("lowestPrice") + "," +CommonUtil.getNowDate() +"\n";
				sbcId = quotMap.get("sbcId");
				//写入品种日K图磁盘文件
				fileName = sbcId + "_"+ CommonVariables.TIME_INTERVAL_DAY +".txt";
				CommonUtil.toFile(currentItem , fileName,CommonUtil.KCHART_DATA_DIR); 
				//写入品种周K图磁盘文件
				fileName = sbcId + "_"+ CommonVariables.TIME_INTERVAL_WEK+".txt";
				CommonUtil.toFile(currentItem , fileName,CommonUtil.KCHART_DATA_DIR); 
				//写入品种月K图磁盘文件
				fileName = sbcId + "_"+ CommonVariables.TIME_INTERVAL_MON +".txt";
				CommonUtil.toFile(currentItem , fileName,CommonUtil.KCHART_DATA_DIR); 
				
			}
		}
	}
	--------------------------------------------------------------------------------------*
	 * Description:	获取K线图
	 * Method:		getKChart
	 * Author:		1.0 created by lijiaxuan at 2015年8月24日 上午11:15:09
	 *--------------------------------------------------------------------------------------
	public List<String> getKChart(String exId ,String sbcId, String interval){
		//> 1. 读取历史价格信息
		if (CommonUtil.isStringNull(interval)){
			interval = CommonVariables.TIME_INTERVAL_DAY; //05-hour; 06-day; 07-week; 08-month;
		}
		String content = CommonUtil.fromFile(sbcId + "_"+ interval +".txt", CommonUtil.KCHART_DATA_DIR); 
		List<String> kChartItems = new  ArrayList<String>();	
		String[] lines = content.split("\n");
		int size = lines.length;
		for (int i = 0; i < size; i++){
			kChartItems.add(lines[i]);		
		}
		//> 2. 获取sbc的交易所ID
		if (CommonUtil.isStringNull(exId)){
			List<String> tmp = quotesRedisDao.hmget(CommonVariables.REDIS_KEY_SBCEXIDMAP, sbcId);
			if (tmp!=null && tmp.size() > 0){
				exId = tmp.get(0);
			}
		}
		//> 3. 获取该品种的交易行情
		List<String> quotations =  quotesRedisDao.queryQuotations(exId, sbcId);
		String currentItem = "";
		for (int i = 0; i < quotations.size(); i++){
			Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotations.get(i));
			currentItem += quotMap.get("openingPrice") + "," + quotMap.get("currentPrice") + "," +quotMap.get("highestPrice") + "," +quotMap.get("lowestPrice") + "," +CommonUtil.getNowDate();
		} 
		//> 4.组装成K线图。
		kChartItems.add(currentItem);
		return kChartItems;
	}

	--------------------------------------------------------------------------------------*
	 * Description:	获取分量图
	 * Method:		getCChart
	 * Author:		1.0 created by lijiaxuan at 2015年8月25日 上午10:35:16
	 *--------------------------------------------------------------------------------------
	public List<String> getCChart(String exId ,String sbcId, String interval){
		//> 1. 读取历史价格信息
		if (CommonUtil.isStringNull(interval)){
			interval = CommonVariables.TIME_INTERVAL_DAY; //05-hour; 06-day; 07-week; 08-month;
		}
		String content = CommonUtil.fromFile(sbcId + "_"+ interval +".txt", CommonUtil.CCHART_DATA_DIR); 
		List<String> cChartItems = new  ArrayList<String>();	
		String[] lines = content.split("\n");
		int size = lines.length;
		for (int i = 0; i < size; i++){
			cChartItems.add(lines[i]);		
		}
		//> 2. 获取sbc的交易所ID
		if (CommonUtil.isStringNull(exId)){
			List<String> tmp = quotesRedisDao.hmget(CommonVariables.REDIS_KEY_SBCEXIDMAP, sbcId);
			if (tmp!=null){
				exId = tmp.get(0);
			}
		}
		//> 3. 获取该品种的交易行情
		List<String> quotations =  quotesRedisDao.queryQuotations(exId, sbcId);
		String currentItem = "";
		for (int i = 0; i < quotations.size(); i++){
			Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotations.get(i));
			currentItem += quotMap.get("dealVolume") + "," + quotMap.get("currentPrice") + "," +CommonUtil.getNowDate();
		} 
		//> 4.组装成分量图。
		cChartItems.add(currentItem);
		return cChartItems;
	}

	--------------------------------------------------------------------------------------*
	 * Description:	离线批量处理分量图，日终跑批写入磁盘文件：次日凌晨1点
	 * Method:		inputCChartData
	 * Author:		1.0 created by lijiaxuan at 2015年8月29日 上午9:30:11
	 *--------------------------------------------------------------------------------------
	public void inputCChartData(){
		List<Integer> sbcIdList =sbcBasicDao.queryAllSbcIds();
		int listSize = sbcIdList.size();
		String sbcId = null;
		String quotation = null;
		String fileName = null;
		String currentItem = null;
		for (int i = 0; i < listSize; i++){
			//> 2. 获取redis中存储的分量图
			sbcId = sbcIdList.get(i).toString();
			Map<String,String> sbcQuotesMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_QUOTSMORE + sbcId);
			Iterator<Map.Entry<String, String>> it = sbcQuotesMap.entrySet().iterator();
			int index = 0;
			currentItem = "";	
			while (it.hasNext()) {
				
				Map.Entry<String, String> entry = it.next();	
				if (index == 0 ){
					if (entry.getKey().equals("totalVolume") || entry.getKey().equals("totalValue")){
						currentItem += entry.getValue(); 	
					} 
				} else {
					if (entry.getKey().equals("totalVolume")|| entry.getKey().equals("totalValue")){
						currentItem += "," + entry.getValue(); 					
					} 
				}					
				index++;				
			}
			currentItem += ","  + CommonUtil.getNowDate() +"\n";
			//写入品种日分量磁盘文件
			fileName = sbcId +".txt";
			CommonUtil.toFile(currentItem , fileName,CommonUtil.CCHART_DATA_DIR); 			
		}
	}*/
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		rankBillboard
	 * Author:		1.0 created by lijiaxuan at 2015年10月16日 下午5:47:23
	 *--------------------------------------------------------------------------------------
	public List<QuotationsDomain> rankBillboard(List<QuotationsDomain> quDomainList, String type){
		List<QuotationsDomain> resList = new ArrayList<QuotationsDomain>();
		for ( int i = 0; i < CommonVariables.RANKLIST_ITEMS_MAX; i++){
			resList.add(quDomainList.get(i));
		}
		//System.out.println("==================================初始的数据是: ");
		//for (int i = 0; i < CommonVariables.RANKLIST_ITEMS_MAX; i++){
		//	System.out.println(resList.get(i).getDealValue().toString());
		//}
	
		int quDomainListSize = quDomainList.size();
		
		for (int i = 0; i < quDomainListSize; i++){
			System.out.println(quDomainList.get(i).getDealValue().toString());
		}
		//构建最小堆
		// 完全二叉树只有数组下标小于或等于 (data.length) / 2 - 1 的元素有孩子结点，遍历这些结点。
		for (int i = CommonVariables.RANKLIST_ITEMS_MAX/2-1; i >= 0; i--){//i从第一个非叶子结点开始  
			minHeapify(resList, i, CommonVariables.RANKLIST_ITEMS_MAX,  type); 
		}
		switch (type){		
			case "averagePrice": {	
				double quAveragePrice = 0;	
				double minAveragePrice = 0;							
				for (int i = CommonVariables.RANKLIST_ITEMS_MAX; i < quDomainListSize; i++){
					if (CommonUtil.isStringDigit(resList.get(0).getAveragePrice())){
						minAveragePrice = Double.parseDouble(resList.get(0).getAveragePrice());	//小根堆，最小值是第一个
					} 
					quAveragePrice = 0;
					if (CommonUtil.isStringDigit(quDomainList.get(i).getAveragePrice())){
						quAveragePrice = Double.parseDouble(quDomainList.get(i).getAveragePrice());
					} 
					if (quAveragePrice > minAveragePrice){
						resList.set(0, quDomainList.get(i));					
						for (int j = CommonVariables.RANKLIST_ITEMS_MAX/2-1; j >= 0; j--){	//i从第一个非叶子结点开始  
							minHeapify(resList, j, CommonVariables.RANKLIST_ITEMS_MAX,  type); 
						}											
					} 								
				}
				heapMain(resList,type); 	//变成顺序的列表			
				break;
			}
			case "currentPrice": {				
				break;
			}
			case "dealValue": {
				double quDealValue = 0;	
				double minDealValue = 0;							
				for (int i = CommonVariables.RANKLIST_ITEMS_MAX; i < quDomainListSize; i++){
					if (CommonUtil.isStringDigit(resList.get(0).getDealValue())){
						minDealValue = Double.parseDouble(resList.get(0).getDealValue());	//小根堆，最小值是第一个
					} 
					quDealValue = 0;
					if (CommonUtil.isStringDigit(quDomainList.get(i).getDealValue())){
						quDealValue = Double.parseDouble(quDomainList.get(i).getDealValue());
					} 
					if (quDealValue > minDealValue){
						resList.set(0, quDomainList.get(i));					
						for (int j = CommonVariables.RANKLIST_ITEMS_MAX/2-1; j >= 0; j--){//i从第一个非叶子结点开始  
							minHeapify(resList, j, CommonVariables.RANKLIST_ITEMS_MAX,  type); 
						}											
					} 								
				}
				heapMain(resList,type); 	//变成顺序的列表			
				break;		
			}
			case "dealVolume": {
				int quDealVolume = 0;	
				int minDealVolume = 0;							
				for (int i = CommonVariables.RANKLIST_ITEMS_MAX; i < quDomainListSize; i++){
					if (CommonUtil.isStringDigit(resList.get(0).getDealVolume())){
						minDealVolume = Integer.parseInt(resList.get(0).getDealVolume());	//小根堆，最小值是第一个
					} 
					quDealVolume = 0;
					if (CommonUtil.isStringDigit(quDomainList.get(i).getDealVolume())){
						quDealVolume = Integer.parseInt(quDomainList.get(i).getDealVolume());
					} 
					if (quDealVolume > minDealVolume){
						resList.set(0, quDomainList.get(i));					
						for (int j = CommonVariables.RANKLIST_ITEMS_MAX/2-1; j >= 0; j--){//i从第一个非叶子结点开始  
							minHeapify(resList, j, CommonVariables.RANKLIST_ITEMS_MAX,  type); 
						}											
					} 								
				}
				heapMain(resList,type); 	//变成顺序的列表		
				break;
			}
		}
		return resList;	
	}
	    

	
	--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		minHeapify
	 * Author:		1.0 created by lijiaxuan at 2015年10月16日 下午5:47:17
	 *--------------------------------------------------------------------------------------
	public static void minHeapify(List<QuotationsDomain> quDomainList, int i,int heapSize, String type){  
		int minIndex = i; 
		int leftIndex = 2*i+1;
		int rightIndex = 2*i+2;	
		switch (type){		
			case "averagePrice": {
				double leftAveragePrice = 0;	
				double rightAveragePrice = 0;	
				double minAveragePrice = 0;								
				if (!CommonUtil.isStringDigit(quDomainList.get(minIndex).getAveragePrice())){
					minAveragePrice = 0;
				} else {
					minAveragePrice = Double.parseDouble(quDomainList.get(minIndex).getAveragePrice());
				}				
				if( leftIndex <= heapSize - 1 ){
					if (!CommonUtil.isStringDigit(quDomainList.get(leftIndex).getAveragePrice())){
						leftAveragePrice = 0;
					} else {
						leftAveragePrice = Double.parseDouble(quDomainList.get(leftIndex).getAveragePrice());
					}
					if (leftAveragePrice < minAveragePrice) {
						minIndex = leftIndex;  
					}	
				}			        
				if( rightIndex <= heapSize-1 ){
					if (!CommonUtil.isStringDigit(quDomainList.get(rightIndex).getAveragePrice())){
						rightAveragePrice = 0;
					} else {
						rightAveragePrice = Double.parseDouble(quDomainList.get(rightIndex).getAveragePrice());
					}
					if (rightAveragePrice < minAveragePrice){
						 minIndex = rightIndex; 
					}
				}    
				
			    if(minIndex !=i){  
			    	QuotationsDomain  tmpQuotationsDomain = quDomainList.get(minIndex); //a[minIndex];  
			    	quDomainList.set(minIndex, quDomainList.get(i));
			    	quDomainList.set(i, tmpQuotationsDomain);
			        minHeapify(quDomainList, minIndex,heapSize, type);  
			      }  
				break;
			}
			case "currentPrice": {		
				break;
			}
			case "dealValue": {
				double leftDealValue = 0;	
				double rightDealValue = 0;	
				double minDealValue = 0;								
				if (!CommonUtil.isStringDigit(quDomainList.get(minIndex).getDealValue())){
					minDealValue = 0;
				} else {
					minDealValue = Double.parseDouble(quDomainList.get(minIndex).getDealValue());
				}				
				if( leftIndex <= heapSize - 1 ){
					if (!CommonUtil.isStringDigit(quDomainList.get(leftIndex).getDealValue())){
						leftDealValue = 0;
					} else {
						leftDealValue =  Double.parseDouble(quDomainList.get(leftIndex).getDealValue());
					}
					if (leftDealValue < minDealValue ) {
						minIndex = leftIndex;  
					}		
				}	
				minDealValue = Double.parseDouble(quDomainList.get(minIndex).getDealValue());
				if( rightIndex <= heapSize-1 ){
					if (!CommonUtil.isStringDigit(quDomainList.get(rightIndex).getDealValue())){
						rightDealValue = 0;
					} else {
						rightDealValue =  Double.parseDouble(quDomainList.get(rightIndex).getDealValue());
					}
					if (rightDealValue < minDealValue){
						 minIndex = rightIndex; 
					}
				}   			
			    if(minIndex !=i){  
			    	QuotationsDomain  tmpQuotationsDomain = quDomainList.get(minIndex); //a[minIndex];  
			    	quDomainList.set(minIndex, quDomainList.get(i));
			    	quDomainList.set(i, tmpQuotationsDomain);
			        minHeapify(quDomainList, minIndex,heapSize, type);  
			      }  
				break;
			}
			case "dealVolume": {
				int leftDealVolume = 0;	
				int rightDealVolume = 0;	
				int minDealVolume = 0;								
				if (!CommonUtil.isStringDigit(quDomainList.get(minIndex).getDealVolume())){
					minDealVolume = 0;
				} else {
					minDealVolume = Integer.parseInt(quDomainList.get(minIndex).getDealVolume());
				}				
				if( leftIndex <= heapSize - 1 ){
					if (!CommonUtil.isStringDigit(quDomainList.get(leftIndex).getDealVolume())){
						leftDealVolume = 0;
					} else {
						leftDealVolume =  Integer.parseInt(quDomainList.get(leftIndex).getDealVolume());
					}
					if (leftDealVolume < minDealVolume ) {
						minIndex = leftIndex;  
					}		
				}	
				minDealVolume = Integer.parseInt(quDomainList.get(minIndex).getDealVolume());
				if( rightIndex <= heapSize-1 ){
					if (!CommonUtil.isStringDigit(quDomainList.get(rightIndex).getDealVolume())){
						rightDealVolume = 0;
					} else {
						rightDealVolume =  Integer.parseInt(quDomainList.get(rightIndex).getDealVolume());
					}
					if (rightDealVolume < minDealVolume){
						 minIndex = rightIndex; 
					}
				}   			
			    if(minIndex !=i){  
			    	QuotationsDomain  tmpQuotationsDomain = quDomainList.get(minIndex); //a[minIndex];  
			    	quDomainList.set(minIndex, quDomainList.get(i));
			    	quDomainList.set(i, tmpQuotationsDomain);
			        minHeapify(quDomainList, minIndex,heapSize, type);  
			      }  
				break;
			}
			case "riseScope": {
				
				break;
			}
			default: {				
				//commonFetcher( CommonUtil.getHost(url), url, exId, this.quotesRedisDao);
				break;
			}				
		}	      
	  }  
	
	
	--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		heapMain
	 * Author:		1.0 created by lijiaxuan at 2015年10月16日 下午5:47:09
	 *--------------------------------------------------------------------------------------
	public static void heapMain(List<QuotationsDomain> quDomainList, String type){  
		int heapSize = CommonVariables.RANKLIST_ITEMS_MAX;  
		for(int i=CommonVariables.RANKLIST_ITEMS_MAX-1;i>0;i--){  
			QuotationsDomain  tmpQuotationsDomain = quDomainList.get(0); //a[minIndex];  
		    quDomainList.set(0, quDomainList.get(i));
		    quDomainList.set(i, tmpQuotationsDomain);
	        heapSize-=1;  
	        minHeapify(quDomainList, 0, heapSize, type);   //在heapSize范围内根结点的左右子树都已经是最大堆  
	      }  
	  }  */
	
	
	/*public void inputBillboard(){
		
		List<QuotationsDomain> quDomainList = new ArrayList<QuotationsDomain>();
		List<String> quList = null;
		int exIdsSize = 0;
		int quListSize = 0;
		Map<String, String> map = new HashMap<String, String>();
		
		//> 2. 获取sbc的交易所ID
		
				String exId = CommonVariables.EXCHANGE_NANJING + "," + CommonVariables.EXCHANGE_NANFANG + "," +
					CommonVariables.EXCHANGE_FULITE + "," + 
					CommonVariables.EXCHANGE_JINMAJIA + "," + CommonVariables.EXCHANGE_SHANGHAI + "," +
					CommonVariables.EXCHANGE_YIJIAO + "," + CommonVariables.EXCHANGE_ZHONGNAN ;
		
		String exId = CommonVariables.EXCHANGE_NANJING ;
		String[] exIds = exId.split(",");
		exIdsSize = exIds.length;
		for (int i = 0 ; i< exIdsSize; i++){
			quList = quotesRedisDao.hvals(CommonVariables.REDIS_KEY_QUOTS + exId);
			quListSize = quList.size();
			for (int j = 0; j < quListSize; j++){
				QuotationsDomain quotationsDomain=  quotesRedisDao.jsonStringToDomain( quList.get(j));
				//System.out.println("quotationsDomain " + quotationsDomain.toString());
				quDomainList.add(quotationsDomain);			
			}
			
		}
		String sbcIds = "";
		List<QuotationsDomain> resList = rankBillboard(quDomainList, "dealValue");
		if (resList != null ){
			int size = resList.size();
			for (int i = 0; i < size; i++){
				if (i == 0){
					sbcIds += resList.get(i).getSbcId();
				} else {
					sbcIds += "," + resList.get(i).getSbcId();
				}	
			}
		}
		map.put("dealValue", sbcIds);
		List<QuotationsDomain> resLIst = rankBillboard(quDomainList, "dealVolume");
		if (resList != null ){
			int size = resList.size();
			for (int i = 0; i < size; i++){
				if (i == 0){
					sbcIds += resList.get(i).getSbcId();
				} else {
					sbcIds += "," + resList.get(i).getSbcId();
				}	
			}
		}
		map.put("dealVolume", sbcIds);		
		if (resLIst != null ){
			quotesRedisDao.hmset(CommonVariables.REDIS_KEY_BILLBOARD, map);
		}
		//String quotations =  quotesRedisDao.queryQuotations(exId, sbcId);	
	}
*/
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	为分时图做数据准备，把数据以特定时间间隔写入redis
	 * Method:		inputTChartData
	 * Author:		1.0 created by lijiaxuan at 2015年8月27日 下午3:42:39
	 *--------------------------------------------------------------------------------------*/
	/*public void inputTChartData(String time){
		int sleepTime = 1000 * 60; //一分钟
		//> 1. 得到所有的品种ID和ExId
		int waitSleepTime = 3000; //3s  
		Map<String,String> sbcExIdMap = quotesRedisDao.hgetAll(CommonVariables.REDIS_KEY_SBCEXIDMAP);
		while (true)//
		 {
			if (CommonUtil.isInTime(CommonVariables.TRADESHARE_BEG_AM, CommonVariables.TRADEHOUR_END_AM) //分时图生成必须要晚于行情数据的抓取
				|| CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_PM, CommonVariables.TRADEHOUR_END_PM)){
		
			Iterator<Map.Entry<String, String>> it = sbcExIdMap.entrySet().iterator();
			String sbcId = null;
			String exId = null;			
			while (it.hasNext()) {
				String currentItem = "";
				//> 2. 每个品种，抽取分时行情，存入redis
				Map.Entry<String, String> entry = it.next();
				sbcId = entry.getKey();
				exId = entry.getValue();
				String quotation = this.getSbcQuotation( exId, sbcId);
				Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotation);	
				if (quotMap==null){
					String closePrice = quotesRedisDao.getClosePrice(sbcId);
					currentItem += closePrice + "," + closePrice + ","  + "0" + 
							"," + "0" + "," +"0" + "," + closePrice + ","  + closePrice + "," +
							CommonUtil.getNowTimeStamp();
				
					quotesRedisDao.lpush(CommonVariables.REDIS_KEY_TIMESHARE + sbcId, currentItem);  
					
					//写入当前总成交额，成交量到redis；用于分时图计算。
					Map<String, String> map = new HashMap<String, String>();
					map.put("dealVolume","0");
					map.put("dealValue", "0");
					quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcId, map);

					continue;
				}
				//> 2. 读取当前价格信息:现价，均价，涨幅，成交量，成交额，最高价，最低价，时间戳 //均价＝开盘时到这个时点的所有成交价格×相应的成交股数/开盘时到这个时点总成交量
				//time = CommonUtil.getNowTimeStamp();
				String req = quotesRedisDao.lgetlast(CommonVariables.REDIS_KEY_TIMESHARE + sbcId);
				String dealVolume = "";
				String dealValue = "";
				if (CommonUtil.isStringNull(req)){
					//dealVolume = "0"; //quotMap.get("dealVolume");
					//dealValue = "0"; //quotMap.get("dealValue");
					dealVolume = quotMap.get("dealVolume");
					dealValue = quotMap.get("dealValue");
					
				} else {
					int dealVolumeD = 0;
					double dealValueD  = 0;
					String lastTotalVolume = quotesRedisDao.getDealVolume(sbcId);
					String lastTotalValue = quotesRedisDao.getDealValue(sbcId);		
					
					if (CommonUtil.isStringDigit(quotMap.get("dealVolume"))) {
						if (!CommonUtil.isStringDigit(lastTotalVolume)){
							lastTotalVolume = "0";
						}
						dealVolumeD = Integer.parseInt(quotMap.get("dealVolume")) - Integer.parseInt(lastTotalVolume);
						if (dealVolumeD < 0) {
							dealVolumeD = 0;
						}
					} 
					if (CommonUtil.isStringDigit(quotMap.get("dealValue"))) {
						if (!CommonUtil.isStringDigit(lastTotalValue)){
							lastTotalValue = "0";
						}
						dealValueD = Double.parseDouble(quotMap.get("dealValue")) - Double.parseDouble(lastTotalValue);
						if (dealValueD < 0) {
							dealValueD = 0;
						}
					}
					dealVolume = "" + dealVolumeD;
					dealValue = CommonUtil.getFormatDouble(dealValueD, 2);
				}				
				currentItem += quotMap.get("currentPrice") + "," + quotMap.get("averagePrice") + ","  + quotMap.get("riseScope") + 
						"," + dealVolume + "," +dealValue + "," + quotMap.get("highestPrice") + ","  + quotMap.get("lowestPrice") + "," +
						quotMap.get("recordTime");
			
				quotesRedisDao.lpush(CommonVariables.REDIS_KEY_TIMESHARE + sbcId, currentItem);  
				
				//写入当前总成交额，成交量到redis；用于分时图计算。
				Map<String, String> map = new HashMap<String, String>();
				map.put("dealVolume", quotMap.get("dealVolume"));
				map.put("dealValue", quotMap.get("dealValue"));
				quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcId, map);
				
			}
			it = null;	
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
	*/

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getExSbcQuotations
	 * Author:		1.0 created by lijiaxuan at 2015年10月21日 下午3:32:44
	 *--------------------------------------------------------------------------------------*/
	public List<String> getExSbcQuotations(String exId){
		return quotesRedisDao.hvals(exId);
	}
}

