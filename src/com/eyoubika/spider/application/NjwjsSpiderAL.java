package com.eyoubika.spider.application;

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
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.eyoubika.sbc.dao.SbcBasicDao;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.spider.web.VO.FetchVO;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.common.BaseSpider;
import com.eyoubika.common.RedisDao;
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
public class NjwjsSpiderAL extends BaseSpider {

	private NewsArticleDao newsArticleDao;
	private BreedBasicDao breedBasicDao;
	private BreedArticleDao breedArticleDao;
	private QuotesRedisDao quotesRedisDao;
	private SbcBasicDao sbcBasicDao;


	public SbcBasicDao getSbcBasicDao() {
		return sbcBasicDao;
	}

	public void setSbcBasicDao(SbcBasicDao sbcBasicDao) {
		this.sbcBasicDao = sbcBasicDao;
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
	
	
	public BreedBasicDao getBreedBasicDao() {
		return breedBasicDao;
	}

	public void setBreedBasicDao(BreedBasicDao breedBasicDao) {
		this.breedBasicDao = breedBasicDao;
	}

	public BreedArticleDao getBreedArticleDao() {
		return breedArticleDao;
	}

	public void setBreedArticleDao(BreedArticleDao breedArticleDao) {
		this.breedArticleDao = breedArticleDao;
	}

	public NjwjsSpiderAL() {		
		host = "http://www.zgqbyp.com"; 
		super.init("njwjs");
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	获取文章列表里的所有link：<URL，标题，网站时间，类型>
	 * Method:		1>.	getArticleLinks
	 * Author:		1.0 created by lijiaxuan at 2015年6月1日 下午7:11:50
	 *--------------------------------------------------------------------------------------*/
	public List<Map<String, String>> getArticleLinks(Document doc, String type) {
		Elements elements = doc.select("div.ilist li");
		String webTime = "";
		List<Map<String, String>> articleList = new ArrayList<Map<String, String>>();
		for (Element element : elements) {
			Elements spans = element.getElementsByTag("span");
			if (spans != null && spans.first() != null) {
				webTime = spans.first().text().trim();
			}
			Elements links = element.getElementsByTag("a");		
			for (Element link : links) {	
				Map<String, String> articleUrl = new HashMap<String, String>();
				String linkHref = link.attr("href");
				String linkText = link.text().trim();	
				System.out.println("linkHref " + linkHref);
				articleUrl.put("link", linkHref);
				articleUrl.put("title", linkText);
				articleUrl.put("webTime", webTime);
				articleUrl.put("type", type);
				articleList.add(articleUrl);
			}						
		}
		return articleList;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取文章标题
	 * Method:		2>.	extractArticleTitle
	 * Author:		1.0 created by lijiaxuan at 2015年6月4日 上午10:08:37
	 *--------------------------------------------------------------------------------------*/
	public String extractArticleTitle(Document doc){
		String titleString = null;
		Elements titles = doc.select("div.neirong_title");		//标题
		
		for (Element title : titles){
			System.out.println("title " +title.toString());
			if (title!=null && title.text()!=null){
				titleString =  title.text().trim();
			}			
		}
		return titleString;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取文章发布时间
	 * Method:		3>.	extractArticleTitle
	 * Author:		1.0 created by lijiaxuan at 2015年6月4日 上午10:28:12
	 *--------------------------------------------------------------------------------------*/
	public String extractArticleTime(Document doc){
		String timeString = null;
		Elements notes = doc.select("div.neirong_note");		//时间 Elements notes = doc.select("div.authi");	
		//System.out.println("notes " +notes.toString());
		for (Element note : notes){
			if (note != null && note.text()!=null){
				//删除中文字符
				String[] times =CommonUtil.removeCNChar(note.text()).replace("：", "").trim().split(" ");
				if (times.length > 1){
					timeString = times[0] + " " + times[1];
				} else {
					timeString =times[0];
				}
			}			
		}
		return timeString;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取文章内容
	 * Method:		4>.	extractArticleText
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午11:19:19
	 *--------------------------------------------------------------------------------------*/
	public String extractArticleText(Document doc){
		String textString = null;
		Elements texts = doc.select("div.neirong_text");		//正文  Elements texts = doc.select("div.pcb");		//正文
		//System.out.println("texts " +texts.toString());
		for (Element text : texts) {		
			Elements ps = text.select("div.neirong_text > p, div.neirong_text > span, div.neirong_text > strong");// Elements ps = text.select("td.t_f > p");
			for (Element p : ps) {
				if (p!=null && p.text()!=null && ! p.text().trim().equals("")){				
					if (textString != null) {					
						textString +=  "<p>"+ p.text().trim() +"</p>";		//"<p>"+ p.text().trim().replaceAll("&nbsp;", "") +"</p>"
					} else{				
						textString =  "<p>"+ p.text().trim() + "</p>";
					}
				}			
			}
		}
		return clean(textString);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	数据清洗
	 * Method:		clean
	 * Author:		1.0 created by lijiaxuan at 2015年7月30日 上午9:17:53
	 *--------------------------------------------------------------------------------------*/
	public String clean(String originString){
		String resString = "";
		if (originString!=null){
			resString = originString.replace("<p>官网：www.zgqbyp.com 微官网：</p>", "");
		}	
		return resString;
	}
	//应当把img的位置信息放到文章article <imgPos>中。
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取文章中的图片
	 * Method:		5>.	extractArticleImg
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午11:17:00
	 *--------------------------------------------------------------------------------------*/
	public String extractArticleImg(Document doc){
		String img = null;
		Elements texts = doc.select("div.neirong_text");		//正文
		for (Element text : texts) {		
			// 如果文章包含图，设置文章属性。
			Elements imgLinks = text.select("img[src]");
			for (Element link : imgLinks) {		
				int iterNum = 0;				
				Element p = link.parent();
				System.out.println("p.tag().getName() " + p.tag().getName());
				while (p!=null && !p.tag().getName().equals("p") && !p.tag().getName().equals("span") && iterNum < CommonVariables.HTML_PARENT_MAX){
					p = p.parent();
					iterNum++;
				}
				//去除垃圾信息
				if (p!= null &&  p.text() != null && p.text().contains("微官网：")){
					continue;
				}
				//p = findTag(link, "p,span");
				String imgUrl = this.FetchImageUrl(link);
				//获取图片目录下最大的ID
				String maxNo = getMaxNo(imgDir);
				  if ( ! imgUrl.contains("http")) {		//绝对地址
			        	imgUrl = this.host + imgUrl;
			        }
				boolean flag = this.downloadImage(imgUrl, maxNo);	
				//如果下载成功，则保存信息到数据库
				if (flag){			
					maxNo  = "<img>" + maxNo + "</img>";				        
					if (img != null ){
						img = img + maxNo;					
					} else {
						img = maxNo;
					}
				}
			}
		}
		return img;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取图片在文章中的位置
	 * Method:		6>.	extractArticleTable
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午11:19:13
	 *--------------------------------------------------------------------------------------*/
	public Element findTag(Element el, String tagString){
		//p span 
		String[] tags = tagString.split(",");
		int size = tags.length;
		int siblingNum = 0;
		int parentNum = 0;
		//1. 广度遍历
		Element originEl = el;
		el = el.nextElementSibling();
		while (el!=null && siblingNum<CommonVariables.HTML_SIBLING_MAX){
			for (int i = 0; i < size; i++){
				if (el.tag().getName().equals(tags[i].trim())&& el.text()!=null && ! el.text().replaceAll(" ", "").trim().equals("")){
					return el;
				}
			}
			el = el.nextElementSibling();
			siblingNum++;
		}
		el = originEl.parent();;
		while (el!=null && parentNum<CommonVariables.HTML_PARENT_MAX){
			Element oriEl = el;
			while (el!=null && siblingNum<CommonVariables.HTML_SIBLING_MAX){
				for (int i = 0; i < size; i++){
					if (el.tag().getName().equals(tags[i].trim()) && el.text()!=null && ! el.text().replaceAll(" ", "").trim().equals("")){
						return el;
					}
				}
				el = el.nextElementSibling();
				siblingNum++;
			}
			el = oriEl.parent();
			parentNum++;
		}
		return null;
	}
	
  /* public void find(Element el, List<Element> elments) {
        List<Node> list = el.childNodes();
        if (!list.isEmpty()) {
        	 for (Node node : list) {
        		 if (node.equals(node))
        	 }
        }
        do {
            list = getNextLevelNode(list);
        } while (list != null);
    }

    public List<Node> getNextLevelNode(List<Node> nodeList) {
        if (!nodeList.isEmpty()) {
            List<Node> list = new ArrayList<Node>();
            for (Node node : nodeList) {
                list.addAll(node.childNodes());
            }
            return list;
        }
        return null;
    }*/
  
	   
	public String extractImgPos(Document doc){
		String imgPos = null;
		int maxSibling = CommonVariables.HTML_SIBLING_MAX;
		String imgPosString = null;
		Elements texts = doc.select("div.neirong_text");		//正文
		for (Element text : texts) {		
			// 如果文章包含表，设置文章属性。
			Elements imgLinks = text.select("img[src]");			
			//System.out.println("table:: " + tables.toString());
			for (Element imgLink : imgLinks) {
				int iterNum = 0;
				
				Element p = imgLink.parent();

				while (p!=null &&!p.tag().getName().equals("p") && !p.tag().getName().equals("span") && iterNum < CommonVariables.HTML_PARENT_MAX){
					p = p.parent();
				
					iterNum++;
				}/**/
				//去除垃圾信息
				if (p!= null &&  p.text() != null && p.text().contains("微官网：")){
					continue;
				}
				p = findTag(imgLink, "p,span");
				/*p = p.nextElementSibling();//;
				int nowSibling = 0;
				while( p!= null &&  p.text() != null &&  p.text().replaceAll(" ", "").trim().equals("") ){
					nowSibling++;
					if (nowSibling > maxSibling) {
						break;
					}
					p = p.nextElementSibling();
				}*/
				if (p != null && p.text() != null){
					imgPosString = "<pos>" + p.text().trim() +"</pos>";
				}
				else {
					imgPosString = "<pos></pos>";
				}			
				//System.out.println("imgPos: " + imgPosString);
				if (imgPos != null ){
					imgPos += imgPosString;					
				} else {
					imgPos = imgPosString;						
				}
			}
		}
		return imgPos;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取表格在文章中的位置
	 * Method:		7>.	extractArticleTable
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午11:19:13
	 *--------------------------------------------------------------------------------------*/
	public String extractTablePos(Document doc){
		String tablePos = null;
		String tablePosString = "";
		int maxSibling = CommonVariables.HTML_SIBLING_MAX;									//最大循环次数
		Elements texts = doc.select("div.neirong_text");		//正文
		for (Element text : texts) {		
			// 如果文章包含表，设置文章属性。
			Elements tables = text.select("div table");			
			for (Element table : tables) {
				
				Element p = table.parent().nextElementSibling();//;
				int nowSibling = 0;
				while( p!= null &&  p.text() != null &&  p.text().replaceAll(" ", "").trim().equals("") ){
					nowSibling++;
					if (nowSibling > maxSibling) {
						break;
					}
					p = p.nextElementSibling();
				}
				if (p!=null && p.text()!=null){
					tablePosString = "<pos>" + p.text().trim() +"</pos>";
				} else {
					tablePosString = "<pos></pos>";
				}		
				if (tablePos != null ){
					tablePos += tablePosString;					
				} else {
					tablePos = tablePosString;						
				}
			}
		}
		return tablePos;
	}
	//应当把table的位置信息放到文章article <tablePos>中。		
		/*--------------------------------------------------------------------------------------*
		 * Description:	获取文章中的表格
		 * Method:		8>.	extractArticleTable
		 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午11:19:13
		 *--------------------------------------------------------------------------------------*/
		public String extractArticleTable(Document doc){
			String tableString = null;
			Elements texts = doc.select("div.neirong_text");		//正文
			for (Element text : texts) {		
				// 如果文章包含表，设置文章属性。
				Elements tables = text.select("div table");			
				//System.out.println("table:: " + tables.toString());
				for (Element table : tables) {							
					String tableContent = this.fetchTableContent(table);					
					if (tableString != null ){
						tableString += tableContent;					
					} else {
						tableString = tableContent;						
					}
				}
			}
			return tableString;
		}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取本地文件中的新闻，目前是先把网页下载到本地，处理后，进行本地的解析。
	 * Method:		9>.	extractNewsArticle
	 * Author:		1.0 created by lijiaxuan at 2015年6月3日 下午8:23:15
	 *--------------------------------------------------------------------------------------*/
	public void extractLocalNewsArticle(String urlorfile, Map<String, String> articleMap, String type){
		Document doc = null;
		String title = null;
		String issueTime = null;
		String textString = null;
		String img = null;
		String table = null;
		String tablePos = null;
		String imgPos = null;

		NewsArticleDomain newsArticleDomain = new NewsArticleDomain();
		//获取Document
		doc = fetchDoucment(urlorfile, type);
		//设置网站时间（网站时间即网站公布时间，与新闻本身的发布时间有可能不一致）
		newsArticleDomain.setWebTime(articleMap.get("webTime").toString());
		//设置文章URL
		newsArticleDomain.setArticleUrl(urlorfile);
		//获取标题
		if ((title = extractArticleTitle(doc)) != null) newsArticleDomain.setTitle(title);
		//获取发布时间
		if ((issueTime = extractArticleTime(doc)) != null) newsArticleDomain.setIssueTime(issueTime);
		//获取正文
		if ((textString = extractArticleText(doc)) != null) newsArticleDomain.setArticle(textString);
		//获取图片
		if ((img = extractArticleImg(doc)) != null){
			newsArticleDomain.setImg(img);
			newsArticleDomain.setHaveImg("1"); 		//有图	
			if ((imgPos = extractImgPos(doc)) != null) newsArticleDomain.setImgPos(imgPos);
		}
		//获取表格
		if ((table = extractArticleTable(doc)) != null) {
			newsArticleDomain.setTable(table);	
			newsArticleDomain.setHaveTable("1"); 	//有表
			if ((tablePos = extractTablePos(doc)) != null) newsArticleDomain.setTablePos(tablePos);
		}
		//NewsArticleDomain.setAnnouceUnit("");
		//设置来源：南京文交所
		newsArticleDomain.setFrom(CommonVariables.EXCHANGE_NANJING);
		//设置抓取时间
		newsArticleDomain.setFetchTime(CommonUtil.getNowDate());
		//设置文章状态：网络抓取未审核
		newsArticleDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
		//设置文章类型
		newsArticleDomain.setType(articleMap.get("type").toString());
		System.out.println("==================== beg ====================");		
		System.out.println(newsArticleDomain.toString());
		System.out.println("==================== end ====================");
		newsArticleDao.addNewsArticle(newsArticleDomain);
		CommonUtil.toLog(newsArticleDomain.toString(), CommonVariables.LOG_TYPE_SPIDER);
		newsArticleDomain = null;

	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取品种文章
	 * Method:		10>.extractBreedText
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午11:26:01
	 *--------------------------------------------------------------------------------------*/
	public String extractBreedText(Document doc){
		String textString = null;
		Elements texts = doc.select("div.neirong_text");		//正文
		for (Element text : texts) {		
			Elements ps = text.select("div.neirong_text > p, div.neirong_text > span, div.neirong_text > strong");
			for (Element p : ps) {
				if (p!=null && p.text()!=null && ! p.text().trim().equals("")){				
					if (textString != null) {					
						textString +=  "<p>"+ p.text().trim() +"</p>";		//"<p>"+ p.text().trim().replaceAll("&nbsp;", "") +"</p>"
					} else{				
						textString =  "<p>"+ p.text().trim() + "</p>";
					}
				}			
			}
		}
		return textString;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取品种信息
	 * Method:		extractBreedInfo
	 * Author:		1.0 created by lijiaxuan at 2015年7月30日 上午9:02:28
	 *--------------------------------------------------------------------------------------*/
	public List<Map<String, String>> extractBreedInfo(Document doc, String type){
		Elements texts = doc.select("table[cellpadding=\"5\"]");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		//SbcBasicDao sbcBasicDao = new SbcBasicDao();
		
		for (Element table : texts) {	
			Element tbody = table.select("tbody").first();
			Elements trs = tbody.select("tr");
			for (int i = 0; i < trs.size(); i++){	
				if (i==0){
					continue;
				} else {
					SbcBasicDomain sbcBasicDomain = new SbcBasicDomain();
					Map<String, String> articleUrl = new HashMap<String, String>();
					Elements tds = trs.get(i).select("td");
					sbcBasicDomain.setSbcCode(tds.get(2).text().trim());
					Elements links = tds.get(1).getElementsByTag("a");		
					for (Element link : links) {	
						String linkHref = link.attr("href");
						String linkText = link.text().trim();			
						articleUrl.put("link", linkHref);
					
						articleUrl.put("title", linkText);
						sbcBasicDomain.setSbcName(linkText);
					}	
					articleUrl.put("webTime", tds.get(9).text().trim());
					articleUrl.put("type", type);
					
					sbcBasicDomain.setSbcType(mapType(tds.get(3).text().trim()));
					sbcBasicDomain.setIssueTime(tds.get(4).text().trim());
					sbcBasicDomain.setIssueVolume(tds.get(5).text().trim());
					sbcBasicDomain.setIssueOrg(tds.get(6).text().trim());
					sbcBasicDomain.setFaceValue(tds.get(7).text().trim());
					sbcBasicDomain.setTexture(tds.get(8).text().trim());
					sbcBasicDomain.setInputDate(CommonUtil.getNowDate());
					sbcBasicDomain.setExId(CommonVariables.EXCHANGE_NANJING);
					sbcBasicDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
					System.out.println("sbcBasicDomain " +sbcBasicDomain.toString()) ;
					int sbcId = sbcBasicDao.addSbcBasic(sbcBasicDomain);
					articleUrl.put("sbcId", "" + sbcId);
					sbcBasicDomain = null;
					list.add(articleUrl);
				}			
			}		
			
		}		
		//sbcBasicDao = null;
		return list;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	映射品种类型
	 * Method:		mapType
	 * Author:		1.0 created by lijiaxuan at 2015年7月30日 上午9:01:55
	 *--------------------------------------------------------------------------------------*/
	public String mapType(String type){
		String res = ""; 
		if (type == null ){
			return "";
		}
		type = type.trim();
		if (type.equals("邮票")) {
			res = CommonVariables.SBC_STAMP;
		} else if (type.equals("钱币")) {
			res = CommonVariables.SBC_COIN;
		} else if (type.equals("电话卡")) {
			res = CommonVariables.SBC_CARD;
		} 
		return res;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取品种列表里的所有link：<URL，标题，网站时间，类型>
	 * Method:		1>.	getBreedsArticleLinks
	 * Author:		1.0 created by lijiaxuan at 2015年6月1日 下午7:11:50
	 *--------------------------------------------------------------------------------------*/
	public List<Map<String, String>> getBreedsArticleLinks(Document doc, String type) {
		Elements elements = doc.select("tr[bgcolor=\"#FFFFFF\"]");
		String webTime = "";
		List<Map<String, String>> articleList = new ArrayList<Map<String, String>>();
		for (Element element : elements) {
			
			Elements spans = element.getElementsByTag("span");
			if (spans != null && spans.first() != null) {
				webTime = spans.first().text().trim();
			}
			Elements links = element.getElementsByTag("a");		
			for (Element link : links) {	
				Map<String, String> articleUrl = new HashMap<String, String>();
				String linkHref = link.attr("href");
				String linkText = link.text().trim();			
				articleUrl.put("link", linkHref);
				articleUrl.put("title", linkText);
				articleUrl.put("webTime", webTime);
				articleUrl.put("type", type);
				articleList.add(articleUrl);
			}						
		}
		return articleList;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取品种信息（品种文章，和品种文章里面抽取的品种信息）
	 * Method:		fetchBreeds
	 * Author:		1.0 created by lijiaxuan at 2015年6月2日 9:00:23
	 *--------------------------------------------------------------------------------------*/
	public void fetchBreeds(String url, String type, String time) {	//2015-5-4
		int start = 1;
		boolean haveFetchFlag = false;
		Document doc;
		String pageUrl = "";
		doc = this.fetchDocument(url+ ".html");
		// 1.> 获得最大页数
		int total = getBreedTotalNumber( doc);
		System.out.println("num: " + total);
		for (int i = start; i <= total; i++){
			if (haveFetchFlag) {
				break;
			}
			if (i == start){
				pageUrl = url + ".html";
			} else {
				pageUrl = url + "_" + i +".html";
			}
			Document docs = fetchDocument(pageUrl);
			Elements texts = docs.select("table[cellpadding=\"5\"]");
			System.out.println(texts);
			// 2.> 打开每个新闻列表页，获得每页的news link
			List<Map<String, String>> articleList = extractBreedInfo(docs, type);
			Iterator articleIt = articleList.iterator();
			//int j = 0;
			while ( articleIt.hasNext()){
				Map<String, String> articleMap = (Map<String, String>)articleIt.next();		
				String articleUrl = articleMap.get("link");
				// 3.> 打开每个新闻，获取新闻内容	
				if (isLocalArticle(articleUrl)){
					if ( ! articleUrl.contains(host)){
						articleUrl = host + "/" +articleMap.get("link").replace("../", "");
					}
					System.out.println("articleURl: " +articleUrl);
					extractLocalBreedsArticle(articleUrl, articleMap, null) ;		
				//} else {
				//	extractRemoteNewsArticle(articleUrl, articleMap);
				//}		
					
					
				}
			}
		}	
	}
			
		/*--------------------------------------------------------------------------------------*
		 * Description:	TODO
		 * Method:		9>.	extractNewsArticle
		 * Author:		1.0 created by lijiaxuan at 2015年6月3日 下午8:23:15
		 *--------------------------------------------------------------------------------------*/
		public void extractLocalBreedsArticle(String urlorfile, Map<String, String> articleMap, String type){
			Document doc = null;
			String title = null;
			String issueTime = null;
			String textString = null;
			String img = null;
			String table = null;
			String tablePos = null;
			String imgPos = null;
			BreedArticleDomain breedArticleDomain = new BreedArticleDomain();
			BreedBasicDomain breedBasicDomain = new BreedBasicDomain();
			doc = fetchDoucment(urlorfile, type);
			breedBasicDomain = extractBreedBasic(doc);
			
			breedArticleDomain.setWebTime(articleMap.get("webTime").toString());
			//newsArticleDomain.setArticleUrl(articleMap.get("link").toString());
			breedArticleDomain.setArticleUrl(urlorfile);
			if ((title = extractArticleTitle(doc)) != null) breedArticleDomain.setTitle(title);
			//System.out.println("title: " + title);
			if ((issueTime = extractArticleTime(doc)) != null) breedArticleDomain.setIssueTime(issueTime);
			//System.out.println("issueTime: " + issueTime);
			if ((textString = extractArticleText(doc)) != null) breedArticleDomain.setArticle(textString);
			//System.out.println("textString: " + textString);
			if ((img = extractArticleImg(doc)) != null){
				breedArticleDomain.setImg(img);
				breedArticleDomain.setHaveImg(CommonVariables.TRUE_SHORT_FLAG); 		//有图	
				if ((imgPos = extractImgPos(doc)) != null) breedArticleDomain.setImgPos(imgPos);
				breedBasicDomain.setPicture(img);
			}
			//System.out.println("img: " + img);
			if ((table = extractArticleTable(doc)) != null) {
				breedArticleDomain.setTable(table);	
				breedArticleDomain.setHaveTable(CommonVariables.TRUE_SHORT_FLAG); 	//有表
				if ((tablePos = extractTablePos(doc)) != null) breedArticleDomain.setTablePos(tablePos);
			}
			//System.out.println("table: " + table);
			//NewsArticleDomain.setAnnouceUnit("");
			breedArticleDomain.setFrom(CommonVariables.EXCHANGE_NANJING);
			breedArticleDomain.setFetchTime(CommonUtil.getNowDate());
			breedArticleDomain.setStatus(CommonVariables.FETCH_CONTENT_ORIGIN);
			breedArticleDomain.setType(articleMap.get("type").toString());
			System.out.println("==================== beg ====================");		
			System.out.println(breedArticleDomain.toString());
			System.out.println("==================== end ====================");
			//存储id
			breedBasicDomain.setBreedId(Integer.parseInt(articleMap.get("sbcId")));
			breedBasicDao.addBreedBasic(breedBasicDomain);
			breedArticleDomain.setSbcId(Integer.parseInt(articleMap.get("sbcId")));
			breedArticleDao.addBreedArticle(breedArticleDomain);		
			CommonUtil.toLog(breedArticleDomain.toString(), CommonVariables.LOG_TYPE_SPIDER);
			breedArticleDomain = null;
			breedBasicDomain = null;		
		}
		
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取行业新闻
	 * Method:		fetchIndustryNews
	 * Author:		1.0 created by lijiaxuan at 2015年6月2日 9:00:23
	 *--------------------------------------------------------------------------------------*/
	public void fetchNews(String url, String type, String time) {	//2015-5-4
		int start = 1;
		boolean haveFetchFlag = false;
		Document doc;
		String pageUrl = "";
		doc = this.fetchDocument(url+ ".html");
		// 1.> 获得最大页数
		int total = getTotalNumber( doc);
		System.out.println("num: " + total);
		for (int i = start; i <= total; i++){
			if (haveFetchFlag) {
				break;
			}
			if (i == start){
				pageUrl = url + ".html";
			} else {
				pageUrl = url + "_" + i +".html";
			}
		
			// 2.> 打开每个新闻列表页，获得每页的news link
			List<Map<String, String>> articleList = getArticleLinks(fetchDocument(pageUrl), type);
			Iterator articleIt = articleList.iterator();
			//int j = 0;
			while ( articleIt.hasNext()){
				//j++;
				//if (j > 1) break;
				Map<String, String> articleMap = (Map<String, String>)articleIt.next();		
				String articleUrl = articleMap.get("link");
				if (time != null && haveFetch(articleMap, CommonUtil.getDateFromString(time))){
					haveFetchFlag = true;
					break;
				}
				System.out.println("articleURl: " +articleUrl);
				// 3.> 打开每个新闻，获取新闻内容	
				if (isLocalArticle(articleUrl)){
					if ( ! articleUrl.contains(host)){
						articleUrl = host + "/" +articleMap.get("link").replace("../", "");
						articleUrl = "http://www.zgqbyp.com/html/2015-7/201573046755.html";
					}
					System.out.println("articleURl: " +articleUrl);
					extractLocalNewsArticle(articleUrl, articleMap, null) ;		
				} else {
					extractRemoteNewsArticle(articleUrl, articleMap);
				}												
			}
		}	
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取新闻
	 * Method:		fetchAllNews
	 * Author:		1.0 created by lijiaxuan at 2015年7月30日 上午9:53:24
	 *--------------------------------------------------------------------------------------*/
	public void fetchAllNews(FetchVO fetchVO){
		//通知公告
		String time = fetchVO.getTime();
		String url = "http://www.zgqbyp.com/web/news/10"; 
		String type = CommonVariables.NOTE_NOTICE;
		fetchNews(url, type, time);
		CommonUtil.toLog("通知公告 抓取完毕 " + CommonUtil.getNowDate());
		url = "http://www.zgqbyp.com/web/news/11"; 
		type = CommonVariables.NOTE_EXCHANGIN_DATA;
		fetchNews(url, type, time);
		CommonUtil.toLog("交易数据 抓取完毕 " + CommonUtil.getNowDate());
		url = "http://www.zgqbyp.com/web/news/108"; 
		type = CommonVariables.NOTE_SUBSCRIBE;
		fetchNews(url, type, time);
		CommonUtil.toLog("申购公告 抓取完毕 " + CommonUtil.getNowDate());
		url = "http://www.zgqbyp.com/web/news/109"; 
		type = CommonVariables.NOTE_DEPOSIT;
		fetchNews(url, type, time);
		CommonUtil.toLog("托管公告 抓取完毕 " + CommonUtil.getNowDate());
		url = "http://www.zgqbyp.com/web/news/110"; 
		type = CommonVariables.NOTE_GOOD_REFERENCE_PRICE;
		fetchNews(url, type, time);
		CommonUtil.toLog("现货参考价 抓取完毕 " + CommonUtil.getNowDate());
		url = "http://www.zgqbyp.com/web/news/111"; 
		type = CommonVariables.NOTE_WAREHOUSE;
		fetchNews(url, type, time);
		CommonUtil.toLog("入库公告 抓取完毕 " + CommonUtil.getNowDate());
		/*//行业新闻
		String url = "http://www.zgqbyp.com/web/news/13"; 
		String type = CommonVariables.INFO_INDUSTRY_NEWS;
		//fetchNews(url, type);
		CommonUtil.toLog("行业新闻 抓取完毕 " + CommonUtil.getNow());
		//市场报价
		url = "http://www.zgqbyp.com/web/news/14"; 
		type = CommonVariables.INFO_MARKET_QUOTES;
		fetchNews(url, type);
		CommonUtil.toLog("市场报价 抓取完毕 " + CommonUtil.getNow());
		//中金研究
		url = "http://www.zgqbyp.com/web/news/15"; 
		type = CommonVariables.INFO_CICC_RESEARCH;
		fetchNews(url, type);
		CommonUtil.toLog("中金研究 抓取完毕 " + CommonUtil.getNow());
		//名家专栏
		url = "http://www.zgqbyp.com/web/news/16"; 
		type = CommonVariables.INFO_EXPERT_COLUMN;
		fetchNews(url, type);
		CommonUtil.toLog("名家专栏 抓取完毕 " + CommonUtil.getNow());
		//营销活动
		url = "http://www.zgqbyp.com/web/news/105"; 
		type = CommonVariables.INFO_MARKETING_CAP;
		fetchNews(url, type);
		CommonUtil.toLog("营销活动 抓取完毕 " + CommonUtil.getNow());*/
	}
	
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取品种
	 * Method:		fetchAllBreeds
	 * Author:		1.0 created by lijiaxuan at 2015年7月30日 上午9:52:53
	 *--------------------------------------------------------------------------------------*/
	public void fetchAllBreeds(FetchVO fetchVO){
		String time = fetchVO.getTime();
		String url = "http://www.zgqbyp.com/web/cpml/index";
		//String url = "http://www.zgqbyp.com/web/news/33"; 
		String type = CommonVariables.MESS_BREED_INFO;
		fetchBreeds(url, type, time);
		CommonUtil.toLog("品种信息 抓取完毕 " + CommonUtil.getNowDate());
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	是否已经抓去过
	 * Method:		haveFetch
	 * Author:		1.0 created by lijiaxuan at 2015年7月30日 上午9:52:25
	 *--------------------------------------------------------------------------------------*/
	public boolean haveFetch(Map<String, String> articleMap, Date time){
		boolean flag= false;
		NewsArticleDao newsArticleDao = new NewsArticleDao();	
		if (time == null) {
			time = new Date();
		}		
		if (CommonUtil.getDateFromString(articleMap.get("webTime")).before(time)){
			flag = true;
		} else if (newsArticleDao.isExistByTitle(articleMap.get("title"))){
			flag = true;
		}
		newsArticleDao = null;
		return flag;
	}
	
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	是否是网站内部的URL
	 * Method:		isLocalArticle
	 * Author:		1.0 created by lijiaxuan at 2015年7月30日 上午9:51:58
	 *--------------------------------------------------------------------------------------*/
	public boolean isLocalArticle(String articleUrl){
		if (articleUrl.contains("http://www.zgqbyp.com/")) {
			return true;
		}
		if (articleUrl.contains("http") || articleUrl.contains("www")){
			return false;
		}
		return true;
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
				if (link.text().trim().equals(">>")) {
					String linkHref = link.attr("href");
					String linkText = link.text().trim();
					String[] hrefs = linkHref.split("_");				
					number = Integer.parseInt(hrefs[hrefs.length - 1].replace(".html", "").trim());
				}
			}
		}
		return number;
	}
	public int getBreedTotalNumber(Document doc) {
		int number = 4;
		/*Elements elements = doc.select("div.fenye");
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
					System.out.println("number " + number);
				}
			}
		}*/
		return number;
	}

	

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		extractBreedBasic
	 * Author:		1.0 created by lijiaxuan at 2015年6月13日 下午6:21:09
	 *--------------------------------------------------------------------------------------*/
	public BreedBasicDomain extractBreedBasic(Document doc){
		BreedBasicDomain breedBasicDomain = new BreedBasicDomain();	
		Elements texts = doc.select("div.neirong_text");		//正文
		if (texts!=null && texts.first() != null) {
			Element text = texts.first();
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
		}
		return breedBasicDomain;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		extractRemoteNewsArticle
	 * Author:		1.0 created by lijiaxuan at 2015年7月8日 上午10:41:41
	 *--------------------------------------------------------------------------------------*/
	public void extractRemoteNewsArticle(String url, Map<String, String> articleMap){
		NewsArticleDomain newsArticleDomain = new NewsArticleDomain();
		
		newsArticleDomain.setTitle(articleMap.get("title").toString());
		newsArticleDomain.setFrom(CommonVariables.EXCHANGE_NANJING);
		newsArticleDomain.setFetchTime(CommonUtil.getNowDate());
		newsArticleDomain.setWebTime(articleMap.get("webTime").toString());
		newsArticleDomain.setStatus(CommonVariables.FETCH_CONTENT_URL);
		newsArticleDomain.setType(articleMap.get("type").toString());
		newsArticleDomain.setArticleUrl(articleMap.get("link").toString());
		System.out.println("==================== beg ====================");		
		System.out.println(newsArticleDomain.toString());
		System.out.println("==================== end ====================");
		NewsArticleDao NewsArticleDao = new NewsArticleDao();
		NewsArticleDao.addNewsArticle(newsArticleDomain);
		newsArticleDomain = null;
		NewsArticleDao = null;
	}
	
	/*public void fetchQuotations(FetchVO fetchVO){
	host = "180.97.2.74";
	init(host);
	String url = "http://180.97.2.74:16929/tradeweb/hq/getHqV_lbData.jsp";
	Document doc = fetchDocument(url);
	String jsonStr = doc.select("body").text().toString();
	System.out.println();
	String exId = CommonVariables.EXCHANGE_NANJING;
	//RedisDao redisDao = new RedisDao();
	quotesRedisDao.addQuotes(JSONObject.fromObject(jsonStr), exId);
}
*/
}