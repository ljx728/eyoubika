package com.eyoubika.user.domain;
public class OpenAccountDetailDomain{
	private String openNo;	//开户业务编号
	private Integer batchNo;	//上传批次号
	private byte[] identifyFrontPic;	//身份证正面照片
	private byte[] identifyBackPic;	//身份证反面照片
	private byte[] bankCardFrontPic;	//银行卡正面照片
	private byte[] bankCardBackPic;	//银行卡反面照片
	private String uploadDate;	//上传日期
	private String status;	//状态
	public String getOpenNo(){
		return openNo;
	}
	public void setOpenNo(String openNo){
		this.openNo = openNo;
	}
	public Integer getBatchNo(){
		return batchNo;
	}
	public void setBatchNo(Integer batchNo){
		this.batchNo = batchNo;
	}
	public byte[] getIdentifyFrontPic(){
		return identifyFrontPic;
	}
	public void setIdentifyFrontPic(byte[] identifyFrontPic){
		this.identifyFrontPic = identifyFrontPic;
	}
	public byte[] getIdentifyBackPic(){
		return identifyBackPic;
	}
	public void setIdentifyBackPic(byte[] identifyBackPic){
		this.identifyBackPic = identifyBackPic;
	}
	public byte[] getBankCardFrontPic(){
		return bankCardFrontPic;
	}
	public void setBankCardFrontPic(byte[] bankCardFrontPic){
		this.bankCardFrontPic = bankCardFrontPic;
	}
	public byte[] getBankCardBackPic(){
		return bankCardBackPic;
	}
	public void setBankCardBackPic(byte[] bankCardBackPic){
		this.bankCardBackPic = bankCardBackPic;
	}
	public String getUploadDate(){
		return uploadDate;
	}
	public void setUploadDate(String uploadDate){
		this.uploadDate = uploadDate;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String toString() {
		String string = "";
		string += "开户业务编号:\t"+ this.getOpenNo()+"\n";
		string += "上传批次号:\t"+ this.getBatchNo()+"\n";
		string += "身份证正面照片:\t"+ this.getIdentifyFrontPic()+"\n";
		string += "身份证反面照片:\t"+ this.getIdentifyBackPic()+"\n";
		string += "银行卡正面照片:\t"+ this.getBankCardFrontPic()+"\n";
		string += "银行卡反面照片:\t"+ this.getBankCardBackPic()+"\n";
		string += "上传日期:\t"+ this.getUploadDate()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		return string;
	}
}
