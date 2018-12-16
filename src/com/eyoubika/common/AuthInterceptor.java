package com.eyoubika.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.eyoubika.common.YbkException;
import com.opensymphony.xwork2.ActionContext;

/*==========================================================================================*
 * Description:	全局验证用户token
 * Class:		AuthInterceptor
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-29 9:24:37
 *==========================================================================================*/
public class AuthInterceptor implements Interceptor {
	private static final long serialVersionUID = 1L;

	public void init() {

	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	拦截器：验证用户token
	 * Method:		intercept
	 * Parameters:	ActionInvocation invocation
	 * History:		1.0 created by lijiaxuan at 2015-5-29 9:24:37
	 *--------------------------------------------------------------------------------------*/
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = null; // Action的返回值
		// 如果返回数据列表，则判断数据量是否过大。
		try {
			if (request.getParameter("limit") != null) {
				if (Integer.parseInt(request.getParameter("limit")) > CommonVariables.LIST_ITEMS_MAX) {
					throw new YbkException(YbkException.CODE900001,
							YbkException.DESC900001);
				}
			}
			// 如果userId为0 则不验证token。
			if (request.getParameter("userId") != null
					&& request.getParameter("userId").toString().equals("0")) {
				return invocation.invoke();
			}
			// 包含token则需要验证：登录需要从数据库验证，其他从内存验证。
			if (request.getParameter("token") != null
					&& request.getParameter("loginType") == null) {
				Map session = ctx.getSession();
				String token = (String) session
						.get(CommonVariables.SESSION_TOKEN);

				if (token == null) {
					// session过期
					throw new YbkException(YbkException.CODE000001,
							YbkException.DESC000001);
				} else if (!request.getParameter("token").toString()
						.equals(token)) {
					// token不匹配
					throw new YbkException(YbkException.CODE010011,
							YbkException.DESC010011);
				} else {
					return invocation.invoke();
				}
			} else {
				return invocation.invoke();
			}
		} catch (YbkException e) {
			String code = null;
			String desc = null;
			String userId = "0";
			e.printStackTrace();
			if (e instanceof YbkException) {
				// 未知的运行时异常
				desc = e.getDesc();
				code = e.getCode();
				userId = "" + e.getUserId();
			} else {
				desc = e.getMessage().trim();// YbkException.CODE999998;//e.getMessage().trim();
				code = YbkException.DESC999998;
				userId = "999998";
			}
			String msg = "{\"code\":\"" + code + "\",\"desc\":\"" + desc
					+ "\"}";
			ErrorPreResultListener listener = new ErrorPreResultListener(msg,
					userId);
			// 设置前置监听器
			invocation.addPreResultListener(listener);
			return "YbkException";
		}

	}

	public void destroy() {

	}
}