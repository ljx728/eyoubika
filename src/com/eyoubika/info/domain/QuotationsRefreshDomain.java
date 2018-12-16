package com.eyoubika.info.domain;
public class QuotationsRefreshDomain{
	private Integer sbcId;	//品种ID
	private String currentPrice;	//现价
	private String averagePrice;	//平均价
	private String riseScope;	//涨幅
	private String dealVolume;	//成交量
	private String dealValue;	//成交额
	private String highestPrice;	//最高价
	private String lowestPrice;	//最低价
	private String dealVolumeChange;	//成交量变化值
	private String dealValueChange;	//成交额变化值
	private String recordTime;	//记录时间
	
	public Integer getSbcId() {
		return sbcId;
	}

	public void setSbcId(Integer sbcId) {
		this.sbcId = sbcId;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(String averagePrice) {
		this.averagePrice = averagePrice;
	}

	public String getRiseScope() {
		return riseScope;
	}

	public void setRiseScope(String riseScope) {
		this.riseScope = riseScope;
	}

	public String getDealVolume() {
		return dealVolume;
	}

	public void setDealVolume(String dealVolume) {
		this.dealVolume = dealVolume;
	}

	public String getDealValue() {
		return dealValue;
	}

	public void setDealValue(String dealValue) {
		this.dealValue = dealValue;
	}

	public String getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(String highestPrice) {
		this.highestPrice = highestPrice;
	}

	public String getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(String lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public String getDealVolumeChange() {
		return dealVolumeChange;
	}

	public void setDealVolumeChange(String dealVolumeChange) {
		this.dealVolumeChange = dealVolumeChange;
	}

	public String getDealValueChange() {
		return dealValueChange;
	}

	public void setDealValueChange(String dealValueChange) {
		this.dealValueChange = dealValueChange;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public void init(){
		this.setSbcId(null);
		this.setDealValue(null);
		this.setCurrentPrice(null);
		this.setHighestPrice(null);
		this.setLowestPrice(null);
		this.setAveragePrice(null);
		this.setRiseScope(null);
		this.setDealVolume(null);
		this.setDealVolumeChange(null);
		this.setDealValueChange(null);
		this.setRecordTime(null);
	}
}
