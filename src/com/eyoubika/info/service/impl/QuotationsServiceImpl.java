package com.eyoubika.info.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.eyoubika.common.BaseService;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.util.HtmlUtil;
import com.eyoubika.info.domain.QuotationsDomain;
import com.eyoubika.info.domain.QuotationsRefreshDomain;
import com.eyoubika.info.service.IQuotationsService;
import com.eyoubika.info.web.VO.QuotationsVO;
import com.eyoubika.info.application.QuotationsAL;
import com.eyoubika.sbc.domain.SbcQuotationHistDomain;
import com.eyoubika.spider.dao.QuotesRedisDao;
import com.eyoubika.spider.domain.NewsBasicDomain;
import com.eyoubika.common.PageInfo;
/*==========================================================================================*
 * Description:	定义了用户服务的接口实现
 * Class:		NewsServiceImpl
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-6-27 10:11:22
 *==========================================================================================*/
public class QuotationsServiceImpl extends BaseService implements IQuotationsService {
	private QuotationsDomain quotationsDomain;
	private SbcQuotationHistDomain sbcQuotationHistDomain;
	private QuotationsAL quotationsAL;
	
	
	public SbcQuotationHistDomain getSbcQuotationHistDomain() {
		return sbcQuotationHistDomain;
	}

	public void setSbcQuotationHistDomain(
			SbcQuotationHistDomain sbcQuotationHistDomain) {
		this.sbcQuotationHistDomain = sbcQuotationHistDomain;
	}

	public QuotationsDomain getQuotationsDomain() {
		return quotationsDomain;
	}

	public void setQuotationsDomain(QuotationsDomain quotationsDomain) {
		this.quotationsDomain = quotationsDomain;
	}

	public QuotationsAL getQuotationsAL() {
		return quotationsAL;
	}

	public void setQuotationsAL(QuotationsAL quotationsAL) {
		this.quotationsAL = quotationsAL;
	}

	@Override
	public Map<String, Object> getQuotationList(QuotationsVO quotationsVO) {
		List<String> resList = null;
		if ( ! CommonUtil.isStringNull(quotationsVO.getExId()) ){
			resList =   quotationsAL.getQuotationList(quotationsVO.getExId());
		} else {
			resList =  quotationsAL.getQuotationList();
		}
	
		return this.buildRetData(quotationsVO.getUserId(), quotationsVO.getToken(), resList);
	}
	
	public Map<String, Object> getExQuotations(QuotationsVO quotationsVO) {
		List<String> resList = quotationsAL.getExQuotations();
		return this.buildRetData(quotationsVO.getUserId(), quotationsVO.getToken(), resList);
	}
	
	
	public Map<String, Object> getSbcQuotation(QuotationsVO quotationsVO) {
		List<String> resList = new  ArrayList<String>();	
		resList.add(quotationsAL.getSbcQuotation(quotationsVO.getExId(), quotationsVO.getSbcId()));
		return this.buildRetData(quotationsVO.getUserId(), quotationsVO.getToken(), resList);
	}
	public Map<String, Object> getSbcQuotationMore(QuotationsVO quotationsVO) {
		List<String> resList = quotationsAL.getSbcQuotationMore(quotationsVO.getSbcId());
		return this.buildRetData(quotationsVO.getUserId(), quotationsVO.getToken(), resList);
	}
	//同时返回K线图和分时图。
	public Map<String, Object> getSbcQuotationDetail(QuotationsVO quotationsVO) {
		QuotesRedisDao quotesRedisDao = new QuotesRedisDao();
		Map<String, Object> res = null;
		Map<String, Object>  map= new HashMap<String, Object>();
		if (quotationsVO.getInterval().equals(CommonVariables.TIME_INTERVAL_MIN)){
			List<String> tList = quotationsAL.getTChart(quotationsVO.getExId(), quotationsVO.getSbcId()); //分时图	
			map.put("chart", tList);
			//res =  this.buildRetData(quotationsVO.getUserId(), quotationsVO.getToken(), tList);
		} else {
			
			List<SbcQuotationHistDomain> kList = quotationsAL.getSbcQuotationDetail(quotationsVO.getSbcId(), quotationsVO.getInterval()); //分时图
			SbcQuotationHistDomain nowDomain = quotationsAL.getNowSbcQuotationHistDomain( quotationsVO.getSbcId(), quotationsVO.getInterval());
			kList.add(0,nowDomain);
			map.put("chart", kList);
		}
		String quotations = quotationsAL.getSbcQuotation(quotationsVO.getExId(), quotationsVO.getSbcId());
		map.put("quotations", quotations);
		res =  this.buildRetData(quotationsVO.getUserId(), quotationsVO.getToken(), map);
		return res;
	}	
	
	public Map<String, Object> getSbcQuotationRefresh(QuotationsVO quotationsVO) {
		Map<String, Object>  map= new HashMap<String, Object>();
		List<QuotationsRefreshDomain> resList = quotationsAL.getRefreshQuotations(quotationsVO.getExId(), quotationsVO.getSbcId()); //分时图
		map.put("chart", resList);
		String quotations = quotationsAL.getSbcQuotation(quotationsVO.getExId(), quotationsVO.getSbcId());
		map.put("quotations", quotations);
		Map<String, Object> res = this.buildRetData(quotationsVO.getUserId(), quotationsVO.getToken(), map);
		map = null;
		return res;		
	}
	public Map<String, Object> getBillboard(QuotationsVO quotationsVO) {
		List<String> resList = new  ArrayList<String>();
		resList = quotationsAL.getBillboard(quotationsVO.getExId(), quotationsVO.getType());		
		Map<String, Object> res = this.buildRetData(quotationsVO.getUserId(), quotationsVO.getToken(), resList);
		resList = null;
		return res;		
	}
	
	
	/*public Map<String, Object> getKChart(QuotationsVO quotationsVO) {
		List<String> resList = quotationsAL.getKChart(quotationsVO.getExId(), quotationsVO.getSbcId(), quotationsVO.getInterval());
		return this.buildRetData(quotationsVO.getUserId(), quotationsVO.getToken(), resList);
	}
	
	public Map<String, Object> getCChart(QuotationsVO quotationsVO) {
		List<String> resList = quotationsAL.getCChart(quotationsVO.getExId(), quotationsVO.getSbcId(), quotationsVO.getInterval());
		return this.buildRetData(quotationsVO.getUserId(), quotationsVO.getToken(), resList);
	}
	
	public Map<String, Object> getTChart(QuotationsVO quotationsVO) {
		List<String> resList = quotationsAL.getTChart(quotationsVO.getExId(), quotationsVO.getSbcId());
		return this.buildRetData(quotationsVO.getUserId(), quotationsVO.getToken(), resList);
	}*/
}