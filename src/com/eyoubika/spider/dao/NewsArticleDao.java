package com.eyoubika.spider.dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.spider.domain.NewsArticleDomain;
import com.eyoubika.spider.domain.NewsBasicDomain;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.PageInfo;
public class NewsArticleDao extends BaseDao {
	String nameSpace = "ta_FetchNewsArticle";
	NewsArticleDomain newsArticleDomain;	//<<attrNote>>
	NewsBasicDomain newsBasicDomain;
	
	public NewsBasicDomain getNewsBasicDomain() {
		return newsBasicDomain;
	}
	public void setNewsBasicDomain(NewsBasicDomain newsBasicDomain) {
		this.newsBasicDomain = newsBasicDomain;
	}
	public NewsArticleDomain getNewsArticleDomain(){
		return newsArticleDomain;
	}
	public void setNewsArticleDomain(NewsArticleDomain newsArticleDomain){
		this.newsArticleDomain = newsArticleDomain;
	}
	public int addNewsArticle(NewsArticleDomain newsArticleDomain){
		int insertId = 0;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.insert(nameSpace +".insertNewsArticle", newsArticleDomain);
		} catch (SQLException e) {
			ob = null;
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		insertId = Integer.parseInt(ob.toString());
		ob = null;
		return insertId;
	}
	public int deleteNewsArticle(int newsId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteNewsArticle", newsId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public int deleteAll(){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteAll");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public void modifyNewsArticle(NewsArticleDomain newsArticleDomain){
		try {
			sqlMapClient.update(nameSpace +".updateNewsArticle", newsArticleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public NewsArticleDomain findNewsArticle(int newsId){
		try {
			return (NewsArticleDomain) sqlMapClient.queryForObject(nameSpace +".selectNewsArticle", newsId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public NewsArticleDomain findNewsArticleByDomain(NewsArticleDomain newsArticleDomain){
		try {
			return (NewsArticleDomain) sqlMapClient.queryForObject(nameSpace +".selectNewsArticleByDomain", newsArticleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public List<NewsArticleDomain> queryNewsArticle(NewsArticleDomain newsArticleDomain){
		try {
			return (List<NewsArticleDomain>) sqlMapClient.queryForList(nameSpace +".selectNewsArticleList", newsArticleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public boolean isExistByDomain(NewsArticleDomain newsArticleDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", newsArticleDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//---------add-------------------
	public boolean isExistByTitle(String title){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByTitle", title);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	
	public List<NewsBasicDomain> queryNewsList(NewsBasicDomain newsBasicDomain){
		try {
			
			return (List<NewsBasicDomain>) sqlMapClient.queryForList(nameSpace +".selectNewsBasicList", newsArticleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public PageInfo queryNewsListInPage(NewsBasicDomain newsBasicDomain, PageInfo pageInfo){
		try {
			//this.setPage();
			System.out.println("pageInfo.start " + pageInfo.getStart());
			Map<String,Object> map = ConverterUtil.objectToMap(newsBasicDomain);		
			int total =  (int) sqlMapClient.queryForObject(nameSpace +".selectNewsBasicListCount", map);
		
			pageInfo.setTotalNum(total);
			
			if (pageInfo.getStart()!= null && pageInfo.getLimit() != null){
				map.put("start", pageInfo.getStart()); 
				map.put("limit", pageInfo.getLimit()); 			
			}
			if (pageInfo.getFf()!= null){
				String ff = CommonUtil.buildAbbr(nameSpace) + "_" + CommonUtil.upperFirst(pageInfo.getFf());
				map.put("ff", ff);
			}
			if (pageInfo.getRank()!= null){			
				map.put("rank", pageInfo.getRank());
			}
			if (newsBasicDomain.getFrom() != null){
				String[] exIdArray = newsBasicDomain.getFrom().split(",");
				List<String> exIdList = new ArrayList<String>();
				int size = exIdArray.length;
				//String sbcIdList = "";
				for (int i = 0; i < size; i++){  
					if (!exIdArray[i].equals("")){
						exIdList.add(exIdArray[i]);
					}		
				}
				
				map.put("exIds", exIdList);
			}
			
			
			//pageInfo.setNext(pageInfo.getStart() +  pageInfo.getLimit());
			pageInfo.setResult((List<NewsBasicDomain>) sqlMapClient.queryForList(nameSpace +".selectNewsBasicListInPage", map));
			System.out.println("start " + map.get("start"));
			return pageInfo;//(List<NewsBasicDomain>) sqlMapClient.queryForList(nameSpace +".selectNewsBasicListInPage", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
}
