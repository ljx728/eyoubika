package com.eyoubika.info.service;
import java.util.Map;

import com.eyoubika.info.web.VO.QuotationsVO;
import com.eyoubika.common.PageInfo;
/*==========================================================================================*
 * Description:	定义用户服务接口
 * Class:		INewsService
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-6-27 10:10:23
 *==========================================================================================*/
public interface IQuotationsService {	
	public Map<String, Object> getQuotationList(QuotationsVO quotationsVO);	
	public Map<String, Object> getExQuotations(QuotationsVO quotationsVO);	
	public Map<String, Object> getSbcQuotation(QuotationsVO quotationsVO);	
	//public Map<String, Object> getKChart(QuotationsVO quotationsVO);	
	//public Map<String, Object> getCChart(QuotationsVO quotationsVO);	
	//public Map<String, Object> getTChart(QuotationsVO quotationsVO);
	public Map<String, Object> getSbcQuotationMore(QuotationsVO quotationsVO);
	public Map<String, Object> getSbcQuotationDetail(QuotationsVO quotationsVO);
	public Map<String, Object> getSbcQuotationRefresh(QuotationsVO quotationsVO);
	public Map<String, Object> getBillboard(QuotationsVO quotationsVO);
	
	
}