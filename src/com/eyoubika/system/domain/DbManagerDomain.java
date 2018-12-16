package com.eyoubika.system.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class DbManagerDomain{
	private String cId;	//cIdid
	private String host;	//主机
	private String port;	//端口
	private String database;	//数据库名
	private String table;	//数据表名
	private String type;	//类型
	private String user;	//数据库用户名
	private String password;	//数据库密码
	public String getCId(){
		return cId;
	}
	public void setCId(String cId){
		this.cId = cId;
	}
	public String getHost(){
		return host;
	}
	public void setHost(String host){
		this.host = host;
	}
	public String getPort(){
		return port;
	}
	public void setPort(String port){
		this.port = port;
	}
	public String getDatabase(){
		return database;
	}
	public void setDatabase(String database){
		this.database = database;
	}
	public String getTable(){
		return table;
	}
	public void setTable(String table){
		this.table = table;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getUser(){
		return user;
	}
	public void setUser(String user){
		this.user = user;
	}
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String toString() {
		String string = "";
		string += "cIdid:\t"+ this.getCId()+"\n";
		string += "主机:\t"+ this.getHost()+"\n";
		string += "端口:\t"+ this.getPort()+"\n";
		string += "数据库名:\t"+ this.getDatabase()+"\n";
		string += "数据表名:\t"+ this.getTable()+"\n";
		string += "类型:\t"+ this.getType()+"\n";
		string += "数据库用户名:\t"+ this.getUser()+"\n";
		string += "数据库密码:\t"+ this.getPassword()+"\n";
		return string;
	}
	public void init(){
		this.setCId(null);
		this.setHost(null);
		this.setPort(null);
		this.setDatabase(null);
		this.setTable(null);
		this.setType(null);
		this.setUser(null);
		this.setPassword(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
