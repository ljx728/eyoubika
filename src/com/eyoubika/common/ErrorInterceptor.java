package com.eyoubika.common;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.eyoubika.common.YbkException;
import com.eyoubika.common.ErrorPreResultListener;
/*==========================================================================================*
 * Description:	全局的出错拦截器
 * Class:		ErrorInterceptor
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-27 12:13:41
 *==========================================================================================*/
public class ErrorInterceptor implements Interceptor {
	private static final long serialVersionUID = 1L;

	public void init() {

	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	拦截器：统一处理exception
	 * Method:		intercept
	 * Parameters:	ActionInvocation actioninvocation
	 * History:		1.0 created by lijiaxuan at 2015-5-27 12:15:01
	 *--------------------------------------------------------------------------------------*/
	public String intercept(ActionInvocation actioninvocation) {

		String result = null; // Action的返回值
		try {
			// 运行被拦截的Action,期间如果发生异常会被catch住
			result = actioninvocation.invoke();
			return result;
		} catch (Exception e) {
			String code = null;
			String desc = null;
			String userId = "0";
			e.printStackTrace();
			if (e instanceof YbkException) {
				// 未知的运行时异常
				YbkException re = (YbkException) e;
				desc = re.getDesc();
				code = re.getCode();
				userId = "" + re.getUserId();
			} else {
				
				desc = e.getMessage().trim();//YbkException.CODE999998;//e.getMessage().trim();
				code = YbkException.DESC999998;
				userId = "999998";
			}
			String msg = "{\"code\":\"" + code + "\",\"desc\":\"" + desc + "\"}";
			ErrorPreResultListener listener = new ErrorPreResultListener(msg,
					userId);
			//设置前置监听器
			actioninvocation.addPreResultListener(listener);

			/*
			 * actioninvocation.addPreResultListener(new PreResultListener() {
			 * public void beforeResult(ActionInvocation actionInvocation,
			 * String arg1) { ValueStack valueStack =
			 * actionInvocation.getStack(); Map<String, Object> jsonData = new
			 * HashMap<String, Object>(); jsonData.put("ret", "0");
			 * jsonData.put("msg", "{code:" + code+ ";msg: "+ desc + "}");
			 * jsonData.put("ret", msg); valueStack.setValue("jsonData",
			 * jsonData);
			 * 
			 * } });
			 */

			String errorMsg = "出现错误信息，请查看日志！";
			// 通过instanceof判断到底是什么异常类型
			/*
			 * if (e instanceof RuntimeException) { //未知的运行时异常 RuntimeException
			 * re = (RuntimeException) e; //re.printStackTrace(); errorMsg =
			 * re.getMessage().trim();
			 * 
			 * }
			 */

			/* 
                        *//**
			 * log4j记录日志
			 */
			/*
			 * Log log = LogFactory
			 * .getLog(actioninvocation.getAction().getClass());
			 * log.error(errorMsg, e);
			 */
			return "YbkException";
		}// ...end of catch
	}

	public void destroy() {

	}
}