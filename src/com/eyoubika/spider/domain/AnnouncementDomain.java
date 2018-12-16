package com.eyoubika.spider.domain;

public class AnnouncementDomain {
	private int annoucementId;
	private String status;
	private String title;
	private String subTitle;
	private String annouceTime;		//通知时间
	private String article;			//文章	
	private String annouceUnit;		//发布单位
	private String documnetNo;		//公文号
	
	private String from;
	private String webTime;				//网站发布时间
	
	private String imgId;		// 文章图片id
	private String imgPos;		// 文章图片pos
	private String tableId;		// 文章表格id
	private String tablePos;	// 文章表格pos

	private String type;		//公告信息类别：普通、申购、托管
	//通知公告。行业资讯。品种信息。动态的交易信息。

	public int getAnnoucementId() {
		return annoucementId;
	}

	public void setAnnoucementId(int annoucementId) {
		this.annoucementId = annoucementId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getAnnouceTime() {
		return annouceTime;
	}

	public void setAnnouceTime(String annouceTime) {
		this.annouceTime = annouceTime;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getAnnouceUnit() {
		return annouceUnit;
	}

	public void setAnnouceUnit(String annouceUnit) {
		this.annouceUnit = annouceUnit;
	}

	public String getDocumnetNo() {
		return documnetNo;
	}

	public void setDocumnetNo(String documnetNo) {
		this.documnetNo = documnetNo;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getWebTime() {
		return webTime;
	}

	public void setWebTime(String webTime) {
		this.webTime = webTime;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getImgPos() {
		return imgPos;
	}

	public void setImgPos(String imgPos) {
		this.imgPos = imgPos;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTablePos() {
		return tablePos;
	}

	public void setTablePos(String tablePos) {
		this.tablePos = tablePos;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String toString(){
		String str = null;
		str += "通知ID：" + this.getAnnoucementId() + "\n";
		str += "标题：" + this.getTitle();
		return str;
	}
}