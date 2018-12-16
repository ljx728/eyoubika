package com.eyoubika.spider.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.eyoubika.util.CommonUtil;
import com.eyoubika.common.BaseSpider;
import com.eyoubika.common.RedisDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.spider.service.INjwjsSpiderService;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.spider.domain.*;
import com.eyoubika.spider.dao.*;
import com.eyoubika.util.*;
/*==========================================================================================*
 * Description:	抓取南京文交所数据的小蜘蛛哈哈
 * Class:		NjwjsSpiderServiceImpl
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-28 15:26:18
 *==========================================================================================*/
public class NjwjsJJSpiderServiceImpl extends BaseSpider implements INjwjsSpiderService{

	private Map<String, String> urls;
	private NewsArticleDomain newsArticleDomain;						//新闻
	private AnnouncementDomain announcementDomain;		//公告
	private BreedBasicDomain breedBasicDomain;			//品种基本信息
	private BreedArticleDomain breedArticleDomain;		//品种文章
	private NewsArticleDao newsArticleDao;
	private BreedBasicDao breedBasicDao;
	private BreedArticleDao BreedArticleDao;
	private QuotesRedisDao quotesRedisDao;
	
	//private AriticleTableDomain ariticleTableDomain;	//文章表格


	public Map<String, String> getUrls() {
		return urls;
	}

	public QuotesRedisDao getQuotesRedisDao() {
		return quotesRedisDao;
	}

	public void setQuotesRedisDao(QuotesRedisDao quotesRedisDao) {
		this.quotesRedisDao = quotesRedisDao;
	}

	public NewsArticleDao getNewsArticleDao() {
		return newsArticleDao;
	}

	public void setNewsArticleDao(NewsArticleDao newsArticleDao) {
		this.newsArticleDao = newsArticleDao;
	}

	public void setUrls(Map<String, String> urls) {
		this.urls = urls;
	}

	public NewsArticleDomain getNewsArticleDomain() {
		return newsArticleDomain;
	}

