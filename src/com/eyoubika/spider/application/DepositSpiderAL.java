package com.eyoubika.spider.application;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.eyoubika.sbc.dao.SbcBasicDao;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.spider.web.VO.FetchVO;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.HtmlCleanUtil;
import com.eyoubika.util.HtmlUtil;
import com.eyoubika.common.BaseSpider;
import com.eyoubika.common.RedisDao;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.YbkException;
import com.eyoubika.info.domain.QuotationsDomain;
import com.eyoubika.spider.domain.*;
import com.eyoubika.spider.dao.*;
import com.eyoubika.util.*;

/*==========================================================================================*
 * Description:	抓取各个交易所的托管公告信息
 * Class:		DepositSpiderAL
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年10月7日 上午10:11:22
 *==========================================================================================*/
public class DepositSpiderAL extends BaseSpider {
	private DepositArticleDao depositArticleDao;
	public DepositArticleDao getDepositArticleDao() {
		return depositArticleDao;
	}
	public void setDepositArticleDao(DepositArticleDao depositArticleDao) {
		this.depositArticleDao = depositArticleDao;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	托管挂牌信息抓取入口
	 * Method:		fetchDepositNews
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 上午10:12:58
	 *--------------------------------------------------------------------------------------*/
	public void fetchDepositArticle(){	
		int produceTaskSleepTime = 200; //0.2s  
		int maxPoolSize = 20; 		// 线程池维护线程的最大数量
		int minPoolSize = 8;	 	// 线程池维护线程的最少数量
		long keepAliveTime = 3;		// 线程池维护线程所允许的空闲时间
		Map<String, String> exMap = new HashMap<String, String>();	//构造<交易所,抓取URL>映射表，然并卵？
		exMap.put(CommonVariables.EXCHANGE_NANJING, "http://www.zgqbyp.com/web/news/109.html");//南京文交所
		exMap.put(CommonVariables.EXCHANGE_NANFANG, "http://www.nfqbyp.com/infomation.html?newsTypeID=17558&newsType=%E6%89%98%E7%AE%A1%E5%85%AC%E5%91%8A"); //南方文交所
		exMap.put( CommonVariables.EXCHANGE_ZHONGNAN, "http://www.znypjy.com/a/xinxipilu/tuoguangonggao/");	//中南文交所
		exMap.put(CommonVariables.EXCHANGE_FULITE, "http://dzp.wjybk.com/?action-category-catid-126"); //福丽特
		exMap.put(CommonVariables.EXCHANGE_JINMAJIA, "http://qbyp.jinmajia.com/article/mtbd/qbyp/gggs/rksq/"); //金马甲
		exMap.put(CommonVariables.EXCHANGE_SHANGHAI, "http://www.shscce.com/html/shscce/zxgg/index_1.shtml");//上海
		exMap.put(CommonVariables.EXCHANGE_YIJIAO, "http://www.cacecybk.com.cn/web/news/news.jsp?parentid=44&classid=55");//艺交所	

		String[] marketIds = {CommonVariables.EXCHANGE_NANJING, CommonVariables.EXCHANGE_NANFANG, CommonVariables.EXCHANGE_ZHONGNAN,
				CommonVariables.EXCHANGE_FULITE,CommonVariables.EXCHANGE_JINMAJIA, CommonVariables.EXCHANGE_SHANGHAI, CommonVariables.EXCHANGE_YIJIAO };	
		//String[] marketIds = {CommonVariables.EXCHANGE_NANFANG};
		//构造线程池
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(minPoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
	                new ThreadPoolExecutor.DiscardOldestPolicy()){
            // 线程执行之前运行
            protected void beforeExecute(Thread t, Runnable r) {
            }
            // 线程执行之后运行
            protected void afterExecute(Runnable r, Throwable t) {
            }
            // 整个线程池停止之后
            protected void terminated() {
            }
        };
		int index = 0;
		int fetchListSize = marketIds.length;
		while (index < fetchListSize) {
			try	{		        	   	  
		        String exId = marketIds[index%fetchListSize];
		        threadPool.execute(new DepositFetcher( exId,  exMap.get(exId) , depositArticleDao)); //多线程抓取：每个交易所一个线程。      
		        Thread.sleep(produceTaskSleepTime); // 睡0.2秒，起来再抓。减轻服务器压力。
		        index++;
			} catch (Exception e){
				e.printStackTrace();
			}
		}	
	} 		
}

/*==========================================================================================*
 * Description:	抓取托管信息
 * Class:		DepositFetcher
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年10月7日 上午10:29:54
 *==========================================================================================*/
class DepositFetcher extends BaseSpider implements Runnable{
	private  String exId;	//交易所
	private String url;		//目标网页入口URL
	private DepositArticleDao depositArticleDao;	//数据库层
	public DepositFetcher(String exId, String url, DepositArticleDao depositArticleDao){
		this.exId = exId;	
		this.url = url;
		this.depositArticleDao= depositArticleDao;
	}
	//===================================== 南京文交所 =====================================//
	/*--------------------------------------------------------------------------------------*
	 * Description:	南京文交所：获取托管信息总页数
	 * Method:		nanjing001000Pager
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 上午11:17:56
	 *--------------------------------------------------------------------------------------*/
	private int nanjing001000Pager(Document doc){
		int res = 0;
		try {
			//根据网页接口，获取总页数
			String pageHtml = doc.select("div.fenye").text();
			pageHtml = CommonUtil.getNumFromString(pageHtml, null);
			res = Integer.parseInt(pageHtml.split(",")[1]);
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980011, YbkException.DESC980011);
		}
		return res;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	南京文交所：解析托管信息列表页
	 * Method:		nanjing001000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 上午11:18:43
	 *--------------------------------------------------------------------------------------*/
	private void nanjing001000Fetcher(){
		boolean fetchFlag = true;			//是否可抓标识
		String pageUrl = null;				//文章列表页URL
		Element pageEl = null;				//网页内容元素
		Elements articleEls = null;			//文章列表元素
		int articleElSize  = 0;				//文章列表个数
		Element link = null;				//文章超链接元素
		String articleUrl = null;			//文章URL
		String title = null;				//文章标题
		String webTime = null;				//文章网站时间
		ArticleDomain articleDomain = new ArticleDomain();	//文章模型
		host = CommonUtil.getHost(url);		//获取host	
		Document doc = fetchDocument(url);	//获取网页源码	
		String maxDate = depositArticleDao.findMaxDateByExId(exId);	//数据库最大文章网站时间
		int i = 0;
		int j = 0;
		if (CommonUtil.isStringNull(maxDate)) {
			maxDate = "";
		}
		int pageNum = nanjing001000Pager(doc); //获取最大页数
		for (i = 1 ; i <= pageNum && fetchFlag; i++){
		//for (i = 1 ; i <=  1 && fetchFlag; i++){
			//构造每一个托管信息列表页的URL
			if ( i == 1){
				pageUrl = "http://www.zgqbyp.com/web/news/109.html";
			} else {
				pageUrl = "http://www.zgqbyp.com/web/news/109_"+i+".html";
			}
			doc = fetchDocument(pageUrl);//获取网页源码
			
			pageEl = doc.select("div.ilist").first();//获取托管信息部分的源码
			articleEls = pageEl.select("li");
			articleElSize = articleEls.size();
			
			//解析获取每条托管信息
			for (j = 0; j < articleElSize && fetchFlag; j++){
			//for (j = 0; j < 1 && fetchFlag; j++){
				articleDomain.init();
				link = articleEls.get(j).getElementsByTag("a").first();
				articleUrl = link.attr("href");
				title = link.text().trim();	
				webTime = articleEls.get(j).select("span").text();
				articleUrl = this.buildUrl(articleUrl);//构造托管信息详情页的URL
				articleDomain.setExId(CommonVariables.EXCHANGE_NANJING);	//交易所
				articleDomain.setTitle(title);		//标题
				articleDomain.setWebTime(CommonUtil.extractDateFromString(webTime, ""));	//网站时间：可用于设定抓取起讫点	
				if (articleDomain.getWebTime().compareTo(maxDate) <= 0){	//如果，第一次抓时，当前日期小于等于数据库最大日期则跳过，以为已经抓取过。
					fetchFlag = false;
					continue;
				}
				articleDomain.setArticleUrl(articleUrl);//抓取的URL
				//System.out.println("articleDomain " + articleDomain.toString());
				//解析托管信息详情页并保存
				nanjing001000Saver(articleDomain);				
			}
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	南京文交所：解析托管信息详情页并保存
	 * Method:		nanjing001000Saver
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 上午11:18:55
	 *--------------------------------------------------------------------------------------*/
	private void nanjing001000Saver(ArticleDomain articleDomain){
		Document doc = fetchDocument(articleDomain.getArticleUrl());
		String issueTime = CommonUtil.removeCNChar(doc.select("div.neirong_note").text()).replace("：", "").trim();		
		issueTime = issueTime.split(" ")[0] + " " + issueTime.split(" ")[1];
		issueTime = issueTime.trim().substring(0, issueTime.length()-1).trim();//去掉最后一个字符
		articleDomain.setIssueTime(issueTime);//发布时间
		articleDomain.setFetchTime(CommonUtil.getNow());			 //抓取时间
		Element text = doc.select("div.neirong_text").first();
		
		String htmlSrc = HtmlCleanUtil.filter(text.toString()); 	//网页源码数据清洗：去除凌乱的标签和标签属性	
		htmlSrc =HtmlCleanUtil.removeTag(htmlSrc, "p", "微官网：");	//网页源码数据清洗：去除内容匹配指定信息的标签
		htmlSrc =HtmlCleanUtil.removeNullTag(htmlSrc);				//网页源码数据清洗：去除内容是空白的标签
		htmlSrc =HtmlCleanUtil.removeBlank(htmlSrc, CommonVariables.COMMON_LEVEL_02);	//网页源码数据清洗：去除多余的空白
		String html = HtmlUtil.buildInfoHtml(htmlSrc, articleDomain.getTitle());//构建网页
		articleDomain.setArticle(html);					//文章
		articleDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);	//状态：原始抓取
		articleDomain.setType(CommonVariables.NOTE_DEPOSIT);
		depositArticleDao.addDepositArticle(articleDomain);		//保存到数据库
	}
	//===================================== 南方交易所 =====================================//
	/*--------------------------------------------------------------------------------------*
	 * Description:	南方交易所：获取托管信息总页数
	 * Method:		nanfang002000Pager
	 * Author:		1.0 created by lijiaxuan at 2015年10月7日 上午10:31:22
	 *--------------------------------------------------------------------------------------*/
	private int nanfang002000Pager(Document doc){
		int res = 0;
		try {
			//根据网页接口，获取总页数
			String pageHtml = doc.select("div.page_num").text();
			pageHtml = CommonUtil.getNumFromString(pageHtml, null);
			res = Integer.parseInt(pageHtml.split(",")[2]);
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980011, YbkException.DESC980011);
		}
		return res;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	南方交易所：解析托管信息列表页
	 * Method:		nanfang002000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 上午10:33:53
	 *--------------------------------------------------------------------------------------*/
	private void nanfang002000Fetcher(){
		boolean fetchFlag = true;			//是否可抓标识
		String pageUrl = null;				//文章列表页URL
		Element pageEl = null;				//网页内容元素
		Elements articleEls = null;			//文章列表元素
		int articleElSize  = 0;				//文章列表个数
		Element link = null;				//文章超链接元素
		String articleUrl = null;			//文章URL
		String title = null;				//文章标题
		String webTime = null;				//文章网站时间
		ArticleDomain articleDomain = new ArticleDomain();	//文章模型
		host = CommonUtil.getHost(url);		//获取host	
		Document doc = fetchDocument(url);	//获取网页源码	
		String maxDate = depositArticleDao.findMaxDateByExId(exId);	//数据库最大文章网站时间
		int i = 0;
		int j = 0;
		if (CommonUtil.isStringNull(maxDate)) {
			maxDate = "";
		}
		doc = fetchDocument(url);	//获取网页源码
		int pageNum = nanfang002000Pager(doc); //获取最大页数
		for (i = 1 ; i <= pageNum && fetchFlag; i++){
		//for (i = 1 ; i <= 1 && fetchFlag; i++){
			//构造每一个托管信息列表页的URL
			pageUrl = "http://www.nfqbyp.com/infomation.html?pageIndex="+ i + "&newsTypeID=17558&newsType=%E6%89%98%E7%AE%A1%E5%85%AC%E5%91%8A";
			doc = fetchDocument(pageUrl);//获取网页源码
			pageEl = doc.select("div.cnews_content").first();//获取托管信息部分的源码
			articleEls = pageEl.select("li");
			articleElSize = articleEls.size();
			//解析获取每条托管信息
			for (j = 0; j < articleElSize && fetchFlag; j++){
			//for (j = 0; j < 5 && fetchFlag; j++){
				articleDomain.init();
				link = articleEls.get(j).getElementsByTag("a").first();
				articleUrl = link.attr("href");
				title = link.text().trim();	
				webTime = articleEls.get(j).select("span.date").text();
				articleUrl = this.buildUrl(articleUrl);//构造托管信息详情页的URL
				articleDomain.setExId(CommonVariables.EXCHANGE_NANFANG);	//交易所
				articleDomain.setTitle(title);		//标题
				articleDomain.setWebTime( CommonUtil.extractDateFromString(webTime, ""));	//网站时间：可用于设定抓取起讫点	
				if ( articleDomain.getWebTime().compareTo(maxDate) <= 0){	//如果，第一次抓时，当前日期小于等于数据库最大日期则跳过，以为已经抓取过。
					fetchFlag = false;
					continue;
				}
				articleDomain.setArticleUrl(articleUrl);//抓取的URL
				//System.out.println("articleDomain " + articleDomain.toString());
				//解析托管信息详情页并保存
				nanfang002000Saver(articleDomain);				
			}
		}
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	南方交易所：解析托管信息详情页并保存
	 * Method:		nanfang002000Saver
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 上午10:38:42
	 *--------------------------------------------------------------------------------------*/
	private void nanfang002000Saver(ArticleDomain articleDomain){
		Document doc = fetchDocument(articleDomain.getArticleUrl());
		String issueTime = CommonUtil.removeCNChar(doc.select("div.dnews_info").text().split("：")[0].trim());
		String splitTime[] = issueTime.split(" ");
		issueTime = CommonUtil.getFormatDate(splitTime[0], "-") + " " + splitTime[1];
		articleDomain.setIssueTime(issueTime);//发布时间
		articleDomain.setFetchTime(CommonUtil.getNow());			 //抓取时间
		Element text = doc.select("div.dnews_content").first();
		
		String htmlSrc = HtmlCleanUtil.filter(text.toString()); 	//网页源码数据清洗：去除凌乱的标签和标签属性
		htmlSrc =HtmlCleanUtil.removeNullTag(htmlSrc);				//网页源码数据清洗：去除内容是空白的标签
		htmlSrc =HtmlCleanUtil.removeBlank(htmlSrc, CommonVariables.COMMON_LEVEL_02);	//网页源码数据清洗：去除多余的空白
		String html = HtmlUtil.buildInfoHtml(htmlSrc, articleDomain.getTitle());//构建网页
		articleDomain.setArticle(html);					//文章
		articleDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);	//状态：原始抓取
		articleDomain.setType(CommonVariables.NOTE_DEPOSIT);
		depositArticleDao.addDepositArticle(articleDomain);		//保存到数据库
	}
	//===================================== 中南文交所 =====================================/
	/*--------------------------------------------------------------------------------------*
	 * Description:	中南文交所：获取托管信息总页数
	 * Method:		zhongnan003000Pager
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午3:12:34
	 *--------------------------------------------------------------------------------------*/
	private int zhongnan003000Pager(Document doc){
		int res = 0;
		try {
			//根据网页接口，获取总页数
			String pageHtml = doc.select("span.pageinfo").text();
			pageHtml = CommonUtil.getNumFromString(pageHtml, null);
			res = Integer.parseInt(pageHtml.split(",")[0]);
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980011, YbkException.DESC980011);
		}
		return res;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	中南文交所：解析托管信息列表页
	 * Method:		zhongnan003000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午3:12:45
	 *--------------------------------------------------------------------------------------*/
	private void zhongnan003000Fetcher(){
		boolean fetchFlag = true;			//是否可抓标识
		String pageUrl = null;				//文章列表页URL
		Element pageEl = null;				//网页内容元素
		Elements articleEls = null;			//文章列表元素
		int articleElSize  = 0;				//文章列表个数
		Element link = null;				//文章超链接元素
		String articleUrl = null;			//文章URL
		String title = null;				//文章标题
		String webTime = null;				//文章网站时间
		ArticleDomain articleDomain = new ArticleDomain();	//文章模型
		host = CommonUtil.getHost(url);		//获取host	
		Document doc = fetchDocument(url);	//获取网页源码	
		String maxDate = depositArticleDao.findMaxDateByExId(exId);	//数据库最大文章网站时间
		int i = 0;
		int j = 0;
		if (CommonUtil.isStringNull(maxDate)) {
			maxDate = "";
		}
		doc = fetchDocument(url);	//获取网页源码
		pageUrl = "";
		int pageNum = zhongnan003000Pager(doc); //获取最大页数
		for (i = 1 ; i <= pageNum && fetchFlag; i++){
		//for (i = 1 ; i <= 1 && fetchFlag; i++){
			//构造每一个托管信息列表页的URL
			pageUrl = "http://www.znypjy.com/a/xinxipilu/tuoguangonggao/list_10_"+ i +".html";
			doc = fetchDocument(pageUrl);//获取网页源码
			
			pageEl = doc.select("div.main_right").first();//获取托管信息部分的源码
			articleEls = pageEl.select("div.main_right_list_title");
			articleElSize = articleEls.size();
			
			//解析获取每条托管信息
			for (j = 0; j < articleElSize && fetchFlag; j++){
			//for (j = 0; j < 5 && fetchFlag; j++){
				articleDomain.init();
				link = articleEls.get(j).getElementsByTag("a").first();
				articleUrl = link.attr("href");
				title = link.text().trim();	
				webTime = articleEls.get(j).select("span").text();
				articleUrl = this.buildUrl(articleUrl);//构造托管信息详情页的URL
				articleDomain.setExId(CommonVariables.EXCHANGE_ZHONGNAN);	//交易所
				articleDomain.setTitle(title);		//标题
				articleDomain.setWebTime(CommonUtil.extractDateFromString(webTime, ""));	//网站时间：可用于设定抓取起讫点	
				if ( articleDomain.getWebTime().compareTo(maxDate) <= 0){	//如果，第一次抓时，当前日期小于等于数据库最大日期则跳过，以为已经抓取过。
					fetchFlag = false;
					continue;
				}
				articleDomain.setArticleUrl(articleUrl);//抓取的URL
				//System.out.println("articleDomain " + articleDomain.toString());
				//解析托管信息详情页并保存
				zhongnan003000Saver(articleDomain);				
			}
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	中南文交所：解析托管信息详情页并保存
	 * Method:		zhongnan003000Saver
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午3:12:53
	 *--------------------------------------------------------------------------------------*/
	private void zhongnan003000Saver(ArticleDomain articleDomain){
		Document doc = fetchDocument(articleDomain.getArticleUrl());
		String issueTime = CommonUtil.removeCNChar(doc.select("p.article_note").text()).replace("：", "").trim();		
		articleDomain.setIssueTime(issueTime);//发布时间
		articleDomain.setFetchTime(CommonUtil.getNow());			 //抓取时间
		Element text = doc.select("div.article_content").first();
		
		String htmlSrc = HtmlCleanUtil.filter(text.toString()); 	//网页源码数据清洗：去除凌乱的标签和标签属性	
		htmlSrc =HtmlCleanUtil.removeTag(htmlSrc, "p", 
				"微信公众号：|T4fMwBovxAtjIuUhl0LpHGHBmhYnPUibTQJ7BzicQ|官方微博：|NTUU29WbZVd31mhcj2GVz8mDhJsC8jUA|手机开户扫一扫：|HrvQdEuFPqMd1osiaFrxNdibTVMpDhsynnA6A|财富热线：");	//网页源码数据清洗：去除内容匹配指定信息的标签
		htmlSrc =HtmlCleanUtil.removeNullTag(htmlSrc);				//网页源码数据清洗：去除内容是空白的标签
		htmlSrc =HtmlCleanUtil.removeBlank(htmlSrc, CommonVariables.COMMON_LEVEL_02);	//网页源码数据清洗：去除多余的空白
		htmlSrc =HtmlUtil.getAbsSource(htmlSrc, CommonUtil.getHost(articleDomain.getArticleUrl())); 
		String html = HtmlUtil.buildInfoHtml(htmlSrc, articleDomain.getTitle());//构建网页
		articleDomain.setArticle(html);					//文章
		articleDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);	//状态：原始抓取
		articleDomain.setType(CommonVariables.NOTE_DEPOSIT);
		depositArticleDao.addDepositArticle(articleDomain);		//保存到数据库
	}
	//===================================== 福丽特交易所 =====================================/
	/*--------------------------------------------------------------------------------------*
	 * Description:	福丽特交易所：获取托管信息总页数
	 * Method:		fulite004000Pager
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午5:54:11
	 *--------------------------------------------------------------------------------------*/
	private int fulite004000Pager(Document doc){
		int res = 0;
		try {
			//根据网页接口，获取总页数
			String pageHtml = doc.select("div.pages").text();
			pageHtml = CommonUtil.getNumFromString(pageHtml, null);
			res = Integer.parseInt(pageHtml.substring(pageHtml.length()-1)); //最多只能取到前九页。
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980011, YbkException.DESC980011);
		}
		return res;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	福丽特交易所：解析托管信息列表页
	 * Method:		fulite004000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午5:54:30
	 *--------------------------------------------------------------------------------------*/
	private void fulite004000Fetcher(){
		boolean fetchFlag = true;			//是否可抓标识
		String pageUrl = null;				//文章列表页URL
		Element pageEl = null;				//网页内容元素
		Elements articleEls = null;			//文章列表元素
		int articleElSize  = 0;				//文章列表个数
		Element link = null;				//文章超链接元素
		String articleUrl = null;			//文章URL
		String title = null;				//文章标题
		String webTime = null;				//文章网站时间
		ArticleDomain articleDomain = new ArticleDomain();	//文章模型
		host = CommonUtil.getHost(url);		//获取host	
		Document doc = fetchDocument(url);	//获取网页源码	
		String maxDate = depositArticleDao.findMaxDateByExId(exId);	//数据库最大文章网站时间
		int i = 0;
		int j = 0;
		if (CommonUtil.isStringNull(maxDate)) {
			maxDate = "";
		}
		doc = fetchDocument(url);	//获取网页源码
		pageUrl = "";
		int pageNum = fulite004000Pager(doc); //获取最大页数
		for (i = 1 ; i <= pageNum && fetchFlag; i++){
		//for (i = 1 ; i <= 1 && fetchFlag; i++){
			//构造每一个托管信息列表页的URL
			pageUrl = "http://dzp.wjybk.com/?action-category-catid-126-page-" + i;
			doc = fetchDocument(pageUrl);//获取网页源码
			
			pageEl = doc.select("div#article").first();//获取托管信息部分的源码
			articleEls = pageEl.select("li");
			articleElSize = articleEls.size();
			
			//解析获取每条托管信息
			for (j = 0; j < articleElSize && fetchFlag; j++){
			//for (j = 0; j < 5 && fetchFlag; j++){
				articleDomain.init();
				link = articleEls.get(j).getElementsByTag("a").first();
				articleUrl = link.attr("href");
				title = link.text().trim();	
				webTime = articleEls.get(j).select("span.box_r").text();
				articleUrl = this.buildUrl(articleUrl);//构造托管信息详情页的URL
				articleDomain.setExId(CommonVariables.EXCHANGE_FULITE);	//交易所
				articleDomain.setTitle(title);		//标题
				articleDomain.setWebTime(CommonUtil.extractDateFromString(webTime, ""));	//网站时间：可用于设定抓取起讫点	
				if (articleDomain.getWebTime().compareTo(maxDate) <= 0){	//如果，第一次抓时，当前日期小于等于数据库最大日期则跳过，以为已经抓取过。
					fetchFlag = false;
					continue;
				}
				articleDomain.setArticleUrl(articleUrl);//抓取的URL
				//System.out.println("articleDomain " + articleDomain.toString());
				//解析托管信息详情页并保存
				fulite004000Saver(articleDomain);				
			}
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	福丽特交易所：解析托管信息详情页并保存
	 * Method:		fulite004000Saver
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午5:54:50
	 *--------------------------------------------------------------------------------------*/
	private void fulite004000Saver(ArticleDomain articleDomain){
		Document doc = fetchDocument(articleDomain.getArticleUrl());
		articleDomain.setIssueTime(CommonUtil.getFormatDate(articleDomain.getWebTime(), null));//发布时间
		articleDomain.setFetchTime(CommonUtil.getNow());			 //抓取时间
		Element text = doc.select("div#article_body").first();
		
		String htmlSrc = HtmlCleanUtil.filter(text.toString()); 	//网页源码数据清洗：去除凌乱的标签和标签属性	
		//htmlSrc = HtmlCleanUtil.removeTag(htmlSrc, "p", "");	//网页源码数据清洗：去除内容匹配指定信息的标签
		htmlSrc = HtmlCleanUtil.removeNullTag(htmlSrc);				//网页源码数据清洗：去除内容是空白的标签
		htmlSrc = HtmlCleanUtil.removeBlank(htmlSrc, CommonVariables.COMMON_LEVEL_02);	//网页源码数据清洗：去除多余的空白
		htmlSrc = HtmlUtil.stuffImgUrl(htmlSrc, host);
		String html = HtmlUtil.buildInfoHtml(htmlSrc, articleDomain.getTitle());//构建网页
		articleDomain.setArticle(html);					//文章
		articleDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);	//状态：原始抓取
		articleDomain.setType(CommonVariables.NOTE_DEPOSIT);
		depositArticleDao.addDepositArticle(articleDomain);		//保存到数据库
	}
	//===================================== 金马甲文交所 =====================================/
	/*--------------------------------------------------------------------------------------*
	 * Description:	金马甲文交所：获取托管信息总页数
	 * Method:		jinmajia005000Pager
	 * Author:		1.0 created by lijiaxuan at 2015年10月9日 上午10:35:17
	 *--------------------------------------------------------------------------------------*/
	private int jinmajia005000Pager(Document doc){
		int res = 0;
		try {
			//根据网页接口，获取总页数	
			String pageHtml = doc.select("center").text();
			pageHtml = CommonUtil.getNumFromString(pageHtml, null);
			String splitStrs[] = pageHtml.split(",");
			res = Integer.parseInt(splitStrs[splitStrs.length-1]);
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980011, YbkException.DESC980011);
		}
		return res;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	金马甲文交所：解析托管信息列表页
	 * Method:		jinmajia005000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年10月9日 上午10:35:27
	 *--------------------------------------------------------------------------------------*/
	private void jinmajia005000Fetcher(){
		boolean fetchFlag = true;			//是否可抓标识
		String pageUrl = null;				//文章列表页URL
		Element pageEl = null;				//网页内容元素
		Elements articleEls = null;			//文章列表元素
		int articleElSize  = 0;				//文章列表个数
		Element link = null;				//文章超链接元素
		String articleUrl = null;			//文章URL
		String title = null;				//文章标题
		String webTime = null;				//文章网站时间
		ArticleDomain articleDomain = new ArticleDomain();	//文章模型
		host = CommonUtil.getHost(url);		//获取host	
		Document doc = fetchDocument(url);	//获取网页源码	
		String maxDate = depositArticleDao.findMaxDateByExId(exId);	//数据库最大文章网站时间
		int i = 0;
		int j = 0;
		if (CommonUtil.isStringNull(maxDate)) {
			maxDate = "";
		}
		int pageNum = jinmajia005000Pager(doc); //获取最大页数
		for (i = 1 ; i <= pageNum && fetchFlag; i++){
		//for (i = 1 ; i <=  1 && fetchFlag; i++){
			//构造每一个托管信息列表页的URL
			if ( i == 1){
				pageUrl = "http://qbyp.jinmajia.com/article/mtbd/qbyp/gggs/rksq/index.shtml";
			} else {
				pageUrl = "http://qbyp.jinmajia.com/article/mtbd/qbyp/gggs/rksq/index.shtml?" + i;
			}
			doc = fetchDocument(pageUrl);//获取网页源码
			
			pageEl = doc.select("ul.article").first();//获取托管信息部分的源码
			articleEls = pageEl.select("li");
			articleElSize = articleEls.size();
			
			//解析获取每条托管信息
			for (j = 0; j < articleElSize && fetchFlag; j++){
			//for (j = 0; j < 1 && fetchFlag; j++){
				articleDomain.init();
				if (CommonUtil.isStringNull(articleEls.get(j).getElementsByTag("a").toString())){
					continue;
				}
				link = articleEls.get(j).getElementsByTag("a").first();
				
				articleUrl = link.attr("href");
				title = link.text().trim();	
				webTime = articleEls.get(j).select("span.timedefault").text();
				articleUrl = this.buildUrl(articleUrl);//构造托管信息详情页的URL
				articleDomain.setExId(exId);	//交易所
				articleDomain.setTitle(title);		//标题
				articleDomain.setWebTime(CommonUtil.extractDateFromString(webTime, ""));	//网站时间：可用于设定抓取起讫点	
				if (articleDomain.getWebTime().compareTo(maxDate) <= 0){	//如果，第一次抓时，当前日期小于等于数据库最大日期则跳过，以为已经抓取过。
					fetchFlag = false;
					continue;
				}
				articleDomain.setArticleUrl(articleUrl);//抓取的URL
				//System.out.println("articleDomain " + articleDomain.toString());
				//解析托管信息详情页并保存
				jinmajia005000Saver(articleDomain);				
			}
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	金马甲文交所：解析托管信息详情页并保存
	 * Method:		jinmajia005000Saver
	 * Author:		1.0 created by lijiaxuan at 2015年10月9日 上午10:35:44
	 *--------------------------------------------------------------------------------------*/
	private void jinmajia005000Saver(ArticleDomain articleDomain){
		Document doc = fetchDocument(articleDomain.getArticleUrl());
		articleDomain.setIssueTime(CommonUtil.getFormatDate(articleDomain.getWebTime(), null));//发布时间
		articleDomain.setFetchTime(CommonUtil.getNow());			 //抓取时间
		Element text = doc.select("td.content").first();
		
		String htmlSrc = HtmlCleanUtil.filter(text.toString()); 	//网页源码数据清洗：去除凌乱的标签和标签属性	
		htmlSrc =HtmlCleanUtil.removeTag(htmlSrc, "p", "微官网：");	//网页源码数据清洗：去除内容匹配指定信息的标签
		htmlSrc =HtmlCleanUtil.removeNullTag(htmlSrc);				//网页源码数据清洗：去除内容是空白的标签
		htmlSrc =HtmlCleanUtil.removeBlank(htmlSrc, CommonVariables.COMMON_LEVEL_02);	//网页源码数据清洗：去除多余的空白
		String html = HtmlUtil.buildInfoHtml(htmlSrc, articleDomain.getTitle());//构建网页
		articleDomain.setArticle(html);					//文章
		articleDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);	//状态：原始抓取
		articleDomain.setType(CommonVariables.NOTE_DEPOSIT);
		depositArticleDao.addDepositArticle(articleDomain);		//保存到数据库
	}
	//===================================== 上海文交所 =====================================/
	/*--------------------------------------------------------------------------------------*
	 * Description:	上海文交所：获取托管信息总页数
	 * Method:		shanghai007000Pager
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午6:32:47
	 *--------------------------------------------------------------------------------------*/
	private int shanghai007000Pager(Document doc){
		int res = 0;
		try {
			//根据网页接口，获取总页数	
			String pageHtml = doc.select("div.pages").text();
			pageHtml = CommonUtil.getNumFromString(pageHtml, null);
			String splitStrs[] = pageHtml.split(",");
			res = Integer.parseInt(splitStrs[splitStrs.length-1]);
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980011, YbkException.DESC980011);
		}
		return res;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	上海文交所：解析托管信息列表页
	 * Method:		shanghai007000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午6:33:19
	 *--------------------------------------------------------------------------------------*/
	private void shanghai007000Fetcher(){
		boolean fetchFlag = true;			//是否可抓标识
		String pageUrl = null;				//文章列表页URL
		Element pageEl = null;				//网页内容元素
		Elements articleEls = null;			//文章列表元素
		int articleElSize  = 0;				//文章列表个数
		Element link = null;				//文章超链接元素
		String articleUrl = null;			//文章URL
		String title = null;				//文章标题
		String webTime = null;				//文章网站时间
		ArticleDomain articleDomain = new ArticleDomain();	//文章模型
		host = CommonUtil.getHost(url);		//获取host	
		Document doc = fetchDocument(url);	//获取网页源码	
		String maxDate = depositArticleDao.findMaxDateByExId(exId);	//数据库最大文章网站时间
		int i = 0;
		int j = 0;
		if (CommonUtil.isStringNull(maxDate)) {
			maxDate = "";
		}
		doc = fetchDocument(url);	//获取网页源码
		int pageNum = shanghai007000Pager(doc); //获取最大页数
		for (i = 1 ; i <= pageNum && fetchFlag; i++){
		//for (i = 1 ; i <= 1 && fetchFlag; i++){
			//构造每一个托管信息列表页的URL
			pageUrl = "http://www.shscce.com/html/shscce/zxgg/index_"+i+".shtml";
			doc = fetchDocument(pageUrl);//获取网页源码
			
			pageEl = doc.select("div.list").first();//获取托管信息部分的源码
			articleEls = pageEl.select("li");
			articleElSize = articleEls.size();
			
			//解析获取每条托管信息
			for (j = 0; j < articleElSize && fetchFlag; j++){
			//for (j = 0; j < 5 && fetchFlag ; j++){
				articleDomain.init();
				link = articleEls.get(j).getElementsByTag("a").first();
				articleUrl = link.attr("href");
				title = link.attr("title").trim();	
				if (!title.contains("入库")){
					continue;
				}
				webTime = articleEls.get(j).select("span").text();
				articleUrl = this.buildUrl(articleUrl);//构造托管信息详情页的URL
				articleDomain.setExId(CommonVariables.EXCHANGE_SHANGHAI);	//交易所	
				articleDomain.setTitle(title);		//标题	
				articleDomain.setWebTime(CommonUtil.extractDateFromString(webTime, ""));	//网站时间：可用于设定抓取起讫点
				//System.out.println("@@@ " +exId + " " + i + " "+ j + " "+ articleDomain.getWebTime() + " "+ maxDate + " ");
				if (articleDomain.getWebTime().compareTo(maxDate) <= 0){	//如果，第一次抓时，当前日期小于等于数据库最大日期则跳过，以为已经抓取过。
					fetchFlag = false;
					continue;
				}
				
				articleDomain.setArticleUrl(articleUrl);//抓取的URL
				//System.out.println("articleDomain " + articleDomain.toString());
				//解析托管信息详情页并保存
				shanghai007000Saver(articleDomain);				
			}
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	上海文交所：解析托管信息详情页并保存
	 * Method:		shanghai007000Saver
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午6:33:37
	 *--------------------------------------------------------------------------------------*/
	private void shanghai007000Saver(ArticleDomain articleDomain){
		Document doc = fetchDocument(articleDomain.getArticleUrl());
		String issueTime = CommonUtil.removeCNChar(doc.select("div.timeinfo").text()).replace("：", "").trim();		
		articleDomain.setIssueTime(issueTime);//发布时间
		articleDomain.setFetchTime(CommonUtil.getNow());			 //抓取时间
		Element text = doc.select("div[style=font-size:14px;line-height:30px]").first();
		
		String htmlSrc = HtmlCleanUtil.filter(text.toString()); 	//网页源码数据清洗：去除凌乱的标签和标签属性	
		//htmlSrc = HtmlCleanUtil.removeTag(htmlSrc, "p", "");	//网页源码数据清洗：去除内容匹配指定信息的标签
		htmlSrc = HtmlCleanUtil.removeNullTag(htmlSrc);				//网页源码数据清洗：去除内容是空白的标签
		htmlSrc = HtmlCleanUtil.removeBlank(htmlSrc, CommonVariables.COMMON_LEVEL_02);	//网页源码数据清洗：去除多余的空白
		htmlSrc = HtmlUtil.stuffImgUrl(htmlSrc, host);
		String html = HtmlUtil.buildInfoHtml(htmlSrc, articleDomain.getTitle());//构建网页
		articleDomain.setArticle(html);					//文章
		articleDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);	//状态：原始抓取
		articleDomain.setType(CommonVariables.NOTE_DEPOSIT);
		depositArticleDao.addDepositArticle(articleDomain);		//保存到数据库
	}
	//===================================== 艺交所 =====================================/
	/*--------------------------------------------------------------------------------------*
	 * Description:	艺交所：获取托管信息总页数
	 * Method:		yijiao010000Pager
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午6:46:33
	 *--------------------------------------------------------------------------------------*/
	private int yijiao010000Pager(Document doc){
		int res = 0;
		try {
			//根据网页接口，获取总页数	
			String pageHtml = doc.select("div.page").text();
			pageHtml = CommonUtil.getNumFromString(pageHtml, null);
			String splitStrs[] = pageHtml.split(",");
			res = Integer.parseInt(splitStrs[splitStrs.length-1]);
		} catch (Exception e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE980011, YbkException.DESC980011);
		}
		return res;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	艺交所：解析托管信息列表页
	 * Method:		yijiao010000Fetcher
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午6:46:41
	 *--------------------------------------------------------------------------------------*/
	private void yijiao010000Fetcher(){
		boolean fetchFlag = true;			//是否可抓标识
		String pageUrl = null;				//文章列表页URL
		Element pageEl = null;				//网页内容元素
		Elements articleEls = null;			//文章列表元素
		int articleElSize  = 0;				//文章列表个数
		Element link = null;				//文章超链接元素
		String articleUrl = null;			//文章URL
		String title = null;				//文章标题
		String webTime = null;				//文章网站时间
		ArticleDomain articleDomain = new ArticleDomain();	//文章模型
		host = CommonUtil.getHost(url);		//获取host	
		Document doc = fetchDocument(url);	//获取网页源码	
		String maxDate = depositArticleDao.findMaxDateByExId(exId);	//数据库最大文章网站时间
		int i = 0;
		int j = 0;
		if (CommonUtil.isStringNull(maxDate)) {
			maxDate = "";
		}
		doc = fetchDocument(url);	//获取网页源码
		int pageNum = yijiao010000Pager(doc); //获取最大页数
		for (i = 1 ; i <= pageNum && fetchFlag; i++){
		//for (i = 1 ; i <= 1 && fetchFlag; i++){
			//构造每一个托管信息列表页的URL
			pageUrl = "http://www.cacecybk.com.cn/web/news/news.jsp?parentid=44&classid=55";
			doc = fetchDocument(pageUrl);//获取网页源码	
			pageEl = doc.select("div.st_rightbor").first().select("div.list").first();//获取托管信息部分的源码
			//获取网站时间
			String dateList = "";
			Elements dateEls = pageEl.select("td[width=50].line");
			for  (j = 0; j < dateEls.size(); j++){
				if (j == 0) {
					dateList += CommonUtil.getNowYear() + "-" +  dateEls.get(j).text();
				} else {
					dateList += "," + CommonUtil.getNowYear() + "-" +  dateEls.get(j).text();
				}
			}	
			
			articleEls = pageEl.select("a");
			articleElSize = articleEls.size();
			String[] dateArray = dateList.split(",");
			//解析获取每条托管信息
			for (j = 0; j < articleElSize && fetchFlag; j++){
			//for (j = 0; j < 5  && fetchFlag; j++){
				articleDomain.init();
				link = articleEls.get(j);
				articleUrl = link.attr("href");
				title = link.attr("title").trim();	
				webTime = articleEls.get(j).select("span").text();
				articleUrl = this.buildUrl(articleUrl);//构造托管信息详情页的URL
				articleDomain.setExId(CommonVariables.EXCHANGE_YIJIAO);	//交易所
				articleDomain.setTitle(title);		//标题
				articleDomain.setWebTime(CommonUtil.extractDateFromString(dateArray[j], ""));	//网站时间：可用于设定抓取起讫点	
				//年份不一样，那么需要做跨年处理
				if (! articleDomain.getWebTime().substring(0, 4).equals(maxDate.substring(0, 4))){
					//根据月份差判断是否继续:月份不同，表示，该日是次年新月，应当继续抓取，否则跳出循环
					if (articleDomain.getWebTime().substring(4, 6).equals(maxDate.substring(4, 6))){
						fetchFlag = false;
						continue;
					}
				} else if (articleDomain.getWebTime().compareTo(maxDate) <= 0){	//如果，第一次抓时，当前日期小于等于数据库最大日期则跳过，以为已经抓取过。
					fetchFlag = false;
					continue;
				}
				articleDomain.setArticleUrl(articleUrl);//抓取的URL
				//System.out.println("articleDomain " + articleDomain.toString());
				//解析托管信息详情页并保存
				yijiao010000Saver(articleDomain);				
			}
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	艺交所：解析托管信息详情页并保存
	 * Method:		yijiao010000Saver
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午6:47:01
	 *--------------------------------------------------------------------------------------*/
	private void yijiao010000Saver(ArticleDomain articleDomain){
		Document doc = fetchDocument(articleDomain.getArticleUrl());
		articleDomain.setIssueTime(CommonUtil.getFormatDate(articleDomain.getWebTime(), null));//发布时间
		articleDomain.setFetchTime(CommonUtil.getNow());			 //抓取时间
		Element text = doc.select("div.news_w").first();
		
		String htmlSrc = HtmlCleanUtil.filter(text.toString()); 	//网页源码数据清洗：去除凌乱的标签和标签属性	
		htmlSrc = HtmlCleanUtil.removeTag(htmlSrc, "p", "官网：|images/gfwx\\.jpg");	//网页源码数据清洗：去除内容匹配指定信息的标签
		htmlSrc = HtmlCleanUtil.removeNullTag(htmlSrc);				//网页源码数据清洗：去除内容是空白的标签
		htmlSrc = HtmlCleanUtil.removeBlank(htmlSrc, CommonVariables.COMMON_LEVEL_02);	//网页源码数据清洗：去除多余的空白
		htmlSrc = HtmlUtil.stuffImgUrl(htmlSrc, host);
		String html = HtmlUtil.buildInfoHtml(htmlSrc, articleDomain.getTitle());//构建网页
		articleDomain.setArticle(html);					//文章
		articleDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);	//状态：原始抓取
		articleDomain.setType(CommonVariables.NOTE_DEPOSIT);
		depositArticleDao.addDepositArticle(articleDomain);		//保存到数据库
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	线程的执行入口
	 * Method:		nanfang002000Saver
	 * Author:		1.0 created by lijiaxuan at 2015年10月7日 上午10:38:42
	 *--------------------------------------------------------------------------------------*/
	public void run()
    {
		switch (this.exId){	
			case CommonVariables.EXCHANGE_NANJING: {	//南京文交所
				nanjing001000Fetcher();		
				break;
			}
			case CommonVariables.EXCHANGE_NANFANG: {	//南方文交所
				nanfang002000Fetcher();		
				break;
			}
			case CommonVariables.EXCHANGE_ZHONGNAN: {	//中南文交所
				zhongnan003000Fetcher();		
				break;
			}
			case CommonVariables.EXCHANGE_FULITE: {		//福利特
				fulite004000Fetcher();		
				break;
			}
			case CommonVariables.EXCHANGE_JINMAJIA: {	//金马甲
				jinmajia005000Fetcher();		
				break;
			}
			case CommonVariables.EXCHANGE_SHANGHAI: {	//上海
				shanghai007000Fetcher();	
				break;
			}
			case CommonVariables.EXCHANGE_YIJIAO: {		//艺交所
				yijiao010000Fetcher();	
				break;
			}
			default: {		

				break;
			}				
		}

    }
}
