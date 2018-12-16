package com.eyoubika.batch;
import com.eyoubika.common.BaseAction;
import com.eyoubika.spider.application.DepositSpiderAL;
import com.eyoubika.spider.application.NoticeSpiderAL;
import com.eyoubika.spider.application.SubscribeSpiderAL;
import com.eyoubika.system.application.SitemapAL;

/*==========================================================================================*
 * Description:	文章抓取执行批量。日终：是指次日凌晨1点。
 * Class:		ArticleBatch
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年9月18日 下午4:33:51
 *==========================================================================================*/
public class UtilBatch extends BaseAction{	
	private static final long serialVersionUID = 1L;
	private SitemapAL  sitemapAL;	
	
	public SitemapAL getSitemapAL() {
		return sitemapAL;
	}

	public void setSitemapAL(SitemapAL sitemapAL) {
		this.sitemapAL = sitemapAL;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void buildSitemap(){
		sitemapAL.buildSitemap();
	}
}