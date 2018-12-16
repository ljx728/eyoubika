package com.eyoubika.system.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class BadWordDomain{
	private Integer wordId;	//铭感次id
	private String word;	//敏感词
	private String type;	//类型
	private String date;	//添加时间
	public Integer getWordId(){
		return wordId;
	}
	public void setWordId(Integer wordId){
		this.wordId = wordId;
	}
	public String getWord(){
		return word;
	}
	public void setWord(String word){
		this.word = word;
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
		string += "铭感次id:\t"+ this.getWordId()+"\n";
		string += "敏感词:\t"+ this.getWord()+"\n";
		string += "类型:\t"+ this.getType()+"\n";
		string += "添加时间:\t"+ this.getDate()+"\n";
		return string;
	}
	public void init(){
		this.setWordId(null);
		this.setWord(null);
		this.setType(null);
		this.setDate(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
