package com.eyoubika.user.domain;
public class FavoriteSbcsDomain{
	private Integer userId;	//用户id
	private Integer sbcId;	//SBCid
	private String date;	//添加日期
	private String time;	//添加时间
	public Integer getUserId(){
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public Integer getSbcId(){
		return sbcId;
	}
	public void setSbcId(Integer sbcId){
		this.sbcId = sbcId;
	}
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String toString() {
		String string = "";
		string += "用户id:\t"+ this.getUserId()+"\n";
		string += "SBCid:\t"+ this.getSbcId()+"\n";
		string += "添加日期:\t"+ this.getDate()+"\n";
		string += "添加时间:\t"+ this.getTime()+"\n";
		return string;
	}
	public void init(){
		this.setUserId(null);
		this.setSbcId(null);
		this.setDate(null);
		this.setTime(null);
	}
}
