package com.eyoubika.sbc.service.impl;
import java.util.List;
import java.util.Map;

import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.PageInfo;
import com.eyoubika.sbc.service.ISbcService;
import com.eyoubika.sbc.web.VO.SbcQueryVO;
import com.eyoubika.sbc.application.SbcAL;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.sbc.domain.SbcSimpleDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class SbcServiceImpl extends BaseService implements ISbcService{
	private SbcAL sbcAL;	//
	private SbcBasicDomain sbcBasicDomain;	//
	private SbcSimpleDomain sbcSimpleDomain;	//
	public SbcBasicDomain getSbcBasicDomain(){
		return sbcBasicDomain;
	}
	public void setSbcBasicDomain(SbcBasicDomain sbcBasicDomain){
		this.sbcBasicDomain = sbcBasicDomain;
	}
	public SbcAL getSbcAL(){
		return sbcAL;
	}
	public void setSbcAL(SbcAL sbcAL){
		this.sbcAL = sbcAL;
	}
	
	public SbcSimpleDomain getSbcSimpleDomain() {
		return sbcSimpleDomain;
	}
	public void setSbcSimpleDomain(SbcSimpleDomain sbcSimpleDomain) {
		this.sbcSimpleDomain = sbcSimpleDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addSbcBasic(SbcQueryVO sbcQueryVO){
		sbcBasicDomain.init();
		ConverterUtil.VOToDomain(sbcQueryVO, sbcBasicDomain);
		sbcAL.addSbcBasic(sbcBasicDomain);
		return this.buildRetData( sbcQueryVO.getUserId() , sbcQueryVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getSbcBasic(SbcQueryVO sbcQueryVO){
		//.init();
		//ConverterUtil.VOToDomain(sbcQueryVO, );
		   sbcAL.getSbcBasic();
		return this.buildRetData( sbcQueryVO.getUserId() , sbcQueryVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getSbcBasicList(SbcQueryVO sbcQueryVO, PageInfo pageInfo){
		sbcBasicDomain.init();
		ConverterUtil.VOToDomain(sbcBasicDomain, sbcQueryVO);
		List<SbcBasicDomain> sbcBasicDomainList = sbcAL.getSbcBasicInPage(sbcBasicDomain, pageInfo);
		   
		return this.buildRetData( sbcQueryVO.getUserId() , sbcQueryVO.getToken(), sbcBasicDomainList);
	}

	
	//>. <CustomFileTag>
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	模糊查询品种，目前支持品种编码和名称，拼音暂不支持
	 * Method:		fuzzyQuerySbcBasics
	 * Author:		1.0 created by lijiaxuan at 2015年8月25日 下午4:20:07
	 *--------------------------------------------------------------------------------------*/
	public  Map<String, Object> fuzzyQuerySbcBasics(SbcQueryVO sbcQueryVO){
		List<SbcSimpleDomain> resDomainList = null;
		if (CommonUtil.isStringDigit(sbcQueryVO.getSbcName())){	//其实用户输入的是sbc编码
			resDomainList = sbcAL.fuzzyQueryBySbcCode(sbcQueryVO.getSbcName());
		} else {
			resDomainList = sbcAL.fuzzyQueryBySbcName(sbcQueryVO.getSbcName());
		}	
		return this.buildRetData( sbcQueryVO.getUserId() , sbcQueryVO.getToken(), resDomainList);
	}
	//>. <CustomFileTag>
}
