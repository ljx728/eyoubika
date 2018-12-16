package com.eyoubika.info.application;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.spider.dao.DepositArticleDao;
import com.eyoubika.spider.dao.NoticeArticleDao;
import com.eyoubika.spider.dao.SubscribeArticleDao;
import com.eyoubika.spider.domain.ArticleDomain;
import com.eyoubika.common.PageInfo;

/*==========================================================================================*
 * Description:	文章信息类
 * Class:		ArticleAL
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年6月27日 下午11:59:39
 *==========================================================================================*/
public class ArticleAL {
	private SubscribeArticleDao subscribeArticleDao;
	private NoticeArticleDao noticeArticleDao;
	private DepositArticleDao depositArticleDao;

	public SubscribeArticleDao getSubscribeArticleDao() {
		return subscribeArticleDao;
	}

	public void setSubscribeArticleDao(SubscribeArticleDao subscribeArticleDao) {
		this.subscribeArticleDao = subscribeArticleDao;
	}

	public NoticeArticleDao getNoticeArticleDao() {
		return noticeArticleDao;
	}

	public void setNoticeArticleDao(NoticeArticleDao noticeArticleDao) {
		this.noticeArticleDao = noticeArticleDao;
	}

	public DepositArticleDao getDepositArticleDao() {
		return depositArticleDao;
	}

	public void setDepositArticleDao(DepositArticleDao depositArticleDao) {
		this.depositArticleDao = depositArticleDao;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	根据文章ID获取文章
	 * Method:		getArticle
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午10:45:45
	 *--------------------------------------------------------------------------------------*/
	public ArticleDomain getArticle(Integer articleId, String type) {
		ArticleDomain res = null;
		switch (type) {
		case CommonVariables.NOTE_SUBSCRIBE: {
			res = subscribeArticleDao.findSubscribeArticle(articleId);
			break;
		}
		case CommonVariables.NOTE_NOTICE: {
			res = noticeArticleDao.findNoticeArticle(articleId);
			break;
		}
		case CommonVariables.NOTE_DEPOSIT: {
			res = depositArticleDao.findDepositArticle(articleId);
			break;
		}
		}
		return res;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	获取文章列表
	 * Method:		getArticleList
	 * Author:		1.0 created by lijiaxuan at 2015年6月29日 下午12:31:13
	 *--------------------------------------------------------------------------------------*/
	public PageInfo getArticleList(ArticleDomain articleDomain,
			PageInfo pageInfo) {
		PageInfo res = null;
		switch (articleDomain.getType()) {
		case CommonVariables.NOTE_SUBSCRIBE: {
			res = subscribeArticleDao.querySubscribeArticleListInPage(
					articleDomain, pageInfo);
			break;
		}
		case CommonVariables.NOTE_NOTICE: {
			res = noticeArticleDao.queryNoticeArticleListInPage(articleDomain,
					pageInfo);
			break;
		}
		case CommonVariables.NOTE_DEPOSIT: {
			res = depositArticleDao.queryDepositArticleListInPage(
					articleDomain, pageInfo);
			break;
		}
		}
		return res;
	}
}