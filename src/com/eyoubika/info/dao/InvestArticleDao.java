package com.eyoubika.info.dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseDao;
import com.eyoubika.common.PageInfo;
import com.eyoubika.common.YbkException;
import com.eyoubika.info.domain.ArticleSimpleDomain;
import com.eyoubika.info.domain.InvestArticleDomain;
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
 * History:		1.0 created by lijiaxuan at 2015-11-23 19:17:01
 *==========================================================================================*/
public class InvestArticleDao extends BaseDao {
	String nameSpace = "tp_InvestArticle";
	private InvestArticleDomain investArticleDomain;	//<<attrNote>>
	public InvestArticleDomain getInvestArticleDomain(){
		return investArticleDomain;
	}
	public void setInvestArticleDomain(InvestArticleDomain investArticleDomain){
		this.investArticleDomain = investArticleDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-23 19:17:01
	 *--------------------------------------------------------------------------------------*/
	public int addInvestArticle(InvestArticleDomain investArticleDomain){
		int insertId = 0;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.insert(nameSpace +".insertInvestArticle", investArticleDomain);
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
	 * Author:		1.0 created by lijiaxuan at 2015-11-23 19:17:01
	 *--------------------------------------------------------------------------------------*/
	public int deleteInvestArticle(Integer articleId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteInvestArticle", articleId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-23 19:17:01
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
	 * Author:		1.0 created by lijiaxuan at 2015-11-23 19:17:01
	 *--------------------------------------------------------------------------------------*/
	public void modifyInvestArticle(InvestArticleDomain investArticleDomain){
		try {
			sqlMapClient.update(nameSpace +".updateInvestArticle", investArticleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-23 19:17:01
	 *--------------------------------------------------------------------------------------*/
	public InvestArticleDomain findInvestArticle(Integer articleId){
		try {
			return (InvestArticleDomain) sqlMapClient.queryForObject(nameSpace +".selectInvestArticle", articleId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-23 19:17:01
	 *--------------------------------------------------------------------------------------*/
	public InvestArticleDomain findInvestArticleByDomain(InvestArticleDomain investArticleDomain){
		try {
			return (InvestArticleDomain) sqlMapClient.queryForObject(nameSpace +".selectInvestArticleByDomain", investArticleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-23 19:17:01
	 *--------------------------------------------------------------------------------------*/
	public List<InvestArticleDomain> queryInvestArticle(InvestArticleDomain investArticleDomain){
		try {
			return (List<InvestArticleDomain>) sqlMapClient.queryForList(nameSpace +".selectInvestArticleList", investArticleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-23 19:17:01
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(InvestArticleDomain investArticleDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", investArticleDomain);
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
	 * Method:		queryInvestArticleListInPage
	 * Author:		1.0 created by lijiaxuan at 2015年11月26日 下午4:24:52
	 *--------------------------------------------------------------------------------------*/
	public PageInfo queryInvestArticleListInPage(InvestArticleDomain investArticleDomain, PageInfo pageInfo){
		try {
			Map<String,Object> map = ConverterUtil.objectToMap(investArticleDomain);		
			int total =  (int) sqlMapClient.queryForObject(nameSpace +".selectInvestArticleListCount", map);		
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
			if (investArticleDomain.getExId() != null){
				String[] exIdArray = investArticleDomain.getExId().split(",");
				List<String> exIdList = new ArrayList<String>();
				int size = exIdArray.length;
				for (int i = 0; i < size; i++){  
					if (!exIdArray[i].equals("")){
						exIdList.add(exIdArray[i]);
					}		
				}
				
				map.put("exIds", exIdList);
			}
			if (investArticleDomain.getStatus() != null){
				String[] statusArray = investArticleDomain.getStatus().split(",");
				List<String> statusList = new ArrayList<String>();
				int size = statusArray.length;
				for (int i = 0; i < size; i++){  
					if (!statusArray[i].equals("")){
						statusList.add(statusArray[i]);
					}		
				}
				
				map.put("statusIds", statusList);
			}
			pageInfo.setResult((List<InvestArticleDomain>) sqlMapClient.queryForList(nameSpace +".selectInvestArticleListInPage", map));

			return pageInfo;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	public List<ArticleSimpleDomain> queryInvestArticleSimple(ArrayList<Integer> articleIds){
		try {
			Map<String,Object> map = new HashMap<String, Object>();		
			map.put("articleIds", articleIds);
			return (List<ArticleSimpleDomain>) sqlMapClient.queryForList(nameSpace +".selectInvestArticleSimple", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	//>. <CustomFileTag>
}
