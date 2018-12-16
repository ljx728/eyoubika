package com.eyoubika.user.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.OpenAccountBriefDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-22 20:53:33
 *==========================================================================================*/
public class OpenAccountBriefDao extends BaseDao {
	String nameSpace = "tu_OpenAccountBrief";
	private OpenAccountBriefDomain openAccountBriefDomain;	//<<attrNote>>
	public OpenAccountBriefDomain getOpenAccountBriefDomain(){
		return openAccountBriefDomain;
	}
	public void setOpenAccountBriefDomain(OpenAccountBriefDomain openAccountBriefDomain){
		this.openAccountBriefDomain = openAccountBriefDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:33
	 *--------------------------------------------------------------------------------------*/
	public void addOpenAccountBrief(OpenAccountBriefDomain openAccountBriefDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertOpenAccountBrief", openAccountBriefDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:33
	 *--------------------------------------------------------------------------------------*/
	public int deleteOpenAccountBrief(String openNo){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteOpenAccountBrief", openNo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:33
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:33
	 *--------------------------------------------------------------------------------------*/
	public void modifyOpenAccountBrief(OpenAccountBriefDomain openAccountBriefDomain){
		try {
			sqlMapClient.update(nameSpace +".updateOpenAccountBrief", openAccountBriefDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:33
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountBriefDomain findOpenAccountBrief(String openNo){
		try {
			return (OpenAccountBriefDomain) sqlMapClient.queryForObject(nameSpace +".selectOpenAccountBrief", openNo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:33
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountBriefDomain findOpenAccountBriefByDomain(OpenAccountBriefDomain openAccountBriefDomain){
		try {
			return (OpenAccountBriefDomain) sqlMapClient.queryForObject(nameSpace +".selectOpenAccountBriefByDomain", openAccountBriefDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:33
	 *--------------------------------------------------------------------------------------*/
	public List<OpenAccountBriefDomain> queryOpenAccountBrief(OpenAccountBriefDomain openAccountBriefDomain){
		try {
			return (List<OpenAccountBriefDomain>) sqlMapClient.queryForList(nameSpace +".selectOpenAccountBriefList", openAccountBriefDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-22 20:53:33
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(OpenAccountBriefDomain openAccountBriefDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", openAccountBriefDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
}
