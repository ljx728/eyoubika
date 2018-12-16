package com.eyoubika.system.service.impl;
import java.util.Map;

import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.system.service.ISystemInfoService;
import com.eyoubika.system.web.VO.SystemInfoVO;
import com.eyoubika.system.application.SystemInfoAL;
import com.eyoubika.system.domain.SystemInfoDomain;
public class SystemInfoServiceImpl extends BaseService implements ISystemInfoService{
	private SystemInfoAL systemInfoAL;	//
	private SystemInfoDomain systemInfoDomain;	//
	public SystemInfoDomain getSystemInfoDomain(){
		return systemInfoDomain;
	}
	public void setSystemInfoDomain(SystemInfoDomain systemInfoDomain){
		this.systemInfoDomain = systemInfoDomain;
	}
	public SystemInfoAL getSystemInfoAL(){
		return systemInfoAL;
	}
	public void setSystemInfoAL(SystemInfoAL systemInfoAL){
		this.systemInfoAL = systemInfoAL;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addSystemInfo(SystemInfoVO systemInfoVO){
		systemInfoDomain.init();
		ConverterUtil.VOToDomain(systemInfoVO, systemInfoDomain);
		systemInfoAL.addSystemInfo(systemInfoDomain);
		return this.buildRetData( systemInfoVO.getUserId() , systemInfoVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getSystemInfo(SystemInfoVO systemInfoVO){
		//.init();
//ConverterUtil.VOToDomain(systemInfoVO, );
		systemInfoDomain =  systemInfoAL.getSystemInfo(Integer.parseInt(systemInfoVO.getSysId()));
		return this.buildRetData( systemInfoVO.getUserId() , systemInfoVO.getToken(), systemInfoDomain);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getSystemInfoByName
	 * Author:		1.0 created by lijiaxuan at 2015年11月26日 下午6:32:04
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getSystemInfoByName(SystemInfoVO systemInfoVO){
		//.init();
//ConverterUtil.VOToDomain(systemInfoVO, );
		systemInfoDomain =  systemInfoAL.getSystemInfoByName(systemInfoVO.getName());
		return this.buildRetData( systemInfoVO.getUserId() , systemInfoVO.getToken(), systemInfoDomain);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> deleteSystemInfo(SystemInfoVO systemInfoVO){
		//.init();
//ConverterUtil.VOToDomain(systemInfoVO, );
		   systemInfoAL.deleteSystemInfo(Integer.parseInt(systemInfoVO.getSysId()));
		return this.buildRetData( systemInfoVO.getUserId() , systemInfoVO.getToken());
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifySystemInfo(SystemInfoVO systemInfoVO){
		systemInfoDomain.init();
		ConverterUtil.VOToDomain(systemInfoVO, systemInfoDomain);
		systemInfoAL.modifySystemInfo(systemInfoDomain);
		return this.buildRetData( systemInfoVO.getUserId() , systemInfoVO.getToken());
	}
	
	public Map<String, Object> checkAppVersion(SystemInfoVO systemInfoVO){
		systemInfoDomain.init();
		String resFlag = CommonVariables.FALSE_SHORT_FLAG;
		ConverterUtil.VOToDomain(systemInfoVO, systemInfoDomain);
		boolean res = systemInfoAL.checkAppVersion(systemInfoDomain);
		if (res) resFlag = CommonVariables.TRUE_SHORT_FLAG;
		return this.buildRetData(systemInfoVO.getUserId(), systemInfoVO.getToken(),  resFlag);
		}
	
}
