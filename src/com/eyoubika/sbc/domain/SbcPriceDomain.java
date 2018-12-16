package com.eyoubika.sbc.domain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class SbcPriceDomain{
	private Integer sbcId;	//品种id
	private String sbcCode;	//品种编号
	private String sbcName;	//名称
	private String exId;	//交易所ID
	private Double openingPrice;	//开盘价
	private Double closingPrice;	//收盘价
	private Double highestPrice;	//最高价
	private Double lowestPrice;	//最低价
	private Double averagePrice;	//平均价
	private Double currentPrice;	//最新价
	private Integer dealVolume;	//成交量
	private Integer poq;	//订货量
	private Double riseValue;	//涨跌
	private Integer totalValue;	//市值
	private String recordDate;	//记录日期
	public Integer getSbcId(){
		return sbcId;
	}
	public void setSbcId(Integer sbcId){
		this.sbcId = sbcId;
	}
	public String getSbcCode(){
		return sbcCode;
	}
	public void setSbcCode(String sbcCode){
		this.sbcCode = sbcCode;
	}
	public String getSbcName(){
		return sbcName;
	}
	public void setSbcName(String sbcName){
		this.sbcName = sbcName;
	}
	public String getExId(){
		return exId;
	}
	public void setExId(String exId){
		this.exId = exId;
	}
	public Double getOpeningPrice(){
		return openingPrice;
	}
	public void setOpeningPrice(Double openingPrice){
		this.openingPrice = openingPrice;
	}
	public Double getClosingPrice(){
		return closingPrice;
	}
	public void setClosingPrice(Double closingPrice){
		this.closingPrice = closingPrice;
	}
	public Double getHighestPrice(){
		return highestPrice;
	}
	public void setHighestPrice(Double highestPrice){
		this.highestPrice = highestPrice;
	}
	public Double getLowestPrice(){
		return lowestPrice;
	}
	public void setLowestPrice(Double lowestPrice){
		this.lowestPrice = lowestPrice;
	}
	public Double getAveragePrice(){
		return averagePrice;
	}
	public void setAveragePrice(Double averagePrice){
		this.averagePrice = averagePrice;
	}
	public Double getCurrentPrice(){
		return currentPrice;
	}
	public void setCurrentPrice(Double currentPrice){
		this.currentPrice = currentPrice;
	}
	public Integer getDealVolume(){
		return dealVolume;
	}
	public void setDealVolume(Integer dealVolume){
		this.dealVolume = dealVolume;
	}
	public Integer getPoq(){
		return poq;
	}
	public void setPoq(Integer poq){
		this.poq = poq;
	}
	public Double getRiseValue(){
		return riseValue;
	}
	public void setRiseValue(Double riseValue){
		this.riseValue = riseValue;
	}
	public Integer getTotalValue(){
		return totalValue;
	}
	public void setTotalValue(Integer totalValue){
		this.totalValue = totalValue;
	}
	public String getRecordDate(){
		return recordDate;
	}
	public void setRecordDate(String recordDate){
		this.recordDate = recordDate;
	}
	public String toString() {
		String string = "";
		string += "品种id:\t"+ this.getSbcId()+"\n";
		string += "品种编号:\t"+ this.getSbcCode()+"\n";
		string += "名称:\t"+ this.getSbcName()+"\n";
		string += "交易所ID:\t"+ this.getExId()+"\n";
		string += "开盘价:\t"+ this.getOpeningPrice()+"\n";
		string += "收盘价:\t"+ this.getClosingPrice()+"\n";
		string += "最高价:\t"+ this.getHighestPrice()+"\n";
		string += "最低价:\t"+ this.getLowestPrice()+"\n";
		string += "平均价:\t"+ this.getAveragePrice()+"\n";
		string += "最新价:\t"+ this.getCurrentPrice()+"\n";
		string += "成交量:\t"+ this.getDealVolume()+"\n";
		string += "订货量:\t"+ this.getPoq()+"\n";
		string += "涨跌:\t"+ this.getRiseValue()+"\n";
		string += "市值:\t"+ this.getTotalValue()+"\n";
		string += "记录日期:\t"+ this.getRecordDate()+"\n";
		return string;
	}
	public void init(){
		this.setSbcId(null);
		this.setSbcCode(null);
		this.setSbcName(null);
		this.setExId(null);
		this.setOpeningPrice(null);
		this.setClosingPrice(null);
		this.setHighestPrice(null);
		this.setLowestPrice(null);
		this.setAveragePrice(null);
		this.setCurrentPrice(null);
		this.setDealVolume(null);
		this.setPoq(null);
		this.setRiseValue(null);
		this.setTotalValue(null);
		this.setRecordDate(null);
	}
	//>. <CustomFileTag>
//>. <CustomFileTag>
}
