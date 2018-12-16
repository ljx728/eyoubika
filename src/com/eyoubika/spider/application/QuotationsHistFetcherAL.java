package com.eyoubika.spider.application;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.eyoubika.sbc.dao.SbcBasicDao;
import com.eyoubika.sbc.dao.SbcQuotationHistDao;
import com.eyoubika.sbc.dao.TradeCalendarDao;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.sbc.domain.SbcQuotationHistDomain;
import com.eyoubika.sbc.domain.TradeCalendarDomain;
import com.eyoubika.spider.web.VO.FetchVO;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.common.BaseSpider;
import com.eyoubika.common.CommonMaps;
import com.eyoubika.common.RedisDao;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.YbkException;
import com.eyoubika.info.domain.QuotationsDomain;
import com.eyoubika.spider.domain.*;
import com.eyoubika.spider.dao.*;
import com.eyoubika.util.*;

/*==========================================================================================*
 * Description:	TODO
 * Class:		QuotationsHistFetcherAL
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年11月17日 下午4:52:42
 *==========================================================================================*/
public class QuotationsHistFetcherAL extends BaseSpider {
	private QuotesRedisDao quotesRedisDao;
	private SbcQuotationHistDao sbcQuotationHistDao;
	private TradeCalendarDao tradeCalendarDao;
	
	public TradeCalendarDao getTradeCalendarDao() {
		return tradeCalendarDao;
	}

	public void setTradeCalendarDao(TradeCalendarDao tradeCalendarDao) {
		this.tradeCalendarDao = tradeCalendarDao;
	}

	public SbcQuotationHistDao getSbcQuotationHistDao() {
		return sbcQuotationHistDao;
	}

	public void setSbcQuotationHistDao(SbcQuotationHistDao sbcQuotationHistDao) {
		this.sbcQuotationHistDao = sbcQuotationHistDao;
	}

	public QuotesRedisDao getQuotesRedisDao() {
		return quotesRedisDao;
	}

