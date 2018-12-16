package com.eyoubika.user.domain;
public class UserBasicDomain{
	private Integer userId;	//用户id
	private String nickName;	//昵称
	private String password;	//密码
	private String token;	//令牌
	private String salt;	//盐值
	private String pnumber;	//手机号
	private String status;	//状态
	private String email;	//邮箱
	private String regiTime;	//注册时间
	private String tradePassword;	//交易密码
	public Integer getUserId(){
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public String getNickName(){
		return nickName;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getToken(){
		return token;
	}
	public void setToken(String token){
		this.token = token;
	}
	public String getSalt(){
		return salt;
	}
	public void setSalt(String salt){
		this.salt = salt;
	}
	public String getPnumber(){
		return pnumber;
	}
	public void setPnumber(String pnumber){
		this.pnumber = pnumber;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getRegiTime(){
		return regiTime;
	}
	public void setRegiTime(String regiTime){
		this.regiTime = regiTime;
	}
	public String getTradePassword(){
		return tradePassword;
	}
	public void setTradePassword(String tradePassword){
		this.tradePassword = tradePassword;
	}
	public String toString() {
		String string = "";
		string += "用户id:\t"+ this.getUserId()+"\n";
		string += "昵称:\t"+ this.getNickName()+"\n";
		string += "密码:\t"+ this.getPassword()+"\n";
		string += "令牌:\t"+ this.getToken()+"\n";
		string += "盐值:\t"+ this.getSalt()+"\n";
		string += "手机号:\t"+ this.getPnumber()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "邮箱:\t"+ this.getEmail()+"\n";
		string += "注册时间:\t"+ this.getRegiTime()+"\n";
		string += "交易密码:\t"+ this.getTradePassword()+"\n";
		return string;
	}
	public void init(){
		this.setUserId(null);
		this.setEmail(null);
		this.setNickName(null);
		this.setPassword(null);
		this.setPnumber(null);
		this.setRegiTime(null);
		this.setSalt(null);
		this.setStatus(null);
		this.setToken(null);
		this.setTradePassword(null);
	}
}
