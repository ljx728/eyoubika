package com.eyoubika.spider.domain;
public class NewsArticleDomain{
	Integer newsId;	//新闻id
	String title;	//新闻标题
	String subTitle;	//新闻副标题
	String issueTime;	//发布时间
	String issueUnit;	//发布单位
	String webTime;	//网站时间
	String fetchTime;	//抓取时间
	String from;	//抓取来源
	String article;	//新闻内容
	String img;	//文章图片
	String imgPos;	//文章图片POS
	String table;	//文章表格
	String tablePos;	//文章表格POS
	String type;	//新闻类别
	String status;	//状态
	String haveImg;	//有图
	String haveTable;	//有表
	String articleUrl;	//文章URL
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
	public String getIssueUnit(){
		return issueUnit;
	}
	public void setIssueUnit(String issueUnit){
		this.issueUnit = issueUnit;
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
	public String getFrom(){
		return from;
	}
	public void setFrom(String from){
		this.from = from;
	}
	public String getArticle(){
		return article;
	}
	public void setArticle(String article){
		this.article = article;
	}
	public String getImg(){
		return img;
	}
	public void setImg(String img){
		this.img = img;
	}
	public String getImgPos(){
		return imgPos;
	}
	public void setImgPos(String imgPos){
		this.imgPos = imgPos;
	}
	public String getTable(){
		return table;
	}
	public void setTable(String table){
		this.table = table;
	}
	public String getTablePos(){
		return tablePos;
	}
	public void setTablePos(String tablePos){
		this.tablePos = tablePos;
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
	public String getHaveImg(){
		return haveImg;
	}
	public void setHaveImg(String haveImg){
		this.haveImg = haveImg;
	}
	public String getHaveTable(){
		return haveTable;
	}
	public void setHaveTable(String haveTable){
		this.haveTable = haveTable;
	}
	public String getArticleUrl(){
		return articleUrl;
	}
	public void setArticleUrl(String articleUrl){
		this.articleUrl = articleUrl;
	}
	public String toString() {
		String string = "";
		string += "新闻id:\t"+ this.getNewsId()+"\n";
		string += "新闻标题:\t"+ this.getTitle()+"\n";
		string += "新闻副标题:\t"+ this.getSubTitle()+"\n";
		string += "发布时间:\t"+ this.getIssueTime()+"\n";
		string += "发布单位:\t"+ this.getIssueUnit()+"\n";
		string += "网站时间:\t"+ this.getWebTime()+"\n";
		string += "抓取时间:\t"+ this.getFetchTime()+"\n";
		string += "抓取来源:\t"+ this.getFrom()+"\n";
		string += "新闻内容:\t"+ this.getArticle()+"\n";
		string += "文章图片:\t"+ this.getImg()+"\n";
		string += "文章图片POS:\t"+ this.getImgPos()+"\n";
		string += "文章表格:\t"+ this.getTable()+"\n";
		string += "文章表格POS:\t"+ this.getTablePos()+"\n";
		string += "新闻类别:\t"+ this.getType()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "有图:\t"+ this.getHaveImg()+"\n";
		string += "有表:\t"+ this.getHaveTable()+"\n";
		string += "文章URL:\t"+ this.getArticleUrl()+"\n";
		return string;
	}
}