package com.eyoubika.system.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.system.domain.DbManagerDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-08-20 17:13:17
 *==========================================================================================*/
public class DbManagerDao extends BaseDao {
	String nameSpace = "ts_DbManager";
	private DbManagerDomain dbManagerDomain;	//<<attrNote>>
	public DbManagerDomain getDbManagerDomain(){
		return dbManagerDomain;
	}
	public void setDbManagerDomain(DbManagerDomain dbManagerDomain){
		this.dbManagerDomain = dbManagerDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 17:13:17
	 *--------------------------------------------------------------------------------------*/
	public void addDbManager(DbManagerDomain dbManagerDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertDbManager", dbManagerDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 17:13:17
	 *--------------------------------------------------------------------------------------*/
	public int deleteDbManager(String cId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteDbManager", cId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 17:13:17
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
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 17:13:17
	 *--------------------------------------------------------------------------------------*/
	public void modifyDbManager(DbManagerDomain dbManagerDomain){
		try {
			sqlMapClient.update(nameSpace +".updateDbManager", dbManagerDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 17:13:17
	 *--------------------------------------------------------------------------------------*/
	public DbManagerDomain findDbManager(String cId){
		try {
			return (DbManagerDomain) sqlMapClient.queryForObject(nameSpace +".selectDbManager", cId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 17:13:17
	 *--------------------------------------------------------------------------------------*/
	public DbManagerDomain findDbManagerByDomain(DbManagerDomain dbManagerDomain){
		try {
			return (DbManagerDomain) sqlMapClient.queryForObject(nameSpace +".selectDbManagerByDomain", dbManagerDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 17:13:17
	 *--------------------------------------------------------------------------------------*/
	public List<DbManagerDomain> queryDbManager(DbManagerDomain dbManagerDomain){
		try {
			return (List<DbManagerDomain>) sqlMapClient.queryForList(nameSpace +".selectDbManagerList", dbManagerDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 17:13:17
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(DbManagerDomain dbManagerDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", dbManagerDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	public boolean isExistTable(String tableName){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.queryForObject(nameSpace +".isExistTable", tableName);
			ret = ((int)ob==0) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	
	//>. <CustomFileTag>
}
