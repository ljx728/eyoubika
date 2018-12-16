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
import com.eyoubika.spider.service.IZongyihuiSpiderService;

/*==========================================================================================*
 * Description:	定义了用户控制器
 * Class:		UserAction
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-21 14:17:33
 *==========================================================================================*/
public class QuotationsSpiderAction extends BaseAction {
	
	
    private IZongyihuiSpiderService zongyihuiSpiderService;

	

	public IZongyihuiSpiderService getZongyihuiSpiderService() {
		return zongyihuiSpiderService;
	}

	public void setZongyihuiSpiderService(
			IZongyihuiSpiderService zongyihuiSpiderService) {
		this.zongyihuiSpiderService = zongyihuiSpiderService;
	}

	public String fetchQuotations(){	
		FetchVO fetchVO = new FetchVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		ConverterUtil.RequestToVO(request, fetchVO);	
		this.jsonData = zongyihuiSpiderService.fetchQuotations(fetchVO);
		fetchVO = null;
		return SUCCESS;
	}	
	
	public String fetchNewsByExId(){	
		/*FetchVO fetchVO = new FetchVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		ConverterUtil.RequestToVO(request, fetchVO);	
		if (fetchVO.getExtId().equals(CommonVariables.EXCHANGE_NANJING)){
			this.jsonData = zongyihuiSpiderService.fetchAllNews(fetchVO);
		}
	
		fetchVO = null;*/
		return SUCCESS;
	}	
	
}