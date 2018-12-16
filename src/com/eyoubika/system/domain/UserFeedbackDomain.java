package com.eyoubika.system.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class UserFeedbackDomain{
	private Integer ufId;	//留言信息id
	private Integer feederId;	//反馈用户id
	private String nickName;	//用户昵称
	private String contact;	//联系方式
	private String content;	//反馈内容
	private String status;	//状态
	private String date;	//添加日期
	private String time;	//添加时间
	public Integer getUfId(){
		return ufId;
	}
	public void setUfId(Integer ufId){
		this.ufId = ufId;
	}
	public Integer getFeederId(){
		return feederId;
	}
	public void setFeederId(Integer feederId){
		this.feederId = feederId;
	}
	public String getNickName(){
		return nickName;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	public String getContact(){
		return contact;
	}
	public void setContact(String contact){
		this.contact = contact;
	}
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
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
		string += "留言信息id:\t"+ this.getUfId()+"\n";
		string += "反馈用户id:\t"+ this.getFeederId()+"\n";
		string += "用户昵称:\t"+ this.getNickName()+"\n";
		string += "联系方式:\t"+ this.getContact()+"\n";
		string += "反馈内容:\t"+ this.getContent()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "添加日期:\t"+ this.getDate()+"\n";
		string += "添加时间:\t"+ this.getTime()+"\n";
		return string;
	}
	public void init(){
		this.setUfId(null);
		this.setFeederId(null);
		this.setNickName(null);
		this.setContact(null);
		this.setContent(null);
		this.setStatus(null);
		this.setDate(null);
		this.setTime(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
