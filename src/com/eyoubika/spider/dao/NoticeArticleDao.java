package com.eyoubika.spider.dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseDao;
import com.eyoubika.common.PageInfo;
import com.eyoubika.common.YbkException;
import com.eyoubika.spider.domain.ArticleDomain;
import com.eyoubika.spider.domain.ArticleDomain;
import com.eyoubika.spider.domain.ArticleDomain;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-10-09 17:25:46
 *==========================================================================================*/
public class NoticeArticleDao extends BaseDao {
	String nameSpace = "ta_NoticeArticle";
	private ArticleDomain articleDomain;	//<<attrNote>>
	public ArticleDomain getArticleDomain(){
		return articleDomain;
	}
	public void setArticleDomain(ArticleDomain articleDomain){
		this.articleDomain = articleDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 17:25:46
	 *--------------------------------------------------------------------------------------*/
	public int addNoticeArticle(ArticleDomain articleDomain){
		int insertId = 0;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.insert(nameSpace +".insertNoticeArticle", articleDomain);
		} catch (SQLException e) {
			ob = null;
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		insertId = Integer.parseInt(ob.toString());
		ob = null;
		return insertId;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 17:25:46
	 *--------------------------------------------------------------------------------------*/
	public int deleteNoticeArticle(Integer articleId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteNoticeArticle", articleId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 17:25:46
	 *--------------------------------------------------------------------------------------*/
	public int deleteAll(){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteAll");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 17:25:46
	 *--------------------------------------------------------------------------------------*/
	public void modifyNoticeArticle(ArticleDomain articleDomain){
		try {
			sqlMapClient.update(nameSpace +".updateNoticeArticle", articleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 17:25:46
	 *--------------------------------------------------------------------------------------*/
	public ArticleDomain findNoticeArticle(Integer articleId){
		try {
			return (ArticleDomain) sqlMapClient.queryForObject(nameSpace +".selectNoticeArticle", articleId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 17:25:46
	 *--------------------------------------------------------------------------------------*/
	public ArticleDomain findNoticeArticleByDomain(ArticleDomain articleDomain){
		try {
			return (ArticleDomain) sqlMapClient.queryForObject(nameSpace +".selectNoticeArticleByDomain", articleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 17:25:46
	 *--------------------------------------------------------------------------------------*/
	public List<ArticleDomain> queryNoticeArticle(ArticleDomain articleDomain){
		try {
			return (List<ArticleDomain>) sqlMapClient.queryForList(nameSpace +".selectNoticeArticleList", articleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 17:25:46
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(ArticleDomain articleDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", articleDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	public PageInfo queryNoticeArticleListInPage(ArticleDomain articleDomain, PageInfo pageInfo){
		try {
			Map<String,Object> map = ConverterUtil.objectToMap(articleDomain);		
			int total =  (int) sqlMapClient.queryForObject(nameSpace +".selectNoticeArticleListCount", map);		
			pageInfo.setTotalNum(total);
			
			if (pageInfo.getStart()!= null && pageInfo.getLimit() != null){
				map.put("start", pageInfo.getStart()); 
				map.put("limit", pageInfo.getLimit()); 			
			}
			if (pageInfo.getFf()!= null){
				String ff = CommonUtil.buildAbbr(nameSpace) + "_" + CommonUtil.upperFirst(pageInfo.getFf());
				if (ff.equals("newsId")){
					ff = "articleId";
				}
				map.put("ff", ff);
			}
			if (pageInfo.getRank()!= null){			
				map.put("rank", pageInfo.getRank());
			}
			if (articleDomain.getExId() != null){
				String[] exIdArray = articleDomain.getExId().split(",");
				List<String> exIdList = new ArrayList<String>();
				int size = exIdArray.length;
				for (int i = 0; i < size; i++){  
					if (!exIdArray[i].equals("")){
						exIdList.add(exIdArray[i]);
					}		
				}
				map.put("exIds", exIdList);
			}
			pageInfo.setResult((List<ArticleDomain>) sqlMapClient.queryForList(nameSpace +".selectNoticeArticleListInPage", map));

			return pageInfo;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getMaxDateByExId
	 * Author:		1.0 created by lijiaxuan at 2015年10月8日 下午4:38:42
	 *--------------------------------------------------------------------------------------*/
	public String findMaxDateByExId(String exId){
		String ret = null;
		Object ob = new Object();
		try {
			ret = (String) sqlMapClient.queryForObject(nameSpace +".selectMaxDateByExId", exId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
}
