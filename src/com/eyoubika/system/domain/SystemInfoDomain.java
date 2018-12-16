package com.eyoubika.system.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class SystemInfoDomain{
	private Integer sysId;	//系统信息id
	private String name;	//名称
	private String value;	//值
	private String type;	//类型
	private String date;	//添加时间
	public Integer getSysId(){
		return sysId;
	}
	public void setSysId(Integer sysId){
		this.sysId = sysId;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date = date;
	}
	public String toString() {
		String string = "";
		string += "系统信息id:\t"+ this.getSysId()+"\n";
		string += "名称:\t"+ this.getName()+"\n";
		string += "值:\t"+ this.getValue()+"\n";
		string += "类型:\t"+ this.getType()+"\n";
		string += "添加时间:\t"+ this.getDate()+"\n";
		return string;
	}
	public void init(){
		this.setSysId(null);
		this.setName(null);
		this.setValue(null);
		this.setType(null);
		this.setDate(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
