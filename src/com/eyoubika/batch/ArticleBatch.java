package com.eyoubika.batch;
import com.eyoubika.common.BaseAction;
import com.eyoubika.spider.application.DepositSpiderAL;
import com.eyoubika.spider.application.NoticeSpiderAL;
import com.eyoubika.spider.application.SubscribeSpiderAL;

/*==========================================================================================*
 * Description:	文章抓取执行批量。日终：是指次日凌晨1点。
 * Class:		ArticleBatch
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年9月18日 下午4:33:51
 *==========================================================================================*/
public class ArticleBatch extends BaseAction{	
	private static final long serialVersionUID = 1L;
	private SubscribeSpiderAL  subscribeSpiderAL;	
	private DepositSpiderAL  depositSpiderAL;	
	private NoticeSpiderAL  noticeSpiderAL;
	
	public NoticeSpiderAL getNoticeSpiderAL() {
		return noticeSpiderAL;
	}
	public void setNoticeSpiderAL(NoticeSpiderAL noticeSpiderAL) {
		this.noticeSpiderAL = noticeSpiderAL;
	}
	public SubscribeSpiderAL getSubscribeSpiderAL() {
		return subscribeSpiderAL;
	}
	public void setSubscribeSpiderAL(SubscribeSpiderAL subscribeSpiderAL) {
		this.subscribeSpiderAL = subscribeSpiderAL;
	}
	
	public DepositSpiderAL getDepositSpiderAL() {
		return depositSpiderAL;
	}
	public void setDepositSpiderAL(DepositSpiderAL depositSpiderAL) {
		this.depositSpiderAL = depositSpiderAL;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取新品申购文章
	 * Method:		fetchSubscribeNews
	 * Author:		1.0 created by lijiaxuan at 2015年10月7日 上午10:50:10
	 *--------------------------------------------------------------------------------------*/
	public void fetchSubscribeArticle(){
		subscribeSpiderAL.fetchSubscribeArticle();		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取新品托管文章
	 * Method:		fetchDepositArticle
	 * Author:		1.0 created by lijiaxuan at 2015年10月7日 上午11:20:34
	 *--------------------------------------------------------------------------------------*/
	public void fetchDepositArticle(){
		depositSpiderAL.fetchDepositArticle();		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取通知类文章（行业新闻等等）
	 * Method:		fetchNoticeArticle
	 * Author:		1.0 created by lijiaxuan at  2015年10月7日 下午4:32:05
	 *--------------------------------------------------------------------------------------*/
	public void fetchNoticeArticle(){
		noticeSpiderAL.fetchNoticeArticle();		
	}
	
}