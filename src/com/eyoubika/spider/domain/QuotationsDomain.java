package com.eyoubika.spider.domain;
public class QuotationsDomain{
	Integer sbcId;	//品种id
	String sbcName;	//名称
	String openingPrice;	//开盘价
	String yesterClosePrice;	//昨日收盘价
	String highestPrice;	//最高价
	String lowestPrice;	//最低价
	String currentPrice;	//最新价
	String averagePrice;	//均价
	String currentGains;	//现量
	String totalVolum;	//总量
	String totalValue;	//总额
	String riseValue;	//涨跌
	String riseScope;	//幅度
	String commitProportion;	//委比
	String quantProportion;	//量比
	String turnoverRate;	//换手率
	String recordTime;	//记录时间
	public Integer getSbcId(){
		return sbcId;
	}
	public void setSbcId(Integer sbcId){
		this.sbcId = sbcId;
	}
	public String getSbcName(){
		return sbcName;
	}
	public void setSbcName(String sbcName){
		this.sbcName = sbcName;
	}
	
	public String getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(String averagePrice) {
		this.averagePrice = averagePrice;
	}
	public String getOpeningPrice(){
		return openingPrice;
	}
	public void setOpeningPrice(String openingPrice){
		this.openingPrice = openingPrice;
	}
	public String getYesterClosePrice(){
		return yesterClosePrice;
	}
	public void setYesterClosePrice(String yesterClosePrice){
		this.yesterClosePrice = yesterClosePrice;
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
	public String getCurrentPrice(){
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice){
		this.currentPrice = currentPrice;
	}
	public String getCurrentGains(){
		return currentGains;
	}
	public void setCurrentGains(String currentGains){
		this.currentGains = currentGains;
	}
	public String getTotalVolum(){
		return totalVolum;
	}
	public void setTotalVolum(String totalVolum){
		this.totalVolum = totalVolum;
	}
	public String getTotalValue(){
		return totalValue;
	}
	public void setTotalValue(String totalValue){
		this.totalValue = totalValue;
	}
	public String getRiseValue(){
		return riseValue;
	}
	public void setRiseValue(String riseValue){
		this.riseValue = riseValue;
	}
	public String getRiseScope(){
		return riseScope;
	}
	public void setRiseScope(String riseScope){
		this.riseScope = riseScope;
	}
	public String getCommitProportion(){
		return commitProportion;
	}
	public void setCommitProportion(String commitProportion){
		this.commitProportion = commitProportion;
	}
	public String getQuantProportion(){
		return quantProportion;
	}
	public void setQuantProportion(String quantProportion){
		this.quantProportion = quantProportion;
	}
	public String getTurnoverRate(){
		return turnoverRate;
	}
	public void setTurnoverRate(String turnoverRate){
		this.turnoverRate = turnoverRate;
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
		string += "名称:\t"+ this.getSbcName()+"\n";
		string += "开盘价:\t"+ this.getOpeningPrice()+"\n";
		string += "昨日收盘价:\t"+ this.getYesterClosePrice()+"\n";
		string += "最高价:\t"+ this.getHighestPrice()+"\n";
		string += "最低价:\t"+ this.getLowestPrice()+"\n";
		string += "最均价:\t"+ this.getAveragePrice()+"\n";
		string += "最新价:\t"+ this.getCurrentPrice()+"\n";
		string += "现量:\t"+ this.getCurrentGains()+"\n";
		string += "总量:\t"+ this.getTotalVolum()+"\n";
		string += "总额:\t"+ this.getTotalValue()+"\n";
		string += "涨跌:\t"+ this.getRiseValue()+"\n";
		string += "幅度:\t"+ this.getRiseScope()+"\n";
		string += "委比:\t"+ this.getCommitProportion()+"\n";
		string += "量比:\t"+ this.getQuantProportion()+"\n";
		string += "换手率:\t"+ this.getTurnoverRate()+"\n";
		string += "记录时间:\t"+ this.getRecordTime()+"\n";
		return string;
	}
}