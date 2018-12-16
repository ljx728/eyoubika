package com.eyoubika.info.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eyoubika.spider.domain.ArticleDomain;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.PageInfo;
import com.eyoubika.info.service.IInvestArticleService;
import com.eyoubika.info.web.VO.ArticleVO;
import com.eyoubika.info.web.VO.InvestArticleVO;
import com.eyoubika.info.application.InvestArticleAL;
import com.eyoubika.info.domain.InvestArticleDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class InvestArticleServiceImpl extends BaseService implements IInvestArticleService{
	private InvestArticleAL investArticleAL;	//
	private InvestArticleDomain investArticleDomain;	//
	public InvestArticleDomain getInvestArticleDomain(){
		return investArticleDomain;
	}
	public void setInvestArticleDomain(InvestArticleDomain investArticleDomain){
		this.investArticleDomain = investArticleDomain;
	}
	public InvestArticleAL getInvestArticleAL(){
		return investArticleAL;
	}
	public void setInvestArticleAL(InvestArticleAL investArticleAL){
		this.investArticleAL = investArticleAL;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addInvestArticle(InvestArticleVO investArticleVO){
		investArticleDomain.init();
		ConverterUtil.VOToDomain(investArticleVO, investArticleDomain);
		investArticleAL.addInvestArticle(investArticleDomain);
		return this.buildRetData( investArticleVO.getUserId() , investArticleVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getInvestArticle(InvestArticleVO investArticleVO){
		InvestArticleDomain	investArticleDomain  = investArticleAL.getInvestArticle(Integer.parseInt(investArticleVO.getArticleId()));
		return this.buildRetData( investArticleVO.getUserId() , investArticleVO.getToken(), investArticleDomain);
	}
	
	public Map<String, Object> editInvestArticle(InvestArticleVO investArticleVO){
		InvestArticleDomain	investArticleDomain  = investArticleAL.editInvestArticle(Integer.parseInt(investArticleVO.getArticleId()));
		return this.buildRetData( investArticleVO.getUserId() , investArticleVO.getToken(), investArticleDomain);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> deleteInvestArticle(InvestArticleVO investArticleVO){
		investArticleDomain.init();
		ConverterUtil.VOToDomain(investArticleVO, investArticleDomain);
		investArticleAL.deleteInvestArticle(investArticleDomain);
		return this.buildRetData( investArticleVO.getUserId() , investArticleVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyInvestArticle(InvestArticleVO investArticleVO){
		investArticleDomain.init();
		ConverterUtil.VOToDomain(investArticleVO, investArticleDomain);
		investArticleAL.modifyInvestArticle(investArticleDomain);
		return this.buildRetData( investArticleVO.getUserId() , investArticleVO.getToken());
	}
	//>. <CustomFileTag>
	public Map<String, Object> getInvestArticleList(InvestArticleVO investArticleVO, PageInfo pageInfo){	
		Map<String, Object> params = new HashMap<String, Object>();
		investArticleDomain.init();
		ConverterUtil.VOToDomain(investArticleVO, investArticleDomain);
		if (investArticleDomain.getStatus() == null){
			investArticleDomain.setStatus("01,00");  //默认只显示全部和手机端的。
		}
		PageInfo res = investArticleAL.getInvestArticleList(investArticleDomain, pageInfo);	
		List<InvestArticleDomain>  listDomains = (List<InvestArticleDomain>) res.getResult();// = newsAL.getNewsList(newsBasicDomain, pageInfo);			
		for (int i = 0 ; i < listDomains.size(); i++){	
			//清空正文
			listDomains.get(i).setContent("");		
		}
		params.put("size", res.getTotalNum());
		params.put("next", res.getNext());
		params.put("list", listDomains);		
		
		return this.buildRetData(investArticleVO.getUserId(), investArticleVO.getToken(), params);
	}
	//>. <CustomFileTag>
}
