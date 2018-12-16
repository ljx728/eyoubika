package com.eyoubika.spider.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.eyoubika.util.CommonUtil;
import com.eyoubika.common.BaseSpider;
import com.eyoubika.spider.service.INjwjsSpiderService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;  
import java.net.MalformedURLException;
import java.net.URL;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.spider.domain.*;
import com.eyoubika.spider.dao.*;

/*==========================================================================================*
 * Description:	抓取南京文交所数据的小蜘蛛哈哈
 * Class:		NjwjsSpiderServiceImpl
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-28 15:26:18
 *==========================================================================================*/
public class ZgqbypSpiderServiceImpl extends BaseSpider implements INjwjsSpiderService{

	private Map<String, String> urls;
	private NewsDomain newsDomain;						//新闻
	private AnnouncementDomain announcementDomain;		//公告
	private BreedBasicDomain breedBasicDomain;			//品种基本信息
	private BreedArticleDomain breedArticleDomain;		//品种文章
	private NewsDao newsDao;
	//private AriticleTableDomain ariticleTableDomain;	//文章表格
	
	public Map<String, String> getUrls() {
		return urls;
	}

	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public void setUrls(Map<String, String> urls) {
		this.urls = urls;
	}

	public NewsDomain getNewsDomain() {
		return newsDomain;
	}

	public void setNewsDomain(NewsDomain newsDomain) {
		this.newsDomain = newsDomain;
	}

	public AnnouncementDomain getAnnouncementDomain() {
		return announcementDomain;
	}

	public void setAnnouncementDomain(AnnouncementDomain announcementDomain) {
		this.announcementDomain = announcementDomain;
	}

	public BreedBasicDomain getBreedBasicDomain() {
		return breedBasicDomain;
	}

	public void setBreedBasicDomain(BreedBasicDomain breedBasicDomain) {
		this.breedBasicDomain = breedBasicDomain;
	}

	public BreedArticleDomain getBreedArticleDomain() {
		return breedArticleDomain;
	}

	public void setBreedArticleDomain(BreedArticleDomain breedArticleDomain) {
		this.breedArticleDomain = breedArticleDomain;
	}

