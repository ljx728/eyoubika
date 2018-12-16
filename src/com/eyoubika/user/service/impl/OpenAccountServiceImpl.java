package com.eyoubika.user.service.impl;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.user.service.IOpenAccountService;
import com.eyoubika.user.web.VO.OpenAccountVO;
import com.eyoubika.user.web.VO.QueryOpenAccountVO;
import com.eyoubika.user.application.OpenAccountAL;
import com.eyoubika.user.domain.OpenAccountPersDomain;
import com.eyoubika.user.domain.OpenAccountCorpDomain;
import com.eyoubika.user.domain.OpenAccountDetailDomain;
import com.eyoubika.user.domain.OpenAccountBriefDomain;
import com.eyoubika.common.PageInfo;
public class OpenAccountServiceImpl extends BaseService implements IOpenAccountService{
	private OpenAccountAL openAccountAL;	//
	private OpenAccountPersDomain openAccountPersDomain;	//
	private OpenAccountCorpDomain openAccountCorpDomain;	//
	private OpenAccountDetailDomain openAccountDetailDomain;	//
	private OpenAccountBriefDomain openAccountBriefDomain;	//
	
	public OpenAccountBriefDomain getOpenAccountBriefDomain() {
		return openAccountBriefDomain;
	}
	public void setOpenAccountBriefDomain(
			OpenAccountBriefDomain openAccountBriefDomain) {
		this.openAccountBriefDomain = openAccountBriefDomain;
	}
	public OpenAccountPersDomain getOpenAccountPersDomain(){
		return openAccountPersDomain;
	}
	public void setOpenAccountPersDomain(OpenAccountPersDomain openAccountPersDomain){
		this.openAccountPersDomain = openAccountPersDomain;
	}
	public OpenAccountCorpDomain getOpenAccountCorpDomain(){
		return openAccountCorpDomain;
	}
	public void setOpenAccountCorpDomain(OpenAccountCorpDomain openAccountCorpDomain){
		this.openAccountCorpDomain = openAccountCorpDomain;
	}
	public OpenAccountDetailDomain getOpenAccountDetailDomain(){
		return openAccountDetailDomain;
	}
	public void setOpenAccountDetailDomain(OpenAccountDetailDomain openAccountDetailDomain){
		this.openAccountDetailDomain = openAccountDetailDomain;
	}
	public OpenAccountAL getOpenAccountAL(){
		return openAccountAL;
	}
	public void setOpenAccountAL(OpenAccountAL openAccountAL){
		this.openAccountAL = openAccountAL;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addOpenAccount(OpenAccountVO openAccountVO){
		String openNo = "";	
		System.out.println(openAccountVO.toString());
		openAccountVO.setStatus(CommonVariables.OPEN_ACCOUNT_UNCHEKED);
		openAccountVO.setApplyTime(CommonUtil.getNow());
		if (openAccountVO.getOpenType().equals(CommonVariables.OPEN_PERS_ACCOUNT)){
			ConverterUtil.VOToDomain(openAccountVO, openAccountPersDomain);		
			openNo = openAccountAL.addPersAccount(openAccountPersDomain);
		} else if (openAccountVO.getOpenType().equals(CommonVariables.OPEN_CORP_ACCOUNT)){
			ConverterUtil.VOToDomain(openAccountVO, openAccountCorpDomain);
			openNo = openAccountAL.addCorpAccount(openAccountCorpDomain);
		}
		HashMap<String, String> map= new HashMap<String, String>();
		map.put("openNo", openNo);
		return this.buildRetData( openAccountVO.getUserId() , openAccountVO.getToken(), map);
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getOpenAccount(OpenAccountVO openAccountVO){
		if (openAccountVO.getOpenType().equals(CommonVariables.OPEN_PERS_ACCOUNT)){
			openAccountPersDomain = openAccountAL.getPersAccount(openAccountVO.getOpenNo());
			ConverterUtil.DomainToVO(openAccountPersDomain, openAccountVO);
		} else {
			openAccountCorpDomain = openAccountAL.getCorpAccount(openAccountVO.getOpenNo());
			ConverterUtil.DomainToVO(openAccountCorpDomain, openAccountVO);
		}
		//String images = openAccountAL.getOpenAccountIamges(openAccountVO.getOpenNo());
		//openAccountVO.set
		return this.buildRetData( openAccountVO.getUserId() , openAccountVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyOpenAccount(OpenAccountVO openAccountVO){
		System.out.println(openAccountVO.toString());
		openAccountVO.setOpenType(CommonVariables.OPEN_PERS_ACCOUNT);
		if (openAccountVO.getOpenType().equals(CommonVariables.OPEN_PERS_ACCOUNT)){
			ConverterUtil.VOToDomain(openAccountVO, openAccountPersDomain);
			openAccountAL.modifyPersAccount(openAccountPersDomain);
		} else if (openAccountVO.getOpenType().equals(CommonVariables.OPEN_CORP_ACCOUNT)){
			ConverterUtil.VOToDomain(openAccountVO, openAccountCorpDomain);
			openAccountAL.modifyCorpAccount(openAccountCorpDomain);
		}
		return this.buildRetData( openAccountVO.getUserId() , openAccountVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getOpenAccountList(OpenAccountVO openAccountVO, PageInfo pageInfo){
		ConverterUtil.VOToDomain(openAccountVO, openAccountBriefDomain);
		System.out.println("openAccountBriefDomain " + openAccountBriefDomain);
		/*if (openAccountVO.getOpenType().equals(CommonVariables.OPEN_PERS_ACCOUNT)){
			ConverterUtil.VOToDomain(openAccountVO, openAccountBriefDomain);
			//openAccountAL.getPersAccountList();
		} else if (openAccountVO.getOpenType().equals(CommonVariables.OPEN_CORP_ACCOUNT)){
			ConverterUtil.VOToDomain(openAccountVO, openAccountBriefDomain);
			//openAccountAL.getCorpAccountList();
		} */
		openAccountAL.getOpenAccountList(openAccountBriefDomain, pageInfo);
		return this.buildRetData( openAccountVO.getUserId() , openAccountVO.getToken());
	}
	/*public Map<String, Object> getOpenAccountList(OpenAccountVO openAccountVO, PageInfo pageInfo){
		if (openAccountVO.getOpenType().equals(CommonVariables.OPEN_PERS_ACCOUNT)){
			ConverterUtil.VOToDomain(openAccountVO, openAccountPersDomain);
			openAccountAL.getPersAccountByDomain(openAccountPersDomain);
		} else if (openAccountVO.getOpenType().equals(CommonVariables.OPEN_CORP_ACCOUNT)){
			ConverterUtil.VOToDomain(openAccountVO, openAccountCorpDomain);
			openAccountAL.getCorpAccountByDomain(openAccountCorpDomain);
		} 
		return this.buildRetData( openAccountVO.getUserId() , openAccountVO.getToken());
	}*/
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifyAccountDetail(OpenAccountVO openAccountVO){
		//ConverterUtil.VOToDomain(openAccountVO, openAccountDetailDomain);
		   openAccountAL.modifyAccountDetail(openAccountDetailDomain);
		return this.buildRetData( openAccountVO.getUserId() , openAccountVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> uploadAccountDetail(OpenAccountVO openAccountVO){
		//ConverterUtil.VOToDomain(openAccountVO, openAccountDetailDomain);
		   openAccountAL.uploadAccountDetail(openAccountDetailDomain);
		return this.buildRetData( openAccountVO.getUserId() , openAccountVO.getToken());
	}
	public Map<String, Object> uploadAccountImage(HttpServletRequest request){
		//ConverterUtil.VOToDomain(openAccountVO, openAccountDetailDomain);
		openAccountAL.uploadAccountImage(request);
		return this.buildRetData(CommonVariables.TRUE_SHORT_FLAG, CommonVariables.TRUE_WROD_FLAG);
	}
	
	public String  createReport(QueryOpenAccountVO queryOpenAccountVO){
		openAccountPersDomain.init();
		ConverterUtil.VOToDomain(queryOpenAccountVO, openAccountPersDomain);
		  return openAccountAL.createReport(openAccountPersDomain);
	}
	
	public InputStream  getAccountReport(QueryOpenAccountVO queryOpenAccountVO){
		String fileName = "OPAC" + queryOpenAccountVO.getApplyDate() + ".xls";
		 return openAccountAL.getAccountReport(fileName);
	}
	public String  createImageZip(QueryOpenAccountVO queryOpenAccountVO){
		openAccountPersDomain.init();
		ConverterUtil.VOToDomain(queryOpenAccountVO, openAccountPersDomain);
		  return openAccountAL.createImageZip(openAccountPersDomain);
	}
	
	public InputStream  getAccountZip(QueryOpenAccountVO queryOpenAccountVO){
		String fileName = "OPAC" + queryOpenAccountVO.getApplyDate() + ".zip";
		 return openAccountAL.getAccountZip(fileName);
	}
	
	
}
