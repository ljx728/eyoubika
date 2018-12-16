package com.eyoubika.sbc.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.sbc.domain.ExchangeBasicDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-23 20:09:44
 *==========================================================================================*/
public class ExchangeBasicDao extends BaseDao {
	String nameSpace = "tp_ExchangeBasicInfo";
	private ExchangeBasicDomain exchangeBasicDomain;	//<<attrNote>>
	public ExchangeBasicDomain getExchangeBasicDomain(){
		return exchangeBasicDomain;
	}
	public void setExchangeBasicDomain(ExchangeBasicDomain exchangeBasicDomain){
		this.exchangeBasicDomain = exchangeBasicDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-23 20:09:44
	 *--------------------------------------------------------------------------------------*/
	public void addExchangeBasic(ExchangeBasicDomain exchangeBasicDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertExchangeBasic", exchangeBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-23 20:09:44
	 *--------------------------------------------------------------------------------------*/
	public int deleteExchangeBasic(String exId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteExchangeBasic", exId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-23 20:09:44
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-23 20:09:44
	 *--------------------------------------------------------------------------------------*/
	public void modifyExchangeBasic(ExchangeBasicDomain exchangeBasicDomain){
		try {
			sqlMapClient.update(nameSpace +".updateExchangeBasic", exchangeBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-23 20:09:44
	 *--------------------------------------------------------------------------------------*/
	public ExchangeBasicDomain findExchangeBasic(String exId){
		try {
			return (ExchangeBasicDomain) sqlMapClient.queryForObject(nameSpace +".selectExchangeBasic", exId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-23 20:09:44
	 *--------------------------------------------------------------------------------------*/
	public ExchangeBasicDomain findExchangeBasicByDomain(ExchangeBasicDomain exchangeBasicDomain){
		try {
			return (ExchangeBasicDomain) sqlMapClient.queryForObject(nameSpace +".selectExchangeBasicByDomain", exchangeBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-23 20:09:44
	 *--------------------------------------------------------------------------------------*/
	public List<ExchangeBasicDomain> queryExchangeBasic(ExchangeBasicDomain exchangeBasicDomain){
		try {
			return (List<ExchangeBasicDomain>) sqlMapClient.queryForList(nameSpace +".selectExchangeBasicList", exchangeBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-23 20:09:44
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(ExchangeBasicDomain exchangeBasicDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", exchangeBasicDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	public List<ExchangeBasicDomain> queryAllExchangeBasic(){
		try {
			return (List<ExchangeBasicDomain>) sqlMapClient.queryForList(nameSpace +".selectAllExchangeBasicList");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	//>. <CustomFileTag>
}
