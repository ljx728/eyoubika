package com.eyoubika.sbc.domain;
public class SbcSimpleDomain{
	private Integer sbcId;	//品种id
	private String sbcCode;	//品种编号
	private String sbcName;	//名称
	private String sbcType;	//类别
	private String exId;	//交易所ID
	private String status;	//状态
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
	
	public String getExId() {
		return exId;
	}
	public void setExId(String exId) {
		this.exId = exId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String toString() {
		String string = "";
		string += "品种id:\t"+ this.getSbcId()+"\n";
		string += "品种编号:\t"+ this.getSbcCode()+"\n";
		string += "名称:\t"+ this.getSbcName()+"\n";
		string += "类别:\t"+ this.getSbcType()+"\n";
		string += "交易所ID:\t"+ this.getExId()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		return string;
	}
	public void init(){
		this.setSbcId(null);
		this.setSbcCode(null);
		this.setSbcName(null);
		this.setSbcType(null);
		this.setExId(null);
		this.setStatus(null);
	}
}
