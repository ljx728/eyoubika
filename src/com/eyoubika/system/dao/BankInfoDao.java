package com.eyoubika.system.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.system.domain.BankInfoDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-14 22:24:06
 *==========================================================================================*/
public class BankInfoDao extends BaseDao {
	String nameSpace = "ts_BankInfo";
	private BankInfoDomain bankInfoDomain;	//<<attrNote>>
	public BankInfoDomain getBankInfoDomain(){
		return bankInfoDomain;
	}
	public void setBankInfoDomain(BankInfoDomain bankInfoDomain){
		this.bankInfoDomain = bankInfoDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:24:06
	 *--------------------------------------------------------------------------------------*/
	public void addBankInfo(BankInfoDomain bankInfoDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertBankInfo", bankInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:24:06
	 *--------------------------------------------------------------------------------------*/
	public int deleteBankInfo(String bankId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteBankInfo", bankId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:24:06
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:24:06
	 *--------------------------------------------------------------------------------------*/
	public void modifyBankInfo(BankInfoDomain bankInfoDomain){
		try {
			sqlMapClient.update(nameSpace +".updateBankInfo", bankInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:24:06
	 *--------------------------------------------------------------------------------------*/
	public BankInfoDomain findBankInfo(String bankId){
		try {
			return (BankInfoDomain) sqlMapClient.queryForObject(nameSpace +".selectBankInfo", bankId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:24:06
	 *--------------------------------------------------------------------------------------*/
	public BankInfoDomain findBankInfoByDomain(BankInfoDomain bankInfoDomain){
		try {
			return (BankInfoDomain) sqlMapClient.queryForObject(nameSpace +".selectBankInfoByDomain", bankInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:24:06
	 *--------------------------------------------------------------------------------------*/
	public List<BankInfoDomain> queryBankInfo(BankInfoDomain bankInfoDomain){
		try {
			return (List<BankInfoDomain>) sqlMapClient.queryForList(nameSpace +".selectBankInfoList", bankInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-14 22:24:06
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(BankInfoDomain bankInfoDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", bankInfoDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
}
