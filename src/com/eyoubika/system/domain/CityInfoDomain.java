package com.eyoubika.system.domain;
public class CityInfoDomain{
	private String cityId;	//城市id
	private String cityName;	//城市名称
	private String proId;	//省份id
	public String getCityId(){
		return cityId;
	}
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	public String getCityName(){
		return cityName;
	}
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	public String getProId(){
		return proId;
	}
	public void setProId(String proId){
		this.proId = proId;
	}
	public String toString() {
		String string = "";
		string += "城市id:\t"+ this.getCityId()+"\n";
		string += "城市名称:\t"+ this.getCityName()+"\n";
		string += "省份id:\t"+ this.getProId()+"\n";
		return string;
	}
}
