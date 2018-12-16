package com.eyoubika.user.service;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eyoubika.user.web.VO.UserBasicVO;
import com.eyoubika.user.web.VO.UserDetailVO;
/*==========================================================================================*
 * Description:	定义用户服务接口
 * Class:		IUserService
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-23 13:52:23
 *==========================================================================================*/
public interface IUserService {
	
	public Map<String, Object> register(UserBasicVO userBasicVO);
	public Map<String, Object> login(UserBasicVO userBasicVO);
	public Map<String, Object> modifyUser(UserBasicVO userBasicVO);
	public Map<String, Object> logout(UserBasicVO userBasicVO);
	public Map<String, Object> getUserInfo(UserBasicVO userBasicVO);
	public Map<String, Object> modifyUserDetail(UserDetailVO userDetailVO);
	public Map<String, Object> uploadIcon(HttpServletRequest request);
	public Map<String, Object> isUserExist(UserBasicVO userBasicVO);
	//public Map<String, Object> modifyEmail(UserDetailVO userDetailVO);
	
}