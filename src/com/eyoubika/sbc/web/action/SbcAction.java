package com.eyoubika.sbc.web.action;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.PageInfo;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.sbc.service.ISbcService;
import com.eyoubika.sbc.web.VO.SbcQueryVO;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
 *==========================================================================================*/
public class SbcAction extends BaseAction {
	private ISbcService sbcService;	//<<attrNote>>
	private SbcQueryVO sbcQueryVO;	
	public ISbcService getSbcService(){
		return sbcService;
	}
	public void setSbcService(ISbcService sbcService){
		this.sbcService = sbcService;
	}
	
	public SbcQueryVO getSbcQueryVO() {
		return sbcQueryVO;
	}
	public void setSbcQueryVO(SbcQueryVO sbcQueryVO) {
		this.sbcQueryVO = sbcQueryVO;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public String addSbcBasic(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		sbcQueryVO.init();
		ConverterUtil.RequestToVO(request, sbcQueryVO);
		//> 3. do
		this.jsonData = sbcService.addSbcBasic(sbcQueryVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public String getSbcBasic(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		sbcQueryVO.init();
		ConverterUtil.RequestToVO(request, sbcQueryVO);
		//> 3. do
		this.jsonData = sbcService.getSbcBasic(sbcQueryVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public String getSbcBasicList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		sbcQueryVO.init();
		PageInfo pageInfo= new PageInfo();	
		pageInfo.assign(request);
		ConverterUtil.RequestToVO(request, sbcQueryVO);
		//> 3. do
		this.jsonData = sbcService.getSbcBasicList(sbcQueryVO, pageInfo);
		pageInfo = null;
		return SUCCESS;
	}

	//>. <CustomFileTag>
	public String fuzzyQuerySbcs(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId") && null==request.getParameter("sbcName") ){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		sbcQueryVO.init();
		ConverterUtil.RequestToVO(request, sbcQueryVO);
		//> 3. do
		this.jsonData = sbcService.fuzzyQuerySbcBasics(sbcQueryVO);
		return SUCCESS;
	}
	
	//>. <CustomFileTag>
}
