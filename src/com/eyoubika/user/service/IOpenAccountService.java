package com.eyoubika.user.service;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eyoubika.user.service.IOpenAccountService;
import com.eyoubika.user.web.VO.OpenAccountVO;
import com.eyoubika.user.web.VO.QueryOpenAccountVO;
import com.eyoubika.common.PageInfo;
public interface IOpenAccountService{
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addOpenAccount(OpenAccountVO openAccountVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getOpenAccount(OpenAccountVO openAccountVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyOpenAccount(OpenAccountVO openAccountVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getOpenAccountList(OpenAccountVO openAccountVO, PageInfo pageInfo);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyAccountDetail(OpenAccountVO openAccountVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> uploadAccountDetail(OpenAccountVO openAccountVO);
	public Map<String, Object>  uploadAccountImage(HttpServletRequest request);
	public InputStream  getAccountReport(QueryOpenAccountVO queryOpenAccountVO);
	public String  createReport(QueryOpenAccountVO queryOpenAccountVO);
	public InputStream  getAccountZip(QueryOpenAccountVO queryOpenAccountVO);
	public String  createImageZip(QueryOpenAccountVO queryOpenAccountVO);
	
	
}
