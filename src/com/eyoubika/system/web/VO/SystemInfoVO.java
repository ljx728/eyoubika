package com.eyoubika.system.web.VO;
public class SystemInfoVO{
	private String userId;	//用户标识
	private String token;	//令牌
	private String msg;	//消息
	private String ret;	//返回码
	private String sysId;	//系统信息ID
	private String name;	//名称
	private String value;	//值
	private String date;	//时间
	private String type;	//类型
	public String getSysId(){
		return sysId;
	}
	public void setSysId(String sysId){
		this.sysId = sysId;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
	}
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date = date;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getRet(){
		return ret;
	}
	public void setRet(String ret){
		this.ret = ret;
	}
	public String getToken(){
		return token;
	}
	public void setToken(String token){
		this.token = token;
	}
	public String getMsg(){
		return msg;
	}
	public void setMsg(String msg){
		this.msg = msg;
	}
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String toString() {
		String string = "";
		string += "系统信息ID:\t"+ this.getSysId()+"\n";
		string += "名称:\t"+ this.getName()+"\n";
		string += "值:\t"+ this.getValue()+"\n";
		string += "时间:\t"+ this.getDate()+"\n";
		string += "类型:\t"+ this.getType()+"\n";
		string += "用户标识:\t"+ this.getUserId()+"\n";
		string += "令牌:\t"+ this.getToken()+"\n";
		string += "返回码:\t"+ this.getRet()+"\n";
		string += "消息:\t"+ this.getMsg()+"\n";
		return string;
	}
	public void init(){
		this.setSysId(null);
		this.setName(null);
		this.setValue(null);
		this.setDate(null);
		this.setType(null);
		this.setUserId(null);
		this.setToken(null);
		this.setRet(null);
		this.setMsg(null);
	}
}
