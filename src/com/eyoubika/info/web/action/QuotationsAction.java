package com.eyoubika.info.web.action;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.info.web.VO.QuotationsVO;
import com.eyoubika.info.service.IQuotationsService;
import com.eyoubika.common.RedisDao;
/*==========================================================================================*
 * Description:	定义了用户控制器
 * Class:		UserAction
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-6-27 9:44:33
 *==========================================================================================*/
public class QuotationsAction extends BaseAction {
    private IQuotationsService quotationsService;
	private QuotationsVO quotationsVO;

	public QuotationsVO getQuotationsVO() {
		return quotationsVO;
	}
	public void setQuotationsVO(QuotationsVO quotationsVO) {
		this.quotationsVO = quotationsVO;
	}
	public IQuotationsService getQuotationsService() {
		return quotationsService;
	}
	public void setQuotationsService(IQuotationsService quotationsService) {
		this.quotationsService = quotationsService;
	}


	/*--------------------------------------------------------------------------------------*
	 * Description: 获取交易所下面的行情列表
	 * Method:		register
	 * Parameters:	void
	 * History:		1.0 created by lijiaxuan at 2015-5-21 14:22:16
	 *--------------------------------------------------------------------------------------*/
	public String getQuotationList(){	
		QuotationsVO quotationsVO = new QuotationsVO();
		HttpServletRequest request = ServletActionContext.getRequest();		
		//> 1.check params 
		if (null == request.getParameter("userId") ) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}			
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, quotationsVO);
		//> 3. do register
		this.jsonData = quotationsService.getQuotationList(quotationsVO);
		//> 4. return json
		quotationsVO = null;
		return SUCCESS;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	获取交易所行情概览
	 * Method:		getExQuotation
	 * Author:		1.0 created by lijiaxuan at 2015年7月31日 下午6:05:44
	 *--------------------------------------------------------------------------------------*/
	public String getExQuotations(){	
		QuotationsVO quotationsVO = new QuotationsVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params
		if (null == request.getParameter("userId") ) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}	
		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, quotationsVO);
		//> 3. do register
		this.jsonData = quotationsService.getExQuotations(quotationsVO);
		//> 4. return json
		quotationsVO = null;
		return SUCCESS;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	获取品种行情
	 * Method:		getSbcQuotation
	 * Author:		1.0 created by lijiaxuan at 2015年7月31日 下午6:05:44
	 *--------------------------------------------------------------------------------------*/
	public String getSbcQuotation(){	
		QuotationsVO quotationsVO = new QuotationsVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}	
		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, quotationsVO);
		//> 3. do register
		this.jsonData = quotationsService.getSbcQuotation(quotationsVO);
		//> 4. return json
		quotationsVO = null;
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getSbcQuotation
	 * Author:		1.0 created by lijiaxuan at 2015年8月27日 下午5:26:40
	 *--------------------------------------------------------------------------------------*/
	public String getSbcQuotationMore(){	
		QuotationsVO quotationsVO = new QuotationsVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}	
		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, quotationsVO);
		//> 3. do register
		this.jsonData = quotationsService.getSbcQuotationMore(quotationsVO);
		//> 4. return json
		quotationsVO = null;
		return SUCCESS;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getSbcQuotationDetail
	 * Author:		1.0 created by lijiaxuan at 2015年8月31日 下午3:30:44
	 *--------------------------------------------------------------------------------------*/
	public String getSbcQuotationDetail(){	
		QuotationsVO quotationsVO = new QuotationsVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId") && null == request.getParameter("interval")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}	
		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, quotationsVO);
		//> 3. do register
		this.jsonData = quotationsService.getSbcQuotationDetail(quotationsVO);
		//> 4. return json
		quotationsVO = null;
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getSbcQuotationRefresh
	 * Author:		1.0 created by lijiaxuan at 2015年9月12日 上午9:40:54
	 *--------------------------------------------------------------------------------------*/
	public String getSbcQuotationRefresh(){	
		QuotationsVO quotationsVO = new QuotationsVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId") && null == request.getParameter("interval")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}	
		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, quotationsVO);
		//> 3. do register
		this.jsonData = quotationsService.getSbcQuotationRefresh(quotationsVO);
		//> 4. return json
		quotationsVO = null;
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getBillboard
	 * Author:		1.0 created by lijiaxuan at 2015年10月14日 下午3:20:33
	 *--------------------------------------------------------------------------------------*/
	public String getBillboard(){	
		QuotationsVO quotationsVO = new QuotationsVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params
		if (null == request.getParameter("userId") && null == request.getParameter("type")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}	
		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, quotationsVO);
		//> 3. do register
		this.jsonData = quotationsService.getBillboard(quotationsVO);
		//> 4. return json
		quotationsVO = null;
		return SUCCESS;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	获取K线图
	 * Method:		getKChart
	 * Author:		1.0 created by lijiaxuan at 2015年8月24日 下午6:15:50
	 *--------------------------------------------------------------------------------------
	public String getKChart(){	
		quotationsVO.init();
		HttpServletRequest request = ServletActionContext.getRequest();		
		//> 1.check
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId") && null ==  request.getParameter("interval")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}			
		//> 2. build
		ConverterUtil.RequestToVO(request, quotationsVO);
		//> 3. do
		this.jsonData = quotationsService.getKChart(quotationsVO);

		return SUCCESS;
	}
	
	--------------------------------------------------------------------------------------*
	 * Description:	获取分量图
	 * Method:		getComponent
	 * Author:		1.0 created by lijiaxuan at 2015年8月24日 下午6:16:59
	 *--------------------------------------------------------------------------------------
	public String getCChart(){	
		QuotationsVO quotationsVO = new QuotationsVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}	
		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, quotationsVO);
		//> 3. do register
		this.jsonData = quotationsService.getCChart(quotationsVO);
		//> 4. return json
		quotationsVO = null;
		return SUCCESS;
	}
	
	--------------------------------------------------------------------------------------*
	 * Description:	获取分量图
	 * Method:		getComponent
	 * Author:		1.0 created by lijiaxuan at 2015年8月24日 下午6:16:59
	 *--------------------------------------------------------------------------------------
	public String getTChart(){	
		QuotationsVO quotationsVO = new QuotationsVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}	
		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, quotationsVO);
		//> 3. do register
		this.jsonData = quotationsService.getTChart(quotationsVO);
		//> 4. return json
		quotationsVO = null;
		return SUCCESS;
	}*/
	
}