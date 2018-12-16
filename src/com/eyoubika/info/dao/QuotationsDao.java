package com.eyoubika.info.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.info.domain.QuotationsDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-08-18 09:58:33
 *==========================================================================================*/
public class QuotationsDao extends BaseDao {
	String nameSpace = "ts_Quotations";
	private QuotationsDomain quotationsDomain;	//<<attrNote>>
	public QuotationsDomain getQuotationsDomain(){
		return quotationsDomain;
	}
	public void setQuotationsDomain(QuotationsDomain quotationsDomain){
		this.quotationsDomain = quotationsDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-18 09:58:33
	 *--------------------------------------------------------------------------------------*/
	public void addQuotations(QuotationsDomain quotationsDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertQuotations", quotationsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-18 09:58:33
	 *--------------------------------------------------------------------------------------*/
	public int deleteQuotations(Integer sbcId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteQuotations", sbcId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-18 09:58:33
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
	 * Author:		1.0 created by lijiaxuan at 2015-08-18 09:58:33
	 *--------------------------------------------------------------------------------------*/
	public void modifyQuotations(QuotationsDomain quotationsDomain){
		try {
			sqlMapClient.update(nameSpace +".updateQuotations", quotationsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-18 09:58:33
	 *--------------------------------------------------------------------------------------*/
	public QuotationsDomain findQuotations(Integer sbcId){
		try {
			return (QuotationsDomain) sqlMapClient.queryForObject(nameSpace +".selectQuotations", sbcId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-18 09:58:33
	 *--------------------------------------------------------------------------------------*/
	public QuotationsDomain findQuotationsByDomain(QuotationsDomain quotationsDomain){
		try {
			return (QuotationsDomain) sqlMapClient.queryForObject(nameSpace +".selectQuotationsByDomain", quotationsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-18 09:58:33
	 *--------------------------------------------------------------------------------------*/
	public List<QuotationsDomain> queryQuotations(QuotationsDomain quotationsDomain){
		try {
			return (List<QuotationsDomain>) sqlMapClient.queryForList(nameSpace +".selectQuotationsList", quotationsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-18 09:58:33
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(QuotationsDomain quotationsDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", quotationsDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
}
