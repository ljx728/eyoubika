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
import com.eyoubika.spider.service.IEpianhongSpiderService;
import com.eyoubika.spider.service.INjwjsSpiderService;

/*==========================================================================================*
 * Description:	定义了用户控制器
 * Class:		UserAction
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-21 14:17:33
 *==========================================================================================*/
public class EpianhongSpiderAction extends BaseAction {
	
	private FetchVO fetchVO;
    private IEpianhongSpiderService epianhongSpiderService;


	public FetchVO getFetchVO() {
		return fetchVO;
	}
	public void setFetchVO(FetchVO fetchVO) {
		this.fetchVO = fetchVO;
	}
	public IEpianhongSpiderService getEpianhongSpiderService() {
		return epianhongSpiderService;
	}
	public void setEpianhongSpiderService(
			IEpianhongSpiderService epianhongSpiderService) {
		this.epianhongSpiderService = epianhongSpiderService;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户注册
	 * Method:		register
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-21 14:22:16
	 *--------------------------------------------------------------------------------------*/
	public String fetchQuotations(){	
		HttpServletRequest request = ServletActionContext.getRequest();
		ConverterUtil.RequestToVO(request, fetchVO);
		this.jsonData =  epianhongSpiderService.fetchQuotations(fetchVO);
		return SUCCESS;
	}	
	
	public String fetchSbcs(){	
		HttpServletRequest request = ServletActionContext.getRequest();
		ConverterUtil.RequestToVO(request, fetchVO);
		this.jsonData =  epianhongSpiderService.fetchSbcs(fetchVO);
		return SUCCESS;
	}	
}