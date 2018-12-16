package com.eyoubika.sbc.dao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.sbc.domain.SbcPriceDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-08-20 16:44:47
 *==========================================================================================*/
public class SbcPriceDao extends BaseDao {
	String nameSpace = "tp_SbcPriceInfo";
	private SbcPriceDomain sbcPriceDomain;	//<<attrNote>>
	public SbcPriceDomain getSbcPriceDomain(){
		return sbcPriceDomain;
	}
	public void setSbcPriceDomain(SbcPriceDomain sbcPriceDomain){
		this.sbcPriceDomain = sbcPriceDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 16:44:47
	 *--------------------------------------------------------------------------------------*/
	public void addSbcPrice(SbcPriceDomain sbcPriceDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertSbcPrice", sbcPriceDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 16:44:47
	 *--------------------------------------------------------------------------------------*/
	public int deleteSbcPrice(Integer sbcId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteSbcPrice", sbcId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 16:44:47
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
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 16:44:47
	 *--------------------------------------------------------------------------------------*/
	public void modifySbcPrice(SbcPriceDomain sbcPriceDomain){
		try {
			sqlMapClient.update(nameSpace +".updateSbcPrice", sbcPriceDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 16:44:47
	 *--------------------------------------------------------------------------------------*/
	public SbcPriceDomain findSbcPrice(Integer sbcId){
		try {
			return (SbcPriceDomain) sqlMapClient.queryForObject(nameSpace +".selectSbcPrice", sbcId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 16:44:47
	 *--------------------------------------------------------------------------------------*/
	public SbcPriceDomain findSbcPriceByDomain(SbcPriceDomain sbcPriceDomain){
		try {
			return (SbcPriceDomain) sqlMapClient.queryForObject(nameSpace +".selectSbcPriceByDomain", sbcPriceDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 16:44:47
	 *--------------------------------------------------------------------------------------*/
	public List<SbcPriceDomain> querySbcPrice(SbcPriceDomain sbcPriceDomain){
		try {
			return (List<SbcPriceDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcPriceList", sbcPriceDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-20 16:44:47
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(SbcPriceDomain sbcPriceDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", sbcPriceDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	public List<SbcPriceDomain> querySbcPriceBySbcList(Map<String,Object> map){
		try {
			return (List<SbcPriceDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcPriceBySbcList",  map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
//>. <CustomFileTag>
}
