package com.eyoubika.system.service;
import java.util.Map;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.BaseService;
import com.eyoubika.system.service.ISystemInfoService;
import com.eyoubika.system.web.VO.SystemInfoVO;
import com.eyoubika.system.application.SystemInfoAL;
import com.eyoubika.system.domain.SystemInfoDomain;
public interface ISystemInfoService{
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> addSystemInfo(SystemInfoVO systemInfoVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getSystemInfo(SystemInfoVO systemInfoVO);
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getSystemInfoByName
	 * Author:		1.0 created by lijiaxuan at 2015年11月26日 下午6:33:12
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> getSystemInfoByName(SystemInfoVO systemInfoVO);
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		checkAppVersion
	 * Author:		1.0 created by lijiaxuan at 2015年11月28日 下午8:51:12
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> checkAppVersion(SystemInfoVO systemInfoVO);
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> deleteSystemInfo(SystemInfoVO systemInfoVO);
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public Map<String, Object> modifySystemInfo(SystemInfoVO systemInfoVO);
}
