package com.eyoubika.sbc.service;
import java.util.Map;

import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.PageInfo;
import com.eyoubika.sbc.service.ISbcService;
import com.eyoubika.sbc.web.VO.SbcQueryVO;
import com.eyoubika.sbc.application.SbcAL;
import com.eyoubika.sbc.domain.SbcBasicDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public interface ISbcService{
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addSbcBasic(SbcQueryVO sbcQueryVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getSbcBasic(SbcQueryVO sbcQueryVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getSbcBasicList(SbcQueryVO sbcQueryVO, PageInfo pageInfo);

	//>. <CustomFileTag>
	public Map<String, Object> fuzzyQuerySbcBasics(SbcQueryVO sbcQueryVO);
	
	//>. <CustomFileTag>
}
