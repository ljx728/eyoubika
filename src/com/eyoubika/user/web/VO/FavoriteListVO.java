package com.eyoubika.user.web.VO;
public class FavoriteListVO{
	private String userId;	//用户标识
	private String token;	//令牌
	private String msg;	//消息
	private String ret;	//返回码

	private String exId;	//交易所Id
	private String sbcId;	//sbcId
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public String getExId() {
		return exId;
	}
	public void setExId(String exId) {
		this.exId = exId;
	}
	public String getSbcId() {
		return sbcId;
	}
	public void setSbcId(String sbcId) {
		this.sbcId = sbcId;
	}
	
	public void init(){
		this.setUserId(null);
		this.setToken(null);
		this.setSbcId(null);
		this.setExId(null);
		this.setRet(null);
		this.setMsg(null);
	}
	
}
