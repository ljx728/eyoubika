package com.eyoubika.batch;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.batch.application.QuotationsBatchAL;
import com.eyoubika.batch.application.RedisIniterBatchAL;
import com.eyoubika.batch.application.SbcBatchAL;
import com.eyoubika.common.BaseAction;
import com.eyoubika.sbc.application.TradeCalendarAL;
import com.eyoubika.spider.application.QuotationsHistFetcherAL;
import com.eyoubika.spider.application.QuotationsIniterAL;
import com.eyoubika.spider.application.SbcVolumeSpiderAL;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.spider.application.QuotationsFetcherAL;

/*==========================================================================================*
 * Description:	执行批量。日终：是指次日凌晨1点。注意时间顺序。
 * Class:		QuotationsBatch
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年9月17日 下午3:03:58
 *==========================================================================================*/
public class QuotationsBatch extends BaseAction{	
	private static final long serialVersionUID = 1L;
	private QuotationsFetcherAL quotationsFetcherAL;
	private QuotationsIniterAL quotationsIniterAL;
	private SbcVolumeSpiderAL sbcVolumeSpiderAL;
	private QuotationsHistFetcherAL quotationsHistFetcherAL;
	private TradeCalendarAL tradeCalendarAL;
	
	private QuotationsBatchAL quotationsBatchAL;
	private RedisIniterBatchAL redisIniterBatchAL;
	private SbcBatchAL sbcBatchAL;
	
	public QuotationsBatchAL getQuotationsBatchAL() {
		return quotationsBatchAL;
	}
	public void setQuotationsBatchAL(QuotationsBatchAL quotationsBatchAL) {
		this.quotationsBatchAL = quotationsBatchAL;
	}
	public RedisIniterBatchAL getRedisIniterBatchAL() {
		return redisIniterBatchAL;
	}
	public void setRedisIniterBatchAL(RedisIniterBatchAL redisIniterBatchAL) {
		this.redisIniterBatchAL = redisIniterBatchAL;
	}
	public SbcBatchAL getSbcBatchAL() {
		return sbcBatchAL;
	}
	public void setSbcBatchAL(SbcBatchAL sbcBatchAL) {
		this.sbcBatchAL = sbcBatchAL;
	}
	public TradeCalendarAL getTradeCalendarAL() {
		return tradeCalendarAL;
	}
	public void setTradeCalendarAL(TradeCalendarAL tradeCalendarAL) {
		this.tradeCalendarAL = tradeCalendarAL;
	}
	public QuotationsHistFetcherAL getQuotationsHistFetcherAL() {
		return quotationsHistFetcherAL;
	}
	public void setQuotationsHistFetcherAL(
			QuotationsHistFetcherAL quotationsHistFetcherAL) {
		this.quotationsHistFetcherAL = quotationsHistFetcherAL;
	}
	public SbcVolumeSpiderAL getSbcVolumeSpiderAL() {
		return sbcVolumeSpiderAL;
	}
	public void setSbcVolumeSpiderAL(SbcVolumeSpiderAL sbcVolumeSpiderAL) {
		this.sbcVolumeSpiderAL = sbcVolumeSpiderAL;
	}
	public QuotationsFetcherAL getQuotationsFetcherAL() {
		return quotationsFetcherAL;
	}
	public void setQuotationsFetcherAL(QuotationsFetcherAL quotationsFetcherAL) {
		this.quotationsFetcherAL = quotationsFetcherAL;
	}
		
