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
 * History:		1.0 created by lijiaxuan at 2015-10-09 15:37:49
 *==========================================================================================*/
public class DepositArticleDao extends BaseDao {
	String nameSpace = "ta_DepositArticle";
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
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 15:37:49
	 *--------------------------------------------------------------------------------------*/
	public int addDepositArticle(ArticleDomain articleDomain){
		int insertId = 0;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.insert(nameSpace +".insertDepositArticle", articleDomain);
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
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 15:37:49
	 *--------------------------------------------------------------------------------------*/
	public int deleteDepositArticle(Integer articleId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteDepositArticle", articleId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 15:37:49
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
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 15:37:49
	 *--------------------------------------------------------------------------------------*/
	public void modifyDepositArticle(ArticleDomain articleDomain){
		try {
			sqlMapClient.update(nameSpace +".updateDepositArticle", articleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 15:37:49
	 *--------------------------------------------------------------------------------------*/
	public ArticleDomain findDepositArticle(Integer articleId){
		try {
			return (ArticleDomain) sqlMapClient.queryForObject(nameSpace +".selectDepositArticle", articleId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 15:37:49
	 *--------------------------------------------------------------------------------------*/
	public ArticleDomain findDepositArticleByDomain(ArticleDomain articleDomain){
		try {
			return (ArticleDomain) sqlMapClient.queryForObject(nameSpace +".selectDepositArticleByDomain", articleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 15:37:49
	 *--------------------------------------------------------------------------------------*/
	public List<ArticleDomain> queryDepositArticle(ArticleDomain articleDomain){
		try {
			return (List<ArticleDomain>) sqlMapClient.queryForList(nameSpace +".selectDepositArticleList", articleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-10-09 15:37:49
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
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		queryDepositArticleListInPage
	 * Author:		1.0 created by lijiaxuan at 2015年10月9日 下午3:41:33
	 *--------------------------------------------------------------------------------------*/
	public PageInfo queryDepositArticleListInPage(ArticleDomain articleDomain, PageInfo pageInfo){
		try {
			Map<String,Object> map = ConverterUtil.objectToMap(articleDomain);		
			int total =  (int) sqlMapClient.queryForObject(nameSpace +".selectDepositArticleListCount", map);		
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
			pageInfo.setResult((List<ArticleDomain>) sqlMapClient.queryForList(nameSpace +".selectDepositArticleListInPage", map));

			return pageInfo;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		findMaxDateByExId
	 * Author:		1.0 created by lijiaxuan at 2015年10月9日 下午3:39:50
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
