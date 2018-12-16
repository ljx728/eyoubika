package com.eyoubika.info.application;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ContentUtil;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.spider.dao.NewsArticleDao;
import com.eyoubika.spider.domain.NewsArticleDomain;
import com.eyoubika.spider.domain.NewsBasicDomain;
import com.eyoubika.common.PageInfo;
/*==========================================================================================*
 * Description:	新闻模块
 * Class:		NewsAL
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-6-27 10:19:52
 *==========================================================================================*/
public class NewsAL  {
	private NewsArticleDao newsArticleDao;
	
	public NewsArticleDao getNewsArticleDao() {
		return newsArticleDao;
	}


	public void setNewsArticleDao(NewsArticleDao newsArticleDao) {
		this.newsArticleDao = newsArticleDao;
	}


	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getQuotations
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午10:45:45
	 *--------------------------------------------------------------------------------------*/
	public NewsArticleDomain  getNews(Integer newsId){
		System.out.println("0  newsAL getNewsInfo");	
		return newsArticleDao.findNewsArticle(newsId);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNewsByParams
	 * Author:		1.0 created by lijiaxuan at 2015年6月29日 下午12:31:10
	 *--------------------------------------------------------------------------------------*/
	public List<NewsArticleDomain>  getNewsByParams(NewsArticleDomain newsArticleDomain){
		System.out.println("0  newsAL getNewsInfo");		
		return newsArticleDao.queryNewsArticle(newsArticleDomain);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNewsList
	 * Author:		1.0 created by lijiaxuan at 2015年6月29日 下午12:31:13
	 *--------------------------------------------------------------------------------------*/
	public PageInfo getNewsList(NewsBasicDomain newsBasicDomain, PageInfo pageInfo){
		System.out.println("0  newsAL getNewsList");		
		return newsArticleDao.queryNewsListInPage(newsBasicDomain, pageInfo);
	}
}