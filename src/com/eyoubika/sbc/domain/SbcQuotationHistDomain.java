package com.eyoubika.sbc.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class SbcQuotationHistDomain{
	private Integer sbcId;	//sbcId
	private String openingPrice;	//开盘价
	private String closePrice;	//收盘价
	private String highestPrice;	//最高价
	private String lowestPrice;	//最低价
	private String riseValue;	//涨跌
	private String dealVolume;	//成交量
	private String dealValue;	//成交额
	private String riseScope;	//涨幅
	private String type;	//类型：月，周，日
	private String date;	//时间
	
	public Integer getSbcId(){
		return sbcId;
	}
	public void setSbcId(Integer sbcId){
		this.sbcId = sbcId;
	}
	public String getOpeningPrice(){
		return openingPrice;
	}
	public void setOpeningPrice(String openingPrice){
		this.openingPrice = openingPrice;
	}
	public String getClosePrice(){
		return closePrice;
	}
	public void setClosePrice(String closePrice){
		this.closePrice = closePrice;
	}
	public String getHighestPrice(){
		return highestPrice;
	}
	public void setHighestPrice(String highestPrice){
		this.highestPrice = highestPrice;
	}
	public String getLowestPrice(){
		return lowestPrice;
	}
	public void setLowestPrice(String lowestPrice){
		this.lowestPrice = lowestPrice;
	}
	public String getRiseValue(){
		return riseValue;
	}
	public void setRiseValue(String riseValue){
		this.riseValue = riseValue;
	}
	public String getDealVolume(){
		return dealVolume;
	}
	public void setDealVolume(String dealVolume){
		this.dealVolume = dealVolume;
	}
	public String getDealValue(){
		return dealValue;
	}
	public void setDealValue(String dealValue){
		this.dealValue = dealValue;
	}
	public String getRiseScope(){
		return riseScope;
	}
	public void setRiseScope(String riseScope){
		this.riseScope = riseScope;
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
		string += "sbcId:\t"+ this.getSbcId()+"\n";
		string += "开盘价:\t"+ this.getOpeningPrice()+"\n";
		string += "收盘价:\t"+ this.getClosePrice()+"\n";
		string += "最高价:\t"+ this.getHighestPrice()+"\n";
		string += "最低价:\t"+ this.getLowestPrice()+"\n";
		string += "涨跌:\t"+ this.getRiseValue()+"\n";
		string += "成交量:\t"+ this.getDealVolume()+"\n";
		string += "成交额:\t"+ this.getDealValue()+"\n";
		string += "涨幅:\t"+ this.getRiseScope()+"\n";
		string += "类型：月，周，日:\t"+ this.getType()+"\n";
		string += "时间:\t"+ this.getDate()+"\n";
		return string;
	}
	public void init(){
		this.setSbcId(null);
		this.setOpeningPrice(null);
		this.setClosePrice(null);
		this.setHighestPrice(null);
		this.setLowestPrice(null);
		this.setRiseValue(null);
		this.setDealVolume(null);
		this.setDealValue(null);
		this.setRiseScope(null);
		this.setType(null);
		this.setDate(null);
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
