package com.eyoubika.sbc.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class SbcIdMapDomain{
	private Integer mapId;	//映射键id
	private Integer sbcId;	//品种id
	private String ephId;	//一片红商品id
	private String ephCode;	//一片红商品编码
	private String status;	//状态
	public Integer getMapId(){
		return mapId;
	}
	public void setMapId(Integer mapId){
		this.mapId = mapId;
	}
	public Integer getSbcId(){
		return sbcId;
	}
	public void setSbcId(Integer sbcId){
		this.sbcId = sbcId;
	}
	public String getEphId(){
		return ephId;
	}
	public void setEphId(String ephId){
		this.ephId = ephId;
	}
	public String getEphCode(){
		return ephCode;
	}
	public void setEphCode(String ephCode){
		this.ephCode = ephCode;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String toString() {
		String string = "";
		string += "映射键id:\t"+ this.getMapId()+"\n";
		string += "品种id:\t"+ this.getSbcId()+"\n";
		string += "一片红商品id:\t"+ this.getEphId()+"\n";
		string += "一片红商品编码:\t"+ this.getEphCode()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		return string;
	}
	public void init(){
		this.setMapId(null);
		this.setSbcId(null);
		this.setEphId(null);
		this.setEphCode(null);
		this.setStatus(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
