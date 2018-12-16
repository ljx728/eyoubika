package com.eyoubika.user.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.OpenAccountCorpDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-24 15:36:41
 *==========================================================================================*/
public class OpenAccountCorpDao extends BaseDao {
	String nameSpace = "tu_OpenAccountCorp";
	private OpenAccountCorpDomain openAccountCorpDomain;	//<<attrNote>>
	public OpenAccountCorpDomain getOpenAccountCorpDomain(){
		return openAccountCorpDomain;
	}
	public void setOpenAccountCorpDomain(OpenAccountCorpDomain openAccountCorpDomain){
		this.openAccountCorpDomain = openAccountCorpDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:41
	 *--------------------------------------------------------------------------------------*/
	public void addOpenAccountCorp(OpenAccountCorpDomain openAccountCorpDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertOpenAccountCorp", openAccountCorpDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:41
	 *--------------------------------------------------------------------------------------*/
	public int deleteOpenAccountCorp(String openNo){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteOpenAccountCorp", openNo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:41
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:41
	 *--------------------------------------------------------------------------------------*/
	public void modifyOpenAccountCorp(OpenAccountCorpDomain openAccountCorpDomain){
		try {
			sqlMapClient.update(nameSpace +".updateOpenAccountCorp", openAccountCorpDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:41
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountCorpDomain findOpenAccountCorp(String openNo){
		try {
			return (OpenAccountCorpDomain) sqlMapClient.queryForObject(nameSpace +".selectOpenAccountCorp", openNo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:41
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountCorpDomain findOpenAccountCorpByDomain(OpenAccountCorpDomain openAccountCorpDomain){
		try {
			return (OpenAccountCorpDomain) sqlMapClient.queryForObject(nameSpace +".selectOpenAccountCorpByDomain", openAccountCorpDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:41
	 *--------------------------------------------------------------------------------------*/
	public List<OpenAccountCorpDomain> queryOpenAccountCorp(OpenAccountCorpDomain openAccountCorpDomain){
		try {
			return (List<OpenAccountCorpDomain>) sqlMapClient.queryForList(nameSpace +".selectOpenAccountCorpList", openAccountCorpDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:41
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(OpenAccountCorpDomain openAccountCorpDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", openAccountCorpDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
}
