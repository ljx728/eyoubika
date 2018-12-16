package com.eyoubika.info.web.action;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.info.web.VO.NewsVO;
import com.eyoubika.info.service.INewsService;
import com.eyoubika.common.PageInfo;
/*==========================================================================================*
 * Description:	定义了用户控制器
 * Class:		NewsAction
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-6-27 9:44:33
 *==========================================================================================*/
public class SubscribeNewsAction extends BaseAction {
  /*  private ISubscribeNewsService subscribeNewsService;
    private NewsVO newsVO;
	
	public NewsVO getNewsVO() {
		return newsVO;
	}

	public void setNewsVO(NewsVO newsVO) {
		this.newsVO = newsVO;
	}

	public ISubscribeNewsService getSubscribeNewsService() {
		return subscribeNewsService;
	}

	public void setSubscribeNewsService(ISubscribeNewsService subscribeNewsService) {
		this.subscribeNewsService = subscribeNewsService;
	}

	--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getSubscribeNews
	 * Author:		1.0 created by lijiaxuan at 2015年10月7日 下午11:54:27
	 *--------------------------------------------------------------------------------------
	public String getSubscribeNews(){	
		NewsVO NewsVO = new NewsVO();
		HttpServletRequest request = ServletActionContext.getRequest();

		if (null == request.getParameter("userId") && null == request.getParameter("newsId")) {
			//if (null == request.getParameter("userId") || null == request.getParameter("token")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}

		ConverterUtil.RequestToVO(request, NewsVO);
		//> 3. do register
		this.jsonData = newsService.getNews(NewsVO);
		//> 4. return json
		NewsVO = null;
		return SUCCESS;
	}
	
	
	--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getNewsList
	 * Author:		1.0 created by lijiaxuan at 2015年6月28日 下午2:49:27
	 *--------------------------------------------------------------------------------------
	public String getSubscribeNewsList(){	
		NewsVO NewsVO = new NewsVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		PageInfo pageInfo= new PageInfo();

		if (null == request.getParameter("userId") ) {
			//if (null == request.getParameter("userId") || null == request.getParameter("token")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}	
		pageInfo.assign(request);
		System.out.println("pageInfo.start " + pageInfo.getStart());
		System.out.println("pageInfo.next " + pageInfo.getNext());
		ConverterUtil.RequestToVO(request, NewsVO);
		//> 3. do register
		this.jsonData = newsService.getNewsList(NewsVO, pageInfo);
		//> 4. return json
		NewsVO = null;
		return SUCCESS;
	}*/
}