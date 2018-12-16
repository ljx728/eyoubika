package com.eyoubika.info.service;
import java.util.Map;

import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.PageInfo;
import com.eyoubika.info.service.IInvestArticleService;
import com.eyoubika.info.web.VO.InvestArticleVO;
import com.eyoubika.info.application.InvestArticleAL;
import com.eyoubika.info.domain.InvestArticleDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public interface IInvestArticleService{
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addInvestArticle(InvestArticleVO investArticleVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getInvestArticle(InvestArticleVO investArticleVO);
	
	public Map<String, Object> editInvestArticle(InvestArticleVO investArticleVO);
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> deleteInvestArticle(InvestArticleVO investArticleVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyInvestArticle(InvestArticleVO investArticleVO);
	//>. <CustomFileTag>
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getInvestArticleList
	 * Author:		1.0 created by lijiaxuan at 2015年11月26日 下午4:39:40
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getInvestArticleList(InvestArticleVO investArticleVO, PageInfo pageInfo);
	
	//>. <CustomFileTag>
}
