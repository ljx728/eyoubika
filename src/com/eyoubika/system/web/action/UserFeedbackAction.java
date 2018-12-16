package com.eyoubika.system.web.action;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.PageInfo;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.system.service.IUserFeedbackService;
import com.eyoubika.system.web.VO.UserFeedbackVO;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
 *==========================================================================================*/
public class UserFeedbackAction extends BaseAction {
	private IUserFeedbackService userFeedbackService;	//<<attrNote>>
	private UserFeedbackVO userFeedbackVO;	//<<attrNote>>
	public IUserFeedbackService getUserFeedbackService(){
		return userFeedbackService;
	}
	public void setUserFeedbackService(IUserFeedbackService userFeedbackService){
		this.userFeedbackService = userFeedbackService;
	}
	public UserFeedbackVO getUserFeedbackVO(){
		return userFeedbackVO;
	}
	public void setUserFeedbackVO(UserFeedbackVO userFeedbackVO){
		this.userFeedbackVO = userFeedbackVO;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public String addUserFeedback(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		userFeedbackVO.init();
		ConverterUtil.RequestToVO(request, userFeedbackVO);
		//> 3. do
		this.jsonData = userFeedbackService.addUserFeedback(userFeedbackVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public String getUserFeedback(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		userFeedbackVO.init();
		ConverterUtil.RequestToVO(request, userFeedbackVO);
		//> 3. do
		this.jsonData = userFeedbackService.getUserFeedback(userFeedbackVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public String getUserFeedbackList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		userFeedbackVO.init();
		PageInfo pageInfo= new PageInfo();	
		pageInfo.assign(request);
		ConverterUtil.RequestToVO(request, userFeedbackVO);
		//> 3. do
		this.jsonData = userFeedbackService.getUserFeedbackList(userFeedbackVO, pageInfo);
		pageInfo = null;
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public String deleteUserFeedback(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		userFeedbackVO.init();
		ConverterUtil.RequestToVO(request, userFeedbackVO);
		//> 3. do
		this.jsonData = userFeedbackService.deleteUserFeedback(userFeedbackVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public String modifyUserFeedback(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		userFeedbackVO.init();
		ConverterUtil.RequestToVO(request, userFeedbackVO);
		//> 3. do
		this.jsonData = userFeedbackService.modifyUserFeedback(userFeedbackVO);
		return SUCCESS;
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
