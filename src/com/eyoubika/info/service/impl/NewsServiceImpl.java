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
import com.eyoubika.info.web.VO.NewsVO;
import com.eyoubika.spider.domain.NewsArticleDomain;
import com.eyoubika.spider.domain.SubscribeArticleDomain;
import com.eyoubika.info.service.INewsService;
import com.eyoubika.info.web.VO.NewsVO;
import com.eyoubika.info.application.NewsAL;
import com.eyoubika.info.application.SubscribeArticleAL;
import com.eyoubika.spider.domain.NewsBasicDomain;
import com.eyoubika.common.PageInfo;
/*==========================================================================================*
 * Description:	定义了用户服务的接口实现
 * Class:		NewsServiceImpl
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-6-27 10:11:22
 *==========================================================================================*/
public class NewsServiceImpl extends BaseService implements INewsService {
	private NewsArticleDomain newsArticleDomain;
	private NewsAL newsAL;
	private SubscribeArticleDomain subscribeArticleDomain;
	private SubscribeArticleAL  subscribeArticleAL;

	
	public NewsArticleDomain getNewsArticleDomain() {
		return newsArticleDomain;
	}

	public void setNewsArticleDomain(NewsArticleDomain newsArticleDomain) {
		this.newsArticleDomain = newsArticleDomain;
	}

	public NewsAL getNewsAL() {
		return newsAL;
	}
	public void setNewsAL(NewsAL newsAL) {
		this.newsAL = newsAL;
	}

	
	public SubscribeArticleDomain getSubscribeArticleDomain() {
		return subscribeArticleDomain;
	}

	public void setSubscribeArticleDomain(
			SubscribeArticleDomain subscribeArticleDomain) {
		this.subscribeArticleDomain = subscribeArticleDomain;
	}

	public SubscribeArticleAL getSubscribeArticleAL() {
		return subscribeArticleAL;
	}

