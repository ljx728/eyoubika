package com.eyoubika.spider.service.impl;

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
import org.jsoup.select.Elements;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import com.eyoubika.spider.web.VO.FetchVO;

import com.eyoubika.util.CommonUtil;
import com.eyoubika.common.BaseSpider;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.RedisDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.spider.service.INjwjsSpiderService;
import com.eyoubika.spider.application.NjwjsSpiderAL;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.spider.domain.*;
import com.eyoubika.spider.dao.*;
import com.eyoubika.util.*;
/*==========================================================================================*
 * Description:	抓取南京文交所数据的小蜘蛛哈哈
 * Class:		NjwjsSpiderServiceImpl
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-28 15:26:18
 *==========================================================================================*/
public class NjwjsSpiderServiceImpl extends BaseService implements INjwjsSpiderService{

	private NewsArticleDomain newsArticleDomain;						//新闻
	private AnnouncementDomain announcementDomain;		//公告
	private BreedBasicDomain breedBasicDomain;			//品种基本信息
	private BreedArticleDomain breedArticleDomain;		//品种文章
	private NjwjsSpiderAL njwjsSpiderAL;


	public NjwjsSpiderAL getNjwjsSpiderAL() {
		return njwjsSpiderAL;
	}

	public void setNjwjsSpiderAL(NjwjsSpiderAL njwjsSpiderAL) {
		this.njwjsSpiderAL = njwjsSpiderAL;
	}

	public NewsArticleDomain getNewsArticleDomain() {
		return newsArticleDomain;
	}

	public void setNewsArticleDomain(NewsArticleDomain NewsArticleDomain) {
		this.newsArticleDomain = NewsArticleDomain;
	}

	public AnnouncementDomain getAnnouncementDomain() {
		return announcementDomain;
	}

	public void setAnnouncementDomain(AnnouncementDomain announcementDomain) {
		this.announcementDomain = announcementDomain;
	}

	public BreedBasicDomain getBreedBasicDomain() {
		return breedBasicDomain;
	}

	public void setBreedBasicDomain(BreedBasicDomain breedBasicDomain) {
		this.breedBasicDomain = breedBasicDomain;
	}

	public BreedArticleDomain getBreedArticleDomain() {
		return breedArticleDomain;
	}

	public void setBreedArticleDomain(BreedArticleDomain breedArticleDomain) {
		this.breedArticleDomain = breedArticleDomain;
	}

	
	public Map<String, Object> fetchAllNews(FetchVO fetchVO){	
		njwjsSpiderAL.fetchAllNews(fetchVO);
		return this.buildRetData(CommonVariables.TRUE_SHORT_FLAG, CommonVariables.TRUE_WROD_FLAG);
	}
	
	
	public Map<String, Object> fetchAllBreeds(FetchVO fetchVO){
		njwjsSpiderAL.fetchAllBreeds(fetchVO);
		return this.buildRetData(CommonVariables.TRUE_SHORT_FLAG, CommonVariables.TRUE_WROD_FLAG);
	}



	public Map<String, Object> fetchQuotations(FetchVO fetchVO){
		njwjsSpiderAL.fetchQuotations(fetchVO);
		return this.buildRetData(CommonVariables.TRUE_SHORT_FLAG, CommonVariables.TRUE_WROD_FLAG);
	}
	
	public void analysis(){
	
		
	}

}