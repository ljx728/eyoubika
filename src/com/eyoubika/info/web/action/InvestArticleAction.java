package com.eyoubika.info.web.action;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.PageInfo;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.info.service.IInvestArticleService;
import com.eyoubika.info.web.VO.InvestArticleVO;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
 *==========================================================================================*/
public class InvestArticleAction extends BaseAction {
	private IInvestArticleService investArticleService;	//<<attrNote>>
	private InvestArticleVO investArticleVO;	//<<attrNote>>
	public IInvestArticleService getInvestArticleService(){
		return investArticleService;
	}
	public void setInvestArticleService(IInvestArticleService investArticleService){
		this.investArticleService = investArticleService;
	}
	public InvestArticleVO getInvestArticleVO(){
		return investArticleVO;
	}
	public void setInvestArticleVO(InvestArticleVO investArticleVO){
		this.investArticleVO = investArticleVO;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public String addInvestArticle(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		investArticleVO.init();
		ConverterUtil.RequestToVO(request, investArticleVO);
		//> 3. do
		this.jsonData = investArticleService.addInvestArticle(investArticleVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public String getInvestArticle(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		investArticleVO.init();
		ConverterUtil.RequestToVO(request, investArticleVO);
		
		//> 3. do
		this.jsonData = investArticleService.getInvestArticle(investArticleVO);
		return SUCCESS;
	}
	
	public String editInvestArticle(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		investArticleVO.init();
		ConverterUtil.RequestToVO(request, investArticleVO);
		//> 3. do
		this.jsonData = investArticleService.editInvestArticle(investArticleVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public String deleteInvestArticle(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		investArticleVO.init();
		ConverterUtil.RequestToVO(request, investArticleVO);
		//> 3. do
		this.jsonData = investArticleService.deleteInvestArticle(investArticleVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public String modifyInvestArticle(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		investArticleVO.init();
		ConverterUtil.RequestToVO(request, investArticleVO);
		//> 3. do
		this.jsonData = investArticleService.modifyInvestArticle(investArticleVO);
		return SUCCESS;
	}
	//>. <CustomFileTag>
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getInvestArticleList
	 * Author:		1.0 created by lijiaxuan at 2015年11月26日 下午4:43:22
	 *--------------------------------------------------------------------------------------*/
	public String getInvestArticleList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		PageInfo pageInfo= new PageInfo();	
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		investArticleVO.init();
		pageInfo.assign(request);
		ConverterUtil.RequestToVO(request, investArticleVO);
		//> 3. do
		this.jsonData = investArticleService.getInvestArticleList(investArticleVO, pageInfo);
		pageInfo = null;
		return SUCCESS;
	}
	
	//>. <CustomFileTag>
}
