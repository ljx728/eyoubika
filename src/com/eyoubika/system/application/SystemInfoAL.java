package com.eyoubika.system.application;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.common.YbkException;
import com.eyoubika.system.domain.SystemInfoDomain;
import com.eyoubika.system.dao.SystemInfoDao;
public class SystemInfoAL{
	private SystemInfoDao systemInfoDao;	//
	public SystemInfoDao getSystemInfoDao(){
		return systemInfoDao;
	}
	public void setSystemInfoDao(SystemInfoDao systemInfoDao){
		this.systemInfoDao = systemInfoDao;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public void addSystemInfo(SystemInfoDomain systemInfoDomain){
		systemInfoDomain.setDate(CommonUtil.getNowDate());
		systemInfoDao.addSystemInfo(systemInfoDomain);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public SystemInfoDomain getSystemInfo(int sysId){
		return systemInfoDao.findSystemInfo(sysId);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getSystemInfoByName
	 * Author:		1.0 created by lijiaxuan at 2015年11月26日 下午6:29:04
	 *--------------------------------------------------------------------------------------*/
	public SystemInfoDomain getSystemInfoByName(String name){
		return systemInfoDao.findSystemInfoByName(name);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public void deleteSystemInfo( int sysId){
		systemInfoDao.deleteSystemInfo(sysId);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 18:24:20
	 *--------------------------------------------------------------------------------------*/
	public void modifySystemInfo(SystemInfoDomain systemInfoDomain){
		systemInfoDao.modifySystemInfo(systemInfoDomain);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		checkAppVersion
	 * Author:		1.0 created by lijiaxuan at 2015年11月28日 下午8:53:21
	 *--------------------------------------------------------------------------------------*/
	public Boolean checkAppVersion(SystemInfoDomain systemInfoDomain){	
		SystemInfoDomain resDomain = systemInfoDao.findSystemInfoByName(systemInfoDomain.getName());
		boolean flag = true;
		if (resDomain == null) return false;
		String version = resDomain.getValue();
		String clientVersion = systemInfoDomain.getValue();
		System.out.println("version " + version);
		String cs[] = clientVersion.split("\\.");
		String vs[] = version.split("\\.");
		if (Integer.parseInt(vs[0]) > Integer.parseInt(cs[0])){
			flag = true;
		} else if (Integer.parseInt(vs[0]) < Integer.parseInt(cs[0])){
			flag = false;
		} else {
			if (Integer.parseInt(vs[1]) > Integer.parseInt(cs[1])){
				flag = true;
			} else if (Integer.parseInt(vs[1]) < Integer.parseInt(cs[1])){
				flag = false;
			} else {
				
					if (Integer.parseInt(vs[2]) >= Integer.parseInt(cs[2])){
						flag = true;
					} else {
						flag = false;
					}
			
				
			}
			
		}
		
	
		return flag;
	}
}
