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
import com.eyoubika.info.web.VO.ArticleVO;
import com.eyoubika.info.web.VO.NewsVO;
import com.eyoubika.spider.domain.ArticleDomain;
import com.eyoubika.spider.domain.NewsArticleDomain;
import com.eyoubika.spider.domain.NewsBasicDomain;
import com.eyoubika.spider.domain.NoticeArticleDomain;
import com.eyoubika.spider.domain.ArticleDomain;
import com.eyoubika.info.service.IArticleService;
import com.eyoubika.info.web.VO.ArticleVO;
import com.eyoubika.info.application.ArticleAL;
import com.eyoubika.info.application.SubscribeArticleAL;
import com.eyoubika.common.PageInfo;
/*==========================================================================================*
 * Description:	定义了用户服务的接口实现
 * Class:		ArticleServiceImpl
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-6-27 10:11:22
 *==========================================================================================*/
public class ArticleServiceImpl extends BaseService implements IArticleService {
	private ArticleDomain articleDomain;
	private ArticleAL  articleAL;

	//private DepositArticleDomain depositArticleDomain;
	//private DepositArticleAL  depositArticleAL;

	
	
	public ArticleDomain getArticleDomain() {
		return articleDomain;
	}

	public void setArticleDomain(
			ArticleDomain articleDomain) {
		this.articleDomain = articleDomain;
	}


	public ArticleAL getArticleAL() {
		return articleAL;
	}

	public void setArticleAL(ArticleAL articleAL) {
		this.articleAL = articleAL;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNewsList
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午10:18:46
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getArticleList(ArticleVO articleVO, PageInfo pageInfo){	
		//NewsBasicDomain newsBasicDomain = new NewsBasicDomain();
		Map<String, Object> params = new HashMap<String, Object>();
		articleDomain.init();
		ConverterUtil.VOToDomain(articleVO, articleDomain);
		PageInfo res = articleAL.getArticleList(articleDomain, pageInfo);	
		List<ArticleDomain>  listDomains = (List<ArticleDomain>) res.getResult();// = newsAL.getNewsList(newsBasicDomain, pageInfo);			
		for (int i = 0 ; i < listDomains.size(); i++){	
			//生成简介
			listDomains.get(i).setArticle(buildBreif(listDomains.get(i).getArticle()));		
		}
		params.put("size", res.getTotalNum());
		params.put("next", res.getNext());
		params.put("list", listDomains);		
		
		return this.buildRetData(articleVO.getUserId(), articleVO.getToken(), params);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNews
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午12:32:41
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getArticle(ArticleVO articleVO){	
		//Object ob= null;
		ArticleDomain ob = articleAL.getArticle(Integer.parseInt(articleVO.getArticleId()), articleVO.getType());			
		return this.buildRetData(articleVO.getUserId(), articleVO.getToken(), ob);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildBreif
	 * Author:		1.0 created by lijiaxuan at 2015年7月8日 上午10:37:19
	 *--------------------------------------------------------------------------------------*/
	public String buildBreif(String article){
		article = HtmlUtil.delHTMLTag(HtmlUtil.subHTMLString(article, "p", 0, CommonVariables.ARTICLE_BRIEF_MAX));
		System.out.println("article " + article) ;
		//article = HtmlUtil.subHTMLString(article, "p", 0, 100);
		return article;
	}

}