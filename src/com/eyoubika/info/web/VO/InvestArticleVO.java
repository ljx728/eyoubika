package com.eyoubika.info.web.VO;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class InvestArticleVO{
	private String userId;	//用户标识
	private String token;	//令牌
	private String msg;	//消息
	private String ret;	//返回码
	private String status;	//状态
	private String exId;	//交易所
	private String tag;	//标签
	private String type;	//类别
	private String articleId;	//文章id
	private String url;		//url
	private String source;		//分享地址
	private String content;	//正文
	private String author;	//作者
	private String title;	//标题
	private String modDate;	//最新修改日期
	private String brief;	//简介
	private String genDate;	//添加日期
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getExId(){
		return exId;
	}
	public void setExId(String exId){
		this.exId = exId;
	}
	public String getTag(){
		return tag;
	}
	public void setTag(String tag){
		this.tag = tag;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getArticleId(){
		return articleId;
	}
	public void setArticleId(String articleId){
		this.articleId = articleId;
	}
	public String getUrl(){
		return url;
	}
	public void setUrl(String url){
		this.url = url;
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
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getModDate(){
		return modDate;
	}
	public void setModDate(String modDate){
		this.modDate = modDate;
	}
	public String getBrief(){
		return brief;
	}
	public void setBrief(String brief){
		this.brief = brief;
	}
	public String getGenDate(){
		return genDate;
	}
	public void setGenDate(String genDate){
		this.genDate = genDate;
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
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "交易所:\t"+ this.getExId()+"\n";
		string += "标签:\t"+ this.getTag()+"\n";
		string += "类别:\t"+ this.getType()+"\n";
		string += "文章id:\t"+ this.getArticleId()+"\n";
		string += "url:\t"+ this.getUrl()+"\n";
		string += "source:\t"+ this.getSource()+"\n";
		string += "正文:\t"+ this.getContent()+"\n";
		string += "作者:\t"+ this.getAuthor()+"\n";
		string += "标题:\t"+ this.getTitle()+"\n";
		string += "最新修改日期:\t"+ this.getModDate()+"\n";
		string += "简介:\t"+ this.getBrief()+"\n";
		string += "添加日期:\t"+ this.getGenDate()+"\n";
		string += "用户标识:\t"+ this.getUserId()+"\n";
		string += "令牌:\t"+ this.getToken()+"\n";
		string += "返回码:\t"+ this.getRet()+"\n";
		string += "消息:\t"+ this.getMsg()+"\n";
		return string;
	}
	public void init(){
		this.setStatus(null);
		this.setExId(null);
		this.setTag(null);
		this.setType(null);
		this.setArticleId(null);
		this.setUrl(null);
		this.setSource(null);
		this.setContent(null);
		this.setAuthor(null);
		this.setTitle(null);
		this.setModDate(null);
		this.setBrief(null);
		this.setGenDate(null);
		this.setUserId(null);
		this.setToken(null);
		this.setRet(null);
		this.setMsg(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
