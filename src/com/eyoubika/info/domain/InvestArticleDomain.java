package com.eyoubika.info.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class InvestArticleDomain{
	private Integer articleId;	//文章id
	private String title;	//标题
	private String url;	//url
	private String source;	//分享地址
	private String brief;	//简介
	private String content;	//正文
	private String author;	//作者
	private String tag;	//标签
	private String exId;	//交易所
	private String type;	//类别
	private String status;	//状态
	private String genDate;	//添加日期
	private String genTime;	//添加时间
	private String modDate;	//修改日期
	private String modTime;	//修改时间
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
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
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public String getAuthor(){
		return author;
	}
	public void setAuthor(String author){
		this.author = author;
	}
	public String getTag(){
		return tag;
	}
	public void setTag(String tag){
		this.tag = tag;
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
	public String getModDate(){
		return modDate;
	}
	public void setModDate(String modDate){
		this.modDate = modDate;
	}
	public String getModTime(){
		return modTime;
	}
	public void setModTime(String modTime){
		this.modTime = modTime;
	}
	public String toString() {
		String string = "";
		string += "文章id:\t"+ this.getArticleId()+"\n";
		string += "标题:\t"+ this.getTitle()+"\n";
		string += "url:\t"+ this.getUrl()+"\n";
		string += "source:\t"+ this.getSource()+"\n";
		string += "简介:\t"+ this.getBrief()+"\n";
		string += "正文:\t"+ this.getContent()+"\n";
		string += "作者:\t"+ this.getAuthor()+"\n";
		string += "标签:\t"+ this.getTag()+"\n";
		string += "交易所:\t"+ this.getExId()+"\n";
		string += "类别:\t"+ this.getType()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "添加日期:\t"+ this.getGenDate()+"\n";
		string += "添加时间:\t"+ this.getGenTime()+"\n";
		string += "修改日期:\t"+ this.getModDate()+"\n";
		string += "修改时间:\t"+ this.getModTime()+"\n";
		return string;
	}
	public void init(){
		this.setArticleId(null);
		this.setTitle(null);
		this.setUrl(null);
		this.setSource(null);
		this.setBrief(null);
		this.setContent(null);
		this.setAuthor(null);
		this.setTag(null);
		this.setExId(null);
		this.setType(null);
		this.setStatus(null);
		this.setGenDate(null);
		this.setGenTime(null);
		this.setModDate(null);
		this.setModTime(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
