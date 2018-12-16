package com.eyoubika.system.web.action;
import com.eyoubika.common.BaseAction;
import com.eyoubika.common.YbkException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.system.web.VO.SmsVO;
import com.eyoubika.system.service.ISmsService;
import com.eyoubika.util.ContentUtil;
import com.eyoubika.util.ConverterUtil;

/*==========================================================================================*
 * Description:	定义了短信服务的控制器实现
 * Class:		SmsAction
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-26 9:00:30
 *==========================================================================================*/
public class SmsAction extends BaseAction {
	private ISmsService smsService;
	
	public ISmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(ISmsService smsService) {
		this.smsService = smsService;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取验证码
	 * Method:		getAuthcode
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-26 9:30:22
	 *--------------------------------------------------------------------------------------*/
	public String getAuthcode(){
		SmsVO smsVO = new SmsVO();
		System.out.println("userAction user() ");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		/*//> 1.check params
		if (null == request.getParameter("pnumber") || null == request.getParameter("password") || null == request.getParameter("authcode")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}*/	 	
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, smsVO);
		//> 3. do 
		this.jsonData = smsService.getAuthcode(smsVO);
		//> 4. return json
		smsVO = null;
		return  SUCCESS;
	}
	
	public String checkAuthcode(){
		SmsVO smsVO = new SmsVO();
		System.out.println("userAction user() ");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		/*//> 1.check params
		if (null == request.getParameter("pnumber") || null == request.getParameter("password") || null == request.getParameter("authcode")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}*/	 	
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, smsVO);
		//> 3. do 
		this.jsonData = smsService.checkAuthcode(smsVO);
		//> 4. return json
		smsVO = null;
		return  SUCCESS;
	}

}