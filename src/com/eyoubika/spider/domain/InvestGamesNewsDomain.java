package com.eyoubika.spider.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class InvestGamesNewsDomain{
	private Integer newsId;	//资讯id
	private String title;	//标题
	private String url;	//url
	private String brief;	//简介
	private String type;	//类型
	private String date;	//添加日期
	private String time;	//添加时间
	public Integer getNewsId(){
		return newsId;
	}
	public void setNewsId(Integer newsId){
		this.newsId = newsId;
	}
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getUrl(){
		return url;
	}
	public void setUrl(String url){
		this.url = url;
	}
	public String getBrief(){
		return brief;
	}
	public void setBrief(String brief){
		this.brief = brief;
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
	public String getTime(){
		return time;
	}
	public void setTime(String time){
		this.time = time;
	}
	public String toString() {
		String string = "";
		string += "资讯id:\t"+ this.getNewsId()+"\n";
		string += "标题:\t"+ this.getTitle()+"\n";
		string += "url:\t"+ this.getUrl()+"\n";
		string += "简介:\t"+ this.getBrief()+"\n";
		string += "类型:\t"+ this.getType()+"\n";
		string += "添加日期:\t"+ this.getDate()+"\n";
		string += "添加时间:\t"+ this.getTime()+"\n";
		return string;
	}
	public void init(){
		this.setNewsId(null);
		this.setTitle(null);
		this.setUrl(null);
		this.setBrief(null);
		this.setType(null);
		this.setDate(null);
		this.setTime(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
