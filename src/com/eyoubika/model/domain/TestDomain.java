package com.eyoubika.model.domain;
public class TestDomain{
	int testId;	//自增id
	String modulePath;	//模块路径
	String className;	//类名
	String packageName;	//包名
	String tableName;	//表名
	public int getTestId(){
		return testId;
	}
	public void setTestId(int testId){
		this.testId = testId;
	}
	public String getModulePath(){
		return modulePath;
	}
	public void setModulePath(String modulePath){
		this.modulePath = modulePath;
	}
	public String getClassName(){
		return className;
	}
	public void setClassName(String className){
		this.className = className;
	}
	public String getPackageName(){
		return packageName;
	}
	public void setPackageName(String packageName){
		this.packageName = packageName;
	}
	public String getTableName(){
		return tableName;
	}
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public String toString() {
		String string = "";
		string += "自增id:\t"+ this.getTestId()+"\n";
		string += "模块路径:\t"+ this.getModulePath()+"\n";
		string += "类名:\t"+ this.getClassName()+"\n";
		string += "包名:\t"+ this.getPackageName()+"\n";
		string += "表名:\t"+ this.getTableName()+"\n";
		return string;
	}
}