package com.eyoubika.sbc.domain;
public class ExchangeBasicDomain{
	private String exId;	//交易所编码
	private String exName;	//交易所名称
	private String website;	//交易所网址
	public String getExId(){
		return exId;
	}
	public void setExId(String exId){
		this.exId = exId;
	}
	public String getExName(){
		return exName;
	}
	public void setExName(String exName){
		this.exName = exName;
	}
	public String getWebsite(){
		return website;
	}
	public void setWebsite(String website){
		this.website = website;
	}
	public String toString() {
		String string = "";
		string += "交易所编码:\t"+ this.getExId()+"\n";
		string += "交易所名称:\t"+ this.getExName()+"\n";
		string += "交易所网址:\t"+ this.getWebsite()+"\n";
		return string;
	}
}
