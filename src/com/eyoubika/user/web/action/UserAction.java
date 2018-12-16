package com.eyoubika.user.web.action;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.user.web.VO.UserBasicVO;
import com.eyoubika.user.web.VO.UserDetailVO;
import com.eyoubika.user.service.IUserService;

/*==========================================================================================*
 * Description:	定义了用户控制器
 * Class:		UserAction
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-21 14:17:33
 *==========================================================================================*/
public class UserAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private UserBasicVO userBasicVO;
	private UserDetailVO userDetailVO;
    private IUserService userService;

	public UserBasicVO getUserBasicVO() {
		return userBasicVO;
	}

	public void setUserBasicVO(UserBasicVO userBasicVO) {
		this.userBasicVO = userBasicVO;
	}

	public UserDetailVO getUserDetailVO() {
		return userDetailVO;
	}

	public void setUserDetailVO(UserDetailVO userDetailVO) {
		this.userDetailVO = userDetailVO;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	用户注册
	 * Method:		register
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-21 14:22:16
	 *--------------------------------------------------------------------------------------*/
	public String register(){	
		HttpServletRequest request = ServletActionContext.getRequest();		
		userBasicVO.init();
		//> 1.check params 手机号，密码，验证码
		//if (null == request.getParameter("pnumber") || null == request.getParameter("password") || null == request.getParameter("authcode")) {
		if (null == request.getParameter("pnumber") || null == request.getParameter("password")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}	
		if (!CommonUtil.checkPnumber(request.getParameter("pnumber"))){
			throw new YbkException(YbkException.CODE010014, YbkException.DESC010014);
		}	
		//> 2. build vo			
		ConverterUtil.RequestToVO(request, userBasicVO);
		//> 3. do register
		this.jsonData = userService.register(userBasicVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户登录
	 * Method:		register
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-21 22:43:51
	 *--------------------------------------------------------------------------------------*/
	public String login(){	
		userBasicVO.init();
		boolean flag = false;
		HttpServletRequest request = ServletActionContext.getRequest();		
		//> 1.check params
		if (null==request.getParameter("loginType")){
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}		
		if (null != request.getParameter("pnumber")){
			if ( null == request.getParameter("password")){
				throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
			}
			if (!CommonUtil.checkPnumber(request.getParameter("pnumber"))){
				throw new YbkException(YbkException.CODE010014, YbkException.DESC010014);
			}
		}
		if (null != request.getParameter("nickName")){
			if ( null == request.getParameter("password")){
				throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
			}
		}
		if (null != request.getParameter("userId")){
			if ( null == request.getParameter("token")){
				throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
			}
		}
		if (null != request.getParameter("email")){
			if ( null == request.getParameter("password")){
				throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
			}
		}
		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, userBasicVO);
		//> 3. do register
		this.jsonData = userService.login(userBasicVO);
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		modifyEmail
	 * Author:		1.0 created by lijiaxuan at 2015年7月23日 下午6:08:04
	 *--------------------------------------------------------------------------------------*/
	public String modifyEmail(){
		userBasicVO.init();
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		//if (null == request.getParameter("email") || null == request.getParameter("userId") || null == request.getParameter("token")) {
		if (null == request.getParameter("email") || null == request.getParameter("userId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, userBasicVO);
		//> 3. do modify
		this.jsonData = userService.modifyUser(userBasicVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户修改手机号
	 * Method:		modifyPhone
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-22 16:26:37
	 *--------------------------------------------------------------------------------------*/
	public String modifyPhone(){
		userBasicVO.init();
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		//if (null == request.getParameter("pnumber") || null == request.getParameter("token")) {
		if (null == request.getParameter("pnumber")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, userBasicVO);
		//> 3. do register
		this.jsonData = userService.modifyUser(userBasicVO);
		//> 4. return json
		return SUCCESS;
	}
	
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		modifyUser
	 * Author:		1.0 created by lijiaxuan at 2015年10月14日 上午9:25:33
	 *--------------------------------------------------------------------------------------*/
	public String modifyUser(){
		userBasicVO.init();
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		//if (null == request.getParameter("pnumber") || null == request.getParameter("token")) {
		if (null == request.getParameter("userId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, userBasicVO);
		//> 3. do register
		this.jsonData = userService.modifyUser(userBasicVO);
		//> 4. return json
		return SUCCESS;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户修改密码
	 * Method:		modifyPassword
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-22 17:33:29
	 *--------------------------------------------------------------------------------------*/
	public String modifyPassword(){
		userBasicVO.init();	
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null == request.getParameter("password") ) {
		//if (null == request.getParameter("password") || null == request.getParameter("token")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, userBasicVO);
		String oldPassword = request.getParameter("oldPassword");
		//> 3. do register
		this.jsonData = userService.modifyUser(userBasicVO);
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	修改用户详情
	 * Method:		modifyUserDetail
	 * Author:		1.0 created by lijiaxuan at 2015年6月13日 下午2:01:36
	 *--------------------------------------------------------------------------------------*/
	public String modifyUserDetail(){
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
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户退出
	 * Method:		logout
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-22 17:48:11
	 *--------------------------------------------------------------------------------------*/
	public String logout(){
		
		userBasicVO.init();	
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null == request.getParameter("userId")) {
		//if (null == request.getParameter("userId") || null == request.getParameter("token")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, userBasicVO);
		//> 3. do logout
		this.jsonData = userService.logout(userBasicVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	获取用户信息
	 * Method:		getUserInfo
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-22 16:03:53
	 *--------------------------------------------------------------------------------------*/
	public String getUserInfo(){
		userBasicVO.init();	
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null == request.getParameter("userId") ) {
		//if (null == request.getParameter("userId") || null == request.getParameter("token")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, userBasicVO);
		//> 3. do register
		this.jsonData = userService.getUserInfo(userBasicVO);
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		isUserExist
	 * Author:		1.0 created by lijiaxuan at 2015年11月8日 下午11:22:37
	 *--------------------------------------------------------------------------------------*/
	public String isUserExist(){
		userBasicVO.init();	
		HttpServletRequest request = ServletActionContext.getRequest();
		//if (null == request.getParameter("userId") ) {
		if (null == request.getParameter("userId") && null == request.getParameter("pnumber")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, userBasicVO);
		//> 3. do register
		this.jsonData = userService.isUserExist(userBasicVO);
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	重置密码
	 * Method:		resetPassword
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-22 16:03:53
	 *--------------------------------------------------------------------------------------*/
	public String resetPassword(){
		userBasicVO.init();	
		HttpServletRequest request = ServletActionContext.getRequest();
		//if (null == request.getParameter("userId") ) {
		if ((null == request.getParameter("userId") && null == request.getParameter("pnumber")) ) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, userBasicVO);
		//> 3. do register
		this.jsonData = userService.modifyUser(userBasicVO);
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		uploadIcon
	 * Author:		1.0 created by lijiaxuan at 2015年8月1日 上午9:36:49
	 *--------------------------------------------------------------------------------------*/
	public String uploadIcon(){
		userDetailVO.init();	
		HttpServletRequest request = ServletActionContext.getRequest();
		//if (null == request.getParameter("userId") ) {
		if (null == request.getParameter("userId") ) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		this.jsonData = userService.uploadIcon(request);
		return SUCCESS;
	}

	
	
}