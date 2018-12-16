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
import com.eyoubika.sbc.dao.SbcQuotationHistDao;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.sbc.domain.SbcIdMapDomain;
import com.eyoubika.sbc.domain.SbcQuotationHistDomain;
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
 * Description:	TODO
 * Class:		SbcVolumeSpiderAL
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年11月2日 下午2:39:36
 *==========================================================================================*/
public class SbcVolumeSpiderAL extends BaseSpider {
	private SbcBasicDao sbcBasicDao;
	private SbcQuotationHistDao sbcQuotationHistDao;
	
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
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		fetchSbcVolumeFromFile
	 * Author:		1.0 created by lijiaxuan at 2015年11月11日 下午3:31:49
	 *--------------------------------------------------------------------------------------*/
	public void fetchSbcVolumeFromFile() {   
		 //String fileName = "/Users/lijiaxuan/Desktop/volume.pdf";
		 String fileName = "/var/lib/tomcat7/webapps/eyoubika/resource/volume.pdf";
		 String fileContext  = "";
		 try {
			 fileContext = CommonUtil.getPdfFileText(fileName);
			}  catch (Exception e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE970011, YbkException.DESC970011 + fileName);
			}

		 String[] lines = fileContext.split("\n");
		 int lineNum = lines.length;
		 //String sbcName = "";
		 String sbcCode = "";
		 String sbcVolume = "";
		 Integer sbcId = null;
		 SbcBasicDomain sbcBasicDomain = new SbcBasicDomain();
		 for (int i = 0 ; i <  lineNum; i++){		 
			 if (lines[i].length() > 50 && lines[i].length() <= 150 ) {		//品种
				 System.out.println("line: " + lines[i]);
				 String[] attrs = lines[i].split("\\s");
					sbcBasicDomain.init();
					sbcCode = attrs[0].trim();
					sbcVolume = attrs[11].trim();
					sbcId = sbcBasicDao.findSbcIdByCode(sbcCode, "007000");
					if (sbcId == null){
						continue;
					}
					sbcBasicDomain.setSbcId(sbcId);
					sbcBasicDomain.setVolume(sbcVolume);
					sbcBasicDao.modifySbcBasic(sbcBasicDomain);			 
			 } else if (lines[i].length() <= 50 && lines[i].length() > 10  && lines[i].contains("合计")) {	
				 String[] attrs = lines[i].split("\\s");
				 System.out.println("lines[i] " + lines[i]);
				 System.out.println("attrs[i] " + attrs.length);
				 System.out.println(" attrs[0[i] " + attrs[0]);
				 Map<String,Object> map = new HashMap<String,Object>();
				 sbcId = sbcBasicDao.findSbcIdByName("综合指数", "007000");
				 if (sbcId == null){
						continue;
					}
				 int sbcVolumeD = 0;
				String table = "tp_Quotation_" + "007000";
				map.put("table", table);			
				map.put("sbcId", sbcId);
				map.put("type", CommonVariables.TIME_INTERVAL_DAY);
				SbcQuotationHistDomain	resDomain = sbcQuotationHistDao.findLastIntvalSbcQuotation(map);				 		 
				if (resDomain == null){
					continue;
				}
				 if (CommonUtil.isStringDigit(attrs[1]) && CommonUtil.isStringDigit(resDomain.getClosePrice())){
					 sbcVolumeD = (int) (Double.parseDouble(attrs[1]) / Double.parseDouble(resDomain.getClosePrice()));
				 } else {
					 continue;
				 }
				 sbcBasicDomain.init();
				 //sbcVolume = CommonUtil.getFormatDouble(sbcVolumeD, 0);
				 sbcBasicDomain.setSbcId(sbcId);
				 sbcBasicDomain.setVolume(""+sbcVolumeD);
				 System.out.println("sbcBasicDomain " + sbcBasicDomain);
				sbcBasicDao.modifySbcBasic(sbcBasicDomain);						 
			 } else if (lines[i].length() > 150) {	//这一行竟然出现了两个品种
				String[] attrs = lines[i].split("\\s");		 
				sbcBasicDomain.init();
				sbcCode = attrs[0].trim();
				sbcVolume = attrs[11].trim();
				sbcId = sbcBasicDao.findSbcIdByCode(sbcCode, "007000");
				if (sbcId == null){
					continue;
				}
				sbcBasicDomain.setSbcId(sbcId);
				sbcBasicDomain.setVolume(sbcVolume);
				System.out.println("sbcBasicDomain " + sbcBasicDomain);					
				if (attrs[14].contains("年")){
					sbcBasicDomain.init();
					sbcCode = attrs[14].split("年")[1].trim();
					sbcVolume = attrs[25].trim();
					sbcId = sbcBasicDao.findSbcIdByCode(sbcCode, "007000");
					if (sbcId == null){
						continue;
					}
					sbcBasicDomain.setSbcId(sbcId);
					sbcBasicDomain.setVolume(sbcVolume);
					sbcBasicDao.modifySbcBasic(sbcBasicDomain);	
				 }		 
			 }	
		 }
	 }  
	public void fetchSbcVolume(){	
		fetchSbcVolumeFromFile();
		host = "www.zongyihui.cn";
		//Integer sbcId  = 0;
		init(host);
		int produceTaskSleepTime = 400; //1s   
		int maxPoolSize = 100; 		// 线程池维护线程的最大数量
		int minPoolSize = 50;	 	// 线程池维护线程的最少数量
		long keepAliveTime = 3;		// 线程池维护线程所允许的空闲时间
		Map<String, String> exMap = new HashMap<String, String>();
		exMap.put("16", CommonVariables.EXCHANGE_NANJING);//南京文交所
		exMap.put("15", CommonVariables.EXCHANGE_NANFANG); //南方文交所
		exMap.put("19", CommonVariables.EXCHANGE_FULITE);
		exMap.put("42", CommonVariables.EXCHANGE_ZHONGNAN);
		exMap.put("36", CommonVariables.EXCHANGE_JINMAJIA);
		exMap.put("SH", CommonVariables.EXCHANGE_SHANGHAI);
		exMap.put("23", CommonVariables.EXCHANGE_YIJIAO);
	
		List<String> fetchList = new ArrayList<String>();	
		String[] marketIds = {"16", "15", "42", "19","36", "23"};	
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
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(minPoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());
		int index = 0;
		int fetchListSize = fetchList.size();	//总共87个页面。
		while (index < fetchListSize)//
		 {
			try
		            {   
		              
		                int id = index%fetchListSize;		  
		                String marketId= fetchList.get(id).split(",")[0];
		                String pageId= fetchList.get(id).split(",")[1];
		                threadPool.execute(new SbcVolumeFetcher(exMap.get(marketId), marketId, pageId, sbcBasicDao));

		                // 便于观察，等待一段时间
		                Thread.sleep(produceTaskSleepTime);
		                index++;
		            }
		            catch (Exception e)
		            {
		                e.printStackTrace();
		            }

		}
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

}


