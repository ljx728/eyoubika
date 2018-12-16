package com.eyoubika.sbc.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.sbc.domain.SbcIdMapDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-08-08 09:22:08
 *==========================================================================================*/
public class SbcIdMapDao extends BaseDao {
	String nameSpace = "tp_SbcIdMap";
	private SbcIdMapDomain sbcIdMapDomain;	//<<attrNote>>
	public SbcIdMapDomain getSbcIdMapDomain(){
		return sbcIdMapDomain;
	}
	public void setSbcIdMapDomain(SbcIdMapDomain sbcIdMapDomain){
		this.sbcIdMapDomain = sbcIdMapDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-08 09:22:08
	 *--------------------------------------------------------------------------------------*/
	public int addSbcIdMap(SbcIdMapDomain sbcIdMapDomain){
		int insertId = 0;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.insert(nameSpace +".insertSbcIdMap", sbcIdMapDomain);
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
	 * Author:		1.0 created by lijiaxuan at 2015-08-08 09:22:08
	 *--------------------------------------------------------------------------------------*/
	public int deleteSbcIdMap(Integer mapId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteSbcIdMap", mapId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-08 09:22:08
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
	 * Author:		1.0 created by lijiaxuan at 2015-08-08 09:22:08
	 *--------------------------------------------------------------------------------------*/
	public void modifySbcIdMap(SbcIdMapDomain sbcIdMapDomain){
		try {
			sqlMapClient.update(nameSpace +".updateSbcIdMap", sbcIdMapDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-08 09:22:08
	 *--------------------------------------------------------------------------------------*/
	public SbcIdMapDomain findSbcIdMap(Integer mapId){
		try {
			return (SbcIdMapDomain) sqlMapClient.queryForObject(nameSpace +".selectSbcIdMap", mapId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-08 09:22:08
	 *--------------------------------------------------------------------------------------*/
	public SbcIdMapDomain findSbcIdMapByDomain(SbcIdMapDomain sbcIdMapDomain){
		try {
			return (SbcIdMapDomain) sqlMapClient.queryForObject(nameSpace +".selectSbcIdMapByDomain", sbcIdMapDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-08 09:22:08
	 *--------------------------------------------------------------------------------------*/
	public List<SbcIdMapDomain> querySbcIdMap(SbcIdMapDomain sbcIdMapDomain){
		try {
			return (List<SbcIdMapDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcIdMapList", sbcIdMapDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-08 09:22:08
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(SbcIdMapDomain sbcIdMapDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", sbcIdMapDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
