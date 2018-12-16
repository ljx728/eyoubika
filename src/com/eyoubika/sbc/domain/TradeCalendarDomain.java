package com.eyoubika.sbc.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class TradeCalendarDomain{
	private String date;	//日期
	private String week;	//名称
	private String month;	//名称
	private String type;	//类型
	private String status;	//状态
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
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String toString() {
		String string = "";
		string += "日期:\t"+ this.getDate()+"\n";
		string += "名称:\t"+ this.getWeek()+"\n";
		string += "名称:\t"+ this.getMonth()+"\n";
		string += "类型:\t"+ this.getType()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		return string;
	}
	public void init(){
		this.setDate(null);
		this.setWeek(null);
		this.setMonth(null);
		this.setType(null);
		this.setStatus(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
