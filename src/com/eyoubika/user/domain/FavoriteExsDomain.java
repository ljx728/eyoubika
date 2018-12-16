package com.eyoubika.user.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class FavoriteExsDomain{
	private Integer userId;	//用户id
	private String exId;	//exId
	private String date;	//添加日期
	private String time;	//添加时间
	public Integer getUserId(){
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public String getExId(){
		return exId;
	}
	public void setExId(String exId){
		this.exId = exId;
	}
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date = date;
	}
	public String getTime(){
		return time;
	}
	public void setTime(String time){
		this.time = time;
	}
	public String toString() {
		String string = "";
		string += "用户id:\t"+ this.getUserId()+"\n";
		string += "exId:\t"+ this.getExId()+"\n";
		string += "添加日期:\t"+ this.getDate()+"\n";
		string += "添加时间:\t"+ this.getTime()+"\n";
		return string;
	}
	public void init(){
		this.setUserId(null);
		this.setExId(null);
		this.setDate(null);
		this.setTime(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
