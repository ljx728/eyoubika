package com.eyoubika.user.web.VO;
import com.eyoubika.common.BaseVO;
/*==========================================================================================*
 * Description:	定义了用户视图数据模型
 * Class:		UserVO
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-21 14:10:36
 *==========================================================================================*/
public class UserBasicVO {
	private String loginType;
	private String pnumber;		
	private String password;	
	private String authcode;		
	private String email;
	private String nickName;
	private String psn;
	private String version;
	private String  termType;
	protected String ret;
	protected String msg;
	protected String userId;
	protected String token;
	
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getPnumber() {
		return pnumber;
	}
	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthcode() {
		return authcode;
	}
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPsn() {
		return psn;
	}
	public void setPsn(String psn) {
		this.psn = psn;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}	
	public void init(){
		this.setUserId(null);
		this.setEmail(null);
		this.setNickName(null);
		this.setPassword(null);
		this.setPnumber(null);
		this.setLoginType(null);
		this.setAuthcode(null);
		this.setPsn(null);
		this.setToken(null);
		this.setVersion(null);
		this.setRet(null);
		this.setMsg(null);
	}
}