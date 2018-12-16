package com.eyoubika.model.domain;
import java.util.Map;
import java.util.List;
public class ModuleDomain{
	String namespace;	//命名空间
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
	
	List<Map<String,String>> actionList;	//属性列表
	List<Map<String,Object>> voList;	//属性列表
	
	
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public List<Map<String, Object>> getVoList() {
		return voList;
	}
	public void setVoList(List<Map<String, Object>> voList) {
		this.voList = voList;
	}
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

	public List<Map<String, String>> getActionList() {
		return actionList;
	}
	public void setActionList(List<Map<String, String>> actionList) {
		this.actionList = actionList;
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
		string += "命名空间:\t"+ this.getNamespace()+"\n";
		string += "action列表:\t"+ this.getActionList()+"\n";
		string += "vo列表:\t"+ this.getVoList()+"\n";
		return string;
	}
}