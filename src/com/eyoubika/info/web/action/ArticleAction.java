package com.eyoubika.info.web.action;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.info.web.VO.ArticleVO;
import com.eyoubika.info.service.IArticleService;
import com.eyoubika.common.PageInfo;
/*==========================================================================================*
 * Description:	TODO
 * Class:		ArticleAction
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 broadskyer@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年10月8日 上午8:46:45
 *==========================================================================================*/
public class ArticleAction extends BaseAction {
    private IArticleService articleService;
    private ArticleVO articleVO;
	
	public ArticleVO getArticleVO() {
		return articleVO;
	}

	public void setArticleVO(ArticleVO articleVO) {
		this.articleVO = articleVO;
	}

	public IArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	获取文章
	 * Method:		getArticle
	 * Author:		1.0 created by lijiaxuan at 2015年6月27日 上午10:13:53
	 *--------------------------------------------------------------------------------------*/
	public String getArticle(){	
		ArticleVO ArticleVO = new ArticleVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null == request.getParameter("userId") && null == request.getParameter("articleId")) {
			//if (null == request.getParameter("userId") || null == request.getParameter("token")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		ConverterUtil.RequestToVO(request, ArticleVO);
		//> 3. do register
		this.jsonData = articleService.getArticle(ArticleVO);
		//> 4. return json
		ArticleVO = null;
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getArticleList
	 * Author:		1.0 created by lijiaxuan at 2015年6月28日 下午2:49:27
	 *--------------------------------------------------------------------------------------*/
	public String getArticleList(){	
		ArticleVO ArticleVO = new ArticleVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		PageInfo pageInfo= new PageInfo();	
		if (null == request.getParameter("userId") || null == request.getParameter("type") || null == request.getParameter("limit") ) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}	
		pageInfo.assign(request);
		ConverterUtil.RequestToVO(request, ArticleVO);
		//> 3. do register
		this.jsonData = articleService.getArticleList(ArticleVO, pageInfo);
		//> 4. return json
		ArticleVO = null;
		pageInfo = null;
		return SUCCESS;
	}
	
}