	public void setNewsArticleDomain(NewsArticleDomain NewsArticleDomain) {
		this.newsArticleDomain = NewsArticleDomain;
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

	public NjwjsJJSpiderServiceImpl() {		
		host = "http://www.njwjsqbyp.com"; 
		super.init("njwjs");
		urls = new HashMap<String, String>();
		urls.put("AnnoucementList","http://www.njwjsqbyp.com/list.asp?id=1&Page=");		//公告列表页
		urls.put("AnnoucementArticle", "http://www.njwjsqbyp.com/show.asp?id=");		//公告文章页
		urls.put("BreedList", "http://www.njwjsqbyp.com/list.asp?id=2&Page=");			//品种列表页
		urls.put("BreedArticle", "http://www.njwjsqbyp.com/show.asp?id=");				//品种文章页
		urls.put("NewList", "http://www.njwjsqbyp.com/list.asp?id=4&Page=");			//新闻列表页
		urls.put("NesArticle", "http://www.njwjsqbyp.com/show.asp?id=");				//新闻文章页
	}


	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取文章列表里的所有link：<标题，URL>
	 * Method:		getArticleLinks
	 * Author:		1.0 created by lijiaxuan at 2015年6月1日 下午7:11:50
	 *--------------------------------------------------------------------------------------*/
	public List<Map<String, String>> getArticleLinks(Document doc) {
		Elements elements = doc.select("div.ilist");
		String webTime = "";
		List<Map<String, String>> aticleList = new ArrayList<Map<String, String>>();
		for (Element element : elements) {
			Elements spans = element.getElementsByTag("span");
			if (spans != null && spans.first() != null) {
				webTime = spans.first().text().trim();
			}
			Elements links = element.getElementsByTag("a");		
			for (Element link : links) {	
				Map<String, String> aticleUrl = new HashMap<String, String>();
				String linkHref = link.attr("href");
				String linkText = link.text().trim();			
				aticleUrl.put("link", linkHref);
				aticleUrl.put("title", linkText);
				aticleUrl.put("webTime", webTime);
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
			NewsArticleDomain.setTablePos(tablePos.first().toString());
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
	public void extractNewsArticle(String urlorfile, Map<String, String> articleMap, String type){
		Document doc = null;
		NewsArticleDomain newsArticleDomain = new NewsArticleDomain();
		doc = fetchDoucment(urlorfile, type);
		newsArticleDomain.setWebTime(articleMap.get("webTime").toString());
		Elements titles = doc.select("div.neirong_title");		//标题
		for (Element title : titles){
			if (title!=null && title.text()!=null){
				newsArticleDomain.setTitle(title.text().trim());
			}			
		}
		
		Elements notes = doc.select("div.neirong_note");		//时间
		for (Element note : notes){
			if (note != null && note.text()!=null){
				newsArticleDomain.setIssueTime(CommonUtil.removeCNChar(note.text()).trim());
			}			
		}
		
		Elements texts = doc.select("div.neirong_text");		//正文
		for (Element text : texts) {
			Elements ps = text.select("div.neirong_text > p");// getElementsByTag("p");
			for (Element p : ps) {
				if (p!=null && p.text()!=null && ! p.text().trim().equals("")){
					if (newsArticleDomain.getArticle() != null) {
						newsArticleDomain.setArticle( newsArticleDomain.getArticle() + "<p>"+ p.text().trim().replaceAll("&nbsp;", "") +"</p>");
					} else{				
						newsArticleDomain.setArticle( "<p>"+ p.text().replaceAll(" ", "").trim() +"</p>");
					}
				}			
			}
			// 如果文章包含图，设置文章属性。
			Elements imgLinks = text.select("img[src]");
			for (Element link : imgLinks) {		        
		        String imgUrl = this.FetchImageUrl(link);
		        String maxNo = getMaxNo(imgDir);
		        boolean flag = this.downloadImage(this.host  + imgUrl, maxNo);				
				if (flag){
					String imgPos = buildImagePos(link);
					maxNo  = "<img>" + maxNo + "</img>";
					imgPos = "<pos>" + imgPos + "<pos>";	        
			    	if (newsArticleDomain.getImg() != null ){
			    		newsArticleDomain.setImg(newsArticleDomain.getImg() + maxNo);
			    		newsArticleDomain.setImgPos(newsArticleDomain.getImgPos() + imgPos);
					} else {
						newsArticleDomain.setImg(maxNo);
						newsArticleDomain.setImgPos(imgPos);
					}
				}
		    }
		   
			// 如果文章包含表，设置文章属性。
			Elements tables = text.select("div table");			
			//System.out.println("table:: " + tables.toString());
			for (Element table : tables) {							
				String tableContent = this.fetchTableContent(table);
				String tablePos = buildTablePos(table);	
				tablePos = "<pos>" + tablePos + "<pos>";
				if (newsArticleDomain.getTable() != null ){
					newsArticleDomain.setTable(newsArticleDomain.getTable() + tableContent);
					newsArticleDomain.setTablePos(newsArticleDomain.getTablePos() + tablePos);
				} else {
					newsArticleDomain.setTable(tableContent);
					newsArticleDomain.setTablePos(tablePos);
				}
			}		
		}		
		
		//NewsArticleDomain.setAnnouceUnit("");
		newsArticleDomain.setFrom("000102");
		newsArticleDomain.setFetchTime(getNowDate());
		newsArticleDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
		newsArticleDomain.setType(CommonVariables.ARTICLE_INDUSTRY_NEWS);
		System.out.println("==================== beg ====================");		
		System.out.println(newsArticleDomain.toString());
		System.out.println("==================== end ====================");
		NewsArticleDao NewsArticleDao = new NewsArticleDao();
		NewsArticleDao.addNewsArticle(newsArticleDomain);
		newsArticleDomain = null;
		NewsArticleDao = null;
	}


	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		extractBreedBasic
	 * Author:		1.0 created by lijiaxuan at 2015年6月13日 下午6:21:09
	 *--------------------------------------------------------------------------------------*/
	public BreedBasicDomain extractBreedBasic(Element text){
		BreedBasicDomain breedBasicDomain = new BreedBasicDomain();	
		String nameRegex = "【*(藏 *品 *)?(名 *称)】*[：:]*";		//名字
		String name = null;
		String issueTimeRegex = "【*发 *行 *时 *间】*[：:]*";		//发行时间
		String issueTime = null;
		String issueVolumnRegex = "【*发 *行 *数? *量】*[：:]*";		//发行量
		String issueVolumn = null;	
		String issuePriceRegex = "【*发 *行 *价 *格?】*[：:]*";	//发行价
		String issuePrice = null;
		String issueStyleRegex = "【*发 *行 *方 *式】*[：:]*";	//
		String issueStyle = null;
		String issueUnitRegex = "【*发 *行 *(单 *位|机 *构|部 *门)】*[：:]*";		//
		String issueUnit = null;	
		String initUnitRegex = "【*发 *起 *单 *位】*[：:]*";		//
		String initUnit = null;
		String issuePlaceRegex = "【*发 *起 *地 *点】*[：:]*";		//发行地点
		String issuePlace = null;		
		//picture			
		String serialNumberRegex = "【*志 *号】*[：:]*";//
		String serialNumber = null;
		String fullSetNumberRegex = "【*全 *套 *枚 *数】*[：:]*";
		String fullSetNumber = null;
		String fullSetPriceRegex = "【*全 *套 *售 *价(（元）)?】*[：:]*";	
		String fullSetPrice = null;
		String priceRegex = "【*售 *价】*[：:]*";		//发行地点
		String price = null;
		String fullSetFaceValueRegex = "【*全 *套 *面 *值】*[：:]*";		
		String fullSetFaceValue = null;
		String faceValueRegex = "【*(明 *信 *片|藏 *品 *)?面 *[值额]】*[：:]*";//面值
		String faceValue = null;
		/*String fullSetMemberRegex = "【*发 *行 *时 *间】*[：:]*";//
		String fullSetMember = null;*/
		String specificationRegex = "【*(明 *信 *片|信 *卡)?(规 *格|尺 *寸|直 *径|半 *径)】*[：:]*";
		String specification = null;
		String qualityRegex = "【*质 *量】*[：:]*";	
		String quality = null;
		String finessRegex = "【*成 *色】*[：:]*";	
		String finess = null;
		String categoryRegex = "【*(藏 *品 *)?类 *别】*[：:]*";		//
		String category = null;		
		String weightRegex = "【*重 *量】*[：:]*";//
		String weight = null;
		String frontPicRegex = "【*(正 *面 *|藏 *品 *)?图 *案】*[：:]*";
		String frontPic = null;
		String backPicRegex = "【*背 *面 *图 *案】*[：:]*";	
		String backPic = null;
		String manufacturerRegex = "【*((印 *(制|刷)|制 *卡) *(厂|单 *位)|铸 *造 *厂 *家)】*[：:]*";	
		//String manufacturerRegex = "【*((印 *(制|刷)|制 *卡) *(厂|单 *位)|铸 *造 *厂 *家)?】*[：:]*";	
		String manufacturer = null;
		String printingRegex = "【*(版 *别|印 *刷 *(方 *式|工 *艺))】*[：:]*";		//
		String printing = null;		
		String designerRegex = "【(.*卡)? *设 *计 *者?】*[：:]*";//
		String designer = null;
		String antiCounterfeitingRegex = "【*防 *伪( *方 *式)?】*[：:]*";
		String antiCounterfeiting = null;
		String perforationDegreeRegex = "【*齿 *孔 *度 *数】*[：:]*";	
		String perforationDegree = null;
		String wholeNumberRegex = "【*整 *张 *枚 *数】*[：:]*";	
		String wholeNumber = null;
		String wholeSpecifRegex = "【*整 *张 *规 *格】*[：:]*";		//
		String wholeSpecif = null;		
		String postImgRegex = "【*邮 *资 *图 *案？】*[：:]*";//
		String postImg = null;
		String postImgSepcifRegex = "【*(明 *信 *片)? *(邮 *票|邮 *资 *图) *规 *格】*[：:]*";//
		String postImgSepcif = null;
		String gumRegex = "【*背 *胶】*[：:]*";
		String gum = null;
		String frontCoverRegex = "【*封 *面】*[：:]*";	
		String frontCover = null;
		String backCoverRegex = "【*封 *底】*[：:]*";	
		String backCover = null;
		String photographerRegex = "【*摄 *影 *者】*[：:]*";		//
		String photographer = null;		
		String textureRegex = "【*(印 *刷 *纸 *张|材 *质)】*[：:]*";	
		String texture = null;
		String systemRegex = "【*制 *式】*[：:]*";	
		String system = null;
		String themeRegex = "【*题 *材】*[：:]*";	
		String theme = null;
		String editorRegex = "【*责 *任 *编 *辑】*[：:]*";	
		String editor = null;		
		
		Elements ps = text.select("div.neirong_text > p, div.neirong_text > span, div.neirong_text > strong");// getElementsByTag("p");
		for (Element p : ps) {
			
			if (p!=null && p.text()!=null && ! p.text().trim().equals("")){
				String line =  p.text().trim();
				if (name == null) 	name =  RegexUtil.getExclusiveString(nameRegex, line, 0);		
				if (issueTime == null)	issueTime =  RegexUtil.getExclusiveString(issueTimeRegex, line, 0);
				if (issueVolumn == null) 	issueVolumn =  RegexUtil.getExclusiveString(issueVolumnRegex, line, 0);		
				if (issuePrice == null)	issuePrice =  RegexUtil.getExclusiveString(issuePriceRegex, line, 0);
				if (issueStyle == null) 	issueStyle =  RegexUtil.getExclusiveString(issueStyleRegex, line, 0);		
				if (issueUnit == null)	issueUnit =  RegexUtil.getExclusiveString(issueUnitRegex, line, 0);				
				if (initUnit == null) 	initUnit =  RegexUtil.getExclusiveString(initUnitRegex, line, 0);		
				if (issuePlace == null)	issuePlace =  RegexUtil.getExclusiveString(issuePlaceRegex, line, 0);
				if (serialNumber == null) 	serialNumber =  RegexUtil.getExclusiveString(serialNumberRegex, line, 0);		
				if (fullSetNumber == null)	fullSetNumber =  RegexUtil.getExclusiveString(fullSetNumberRegex, line, 0);
				if (fullSetPrice == null) 	fullSetPrice =  RegexUtil.getExclusiveString(fullSetPriceRegex, line, 0);		
				if (price == null)	price =  RegexUtil.getExclusiveString(priceRegex, line, 0);			
				if (fullSetFaceValue == null) 	fullSetFaceValue =  RegexUtil.getExclusiveString(fullSetFaceValueRegex, line, 0);		
				if (faceValue == null)	faceValue =  RegexUtil.getExclusiveString(faceValueRegex, line, 0);
				if (specification == null) 	specification =  RegexUtil.getExclusiveString(specificationRegex, line, 80);		
				if (quality == null)	quality =  RegexUtil.getExclusiveString(qualityRegex, line, 0);
				if (finess == null) 	finess =  RegexUtil.getExclusiveString(finessRegex, line, 0);		
				if (category == null)	category =  RegexUtil.getExclusiveString(categoryRegex, line, 0);			
				if (weight == null) 	weight =  RegexUtil.getExclusiveString(weightRegex, line, 0);		
				if (frontPic == null)	frontPic =  RegexUtil.getExclusiveString(frontPicRegex, line, 128);
				if (backPic == null) 	backPic =  RegexUtil.getExclusiveString(backPicRegex, line, 128);		
				if (manufacturer == null)	manufacturer =  RegexUtil.getExclusiveString(manufacturerRegex, line, 0);
				if (printing == null) 	printing =  RegexUtil.getExclusiveString(printingRegex, line, 0);		
				if (designer == null)	designer =  RegexUtil.getExclusiveString(designerRegex, line, 0);				
				if (antiCounterfeiting == null) 	antiCounterfeiting =  RegexUtil.getExclusiveString(antiCounterfeitingRegex, line, 0);		
				if (perforationDegree == null)	perforationDegree =  RegexUtil.getExclusiveString(perforationDegreeRegex, line, 0);
				if (wholeNumber == null) 	wholeNumber =  RegexUtil.getExclusiveString(wholeNumberRegex, line, 0);		
				if (wholeSpecif == null)	wholeSpecif =  RegexUtil.getExclusiveString(wholeSpecifRegex, line, 0);
				if (postImg == null) 	postImg =  RegexUtil.getExclusiveString(postImgRegex, line, 0);		
				if (postImgSepcif == null)	postImgSepcif =  RegexUtil.getExclusiveString(postImgSepcifRegex, line, 0);
				
				if (gum == null) 	gum =  RegexUtil.getExclusiveString(gumRegex, line, 0);		
				if (frontCover == null)	frontCover =  RegexUtil.getExclusiveString(frontCoverRegex, line, 0);
				if (backCover == null) 	backCover =  RegexUtil.getExclusiveString(backCoverRegex, line, 0);		
				if (photographer == null)	photographer =  RegexUtil.getExclusiveString(photographerRegex, line, 0);
				if (texture == null) 	texture =  RegexUtil.getExclusiveString(textureRegex, line, 0);		
				if (system == null)	system =  RegexUtil.getExclusiveString(systemRegex, line, 0);
				
				if (theme == null) 	theme =  RegexUtil.getExclusiveString(themeRegex, line, 0);		
				if (editor == null)	editor =  RegexUtil.getExclusiveString(editorRegex, line, 0);
			}			
		}
		breedBasicDomain.setName(name);
		breedBasicDomain.setIssueTime(issueTime);
		breedBasicDomain.setIssueVolumn(issueVolumn);
		breedBasicDomain.setIssuePrice(issuePrice);
		breedBasicDomain.setIssueStyle(issueStyle);
		breedBasicDomain.setIssueUnit(issueUnit);
		breedBasicDomain.setInitUnit(initUnit);
		breedBasicDomain.setIssuePlace(issuePlace);
		breedBasicDomain.setSerialNumber(serialNumber);
		breedBasicDomain.setFullSetNumber(fullSetNumber);
		breedBasicDomain.setFullSetPrice(fullSetPrice);
		breedBasicDomain.setPrice(price);	
		breedBasicDomain.setFullSetFaceValue(fullSetFaceValue);
		breedBasicDomain.setFaceValue(faceValue);
		breedBasicDomain.setSpecification(specification);
		breedBasicDomain.setQuality(quality);
		breedBasicDomain.setFiness(finess);
		breedBasicDomain.setCategory(category);	
		breedBasicDomain.setWeight(weight);
		breedBasicDomain.setFrontPic(frontPic);
		breedBasicDomain.setBackPic(backPic);
		breedBasicDomain.setManufacturer(manufacturer);
		breedBasicDomain.setPrinting(printing);
		breedBasicDomain.setDesigner(designer);	
		breedBasicDomain.setAntiCounterfeiting(antiCounterfeiting);
		breedBasicDomain.setPerforationDegree(perforationDegree);
		breedBasicDomain.setWholeNumber(wholeNumber);
		breedBasicDomain.setWholeSpecif(wholeSpecif);
		breedBasicDomain.setPostImg(postImg);
		breedBasicDomain.setPostImgSepcif(postImgSepcif);
		
		breedBasicDomain.setGum(gum);
		breedBasicDomain.setFrontCover(frontCover);
		breedBasicDomain.setBackCover(backCover);
		breedBasicDomain.setPhotographer(photographer);
		breedBasicDomain.setTexture(texture);
		breedBasicDomain.setSystem(system);
		
		breedBasicDomain.setTheme(theme);
		breedBasicDomain.setEditor(editor);
		return breedBasicDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		extractBreedArticle
	 * Author:		1.0 created by lijiaxuan at 2015年6月4日 上午10:08:37
	 *--------------------------------------------------------------------------------------*/
	public void extractBreedArticle(String urlorfile, String type){
		Document doc = null;
		BreedArticleDomain breedArticleDomain = new BreedArticleDomain();
		BreedBasicDomain breedBasicDomain = new BreedBasicDomain();	
		BreedArticleDao breedArticleDao = new BreedArticleDao();
		BreedBasicDao breedBasicDao = new BreedBasicDao();
		doc = fetchDoucment(urlorfile, type);
		Elements titles = doc.select("div.neirong_title");		//标题
		for (Element title : titles){
			if (title!=null && title.text()!=null){
				breedArticleDomain.setTitle(title.text().trim());
			}			
		}
		
		Elements notes = doc.select("div.neirong_note");		//时间
		for (Element note : notes){
			if (note != null && note.text()!=null){
				breedArticleDomain.setIssueTime(CommonUtil.removeCNChar(note.text()).trim());
			}			
		}
		
		Elements texts = doc.select("div.neirong_text");		//正文
		for (Element text : texts) {
			// 解析存入品种数据库
			breedBasicDomain = extractBreedBasic(text);
			
			
			Elements ps = text.select("div.neirong_text > p, div.neirong_text > span, div.neirong_text > strong");// getElementsByTag("p");
			for (Element p : ps) {
				if (p!=null && p.text()!=null && ! p.text().trim().equals("")){
					if (breedArticleDomain.getArticle() != null) {
						breedArticleDomain.setArticle( breedArticleDomain.getArticle() + "<p>"+ p.text().trim() +"</p>");
					} else{				
						breedArticleDomain.setArticle( "<p>"+ p.text().trim() +"</p>");
					}
				}			
			}
			// 如果文章包含图，设置文章属性。
			Elements imgLinks = text.select("img[src]");
		    for (Element link : imgLinks) {		        
		    	String imgUrl = this.FetchImageUrl(link);
		        String maxNo = getMaxNo(imgDir);
		        if ( ! imgUrl.contains("http")) {		//绝对地址
		        	imgUrl = this.host + imgUrl;
		        }
		        boolean flag = this.downloadImage(imgUrl, maxNo);
				
				if (flag) {
					String imgPos = buildImagePos(link);
					maxNo  = "<img>" + maxNo + "</img>";
					imgPos = "<pos>" + imgPos + "<pos>";	       
			    	if (breedArticleDomain.getImg() != null ){
			    		breedArticleDomain.setImg(breedArticleDomain.getImg() + maxNo);
			    		breedArticleDomain.setImgPos(breedArticleDomain.getImgPos() + imgPos);
					} else {
						breedArticleDomain.setImg(maxNo);
						breedArticleDomain.setImgPos(imgPos);
					}
				}
		    }
		   
			// 如果文章包含表，设置文章属性。
			Elements tables = text.select("div table");			
			for (Element table : tables) {							
				String tableContent = this.fetchTableContent(table);
				String tablePos = buildTablePos(table);	
				
				tablePos = "<pos>" + tablePos + "<pos>";
				if (breedArticleDomain.getTable() != null ){
					breedArticleDomain.setTable(breedArticleDomain.getTable() + tableContent);
					breedArticleDomain.setTablePos(breedArticleDomain.getTablePos() + tablePos);
				} else {
					breedArticleDomain.setTable(tableContent);
					breedArticleDomain.setTablePos(tablePos);
				}
			}		
		}		
		
		//NewsArticleDomain.setAnnouceUnit(""); 
		breedArticleDomain.setFrom("000102");		//南京文交所经济会员
		breedArticleDomain.setFetchTime(getNowDate());
		breedArticleDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
		breedArticleDomain.setType(CommonVariables.ARTICLE_BREED_INFO);
	
		//存储图片信息
		if(breedArticleDomain.getImg() != null){
			breedBasicDomain.setPicture(breedArticleDomain.getImg());
		}
		//存储id
		int breedId = breedBasicDao.addBreedBasic(breedBasicDomain);
		breedArticleDomain.setBreedId(breedId);
		breedArticleDao.addBreedArticle(breedArticleDomain);
		CommonUtil.toLog("=====================breedBasicDomain=====================\n");
		CommonUtil.toLog(breedBasicDomain.toString());
		CommonUtil.toLog("=====================breedArticleDomain=====================\n");
		CommonUtil.toLog(breedArticleDomain.toString());
		breedArticleDomain = null;
		breedArticleDao = null;
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
			List<Map<String, String>> aticleList = getArticleLinks(fetchDocument(pageUrl));
			Iterator aticleIt = aticleList.iterator();
			int j = 0;
			while ( aticleIt.hasNext()){
				j++;
				Map<String, String> articleMap = (Map<String, String>)aticleIt.next();		
				String aticleUrl = host + "/" +articleMap.get("link");
				System.out.println("articleURl: " +aticleUrl);
				// 3.> 打开每个新闻，获取新闻内容													
				extractNewsArticle(aticleUrl, articleMap, null) ;								
			}
		}	
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取品种信息
	 * Method:		fetchBreedInfo
	 * Author:		1.0 created by lijiaxuan at 2015年6月2日 9:00:23
	 *--------------------------------------------------------------------------------------*/
	public void fetchBreedInfo() {
		int start = 1;
		String url = urls.get("BreedList") + start; 
		Document doc;
		doc = this.fetchDocument(url);
		// 1.> 获得最大页数
		int total = getTotalNumber( doc);	
		for (int i = start; i <= total; i++){
			String pageUrl = urls.get("BreedList") + i;
			// 2.> 打开每个新闻列表页，获得每页的品种 link
			List<Map<String, String>> aticleList = getArticleLinks(fetchDocument(pageUrl));
			Iterator aticleIt = aticleList.iterator();
			
			int j = 0;
			while ( aticleIt.hasNext()){
				j++;
				Map<String, String> articleMap = (Map<String, String>)aticleIt.next();		
				String aticleUrl = host + "/" +articleMap.get("link");
				System.out.println("articleURl: " +aticleUrl);
				//3.> 打开每篇文章，获取品种内容													
				extractBreedArticle(aticleUrl, null) ;									
			}
		}
		System.out.println("==END==");
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
					String[] hrefs = linkHref.split("=");
					number = Integer.parseInt(hrefs[hrefs.length - 1].trim());
				}
			}
		}
		return number;
	}

	public void fetchRealTimeQuotes(){
		host = "180.97.2.74";
		init(host);
		String url = "http://180.97.2.74:16929/tradeweb/hq/getHqV_lbData.jsp";
		Document doc = fetchDocument(url);
		String jsonStr = doc.select("body").text().toString();
		System.out.println();
		//RedisDao redisDao = new RedisDao();
		quotesRedisDao.addQuotes(JSONObject.fromObject(jsonStr));
	}
	
	public void analysis(){
		
		
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		main
	 * Author:		1.0 created by lijiaxuan at 2015年6月5日 下午3:49:53
	 *--------------------------------------------------------------------------------------*/
	public static void main(String args[]) throws UnsupportedEncodingException {
		NjwjsSpiderServiceImpl njwjsSpider = new NjwjsSpiderServiceImpl();
		njwjsSpider.fetchRealTimeQuotes();
		//njwjsSpider.fetchBreedInfo();
		//njwjsSpider.fetchIndustryNews();
	}

}