	public void setQuotesRedisDao(QuotesRedisDao quotesRedisDao) {
		this.quotesRedisDao = quotesRedisDao;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		fetchQuotationsHist
	 * Author:		1.0 created by lijiaxuan at 2015年11月17日 下午3:58:45
	 *--------------------------------------------------------------------------------------*/
	public void fetchQuotationsHist(){
		host = "app.epianhong.com";
		init(host);
		int produceTaskSleepTime = 200; //0.2s  
		int waitSleepTime = 3000; //3s  //三秒等待
		int maxPoolSize = 100; 		// 线程池维护线程的最大数量
		int minPoolSize = 60;	 	// 线程池维护线程的最少数量
		long keepAliveTime = 3;		// 线程池维护线程所允许的空闲时间
		
		
		Map<String, String> exMap = new HashMap<String, String>();
		
		exMap.put(CommonVariables.EXCHANGE_NANJING, "2");//南京文交所
		exMap.put(CommonVariables.EXCHANGE_NANFANG, "5"); //南方文交所
		exMap.put( CommonVariables.EXCHANGE_ZHONGNAN, "6");	//中南文交所
		exMap.put(CommonVariables.EXCHANGE_FULITE, "16"); //福丽特 ?math.random()
		exMap.put(CommonVariables.EXCHANGE_JINMAJIA, "3");//金马甲
		exMap.put(CommonVariables.EXCHANGE_SHANGHAI, "http://ta.shscce.com:8080/front/hq/delay_hq.htm");//上海
		exMap.put(CommonVariables.EXCHANGE_YIJIAO, "9");//艺交所
		List<String> fetchList = new ArrayList<String>();	
		
		String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	
		//String[] marketIds = {CommonVariables.EXCHANGE_NANJING};
		//String content = "=======================\nFetch BEG: "+ CommonUtil.getNow()  +"\n";
		//CommonUtil.toLog(content);   
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(minPoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
	                new ThreadPoolExecutor.DiscardOldestPolicy()){
 
            // 线程执行之前运行
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                //System.out.println("...............beforeExecute");
            }
 
            // 线程执行之后运行
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                //System.out.println("...............afterExecute");
            }
 
            // 整个线程池停止之后
            protected void terminated() {
                //System.out.println("...............thread stop");
            }
        };
		int index = 0;
		int fetchListSize = marketIds.length;
		while (true)//
		 {
			if (index < fetchListSize){
		            try
		            {
		            	index++;	   
		                int id = index%fetchListSize;		  
		                String exId = marketIds[id];
		                threadPool.execute(new SHIFetcher( exId,  exMap.get(exId) ,quotesRedisDao, sbcQuotationHistDao, tradeCalendarDao));
		                // 便于观察，等待一段时间
		                Thread.sleep(produceTaskSleepTime);
		            }
		            catch (Exception e)
		            {
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
/*==========================================================================================*
 * Description:	多线程之抓取行情子线程
 * Class:		SHIFetcher
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年11月17日 下午3:59:38
 *==========================================================================================*/
class SHIFetcher extends BaseSpider implements Runnable{
	private  String exId;
	private String dzpid;
	private QuotesRedisDao quotesRedisDao;
	private SbcQuotationHistDao sbcQuotationHistDao;
	private TradeCalendarDao tradeCalendarDao;
	
	public SHIFetcher(String exId, String dzpid, QuotesRedisDao quotesRedisDao, SbcQuotationHistDao sbcQuotationHistDao, TradeCalendarDao tradeCalendarDao){
		host = "app.epianhong.com";
		init(host);
		this.exId = exId;	
		this.dzpid = dzpid;
		this.quotesRedisDao= quotesRedisDao;
		this.sbcQuotationHistDao = sbcQuotationHistDao;
		this.tradeCalendarDao = tradeCalendarDao;
		
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	转换e片红的品种ID到服务器品种ID
	 * Method:		converseSbcId
	 * Author:		1.0 created by lijiaxuan at 2015年11月18日 上午9:22:23
	 *--------------------------------------------------------------------------------------*/
	private HashMap<String, String> getSbcIdMap(String exId, String dzpid){
		String url = "http://app.epianhong.com/getAllGoodsMarketInfoByDzp?uid=06CEA8BB-EADD-4DFA-915F-CB69D16A6A5E&phonetype=2&dzpid=" + dzpid;
		Integer sbcId  = 0;
		String sbcIdStr  = null;
		JSONArray jsonArray = null;
		HashMap<String, String> sbcIdMap = new HashMap<String, String>();
		SbcQuotationHistDomain sbcQuotationHistDomain=new SbcQuotationHistDomain();
		Document doc = fetchRealTimedoc(url);
		String code = "";
		try {
			String jsonStr = doc.select("body").text().toString();
			//System.out.println("jsonStr " + jsonStr);
			JSONObject qu = JSONObject.fromObject(jsonStr);
			jsonArray = JSONArray.fromObject(qu.get("goods"));
			//System.out.println("jsonArray " + jsonArray.size());
			//System.out.println("jsonArray " + jsonArray.get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
		}
		
		for (int i = 0; i < jsonArray.size(); i++) { 	
			JSONObject obj = JSONObject.fromObject(jsonArray.get(i));	
			code = obj.get("code").toString();
			sbcIdStr = quotesRedisDao.getSbcId(exId, code);
			if (sbcIdStr == null){
				continue;
			}
			sbcIdMap.put(obj.get("goodsId").toString(), sbcIdStr );
		}
		return sbcIdMap;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description: 上海交易所日K图
	 * Method:		dayKSHFetcher
	 * Author:		1.0 created by lijiaxuan at 2015年11月23日 上午10:12:20
	 *--------------------------------------------------------------------------------------*/
	public void dayKSHFetcher(SbcQuotationHistDao sbcQuotationHistDao){
		Map<String, String> codeIdMap = quotesRedisDao.hgetAll("sc:007000");
		String sbcId  = "";
		String sbcCode = "";
		Iterator iter = codeIdMap.entrySet().iterator();
		String url = "";
		double dealValueD = 0;
		JSONArray jsonArray = null;
		String dealValue  = null;
		double riseValueD = 0;
		String date = "";
		SbcQuotationHistDomain sbcQuotationHistDomain=new SbcQuotationHistDomain();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			sbcCode = entry.getKey().toString();
			sbcId = entry.getValue().toString();
			url = "http://www.youbiquan.com/marketPriceDTJson.do?showType=getMarketPriceDTListWithDays2&stockCode="+sbcCode+"&limit=100&market=6&_=1447863676658";
			Document doc = fetchRealTimedoc(url);	
			try {
				String jsonStr = "{\"datas\":" + doc.select("body").text().toString() +"}";
				//System.out.println("jsonStr " + jsonStr);
				JSONObject qu = JSONObject.fromObject(jsonStr);
				jsonArray = JSONArray.fromObject(qu.get("datas"));
				if (jsonArray == null || jsonArray.size() <=0) {
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
			}
			for (int i = 0; i < jsonArray.size(); i++) { 	
				JSONObject obj = JSONObject.fromObject(jsonArray.get(i));		
				//System.out.println("obj " + jsonArray.get(i));
				sbcQuotationHistDomain.init();
				try {
					
					sbcQuotationHistDomain.setClosePrice(obj.get("closePrice").toString());		
					sbcQuotationHistDomain.setDealVolume(obj.get("totalAmount").toString());
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getDealVolume())){
						dealValueD = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) * Double.parseDouble(sbcQuotationHistDomain.getDealVolume());
						dealValue = CommonUtil.getFormatDouble(dealValueD, 2);
					}	
					sbcQuotationHistDomain.setDealValue(dealValue);
					sbcQuotationHistDomain.setHighestPrice(obj.get("highPrice").toString());
					sbcQuotationHistDomain.setLowestPrice(obj.get("lowPrice").toString());
					sbcQuotationHistDomain.setOpeningPrice(obj.get("openPrice").toString());
					//sbcQuotationHistDomain.setRiseScope(obj.get("topdownrate").toString());
					sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));
					sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_DAY);
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice())){
						riseValueD = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
						
					}
					sbcQuotationHistDomain.setRiseValue( CommonUtil.getFormatDouble(riseValueD, 2));
					if (obj.get("date") == null) {
						continue;
					}
					date = CommonUtil.getFormatDate(obj.get("date").toString(), null);
					if (date.equals(CommonUtil.getNowDate())){ //去除当天的K图
						continue;
					}
					sbcQuotationHistDomain.setDate(date);
					//System.out.println(sbcQuotationHistDomain);
					sbcQuotationHistDao.addSbcQuotationHist(sbcQuotationHistDomain, "007000");

				} catch (Exception e) {
					e.printStackTrace();
					throw new YbkException(YbkException.CODE980013, YbkException.DESC980013);
				}
			}
		}
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		weekKSHFetcher
	 * Author:		1.0 created by lijiaxuan at 2015年11月24日 下午9:21:05
	 *--------------------------------------------------------------------------------------*/
	public void weekKSHFetcher(SbcQuotationHistDao sbcQuotationHistDao){
		Map<String, String> codeIdMap = quotesRedisDao.hgetAll("sc:007000");
		String sbcId  = "";
		String sbcCode = "";
		Iterator iter = codeIdMap.entrySet().iterator();
		String url = "";
		double dealValueD = 0;
		JSONArray jsonArray = null;
		String dealValue  = null;
		double riseValueD = 0;
		String date = "";
		SbcQuotationHistDomain sbcQuotationHistDomain=new SbcQuotationHistDomain();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			sbcCode = entry.getKey().toString();
			sbcId = entry.getValue().toString();
			url = "http://www.youbiquan.com/marketPriceDTJson.do?showType=getMarketPriceDTListWithDays2&stockCode="+sbcCode+"&limit=100&market=6&_=1447863676658";
			Document doc = fetchRealTimedoc(url);	
			try {
				String jsonStr = "{\"datas\":" + doc.select("body").text().toString() +"}";
				//System.out.println("jsonStr " + jsonStr);
				JSONObject qu = JSONObject.fromObject(jsonStr);
				jsonArray = JSONArray.fromObject(qu.get("datas"));
				if (jsonArray == null || jsonArray.size() <=0) {
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
			}
			for (int i = 0; i < jsonArray.size(); i++) { 	
				JSONObject obj = JSONObject.fromObject(jsonArray.get(i));		
				//System.out.println("obj " + jsonArray.get(i));
				sbcQuotationHistDomain.init();
				try {
					
					sbcQuotationHistDomain.setClosePrice(obj.get("closePrice").toString());		
					sbcQuotationHistDomain.setDealVolume(obj.get("totalAmount").toString());
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getDealVolume())){
						dealValueD = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) * Double.parseDouble(sbcQuotationHistDomain.getDealVolume());
						dealValue = CommonUtil.getFormatDouble(dealValueD, 2);
					}	
					sbcQuotationHistDomain.setDealValue(dealValue);
					sbcQuotationHistDomain.setHighestPrice(obj.get("highPrice").toString());
					sbcQuotationHistDomain.setLowestPrice(obj.get("lowPrice").toString());
					sbcQuotationHistDomain.setOpeningPrice(obj.get("openPrice").toString());
					//sbcQuotationHistDomain.setRiseScope(obj.get("topdownrate").toString());
					sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));
					sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_DAY);
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice())){
						riseValueD = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
						
					}
					sbcQuotationHistDomain.setRiseValue( CommonUtil.getFormatDouble(riseValueD, 2));
					if (obj.get("date") == null) {
						continue;
					}
					date = CommonUtil.getFormatDate(obj.get("date").toString(), null);
					if (date.equals(CommonUtil.getNowDate())){ //去除当天的K图
						continue;
					}
					sbcQuotationHistDomain.setDate(date);
					//System.out.println(sbcQuotationHistDomain);
					sbcQuotationHistDao.addSbcQuotationHist(sbcQuotationHistDomain, "007000" );

				} catch (Exception e) {
					e.printStackTrace();
					throw new YbkException(YbkException.CODE980013, YbkException.DESC980013);
				}
			}
		}
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		monthKSHFetcher
	 * Author:		1.0 created by lijiaxuan at 2015年11月24日 下午9:22:03
	 *--------------------------------------------------------------------------------------*/
	public void monthKSHFetcher(SbcQuotationHistDao sbcQuotationHistDao){
		Map<String, String> codeIdMap = quotesRedisDao.hgetAll("sc:007000");
		String sbcId  = "";
		String sbcCode = "";
		Iterator iter = codeIdMap.entrySet().iterator();
		String url = "";
		double dealValueD = 0;
		JSONArray jsonArray = null;
		String dealValue  = null;
		double riseValueD = 0;
		String date = "";
		SbcQuotationHistDomain sbcQuotationHistDomain=new SbcQuotationHistDomain();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			sbcCode = entry.getKey().toString();
			sbcId = entry.getValue().toString();
			url = "http://www.youbiquan.com/marketPriceDTJson.do?showType=getMarketPriceDTListWithDays2&stockCode="+sbcCode+"&limit=100&market=6&_=1447863676658";
			Document doc = fetchRealTimedoc(url);	
			try {
				String jsonStr = "{\"datas\":" + doc.select("body").text().toString() +"}";
				//System.out.println("jsonStr " + jsonStr);
				JSONObject qu = JSONObject.fromObject(jsonStr);
				jsonArray = JSONArray.fromObject(qu.get("datas"));
				if (jsonArray == null || jsonArray.size() <=0) {
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
			}
			for (int i = 0; i < jsonArray.size(); i++) { 	
				JSONObject obj = JSONObject.fromObject(jsonArray.get(i));		
				//System.out.println("obj " + jsonArray.get(i));
				sbcQuotationHistDomain.init();
				try {
					
					sbcQuotationHistDomain.setClosePrice(obj.get("closePrice").toString());		
					sbcQuotationHistDomain.setDealVolume(obj.get("totalAmount").toString());
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getDealVolume())){
						dealValueD = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) * Double.parseDouble(sbcQuotationHistDomain.getDealVolume());
						dealValue = CommonUtil.getFormatDouble(dealValueD, 2);
					}	
					sbcQuotationHistDomain.setDealValue(dealValue);
					sbcQuotationHistDomain.setHighestPrice(obj.get("highPrice").toString());
					sbcQuotationHistDomain.setLowestPrice(obj.get("lowPrice").toString());
					sbcQuotationHistDomain.setOpeningPrice(obj.get("openPrice").toString());
					//sbcQuotationHistDomain.setRiseScope(obj.get("topdownrate").toString());
					sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));
					sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_DAY);
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice())){
						riseValueD = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
						
					}
					sbcQuotationHistDomain.setRiseValue( CommonUtil.getFormatDouble(riseValueD, 2));
					if (obj.get("date") == null) {
						continue;
					}
					date = CommonUtil.getFormatDate(obj.get("date").toString(), null);
					if (date.equals(CommonUtil.getNowDate())){ //去除当天的K图
						continue;
					}
					sbcQuotationHistDomain.setDate(date);
					//System.out.println(sbcQuotationHistDomain);
					sbcQuotationHistDao.addSbcQuotationHist(sbcQuotationHistDomain, "007000" );

				} catch (Exception e) {
					e.printStackTrace();
					throw new YbkException(YbkException.CODE980013, YbkException.DESC980013);
				}
			}
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取日K图数据
	 * Method:		dayKFetcher
	 * Author:		1.0 created by lijiaxuan at 2015年11月17日 上午9:20:21
	 *--------------------------------------------------------------------------------------*/
	public void dayKFetcher(HashMap<String, String> sbcIdMap, String exId, SbcQuotationHistDao sbcQuotationHistDao){
		Iterator iter = sbcIdMap.entrySet().iterator();
		String sbcId  = "";
		String goodsId = "";
		JSONArray jsonArray = null;
		String dealValue  = null;
		double riseValueD = 0;
		String date = "";
		SbcQuotationHistDomain sbcQuotationHistDomain=new SbcQuotationHistDomain();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			goodsId = entry.getKey().toString();
			sbcId = entry.getValue().toString();
			String url = "http://app.epianhong.com/gad?uid=06CEA8BB-EADD-4DFA-915F-CB69D16A6A5E&phonetype=2&gd="+goodsId;	
			Document doc = fetchRealTimedoc(url);	
			try {
				String jsonStr = doc.select("body").text().toString();
				//System.out.println("jsonStr " + jsonStr);
				JSONObject qu = JSONObject.fromObject(jsonStr);
				jsonArray = JSONArray.fromObject(qu.get("datas"));
				if (jsonArray == null || jsonArray.size() <=0) {
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
			}
			for (int i = 0; i < jsonArray.size(); i++) { 	
				JSONObject obj = JSONObject.fromObject(jsonArray.get(i));		 
				sbcQuotationHistDomain.init();
				try {
					sbcQuotationHistDomain.setClosePrice(obj.get("closeprice").toString());		
					dealValue = obj.get("totalprice").toString();
					if (CommonUtil.isStringDigit(dealValue)){
						dealValue = CommonUtil.getFormatDouble(Double.parseDouble(dealValue), 2);
					}		
					sbcQuotationHistDomain.setDealValue(dealValue);
					sbcQuotationHistDomain.setDealVolume(obj.get("amout").toString());
					sbcQuotationHistDomain.setHighestPrice(obj.get("topprice").toString());
					sbcQuotationHistDomain.setLowestPrice(obj.get("lowprice").toString());
					sbcQuotationHistDomain.setOpeningPrice(obj.get("openprice").toString());
					sbcQuotationHistDomain.setRiseScope(obj.get("topdownrate").toString());
					sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));
					sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_DAY);
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice())){
						riseValueD = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
						
					}
					sbcQuotationHistDomain.setRiseValue( CommonUtil.getFormatDouble(riseValueD, 2));
					if (obj.get("date") == null) {
						continue;
					}
					date = CommonUtil.getTimeFromTimstamp(obj.get("date").toString(), "yyyyMMdd");
					//if (date.equals(CommonUtil.getNowDate())){ //去除当天的K图
					//	continue;
					//}
					sbcQuotationHistDomain.setDate(date);
					//System.out.println(sbcQuotationHistDomain);
					sbcQuotationHistDao.addSbcQuotationHist(sbcQuotationHistDomain, exId );

				} catch (Exception e) {
					e.printStackTrace();
					throw new YbkException(YbkException.CODE980013, YbkException.DESC980013);
				}
			}
			
		}
		 sbcQuotationHistDomain = null;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取周K图
	 * Method:		weekKFetcher
	 * Author:		1.0 created by lijiaxuan at 2015年11月17日 上午11:20:21
	 *--------------------------------------------------------------------------------------*/
	public void weekKFetcher(HashMap<String, String> sbcIdMap, String exId, SbcQuotationHistDao sbcQuotationHistDao, TradeCalendarDao tradeCalendarDao){
		Iterator iter = sbcIdMap.entrySet().iterator();
		String sbcId  = "";
		String goodsId = "";
		JSONArray jsonArray = null;
		String dealValue  = null;
		double riseValueD = 0;
		String date = "";
		String type = "";
		TradeCalendarDomain  tradeCalendarDomain = new TradeCalendarDomain();
		SbcQuotationHistDomain sbcQuotationHistDomain=new SbcQuotationHistDomain();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			goodsId = entry.getKey().toString();
			sbcId = entry.getValue().toString();
			String url = "http://app.epianhong.com/getWeekData?uid=06CEA8BB-EADD-4DFA-915F-CB69D16A6A5E&phonetype=2&goodsid="+goodsId;	
			Document doc = fetchRealTimedoc(url);	
			try {
				String jsonStr = doc.select("body").text().toString();
				//System.out.println("jsonStr " + jsonStr);
				JSONObject qu = JSONObject.fromObject(jsonStr);
				jsonArray = JSONArray.fromObject(qu.get("datas"));
				if (jsonArray == null || jsonArray.size() <=0) {
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
			}
			for (int i = 0; i < jsonArray.size(); i++) { 	
				JSONObject obj = JSONObject.fromObject(jsonArray.get(i));		 
				sbcQuotationHistDomain.init();
				try {
					if (obj.get("date") == null) {
						continue;
					}			
					date = CommonUtil.getTimeFromTimstamp(obj.get("date").toString(), "yyyyMMdd");
					//System.out.println("date " + date);
					tradeCalendarDomain = tradeCalendarDao.findTradeCalendar(date);
					if (tradeCalendarDomain != null){
						type = tradeCalendarDomain.getType();
					}
					//交易所的一周最后一日交易日与当前日期不符则不存入K图 
					//月末
					if (type.compareTo(CommonVariables.COMMON_LEVEL_04) > 0){
						int typeI = Integer.parseInt(type) - 4 ;
						if (typeI < 10 ){
							type = "0" + typeI;
						} else {
							type = "" + typeI;
						}
					}
					if (! CommonMaps.tradeDayMap.get(exId).toString().equals(type)){
						continue;
					}
					sbcQuotationHistDomain.setClosePrice(obj.get("closeprice").toString());		
					dealValue = obj.get("totalprice").toString();
					if (CommonUtil.isStringDigit(dealValue)){
						dealValue = CommonUtil.getFormatDouble(Double.parseDouble(dealValue), 2);
					}		
					sbcQuotationHistDomain.setDealValue(dealValue);
					sbcQuotationHistDomain.setDealVolume(obj.get("amout").toString());
					sbcQuotationHistDomain.setHighestPrice(obj.get("topprice").toString());
					sbcQuotationHistDomain.setLowestPrice(obj.get("lowprice").toString());
					sbcQuotationHistDomain.setOpeningPrice(obj.get("openprice").toString());
					sbcQuotationHistDomain.setRiseScope(obj.get("topdownrate").toString());
					sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));
					sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_WEK);
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice())){
						riseValueD = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
						
					}
					sbcQuotationHistDomain.setRiseValue( CommonUtil.getFormatDouble(riseValueD, 2));
					if (obj.get("date") == null) {
						continue;
					}
					
					//if (date.equals(CommonUtil.getNowDate())){ //去除当天的K图
					///	continue;
					//}
					sbcQuotationHistDomain.setDate(date);
					//System.out.println(sbcQuotationHistDomain);
					sbcQuotationHistDao.addSbcQuotationHist(sbcQuotationHistDomain, exId );

				} catch (Exception e) {
					e.printStackTrace();
					throw new YbkException(YbkException.CODE980013, YbkException.DESC980013);
				}
			}
			
		}
		tradeCalendarDomain = null;
		 sbcQuotationHistDomain = null;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取月K图
	 * Method:		monthKFetcher
	 * Author:		1.0 created by lijiaxuan at 2015年11月18日 下午5:57:31
	 *--------------------------------------------------------------------------------------*/
	public void monthKFetcher(HashMap<String, String> sbcIdMap, String exId, SbcQuotationHistDao sbcQuotationHistDao, TradeCalendarDao tradeCalendarDao){
		Iterator iter = sbcIdMap.entrySet().iterator();
		String sbcId  = "";
		String goodsId = "";
		JSONArray jsonArray = null;
		String dealValue  = null;
		double riseValueD = 0;
		String date = "";
		String type = "";
		TradeCalendarDomain  tradeCalendarDomain = new TradeCalendarDomain();
		SbcQuotationHistDomain sbcQuotationHistDomain=new SbcQuotationHistDomain();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			goodsId = entry.getKey().toString();
			sbcId = entry.getValue().toString();
			String url = "http://app.epianhong.com/getMonthData?uid=06CEA8BB-EADD-4DFA-915F-CB69D16A6A5E&phonetype=2&goodsid="+goodsId;	
			Document doc = fetchRealTimedoc(url);	
			try {
				String jsonStr = doc.select("body").text().toString();
				//System.out.println("jsonStr " + jsonStr);
				JSONObject qu = JSONObject.fromObject(jsonStr);
				jsonArray = JSONArray.fromObject(qu.get("datas"));
				if (jsonArray == null || jsonArray.size() <=0) {
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
			}
			for (int i = 0; i < jsonArray.size(); i++) { 	
				JSONObject obj = JSONObject.fromObject(jsonArray.get(i));		 
				sbcQuotationHistDomain.init();
				try {
					date = CommonUtil.getTimeFromTimstamp(obj.get("date").toString(), "yyyyMMdd");
					tradeCalendarDomain = tradeCalendarDao.findTradeCalendar(date);
					//交易所的一周最后一日交易日与当前日期不符则不存入K图
					//不是月末
					if (type.compareTo(CommonVariables.COMMON_LEVEL_04) <= 0){
						continue;
					}
					
					sbcQuotationHistDomain.setClosePrice(obj.get("closeprice").toString());		
					dealValue = obj.get("totalprice").toString();
					if (CommonUtil.isStringDigit(dealValue)){
						dealValue = CommonUtil.getFormatDouble(Double.parseDouble(dealValue), 2);
					}		
					sbcQuotationHistDomain.setDealValue(dealValue);
					sbcQuotationHistDomain.setDealVolume(obj.get("amout").toString());
					sbcQuotationHistDomain.setHighestPrice(obj.get("topprice").toString());
					sbcQuotationHistDomain.setLowestPrice(obj.get("lowprice").toString());
					sbcQuotationHistDomain.setOpeningPrice(obj.get("openprice").toString());
					sbcQuotationHistDomain.setRiseScope(obj.get("topdownrate").toString());
					sbcQuotationHistDomain.setSbcId(Integer.parseInt(sbcId));
					sbcQuotationHistDomain.setType(CommonVariables.TIME_INTERVAL_MON);
					if (CommonUtil.isStringDigit(sbcQuotationHistDomain.getClosePrice()) && CommonUtil.isStringDigit(sbcQuotationHistDomain.getOpeningPrice())){
						riseValueD = Double.parseDouble(sbcQuotationHistDomain.getClosePrice()) - Double.parseDouble(sbcQuotationHistDomain.getOpeningPrice());
						
					}
					sbcQuotationHistDomain.setRiseValue( CommonUtil.getFormatDouble(riseValueD, 2));
					if (obj.get("date") == null) {
						continue;
					}
					
					//if (date.equals(CommonUtil.getNowDate())){ //去除当天的K图
					//	continue;
					//}
					sbcQuotationHistDomain.setDate(date);
					//System.out.println(sbcQuotationHistDomain);
					sbcQuotationHistDao.addSbcQuotationHist(sbcQuotationHistDomain, exId);

				} catch (Exception e) {
					e.printStackTrace();
					throw new YbkException(YbkException.CODE980013, YbkException.DESC980013);
				}
			}
			
		}
		tradeCalendarDomain = null;
		 sbcQuotationHistDomain = null;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取日K图数据
	 * Method:		commonFetcher
	 * Author:		1.0 created by lijiaxuan at 2015年11月17日 上午9:20:21
	 *--------------------------------------------------------------------------------------*/
	private void commonFetcher( String exId,String dzpid, QuotesRedisDao quotesRedisDao, SbcQuotationHistDao sbcQuotationHistDao, TradeCalendarDao tradeCalendarDao){
		HashMap<String, String> sbcIdMap = getSbcIdMap(exId, dzpid);
		dayKFetcher( sbcIdMap,  exId,  sbcQuotationHistDao);
		weekKFetcher( sbcIdMap,  exId,  sbcQuotationHistDao, tradeCalendarDao);
		monthKFetcher( sbcIdMap,  exId,  sbcQuotationHistDao, tradeCalendarDao);
		dayKSHFetcher(sbcQuotationHistDao);
		//weekKSHFetcher(sbcQuotationHistDao);
		//monthKSHFetcher(sbcQuotationHistDao);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		jsonToQuotationsDomain
	 * Author:		1.0 created by lijiaxuan at 2015年11月17日 下午5:19:41
	 *--------------------------------------------------------------------------------------*/
	public void jsonToQuotationsDomain(QuotationsDomain quotationsDomain, JSONObject obj){
		String averagePrice  = "-";
		String openingPrice = "";
		String currentPrice = "";
		String highestPrice = "";
		String lowestPrice = "";
		
		quotationsDomain.setSbcCode(obj.get("code").toString().trim());
		quotationsDomain.setSbcName(obj.get("fullname").toString());
		quotationsDomain.setYesterClPrice(obj.get("YesterBalancePrice").toString());
		openingPrice = obj.get("OpenPrice").toString().trim();
		if (CommonUtil.isStringZero(openingPrice)){
			openingPrice = quotationsDomain.getYesterClPrice();
		}
		quotationsDomain.setOpeningPrice(openingPrice);
		currentPrice = obj.get("CurPrice").toString().trim();
		if (CommonUtil.isStringZero(currentPrice)){
			currentPrice = quotationsDomain.getYesterClPrice();
		}
		quotationsDomain.setCurrentPrice(currentPrice);	
	
		quotationsDomain.setRiseScope(obj.get("CurrentGains").toString());
		quotationsDomain.setDealVolume(obj.get("TotalAmount").toString());
		quotationsDomain.setDealValue(obj.get("TotalMoney").toString());
		
		
		highestPrice = obj.get("HighPrice").toString().trim();
		if (CommonUtil.isStringZero(highestPrice)){
			highestPrice = quotationsDomain.getYesterClPrice();
		}
		quotationsDomain.setHighestPrice(highestPrice);
		lowestPrice = obj.get("LowPrice").toString().trim();
		if (CommonUtil.isStringZero(lowestPrice)){
			lowestPrice = quotationsDomain.getYesterClPrice();
		}
		quotationsDomain.setLowestPrice(lowestPrice);
		if (quotationsDomain.getSbcName().contains("指数")){
			averagePrice = "";
		} else if (CommonUtil.isStringDigit(quotationsDomain.getDealValue()) && CommonUtil.isStringDigit(quotationsDomain.getDealVolume())){
			if (quotationsDomain.getDealVolume().equals("0")) {
				averagePrice = quotationsDomain.getCurrentPrice();
			} else {
				Double averagePriceD = Double.parseDouble(quotationsDomain.getDealValue()) / Double.parseDouble(quotationsDomain.getDealVolume());
				averagePrice = CommonUtil.getFormatDouble(averagePriceD, 2);
			}	
		} 		
		quotationsDomain.setAveragePrice(averagePrice);
	}
	
	
	public void run()
    {
		switch (this.exId){		
			case CommonVariables.EXCHANGE_SHANGHAI: {
				//shanghai007000Fetcher(this.quotesRedisDao);	
				break;
			}
			default: {				
				commonFetcher(exId, dzpid, this.quotesRedisDao, this.sbcQuotationHistDao, this.tradeCalendarDao);
				break;
			}				
		}

    }
}