	public ZgqbypSpiderServiceImpl() {
		htmlDir = "/Users/lijiaxuan/ljx728/html/njwjs/";
		host = "http://www.njwjsqbyp.com"; 
		urls = new HashMap<String, String>();
		urls.put("AnnoucementList","http://www.njwjsqbyp.com/list.asp?id=1&Page=");		//公告列表页
		urls.put("AnnoucementArticle", "http://www.njwjsqbyp.com/show.asp?id=");		//公告文章页
		urls.put("BreedList", "http://www.njwjsqbyp.com/list.asp?id=2&Page=");			//品种列表页
		urls.put("BreedArticle", "http://www.njwjsqbyp.com/show.asp?id=");				//品种文章页
		
		urls.put("行业新闻", "http://www.zgqbyp.com/web/news/13.html");			
		urls.put("市场报价", "http://www.zgqbyp.com/web/news/14.html");
		urls.put("中金研究", "http://www.zgqbyp.com/web/news/15.html");			
		urls.put("名家专栏", "http://www.zgqbyp.com/web/news/16.html");	
		urls.put("中金俱乐部", "http://www.zgqbyp.com/web/news/31.html");	
	}


	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取文章列表里的所有link：<标题，URL>
	 * Method:		getArticleLinks
	 * Author:		1.0 created by lijiaxuan at 2015年6月1日 下午7:11:50
	 *--------------------------------------------------------------------------------------*/
	public List<Map<String, String>> getArticleLinks(Document doc) {
		Elements elements = doc.select("div.ilist");
		List<Map<String, String>> aticleList = new ArrayList<Map<String, String>>();
		System.out.println(elements.toString());
		for (Element element : elements) {
			Elements links = element.getElementsByTag("a");
			for (Element link : links) {
				Map<String, String> aticleUrl = new HashMap<String, String>();
				String linkHref = link.attr("href");
				String linkText = link.text().trim();
				System.out.println(linkHref);
				System.out.println(linkText);
				aticleUrl.put("link", linkHref);
				aticleUrl.put("title", linkText);
				aticleList.add(aticleUrl);
			}
		}
		return aticleList;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取table在文章中的位置
	 * Method:		buildTablePos
	 * Author:		1.0 created by lijiaxuan at 2015年6月2日 上午9:54:21
	 *--------------------------------------------------------------------------------------*/
	public String buildTablePos(Element table){
		return "";
		/*Elements tablePos = table.select("table ~ p:matches([^ ]+)");// 定位后一个句子
		if (tablePos != null && tablePos.first() !=null){
			newsDomain.setTablePos(tablePos.first().toString());
		}
		return tablePos.toString();*/
		/*if (tablePos == null || tablePos.toString().trim().equals("")){
			System.out.println("begddd ");
			Elements els = table.parent().children();
			Element c = null;
			boolean flag = false;
			for (Element el : els){
				if (el.toString() != table.toString() && flag == false){
					System.out.println("111" );
					continue;
				}else if (el.toString() == table.toString() && flag == false){
					flag = true;
					System.out.println("222" );
				}else {
					System.out.println("el: " + el.toString());
					if (el.text()!=null && ! el.text().equals("")){
						System.out.println("el.text() " + el.text());
						c = el;
						break;
					}
				}
				
			}
			if (c==null){
				Elements els2= table.parent().parent().children();
				
				boolean flag2 = false;
				for (Element el2 : els2){
					if (el2.children().first() == null && flag2 == false){
						continue;
					}
					if (el2.children().first() != null && el2.children().first().toString() != table.toString() && flag2 == false){
						System.out.println("111" );
						continue;
					} else if (el2.children().first() != null && el2.children().first().toString() == table.toString() && flag2 == false){
						flag2 = true;
						System.out.println("222" );
					}else {
						System.out.println("el2: " + el2.toString());
						if (el2.text()!=null && ! el2.text().equals("")){
							System.out.println("el2.text() " + el2.text());
							c = el2;
							break;
						}
					
					}
					
				}				
			}
		} else {
			System.out.println("not null " );
		}*/
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取image在文章中的位置
	 * Method:		buildImagePos
	 * Author:		1.0 created by lijiaxuan at 2015年6月2日 上午10:22:17
	 *--------------------------------------------------------------------------------------*/
	public String buildImagePos(Element imgLink){
		
		//String imagesPath = imgLink.attr("src");
		  //Elements imagesPos = text.select("img[src~=(?i)\\.(png|jpe?g|gif)] ~ p:matches([：，。！]+)");// 定位后一个句子
       // Elements imagesPos = text.select("img[src] ~ p:matches([：，。！]+)");// 定位后一个句子
       ///[^u4E00-u9FA5]/g
        /*if (imagesPos == null) {
        	 imagesPos = text.select("img[src] ~ span:matches([：，。！如]+)");// 定位后一个句子
        }*/
          // Elements imagesPos = text.select("img[src] ~  p ");
		//return "<img>" + imagesPath.trim() + "</img>";
		return "";
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	根据URL或者文件名称解析新闻文章内容
	 * Method:		extractArticle
	 * Author:		1.0 created by lijiaxuan at 2015年6月3日 上午12:00:10
	 *--------------------------------------------------------------------------------------*/
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		extractNewsArticle
	 * Author:		1.0 created by lijiaxuan at 2015年6月3日 下午8:23:15
	 *--------------------------------------------------------------------------------------*/
	public void extractNewsArticle(String urlorfile, String type) throws Exception {
		Document doc = null;
		NewsDomain newsDomain = new NewsDomain();
		if ( type == null){
			type = "both";  //web
		}
		if (type == "local"){
			File input = new File(this.htmlDir + urlorfile);  
			doc = Jsoup.parse(input, "UTF-8", this.host ); 
		} else { 
			doc =fetchDocument(urlorfile);
		} 
		
		Elements titles = doc.select("div.neirong_title");		//标题
		for (Element title : titles){
			if (title!=null && title.text()!=null){
				newsDomain.setTitle(title.text().trim());
				if ( type == "both" ){		
					doc =fetchDocument(urlorfile);
					CommonUtil.toFile(doc.toString(), newsDomain.getTitle() + ".html", this.htmlDir);
				}
			}			
		}
		
		Elements notes = doc.select("div.neirong_note");		//时间
		//String timeRegex = "[0-9]+";
		//Pattern timePattern = Pattern.compile(timeRegex); 
		for (Element note : notes){
			//Matcher timeMatcher = timePattern.matcher(note.text());
			if (note != null && note.text()!=null){
				newsDomain.setIssueTime(CommonUtil.removeCNChar(note.text()).trim());
			}			
		}
		
		Elements texts = doc.select("div.neirong_text");		//正文
		System.out.println(texts.toString());
		for (Element text : texts) {
			Elements ps = text.select("div.neirong_text > p");// getElementsByTag("p");
			for (Element p : ps) {
				if (p!=null && p.text()!=null && ! p.text().trim().equals("")){
					System.out.println("" + newsDomain.getArticle());
					if (newsDomain.getArticle() != null) {
						newsDomain.setArticle( newsDomain.getArticle() + "<p>"+ p.text().trim().replaceAll("&nbsp;", "") +"</p>");
					} else{				
						newsDomain.setArticle( "<p>"+ p.text().replaceAll(" ", "").trim() +"</p>");
					}
				}			
			}
			// 如果文章包含图，设置文章属性。
			Elements imgLinks = text.select("img[src]");
		    for (Element link : imgLinks) {
		        
		        String imgUrl = this.FetchImageUrl(link);
		        String imgPos = buildImagePos(link);
		    	if (newsDomain.getImgUrl() != null ){
					newsDomain.setImgUrl(newsDomain.getImgUrl() + imgUrl);
					newsDomain.setImgPos(newsDomain.getImgPos() + imgPos);
				} else {
					newsDomain.setImgUrl(imgUrl);
					newsDomain.setImgPos(imgPos);
				}
		    }
		   
			// 如果文章包含表，设置文章属性。
			Elements tables = text.select("div table");			
			//System.out.println("table:: " + tables.toString());
			for (Element table : tables) {							
				String tableContent = this.fetchTableContent(table);
				String tablePos = buildTablePos(table);
				
				if (newsDomain.getTableContent() != null ){
					newsDomain.setTableContent(newsDomain.getTableContent() + tableContent);
					newsDomain.setTablePos(newsDomain.getTablePos() + tablePos);
				} else {
					newsDomain.setTableContent(tableContent);
					newsDomain.setTablePos(tablePos);
				}
			}		
		}		
		//newsDomain.setAnnouceUnit("");
		newsDomain.setFrom("南京文交所");
		newsDomain.setFetchTime(getNowDate());
		newsDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
		newsDomain.setType(CommonVariables.ARTICLE_INDUSTRY_NEWS);
		System.out.println("==================== beg ====================");		
		System.out.println(newsDomain.toString());
		System.out.println("==================== end ====================");
		NewsDao newsDao = new NewsDao();
		newsDao.addNews(newsDomain);
		newsDomain = null;
		newsDao = null;
	}


	public String getNowDate(){   
	    String temp_str="";   
	    Date dt = new Date();   
	    //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制   
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	    temp_str=sdf.format(dt);   
	    return temp_str;   
	} 
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取行业新闻
	 * Method:		fetchIndustryNews
	 * Author:		1.0 created by lijiaxuan at 2015年6月2日 9:00:23
	 *--------------------------------------------------------------------------------------*/
	public void fetchIndustryNews() {
		int start = 1;
		String url = urls.get("NewList") + start; 
		Document doc;
			doc = this.fetchDocument(url);
			// 1.> 获得最大页数
			int total = getTotalNumber( doc);		
			for (int i = start; i <= total; i++){
				String pageUrl = urls.get("NewList") + i;
				// 2.> 打开每个新闻列表页，获得每页的news link
				sleep(0);
				List<Map<String, String>> aticleList = getArticleLinks(fetchDocument(pageUrl));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				Iterator aticleIt = aticleList.iterator();
				int j = 0;
				while ( aticleIt.hasNext()){
					j++;
					Map<String, String> articleMap = (Map<String, String>)aticleIt.next();
					
					String aticleUrl = host + "/" +articleMap.get("link");
					//aticleUrl = "http://www.njwjsqbyp.com/show.asp?id=117";
					System.out.println("articleURl: " +aticleUrl);
					// 3.> 打开每个新闻，获取新闻内容
					try {					
					//aticleUrl = "http://www.njwjsqbyp.com/show.asp?id=1917";
					//aticleUrl = "http://www.njwjsqbyp.com/show.asp?id=1916"
						sleep(0);
						extractNewsArticle(aticleUrl, null) ;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if ( j == 2) return;
					
				}
			}
	
		return;
	}

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取最大页数
	 * Method:		getTotalNumber
	 * Author:		1.0 created by lijiaxuan at 2015年6月1日 下午3:01:35
	 *--------------------------------------------------------------------------------------*/
	public int getTotalNumber(Document doc) {
		int number = 0;
		Elements elements = doc.select("div.fenye");
		// System.out.println(elements.toString());
		for (Element element : elements) {
			Elements links = element.getElementsByTag("a");
			for (Element link : links) {
				if (link.text().trim().equals("尾页")) {
					String linkHref = link.attr("href");
					String linkText = link.text().trim();
					System.out.println(linkHref);
					System.out.println(linkText);
					String[] hrefs = linkHref.split("=");
					number = Integer.parseInt(hrefs[hrefs.length - 1].trim());
				}
			}
		}
		return number;
	}

	public static void main(String args[]) throws UnsupportedEncodingException {
		NjwjsSpiderServiceImpl njwjsSpider = new NjwjsSpiderServiceImpl();
		
		njwjsSpider.fetchIndustryNews();
		//String url = "http://www.njwjsqbyp.com/show.asp?id=1917";
		//String url = "http://www.njwjsqbyp.com/show.asp?id=1809";
		
		try {
			//njwjsSpider.extractArticleLocal(njwjsSpider.buildDocument(url, true, null));
			//njwjsSpider.extractArticleLocal(njwjsSpider.buildDocument("2015273《三版伍角券》等交易藏品停牌公告---南京文交所钱币邮票网", false), null);
			//njwjsSpider.extractArticle(njwjsSpider.fetchDocument("目前国内最热门的“邮币资讯”手机行情软件下载---南京文交所钱币邮票网", false, null), "local");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//public Element findSub

}