package com.eyoubika.spider.application;

import java.io.IOException;
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
import com.eyoubika.sbc.dao.SbcIdMapDao;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.sbc.domain.SbcIdMapDomain;
import com.eyoubika.spider.web.VO.FetchVO;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
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
 * Description:	抓取epianhong数据的小蜘蛛哈哈
 * Class:		EpianhongSpiderAL
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-8-06 15:26:18
 *==========================================================================================*/
public class ZongyihuiSpiderAL extends BaseSpider {
	private QuotesRedisDao quotesRedisDao;
	private SbcBasicDao sbcBasicDao;
	/*private String uid = "31acf78bc5afc2e8f5e79be35e81aca4";
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}*/

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

	public void initSHQuotRedis( ){
		String url = "http://wx.shscce.com/quotation/marketQuotation";
		Integer sbcId  = 0;
		String exId= CommonVariables.EXCHANGE_SHANGHAI;
		host = "wx.shscce.com";
		init(host);
		SbcBasicDomain sbcBasicDomain= new SbcBasicDomain();
			Document doc = fetchRealTimedoc(url);
			Elements trs = doc.select("div.tableContainer tr");		//正文				
			for (Element tr : trs) {					
				Elements tds = tr.select("td.aItem");
				if (tds != null && ! tds.text().equals("")){
					String[] proName =  tds.eq(0).text().split(" ");
					sbcBasicDomain.init();
					sbcBasicDomain.setExId(exId);
					sbcBasicDomain.setSbcCode(proName[1].trim());
					sbcBasicDomain.setSbcName(proName[0].trim());
					//如果，数据库里面存在，则不插入。
					if (sbcBasicDao.isExistByDomain(sbcBasicDomain)){
						continue;
					}
					sbcBasicDomain.setInputDate(CommonUtil.getNowDate());
					sbcBasicDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
					if (sbcBasicDomain.getSbcName().contains("指数")){
						sbcBasicDomain.setSbcType(CommonVariables.SBC_INDEX);	//指数类品种
					} else {
						sbcBasicDomain.setSbcType(CommonVariables.SBC_INITIAL);	//一般初始化品种（未分类）
					}									
					sbcId = sbcBasicDao.addSbcBasic(sbcBasicDomain);
					sbcBasicDomain.setSbcId(sbcId);
					quotesRedisDao.initQuoteRedis(sbcBasicDomain);	
					System.out.println("sbcBasicDomain " + sbcBasicDomain.toString());				
				}
			}
		sbcBasicDomain = null;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		initQuotRedis
	 * Author:		1.0 created by lijiaxuan at 2015年8月19日 下午3:57:56
	 *--------------------------------------------------------------------------------------*/
	public void initQuotRedis( ){
		host = "www.zongyihui.cn";
		Integer sbcId  = 0;
		init(host);
		Map<String, String> exMap = new HashMap<String, String>();
		exMap.put("16", CommonVariables.EXCHANGE_NANJING);//南京文交所
		exMap.put("15", CommonVariables.EXCHANGE_NANFANG); //南方文交所
		exMap.put("19", CommonVariables.EXCHANGE_FULITE);
		exMap.put("42", CommonVariables.EXCHANGE_ZHONGNAN);
		
		exMap.put("36", CommonVariables.EXCHANGE_JINMAJIA);
		exMap.put("91", CommonVariables.EXCHANGE_JIANGSU);
		//exMap.put("16", CommonVariables.EXCHANGE_SHANGHAI);
		exMap.put("30", CommonVariables.EXCHANGE_HUAXIA);
		exMap.put("41", CommonVariables.EXCHANGE_HUAZHONG);
		exMap.put("23", CommonVariables.EXCHANGE_YIJIAO);
		/**/
		SbcBasicDomain sbcBasicDomain= new SbcBasicDomain();
		for (Map.Entry<String, String> entry : exMap.entrySet()) {
			String marketId= entry.getKey();
			String exId = entry.getValue();			
			int page = getMaxPageIndex(marketId);
			for (int i = 1; i <= page; i++){
				//System.out.println("page: " + i);
				String url = "http://" + host + "/web/info/hqcenter1.jsp?kind=selectmarket&market="+marketId+"&hqwb=&industry=0&pageIndex=" +  i;
				Document doc = fetchRealTimedoc(url);
				Elements trs = doc.select("div.bor tr");		//正文				
				for (Element tr : trs) {					
					Elements tds = tr.select("td.tb_bt11, td.tb_bt111");
					if (tds != null && ! tds.text().equals("")){	
						sbcBasicDomain.init();
						sbcBasicDomain.setExId(exId);
						sbcBasicDomain.setSbcCode(tds.eq(0).text());
						sbcBasicDomain.setSbcName(tds.eq(1).text());
						//如果，数据库里面存在，则不插入。
						if (sbcBasicDao.isExistByDomain(sbcBasicDomain)){
							continue;
						}
						
						sbcBasicDomain.setInputDate(CommonUtil.getNowDate());
						sbcBasicDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
						if (sbcBasicDomain.getSbcName().contains("指数")){
							sbcBasicDomain.setSbcType(CommonVariables.SBC_INDEX);	//指数类品种
						} else {
							sbcBasicDomain.setSbcType(CommonVariables.SBC_INITIAL);	//一般初始化品种（未分类）
						}									
						sbcId = sbcBasicDao.addSbcBasic(sbcBasicDomain);
						sbcBasicDomain.setSbcId(sbcId);
						quotesRedisDao.initQuoteRedis(sbcBasicDomain);			
					}
				}
			}
		}
		sleep(1000);
		sbcBasicDomain = null;
		//初始化上海文交所数据库
		initSHQuotRedis();
	} 
	
	public void fetchQuotationsMore(){
		host = "www.zongyihui.cn";
		//Integer sbcId  = 0;
		init(host);
		int maxMarket = 9;
		int produceTaskSleepTime = 1000; //1s  
		int waitSleepTime = 5000; //5s  
		int maxPoolSize = 100; 		// 线程池维护线程的最大数量
		int minPoolSize = 50;	 	// 线程池维护线程的最少数量
		long keepAliveTime = 3;		// 线程池维护线程所允许的空闲时间
		Map<String, String> exMap = new HashMap<String, String>();
		exMap.put("16", CommonVariables.EXCHANGE_NANJING);//南京文交所
		exMap.put("15", CommonVariables.EXCHANGE_NANFANG); //南方文交所
		exMap.put("19", CommonVariables.EXCHANGE_FULITE);
		exMap.put("42", CommonVariables.EXCHANGE_ZHONGNAN);
		exMap.put("19", CommonVariables.EXCHANGE_FULITE);
		exMap.put("36", CommonVariables.EXCHANGE_JINMAJIA);
		exMap.put("91", CommonVariables.EXCHANGE_JIANGSU);
		exMap.put("SH", CommonVariables.EXCHANGE_SHANGHAI);
		exMap.put("30", CommonVariables.EXCHANGE_HUAXIA);
		exMap.put("41", CommonVariables.EXCHANGE_HUAZHONG);
		exMap.put("23", CommonVariables.EXCHANGE_YIJIAO);
	
		List<String> fetchList = new ArrayList<String>();	
		String[] marketIds = {"16", "15", "42", "19","36","91", "30", "41", "23"};	
		for (int i = 0; i < marketIds.length; i++){
			int page = getMoreMaxPageIndex(marketIds[i]);
			if (page == 0){
				throw new YbkException(YbkException.CODE980011, YbkException.CODE980011 + ": " + marketIds[i]);
			}
			for (int j = 0 ; j < page; j++){			
				fetchList.add(marketIds[i] + "," + j);	//构造出抓取列表：<交易市场ID， 当前页>
			}		
		}
		fetchList.add("SH,1");	//补上上海交易所
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(minPoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());
		int index = 0;
		while (true)//
		 {
			if (CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_AM, CommonVariables.TRADEHOUR_END_AM) 
				|| CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_PM, CommonVariables.TRADEHOUR_END_PM)){
		            try
		            {
		            	index++;
		                // 产生一个任务，并将其加入到线程池
		                //String task = "task@ " + index;
		                //System.out.println("put " + task);
		                int fetchListSize = fetchList.size();	//总共87个页面。
		                //System.out.println("fetchListSize " +fetchListSize);
		                int id = index%fetchListSize;		  
		                String marketId= fetchList.get(id).split(",")[0];
		                String pageId= fetchList.get(id).split(",")[1];
		                threadPool.execute(new QuotesMoreFetcher(exMap.get(marketId), marketId, pageId,  quotesRedisDao, sbcBasicDao));

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
		
		
		/*for (Map.Entry<String, String> entry : exMap.entrySet()) {
			String marketId= entry.getKey();
			String exId = entry.getValue();
			Thread thread=new Thread(new QuotesMoreFetcher(exId, marketId, quotesRedisDao, sbcBasicDao));
			thread.start();	
		}*/
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
		int waitSleepTime = 5000; //5s  
		int maxPoolSize = 100; 		// 线程池维护线程的最大数量
		int minPoolSize = 60;	 	// 线程池维护线程的最少数量
		long keepAliveTime = 3;		// 线程池维护线程所允许的空闲时间
		Map<String, String> exMap = new HashMap<String, String>();
		exMap.put("16", CommonVariables.EXCHANGE_NANJING);//南京文交所
		exMap.put("15", CommonVariables.EXCHANGE_NANFANG); //南方文交所
		exMap.put("42", CommonVariables.EXCHANGE_ZHONGNAN);
		exMap.put("19", CommonVariables.EXCHANGE_FULITE);
		exMap.put("36", CommonVariables.EXCHANGE_JINMAJIA);
		exMap.put("91", CommonVariables.EXCHANGE_JIANGSU);
		exMap.put("SH", CommonVariables.EXCHANGE_SHANGHAI);
		exMap.put("30", CommonVariables.EXCHANGE_HUAXIA);
		exMap.put("41", CommonVariables.EXCHANGE_HUAZHONG);
		exMap.put("23", CommonVariables.EXCHANGE_YIJIAO);
		List<String> fetchList = new ArrayList<String>();	
		String[] marketIds = {"16", "15", "42", "19","36","91", "30", "41", "23"};	
		for (int i = 0; i < marketIds.length; i++){
			int page = getMaxPageIndex(marketIds[i]);
			if (page == 0){
				throw new YbkException(YbkException.CODE980011, YbkException.CODE980011 + ": " + marketIds[i]);
			}
			for (int j = 0 ; j < page; j++){			
				fetchList.add(marketIds[i] + "," + j);	//构造出抓取列表：<交易市场ID， 当前页>
			}		
		}
		fetchList.add("SH,1");	//补上上海交易所
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
		while (true)//
		 {
			if (CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_AM, CommonVariables.TRADEHOUR_END_AM) 
				|| CommonUtil.isInTime(CommonVariables.TRADEHOUR_BEG_PM, CommonVariables.TRADEHOUR_END_PM)){
		            try
		            {
		            	index++;
		                // 产生一个任务，并将其加入到线程池
		                //String task = "task@ " + index;
		                //System.out.println("put " + task);
		                int fetchListSize = fetchList.size();	//总共87个页面。
		                //System.out.println("fetchListSize " +fetchListSize);
		                int id = index%fetchListSize;		  
		                String marketId= fetchList.get(id).split(",")[0];
		                String pageId= fetchList.get(id).split(",")[1];
		                threadPool.execute(new Fetcher(exMap.get(marketId), marketId, pageId,  quotesRedisDao));

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
			/*  for (Map.Entry<String, String> entry : exMap.entrySet()) {
					String marketId= entry.getKey();
					String exId = entry.getValue();	  
			Thread thread=new Thread(new Fetcher(exId, marketId,  quotesRedisDao));
			thread.start();		
		}	*/	
	} 

	public int getMaxPageIndex(String marketId){
		int page = 0;
		String url = "http://" + host + "/web/info/hqcenter1.jsp?kind=selectmarket&market="+marketId+"&hqwb=&industry=0";
		Document doc = fetchDocument(url);
		Elements tds = doc.select("td[height=30] td");		//正文
		if (tds !=null){
			page = Integer.parseInt(tds.last().text().split("/")[1]);
		}
		return page;
	}
	public int getMoreMaxPageIndex(String marketId){
		int page = 0;
		//String url = "http://" + host + "/web/info/bika/news/ybkhq.jsp?kind=kind&market=0&hqwb=&industry=0";
		String url = "http://" + host + "/web/info/bika/news/ybkhq.jsp?kind=selectmarket&market="+marketId+"&hqwb=&industry=0";
		Document doc = fetchDocument(url);
		Elements tds = doc.select("td[height=30] td");		//正文
		
		if (tds !=null){
			page = Integer.parseInt(tds.last().text().split("/")[1]);
		}
		return page;
	}
	
	/*//废弃！！
	public void fetchSbcs(){
		host = "http://app.epianhong.com";
		init(host);
		String exList[] = {"3","4","5","6"};//,"3","4","5","6"
		int size = exList.length;
		for (int j = 0; j < size; j++){
			String dzpid = exList[j];
			String url = host + "/getAllGoodsMarketInfoByDzp?uid="+uid+"&phonetype=1&dzpid=" + dzpid;
			Document doc = fetchDocument(url, "phone");
			String jsonStr = doc.select("body").text().toString();
			System.out.println("url" + url);
			String exId = sbcMapRedisDao.findExId(dzpid) ;
					JSONArray jsonArray = JSONArray.fromObject(JSONObject.fromObject(jsonStr).get("goods"));
					 for (int i = 0; i < jsonArray.size(); i++) { 	
						 SbcBasicDomain  sbcBasicDomain = new SbcBasicDomain();
						 JSONObject obj = JSONObject.fromObject(jsonArray.get(i));
						
						 sbcBasicDomain.setSbcName(obj.get("name").toString());
						// Integer sbcId = null;
						 SbcBasicDomain  tmpDomain = sbcBasicDao.findSbcBasicByDomain(sbcBasicDomain);//.findSbcIdBySbcCode(obj.get("code").toString());						 
						 sbcBasicDomain.setSbcCode(obj.get("code").toString());						
						 sbcBasicDomain.setIssuePrice(obj.get("publishprice").toString());
						 sbcBasicDomain.setIssueTime(obj.get("publishtime").toString());						 					 
						 String type = "";
						 if (obj.get("type").toString().equals("1")){
							 type = CommonVariables.SBC_INITIAL;
						 } else  if (obj.get("type").toString().equals("2")){
							 type = CommonVariables.SBC_INDEX;
						 } 
						 sbcBasicDomain.setSbcType(type);
						 
						if (tmpDomain == null) {	
							sbcBasicDomain.setInputDate(CommonUtil.getNowDate());
							sbcBasicDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
							sbcBasicDomain.setExId(exId);	 
							sbcBasicDao.addSbcBasic(sbcBasicDomain);
						} else {
							sbcBasicDao.modifySbcBasic(sbcBasicDomain);
						}
					}
		}
		
	}*/
}


/*==========================================================================================*
 * Description:	多线程之抓取行情子线程
 * Class:		Fetcher
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年8月19日 下午3:59:38
 *==========================================================================================*/
/*class Fetcher extends BaseSpider implements Runnable{

	private  String exId;
	private  String marketId;
	private  String page;
	private QuotesRedisDao quotesRedisDao;
	public Fetcher(String exId, String marketId, String page, QuotesRedisDao quotesRedisDao){
		host = "www.zongyihui.cn";
		init(host);
		this.exId = exId;
		this.marketId = marketId;
		this.quotesRedisDao= quotesRedisDao;
		this.page = page;
	}
	public int getMaxPageIndex(String marketId){
		int page = 0;
		String url = "http://" + host + "/web/info/hqcenter1.jsp?kind=selectmarket&market="+marketId+"&hqwb=&industry=0";
		Document doc = fetchDocument(url);
		Elements tds = doc.select("td[height=30] td");		//正文
		
		if (tds !=null){
			page = Integer.parseInt(tds.last().text().split("/")[1]);
		}
		return page;
	}

	public void fetchSHQuotations(){
		
		String url = "http://wx.shscce.com/quotation/marketQuotation";
		Integer sbcId  = 0;
		String exId= CommonVariables.EXCHANGE_SHANGHAI;
		host = "wx.shscce.com";
		SbcBasicDomain sbcBasicDomain= new SbcBasicDomain();
		int count = 0;
		QuotationsDomain quotationsDomain=new QuotationsDomain();
			Document doc = fetchRealTimedoc(url);
			Elements trs = doc.select("div.tableContainer tr");		//正文				
			for (Element tr : trs) {					
				Elements tds = tr.select("td");
				if (tds != null && ! tds.text().equals("")){

					String[] proName =  tds.eq(0).text().split(" ");


					quotationsDomain.setSbcCode(proName[1].trim());
					String querySbcId = quotesRedisDao.getSbcId(exId, quotationsDomain.getSbcCode());
					if (querySbcId==null){
						System.out.println( "找不到对应的Id： "+  exId  + quotationsDomain.getSbcCode());
						continue;
					}
					sbcId = Integer.parseInt(querySbcId);
					quotationsDomain.setSbcName(proName[0].trim());
					
					quotationsDomain.setExId(exId);
					quotationsDomain.setCurrentPrice(tds.eq(1).text().trim().replace(",", ""));
					quotationsDomain.setHighestPrice(tds.eq(7).text().trim().replace(",", ""));
					quotationsDomain.setLowestPrice(tds.eq(8).text().trim().replace(",", ""));
					quotationsDomain.setAveragePrice("");
					quotationsDomain.setYesterAvPrice("");
					quotationsDomain.setRiseValue(tds.eq(2).text().trim().replace(",", ""));
					quotationsDomain.setDealVolume(tds.eq(5).text().trim());
					String dealValue = tds.eq(6).text().trim().replace(",", "");				
					if (dealValue.contains("万")){						
						dealValue = dealValue.replace("万", "");
						double dealValueFl  = Double.parseDouble(dealValue) * 10000;
						NumberFormat formatter = NumberFormat.getNumberInstance();
						formatter.setMaximumFractionDigits(18);
						dealValue = formatter.format(dealValueFl);
					}
					
					
					quotationsDomain.setDealValue(dealValue);
					quotationsDomain.setPoq("");	
					quotationsDomain.setRiseScope(tds.eq(3).text().trim().replace("%", ""));
					quotationsDomain.setSbcId(sbcId);
					if (CommonUtil.isStringNull(quotesRedisDao.getOpenPrice(querySbcId))) { //一天的第一条则当做开盘价					
						if (! CommonUtil.isStringZero(quotationsDomain.getCurrentPrice())){  //如果数据是0，则表示刚开盘？
							Map<String, String> map = new HashMap<String, String>();
							map.put("openPrice", quotationsDomain.getCurrentPrice());	
							quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcId.toString(), map);
							map = null;
						}		
						
					}
					quotationsDomain.setOpeningPrice(quotesRedisDao.getOpenPrice(querySbcId));					
					quotationsDomain.setYesterClPrice(tds.eq(4).text().trim().replace(",", ""));
					count++;
				
					String content = count +" -->: " +quotationsDomain.toString()+ "\n" +"============================ \n" ;
					CommonUtil.toLog(content);
					if (! CommonUtil.isStringZero(quotationsDomain.getCurrentPrice())){  //如果数据是0，则表示刚开盘？
						continue;	//如此任性，是因为，上海交易所，一开始竟然是0，所以必须跳过。
					}
					quotesRedisDao.addQuoteDomain(quotationsDomain);
				}
			}
		String content = "Fetch END: "+ CommonUtil.getTimestamp() +"\n" + "-----------------------------------\n";
		CommonUtil.toLog(content);
		quotationsDomain = null;
		sbcBasicDomain = null;
	}
	
	public void run()
    {
		if (marketId.equals("SH")){
			fetchSHQuotations();
		} else {
		host = "www.zongyihui.cn";
		Integer sbcId  = 0;
		int count = 0;
		QuotationsDomain quotationsDomain=new QuotationsDomain();
		//int page = getMaxPageIndex(marketId);
		//for (int i = 1; i <= page; i++){
			String url = "http://" + host + "/web/info/hqcenter1.jsp?kind=selectmarket&market="+marketId+"&hqwb=&industry=0&pageIndex=" +  page;
			
			Document doc = fetchRealTimedoc(url);
			Elements trs = doc.select("div.bor tr");		//正文				
			for (Element tr : trs) {					
				Elements tds = tr.select("td.tb_bt11, td.tb_bt111");
				if (tds != null && ! tds.text().equals("")){
					//System.out.println("tds.toString()" + tds.toString());
					quotationsDomain.setSbcCode(tds.eq(0).text());
					String querySbcId = quotesRedisDao.getSbcId(exId, quotationsDomain.getSbcCode());
					if (querySbcId==null){
						//System.out.println( "找不到对应的Id： "+  exId  + quotationsDomain.getSbcCode());
						continue;
					}
					sbcId = Integer.parseInt(querySbcId);
					quotationsDomain.setSbcName(tds.eq(1).text());
					
					quotationsDomain.setExId(exId);
					quotationsDomain.setCurrentPrice(tds.eq(2).text());
					quotationsDomain.setOpeningPrice(tds.eq(3).text());
					quotationsDomain.setHighestPrice(tds.eq(4).text());
					quotationsDomain.setLowestPrice(tds.eq(5).text());
					quotationsDomain.setAveragePrice(tds.eq(6).text());
					quotationsDomain.setYesterAvPrice(tds.eq(7).text());
					quotationsDomain.setRiseValue(tds.eq(8).text());
					//if (CommonUtil.isStringNull(quotesRedisDao.getTotalValue(sbcId.toString()))){ //如果数据库里数据为		
					//} 
					quotationsDomain.setDealValue("");
					quotationsDomain.setDealVolume(tds.eq(9).text());
					quotationsDomain.setPoq(tds.eq(10).text());	
		
					
					quotationsDomain.setSbcId(sbcId);
					quotationsDomain.setYesterClPrice(quotesRedisDao.getClosePrice(querySbcId));
					
					String clPrice = quotationsDomain.getYesterClPrice();
					String riseValue = quotationsDomain.getRiseValue();
					if (CommonUtil.isStringDigit(clPrice) && CommonUtil.isStringDigit(riseValue) ){	
						
						Double riseScope = Double.parseDouble(riseValue) / Double.parseDouble(clPrice) * 100;					
						quotationsDomain.setRiseScope(CommonUtil.getFormatDouble(riseScope, 0));
					} else { //否则就为空？
						quotationsDomain.setRiseScope("0");
					}
					count++;
					String content = count +" -->: " +quotationsDomain.toString()+ "\n" +"============================ \n" ;
					CommonUtil.toLog(content);
					quotesRedisDao.addQuoteDomain(quotationsDomain);				
				}
			//}
		}
		String content = "Fetch END: "+ CommonUtil.getTimestamp() +"\n" + "-----------------------------------\n";
		CommonUtil.toLog(content);
		quotationsDomain = null;
    }
    }
}*/


class QuotesMoreFetcher extends BaseSpider implements Runnable{

	private  String exId;
	private  String marketId;
	private  String page;
	private QuotesRedisDao quotesRedisDao;
	private SbcBasicDao sbcBasicDao;
	public QuotesMoreFetcher( String exId, String marketId,String page, QuotesRedisDao quotesRedisDao,SbcBasicDao sbcBasicDao){
		host = "www.zongyihui.cn";
		init(host);
		this.exId = exId;
		this.marketId = marketId;
		this.quotesRedisDao= quotesRedisDao;
		this.sbcBasicDao = sbcBasicDao;
		this.page = page;
	}
	public int getMaxPageIndex(){
		int page = 0;
		//String url = "http://" + host + "/web/info/bika/news/ybkhq.jsp?kind=kind&market=0&hqwb=&industry=0";
		String url = "http://" + host + "/web/info/bika/news/ybkhq.jsp?kind=selectmarket&market="+marketId+"&hqwb=&industry=0";
		Document doc = fetchDocument(url);
		Elements tds = doc.select("td[height=30] td");		//正文
		
		if (tds !=null){
			page = Integer.parseInt(tds.last().text().split("/")[1]);
		}
		//System.out.println("page: " + page);
		return page;
	}
	public void fetchSHQuotationsMore(){
		List<String> sbcIdList = quotesRedisDao.hvals(CommonVariables.REDIS_KEY_SBCCODEMAP + CommonVariables.EXCHANGE_SHANGHAI);//查询该交易所下的所有品种行情
		int size = sbcIdList.size();
		String sbcIdStr = "";
		String totalVolume = "";
		String totalValue = "";
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0 ; i < size; i++){
			map.clear();
			String quotations =  quotesRedisDao.queryQuotations(exId, sbcIdList.get(i));	//上海交易所从实时行情里面取总交易额交易量
			if (quotations == null) {
				continue;
			}
			Map<String, String> quotMap =  quotesRedisDao.jsonStringToMap( quotations);		//查询品种的ID
			totalValue = quotMap.get("dealValue");
			totalVolume = quotMap.get("dealVolume");
			sbcIdStr = quotMap.get("sbcId");
			Double dealVolumeRes = null;
			Double dealValueRes = null;
			String dealVolume = quotesRedisDao.getTotalVolume(sbcIdStr);
			String dealValue = quotesRedisDao.getTotalValue(sbcIdStr);
			//如果交易量为空，则置总交易量为当前交易量；否则，计算差值
			if (CommonUtil.isStringNull(quotesRedisDao.getDealVolume(sbcIdStr))){
				dealVolume = totalVolume;
			} else {
				if (CommonUtil.isStringDigit(dealVolume)) {
					dealVolumeRes = Double.parseDouble(totalVolume) - Double.parseDouble(dealVolume);
					dealVolume = dealVolumeRes.toString();
				}
			}
			//如果交易额为空，则置总交易额为当前交易额；否则，计算差值
			if (CommonUtil.isStringNull(quotesRedisDao.getDealValue(sbcIdStr))){
				dealValue = totalValue;
			} else {
				if (CommonUtil.isStringDigit(dealValue)) {
					dealValueRes = Double.parseDouble(totalValue) - Double.parseDouble(dealValue);
					dealValue = dealValueRes.toString();
				}
			}
			
			map.put("totalVolume", totalVolume);
			map.put("totalValue", totalValue);
			map.put("dealVolume", dealVolume);
			map.put("dealValue", dealValue);
			quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcIdStr, map);
		}
		map = null;
	}
	public void run()
    {	
		if (marketId.equals("SH")){
		fetchSHQuotationsMore();
		} else {
		Integer sbcId  = null;
		int count = 0;
		String sbcName = null;
		String exName = null;
		String totalVolume = null;
		String totalValue = null;
		String exId = null;
		Map<String, String> map = new HashMap<String, String>();
		//for (int i = 1; i <= page; i++){
			String url = "http://" + host + "/web/info/bika/news/ybkhq.jsp?kind=selectmarket&market="+marketId+"&hqwb=&industry=0&pageIndex=" +  page;
			//System.out.println("url " + url);
			Document doc = null;
			try {
				doc = fetchRealTimedoc(url);
			} catch (Exception e){
				//System.out.println("打不开了：：： " + url);
			}
			Elements trs = doc.select("div.bor tr");		//正文				
			for (Element tr : trs) {
				Elements tds = tr.select("td.tb_bt11, td.tb_bt111");
				if (tds != null &&  tds.text() != null && ! tds.text().equals("")){
					sbcName = tds.eq(1).text().trim();
					exName = tds.eq(0).text().trim();
					totalVolume = tds.eq(7).text().trim();
					totalValue = tds.eq(8).text().trim();
					//System.out.println("sbcName: " + sbcName);
					//System.out.println("exName: " + exName);
					exId = getExId(exName);//CommonMaps.exchangeMap.get(exName);
					if (exId == null){
						//System.out.println("交易所id竟然是空的");
						continue;
					}
					//System.out.println("exId: " + exId);
					sbcId = sbcBasicDao.findSbcIdByName(sbcName, exId);
					String sbcIdStr = sbcId.toString();
					//System.out.println("找到对应的sbcId: " + sbcId);
					if (sbcId != null) {
						Double dealVolumeRes = null;
						Double dealValueRes = null;
						String dealVolume = quotesRedisDao.getTotalVolume(sbcIdStr);
						String dealValue = quotesRedisDao.getTotalValue(sbcIdStr);
						//如果交易量为空，则置总交易量为当前交易量；否则，计算差值
						if (CommonUtil.isStringNull(quotesRedisDao.getDealVolume(sbcIdStr))){
							dealVolume = totalVolume;
						} else {
							if (CommonUtil.isStringDigit(dealVolume)) {
								dealVolumeRes = Double.parseDouble(totalVolume) - Double.parseDouble(dealVolume);
								dealVolume = CommonUtil.getFormatDouble( dealVolumeRes, 0);
							}
						}
						//如果交易额为空，则置总交易额为当前交易量；否则，计算差值
						if (CommonUtil.isStringNull(quotesRedisDao.getDealValue(sbcIdStr))){
							dealValue = totalValue;
						} else {
							if (CommonUtil.isStringDigit(dealValue)) {
								dealValueRes = Double.parseDouble(totalValue) - Double.parseDouble(dealValue);
								dealValue = CommonUtil.getFormatDouble(dealValueRes, 0 );
							}
						}
						
						map.put("totalVolume", totalVolume);
						map.put("totalValue", totalValue);
						map.put("dealVolume", dealVolume);
						map.put("dealValue", dealValue);
						String content = "Fetch MORE: " + sbcId + ": "+ CommonUtil.getNowTimeStamp() +"\n" + "-----------------------------------\n";
						content += "totalVolume" + map.put("totalVolume", totalVolume) + "\n";
						content += "totalValue" + map.put("totalValue", totalValue) + "\n";
						content += "dealVolume" + map.put("dealVolume", dealVolume) + "\n";
						content += "dealValue" + map.put("dealValue", dealVolume) + "\n";
						CommonUtil.toLog(content);
						quotesRedisDao.hmset(CommonVariables.REDIS_KEY_QUOTSMORE+ sbcIdStr, map);
					}				
				}
			}
		//}
		//String content = "Fetch END: "+ CommonUtil.getNow() +"\n" + "-----------------------------------\n";
		//CommonUtil.toLog(content);
			map = null;
		}
    }
	
	public String getExId(String name){
		String res = null;
		if (name.equals("南京文交所")) {
			res = CommonVariables.EXCHANGE_NANJING;
		} else if (name.equals("南方文交所")) {
			res = CommonVariables.EXCHANGE_NANFANG;
		} else if (name.equals("中南邮票交易")) {
			res = CommonVariables.EXCHANGE_ZHONGNAN;
		} else if (name.equals("北交所福丽特")) {
			res = CommonVariables.EXCHANGE_FULITE;
		} else if (name.equals("金马甲")) {
			res = CommonVariables.EXCHANGE_JINMAJIA;
		} else if (name.equals("江苏省文交所")) {
			res = CommonVariables.EXCHANGE_JIANGSU;
		} else if (name.equals("华夏文交所")) {
			res = CommonVariables.EXCHANGE_HUAXIA;
		} else if (name.equals("华中文交所")) {
			res = CommonVariables.EXCHANGE_HUAZHONG;
		} else if (name.equals("中国艺交所邮")) {
			res = CommonVariables.EXCHANGE_YIJIAO;
		} 	
		return res;
	}
}
