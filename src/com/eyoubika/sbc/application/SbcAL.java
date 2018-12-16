package com.eyoubika.sbc.application;
import java.util.List;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.PageInfo;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.common.YbkException;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.sbc.domain.SbcSimpleDomain;
import com.eyoubika.sbc.dao.SbcBasicDao;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class SbcAL{
	private SbcBasicDao sbcBasicDao;	//
	public SbcBasicDao getSbcBasicDao(){
		return sbcBasicDao;
	}
	public void setSbcBasicDao(SbcBasicDao sbcBasicDao){
		this.sbcBasicDao = sbcBasicDao;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public void getSbcBasic( ){
		//sbcBasicDao.findSbcBasicInPage(sbcId)
	}
	
	public List<SbcBasicDomain> getSbcBasicInPage(SbcBasicDomain sbcBasicDomain, PageInfo pageInfo){
		return sbcBasicDao.querySbcBasicInPage(sbcBasicDomain, pageInfo);
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public void getAllSbcs( ){
		
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-25 15:31:43
	 *--------------------------------------------------------------------------------------*/
	public void addSbcBasic(SbcBasicDomain sbcBasicDomain){
		
	}
	
	
	//>. <CustomFileTag>
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		fuzzyQueyBySbcCode
	 * Author:		1.0 created by lijiaxuan at 2015年8月25日 下午4:05:57
	 *--------------------------------------------------------------------------------------*/
	public List<SbcSimpleDomain> fuzzyQueryBySbcCode(String sbcCode){
		List<SbcSimpleDomain> resDomainList = sbcBasicDao.fuzzyQueryBySbcCode(sbcCode);
		return resDomainList;
	}
		
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		fuzzyQueryBySbcName
	 * Author:		1.0 created by lijiaxuan at 2015年8月25日 下午4:07:34
	 *--------------------------------------------------------------------------------------*/
	public List<SbcSimpleDomain> fuzzyQueryBySbcName(String sbcName){
		List<SbcSimpleDomain> resDomainList = sbcBasicDao.fuzzyQueryBySbcName(sbcName);
		return resDomainList;
	}
	//>. <CustomFileTag>
}
