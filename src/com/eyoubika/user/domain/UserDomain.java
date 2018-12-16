package com.eyoubika.user.domain;
/*==========================================================================================*
 * Description:	用户领域模型
 * Class:		UserDomain
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-23 20:13:59
 *==========================================================================================*/
public class UserDomain {
	private Integer userId;		
	private String nickName;	
	private String password;		
	private String token;
	private String salt;
	private String pnumber;
	private String status;
	private String tradePassword;
	private String regiTime;	
	
	public String getRegiTime() {
		return regiTime;
	}
	public void setRegiTime(String regiTime) {
		this.regiTime = regiTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPnumber() {
		return pnumber;
	}
	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTradePassword() {
		return tradePassword;
	}
	public void setTradePassword(String tradePassword) {
		this.tradePassword = tradePassword;
	}
	
	public String toString(){
		String str = "";
		str += "标识：\t" + this.getUserId() + "\n";
		str += "手机号码：\t" + this.getPnumber() + "\n";
		str += "昵称：\t" + this.getNickName() + "\n";
		str += "密码：\t" + this.getPassword() + "\n";
		str += "令牌：\t" + this.getToken() + "\n";
		str += "盐值：\t" + this.getSalt()+ "\n";	
		str += "状态：\t" + this.getStatus()+ "\n";		
		str += "交易密码：\t" + this.getTradePassword() + "\n";	
		return str;
	}
}