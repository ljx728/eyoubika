package com.eyoubika.system.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.system.domain.SystemInfoDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-11-19 10:31:50
 *==========================================================================================*/
public class SystemInfoDao extends BaseDao {
	String nameSpace = "ts_SystemInfo";
	private SystemInfoDomain systemInfoDomain;	//<<attrNote>>
	public SystemInfoDomain getSystemInfoDomain(){
		return systemInfoDomain;
	}
	public void setSystemInfoDomain(SystemInfoDomain systemInfoDomain){
		this.systemInfoDomain = systemInfoDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:31:50
	 *--------------------------------------------------------------------------------------*/
	public int addSystemInfo(SystemInfoDomain systemInfoDomain){
		int insertId = 0;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.insert(nameSpace +".insertSystemInfo", systemInfoDomain);
		} catch (SQLException e) {
			ob = null;
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		insertId = Integer.parseInt(ob.toString());
		ob = null;
		return insertId;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:31:50
	 *--------------------------------------------------------------------------------------*/
	public int deleteSystemInfo(Integer sysId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteSystemInfo", sysId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:31:50
	 *--------------------------------------------------------------------------------------*/
	public int deleteAll(){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteAll");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:31:50
	 *--------------------------------------------------------------------------------------*/
	public void modifySystemInfo(SystemInfoDomain systemInfoDomain){
		try {
			sqlMapClient.update(nameSpace +".updateSystemInfo", systemInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:31:50
	 *--------------------------------------------------------------------------------------*/
	public SystemInfoDomain findSystemInfo(Integer sysId){
		try {
			return (SystemInfoDomain) sqlMapClient.queryForObject(nameSpace +".selectSystemInfo", sysId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:31:50
	 *--------------------------------------------------------------------------------------*/
	public SystemInfoDomain findSystemInfoByDomain(SystemInfoDomain systemInfoDomain){
		try {
			return (SystemInfoDomain) sqlMapClient.queryForObject(nameSpace +".selectSystemInfoByDomain", systemInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:31:50
	 *--------------------------------------------------------------------------------------*/
	public List<SystemInfoDomain> querySystemInfo(SystemInfoDomain systemInfoDomain){
		try {
			return (List<SystemInfoDomain>) sqlMapClient.queryForList(nameSpace +".selectSystemInfoList", systemInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:31:50
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(SystemInfoDomain systemInfoDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", systemInfoDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	public SystemInfoDomain findSystemInfoByName(String name){
		try {
			return (SystemInfoDomain) sqlMapClient.queryForObject(nameSpace +".selectSystemInfoByName", name);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	//>. <CustomFileTag>
}
