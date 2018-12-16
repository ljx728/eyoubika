package com.eyoubika.user.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eyoubika.common.BaseService;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.user.web.VO.UserBasicVO;
import com.eyoubika.user.web.VO.UserDetailVO;
import com.eyoubika.user.domain.UserBasicDomain;
import com.eyoubika.user.domain.UserDetailDomain;
import com.eyoubika.user.domain.UserInfoDomain;
import com.eyoubika.user.domain.UserLoginDomain;
import com.eyoubika.user.service.IUserService;
import com.eyoubika.user.application.UserAL;
/*==========================================================================================*
 * Description:	定义了用户服务的接口实现
 * Class:		UserServiceImpl
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-23 13:54:53
 *==========================================================================================*/
public class UserServiceImpl extends BaseService implements IUserService {
	private UserBasicDomain userBasicDomain;
	private UserDetailDomain userDetailDomain;
	private UserInfoDomain userInfoDomain;
	private UserLoginDomain userLoginDomain;
	private UserAL userAL;
	
	
	public UserLoginDomain getUserLoginDomain() {
		return userLoginDomain;
	}

	public void setUserLoginDomain(UserLoginDomain userLoginDomain) {
		this.userLoginDomain = userLoginDomain;
	}

	public UserBasicDomain getUserBasicDomain() {
		return userBasicDomain;
	}

	public void setUserBasicDomain(UserBasicDomain userBasicDomain) {
		this.userBasicDomain = userBasicDomain;
	}

	public UserDetailDomain getUserDetailDomain() {
		return userDetailDomain;
	}

	public void setUserDetailDomain(UserDetailDomain userDetailDomain) {
		this.userDetailDomain = userDetailDomain;
	}

	public UserInfoDomain getUserInfoDomain() {
		return userInfoDomain;
	}

	public void setUserInfoDomain(UserInfoDomain userInfoDomain) {
		this.userInfoDomain = userInfoDomain;
	}

	public UserAL getUserAL() {
		return userAL;
	}

	public void setUserAL(UserAL userAL) {
		this.userAL = userAL;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	用户注册
	 * Method:		register
	 * Parameters:	UserBasicVO UserBasicVO
	 * History:		1.0 created by lijiaxuan at 2015-5-23 14:12:11
	 *--------------------------------------------------------------------------------------*/
	public  Map<String, Object> register(UserBasicVO UserBasicVO){
		userBasicDomain.init();
		ConverterUtil.VOToDomain(UserBasicVO, userBasicDomain);		
		userBasicDomain = userAL.register(userBasicDomain, UserBasicVO.getAuthcode());
		return this.buildRetData( userBasicDomain.getUserId().toString(), userBasicDomain.getToken());		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户登录
	 * Method:		login
	 * Parameters:	UserBasicVO UserBasicVO
	 * History:		1.0 created by lijiaxuan at 2015-5-23 14:28:27
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object>login(UserBasicVO UserBasicVO){
		userBasicDomain.init();
		ConverterUtil.VOToDomain(UserBasicVO, userBasicDomain);
		userBasicDomain = userAL.login(userBasicDomain, UserBasicVO.getLoginType());
		if (userBasicDomain.getUserId() != null){
			userLoginDomain.init();
			ConverterUtil.VOToDomain(UserBasicVO, userLoginDomain);
			userLoginDomain.setUserId(userBasicDomain.getUserId());
			userAL.initLogin(userLoginDomain);		//初始化登录信息
		}
		return this.buildRetData( userBasicDomain.getUserId().toString(), userBasicDomain.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户修改
	 * Method:		modifyUser
	 * Parameters:	UserBasicVO UserBasicVO
	 * History:		1.0 created by lijiaxuan at 2015-5-23 14:43:22
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyUser(UserBasicVO userBasicVO){
		userBasicDomain.init();	
		ConverterUtil.VOToDomain(userBasicVO, userBasicDomain);
		userAL.modifyUser(userBasicDomain);
		return this.buildRetData(userBasicVO.getUserId(), userBasicVO.getToken());
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	用户修改
	 * Method:		modifyUserDetail
	 * Parameters:	UserDetailVO UserDetailVO
	 * History:		1.0 created by lijiaxuan at 2015-6-27 19:23:22
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyUserDetail(UserDetailVO userDetailVO){	
		userDetailDomain.init();
		ConverterUtil.VOToDomain(userDetailVO, userDetailDomain);
		userAL.modifyUserDetail(userDetailDomain);
		return this.buildRetData(userDetailVO.getUserId(), userDetailVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户退出
	 * Method:		logout
	 * Parameters:	UserBasicVO UserBasicVO
	 * History:		1.0 created by lijiaxuan at 2015-5-23 14:49:31
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> logout(UserBasicVO UserBasicVO){
		userBasicDomain.init();
		ConverterUtil.VOToDomain(UserBasicVO, userBasicDomain);	
		userId = userBasicDomain.getUserId();		
		userAL.logout(userBasicDomain);
		return this.buildRetData(userBasicDomain.getUserId().toString(), userBasicDomain.getToken());
	}	
	
	public Map<String, Object> uploadIcon(HttpServletRequest request){	
		userAL.uploadIcon(request);
		return this.buildRetData( request.getParameter("userId").toString(), request.getParameter("token").toString());
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		isUserExist
	 * Author:		1.0 created by lijiaxuan at 2015年11月8日 下午11:16:38
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> isUserExist(UserBasicVO userBasicVO){
		String flag = "0";
		if (userAL.checkReRegister(userBasicVO.getPnumber())){
			flag = "1";
		}
		return this.buildRetData("0", "0", flag);
		/*return true;*/
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户信息查询(用户全量信息)
	 * Method:		getUserInfo
	 * Parameters:	UserBasicVO UserBasicVO
	 * History:		1.0 created by lijiaxuan at 2015-5-23 14:49:31
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getUserInfo(UserBasicVO userBasicVO){
		userInfoDomain.init();
		userInfoDomain = userAL.getUserInfo(Integer.parseInt(userBasicVO.getUserId()));
		return this.buildRetData(userBasicVO.getUserId(), userBasicVO.getToken(), userInfoDomain);
	}
}