package com.eyoubika.system.web.action;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
public class SystemAction extends BaseAction{
	public String checkVersion(){
		userDetailVO.init();			
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null == request.getParameter("userId")) {
		//if (null == request.getParameter("userId") || null == request.getParameter("token")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, userDetailVO);
		//> 3. do register
		this.jsonData = userService.modifyUserDetail(userDetailVO);
		return SUCCESS;
	}
}