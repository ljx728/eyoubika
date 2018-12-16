package com.eyoubika.spider.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class SubscribeArticleDomain{
	private Integer articleId;	//文章id
	private String title;	//文章标题
	private String subTitle;	//文章副标题
	private String issueTime;	//发布时间
	private String webTime;	//网站时间
	private String fetchTime;	//抓取时间
	private String exId;	//抓取来源
	private String article;	//新闻内容
	private String status;	//状态
	private String haveImg;	//有图
	private String articleUrl;	//文章URL
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
	public String getSubTitle(){
		return subTitle;
	}
	public void setSubTitle(String subTitle){
		this.subTitle = subTitle;
	}
	public String getIssueTime(){
		return issueTime;
	}
	public void setIssueTime(String issueTime){
		this.issueTime = issueTime;
	}
	public String getWebTime(){
		return webTime;
	}
	public void setWebTime(String webTime){
		this.webTime = webTime;
	}
	public String getFetchTime(){
		return fetchTime;
	}
	public void setFetchTime(String fetchTime){
		this.fetchTime = fetchTime;
	}

	public String getExId() {
		return exId;
	}
	public void setExId(String exId) {
		this.exId = exId;
	}
	public String getArticle(){
		return article;
	}
	public void setArticle(String article){
		this.article = article;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getHaveImg(){
		return haveImg;
	}
	public void setHaveImg(String haveImg){
		this.haveImg = haveImg;
	}
	public String getArticleUrl(){
		return articleUrl;
	}
	public void setArticleUrl(String articleUrl){
		this.articleUrl = articleUrl;
	}
	public String toString() {
		String string = "";
		string += "文章id:\t"+ this.getArticleId()+"\n";
		string += "文章标题:\t"+ this.getTitle()+"\n";
		string += "文章副标题:\t"+ this.getSubTitle()+"\n";
		string += "发布时间:\t"+ this.getIssueTime()+"\n";
		string += "网站时间:\t"+ this.getWebTime()+"\n";
		string += "抓取时间:\t"+ this.getFetchTime()+"\n";
		string += "抓取来源:\t"+ this.getExId()+"\n";
		string += "新闻内容:\t"+ this.getArticle()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "有图:\t"+ this.getHaveImg()+"\n";
		string += "文章URL:\t"+ this.getArticleUrl()+"\n";
		return string;
	}
	public void init(){
		this.setArticleId(null);
		this.setTitle(null);
		this.setSubTitle(null);
		this.setIssueTime(null);
		this.setWebTime(null);
		this.setFetchTime(null);
		this.setExId(null);
		this.setArticle(null);
		this.setStatus(null);
		this.setHaveImg(null);
		this.setArticleUrl(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
