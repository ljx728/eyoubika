package com.eyoubika.sbc.service.impl;
import java.util.Map;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.BaseService;
import com.eyoubika.sbc.service.ITradeCalendarService;
import com.eyoubika.sbc.web.VO.TradeCalendarVO;
import com.eyoubika.sbc.application.TradeCalendarAL;
import com.eyoubika.sbc.domain.TradeCalendarDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class TradeCalendarServiceImpl extends BaseService implements ITradeCalendarService{
	private TradeCalendarAL tradeCalendarAL;	//
	private TradeCalendarDomain tradeCalendarDomain;	//
	public TradeCalendarDomain getTradeCalendarDomain(){
		return tradeCalendarDomain;
	}
	public void setTradeCalendarDomain(TradeCalendarDomain tradeCalendarDomain){
		this.tradeCalendarDomain = tradeCalendarDomain;
	}
	public TradeCalendarAL getTradeCalendarAL(){
		return tradeCalendarAL;
	}
	public void setTradeCalendarAL(TradeCalendarAL tradeCalendarAL){
		this.tradeCalendarAL = tradeCalendarAL;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addTradeDay(TradeCalendarVO tradeCalendarVO){
		//.init();
//ConverterUtil.VOToDomain(tradeCalendarVO, );
		   tradeCalendarAL.addTradeDay();
		return this.buildRetData( tradeCalendarVO.getUserId() , tradeCalendarVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getTradeDay(TradeCalendarVO tradeCalendarVO){
		//.init();
//ConverterUtil.VOToDomain(tradeCalendarVO, );
		TradeCalendarDomain tradeCalendarDomain = tradeCalendarAL.getTradeDay(tradeCalendarVO.getDate());
		return this.buildRetData( tradeCalendarVO.getUserId() , tradeCalendarVO.getToken(), tradeCalendarDomain);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> deleteTradeDay(TradeCalendarVO tradeCalendarVO){
		//tradeCalendarDomain.init();
//ConverterUtil.VOToDomain(tradeCalendarVO, tradeCalendarDomain);
		   tradeCalendarAL.deleteTradeDay(tradeCalendarDomain);
		return this.buildRetData( tradeCalendarVO.getUserId() , tradeCalendarVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyTradeDay(TradeCalendarVO tradeCalendarVO){
		//tradeCalendarDomain.init();
//ConverterUtil.VOToDomain(tradeCalendarVO, tradeCalendarDomain);
		   tradeCalendarAL.modifyTradeDay(tradeCalendarDomain);
		return this.buildRetData( tradeCalendarVO.getUserId() , tradeCalendarVO.getToken());
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
