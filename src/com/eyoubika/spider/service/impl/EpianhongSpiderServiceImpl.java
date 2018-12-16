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
import com.eyoubika.spider.service.IEpianhongSpiderService;
import com.eyoubika.spider.service.INjwjsSpiderService;
import com.eyoubika.spider.application.EpianhongSpiderAL;
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
public class EpianhongSpiderServiceImpl extends BaseService implements IEpianhongSpiderService{
	private EpianhongSpiderAL epianhongSpiderAL;
	
	public EpianhongSpiderAL getEpianhongSpiderAL() {
		return epianhongSpiderAL;
	}

	public void setEpianhongSpiderAL(EpianhongSpiderAL epianhongSpiderAL) {
		this.epianhongSpiderAL = epianhongSpiderAL;
	}

	public Map<String, Object> fetchQuotations(FetchVO fetchVO){
		epianhongSpiderAL.fetchQuotations(fetchVO);
		return this.buildRetData(CommonVariables.TRUE_SHORT_FLAG, CommonVariables.TRUE_WROD_FLAG);
	}
	public Map<String, Object> fetchSbcs(FetchVO fetchVO){
		epianhongSpiderAL.fetchSbcs(fetchVO);
		return this.buildRetData(CommonVariables.TRUE_SHORT_FLAG, CommonVariables.TRUE_WROD_FLAG);
	}
	
}