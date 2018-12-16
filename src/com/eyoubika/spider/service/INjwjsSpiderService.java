package com.eyoubika.spider.service;

import java.util.Map;

import com.eyoubika.spider.web.VO.FetchVO;

public interface INjwjsSpiderService{
	public  Map<String, Object> fetchQuotations(FetchVO fetchVO);
	public Map<String, Object> fetchAllNews(FetchVO fetchVO);
	public Map<String, Object> fetchAllBreeds(FetchVO fetchVO);
	
}