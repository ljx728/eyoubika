package com.eyoubika.sbc.service;
import java.util.Map;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.BaseService;
import com.eyoubika.sbc.service.ITradeCalendarService;
import com.eyoubika.sbc.web.VO.TradeCalendarVO;
import com.eyoubika.sbc.application.TradeCalendarAL;
import com.eyoubika.sbc.domain.TradeCalendarDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public interface ITradeCalendarService{
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addTradeDay(TradeCalendarVO tradeCalendarVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getTradeDay(TradeCalendarVO tradeCalendarVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> deleteTradeDay(TradeCalendarVO tradeCalendarVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyTradeDay(TradeCalendarVO tradeCalendarVO);
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
