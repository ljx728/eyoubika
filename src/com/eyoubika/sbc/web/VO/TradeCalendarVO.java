package com.eyoubika.sbc.web.VO;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class TradeCalendarVO{
	private String userId;	//用户标识
	private String token;	//令牌
	private String msg;	//消息
	private String ret;	//返回码
	private String status;	//状态
	private String month;	//月次
	private String type;	//交易日类型
	private String date;	//日期
	private String week;	//周次
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getMonth(){
		return month;
	}
	public void setMonth(String month){
		this.month = month;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date = date;
	}
	public String getWeek(){
		return week;
	}
	public void setWeek(String week){
		this.week = week;
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
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "月次:\t"+ this.getMonth()+"\n";
		string += "交易日类型:\t"+ this.getType()+"\n";
		string += "日期:\t"+ this.getDate()+"\n";
		string += "周次:\t"+ this.getWeek()+"\n";
		string += "用户标识:\t"+ this.getUserId()+"\n";
		string += "令牌:\t"+ this.getToken()+"\n";
		string += "返回码:\t"+ this.getRet()+"\n";
		string += "消息:\t"+ this.getMsg()+"\n";
		return string;
	}
	public void init(){
		this.setStatus(null);
		this.setMonth(null);
		this.setType(null);
		this.setDate(null);
		this.setWeek(null);
		this.setUserId(null);
		this.setToken(null);
		this.setRet(null);
		this.setMsg(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
