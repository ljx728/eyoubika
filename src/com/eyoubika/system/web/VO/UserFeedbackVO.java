package com.eyoubika.system.web.VO;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class UserFeedbackVO{
	private String userId;	//用户标识
	private String token;	//令牌
	private String msg;	//消息
	private String ret;	//返回码
	private String content;	//反馈内容
	private String time;	//添加时间
	private String ufId;	//留言信息id
	private String feederId;	//用户标识
	private String status;	//状态
	private String nickName;	//用户昵称
	private String date;	//添加日期
	private String contact;	//联系方式
	public String getContent(){
		return content;
	}
	
	public String getFeederId() {
		return feederId;
	}

	public void setFeederId(String feederId) {
		this.feederId = feederId;
	}

	public void setContent(String content){
		this.content = content;
	}
	public String getTime(){
		return time;
	}
	public void setTime(String time){
		this.time = time;
	}
	public String getUfId(){
		return ufId;
	}
	public void setUfId(String ufId){
		this.ufId = ufId;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getNickName(){
		return nickName;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date = date;
	}
	public String getContact(){
		return contact;
	}
	public void setContact(String contact){
		this.contact = contact;
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
		string += "反馈内容:\t"+ this.getContent()+"\n";
		string += "反馈用户:\t"+ this.getFeederId()+"\n";
		string += "添加时间:\t"+ this.getTime()+"\n";
		string += "留言信息id:\t"+ this.getUfId()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "用户昵称:\t"+ this.getNickName()+"\n";
		string += "添加日期:\t"+ this.getDate()+"\n";
		string += "联系方式:\t"+ this.getContact()+"\n";
		string += "用户标识:\t"+ this.getUserId()+"\n";
		string += "令牌:\t"+ this.getToken()+"\n";
		string += "返回码:\t"+ this.getRet()+"\n";
		string += "消息:\t"+ this.getMsg()+"\n";
		return string;
	}
	public void init(){
		this.setContent(null);
		this.setTime(null);
		this.setUfId(null);
		this.setStatus(null);
		this.setNickName(null);
		this.setDate(null);
		this.setContact(null);
		this.setUserId(null);
		this.setToken(null);
		this.setFeederId(null);
		this.setRet(null);
		this.setMsg(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