class SbcVolumeFetcher extends BaseSpider implements Runnable{
	private  String exId;
	private  String marketId;
	private  String page;
	private SbcBasicDao sbcBasicDao;
	public SbcVolumeFetcher( String exId, String marketId,String page, SbcBasicDao sbcBasicDao){
		host = "www.zongyihui.cn";
		init(host);
		this.exId = exId;
		this.marketId = marketId;
		this.sbcBasicDao = sbcBasicDao;
		this.page = page;
	}
	
	public void run()
    {	
		if (marketId.equals("SH")){
		//fetchSHQuotationsMore();
		} else {
		Integer sbcId  = null;
		String sbcName = null;
		String exName = null;
		String sbcVolume = null;
		String exId = null;
		SbcBasicDomain sbcBasicDomain = new SbcBasicDomain();
			String url = "http://" + host + "/web/info/bika/news/ybkhq.jsp?kind=selectmarket&market="+marketId+"&hqwb=&industry=0&pageIndex=" +  page;
			System.out.println("url " + url);
			Document doc = null;
			try {
				doc = fetchRealTimedoc(url);
			} catch (Exception e){
			}
			Elements trs = doc.select("div.bor tr");		//正文				
			for (Element tr : trs) {
				Elements tds = tr.select("td.tb_bt11, td.tb_bt111");
				if (tds != null &&  tds.text() != null && ! tds.text().equals("")){
					sbcBasicDomain.init();
					sbcName = tds.eq(1).text().trim();
					exName = tds.eq(0).text().trim();
					sbcVolume = tds.eq(9).text().trim();
					exId = getExId(exName);//CommonMaps.exchangeMap.get(exName);
					if (exId == null){
						continue;
					}		
					sbcId = sbcBasicDao.findSbcIdByName(sbcName, exId);
					if (sbcId == null){
						continue;
					}
					sbcBasicDomain.setSbcId(sbcId);
					sbcBasicDomain.setVolume(sbcVolume);
					sbcBasicDao.modifySbcBasic(sbcBasicDomain);	
				}
			}
			sbcBasicDomain = null;	
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
