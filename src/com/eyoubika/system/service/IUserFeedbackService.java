package com.eyoubika.system.service;
import java.util.Map;

import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.PageInfo;
import com.eyoubika.system.service.IUserFeedbackService;
import com.eyoubika.system.web.VO.UserFeedbackVO;
import com.eyoubika.system.application.UserFeedbackAL;
import com.eyoubika.system.domain.UserFeedbackDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public interface IUserFeedbackService{
	/*--------------------------------------------------------------------------------------*
	 * Description:	新增用户反馈
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addUserFeedback(UserFeedbackVO userFeedbackVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取用户反馈
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getUserFeedback(UserFeedbackVO userFeedbackVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取用户反馈列表_
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getUserFeedbackList(UserFeedbackVO userFeedbackVO, PageInfo pageInfo);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> deleteUserFeedback(UserFeedbackVO userFeedbackVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyUserFeedback(UserFeedbackVO userFeedbackVO);
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