	public QuotationsIniterAL getQuotationsIniterAL() {
		return quotationsIniterAL;
	}
	public void setQuotationsIniterAL(QuotationsIniterAL quotationsIniterAL) {
		this.quotationsIniterAL = quotationsIniterAL;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	初始化sbc内存数据库信息。日终执行。
	 * Method:		initQuotRedis
	 * Author:		1.0 created by lijiaxuan at 2015年9月17日 下午2:57:17
	 *--------------------------------------------------------------------------------------*/
	public void initSbcRedis(){
		quotationsIniterAL.initSbcRedis();		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	初始化sbc数据库信息。日终执行。
	 * Method:		initSbcs
	 * Author:		1.0 created by lijiaxuan at 2015年10月20日 下午4:22:50
	 *--------------------------------------------------------------------------------------*/
	public void initSbcs(){
		quotationsIniterAL.initSbcs();		
	}
		
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取行情信息。交易时间开始时执行。
	 * Method:		fetchQuotations
	 * Author:		1.0 created by lijiaxuan at 2015年9月17日 下午2:57:15
	 *--------------------------------------------------------------------------------------*/
	public void fetchQuotations(){
		quotationsFetcherAL.fetchQuotations();
	}	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	初始化品种库存。日终执行。
	 * Method:		inputSbcVolume
	 * Author:		1.0 created by lijiaxuan at 2015年11月1日 下午4:42:03
	 *--------------------------------------------------------------------------------------*/
	public void initSbcVolume(){
		redisIniterBatchAL.initSbcVolume();		
	}	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	抓取品种库存。日终执行。
	 * Method:		fetchSbcVolume
	 * Author:		1.0 created by lijiaxuan at 2015年11月1日 下午4:32:43
	 *--------------------------------------------------------------------------------------*/
	public void fetchSbcVolume(){	
		sbcVolumeSpiderAL.fetchSbcVolume();
	}	
	public void fetchSbcVolumeFromFile(){	
		sbcVolumeSpiderAL.fetchSbcVolumeFromFile();
	}	
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	录入每日行情到数据库：K线图数据。日终执行
	 * Method:		calSbcKQuotation
	 * Author:		1.0 created by lijiaxuan at 2015年9月17日 下午2:57:32
	 *--------------------------------------------------------------------------------------*/
	public void calSbcKQuotation(){
		sbcBatchAL.calSbcKQuotation();	
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	生成每个品种的五分钟涨幅。交易时间执行。
	 * Method:		inputFiveMinRise
	 * Author:		1.0 created by lijiaxuan at 2015年11月09日 下午4:43:57
	 *--------------------------------------------------------------------------------------*/
	public void inputFiveMinRise(){
		quotationsBatchAL.inputFiveMinRise();	
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	生成每个品种的三日涨幅。交易时间执行。
	 * Method:		inputThreeDayRise
	 * Author:		1.0 created by lijiaxuan at 2015年11月09日 下午4:44:34
	 *--------------------------------------------------------------------------------------*/
	public void inputThreeDayRise(){
		quotationsBatchAL.inputThreeDayRise();	
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	计算每个交易所的市值。交易时间执行
	 * Method:		inputMarketValue
	 * Author:		1.0 created by lijiaxuan at 2015年11月12日 下午4:45:07
	 *--------------------------------------------------------------------------------------*/
	public void inputMarketValue(){
		quotationsBatchAL.inputMarketValue();	
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	删除K线图数据。不执行。
	 * Method:		deleteSbcQuotationDetail
	 * Author:		1.0 created by lijiaxuan at 2015年10月20日 下午4:23:40
	 *--------------------------------------------------------------------------------------*/
	public void deleteSbcQuotationDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String date = CommonUtil.getNowDate();	
		String type = "00";
		//> 1.check params
		if (null != request.getParameter("date") ) {
			date = request.getParameter("date");
		}
		if (null != request.getParameter("type")) {
			type = request.getParameter("type");
		}
		if (type.equals("00")){
			sbcBatchAL.deleteSbcQuotationDetail( date, "06");	
			sbcBatchAL.deleteSbcQuotationDetail( date, "07");	
			sbcBatchAL.deleteSbcQuotationDetail( date, "08");	
		} else {
			sbcBatchAL.deleteSbcQuotationDetail( date, type);	
		} 
	}
	

	/*--------------------------------------------------------------------------------------*
	 * Description:	从第三方抓取K线图历史数据。初始化执行。
	 * Method:		fetchQuotationsHist
	 * Author:		1.0 created by lijiaxuan at 2015年11月17日 下午4:53:16
	 *--------------------------------------------------------------------------------------*/
	public void fetchQuotationsHist(){
		quotationsHistFetcherAL.fetchQuotationsHist();		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	生成每个品种的当日波动信息。交易时间执行。
	 * Method:		inputVolatility
	 * Author:		1.0 created by lijiaxuan at 2015年10月21日 下午4:12:33
	 *--------------------------------------------------------------------------------------*/
	public void inputVolatility(){
		quotationsBatchAL.inputVolatility();		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	生成每个品种的换手率信息。交易时间执行。
	 * Method:		inputTurnoverRate
	 * Author:		1.0 created by lijiaxuan at 2015年10月28日 下午4:48:03
	 *--------------------------------------------------------------------------------------*/
	public void inputTurnoverRate(){
		quotationsBatchAL.inputTurnoverRate();		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		initTradeDay
	 * Author:		1.0 created by lijiaxuan at 2015年12月1日 下午4:45:37
	 *--------------------------------------------------------------------------------------*/
	public void initTradeDay(){
		tradeCalendarAL.initTradeDay();		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	清空内存数据库。仅保留qu:xxxxxx 。
	 * Method:		cleanRedis
	 * Author:		1.0 created by lijiaxuan at 2015年10月20日 下午5:05:05
	 *--------------------------------------------------------------------------------------*/
	public void cleanRedis(){
		redisIniterBatchAL.cleanRedis();		
	}	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		cleanRedisSbc
	 * Author:		1.0 created by lijiaxuan at 2015年12月1日 下午4:44:25
	 *--------------------------------------------------------------------------------------*/
	public void cleanRedisSbc(){
		redisIniterBatchAL.cleanRedisSbc();		
	}	
	/*--------------------------------------------------------------------------------------*
	 * Description:	清空分时信息。日终执行。
	 * Method:		cleanRedisTChart
	 * Author:		1.0 created by lijiaxuan at 2015年9月17日 下午2:57:37
	 *--------------------------------------------------------------------------------------*/
	public void cleanRedisTChart(){
		redisIniterBatchAL.cleanRedisTChart();		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	初始化品种三日前收盘价。日终执行。
	 * Method:		migrateThreeDayPrice
	 * Author:		1.0 created by lijiaxuan at 2015年11月12日 下午4:44:44
	 *--------------------------------------------------------------------------------------*/
	public void initThreeDayPrice(){
		redisIniterBatchAL.initThreeDayPrice();	
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		initSbcHistory
	 * Author:		1.0 created by lijiaxuan at 2015年12月1日 下午4:45:28
	 *--------------------------------------------------------------------------------------*/
	public void initSbcHistory(){
		redisIniterBatchAL.initSbcHistory();		
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		calSbcQuotationDayDetail
	 * Author:		1.0 created by lijiaxuan at 2015年12月1日 下午6:55:22
	 *--------------------------------------------------------------------------------------*/
	public void calSbcQuotationDayDetail(){
		sbcBatchAL.calSbcQuotationDayDetail(request.getParameter("exId"));	
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		calSbcQuotationWeekDetail
	 * Author:		1.0 created by lijiaxuan at 2015年12月1日 下午6:55:20
	 *--------------------------------------------------------------------------------------*/
	/*public void calSbcQuotationWeekDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();	
		sbcBatchAL.calSbcQuotationWeekDetail(request.getParameter("exId"), request.getParameter("date"));	
	}*/
	public void calAllWeekDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();	
		sbcBatchAL.calAllWeekDetail(request.getParameter("exId"), request.getParameter("date"));	
	}
	public void calSbcWeekData(){
		HttpServletRequest request = ServletActionContext.getRequest();	
		sbcBatchAL.calSbcWeekData(request.getParameter("exId"),request.getParameter("sbcId"), request.getParameter("date"));	
	}
	public void calAllMonthDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();	
		sbcBatchAL.calAllMonthDetail(request.getParameter("exId"), request.getParameter("date"));	
	}
	public void calSbcMonthData(){
		HttpServletRequest request = ServletActionContext.getRequest();	
		sbcBatchAL.calSbcMonthData(request.getParameter("exId"),request.getParameter("sbcId"), request.getParameter("date"));	
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		calSbcQuotationMonthDetail
	 * Author:		1.0 created by lijiaxuan at 2015年12月1日 下午6:55:25
	 *--------------------------------------------------------------------------------------*/
	/*public void calSbcQuotationMonthDetail(){
		sbcBatchAL.calSbcQuotationMonthDetail(request.getParameter("exId"), request.getParameter("date"));	
	}*/
}