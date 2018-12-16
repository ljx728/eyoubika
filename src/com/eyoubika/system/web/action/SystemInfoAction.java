package com.eyoubika.system.web.action;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.eyoubika.common.BaseAction;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.system.service.ISystemInfoService;
import com.eyoubika.system.web.VO.SystemInfoVO;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
 *==========================================================================================*/
public class SystemInfoAction extends BaseAction {
	private ISystemInfoService systemInfoService;	//<<attrNote>>
	private SystemInfoVO systemInfoVO;	//<<attrNote>>
	public ISystemInfoService getSystemInfoService(){
		return systemInfoService;
	}
	public void setSystemInfoService(ISystemInfoService systemInfoService){
		this.systemInfoService = systemInfoService;
	}
	public SystemInfoVO getSystemInfoVO(){
		return systemInfoVO;
	}
	public void setSystemInfoVO(SystemInfoVO systemInfoVO){
		this.systemInfoVO = systemInfoVO;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public String addSystemInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		systemInfoVO.init();
		ConverterUtil.RequestToVO(request, systemInfoVO);
		//> 3. do
		this.jsonData = systemInfoService.addSystemInfo(systemInfoVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public String getSystemInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		//if (null==request.getParameter("userId")){;
		//	throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		//}
		//> 2. build vo
		systemInfoVO.init();
		ConverterUtil.RequestToVO(request, systemInfoVO);
		//> 3. do
		this.jsonData = systemInfoService.getSystemInfo(systemInfoVO);
		return SUCCESS;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getSystemInfoByName
	 * Author:		1.0 created by lijiaxuan at 2015年11月26日 下午6:32:46
	 *--------------------------------------------------------------------------------------*/
	public String getSystemInfoByName(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		//if (null==request.getParameter("userId")){;
		//	throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		//}
		//> 2. build vo
		systemInfoVO.init();
		ConverterUtil.RequestToVO(request, systemInfoVO);
		//> 3. do
		this.jsonData = systemInfoService.getSystemInfoByName(systemInfoVO);
		return SUCCESS;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public String deleteSystemInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		systemInfoVO.init();
		ConverterUtil.RequestToVO(request, systemInfoVO);
		//> 3. do
		this.jsonData = systemInfoService.deleteSystemInfo(systemInfoVO);
		return SUCCESS;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public String modifySystemInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		if (null==request.getParameter("userId")){;
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}
		//> 2. build vo
		systemInfoVO.init();
		ConverterUtil.RequestToVO(request, systemInfoVO);
		//> 3. do
		this.jsonData = systemInfoService.modifySystemInfo(systemInfoVO);
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		checkAppVersion
	 * Author:		1.0 created by lijiaxuan at 2015年11月28日 下午8:54:19
	 *--------------------------------------------------------------------------------------*/
	public String checkAppVersion(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//> 1.check params
		//if (null==request.getParameter("userId")){;
		//	throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		//}
		//> 2. build vo
		systemInfoVO.init();
		ConverterUtil.RequestToVO(request, systemInfoVO);
		//> 3. do
		this.jsonData = systemInfoService.checkAppVersion(systemInfoVO);
		return SUCCESS;
	}
}
