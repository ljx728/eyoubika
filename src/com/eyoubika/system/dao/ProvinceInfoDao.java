package com.eyoubika.system.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.system.domain.ProvinceInfoDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-14 22:25:03
 *==========================================================================================*/
public class ProvinceInfoDao extends BaseDao {
	String nameSpace = "ts_ProvinceInfo";
	private ProvinceInfoDomain provinceInfoDomain;	//<<attrNote>>
	public ProvinceInfoDomain getProvinceInfoDomain(){
		return provinceInfoDomain;
	}
	public void setProvinceInfoDomain(ProvinceInfoDomain provinceInfoDomain){
		this.provinceInfoDomain = provinceInfoDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:03
	 *--------------------------------------------------------------------------------------*/
	public void addProvinceInfo(ProvinceInfoDomain provinceInfoDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertProvinceInfo", provinceInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:03
	 *--------------------------------------------------------------------------------------*/
	public int deleteProvinceInfo(String proId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteProvinceInfo", proId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:03
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:03
	 *--------------------------------------------------------------------------------------*/
	public void modifyProvinceInfo(ProvinceInfoDomain provinceInfoDomain){
		try {
			sqlMapClient.update(nameSpace +".updateProvinceInfo", provinceInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:03
	 *--------------------------------------------------------------------------------------*/
	public ProvinceInfoDomain findProvinceInfo(String proId){
		try {
			return (ProvinceInfoDomain) sqlMapClient.queryForObject(nameSpace +".selectProvinceInfo", proId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:03
	 *--------------------------------------------------------------------------------------*/
	public ProvinceInfoDomain findProvinceInfoByDomain(ProvinceInfoDomain provinceInfoDomain){
		try {
			return (ProvinceInfoDomain) sqlMapClient.queryForObject(nameSpace +".selectProvinceInfoByDomain", provinceInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:03
	 *--------------------------------------------------------------------------------------*/
	public List<ProvinceInfoDomain> queryProvinceInfo(ProvinceInfoDomain provinceInfoDomain){
		try {
			return (List<ProvinceInfoDomain>) sqlMapClient.queryForList(nameSpace +".selectProvinceInfoList", provinceInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:03
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(ProvinceInfoDomain provinceInfoDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", provinceInfoDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
}