	public void setSubscribeArticleAL(SubscribeArticleAL subscribeArticleAL) {
		this.subscribeArticleAL = subscribeArticleAL;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNewsList
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午10:18:46
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getNewsList(NewsVO newsVO, PageInfo pageInfo){	
		NewsBasicDomain newsBasicDomain = new NewsBasicDomain();
		Map<String, Object> params = new HashMap<String, Object>();
		switch (newsVO.getType()){		
			case CommonVariables.NOTE_SUBSCRIBE: {
				subscribeArticleDomain.init();
				subscribeArticleDomain.setExId(newsVO.getExId());
				PageInfo res =  subscribeArticleAL.getSubscribeArticleList(subscribeArticleDomain, pageInfo);	
				List<SubscribeArticleDomain>  listDomains = (List<SubscribeArticleDomain>) res.getResult();// = newsAL.getNewsList(newsBasicDomain, pageInfo);			
				for (int i = 0 ; i < listDomains.size(); i++){	
					//生成简介
					listDomains.get(i).setArticle(buildBreif(listDomains.get(i).getArticle()));		
				}
				params.put("size", res.getTotalNum());
				params.put("next", res.getNext());
				params.put("list", listDomains);		
				break;
			}
			case CommonVariables.NOTE_NOTICE: {					
				newsBasicDomain = newsVOToNewsBasicDomain(newsVO);	
				newsBasicDomain.setFrom(newsVO.getExId());
				PageInfo res =  newsAL.getNewsList(newsBasicDomain, pageInfo);	
				List<NewsBasicDomain>  listDomains = (List<NewsBasicDomain>) res.getResult();// = newsAL.getNewsList(newsBasicDomain, pageInfo);			
				for (int i = 0 ; i < listDomains.size(); i++){	
					//生成简介
					listDomains.get(i).setBrief(buildBreif(listDomains.get(i).getBrief()));		
				}
				params.put("size", res.getTotalNum());
				params.put("next", res.getNext());
				params.put("list", listDomains);		
				break;
			}				
		}	
		return this.buildRetData(newsVO.getUserId(), newsVO.getToken(), params);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNewsList
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午10:18:46
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getNewsByParams(NewsVO newsVO){
		NewsArticleDomain newsArticleDomain = new NewsArticleDomain();
		System.out.println("domain: " + newsArticleDomain);
		newsArticleDomain = newsVOToNewsArticleDomain(newsVO);		
		System.out.println("domain: " + newsArticleDomain);
		List<NewsArticleDomain>  listDomains = newsAL.getNewsByParams(newsArticleDomain);
		List<NewsArticleDomain>  resDomains = new ArrayList<NewsArticleDomain>(); 
		for (int i = 0 ; i < listDomains.size(); i++){
			NewsArticleDomain domain = new NewsArticleDomain();
			//resDomains.add(convertArticleDomain(listDomains.get(i)));
			listDomains.get(i).setArticle(convertArticle(listDomains.get(i)));
			resDomains.add(listDomains.get(i));
			System.out.println("domain: " + resDomains.get(i));
		}
		return this.buildRetData(newsVO.getUserId(), newsVO.getToken(), resDomains, 0);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNews
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午12:32:41
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getNews(NewsVO newsVO){	
		Object ob= null;
		switch (newsVO.getType()){		
			case CommonVariables.NOTE_SUBSCRIBE: {
				SubscribeArticleDomain subscribeArticleDomain =  subscribeArticleAL.getSubscribeArticle(Integer.parseInt(newsVO.getNewsId()));			
				ob = subscribeArticleDomain;			
				break;
			}
			case CommonVariables.INFO_INDUSTRY_NEWS: {				
				NewsArticleDomain newsDomain = newsAL.getNews(Integer.parseInt(newsVO.getNewsId()));
				System.out.println("domain: " + newsDomain);
				newsDomain.setArticle(convertArticle(newsDomain));
				ob = newsDomain;			
				break;
			}				
		}
		return this.buildRetData(newsVO.getUserId(), newsVO.getToken(), ob);
	}

		
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		convertToDomain
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午10:58:23
	 *--------------------------------------------------------------------------------------*/
	public NewsArticleDomain newsVOToNewsArticleDomain(NewsVO newsVO){
		NewsArticleDomain resDomain = new NewsArticleDomain();	
		if ( !CommonUtil.isStringNull(newsVO.getNewsId())){
			resDomain.setNewsId(Integer.parseInt(newsVO.getNewsId()));
		}	
		resDomain.setFrom(newsVO.getExId());
		resDomain.setType(newsVO.getType());
		resDomain.setWebTime(newsVO.getTime());
		resDomain.setStatus(newsVO.getStatus());
		return resDomain;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		convertToNewsBasicDomain
	 * Author:		1.0 created by lijiaxuan at 2015年6月29日 下午2:39:30
	 *--------------------------------------------------------------------------------------*/
	public NewsBasicDomain newsVOToNewsBasicDomain(NewsVO newsVO){
		NewsBasicDomain resDomain = new NewsBasicDomain();	
		if ( !CommonUtil.isStringNull(newsVO.getNewsId())){
			resDomain.setNewsId(Integer.parseInt(newsVO.getNewsId()));
		}
		resDomain.setFrom(newsVO.getExId());
		resDomain.setType(newsVO.getType());
		resDomain.setWebTime(newsVO.getTime());
		resDomain.setStatus(newsVO.getStatus());
		return resDomain;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildImgUrl
	 * Author:		1.0 created by lijiaxuan at 2015年6月28日 下午3:13:48
	 *--------------------------------------------------------------------------------------*/
	/*public String buildImgUrl(String imgName){
		String imgUrl = CommonUtil.WEB_ROOT;
		imgUrl += CommonUtil.IMG_DIR + imgName;
		System.out.println("imgUrl " + imgUrl);
		return imgUrl;
	}*/
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		convertArticle
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午10:58:26
	 *--------------------------------------------------------------------------------------*/
	public String convertArticle(NewsArticleDomain newsArticleDomain){
	
		//String resArticle = "";//"<html><head></head><body>";
		String resArticle = "<html><head></head><body>";
		String article = newsArticleDomain.getArticle();
		int articleSize = article.length();
		StringBuffer sb=new StringBuffer(article);
		
		String img = newsArticleDomain.getImg();
		String table = newsArticleDomain.getTable();
		String imgPos = newsArticleDomain.getImgPos();
		String tablePos = newsArticleDomain.getTablePos();
		
		
		//插入表格	
		List<String> tablePoss = HtmlUtil.getHTMLTagContent(tablePos, "pos");
		List<String> tables = HtmlUtil.getHTMLTagContent(table, "table");
		if (tables != null){
			if (tablePoss == null){
				for(int i=0;i<tables.size();i++){	
					sb.insert(articleSize,"<table>" + tables.get(i) + "</table>");	// -3 <p>	
					articleSize = sb.length();
				}
			}else {
				for (int i = 0; i < tablePoss.size(); i++){
					for(int j=0;j<articleSize;j++){
						j = article.indexOf(tablePoss.get(i),j);
						if(j<0) break;
						sb.insert(j-3,"<table>" +tables.get(i)+ "</table>");	// -3 <p>		
						articleSize = sb.length();
					}
				}
			}		
		}
		
		//插入图片
		List<String> imgPoss = HtmlUtil.getHTMLTagContent(imgPos, "pos");
		List<String> imgs = HtmlUtil.getHTMLTagContent(img, "img");
		
		if (imgs != null){
			if (imgPoss == null){
				
				for(int i=0;i<imgs.size();i++){
					System.out.println("articleSize " + articleSize);
					System.out.println("imgs.get(i) " + imgs.get(i));
					System.out.println("sb: " + sb.toString());
					sb.insert(articleSize,"<img>" +imgs.get(i)+ "</img>");	// -3 <p>
					articleSize = sb.length();
				}
			} else {
				for (int i = 0; i < imgPoss.size(); i++){
					for(int j=0;j<articleSize;j++){
						j = article.indexOf(imgPoss.get(i),j);
						if(j<0) break;
						sb.insert(j-3,"<img>" +imgs.get(i)+ "</img>");	// -3 <p>	
						articleSize = sb.length();
					}
				}			
			}
		}
			
		resArticle +=sb.toString();		
		System.out.println("sb  " + sb.toString());	
		resArticle += "</body></html>";
		return resArticle;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		convertArticle
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午10:58:26
	 *--------------------------------------------------------------------------------------*/
	public NewsArticleDomain convertArticleDomain(NewsArticleDomain newsArticleDomain){
		NewsArticleDomain resDomain = new NewsArticleDomain();	
		//
		String resArticle = "<html><head></head><body>";
		String article = newsArticleDomain.getArticle();
		String img = newsArticleDomain.getImg();
		String table = newsArticleDomain.getTable();
		String imgPos = newsArticleDomain.getImgPos();
		String tablePos = newsArticleDomain.getTablePos();
		List<String> tablePoss = HtmlUtil.getHTMLTagContent(tablePos, "pos");
		List<String> tables = HtmlUtil.getHTMLTagContent(table, "table");
		int articleSize = article.length();
		
		StringBuffer sb=new StringBuffer(article);
		for (int i = 0; i < tablePoss.size(); i++){
			for(int j=0;j<articleSize;j++){
				j = article.indexOf(tablePoss.get(i),j);
				if(j<0) break;
				sb.insert(j-3,tables.get(i));	// -3 <p>			
			}
		}
		resArticle +=sb.toString();
		System.out.println("sb  " + sb.toString());	
		resArticle += "</body></html>";
		resDomain.setArticle(resArticle);
		return resDomain;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildBreif
	 * Author:		1.0 created by lijiaxuan at 2015年7月8日 上午10:37:19
	 *--------------------------------------------------------------------------------------*/
	public String buildBreif(String article){
		article = HtmlUtil.delHTMLTag(HtmlUtil.subHTMLString(article, "p", 0, 100));
		//article = HtmlUtil.subHTMLString(article, "p", 0, 100);
		return article;
	}

}