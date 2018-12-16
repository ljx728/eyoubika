package com.eyoubika.user.application;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.CommonMaps;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.YbkException;
import com.eyoubika.system.application.ImageAL;
import com.eyoubika.system.dao.BadWordDao;
import com.eyoubika.system.domain.BadWordDomain;
import com.eyoubika.util.ContentUtil;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.user.dao.UserBasicDao;
import com.eyoubika.user.dao.UserDetailDao;
import com.eyoubika.user.dao.UserLoginDao;
import com.eyoubika.user.domain.FavoriteExsDomain;
import com.eyoubika.user.domain.UserBasicDomain;
import com.eyoubika.user.domain.UserDetailDomain;
import com.eyoubika.user.domain.UserInfoDomain;
import com.eyoubika.user.domain.UserLoginDomain;
/*==========================================================================================*
 * Description:	用户模块
 * Class:		UserAL
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-24 10:10:20
 *==========================================================================================*/
public class UserAL  {
	private UserBasicDao userBasicDao;
	private UserDetailDao userDetailDao;
	private BadWordDao badWordDao;
	private UserLoginDao userLoginDao;
	private FavorListAL favorListAL;
	
	public FavorListAL getFavorListAL() {
		return favorListAL;
	}

	public void setFavorListAL(FavorListAL favorListAL) {
		this.favorListAL = favorListAL;
	}



	public BadWordDao getBadWordDao() {
		return badWordDao;
	}



	public void setBadWordDao(BadWordDao badWordDao) {
		this.badWordDao = badWordDao;
	}



	public UserLoginDao getUserLoginDao() {
		return userLoginDao;
	}



	public void setUserLoginDao(UserLoginDao userLoginDao) {
		this.userLoginDao = userLoginDao;
	}



	public UserBasicDao getUserBasicDao() {
		return userBasicDao;
	}



	public void setUserBasicDao(UserBasicDao userBasicDao) {
		this.userBasicDao = userBasicDao;
	}



	public UserDetailDao getUserDetailDao() {
		return userDetailDao;
	}



	public void setUserDetailDao(UserDetailDao userDetailDao) {
		this.userDetailDao = userDetailDao;
	}



