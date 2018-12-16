package com.eyoubika.model.domain;
import java.util.Map;
import java.util.List;
public class JavaFileModelDomain{
	String moduleName;	//模块名称
	String moduleDir;	//模块路径
	String layerType;	//层次类型
	String packageName;	//包名
	String className;	//类名
	String fileDir;	//文件路径
	String fileType;	//文件类型
	String tableName;	//表名
	String primaryKey;	//表主键
	boolean isAuto;		//主键自增
	List<Map<String,String>> attributeList;	//属性列表
	public String getModuleName(){
		return moduleName;
	}
	public void setModuleName(String moduleName){
		this.moduleName = moduleName;
	}
	
	public boolean isAuto() {
		return isAuto;
	}
	public void setAuto(boolean isAuto) {
		this.isAuto = isAuto;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getModuleDir(){
		return moduleDir;
	}
	public void setModuleDir(String moduleDir){
		this.moduleDir = moduleDir;
	}
	public String getLayerType(){
		return layerType;
	}
	public void setLayerType(String layerType){
		this.layerType = layerType;
	}
	public String getPackageName(){
		return packageName;
	}
	public void setPackageName(String packageName){
		this.packageName = packageName;
	}
	public String getClassName(){
		return className;
	}
	public void setClassName(String className){
		this.className = className;
	}
	public String getFileDir(){
		return fileDir;
	}
	public void setFileDir(String fileDir){
		this.fileDir = fileDir;
	}
	public String getFileType(){
		return fileType;
	}
	public void setFileType(String fileType){
		this.fileType = fileType;
	}
	public String getTableName(){
		return tableName;
	}
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public List<Map<String,String>> getAttributeList(){
		return attributeList;
	}
	public void setAttributeList(List<Map<String,String>> attributeList){
		this.attributeList = attributeList;
	}
	public String toString() {
		String string = "";
		string += "模块名称:\t"+ this.getModuleName()+"\n";
		string += "模块路径:\t"+ this.getModuleDir()+"\n";
		string += "层次类型:\t"+ this.getLayerType()+"\n";
		string += "包名:\t"+ this.getPackageName()+"\n";
		string += "类名:\t"+ this.getClassName()+"\n";
		string += "文件路径:\t"+ this.getFileDir()+"\n";
		string += "文件类型:\t"+ this.getFileType()+"\n";
		string += "表名:\t"+ this.getTableName()+"\n";
		string += "表Id:\t"+ this.getPrimaryKey()+"\n";
		string += "属性列表:\t"+ this.getAttributeList()+"\n";
		return string;
	}
}