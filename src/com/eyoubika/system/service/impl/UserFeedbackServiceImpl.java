package com.eyoubika.system.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.PageInfo;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.sbc.web.VO.SbcQueryVO;
import com.eyoubika.spider.domain.ArticleDomain;
import com.eyoubika.system.service.IUserFeedbackService;
import com.eyoubika.system.web.VO.UserFeedbackVO;
import com.eyoubika.system.application.UserFeedbackAL;
import com.eyoubika.system.domain.UserFeedbackDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class UserFeedbackServiceImpl extends BaseService implements IUserFeedbackService{
	private UserFeedbackAL userFeedbackAL;	//
	private UserFeedbackDomain userFeedbackDomain;	//
	public UserFeedbackDomain getUserFeedbackDomain(){
		return userFeedbackDomain;
	}
	public void setUserFeedbackDomain(UserFeedbackDomain userFeedbackDomain){
		this.userFeedbackDomain = userFeedbackDomain;
	}
	public UserFeedbackAL getUserFeedbackAL(){
		return userFeedbackAL;
	}
	public void setUserFeedbackAL(UserFeedbackAL userFeedbackAL){
		this.userFeedbackAL = userFeedbackAL;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addUserFeedback(UserFeedbackVO userFeedbackVO){
		userFeedbackDomain.init();
		ConverterUtil.VOToDomain(userFeedbackVO, userFeedbackDomain);
		//userFeedbackDomain.setFeederId(Integer.parseInt(userFeedbackVO.getUserId()));
		userFeedbackAL.addUserFeedback(userFeedbackDomain);
		return this.buildRetData( userFeedbackVO.getUserId() , userFeedbackVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getUserFeedback(UserFeedbackVO userFeedbackVO){
		userFeedbackDomain.init();
		ConverterUtil.VOToDomain(userFeedbackVO, userFeedbackDomain);
		userFeedbackDomain = userFeedbackAL.getUserFeedback(Integer.parseInt(userFeedbackVO.getUfId()));
		return this.buildRetData( userFeedbackVO.getUserId() , userFeedbackVO.getToken(), userFeedbackDomain);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getUserFeedbackList(UserFeedbackVO userFeedbackVO, PageInfo pageInfo){
		Map<String, Object> params = new HashMap<String, Object>();
		userFeedbackDomain.init();
		ConverterUtil.VOToDomain(userFeedbackVO, userFeedbackDomain);
		PageInfo res = userFeedbackAL.getUserFeedbackInPage(userFeedbackDomain, pageInfo);	
		List<UserFeedbackDomain>  listDomains = (List<UserFeedbackDomain>) res.getResult();// = newsAL.getNewsList(newsBasicDomain, pageInfo);				
		params.put("size", res.getTotalNum());
		params.put("next", res.getNext());
		params.put("list", listDomains);		
		return this.buildRetData(userFeedbackVO.getUserId(), userFeedbackVO.getToken(), params);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> deleteUserFeedback(UserFeedbackVO userFeedbackVO){
		userFeedbackAL.deleteUserFeedback(Integer.parseInt(userFeedbackVO.getUfId()));
		return this.buildRetData( userFeedbackVO.getUserId() , userFeedbackVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2016-01-13 17:21:37
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyUserFeedback(UserFeedbackVO userFeedbackVO){
		userFeedbackDomain.init();
		ConverterUtil.VOToDomain(userFeedbackVO, userFeedbackDomain);
		//存入修改人的userId
		//userFeedbackDomain.setFeederId(Integer.parseInt(userFeedbackVO.getUserId()));
		userFeedbackAL.modifyUserFeedback(userFeedbackDomain);
		return this.buildRetData( userFeedbackVO.getUserId() , userFeedbackVO.getToken());
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
