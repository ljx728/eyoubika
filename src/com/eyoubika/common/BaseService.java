package com.eyoubika.common;

import java.util.HashMap;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.eyoubika.util.ConverterUtil;

/*==========================================================================================*
 * Description:	BaseService
 * Class:		BaseService
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-22 22:09:12
 *==========================================================================================*/
public class BaseService {
	protected Map<String, Object> retData;
	protected HttpServletRequest request;
	protected boolean ret;
	protected int userId;
	protected String token;
	protected String msgCode;
	protected BaseLoger loger;

	public BaseService() {
		ret = true;
		userId = 0;
		token = null;
		msgCode = null;
		loger = new BaseLoger(this.getClass().toString());
	}

	public BaseLoger getLoger() {
		return loger;
	}

	public void setLoger(BaseLoger loger) {
		this.loger = loger;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public boolean isRet() {
		return ret;
	}

	public void setRet(boolean ret) {
		this.ret = ret;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public Map<String, Object> getRetData() {
		return retData;
	}

	public void setRetData(Map<String, Object> retData) {
		this.retData = retData;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	构造返回到ACTION的retData: 业务流程顺利ret=1，并返回userId和token
	 * Method:		buildRetData
	 * Parameters:	int userId, String token
	 * History:		1.0 created by lijiaxuan at 2015-5-22 23:24:37
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> buildRetData(String userId, String token) {
		retData = new HashMap<String, Object>();
		retData.put("ret", "1");
		retData.put("userId", userId);
		if (token != null) {
			retData.put("token", token);
		}
		return retData;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	构造返回到ACTION的retData: 业务流程未知，返回userId和token, msg
	 * Method:		buildRetData
	 * Parameters:	boolean ret, int userId, String token, String msgCode
	 * History:		1.0 created by lijiaxuan at 2015-5-22 23:34:10
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> buildRetData(boolean ret, String userId,
			String token, String msgCode) {
		retData = new HashMap<String, Object>();

		retData.put("ret", ret ? "1" : "0");
		retData.put("userId", userId);

		if (token != null) {
			retData.put("token", token);
		}

		if (msgCode != null) {
			String descStr = "DESC" + msgCode;
			try {
				Field descField = YbkException.class.getField(descStr);
				retData.put("msg", "{\"code\":\"" + msgCode + "\";\"desc\":\""
						+ descField.get(null).toString() + "\"}");
			} catch (NoSuchFieldException | SecurityException
					| IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return retData;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	构造返回到ACTION的retData: 业务流程顺利ret=1，并返回userId和token, 以及其他参数
	 * Method:		buildRetData
	 * Parameters:	int userId, String token, Object ob
	 * History:		1.0 created by lijiaxuan at 2015-5-24 11:27:56
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> buildRetData(String userId, String token,
			Object ob) {
		retData = new HashMap<String, Object>();
		retData.put("ret", "1");
		retData.put("userId", userId);
		if (token != null) {
			retData.put("token", token);
		}
		if (ob != null) {
			if (ob instanceof String) {
				retData.put("data", ob);	
			} else {
				retData.put("data", ConverterUtil.objectToJson(ob));
			}
		}
		return retData;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildRetData
	 * Author:		1.0 created by lijiaxuan at 2015年6月28日 下午12:28:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> buildRetData(String userId, String token,
			Map<String, Object> params) {
		retData = new HashMap<String, Object>();
		retData.put("ret", "1");
		retData.put("userId", userId);
		if (token != null) {
			retData.put("token", token);
		}

		retData.put("data", ConverterUtil.dataToJson(params));
		return retData;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildRetData
	 * Author:		1.0 created by lijiaxuan at 2015年6月28日 下午12:28:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> buildRetData(String userId, String token,
			Object list, int size) {
		retData = new HashMap<String, Object>();
		retData.put("ret", "1");
		retData.put("userId", userId);
		if (token != null) {
			retData.put("token", token);
		}
		if (list != null) {
			retData.put("data", ConverterUtil.listToJson(list, size));
		}
		return retData;
	}

	/*
	 * public UserInfoSession getCurrentUser() { request =
	 * ServletActionContext.getRequest(); UserInfoSession currentUser =
	 * (UserInfoSession)
	 * (request.getSession().getAttribute(CommonVariables.SESSION_OPERATOR)); if
	 * (currentUser == null) throw new YbkException(YbkException.CODE000001,
	 * YbkException.DESC000001); return currentUser; }
	 */
}