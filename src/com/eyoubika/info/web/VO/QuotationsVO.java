package com.eyoubika.info.web.VO;
public class QuotationsVO{
	private String sbcId;
	private String exId;
	private String interval;
	private String type;	//类型
	//private 
	protected String ret;
	protected String msg;
	protected String userId;
	protected String token;

	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getSbcId() {
		return sbcId;
	}
	public void setSbcId(String sbcId) {
		this.sbcId = sbcId;
	}
	public String getExId() {
		return exId;
	}
	public void setExId(String exId) {
		this.exId = exId;
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
	
	public void init(){
		this.setExId(null);
		this.setInterval(null);
		this.setMsg(null);
		this.setRet(null);
		this.setSbcId(null);
		this.setToken(null);
		this.setUserId(null);
	}
}