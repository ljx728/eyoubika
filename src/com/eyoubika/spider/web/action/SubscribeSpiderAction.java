package com.eyoubika.spider.web.action;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.spider.web.VO.FetchVO;
import com.eyoubika.spider.service.ISubscribeSpiderService;

/*==========================================================================================*
 * Description:	TODO
 * Class:		SubscribeSpiderAction
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年10月6日 下午2:20:08
 *==========================================================================================*/
public class SubscribeSpiderAction extends BaseAction {
	
	
    private ISubscribeSpiderService subscribeSpiderService;

	

	public ISubscribeSpiderService getSubscribeSpiderService() {
		return subscribeSpiderService;
	}
	public void setSubscribeSpiderService(ISubscribeSpiderService subscribeSpiderService) {
		this.subscribeSpiderService = subscribeSpiderService;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户注册
	 * Method:		register
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-21 14:22:16
	 *--------------------------------------------------------------------------------------*/
	public String fetchRealTimeQuotes(){	
	//	UserVO UserVO = new UserVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		//ConverterUtil.RequestToVO(request, UserVO);
		//> 3. do register
		//this.jsonData = subscribeSpiderService.fetchRealTimeQuotes();
		 subscribeSpiderService.fetchRealTimeQuotes();
		//> 4. return json
		//UserVO = null;
		return SUCCESS;
	}	
	public String fetchAllNews(){	
		FetchVO fetchVO = new FetchVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		ConverterUtil.RequestToVO(request, fetchVO);
		
		this.jsonData = subscribeSpiderService.fetchAllNews(fetchVO);
		 subscribeSpiderService.fetchRealTimeQuotes();
		//> 4. return json
		//UserVO = null;
		return SUCCESS;
	}	
	
	
	
}