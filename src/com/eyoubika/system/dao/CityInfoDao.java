package com.eyoubika.system.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.system.domain.CityInfoDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-14 22:25:10
 *==========================================================================================*/
public class CityInfoDao extends BaseDao {
	String nameSpace = "ts_CityInfo";
	private CityInfoDomain cityInfoDomain;	//<<attrNote>>
	public CityInfoDomain getCityInfoDomain(){
		return cityInfoDomain;
	}
	public void setCityInfoDomain(CityInfoDomain cityInfoDomain){
		this.cityInfoDomain = cityInfoDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:10
	 *--------------------------------------------------------------------------------------*/
	public void addCityInfo(CityInfoDomain cityInfoDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertCityInfo", cityInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:10
	 *--------------------------------------------------------------------------------------*/
	public int deleteCityInfo(String cityId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteCityInfo", cityId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:10
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:10
	 *--------------------------------------------------------------------------------------*/
	public void modifyCityInfo(CityInfoDomain cityInfoDomain){
		try {
			sqlMapClient.update(nameSpace +".updateCityInfo", cityInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:10
	 *--------------------------------------------------------------------------------------*/
	public CityInfoDomain findCityInfo(String cityId){
		try {
			return (CityInfoDomain) sqlMapClient.queryForObject(nameSpace +".selectCityInfo", cityId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:10
	 *--------------------------------------------------------------------------------------*/
	public CityInfoDomain findCityInfoByDomain(CityInfoDomain cityInfoDomain){
		try {
			return (CityInfoDomain) sqlMapClient.queryForObject(nameSpace +".selectCityInfoByDomain", cityInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:10
	 *--------------------------------------------------------------------------------------*/
	public List<CityInfoDomain> queryCityInfo(CityInfoDomain cityInfoDomain){
		try {
			return (List<CityInfoDomain>) sqlMapClient.queryForList(nameSpace +".selectCityInfoList", cityInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:25:10
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(CityInfoDomain cityInfoDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", cityInfoDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
}
