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
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.spider.web.VO.FetchVO;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.common.BaseSpider;
import com.eyoubika.common.RedisDao;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.YbkException;
import com.eyoubika.info.domain.QuotationsDomain;
import com.eyoubika.spider.domain.*;
import com.eyoubika.spider.dao.*;
import com.eyoubika.util.*;

/*==========================================================================================*
 * Description:	交易所行情数据抓取小蜘蛛哈哈
 * Class:		QuotationsFetcherAL
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年9月30日 上午9:36:32
 *==========================================================================================*/
public class QuotationsFetcherAL extends BaseSpider {
	private QuotesRedisDao quotesRedisDao;
	private SbcBasicDao sbcBasicDao;

	
	
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
	 * Description:	TODO
	 * Method:		fetchQuotations
	 * Author:		1.0 created by lijiaxuan at 2015年8月19日 下午3:58:45
	 *--------------------------------------------------------------------------------------*/
	public void fetchQuotations(){
		//initQuotRedis(fetchVO);
		host = "www.zongyihui.cn";
		//Integer sbcId  = 0;
		init(host);
		int maxMarket = 9;
		int produceTaskSleepTime = 200; //0.2s  
		int waitSleepTime = 3000; //3s  //三秒等待
		int maxPoolSize = 100; 		// 线程池维护线程的最大数量
		int minPoolSize = 60;	 	// 线程池维护线程的最少数量
		long keepAliveTime = 3;		// 线程池维护线程所允许的空闲时间
		
		
		Map<String, String> exMap = new HashMap<String, String>();
		
		exMap.put(CommonVariables.EXCHANGE_NANJING, "http://180.97.2.74:16929/tradeweb/hq/getHqV_lbData.jsp");//南京文交所
		exMap.put(CommonVariables.EXCHANGE_NANFANG, "http://121.40.125.254:18080//tradeweb/refreshHQ"); //南方文交所
		exMap.put( CommonVariables.EXCHANGE_ZHONGNAN, "http://180.97.2.111:15910/tradeweb/hq/getHqV_lbData.jsp");	//中南文交所
		exMap.put(CommonVariables.EXCHANGE_FULITE, "http://dzp.wjybk.com/adv/cs_com_cn/quotation.html"); //福丽特 ?math.random()
		exMap.put(CommonVariables.EXCHANGE_JINMAJIA, "http://218.246.20.83:16929/tradeweb/hq/getHqV_lbData.jsp");//金马甲
		exMap.put(CommonVariables.EXCHANGE_SHANGHAI, "http://ta.shscce.com:8080/front/hq/delay_hq.htm");//上海
		exMap.put(CommonVariables.EXCHANGE_YIJIAO, "http://jy.cacecybk.com.cn:16929/tradeweb/hq/getHqV_lbData.jsp");//艺交所
		List<String> fetchList = new ArrayList<String>();	
		
		String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	
		//String[] marketIds = {CommonVariables.EXCHANGE_SHANGHAI};
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
			
			if (CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_AM, CommonVariables.TRADEHOUR_END_AM) 
				|| CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_PM, CommonVariables.TRADEHOUR_END_PM)){
			//if (index < fetchListSize){
		            try
		            {
		            	index++;	   
		                int id = index%fetchListSize;		  
		                String exId = marketIds[id];
		                threadPool.execute(new Fetcher( exId,  exMap.get(exId) ,quotesRedisDao));
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
 * Class:		Fetcher
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年8月19日 下午3:59:38
 *==========================================================================================*/
class Fetcher extends BaseSpider implements Runnable{
	private  String exId;
	private String url;
	private QuotesRedisDao quotesRedisDao;
	public Fetcher(String exId, String url, QuotesRedisDao quotesRedisDao){
		host = "www.zongyihui.cn";
		init(host);
		this.exId = exId;	
		this.url = url;
		this.quotesRedisDao= quotesRedisDao;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	改成抓取一片红的数据了
	 * Method:		fulite004000Fetcher
	 * Author:		3.0 created by lijiaxuan at 2016年1月11日 下午2:43:28
	 *--------------------------------------------------------------------------------------*/
	private void fulite004000Fetcher( QuotesRedisDao quotesRedisDao){
		String url = "http://www.epianhong.com/web/gagmi?dzpid=1";
		Integer sbcId  = 0;
		String exId= CommonVariables.EXCHANGE_FULITE;
		host = "www.epianhong.com";
		String averagePrice = "";
		String openingPrice = "";
		String currentPrice = "";
		String highestPrice = "";
		String lowestPrice = "";
		String sbcVolume = "0";
		String turnoverRate = "0";
		String marketValue = "0";	
		String riseValue = "0";
	
		double riseScopeD = 0;
		String dealValue = "0";
		String dealVolume = "0";
		String yesterPrice = "";
		JSONArray jsonArray = null;
		QuotationsDomain quotationsDomain=new QuotationsDomain();
		Document doc = fetchRealTimedoc(url);
		try {
			String jsonStr = doc.select("body").text().toString();
			JSONObject qu = JSONObject.fromObject(jsonStr);
			jsonArray = JSONArray.fromObject(qu.get("goods"));
		
		 for (int i = 0; i < jsonArray.size(); i++) { 	
			JSONObject obj = JSONObject.fromObject(jsonArray.get(i));		 
			quotationsDomain.init();
			quotationsDomain.setRecordTime(CommonUtil.getNowTimeStamp());//抓取时间
				quotationsDomain.setSbcCode(obj.get("code").toString().trim());//品种编码
				String querySbcId = quotesRedisDao.getSbcId(exId, quotationsDomain.getSbcCode());
				if (querySbcId==null){
					continue;
				}
				
				sbcId = Integer.parseInt(querySbcId);//品种ID
				quotationsDomain.setExId(exId);//品种交易所ID
				quotationsDomain.setSbcId(sbcId);
				quotationsDomain.setSbcName(obj.get("name").toString());	//品种名称
				
				yesterPrice = obj.get("ydclosingprice").toString().trim();
				if (!CommonUtil.isStringDigit(yesterPrice)  || CommonUtil.isStringZero(yesterPrice)){
					yesterPrice = quotesRedisDao.getDayClosePrice(querySbcId);
				}
				quotationsDomain.setYesterClPrice(yesterPrice);//昨日收盘价
				
				openingPrice = obj.get("openingprice").toString().trim();
				if (!CommonUtil.isStringDigit(openingPrice)  || CommonUtil.isStringZero(openingPrice)){
					openingPrice = quotationsDomain.getYesterClPrice();
				}
				quotationsDomain.setOpeningPrice(openingPrice);
				
				currentPrice = obj.get("recentprice").toString().trim();
				if (!CommonUtil.isStringDigit(currentPrice) || CommonUtil.isStringZero(currentPrice)){
					currentPrice = quotationsDomain.getYesterClPrice();
				}
				quotationsDomain.setCurrentPrice(currentPrice);	
	
				riseValue = obj.get("topdown").toString().trim(); //涨跌
				if (!CommonUtil.isStringDigit(riseValue)){
					riseValue = "0";
				}
				quotationsDomain.setRiseValue(riseValue);
			
				
				if (CommonUtil.isStringDigit(quotationsDomain.getYesterClPrice())){
					riseScopeD = ( Double.parseDouble(riseValue) /  Double.parseDouble(quotationsDomain.getYesterClPrice())) * 100;
				} 
				quotationsDomain.setRiseScope(CommonUtil.getFormatDouble(riseScopeD, 2));	//涨幅
				
				dealVolume = obj.get("dealcount").toString().trim(); //交易量
				if ( !CommonUtil.isStringDigit(dealVolume)){
					dealVolume = "0";
				}
				quotationsDomain.setDealVolume(dealVolume);
				
				dealValue = obj.get("dealprice").toString().trim();//交易额
				if ( !CommonUtil.isStringDigit(dealValue)){
					dealValue = "0";
				}
				quotationsDomain.setDealValue(dealValue);
					
				sbcVolume = quotesRedisDao.getSbcVolume(querySbcId);
				if (CommonUtil.isStringDigit(quotationsDomain.getDealVolume()) && CommonUtil.isStringDigit(sbcVolume) && !CommonUtil.isStringZero(sbcVolume)){
					double turnoverRateD = (Double.parseDouble(quotationsDomain.getDealVolume())/Double.parseDouble(sbcVolume) ) * 100;
					turnoverRate = CommonUtil.getFormatDouble(turnoverRateD, 4);
				}
				quotationsDomain.setTurnoverRate(turnoverRate);  //换手率
				if (quotationsDomain.getSbcName().equals("综合指数")){
					marketValue = quotesRedisDao.getMarketValue(querySbcId);
				} else {
					if (CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice()) && CommonUtil.isStringDigit(sbcVolume)){
						double marketValueD = Double.parseDouble(quotationsDomain.getCurrentPrice())*Double.parseDouble(sbcVolume);
						marketValue = CommonUtil.getFormatDouble(marketValueD, 2);
					}
				}
				quotationsDomain.setMarketValue(marketValue);
				
				highestPrice = obj.get("topprice").toString();
				if (!CommonUtil.isStringDigit(highestPrice) || CommonUtil.isStringZero(highestPrice)){
					highestPrice = quotationsDomain.getYesterClPrice();
				}
				quotationsDomain.setHighestPrice(highestPrice);
				lowestPrice =obj.get("bottomprice").toString().trim();
				if (!CommonUtil.isStringDigit(lowestPrice) || CommonUtil.isStringZero(lowestPrice)){
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
				//System.out.println(quotationsDomain);
				quotesRedisDao.addQuoteDomain(quotationsDomain);							
					//String content = i +" -->: " +quotationsDomain.toString()+ "\n" +"============================ \n" ;
					//CommonUtil.toLog(content);
		 	}	
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
		}
			
		quotationsDomain = null;
	}
	/*private void fulite004000Fetcher( QuotesRedisDao quotesRedisDao){
		String url = "http://www.epianhong.com/screen_fltall.html";
		Integer sbcId  = 0;
		String exId= CommonVariables.EXCHANGE_FULITE;
		host = "www.epianhong.com";
		String averagePrice = "";
		String openingPrice = "";
		String currentPrice = "";
		String highestPrice = "";
		String lowestPrice = "";
		String sbcVolume = "0";
		String turnoverRate = "0";
		String marketValue = "0";	
		String riseScope = "0";
	
		double riseValueD = 0;
		String dealValue = "0";
		String dealVolume = "0";
		String yesterPrice = "";
		QuotationsDomain quotationsDomain=new QuotationsDomain();
		
		
		Document doc = fetchRealTimedoc(url);
		try {
		System.out.println("doc " + doc.toString());
		Elements trs = doc.select("table.scrollTable").get(0).select("tr");		//正文				
		for (Element tr : trs) {			
			Elements tds = tr.select("td");
			if (tds != null && ! tds.text().equals("")){
				quotationsDomain.init();
				quotationsDomain.setRecordTime(CommonUtil.getNowTimeStamp());
				quotationsDomain.setSbcCode(tds.eq(1).text().trim());
				String querySbcId = quotesRedisDao.getSbcId(exId, quotationsDomain.getSbcCode());
				if (querySbcId==null){
					//System.out.println( "找不到对应的Id： "+  exId  + quotationsDomain.getSbcCode());
					continue;
				}
				sbcId = Integer.parseInt(querySbcId);
				quotationsDomain.setSbcName(tds.eq(2).text().trim());					
				quotationsDomain.setExId(exId);	
				quotationsDomain.setSbcId(sbcId);
				
				yesterPrice = tds.eq(3).text().trim();
				if (!CommonUtil.isStringDigit(yesterPrice) || CommonUtil.isStringZero(yesterPrice)){
				yesterPrice = quotesRedisDao.getDayClosePrice(querySbcId);
				}
				quotationsDomain.setYesterClPrice(yesterPrice);	//昨日收盘价
				
				openingPrice = tds.eq(4).text().trim();
				if (!CommonUtil.isStringDigit(openingPrice) || CommonUtil.isStringZero(openingPrice)){
					openingPrice = quotationsDomain.getYesterClPrice();
				}
				quotationsDomain.setOpeningPrice(openingPrice);
				currentPrice = tds.eq(5).text().trim();
				if (!CommonUtil.isStringDigit(currentPrice) || CommonUtil.isStringZero(currentPrice)){
					currentPrice = quotationsDomain.getYesterClPrice();
				}
				quotationsDomain.setCurrentPrice(currentPrice);									
				if (CommonUtil.isStringDigit(quotationsDomain.getYesterClPrice()) && CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice())){
					riseValueD = Double.parseDouble(quotationsDomain.getCurrentPrice()) - Double.parseDouble(quotationsDomain.getYesterClPrice());
				} 
				riseScope = tds.eq(6).text().replace("%", "").trim(); //涨幅
				if (!CommonUtil.isStringDigit(riseScope)){
					riseScope = "0";
				} 
				quotationsDomain.setRiseScope(riseScope);
				quotationsDomain.setRiseValue(CommonUtil.getFormatDouble(riseValueD, 2));	//涨跌

				dealVolume = tds.eq(7).text().trim(); //交易量
				if ( !CommonUtil.isStringDigit(dealVolume)){
					dealVolume = "0";
				}
				quotationsDomain.setDealVolume(dealVolume);
	
				dealValue = tds.eq(8).text().trim();//交易额
				if ( !CommonUtil.isStringDigit(dealValue)){
					dealValue = "0";
				}
				quotationsDomain.setDealValue(dealValue);

				
				sbcVolume = quotesRedisDao.getSbcVolume(querySbcId);
				if (CommonUtil.isStringDigit(quotationsDomain.getDealVolume()) && CommonUtil.isStringDigit(sbcVolume) && !CommonUtil.isStringZero(sbcVolume)){
					double turnoverRateD = (Double.parseDouble(quotationsDomain.getDealVolume())/Double.parseDouble(sbcVolume) ) * 100;
					turnoverRate = CommonUtil.getFormatDouble(turnoverRateD, 4);
				}
				quotationsDomain.setTurnoverRate(turnoverRate);  //换手率
				if (quotationsDomain.getSbcName().equals("综合指数")){
					marketValue = quotesRedisDao.getMarketValue(querySbcId);
				} else {
					if (CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice()) && CommonUtil.isStringDigit(sbcVolume)){
						double marketValueD = Double.parseDouble(quotationsDomain.getCurrentPrice())*Double.parseDouble(sbcVolume);
						marketValue = CommonUtil.getFormatDouble(marketValueD, 2);
					}
				}
				quotationsDomain.setMarketValue(marketValue);
				
				highestPrice = tds.eq(9).text().trim();
				if (!CommonUtil.isStringDigit(highestPrice) || CommonUtil.isStringZero(highestPrice)){
					highestPrice = quotationsDomain.getYesterClPrice();
				}
				quotationsDomain.setHighestPrice(highestPrice);
				lowestPrice = tds.eq(10).text().trim();
				if (!CommonUtil.isStringDigit(lowestPrice) || CommonUtil.isStringZero(lowestPrice)){
					lowestPrice = quotationsDomain.getYesterClPrice();
				}
				quotationsDomain.setLowestPrice(tds.eq(10).text().trim());		
				if (CommonUtil.isStringDigit(quotationsDomain.getDealValue()) && CommonUtil.isStringDigit(quotationsDomain.getDealVolume())){
					if (quotationsDomain.getDealVolume().equals("0")) {
						averagePrice = quotationsDomain.getCurrentPrice();
					} else {
						Double averagePriceD = Double.parseDouble(quotationsDomain.getDealValue()) / Double.parseDouble(quotationsDomain.getDealVolume());
						averagePrice = CommonUtil.getFormatDouble(averagePriceD, 2);
					}	
				} 		
				quotationsDomain.setAveragePrice(averagePrice);
					
				
				System.out.println(quotationsDomain);
				//quotesRedisDao.addQuoteDomain(quotationsDomain);
			}
		}
					
					
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
		}		
		quotationsDomain = null;
	}*/
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		fulite004000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年9月30日 下午3:49:56
	 *--------------------------------------------------------------------------------------*/
	/*private void fulite004000Fetcher( QuotesRedisDao quotesRedisDao){
		String url = "http://dzp.wjybk.com/adv/cs_com_cn/quotation.html" + "?=" + (1+Math.random()*(1000-1+1));
		Integer sbcId  = 0;
		String exId= CommonVariables.EXCHANGE_FULITE;
		host = "dzp.wjybk.com";
		String averagePrice = "";
		String openingPrice = "";
		String currentPrice = "";
		String highestPrice = "";
		String lowestPrice = "";
		String sbcVolume = "0";
		String turnoverRate = "0";
		String marketValue = "0";	
		String riseScope = "0";
	
		double riseValueD = 0;
		String dealValue = "0";
		String dealVolume = "0";
		String yesterPrice = "";
		QuotationsDomain quotationsDomain=new QuotationsDomain();
		Document doc = fetchRealTimedoc(url);
		try {
			String exQuoteStr = doc.select("body").text().toString();
			if ( ! CommonUtil.isStringNull(exQuoteStr)) {
				String[] exQuoteList = exQuoteStr.split("\\|");
				int exQuoteSize = exQuoteList.length;
				for (int i = 0; i < exQuoteSize; i++){
					String sbcQuoteStr = exQuoteList[i];
					String[] sbcQuoteList = sbcQuoteStr.split(",");
					quotationsDomain.init();
					quotationsDomain.setRecordTime(CommonUtil.getNowTimeStamp());	//抓取时间
					quotationsDomain.setSbcCode(sbcQuoteList[0].trim());			//品种编码
					
					String querySbcId = quotesRedisDao.getSbcId(exId, quotationsDomain.getSbcCode());
					if (querySbcId==null){	
						continue;
					}		
					sbcId = Integer.parseInt(querySbcId);		//品种ID
					quotationsDomain.setExId(exId);				//品种交易所ID
					quotationsDomain.setSbcId(sbcId);
					quotationsDomain.setSbcName(sbcQuoteList[1].trim());	//品种名称
					yesterPrice = sbcQuoteList[2].trim();
					if (!CommonUtil.isStringDigit(yesterPrice)  || CommonUtil.isStringZero(yesterPrice)){
						yesterPrice = quotesRedisDao.getDayClosePrice(querySbcId);
					}
					quotationsDomain.setYesterClPrice(yesterPrice);	//昨日收盘价
					openingPrice = sbcQuoteList[3].trim();			//开盘价
					if (!CommonUtil.isStringDigit(openingPrice) || CommonUtil.isStringZero(openingPrice)){
						openingPrice = quotationsDomain.getYesterClPrice();
					}
					quotationsDomain.setOpeningPrice(openingPrice);
					currentPrice = sbcQuoteList[4].trim();  //现价
					if (!CommonUtil.isStringDigit(currentPrice)  || CommonUtil.isStringZero(currentPrice)){
						currentPrice = quotationsDomain.getYesterClPrice();
					}
					quotationsDomain.setCurrentPrice(currentPrice);		
					riseScope = sbcQuoteList[5].trim(); //涨幅
					if (!CommonUtil.isStringDigit(riseScope)){
						riseScope = "0";
					}
					quotationsDomain.setRiseScope(riseScope);	
					if (CommonUtil.isStringDigit(quotationsDomain.getYesterClPrice()) && CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice())){
						riseValueD = Double.parseDouble(quotationsDomain.getCurrentPrice()) - Double.parseDouble(quotationsDomain.getYesterClPrice());
					} 
					quotationsDomain.setRiseValue(CommonUtil.getFormatDouble(riseValueD, 2));	//涨跌
						
					dealVolume = sbcQuoteList[6].trim(); //交易量
					if ( !CommonUtil.isStringDigit(dealVolume)){
						dealVolume = "0";
					}
					quotationsDomain.setDealVolume(dealVolume);
					dealValue = sbcQuoteList[7].trim();//交易额
					if ( !CommonUtil.isStringDigit(dealValue)){
						dealValue = "0";
					}
					quotationsDomain.setDealValue(dealValue);

					sbcVolume = quotesRedisDao.getSbcVolume(querySbcId);
					if (CommonUtil.isStringDigit(quotationsDomain.getDealVolume()) && CommonUtil.isStringDigit(sbcVolume) && !CommonUtil.isStringZero(sbcVolume)){
						double turnoverRateD = (Double.parseDouble(quotationsDomain.getDealVolume())/Double.parseDouble(sbcVolume) ) * 100;
						turnoverRate = CommonUtil.getFormatDouble(turnoverRateD, 4);
					}
					quotationsDomain.setTurnoverRate(turnoverRate);  //换手率
					if (quotationsDomain.getSbcName().equals("综合指数")){
						marketValue = quotesRedisDao.getMarketValue(querySbcId);
					} else {
						if (CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice()) && CommonUtil.isStringDigit(sbcVolume)){
							double marketValueD = Double.parseDouble(quotationsDomain.getCurrentPrice())*Double.parseDouble(sbcVolume);
							marketValue = CommonUtil.getFormatDouble(marketValueD, 2);
						}
					}
					
					quotationsDomain.setMarketValue(marketValue); //市值
					highestPrice = sbcQuoteList[8].trim();
					if (!CommonUtil.isStringDigit(highestPrice) || CommonUtil.isStringZero(highestPrice)){
						highestPrice = quotationsDomain.getYesterClPrice();
					}
					quotationsDomain.setHighestPrice(highestPrice); //最高价
					lowestPrice = sbcQuoteList[9].trim();
					if (!CommonUtil.isStringDigit(lowestPrice)  || CommonUtil.isStringZero(lowestPrice)){
						lowestPrice = quotationsDomain.getYesterClPrice();
					}
					quotationsDomain.setLowestPrice(lowestPrice);	  	//最低价
					if (quotationsDomain.getSbcName().contains("指数")){ //均价
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
					quotesRedisDao.addQuoteDomain(quotationsDomain);							
					//String content = i +" -->: " +quotationsDomain.toString()+ "\n" +"============================ \n" ;
					//CommonUtil.toLog(content);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
		}		
		quotationsDomain = null;
	}
	*/

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		nanfang002000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年9月30日 下午3:50:08
	 *--------------------------------------------------------------------------------------*/
	private void nanfang002000Fetcher( QuotesRedisDao quotesRedisDao){
		String url = "http://121.40.125.254:18080/tradeweb/refreshHQ";
		Integer sbcId  = 0;
		String exId= CommonVariables.EXCHANGE_NANFANG;
		host = "121.40.125.254";
		int count = 0;
		String averagePrice  = "-";
		String openingPrice = "";
		String currentPrice = "";
		String highestPrice = "";
		String lowestPrice = "";
		String sbcVolume = "0";
		String turnoverRate = "0";
		String marketValue = "0";	
		String riseScope = "0";
		double riseValueD = 0;
		String dealValue = "0";
		String dealVolume = "0";
		String yesterPrice = "";	
		JSONArray jsonArray = null;
		QuotationsDomain quotationsDomain=new QuotationsDomain();
		Document doc = fetchRealTimedoc(url);
		try {
			String jsonStr = doc.select("body").text().toString();
			JSONObject qu = JSONObject.fromObject(jsonStr);
			jsonArray = JSONArray.fromObject(qu.get("tables"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
		}

		 for (int i = 0; i < jsonArray.size(); i++) { 	
			JSONObject obj = JSONObject.fromObject(jsonArray.get(i));		 
			quotationsDomain.init();
			quotationsDomain.setRecordTime(CommonUtil.getNowTimeStamp());//抓取时间
			try {
				quotationsDomain.setSbcCode(obj.get("c").toString().trim());//品种编码
				String querySbcId = quotesRedisDao.getSbcId(exId, quotationsDomain.getSbcCode());
				if (querySbcId==null){
					continue;
				}
				
				sbcId = Integer.parseInt(querySbcId);//品种ID
				quotationsDomain.setExId(exId);//品种交易所ID
				quotationsDomain.setSbcId(sbcId);
				quotationsDomain.setSbcName(obj.get("fn").toString());	//品种名称
				
				yesterPrice = obj.get("ybp").toString().trim();
				if (!CommonUtil.isStringDigit(yesterPrice)  || CommonUtil.isStringZero(yesterPrice)){
					yesterPrice = quotesRedisDao.getDayClosePrice(querySbcId);
				}
				quotationsDomain.setYesterClPrice(yesterPrice);//昨日收盘价
				
				openingPrice = obj.get("op").toString().trim();
				if (!CommonUtil.isStringDigit(openingPrice)  || CommonUtil.isStringZero(openingPrice)){
					openingPrice = quotationsDomain.getYesterClPrice();
				}
				quotationsDomain.setOpeningPrice(openingPrice);
				
				currentPrice = obj.get("cp").toString().trim();
				if (!CommonUtil.isStringDigit(currentPrice) || CommonUtil.isStringZero(currentPrice)){
					currentPrice = quotationsDomain.getYesterClPrice();
				}
				quotationsDomain.setCurrentPrice(currentPrice);	
	
				riseScope = obj.get("cg").toString().trim(); //涨幅
				if (!CommonUtil.isStringDigit(riseScope)){
					riseScope = "0";
				}
				quotationsDomain.setRiseScope(riseScope);
				
				if (CommonUtil.isStringDigit(quotationsDomain.getYesterClPrice()) && CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice())){
					riseValueD = Double.parseDouble(quotationsDomain.getCurrentPrice()) - Double.parseDouble(quotationsDomain.getYesterClPrice());
				} 
				quotationsDomain.setRiseValue(CommonUtil.getFormatDouble(riseValueD, 2));	//涨跌

				dealVolume = obj.get("ta").toString().trim(); //交易量
				if ( !CommonUtil.isStringDigit(dealVolume)){
					dealVolume = "0";
				}
				quotationsDomain.setDealVolume(dealVolume);
				
				dealValue = obj.get("tm").toString().trim();//交易额
				if ( !CommonUtil.isStringDigit(dealValue)){
					dealValue = "0";
				}
				quotationsDomain.setDealValue(dealValue);
					
				sbcVolume = quotesRedisDao.getSbcVolume(querySbcId);
				if (CommonUtil.isStringDigit(quotationsDomain.getDealVolume()) && CommonUtil.isStringDigit(sbcVolume) && !CommonUtil.isStringZero(sbcVolume)){
					double turnoverRateD = (Double.parseDouble(quotationsDomain.getDealVolume())/Double.parseDouble(sbcVolume) ) * 100;
					turnoverRate = CommonUtil.getFormatDouble(turnoverRateD, 4);
				}
				quotationsDomain.setTurnoverRate(turnoverRate);  //换手率
				if (quotationsDomain.getSbcName().equals("综合指数")){
					marketValue = quotesRedisDao.getMarketValue(querySbcId);
				} else {
					if (CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice()) && CommonUtil.isStringDigit(sbcVolume)){
						double marketValueD = Double.parseDouble(quotationsDomain.getCurrentPrice())*Double.parseDouble(sbcVolume);
						marketValue = CommonUtil.getFormatDouble(marketValueD, 2);
					}
				}
				quotationsDomain.setMarketValue(marketValue);
				
				highestPrice = obj.get("hp").toString();
				if (!CommonUtil.isStringDigit(highestPrice) || CommonUtil.isStringZero(highestPrice)){
					highestPrice = quotationsDomain.getYesterClPrice();
				}
				quotationsDomain.setHighestPrice(highestPrice);
				lowestPrice =obj.get("lp").toString().trim();
				if (!CommonUtil.isStringDigit(lowestPrice) || CommonUtil.isStringZero(lowestPrice)){
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
			} catch (Exception e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980013, YbkException.DESC980013);
			}			
			quotesRedisDao.addQuoteDomain(quotationsDomain);	
			count++;				
			//String content = count +" -->: " +quotationsDomain.toString()+ "\n" +"============================ \n" ;
			//System.out.println(content);
			//CommonUtil.toLog(content);
		}
		 
		 
		//String content = "Fetch END: "+ CommonUtil.getTimestamp() +"\n" + "-----------------------------------\n";
		//CommonUtil.toLog(content);	
		quotationsDomain = null;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		shanghai007000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年9月30日 下午3:52:26
	 *--------------------------------------------------------------------------------------*/
	private void shanghai007000Fetcher( QuotesRedisDao quotesRedisDao){
		//String url = "http://ta.shscce.com:8080/front/hq/delay_hq.htm";
		String url = "http://ta.shscce.com:8080/front/hq/delay_hq.json";
		Integer sbcId  = 0;
		String exId= CommonVariables.EXCHANGE_SHANGHAI;
		host = "ta.shscce.com";
		int count = 0;
		String averagePrice  = "-";
		String openingPrice = "";
		String currentPrice = "";
		String highestPrice = "";
		String lowestPrice = "";
		String sbcVolume = "0";
		String turnoverRate = "0";
		String marketValue = "0";	
		String riseScope = "0";
		double riseValueD = 0;
		String dealValue = "0";
		String dealVolume = "0";
		String yesterPrice = "";
		QuotationsDomain quotationsDomain=new QuotationsDomain();
		
		Document doc = fetchRealTimedoc(url);
		String tmpHtml = doc.toString().split("indexHtml\":\"")[1].replace("\"}])", "").replaceAll("&lt;\\\\/td&gt;", "").replaceAll("&lt;\\\\/tr&gt;", "").replaceAll("&lt;\\\\/table&gt;", "");
		doc = Jsoup.parse(tmpHtml);
		//System.out.println("doc " + doc.toString());
		Elements trs = doc.select("table").get(1).select("tr");		//正文				
			//System.out.println("in shhalg " + doc.toString());
			for (Element tr : trs) {			
				Elements tds = tr.select("td");
				if (tds != null && ! tds.text().equals("")){
					quotationsDomain.init();
					quotationsDomain.setRecordTime(CommonUtil.getNowTimeStamp());
					quotationsDomain.setSbcCode(tds.eq(1).text().trim());
					String querySbcId = quotesRedisDao.getSbcId(exId, quotationsDomain.getSbcCode());
					if (querySbcId==null){
						//System.out.println( "找不到对应的Id： "+  exId  + quotationsDomain.getSbcCode());
						continue;
					}
					sbcId = Integer.parseInt(querySbcId);
					quotationsDomain.setSbcName(tds.eq(2).text().trim());					
					quotationsDomain.setExId(exId);	
					quotationsDomain.setSbcId(sbcId);
					
					yesterPrice = tds.eq(3).text().trim();
					if (!CommonUtil.isStringDigit(yesterPrice) || CommonUtil.isStringZero(yesterPrice)){
					yesterPrice = quotesRedisDao.getDayClosePrice(querySbcId);
					}
					quotationsDomain.setYesterClPrice(yesterPrice);	//昨日收盘价
					
					openingPrice = tds.eq(4).text().trim();
					if (!CommonUtil.isStringDigit(openingPrice) || CommonUtil.isStringZero(openingPrice)){
						openingPrice = quotationsDomain.getYesterClPrice();
					}
					quotationsDomain.setOpeningPrice(openingPrice);
					currentPrice = tds.eq(5).text().trim();
					if (!CommonUtil.isStringDigit(currentPrice) || CommonUtil.isStringZero(currentPrice)){
						currentPrice = quotationsDomain.getYesterClPrice();
					}
					quotationsDomain.setCurrentPrice(currentPrice);									
					if (CommonUtil.isStringDigit(quotationsDomain.getYesterClPrice()) && CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice())){
						riseValueD = Double.parseDouble(quotationsDomain.getCurrentPrice()) - Double.parseDouble(quotationsDomain.getYesterClPrice());
					} 
					riseScope = tds.eq(6).text().replace("%", "").trim(); //涨幅
					if (!CommonUtil.isStringDigit(riseScope)){
						riseScope = "0";
					} 
					quotationsDomain.setRiseScope(riseScope);
					quotationsDomain.setRiseValue(CommonUtil.getFormatDouble(riseValueD, 2));	//涨跌

					dealVolume = tds.eq(7).text().trim(); //交易量
					if ( !CommonUtil.isStringDigit(dealVolume)){
						dealVolume = "0";
					}
					quotationsDomain.setDealVolume(dealVolume);
		
					dealValue = tds.eq(8).text().trim();//交易额
					if ( !CommonUtil.isStringDigit(dealValue)){
						dealValue = "0";
					}
					quotationsDomain.setDealValue(dealValue);

					
					sbcVolume = quotesRedisDao.getSbcVolume(querySbcId);
					if (CommonUtil.isStringDigit(quotationsDomain.getDealVolume()) && CommonUtil.isStringDigit(sbcVolume) && !CommonUtil.isStringZero(sbcVolume)){
						double turnoverRateD = (Double.parseDouble(quotationsDomain.getDealVolume())/Double.parseDouble(sbcVolume) ) * 100;
						turnoverRate = CommonUtil.getFormatDouble(turnoverRateD, 4);
					}
					quotationsDomain.setTurnoverRate(turnoverRate);  //换手率
					if (quotationsDomain.getSbcName().equals("综合指数")){
						marketValue = quotesRedisDao.getMarketValue(querySbcId);
						
					} else {
						if (CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice()) && CommonUtil.isStringDigit(sbcVolume)){
							double marketValueD = Double.parseDouble(quotationsDomain.getCurrentPrice())*Double.parseDouble(sbcVolume);
							marketValue = CommonUtil.getFormatDouble(marketValueD, 2);
						}
					}
					quotationsDomain.setMarketValue(marketValue);
					
					highestPrice = tds.eq(9).text().trim();
					if (!CommonUtil.isStringDigit(highestPrice) || CommonUtil.isStringZero(highestPrice)){
						highestPrice = quotationsDomain.getYesterClPrice();
					}
					quotationsDomain.setHighestPrice(highestPrice);
					lowestPrice = tds.eq(10).text().trim();
					if (!CommonUtil.isStringDigit(lowestPrice) || CommonUtil.isStringZero(lowestPrice)){
						lowestPrice = quotationsDomain.getYesterClPrice();
					}
					quotationsDomain.setLowestPrice(tds.eq(10).text().trim());		
					if (CommonUtil.isStringDigit(quotationsDomain.getDealValue()) && CommonUtil.isStringDigit(quotationsDomain.getDealVolume())){
						if (quotationsDomain.getDealVolume().equals("0")) {
							averagePrice = quotationsDomain.getCurrentPrice();
						} else {
							Double averagePriceD = Double.parseDouble(quotationsDomain.getDealValue()) / Double.parseDouble(quotationsDomain.getDealVolume());
							averagePrice = CommonUtil.getFormatDouble(averagePriceD, 2);
						}	
					} 		
					quotationsDomain.setAveragePrice(averagePrice);
					count++;			
					//String content = count +" -->: " +quotationsDomain.toString()+ "\n" +"============================ \n" ;
					//System.out.println(content);
					//CommonUtil.toLog(content);
				/*	if (! CommonUtil.isStringZero(quotationsDomain.getCurrentPrice())){  //如果数据是0，则表示刚开盘？
						continue;	//如此任性，是因为，上海交易所，一开始竟然是0，所以必须跳过。
					}*/
					
					
					quotesRedisDao.addQuoteDomain(quotationsDomain);
				}
			}
		shanghai007000Index(quotationsDomain,  quotesRedisDao);
		quotationsDomain = null;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		shanghai007000Index
	 * Author:		1.0 created by lijiaxuan at 2015年10月11日 下午9:52:25
	 *--------------------------------------------------------------------------------------*/
	void shanghai007000Index(QuotationsDomain quotationsDomain, QuotesRedisDao quotesRedisDao){
		//String url = "http://www.shscce.com/html/shscce/index.shtml";
		String url = "http://ta.shscce.com:8080/front/hq/delay_hq.json?stockPreCodes=&indexCodes=600001";
		String averagePrice  = "-";
		String openingPrice = "";
		String currentPrice = "";
		String highestPrice = "";
		String lowestPrice = "";
		Integer sbcId  = 0;
		String exId= CommonVariables.EXCHANGE_SHANGHAI;
		host = "ta.shscce.com";
		Document doc = fetchRealTimedoc(url);
		//Element tr = doc.select("tbody th").first();		//正文			
		//Elements tds = tr.select("td");	
	
		String tmpHtml = doc.toString().split("indexHtml\":\"")[1].replace("\"}])", "").replaceAll("&lt;\\\\/th&gt;", "").replaceAll("&lt;\\\\/th&gt;", "").replaceAll("&lt;\\\\/table&gt;", "");
		doc = Jsoup.parse(tmpHtml);
		//System.out.println("doc " + doc.toString());
		Elements ths = doc.select("table").get(0).select("th");		//正文					
		
		if (ths != null ){
			quotationsDomain.init();		
			quotationsDomain.setRecordTime(CommonUtil.getNowTimeStamp());
			//quotationsDomain.setSbcCode(tds.eq(1).text().trim());
			quotationsDomain.setSbcCode("600001");
			String querySbcId = quotesRedisDao.getSbcId(exId, quotationsDomain.getSbcCode());
			if (querySbcId==null){
				//System.out.println( "找不到对应的Id： "+  exId  + quotationsDomain.getSbcCode());
				return;
			}
			sbcId = Integer.parseInt(querySbcId);
			quotationsDomain.setSbcName("综合指数");		
			quotationsDomain.setExId(exId);	
			quotationsDomain.setSbcId(sbcId);
			quotationsDomain.setYesterClPrice(quotesRedisDao.getDayClosePrice(querySbcId));
			//openingPrice = tds.eq(4).text().replace("<\\/th>", "").trim();
			//if (CommonUtil.isStringZero(openingPrice)){
			//	openingPrice = quotationsDomain.getYesterClPrice();
			//}
			quotationsDomain.setOpeningPrice(quotationsDomain.getYesterClPrice());
			currentPrice = ths.eq(1).text().replace("<\\/th>", "").trim();
			if (!CommonUtil.isStringDigit(currentPrice) || CommonUtil.isStringZero(currentPrice) ){
				currentPrice = quotationsDomain.getYesterClPrice();
			}
			quotationsDomain.setCurrentPrice(currentPrice);	
			quotationsDomain.setRiseScope(ths.eq(3).text().replace("<\\/th>", "").replace("%", "").trim());
			quotationsDomain.setDealVolume(CommonUtil.getNumFromString(ths.eq(5).text().trim(), ""));
			String dealValue = ths.eq(7).text().replace("<\\/th>", "").trim();
			if (dealValue.contains("万")){
				dealValue = CommonUtil.getNumFromString(dealValue, null).trim();
				double dealValueD = Double.parseDouble(dealValue) * 10000;
				dealValue = CommonUtil.getFormatDouble(dealValueD, 2);
			}
			
			quotationsDomain.setDealValue(dealValue);		
			//highestPrice = tds.eq(9).text().replace("<\\/th>", "").trim();
			//if (CommonUtil.isStringZero(highestPrice)){
			//	highestPrice = quotationsDomain.getYesterClPrice();
			//}
			String marketValue = "0";
			if (quotationsDomain.getSbcName().equals("综合指数")){
				marketValue = quotesRedisDao.getMarketValue(querySbcId);
			} 			
			quotationsDomain.setMarketValue(marketValue);
		
			//quotationsDomain.setHighestPrice(highestPrice);
			//lowestPrice = tds.eq(10).text().split("<\\\\/th>")[0].trim();
			//if (CommonUtil.isStringZero(lowestPrice)){
			//	lowestPrice = quotationsDomain.getYesterClPrice();
			//}
			quotationsDomain.setHighestPrice(quotationsDomain.getYesterClPrice());	
			quotationsDomain.setLowestPrice(quotationsDomain.getYesterClPrice());	
			double riseValueD  = 0;
			double riseScopeD  = 0;
			
			if (CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice()) && CommonUtil.isStringDigit(quotationsDomain.getYesterClPrice())){
				riseValueD = Double.parseDouble(quotationsDomain.getCurrentPrice()) - Double.parseDouble(quotationsDomain.getYesterClPrice());
				riseScopeD = (riseValueD / Double.parseDouble(quotationsDomain.getYesterClPrice())) * 100;
			}
			quotationsDomain.setRiseValue(CommonUtil.getFormatDouble(riseValueD, 2));
			quotationsDomain.setRiseScope(CommonUtil.getFormatDouble(riseScopeD, 2));
			//if (quotationsDomain.getSbcName().contains("指数")){
			//	averagePrice = "";
			///} else if (CommonUtil.isStringDigit(quotationsDomain.getDealValue()) && CommonUtil.isStringDigit(quotationsDomain.getDealVolume())){
			//	if (quotationsDomain.getDealVolume().equals("0")) {
			//		averagePrice = quotationsDomain.getCurrentPrice();
			//	} else {
			//		Double averagePriceD = Double.parseDouble(quotationsDomain.getDealValue()) / Double.parseDouble(quotationsDomain.getDealVolume());
			//		averagePrice = CommonUtil.getFormatDouble(averagePriceD, 2);
			//	}	
			//} 		
			
			averagePrice = "";
			//quotationsDomain.setAveragePrice(averagePrice);
			//String content = " -->: " +quotationsDomain.toString()+ "\n" +"============================ \n" ;
			//System.out.println(content);
			//CommonUtil.toLog(content);
			quotesRedisDao.addQuoteDomain(quotationsDomain);
		}

	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		commonFetcher
	 * Author:		1.0 created by lijiaxuan at 2015年9月30日 下午3:52:26
	 *--------------------------------------------------------------------------------------*/
	private void commonFetcher( String host, String url, String exId, QuotesRedisDao quotesRedisDao){
		//url = "http://180.97.2.111:15910/tradeweb/hq/getHqV_lbData.jsp";
		Integer sbcId  = 0;
		//String exId= CommonVariables.EXCHANGE_ZHONGNAN;
		//host = "180.97.2.111";
		int count = 0;
		JSONArray jsonArray = null;
		QuotationsDomain quotationsDomain=new QuotationsDomain();
		Document doc = fetchRealTimedoc(url);
		String sbcVolume = "0";
		String turnoverRate = "0";
		String marketValue = "0";	
		try {
			String jsonStr = doc.select("body").text().toString();
			JSONObject qu = JSONObject.fromObject(jsonStr);
			jsonArray = JSONArray.fromObject(qu.get("tables"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
		}

		 for (int i = 0; i < jsonArray.size(); i++) { 	
			JSONObject obj = JSONObject.fromObject(jsonArray.get(i));		 
			quotationsDomain.init();
			try {
				quotationsDomain.setRecordTime(CommonUtil.getNowTimeStamp());
				jsonToQuotationsDomain( quotationsDomain,  obj);	
				String querySbcId = quotesRedisDao.getSbcId(exId, quotationsDomain.getSbcCode());
	
				
				sbcVolume = quotesRedisDao.getSbcVolume(querySbcId);
				
				if (CommonUtil.isStringDigit(quotationsDomain.getDealVolume()) && CommonUtil.isStringDigit(sbcVolume) && !CommonUtil.isStringZero(sbcVolume)){
					double turnoverRateD = (Double.parseDouble(quotationsDomain.getDealVolume())/Double.parseDouble(sbcVolume) ) * 100;
					turnoverRate = CommonUtil.getFormatDouble(turnoverRateD, 4);
				}
				quotationsDomain.setTurnoverRate(turnoverRate);
				if (obj.get("fullname").toString().equals("综合指数") || obj.get("fullname").toString().equals("邮币综合指数")){
					marketValue = quotesRedisDao.getMarketValue(querySbcId);
				} else {
					if (CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice()) && CommonUtil.isStringDigit(sbcVolume)){
						double marketValueD = Double.parseDouble(quotationsDomain.getCurrentPrice())*Double.parseDouble(sbcVolume);
						marketValue = CommonUtil.getFormatDouble(marketValueD, 2);
					}
				}	
				quotationsDomain.setMarketValue(marketValue);
				
				if (querySbcId==null){
					continue;
				}
				sbcId = Integer.parseInt(querySbcId);
				quotationsDomain.setSbcId(sbcId);
				quotationsDomain.setExId(exId);
			} catch (Exception e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980013, YbkException.DESC980013);
			}			
			quotesRedisDao.addQuoteDomain(quotationsDomain);	
			count++;				
			//String content = count +" -->: " +quotationsDomain.toString()+ "\n" +"============================ \n" ;
			//System.out.println(content);
			//CommonUtil.toLog(content);
		}
		//String content = "Fetch END: "+ CommonUtil.getTimestamp() +"\n" + "-----------------------------------\n";
		//CommonUtil.toLog(content);	
		quotationsDomain = null;
	}
	
	public void jsonToQuotationsDomain(QuotationsDomain quotationsDomain, JSONObject obj){
		String averagePrice  = "-";
		String openingPrice = "";
		String currentPrice = "";
		String highestPrice = "";
		String lowestPrice = "";

		String riseScope = "0";
		double riseValueD = 0;
		String dealValue = "0";
		String dealVolume = "0";
		String yesterPrice = "";
		
		quotationsDomain.setSbcCode(obj.get("code").toString().trim());
		quotationsDomain.setSbcName(obj.get("fullname").toString());
		
		yesterPrice = obj.get("YesterBalancePrice").toString();

	//	if (!CommonUtil.isStringDigit(yesterPrice)){
	//	yesterPrice = quotesRedisDao.getDayClosePrice(querySbcId);
	//	}

		quotationsDomain.setYesterClPrice(yesterPrice);	//昨日收盘价
		
		openingPrice = obj.get("OpenPrice").toString().trim();
		if (!CommonUtil.isStringDigit(openingPrice) || CommonUtil.isStringZero(openingPrice)){
			openingPrice = quotationsDomain.getYesterClPrice();
		}
		quotationsDomain.setOpeningPrice(openingPrice);
		currentPrice = obj.get("CurPrice").toString().trim();
		if (!CommonUtil.isStringDigit(currentPrice) || CommonUtil.isStringZero(currentPrice)){
			currentPrice = quotationsDomain.getYesterClPrice();
		}
		quotationsDomain.setCurrentPrice(currentPrice);	
	
		riseScope = obj.get("CurrentGains").toString(); //涨幅

		if (!CommonUtil.isStringDigit(riseScope)){
			riseScope = "0";
		}
		quotationsDomain.setRiseScope(riseScope);
		if (CommonUtil.isStringDigit(quotationsDomain.getYesterClPrice()) && CommonUtil.isStringDigit(quotationsDomain.getCurrentPrice())){
			riseValueD = Double.parseDouble(quotationsDomain.getCurrentPrice()) - Double.parseDouble(quotationsDomain.getYesterClPrice());
		} 
		quotationsDomain.setRiseValue(CommonUtil.getFormatDouble(riseValueD, 2));	//涨跌

		dealVolume = obj.get("TotalAmount").toString(); //交易量
		if ( !CommonUtil.isStringDigit(dealVolume)){
		dealVolume = "0";
		}
		quotationsDomain.setDealVolume(dealVolume);
		dealValue = obj.get("TotalMoney").toString();//交易额
		if ( !CommonUtil.isStringDigit(dealValue)){
			dealValue = "0";
		}
		quotationsDomain.setDealValue(dealValue);
		
		highestPrice = obj.get("HighPrice").toString().trim();
		if (!CommonUtil.isStringDigit(highestPrice) || CommonUtil.isStringZero(highestPrice)){
			highestPrice = quotationsDomain.getYesterClPrice();
		}
		quotationsDomain.setHighestPrice(highestPrice);
		lowestPrice = obj.get("LowPrice").toString().trim();
		if (!CommonUtil.isStringDigit(lowestPrice) || CommonUtil.isStringZero(lowestPrice)){
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
			case CommonVariables.EXCHANGE_NANFANG: {
				nanfang002000Fetcher(this.quotesRedisDao);		
				break;
			}
			case CommonVariables.EXCHANGE_FULITE: {
				fulite004000Fetcher(this.quotesRedisDao);		
				break;
			}
			case CommonVariables.EXCHANGE_SHANGHAI: {
				shanghai007000Fetcher(this.quotesRedisDao);	
				break;
			}
			default: {				
				commonFetcher( CommonUtil.getHost(url), url, exId, this.quotesRedisDao);
				break;
			}				
		}

    }
}
