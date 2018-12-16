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
public class InvestGamesNewsSpiderAL extends BaseSpider {

	private InvestGamesNewsDao investGamesNewsDao;	//
	
	public InvestGamesNewsDao getInvestGamesNewsDao() {
		return investGamesNewsDao;
	}
	public void setInvestGamesNewsDao(InvestGamesNewsDao investGamesNewsDao) {
		this.investGamesNewsDao = investGamesNewsDao;
	}
	
	public String fetchNews(InvestGamesNewsDomain investGamesNewsDomain){
		host = "http://mp.weixin.qq.com";
		init(host);
		String resHtml = "";
		//url = "http://mp.weixin.qq.com/s?__biz=MzA5MDA4NjQ0Nw==&mid=207915405&idx=1&sn=aa4c4c61380456f0820617b76b8d7934#rd";
		Document doc = fetchDocument(investGamesNewsDomain.getUrl(), "weixin");
		//Document doc = fetchRealTimedoc(investGamesNewsDomain.getUrl());
		System.out.println(doc.toString());
		String title = doc.select("h2.rich_media_title").text().trim();
		//Elements originEl = doc.select("div.rich_media_thumb_wrp");
		//String imgUrl = originEl.toString().split("function")[1].split("\"")[1];
		//System.out.println(originEl.toString());
	
		//> 1. 修改图片URL
		/*
		String imgContent = "<div class=\"rich_media_thumb_wrp_add\" id=\"media\">" +
"<img class=\"rich_media_thumb\" id=\"js_cover\" src=\"" + imgUrl + "\" /></div>";
		doc.select("div.rich_media_thumb_wrp").after(imgContent);
		doc.select("div.rich_media_thumb_wrp").remove();*/
		//> 2. 修改微信扫一扫	
		String saoyisaoUrl ="http://mp.weixin.qq.com/mp/qrcode?scene=10000004&size=102&__biz=MzA5MDA4NjQ0Nw==";
		Elements saoyisaoEl = doc.select("div.qr_code_pc");		
		String saoyisaoContent = "<div class=\"qr_code_pc_add\"><img id=\"js_pc_qr_code_img\" class=\"qr_code_pc_img\" src=\""+  
		saoyisaoUrl +"\"><p>微信扫一扫<br>关注该公众号</p></div>";
		doc.select("div.qr_code_pc").after(saoyisaoContent);
		doc.select("div.qr_code_pc").remove();
		resHtml = doc.toString().replace("qr_code_pc_add", "qr_code_pc").replace("data-src", "src");
		
		String fileDir=CommonUtil.INVESTMENT_HTML_DIR;
		//String fileDir="/Users/lijiaxuan/ljx728/";
		//String maxNo = "1";
		String fileName=investGamesNewsDomain.getNewsId()+".html";
		//System.out.println("fileDir: " + fileDir);
		CommonUtil.toFile(resHtml, fileName, fileDir);
		//investGamesNewsDomain.setTime(CommonUtil.getNow());
		investGamesNewsDomain.setTitle(title);
		if (CommonUtil.isStringNull(title)){
			throw new YbkException(YbkException.CODE980005, YbkException.DESC980005);
		}
		investGamesNewsDomain.setType(CommonVariables.FETCH_CONTENT_ORIGIN);
		//System.out.println(investGamesNewsDomain.toString());
		//investGamesNewsDomain.setNewsId(maxNo);
		investGamesNewsDao.modifyInvestGamesNews(investGamesNewsDomain);
		return title;
	}
	
	public String fetchNewsBak(InvestGamesNewsDomain investGamesNewsDomain){
		host = "http://mp.weixin.qq.com";
		init(host);
		String resHtml = "";
		//url = "http://mp.weixin.qq.com/s?__biz=MzA5MDA4NjQ0Nw==&mid=207915405&idx=1&sn=aa4c4c61380456f0820617b76b8d7934#rd";
		Document doc = fetchDocument(investGamesNewsDomain.getUrl(), "phone");
		Elements originEl = doc.select("div.rich_media_thumb_wrp");
		String imgUrl = originEl.toString().split("function")[1].split("\"")[1];
		//System.out.println(originEl.toString());
		String title = doc.select("h2.rich_media_title").text().trim();
		//> 1. 修改图片URL
		
		String imgContent = "<div class=\"rich_media_thumb_wrp_add\" id=\"media\">" +
"<img class=\"rich_media_thumb\" id=\"js_cover\" src=\"" + imgUrl + "\" /></div>";
		doc.select("div.rich_media_thumb_wrp").after(imgContent);
		doc.select("div.rich_media_thumb_wrp").remove();
		//> 2. 修改微信扫一扫
		
		String saoyisaoUrl ="http://mp.weixin.qq.com/mp/qrcode?scene=10000004&size=102&__biz=MzA5MDA4NjQ0Nw==";
		Elements saoyisaoEl = doc.select("div.qr_code_pc");
		
		String saoyisaoContent = "<div class=\"qr_code_pc_add\"><img id=\"js_pc_qr_code_img\" class=\"qr_code_pc_img\" src=\""+  
		saoyisaoUrl +"\"><p>微信扫一扫<br>关注该公众号</p></div>";
		doc.select("div.qr_code_pc").after(saoyisaoContent);
		doc.select("div.qr_code_pc").remove();
		resHtml = doc.toString().replace("qr_code_pc_add", "qr_code_pc").replace("rich_media_thumb_wrp_add", "rich_media_thumb_wrp");
		//String resString = "";
		//Elements texts = doc.select("div.neirong_text");		
		//System.out.println(doc.toString().replace("qr_code_pc_add", "qr_code_pc"));			
		String fileDir=CommonUtil.INVESTMENT_HTML_DIR;
		//String fileDir="/Users/lijiaxuan/ljx728/";
		//String maxNo = "1";
		String fileName=investGamesNewsDomain.getNewsId()+".html";
		//System.out.println("fileDir: " + fileDir);
		CommonUtil.toFile(resHtml, fileName, fileDir);
		//investGamesNewsDomain.setTime(CommonUtil.getNow());
		investGamesNewsDomain.setTitle(title);
		if (CommonUtil.isStringNull(title)){
			throw new YbkException(YbkException.CODE980005, YbkException.DESC980005);
		}
		investGamesNewsDomain.setType(CommonVariables.FETCH_CONTENT_ORIGIN);
		//System.out.println(investGamesNewsDomain.toString());
		//investGamesNewsDomain.setNewsId(maxNo);
		investGamesNewsDao.modifyInvestGamesNews(investGamesNewsDomain);
		return title;
	}
	/*public static void main(String args[]){
		InvestGamesNewsSpiderAL investGamesNewsSpiderAL = new InvestGamesNewsSpiderAL();
		investGamesNewsSpiderAL.fetchNews("");
	}*/
}