	/*--------------------------------------------------------------------------------------*
	 * Description:	用户注册
	 * Method:		register
	 * Parameters:	UserBasicDomain userBasicDomain, String authcode
	 * History:		1.0 created by lijiaxuan at 2015-5-24 19:12:41
	 *--------------------------------------------------------------------------------------*/
	public  UserBasicDomain register(UserBasicDomain userBasicDomain, String authcode){
		String logString ="==【用户注册】 "  + CommonUtil.getNow() + " ==";
		//CommonUtil.toLog(logString);
		//CommonUtil.toLog(userBasicDomain.toString());
		String pnumber = userBasicDomain.getPnumber();
		boolean ret = true;
		int userId = 0;
		//HttpSession session = ServletActionContext.getRequest().getSession();
		//logString = "Input Authcode: " + authcode + "\n" + "Session Authcode: " + session.getAttribute(CommonVariables.SESSION_AUTHCODE);
		//CommonUtil.toLog(logString);
		//> 1.check authCode.
			
		/*if ( ! authcode.equals("eyoubika")){
			throw new YbkException(YbkException.CODE010003, YbkException.DESC010003 + "：测试阶段，验证码是：eyoubika");
		}
		Object userAuthcode =  "eyoubika";// session.getAttribute(CommonVariables.SESSION_AUTHCODE);
		if (userAuthcode == null) {
			throw new YbkException(YbkException.CODE010002, YbkException.DESC010002);
		}
		if ( ! authcode.equals(userAuthcode.toString())){
			throw new YbkException(YbkException.CODE010003, YbkException.DESC010003);
		} else*/ {
			//> 2.check reRegister.
			if (checkReRegister(pnumber)){
				ret = false;
				throw new YbkException(YbkException.CODE010004, YbkException.DESC010004);
			} else {
				
				//> 3.check goodName.
				if (checkBadName(userBasicDomain.getNickName())) {
					ret = false;
					throw new YbkException(YbkException.CODE010007, YbkException.DESC010007);
				} else {
					
					//> 4. register
				
					userBasicDomain.setPnumber(pnumber);
					userBasicDomain.setRegiTime(CommonUtil.getNow());
					//注册时，token为空？
					userBasicDomain.setToken(ContentUtil.generateToken());				
					userBasicDomain.setSalt(ContentUtil.generateSalt());
					userBasicDomain.setPassword(ContentUtil.generatePassword(userBasicDomain.getPassword(), userBasicDomain.getSalt()));
					userBasicDomain.setStatus(CommonVariables.USER_STATUS_NORMAL);				
					userId = userBasicDao.addUserBasic(userBasicDomain);
					UserDetailDomain userDetailDomain = new UserDetailDomain();
					userDetailDomain.setUserId(userId);
					userDetailDao.addUserDetail(userDetailDomain);
					userDetailDomain = null;
					// 注册成功后，需要添加所有交易所到用户自选列表
					FavoriteExsDomain favoriteExsDomain = new FavoriteExsDomain();					
					favorListAL.addFavoriteExs( ""+userId, CommonMaps.exchangeIdStr, favoriteExsDomain);
					favoriteExsDomain = null;
					logString ="==【用户注册后】 "  + CommonUtil.getNow() + " ==\n";
					//CommonUtil.toLog(logString);
					//CommonUtil.toLog(userBasicDomain.toString());
				}
			}			
		}			
		return userBasicDomain;	
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		checkBadName
	 * Author:		1.0 created by lijiaxuan at 2015年8月3日 下午4:10:50
	 *--------------------------------------------------------------------------------------*/
	protected boolean checkBadName(String name){
		boolean res = false;
		if (name == null ){
			
		} else {
			BadWordDomain badWordDomain = new BadWordDomain();
			badWordDomain.setWord(name);
			badWordDomain.setType(CommonVariables.BADWORD_NICKNAME);
			res = badWordDao.isLikeBadWord(badWordDomain);
			badWordDomain = null;
		}
		return res;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	检查是否用户重复注册
	 * Method:		checkReRegister
	 * Parameters:	String pnumber
	 * History:		1.0 created by lijiaxuan at 2015-5-24 11:33:58
	 *--------------------------------------------------------------------------------------*/
	public boolean checkReRegister(String pnumber){
		return userBasicDao.isExistPnumber(pnumber);	
		/*return true;*/
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		initLogin
	 * Author:		1.0 created by lijiaxuan at 2015年8月3日 下午5:09:57
	 *--------------------------------------------------------------------------------------*/
	public void initLogin(UserLoginDomain userLoginDomain){
		//add
		UserLoginDomain tempDomain = userLoginDao.findUserLogin(userLoginDomain.getUserId());
		if (tempDomain == null) {			
			userLoginDomain.setFirstLogiTime(CommonUtil.getNow());
			userLoginDomain.setLogiTime(CommonUtil.getNow());
			userLoginDao.addUserLogin(userLoginDomain);
		} else {
			//modify
			userLoginDomain.setLastLogiTime(tempDomain.getLogiTime());
			userLoginDomain.setLogiTime(CommonUtil.getNow());
			userLoginDao.modifyUserLogin(userLoginDomain);		
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户登录
	 * Method:		login
	 * Parameters:	UserBasicDomain loginDomain, String loginType
	 * History:		1.0 created by lijiaxuan at 2015-5-24 10:12:28
	 *--------------------------------------------------------------------------------------*/
	public UserBasicDomain login(UserBasicDomain loginDomain, String loginType){
		
		System.out.println("0  userServiceImpl login");
		//自动登录 uid + token
		if (loginType.equals(CommonVariables.LOGIN_TYPE_AUTO)) {
			String userToken = userBasicDao.findUserToken(loginDomain.getUserId());
			if (!userToken.equals(loginDomain.getToken())){
				//token无效或已过期
				throw new YbkException(YbkException.CODE010011, YbkException.DESC010011);	
			}
		} else {
			//用户登录 pnumber + pwd
			UserBasicDomain userBasicDomain = new UserBasicDomain();
			//手机号登录
			if (CommonUtil.isStringNull(loginDomain.getPassword())){
				throw new YbkException(YbkException.CODE010012, YbkException.DESC010012);
			}
	
			
			if (loginDomain.getPnumber()!=null){	//手机号登录
				userBasicDomain = userBasicDao.findUserBasicByPnumber(loginDomain.getPnumber());
			} else if (loginDomain.getNickName()!=null){	//昵称登录
				if(loginType.equals(CommonVariables.LOGIN_TYPE_ADMIN)) {
					UserBasicDomain tmpDomain = new UserBasicDomain();
					tmpDomain.setNickName(loginDomain.getNickName());
					tmpDomain.setStatus(CommonVariables.USER_STATUS_ADMIN);
					userBasicDomain = userBasicDao.findUserBasicByDomain(tmpDomain);
					
					tmpDomain = null;
				} else {
					userBasicDomain = userBasicDao.findUserBasicByNickName(loginDomain.getNickName());
				}
				
			} else if (loginDomain.getEmail()!=null){	//邮箱登录
				userBasicDomain = userBasicDao.findUserBasicByEmail(loginDomain.getEmail());
			}
			
			if (userBasicDomain == null){
				//用户不存在
				throw new YbkException(YbkException.CODE010001, YbkException.DESC010001);
			}
			loginDomain.setUserId(userBasicDomain.getUserId());
			String loginPwd = ContentUtil.generatePassword(loginDomain.getPassword(), userBasicDomain.getSalt());
			
			if (!loginPwd.equals(userBasicDomain.getPassword())){
				//密码输入错误
				throw new YbkException(YbkException.CODE010010, YbkException.DESC010010);
			} else {
				//输入的密码与数据库里的密码相差一次MD5.所以要把登录密码更新成数据库密码
				loginDomain.setPassword(loginPwd);			
			}		
			userBasicDomain = null;		
		}
		
		String token = ContentUtil.generateToken();
		loginDomain.setToken(token);
		userBasicDao.modifyUserBasic(loginDomain);  //token 需要保存吗？可以不存的吧。如果不存，则不需要写数据库
		HttpSession session = ServletActionContext.getRequest().getSession(); 
		session.setAttribute(CommonVariables.SESSION_USERID, loginDomain.getUserId());
		session.setAttribute(CommonVariables.SESSION_TOKEN, loginDomain.getToken());

		return loginDomain;	
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	修改用户资料
	 * Method:		modifyUser
	 * Parameters:	UserBasicDomain userBasicDomain
	 * History:		1.0 created by lijiaxuan at 2015-5-24 14:19:16
	 *--------------------------------------------------------------------------------------*/
	public void modifyUser(UserBasicDomain userBasicDomain){
		System.out.println("0  userAL modifyUserByUserId");
		//modify user's password.
		if (userBasicDomain.getUserId() == null || userBasicDomain.getUserId() == 0){
			UserBasicDomain tmpDomain = userBasicDao.findUserBasicByPnumber(userBasicDomain.getPnumber());
			if (tmpDomain!= null){
				userBasicDomain.setUserId(tmpDomain.getUserId());
			}
		}
		if ( ! CommonUtil.isStringNull(userBasicDomain.getPassword())){
			String salt = userBasicDao.findUserSalt(userBasicDomain.getUserId());
			userBasicDomain.setPassword(ContentUtil.generatePassword(userBasicDomain.getPassword(), salt));
		} 
		userBasicDao.modifyUserBasic(userBasicDomain);
	}
	

	/*--------------------------------------------------------------------------------------*
	 * Description:	修改用户详情资料
	 * Method:		modifyUserDetail
	 * History:		1.0 created by lijiaxuan at 2015-5-25 11:23:13
	 *--------------------------------------------------------------------------------------*/
	public void modifyUserDetail(UserDetailDomain userDetailDomain){
		if (! userDetailDao.isExist(userDetailDomain.getUserId())){
			userDetailDao.addUserDetail(userDetailDomain);
		} else {
			userDetailDao.modifyUserDetail(userDetailDomain);		
		}	
	}

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	用户退出
	 * Method:		logout
	 * Parameters:	UserBasicDomain userBasicDomain
	 * History:		1.0 created by lijiaxuan at 2015-5-28 9:39:24
	 *--------------------------------------------------------------------------------------*/
	public void logout(UserBasicDomain userBasicDomain){
		HttpServletRequest request = ServletActionContext.getRequest();	  
		HttpSession session = request.getSession();	  
		session.invalidate();
	}	
	
	public UserInfoDomain getUserInfo(Integer userId){
		//modify user's password.
		/*if ( ! CommonUtil.isStringNull(userBasicDomain.getPassword())){
			String salt = userBasicDao.getUserSalt(userBasicDomain.getUserId());
			userBasicDomain.setPassword(ContentUtil.generatePassword(userBasicDomain.getPassword(), salt));
		} else {
			throw new YbkException(YbkException.CODE010010, YbkException.DESC010010 + "：密码不能为空");
		}*/
		return userBasicDao.findUserInfo(userId);
	}
	
	public UserBasicDomain getUser(Integer userId){
		System.out.println("0  userAL getUserInfo");
		//modify user's password.
		/*if ( ! CommonUtil.isStringNull(userBasicDomain.getPassword())){
			String salt = userBasicDao.getUserSalt(userBasicDomain.getUserId());
			userBasicDomain.setPassword(ContentUtil.generatePassword(userBasicDomain.getPassword(), salt));
		} else {
			throw new YbkException(YbkException.CODE010010, YbkException.DESC010010 + "：密码不能为空");
		}*/
		return userBasicDao.findUserBasic(userId);
	}
	
	public void uploadIcon(HttpServletRequest request){
		ImageAL imageAL = new ImageAL();
		String batchNo = imageAL.uploadImage(request);
		imageAL = null;
	}
	
	
	/*
	//0.if user want to rember himself, set cookie.
			boolean rememberme = false;
			if (rememberme){
			Cookie cookie = new Cookie("uid", "10");
			cookie.setMaxAge(60*60*24*365);
			httpResponse.addCookie(cookie);
			
			cookie = new Cookie("username", "lijiaxuan");
			cookie.setMaxAge(60*60*24*365);
			httpResponse.addCookie(cookie);
			}
			
			//1. get from cookies
			Cookie[] cookies = httpRequest.getCookies();
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("uid")){
					uid = cookie.getValue();
		        	System.out.println("uid: " + uid);
				}
				if(cookie.getName().equals("username")){
		        	username = cookie.getValue();
		        	System.out.println("username: " + username);
		        }
				if(cookie.getName().equals("ddd")){
		        	String ddd = cookie.getValue();
		        	System.out.println("ddd: " + ddd);
		        }
			}
			//2. get from form
			if (uid==null && username == null) {
				
			}
			*/
}