package com.eyoubika.spider.domain;
public class BreedArticleDomain{
	private Integer articleId;	//文章id
	private Integer sbcId;	//品种id
	private String title;	//新闻标题
	private String subTitle;	//新闻副标题
	private String issueTime;	//发布时间
	private String issueUnit;	//发布单位
	private String webTime;	//网站时间
	private String fetchTime;	//抓取时间
	private String from;	//抓取来源
	private String article;	//新闻内容
	private String img;	//文章图片
	private String imgPos;	//文章图片POS
	private String table;	//文章表格
	private String tablePos;	//文章表格POS
	private String type;	//新闻类别
	private String status;	//状态
	private String haveImg;	//有图
	private String haveTable;	//有表
	private String articleUrl;	//文章URL
	public Integer getArticleId(){
		return articleId;
	}
	public void setArticleId(Integer articleId){
		this.articleId = articleId;
	}
	public Integer getSbcId(){
		return sbcId;
	}
	public void setSbcId(Integer sbcId){
		this.sbcId = sbcId;
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
		string += "文章id:\t"+ this.getArticleId()+"\n";
		string += "品种id:\t"+ this.getSbcId()+"\n";
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
	public void init(){
		this.setArticleId(null);
		this.setSbcId(null);
		this.setTitle(null);
		this.setSubTitle(null);
		this.setIssueTime(null);
		this.setIssueUnit(null);
		this.setWebTime(null);
		this.setFetchTime(null);
		this.setFrom(null);
		this.setArticle(null);
		this.setImg(null);
		this.setImgPos(null);
		this.setTable(null);
		this.setTablePos(null);
		this.setType(null);
		this.setStatus(null);
		this.setHaveImg(null);
		this.setHaveTable(null);
		this.setArticleUrl(null);
	}
}
