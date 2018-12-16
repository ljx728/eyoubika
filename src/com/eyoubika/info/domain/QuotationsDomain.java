package com.eyoubika.info.domain;
public class QuotationsDomain{
	private Integer sbcId;	//品种id
	private String sbcCode;	//品种编号
	private String sbcName;	//名称
	private String exId;	// 交易所编号
	private String yesterClPrice;	//昨日收盘价
	private String openingPrice;	//开盘价
	private String currentPrice;	//最新价
	private String highestPrice;	//最高价
	private String lowestPrice;	//最低价
	private String averagePrice;	//平均价
	//private String yesterAvPrice;	//昨日平均价
	private String riseValue;	//涨跌
	private String riseScope;	//涨幅
	private String dealVolume;	//成交量
	private String dealValue;	//成交额
	private String turnoverRate;	//换手率
	private String marketValue;	//市值
	private String recordTime;	//记录时间
	
	
	public String getTurnoverRate() {
		return turnoverRate;
	}
	public void setTurnoverRate(String turnoverRate) {
		this.turnoverRate = turnoverRate;
	}
	public String getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}
	public String getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(String averagePrice) {
		this.averagePrice = averagePrice;
	}
	public String getDealValue() {
		return dealValue;
	}
	public void setDealValue(String dealValue) {
		this.dealValue = dealValue;
	}
	public String getRiseScope() {
		return riseScope;
	}
	public void setRiseScope(String riseScope) {
		this.riseScope = riseScope;
	}
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
	public String getYesterClPrice(){
		return yesterClPrice;
	}
	public void setYesterClPrice(String yesterClPrice){
		this.yesterClPrice = yesterClPrice;
	}
	public String getOpeningPrice(){
		return openingPrice;
	}
	public void setOpeningPrice(String openingPrice){
		this.openingPrice = openingPrice;
	}
	public String getCurrentPrice(){
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice){
		this.currentPrice = currentPrice;
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
	
	public String getRecordTime(){
		return recordTime;
	}
	public void setRecordTime(String recordTime){
		this.recordTime = recordTime;
	}
	public String toString() {
		String string = "";
		string += "品种id:\t"+ this.getSbcId()+"\n";
		string += "品种编号:\t"+ this.getSbcCode()+"\n";
		string += "名称:\t"+ this.getSbcName()+"\n";
		string += " 交易所编号:\t"+ this.getExId()+"\n";
		string += "昨日收盘价:\t"+ this.getYesterClPrice()+"\n";
		string += "开盘价:\t"+ this.getOpeningPrice()+"\n";
		string += "均价:\t"+ this.getAveragePrice()+"\n";
		string += "最新价:\t"+ this.getCurrentPrice()+"\n";
		string += "最高价:\t"+ this.getHighestPrice()+"\n";
		string += "最低价:\t"+ this.getLowestPrice()+"\n";
		string += "涨跌:\t"+ this.getRiseValue()+"\n";
		string += "成交量:\t"+ this.getDealVolume()+"\n";
		string += "成交额:\t"+ this.getDealValue()+"\n";
		string += "记录时间:\t"+ this.getRecordTime()+"\n";
		return string;
	}
	public void init(){
		this.setSbcId(null);
		this.setSbcCode(null);
		this.setRiseScope(null);
		this.setSbcName(null);
		this.setExId(null);
		this.setYesterClPrice(null);
		this.setAveragePrice(null);
		this.setOpeningPrice(null);
		this.setCurrentPrice(null);
		this.setHighestPrice(null);
		this.setLowestPrice(null);
		this.setRiseValue(null);
		this.setDealVolume(null);
		this.setRecordTime(null);
	}
}
