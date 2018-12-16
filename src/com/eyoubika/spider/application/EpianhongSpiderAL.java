package com.eyoubika.spider.application;

import java.io.IOException;
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
import com.eyoubika.common.RedisDao;
import com.eyoubika.common.CommonVariables;
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
public class EpianhongSpiderAL extends BaseSpider {
	private QuotesRedisDao quotesRedisDao;
	private SbcBasicDao sbcBasicDao;
	private SbcIdMapDao sbcIdMapDao;
	private SbcMapRedisDao sbcMapRedisDao;
	private String uid = "31acf78bc5afc2e8f5e79be35e81aca4";
	
	
	public SbcMapRedisDao getSbcMapRedisDao() {
		return sbcMapRedisDao;
	}

	public void setSbcMapRedisDao(SbcMapRedisDao sbcMapRedisDao) {
		this.sbcMapRedisDao = sbcMapRedisDao;
	}

	public SbcIdMapDao getSbcIdMapDao() {
		return sbcIdMapDao;
	}

	public void setSbcIdMapDao(SbcIdMapDao sbcIdMapDao) {
		this.sbcIdMapDao = sbcIdMapDao;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public void fetchTopQuotations(FetchVO fetchVO){
		host = "app.epianhong.com";
		init(host);
		String dzpid = "1";
		String url = host + "/getAllGoodsMarketInfoByDzp?uid="+this.uid+"&phonetype=1&dzpid=" + dzpid;
		Document doc = fetchDocument(url, "phone");
		String jsonStr = doc.select("goods").text().toString();
		System.out.println();
		String exId = CommonVariables.EXCHANGE_FULITE;
		//RedisDao redisDao = new RedisDao();
		//quotesRedisDao.addQuotes(JSONObject.fromObject(jsonStr), exId);
	} 
	
	public void fetchQuotations(FetchVO fetchVO){
		host = "http://app.epianhong.com";
		init(host);
		String dzpid = sbcMapRedisDao.findEpid(fetchVO.getExId());
		System.out.println("getExtId " +fetchVO.getExId());
		System.out.println("dzpid " +dzpid);
		String url = host + "/getAllGoodsMarketInfoByDzp?uid="+uid+"&phonetype=1&dzpid=" + dzpid;
		Document doc = fetchDocument(url, "phone");
		String jsonStr = doc.select("body").text().toString();
		System.out.println("url" + url);
		System.out.println("jsonStr" + jsonStr);
		String exId =fetchVO.getExId();
		//RedisDao redisDao = new RedisDao();
		//quotesRedisDao.addQuotes(JSONObject.fromObject(jsonStr), exId);
	} 
	
	public void fetchSbcs(FetchVO fetchVO){
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
			//System.out.println("jsonStr" + jsonStr);
			String exId = sbcMapRedisDao.findExId(dzpid) ;
					JSONArray jsonArray = JSONArray.fromObject(JSONObject.fromObject(jsonStr).get("goods"));
					 for (int i = 0; i < jsonArray.size(); i++) { 	
						 SbcBasicDomain  sbcBasicDomain = new SbcBasicDomain();
						 SbcIdMapDomain sbcIdMapDomain = new SbcIdMapDomain();
						 JSONObject obj = JSONObject.fromObject(jsonArray.get(i));
						 sbcBasicDomain.setExId(exId);
						 sbcBasicDomain.setInputDate(CommonUtil.getNowDate());
						 sbcBasicDomain.setIssuePrice(obj.get("publishprice").toString());
						 sbcBasicDomain.setIssueTime(obj.get("publishtime").toString());
						 sbcBasicDomain.setSbcCode(obj.get("code").toString());
						 sbcBasicDomain.setSbcName(obj.get("name").toString());
						 sbcBasicDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
						 String type = "";
						 if (obj.get("type").toString().equals("1")){
							 type = CommonVariables.SBC_INITIAL;
						 } else  if (obj.get("type").toString().equals("2")){
							 type = CommonVariables.SBC_INDEX;
						 } 
						 sbcBasicDomain.setSbcType(type);
						 Integer sbcId = null;
						sbcId = sbcBasicDao.findSbcIdBySbcCode(obj.get("code").toString());
						if (sbcId == null) {						
							sbcId = sbcBasicDao.addSbcBasic(sbcBasicDomain);
							sbcIdMapDomain.setSbcId(sbcId);
							sbcIdMapDomain.setEphId(obj.get("goodsId").toString());
							sbcIdMapDomain.setEphCode(obj.get("code").toString());
							sbcIdMapDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
							System.out.println("sbcIdMapDomain ; " + sbcIdMapDomain);
							sbcIdMapDao.addSbcIdMap(sbcIdMapDomain);	
							sbcMapRedisDao.addSbcMap(sbcIdMapDomain);
						}			
					}
		}
		
	}
	

}