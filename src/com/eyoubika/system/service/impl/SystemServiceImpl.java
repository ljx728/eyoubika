package com.eyoubika.system.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.system.web.VO.SmsVO;
import com.eyoubika.system.service.ISystemService;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ContentUtil;
/*==========================================================================================*
 * Description:	定义了短信服务的接口实现
 * Class:		SmsServiceImpl
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-26 9:14:23
 *==========================================================================================*/
public class SystemServiceImpl extends BaseService implements ISystemService{
	//private String authcode;
	/*--------------------------------------------------------------------------------------*
	 * Description:	构造验证码并置入session
	 * Method:		getAuthcode
	 * Parameters:	SmsVO smsVo
	 * History:		1.0 created by lijiaxuan at 2015-5-26 9:32:49
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> checkDb(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//String pnumber = request.getParameter("pnumber");
		
		HttpSession session = request.getSession();	
	
		//session.setMaxInactiveInterval(Integer.parseInt(CommonVariables.SESSION_EXPIRETIME));
		return this.buildRetData( "0", "");	//用户注册前，userId=0
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	验证session
	 * Method:		checkAuthcode
	 * Parameters:	SmsVO smsVo
	 * History:		1.0 created by lijiaxuan at 2015-6-17 20:15:22
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> checkAuthcode(SmsVO smsVo){
		if (smsVo.getAuthCode() == null) {
			throw new YbkException(YbkException.CODE000002, YbkException.DESC000002);
		}	
		String authCode = ServletActionContext.getRequest().getSession().getAttribute(CommonVariables.SESSION_AUTHCODE).toString();
		if (authCode == null){
			throw new YbkException(YbkException.CODE000001, YbkException.DESC000001);
		}				
		if (smsVo.getAuthCode().equals(authCode)) {
			return this.buildRetData( smsVo.getUserId(), smsVo.getToken());
		} else {
			return this.buildRetData( false, smsVo.getUserId(), smsVo.getToken(), "010003");
		}
	}
}