package com.eyoubika.sbc.domain;
public class SbcBasicDomain{
	private Integer sbcId;	//品种id
	private String sbcCode;	//品种编号
	private String sbcName;	//名称
	private String sbcType;	//类别
	private String issueTime;	//发行日期
	private String issueOrg;	//发行机构
	private String issueVolume;	//发行量
	private String issuePrice;	//发行价
	private String faceValue;	//面值
	private String texture;	//材质
	private String volume;	//库存量
	private String exId;	//交易所ID
	private String status;	//状态
	private String inputDate;	//录入时间
	public Integer getSbcId(){
		return sbcId;
	}
	public void setSbcId(Integer sbcId){
		this.sbcId = sbcId;
	}
	public String getSbcCode(){
		return sbcCode;
	}
	public void setSbcCode(String sbcCode){
		this.sbcCode = sbcCode;
	}
	public String getSbcName(){
		return sbcName;
	}
	public void setSbcName(String sbcName){
		this.sbcName = sbcName;
	}
	public String getSbcType(){
		return sbcType;
	}
	public void setSbcType(String sbcType){
		this.sbcType = sbcType;
	}
	public String getIssueTime(){
		return issueTime;
	}
	public void setIssueTime(String issueTime){
		this.issueTime = issueTime;
	}
	public String getIssueOrg(){
		return issueOrg;
	}
	public void setIssueOrg(String issueOrg){
		this.issueOrg = issueOrg;
	}
	public String getIssueVolume(){
		return issueVolume;
	}
	public void setIssueVolume(String issueVolume){
		this.issueVolume = issueVolume;
	}
	public String getIssuePrice(){
		return issuePrice;
	}
	public void setIssuePrice(String issuePrice){
		this.issuePrice = issuePrice;
	}
	public String getFaceValue(){
		return faceValue;
	}
	public void setFaceValue(String faceValue){
		this.faceValue = faceValue;
	}
	public String getTexture(){
		return texture;
	}
	public void setTexture(String texture){
		this.texture = texture;
	}
	public String getVolume(){
		return volume;
	}
	public void setVolume(String volume){
		this.volume = volume;
	}
	public String getExId(){
		return exId;
	}
	public void setExId(String exId){
		this.exId = exId;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getInputDate(){
		return inputDate;
	}
	public void setInputDate(String inputDate){
		this.inputDate = inputDate;
	}
	public String toString() {
		String string = "";
		string += "品种id:\t"+ this.getSbcId()+"\n";
		string += "品种编号:\t"+ this.getSbcCode()+"\n";
		string += "名称:\t"+ this.getSbcName()+"\n";
		string += "类别:\t"+ this.getSbcType()+"\n";
		string += "发行日期:\t"+ this.getIssueTime()+"\n";
		string += "发行机构:\t"+ this.getIssueOrg()+"\n";
		string += "发行量:\t"+ this.getIssueVolume()+"\n";
		string += "发行价:\t"+ this.getIssuePrice()+"\n";
		string += "面值:\t"+ this.getFaceValue()+"\n";
		string += "材质:\t"+ this.getTexture()+"\n";
		string += "库存量:\t"+ this.getVolume()+"\n";
		string += "交易所ID:\t"+ this.getExId()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "录入时间:\t"+ this.getInputDate()+"\n";
		return string;
	}
	public void init(){
		this.setSbcId(null);
		this.setSbcCode(null);
		this.setSbcName(null);
		this.setSbcType(null);
		this.setIssueTime(null);
		this.setIssueOrg(null);
		this.setIssueVolume(null);
		this.setIssuePrice(null);
		this.setFaceValue(null);
		this.setTexture(null);
		this.setVolume(null);
		this.setExId(null);
		this.setStatus(null);
		this.setInputDate(null);
	}
}
