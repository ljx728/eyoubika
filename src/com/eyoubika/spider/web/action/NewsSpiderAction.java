package com.eyoubika.spider.web.action;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.spider.web.VO.FetchVO;
import com.eyoubika.spider.service.INjwjsSpiderService;

/*==========================================================================================*
 * Description:	定义了用户控制器
 * Class:		UserAction
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-21 14:17:33
 *==========================================================================================*/
public class NewsSpiderAction extends BaseAction {
	
	
    private INjwjsSpiderService njwjsSpiderService;

	

	public INjwjsSpiderService getNjwjsSpiderService() {
		return njwjsSpiderService;
	}
	public void setNjwjsSpiderService(INjwjsSpiderService njwjsSpiderService) {
		this.njwjsSpiderService = njwjsSpiderService;
	}

	public String fetchAllNews(){	
		FetchVO fetchVO = new FetchVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		ConverterUtil.RequestToVO(request, fetchVO);	
		this.jsonData = njwjsSpiderService.fetchAllNews(fetchVO);
		fetchVO = null;
		return SUCCESS;
	}	
	
	public String fetchNewsByExId(){	
		FetchVO fetchVO = new FetchVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		ConverterUtil.RequestToVO(request, fetchVO);	
		if (fetchVO.getExtId().equals(CommonVariables.EXCHANGE_NANJING)){
			this.jsonData = njwjsSpiderService.fetchAllNews(fetchVO);
		}
	
		fetchVO = null;
		return SUCCESS;
	}	
	
}