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
 * Description:	交易所品种数据抓取小蜘蛛哈哈
 * Class:		QuotationsIniterAL
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年9月30日 上午9:36:32
 *==========================================================================================*/
public class QuotationsIniterAL extends BaseSpider {
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
	 * Description:	初始化邮币卡品种内存数据库。从mysql数据库读入到redis数据库
	 * Method:		initSbcRedis
	 * Author:		1.0 created by lijiaxuan at 2015年10月20日 下午4:21:14
	 *--------------------------------------------------------------------------------------*/
	public void initSbcRedis(){
		String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	  
		int marketIdsSize = marketIds.length;
		int sbcListSize = 0;
		SbcBasicDomain sbcBasicDomain = new SbcBasicDomain();
		for (int i = 0; i < marketIdsSize; i++){
			sbcBasicDomain.setExId(marketIds[i]);
			List<SbcBasicDomain> sbcList = sbcBasicDao.querySbcBasic(sbcBasicDomain);
			sbcListSize = sbcList.size(); 
			for (int j = 0; j < sbcListSize; j++){
				quotesRedisDao.initQuoteRedis(sbcList.get(j));	
			}		
		}									
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	初始化邮币卡品种数据库
	 * Method:		initSbcs
	 * Author:		1.0 created by lijiaxuan at 2015年8月19日 下午3:58:45
	 *--------------------------------------------------------------------------------------*/
	public void initSbcs(){
		int produceTaskSleepTime = 200; //0.2s  
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
		String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	  
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(minPoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
	                new ThreadPoolExecutor.DiscardOldestPolicy()){
            protected void beforeExecute(Thread t, Runnable r) {
            }
            protected void afterExecute(Runnable r, Throwable t) {
            }
            protected void terminated() {
            }
        };
		int fetchListSize = marketIds.length;
		for (int i = 0; i < fetchListSize; i++)
		try{
			    int id = i%fetchListSize;		  
		        String exId = marketIds[id];
		        threadPool.execute(new Initer( exId,  exMap.get(exId) ,quotesRedisDao));
		        //等待一段时间，减轻服务器压力
		        Thread.sleep(produceTaskSleepTime);
		} catch (Exception e){
		    e.printStackTrace();
		}
		exMap = null;
	} 		
}
/*==========================================================================================*
 * Description:	多线程之初始化
 * Class:		Fetcher
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年8月19日 下午3:59:38
 *==========================================================================================*/
class Initer extends BaseSpider implements Runnable{
	private  String exId;
	private String url;
	private QuotesRedisDao quotesRedisDao;
	public Initer(String exId, String url, QuotesRedisDao quotesRedisDao){
		host = "www.zongyihui.cn";
		init(host);
		this.exId = exId;	
		this.url = url;
		this.quotesRedisDao= quotesRedisDao;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	福利特品种数据抓取
	 * Method:		fulite004000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年9月30日 下午3:49:56
	 *--------------------------------------------------------------------------------------*/
	private void fulite004000Initer( QuotesRedisDao quotesRedisDao){
		String url = "http://dzp.wjybk.com/adv/cs_com_cn/quotation.html" + "?=" + (1+Math.random()*(1000-1+1));
		Integer sbcId  = 0;
		String exId= CommonVariables.EXCHANGE_FULITE;
		host = "dzp.wjybk.com";
		SbcBasicDomain sbcBasicDomain=new SbcBasicDomain();
		SbcBasicDao sbcBasicDao = new SbcBasicDao();
		Document doc = fetchRealTimedoc(url);
		try {
			String exQuoteStr = doc.select("body").text().toString();
			if ( ! CommonUtil.isStringNull(exQuoteStr)) {
				String[] exQuoteList = exQuoteStr.split("\\|");
				int exQuoteSize = exQuoteList.length;
				for (int i = 0; i < exQuoteSize; i++){
					String sbcQuoteStr = exQuoteList[i];
					String[] sbcQuoteList = sbcQuoteStr.split(",");				
					sbcBasicDomain.init();
					sbcBasicDomain.setExId(exId);
					sbcBasicDomain.setSbcCode(sbcQuoteList[0].trim());
					//
					//如果，数据库里面存在，则不插入。
					if (sbcBasicDao.isExistByDomain(sbcBasicDomain)){
						continue;
					}
					sbcBasicDomain.setSbcName(sbcQuoteList[1].trim());
					sbcBasicDomain.setInputDate(CommonUtil.getNowDate());
					sbcBasicDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
					if (sbcBasicDomain.getSbcName().contains("指数")){
						sbcBasicDomain.setSbcType(CommonVariables.SBC_INDEX);	//指数类品种
					} else {
						sbcBasicDomain.setSbcType(CommonVariables.SBC_INITIAL);	//一般初始化品种（未分类）
					}									
					sbcId = sbcBasicDao.addSbcBasic(sbcBasicDomain);
					//sbcBasicDomain.setSbcId(sbcId);
					//quotesRedisDao.initQuoteRedis(sbcBasicDomain);			
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980012, YbkException.DESC980012);
		}		
		sbcBasicDao = null;
		sbcBasicDomain = null;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	南方交易所品种初始化
	 * Method:		nanfang002000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年9月30日 下午3:50:08
	 *--------------------------------------------------------------------------------------*/
	private void nanfang002000Fetcher( QuotesRedisDao quotesRedisDao){
		String url = "http://121.40.125.254:18080/tradeweb/refreshHQ";
		Integer sbcId  = 0;
		String exId= CommonVariables.EXCHANGE_NANFANG;
		host = "121.40.125.254";
		SbcBasicDomain sbcBasicDomain=new SbcBasicDomain();
		SbcBasicDao sbcBasicDao = new SbcBasicDao();
		JSONArray jsonArray = null;	
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
			try {			
				sbcBasicDomain.init();
				sbcBasicDomain.setExId(exId);
				sbcBasicDomain.setSbcCode(obj.get("c").toString().trim());
				//
				//如果，数据库里面存在，则不插入。
				if (sbcBasicDao.isExistByDomain(sbcBasicDomain)){
					continue;
				}
				sbcBasicDomain.setSbcName(obj.get("fn").toString());
				sbcBasicDomain.setInputDate(CommonUtil.getNowDate());
				sbcBasicDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
				if (sbcBasicDomain.getSbcName().contains("指数")){
					sbcBasicDomain.setSbcType(CommonVariables.SBC_INDEX);	//指数类品种
				} else {
					sbcBasicDomain.setSbcType(CommonVariables.SBC_INITIAL);	//一般初始化品种（未分类）
				}									
				sbcId = sbcBasicDao.addSbcBasic(sbcBasicDomain);	
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980013, YbkException.DESC980013);
			}			
		}
		sbcBasicDomain = null;
		sbcBasicDao = null;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	上海交易所品种初始化
	 * Method:		shanghai007000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年9月30日 下午3:52:26
	 *--------------------------------------------------------------------------------------*/
	private void shanghai007000Initer( QuotesRedisDao quotesRedisDao){
		//String url = "http://ta.shscce.com:8080/front/hq/delay_hq.htm";
		String url = "http://ta.shscce.com:8080/front/hq/delay_hq.json";
		Integer sbcId  = 0;
		String exId= CommonVariables.EXCHANGE_SHANGHAI;
		host = "ta.shscce.com";
		SbcBasicDomain sbcBasicDomain=new SbcBasicDomain();
		SbcBasicDao sbcBasicDao = new SbcBasicDao();
		int count = 0;
			Document doc = fetchRealTimedoc(url);
			String tmpHtml = doc.toString().split("indexHtml\":\"")[1].replace("\"}])", "").replaceAll("&lt;\\\\/td&gt;", "").replaceAll("&lt;\\\\/tr&gt;", "").replaceAll("&lt;\\\\/table&gt;", "");
			doc = Jsoup.parse(tmpHtml);
			//System.out.println("doc " + doc.toString());
			Elements trs = doc.select("table").get(1).select("tr");		//正文		
			for (Element tr : trs) {		
				Elements tds = tr.select("td");
				if (tds != null && ! tds.text().equals("")){
					
					sbcBasicDomain.init();
					sbcBasicDomain.setExId(exId);
					sbcBasicDomain.setSbcCode(tds.eq(1).text().trim());
					//
					//如果，数据库里面存在，则不插入。
					if (sbcBasicDao.isExistByDomain(sbcBasicDomain)){
						continue;
					}		
					sbcBasicDomain.setSbcName(tds.eq(2).text().trim());
					sbcBasicDomain.setInputDate(CommonUtil.getNowDate());
					sbcBasicDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
					if (sbcBasicDomain.getSbcName().contains("指数")){
						sbcBasicDomain.setSbcType(CommonVariables.SBC_INDEX);	//指数类品种
					} else {
						sbcBasicDomain.setSbcType(CommonVariables.SBC_INITIAL);	//一般初始化品种（未分类）
					}									
					sbcId = sbcBasicDao.addSbcBasic(sbcBasicDomain);
				}
			}
		shanghai007000InitIndex(sbcBasicDomain,  quotesRedisDao, sbcBasicDao);
		sbcBasicDomain = null;
		sbcBasicDao = null;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	上海交易所综合指数单独设置
	 * Method:		shanghai007000InitIndex
	 * Author:		1.0 created by lijiaxuan at 2015年9月20日 下午3:52:12
	 *--------------------------------------------------------------------------------------*/
	void shanghai007000InitIndex(SbcBasicDomain sbcBasicDomain, QuotesRedisDao quotesRedisDao,SbcBasicDao sbcBasicDao){
		exId = CommonVariables.EXCHANGE_SHANGHAI;
		sbcBasicDomain.init();
		sbcBasicDomain.setExId(exId);
		sbcBasicDomain.setSbcCode("600001");
		//
		//如果，数据库里面存在，则不插入。
		if (sbcBasicDao.isExistByDomain(sbcBasicDomain)){
			return;
		}
		sbcBasicDomain.setSbcName("综合指数");
		sbcBasicDomain.setInputDate(CommonUtil.getNowDate());
		sbcBasicDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
		sbcBasicDomain.setSbcType(CommonVariables.SBC_INDEX);	//指数类品种									
		sbcBasicDao.addSbcBasic(sbcBasicDomain);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		commonFetcher
	 * Author:		1.0 created by lijiaxuan at 2015年9月30日 下午3:52:26
	 *--------------------------------------------------------------------------------------*/
	private void commonIniter( String host, String url, String exId, QuotesRedisDao quotesRedisDao){
		Integer sbcId  = 0;
		int count = 0;
		JSONArray jsonArray = null;
		Document doc = fetchRealTimedoc(url);
		SbcBasicDomain sbcBasicDomain=new SbcBasicDomain();
		SbcBasicDao sbcBasicDao = new SbcBasicDao();	
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
			try {
				sbcBasicDomain.init();
				sbcBasicDomain.setExId(exId);
				sbcBasicDomain.setSbcCode(obj.get("code").toString().trim());
				//
				//如果，数据库里面存在，则不插入。
				if (sbcBasicDao.isExistByDomain(sbcBasicDomain)){
					continue;
				}	
				sbcBasicDomain.setSbcName(obj.get("fullname").toString());
				sbcBasicDomain.setInputDate(CommonUtil.getNowDate());
				sbcBasicDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
				if (sbcBasicDomain.getSbcName().contains("指数")){
					sbcBasicDomain.setSbcType(CommonVariables.SBC_INDEX);	//指数类品种
				} else {
					sbcBasicDomain.setSbcType(CommonVariables.SBC_INITIAL);	//一般初始化品种（未分类）
				}									
				sbcId = sbcBasicDao.addSbcBasic(sbcBasicDomain);
				//sbcBasicDomain.setSbcId(sbcId);
				//quotesRedisDao.initQuoteRedis(sbcBasicDomain);			
			} catch (Exception e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE980013, YbkException.DESC980013);
			}			
		}
		sbcBasicDomain = null;
		sbcBasicDao = null;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	线程执行入口
	 * Method:		run
	 * Author:		1.0 created by lijiaxuan at 2015年9月30日 下午4:52:26
	 *--------------------------------------------------------------------------------------*/
	public void run()
    {
		switch (this.exId){		
			case CommonVariables.EXCHANGE_NANFANG: {
				nanfang002000Fetcher(this.quotesRedisDao);		
				break;
			}
			case CommonVariables.EXCHANGE_FULITE: {
				fulite004000Initer(this.quotesRedisDao);		
				break;
			}
			case CommonVariables.EXCHANGE_SHANGHAI: {
				shanghai007000Initer(this.quotesRedisDao);	
				break;
			}
			default: {				
				commonIniter( CommonUtil.getHost(url), url, exId, this.quotesRedisDao);
				break;
			}			
		}
    }
}
