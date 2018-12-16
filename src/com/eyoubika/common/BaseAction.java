package com.eyoubika.common;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.opensymphony.xwork2.ActionSupport;
import com.eyoubika.common.PageInfo;
/*==========================================================================================*
 * Description:	BaseAction
 * Class:		BaseAction
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-20 17:34:33
 *==========================================================================================*/
public class BaseAction extends ActionSupport { 
	private static final long serialVersionUID = 1L;
	protected Map<String, Object> jsonData;   
	protected HttpServletRequest request;
	protected PageInfo pageInfo;

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
}