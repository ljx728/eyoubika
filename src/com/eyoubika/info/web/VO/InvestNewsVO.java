package com.eyoubika.info.web.VO;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class InvestNewsVO{
	private String userId;	//用户标识
	private String token;	//令牌
	private String msg;	//消息
	private String ret;	//返回码
	private String title;	//标题
	private String newsId;	//NewsId
	private String type;	//类型
	private String brief;	//简介
	private String url;	//URL
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getNewsId(){
		return newsId;
	}
	public void setNewsId(String newsId){
		this.newsId = newsId;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getBrief(){
		return brief;
	}
	public void setBrief(String brief){
		this.brief = brief;
	}
	public String getUrl(){
		return url;
	}
	public void setUrl(String url){
		this.url = url;
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
		string += "标题:\t"+ this.getTitle()+"\n";
		string += "NewsId:\t"+ this.getNewsId()+"\n";
		string += "类型:\t"+ this.getType()+"\n";
		string += "简介:\t"+ this.getBrief()+"\n";
		string += "URL:\t"+ this.getUrl()+"\n";
		string += "用户标识:\t"+ this.getUserId()+"\n";
		string += "令牌:\t"+ this.getToken()+"\n";
		string += "返回码:\t"+ this.getRet()+"\n";
		string += "消息:\t"+ this.getMsg()+"\n";
		return string;
	}
	public void init(){
		this.setTitle(null);
		this.setNewsId(null);
		this.setType(null);
		this.setBrief(null);
		this.setUrl(null);
		this.setUserId(null);
		this.setToken(null);
		this.setRet(null);
		this.setMsg(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
