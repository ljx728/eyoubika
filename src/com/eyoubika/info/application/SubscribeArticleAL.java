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
import com.eyoubika.spider.dao.SubscribeArticleDao;
import com.eyoubika.spider.domain.NewsArticleDomain;
import com.eyoubika.spider.domain.NewsBasicDomain;
import com.eyoubika.spider.domain.ArticleDomain;
import com.eyoubika.common.PageInfo;

/*==========================================================================================*
 * Description:	TODO
 * Class:		SubscribeArticleAL
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年10月7日 下午11:59:39
 *==========================================================================================*/
public class SubscribeArticleAL  {
	private SubscribeArticleDao subscribeArticleDao;
	public SubscribeArticleDao getSubscribeArticleDao() {
		return subscribeArticleDao;
	}

	public void setSubscribeArticleDao(SubscribeArticleDao subscribeArticleDao) {
		this.subscribeArticleDao = subscribeArticleDao;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getQuotations
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午10:45:45
	 *--------------------------------------------------------------------------------------*/
	public ArticleDomain  getSubscribeArticle(Integer articleId){
		return subscribeArticleDao.findSubscribeArticle(articleId);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNewsList
	 * Author:		1.0 created by lijiaxuan at 2015年6月29日 下午12:31:13
	 *--------------------------------------------------------------------------------------*/
	public PageInfo getSubscribeArticleList(ArticleDomain articleDomain, PageInfo pageInfo){		
		return subscribeArticleDao.querySubscribeArticleListInPage(articleDomain, pageInfo);
	}
}