package com.eyoubika.system.application;
import java.util.List;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.PageInfo;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.common.YbkException;
import com.eyoubika.system.domain.UserFeedbackDomain;
import com.eyoubika.system.dao.UserFeedbackDao;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class UserFeedbackAL{
	private UserFeedbackDao userFeedbackDao;	//
	public UserFeedbackDao getUserFeedbackDao(){
		return userFeedbackDao;
	}
	public void setUserFeedbackDao(UserFeedbackDao userFeedbackDao){
		this.userFeedbackDao = userFeedbackDao;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public void addUserFeedback(UserFeedbackDomain userFeedbackDomain){
		if (userFeedbackDomain.getFeederId() == null) {
			userFeedbackDomain.setFeederId(0);
		}
		userFeedbackDomain.setDate(CommonUtil.getNowDate());
		userFeedbackDomain.setTime(CommonUtil.getNowTime());
		userFeedbackDomain.setStatus(CommonVariables.COMMON_LEVEL_01);
		userFeedbackDao.addUserFeedback(userFeedbackDomain);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public UserFeedbackDomain getUserFeedback(Integer ufId ){
		return userFeedbackDao.findUserFeedback(ufId);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public PageInfo getUserFeedbackInPage(UserFeedbackDomain userFeedbackDomain, PageInfo pageInfo){
		return userFeedbackDao.queryUserFeedbackInPage(userFeedbackDomain, pageInfo);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public void deleteUserFeedback(Integer ufId){
		 userFeedbackDao.deleteUserFeedback( ufId);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public void modifyUserFeedback(UserFeedbackDomain userFeedbackDomain){
		userFeedbackDao.modifyUserFeedback(userFeedbackDomain);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
