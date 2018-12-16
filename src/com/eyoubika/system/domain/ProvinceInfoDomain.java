package com.eyoubika.system.domain;
public class ProvinceInfoDomain{
	private String proId;	//省份id
	private String proName;	//省名
	public String getProId(){
		return proId;
	}
	public void setProId(String proId){
		this.proId = proId;
	}
	public String getProName(){
		return proName;
	}
	public void setProName(String proName){
		this.proName = proName;
	}
	public String toString() {
		String string = "";
		string += "省份id:\t"+ this.getProId()+"\n";
		string += "省名:\t"+ this.getProName()+"\n";
		return string;
	}
}
