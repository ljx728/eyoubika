package com.eyoubika.common;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.ValueStack;

/*==========================================================================================*
 * Description:	定义了Error拦截器的前置监听器
 * Class:		ErrorPreResultListener
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-27 12:30:21
 *==========================================================================================*/
public class ErrorPreResultListener implements PreResultListener{
	String msg;
	String userId;
		
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ErrorPreResultListener(String msg, String userId){
		this.msg = msg;
		this.userId = userId;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	构造返回到客户端的json数据
	 * Method:		beforeResult
	 * Parameters:	ActionInvocation actionInvocation, String arg1
	 * History:		1.0 created by lijiaxuan at 2015-5-27 12:32:49
	 *--------------------------------------------------------------------------------------*/
	@Override
	public void beforeResult(ActionInvocation actionInvocation, String arg1) {
		// TODO Auto-generated method stub
		 ValueStack valueStack = actionInvocation.getStack();  
         Map<String, Object> jsonData = new HashMap<String, Object>();
         jsonData.put("ret", "0");
         jsonData.put("msg", this.msg);
         jsonData.put("userId", this.userId);
         valueStack.setValue("jsonData", jsonData);
	}
	//public ErrorPreResultListener
}