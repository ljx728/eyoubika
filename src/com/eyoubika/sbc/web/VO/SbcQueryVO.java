package com.eyoubika.sbc.web.VO;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class SbcQueryVO{
	private String userId;	//用户标识
	private String token;	//令牌
	private String msg;	//消息
	private String ret;	//返回码
	private String exId;	//交易所ID
	private String sbcId;	//邮币卡Id
	private String sbcType;	//邮币卡类型
	private String sbcName;	//邮币卡名称
	public String getExId(){
		return exId;
	}
	public void setExId(String exId){
		this.exId = exId;
	}
	public String getSbcId(){
		return sbcId;
	}
	public void setSbcId(String sbcId){
		this.sbcId = sbcId;
	}
	public String getSbcType(){
		return sbcType;
	}
	public void setSbcType(String sbcType){
		this.sbcType = sbcType;
	}
	public String getSbcName(){
		return sbcName;
	}
	public void setSbcName(String sbcName){
		this.sbcName = sbcName;
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
		string += "交易所ID:\t"+ this.getExId()+"\n";
		string += "邮币卡Id:\t"+ this.getSbcId()+"\n";
		string += "邮币卡类型:\t"+ this.getSbcType()+"\n";
		string += "邮币卡名称:\t"+ this.getSbcName()+"\n";
		string += "用户标识:\t"+ this.getUserId()+"\n";
		string += "令牌:\t"+ this.getToken()+"\n";
		string += "返回码:\t"+ this.getRet()+"\n";
		string += "消息:\t"+ this.getMsg()+"\n";
		return string;
	}
	public void init(){
		this.setExId(null);
		this.setSbcId(null);
		this.setSbcType(null);
		this.setSbcName(null);
		this.setUserId(null);
		this.setToken(null);
		this.setRet(null);
		this.setMsg(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
