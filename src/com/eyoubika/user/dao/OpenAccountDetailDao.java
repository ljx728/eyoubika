package com.eyoubika.user.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.OpenAccountDetailDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-22 20:53:40
 *==========================================================================================*/
public class OpenAccountDetailDao extends BaseDao {
	String nameSpace = "tu_OpenAccountDetail";
	private OpenAccountDetailDomain openAccountDetailDomain;	//<<attrNote>>
	public OpenAccountDetailDomain getOpenAccountDetailDomain(){
		return openAccountDetailDomain;
	}
	public void setOpenAccountDetailDomain(OpenAccountDetailDomain openAccountDetailDomain){
		this.openAccountDetailDomain = openAccountDetailDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:40
	 *--------------------------------------------------------------------------------------*/
	public void addOpenAccountDetail(OpenAccountDetailDomain openAccountDetailDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertOpenAccountDetail", openAccountDetailDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:40
	 *--------------------------------------------------------------------------------------*/
	public int deleteOpenAccountDetail(String openNo){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteOpenAccountDetail", openNo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:40
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:40
	 *--------------------------------------------------------------------------------------*/
	public void modifyOpenAccountDetail(OpenAccountDetailDomain openAccountDetailDomain){
		try {
			sqlMapClient.update(nameSpace +".updateOpenAccountDetail", openAccountDetailDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:40
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountDetailDomain findOpenAccountDetail(String openNo){
		try {
			return (OpenAccountDetailDomain) sqlMapClient.queryForObject(nameSpace +".selectOpenAccountDetail", openNo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:40
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountDetailDomain findOpenAccountDetailByDomain(OpenAccountDetailDomain openAccountDetailDomain){
		try {
			return (OpenAccountDetailDomain) sqlMapClient.queryForObject(nameSpace +".selectOpenAccountDetailByDomain", openAccountDetailDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:40
	 *--------------------------------------------------------------------------------------*/
	public List<OpenAccountDetailDomain> queryOpenAccountDetail(OpenAccountDetailDomain openAccountDetailDomain){
		try {
			return (List<OpenAccountDetailDomain>) sqlMapClient.queryForList(nameSpace +".selectOpenAccountDetailList", openAccountDetailDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:40
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(OpenAccountDetailDomain openAccountDetailDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", openAccountDetailDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
}
