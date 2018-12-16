package com.eyoubika.user.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class UserLoginDomain{
	private Integer userId;	//用户id
	private String UUID;	//UUID
	private String termType;	//终端类型
	private String firstLogiTime;	//首次登录时间
	private String lastLogiTime;	//上次登录时间
	private String logiTime;	//登录时间
	private String logiIp;	//登录IP
	private String version;	//客户端版本
	public Integer getUserId(){
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public String getUUID(){
		return UUID;
	}
	public void setUUID(String UUID){
		this.UUID = UUID;
	}
	public String getTermType(){
		return termType;
	}
	public void setTermType(String termType){
		this.termType = termType;
	}
	public String getFirstLogiTime(){
		return firstLogiTime;
	}
	public void setFirstLogiTime(String firstLogiTime){
		this.firstLogiTime = firstLogiTime;
	}
	public String getLastLogiTime(){
		return lastLogiTime;
	}
	public void setLastLogiTime(String lastLogiTime){
		this.lastLogiTime = lastLogiTime;
	}
	public String getLogiTime(){
		return logiTime;
	}
	public void setLogiTime(String logiTime){
		this.logiTime = logiTime;
	}
	public String getLogiIp(){
		return logiIp;
	}
	public void setLogiIp(String logiIp){
		this.logiIp = logiIp;
	}
	public String getVersion(){
		return version;
	}
	public void setVersion(String version){
		this.version = version;
	}
	public String toString() {
		String string = "";
		string += "用户id:\t"+ this.getUserId()+"\n";
		string += "UUID:\t"+ this.getUUID()+"\n";
		string += "终端类型:\t"+ this.getTermType()+"\n";
		string += "首次登录时间:\t"+ this.getFirstLogiTime()+"\n";
		string += "上次登录时间:\t"+ this.getLastLogiTime()+"\n";
		string += "登录时间:\t"+ this.getLogiTime()+"\n";
		string += "登录IP:\t"+ this.getLogiIp()+"\n";
		string += "客户端版本:\t"+ this.getVersion()+"\n";
		return string;
	}
	public void init(){
		this.setUserId(null);
		this.setUUID(null);
		this.setTermType(null);
		this.setFirstLogiTime(null);
		this.setLastLogiTime(null);
		this.setLogiTime(null);
		this.setLogiIp(null);
		this.setVersion(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
