package com.eyoubika.sbc.web.action;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.eyoubika.common.BaseAction;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.sbc.service.ITradeCalendarService;
import com.eyoubika.sbc.web.VO.TradeCalendarVO;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
 *==========================================================================================*/
public class TradeCalendarAction extends BaseAction {
	private ITradeCalendarService tradeCalendarService;	//<<attrNote>>
	private TradeCalendarVO tradeCalendarVO;	//<<attrNote>>
	public ITradeCalendarService getTradeCalendarService(){
		return tradeCalendarService;
	}
	public void setTradeCalendarService(ITradeCalendarService tradeCalendarService){
		this.tradeCalendarService = tradeCalendarService;
	}
	public TradeCalendarVO getTradeCalendarVO(){
		return tradeCalendarVO;
	}
	public void setTradeCalendarVO(TradeCalendarVO tradeCalendarVO){
		this.tradeCalendarVO = tradeCalendarVO;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public String addTradeDay(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		tradeCalendarVO.init();
		ConverterUtil.RequestToVO(request, tradeCalendarVO);
		//> 3. do
		this.jsonData = tradeCalendarService.addTradeDay(tradeCalendarVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public String getTradeDay(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		tradeCalendarVO.init();
		ConverterUtil.RequestToVO(request, tradeCalendarVO);
		//> 3. do
		this.jsonData = tradeCalendarService.getTradeDay(tradeCalendarVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public String deleteTradeDay(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		tradeCalendarVO.init();
		ConverterUtil.RequestToVO(request, tradeCalendarVO);
		//> 3. do
		this.jsonData = tradeCalendarService.deleteTradeDay(tradeCalendarVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-24 11:19:38
	 *--------------------------------------------------------------------------------------*/
	public String modifyTradeDay(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		tradeCalendarVO.init();
		ConverterUtil.RequestToVO(request, tradeCalendarVO);
		//> 3. do
		this.jsonData = tradeCalendarService.modifyTradeDay(tradeCalendarVO);
		return SUCCESS;
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
