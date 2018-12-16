package com.eyoubika.spider.service;

import java.util.Map;

import com.eyoubika.spider.web.VO.FetchVO;

public interface IZongyihuiSpiderService{
	public  Map<String, Object> fetchQuotations(FetchVO fetchVO);
	//public  Map<String, Object> fetchSbcs(FetchVO fetchVO);
	
}