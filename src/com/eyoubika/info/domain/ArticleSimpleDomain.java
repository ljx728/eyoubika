package com.eyoubika.info.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class ArticleSimpleDomain{
	private Integer articleId;	//文章id
	private String title;	//标题
	private String url;	//url
	private String brief;	//简介
	private String author;	//作者
	private String exId;	//交易所
	private String type;	//类别
	private String status;	//状态
	private String genDate;	//添加日期
	private String genTime;	//添加时间
	
	public Integer getArticleId(){
		return articleId;
	}
	public void setArticleId(Integer articleId){
		this.articleId = articleId;
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

	public String getAuthor(){
		return author;
	}
	public void setAuthor(String author){
		this.author = author;
	}
	public String getExId(){
		return exId;
	}
	public void setExId(String exId){
		this.exId = exId;
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
	public String getGenDate(){
		return genDate;
	}
	public void setGenDate(String genDate){
		this.genDate = genDate;
	}
	public String getGenTime(){
		return genTime;
	}
	public void setGenTime(String genTime){
		this.genTime = genTime;
	}

	public String toString() {
		String string = "";
		string += "文章id:\t"+ this.getArticleId()+"\n";
		string += "标题:\t"+ this.getTitle()+"\n";
		string += "url:\t"+ this.getUrl()+"\n";
		string += "简介:\t"+ this.getBrief()+"\n";
		string += "作者:\t"+ this.getAuthor()+"\n";
		string += "交易所:\t"+ this.getExId()+"\n";
		string += "类别:\t"+ this.getType()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "添加日期:\t"+ this.getGenDate()+"\n";
		string += "添加时间:\t"+ this.getGenTime()+"\n";
		return string;
	}
	public void init(){
		this.setArticleId(null);
		this.setTitle(null);
		this.setUrl(null);
		this.setBrief(null);
		this.setAuthor(null);
		this.setExId(null);
		this.setType(null);
		this.setStatus(null);
		this.setGenDate(null);
		this.setGenTime(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
