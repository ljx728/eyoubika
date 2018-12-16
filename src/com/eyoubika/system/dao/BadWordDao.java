package com.eyoubika.system.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.system.domain.BadWordDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-08-03 15:39:24
 *==========================================================================================*/
public class BadWordDao extends BaseDao {
	String nameSpace = "ts_BadWordInfo";
	private BadWordDomain badWordDomain;	//<<attrNote>>
	public BadWordDomain getBadWordDomain(){
		return badWordDomain;
	}
	public void setBadWordDomain(BadWordDomain badWordDomain){
		this.badWordDomain = badWordDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 15:39:24
	 *--------------------------------------------------------------------------------------*/
	public int addBadWord(BadWordDomain badWordDomain){
		int insertId = 0;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.insert(nameSpace +".insertBadWord", badWordDomain);
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
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 15:39:24
	 *--------------------------------------------------------------------------------------*/
	public int deleteBadWord(Integer wordId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteBadWord", wordId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 15:39:24
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
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 15:39:24
	 *--------------------------------------------------------------------------------------*/
	public void modifyBadWord(BadWordDomain badWordDomain){
		try {
			sqlMapClient.update(nameSpace +".updateBadWord", badWordDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 15:39:24
	 *--------------------------------------------------------------------------------------*/
	public BadWordDomain findBadWord(Integer wordId){
		try {
			return (BadWordDomain) sqlMapClient.queryForObject(nameSpace +".selectBadWord", wordId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 15:39:24
	 *--------------------------------------------------------------------------------------*/
	public BadWordDomain findBadWordByDomain(BadWordDomain badWordDomain){
		try {
			return (BadWordDomain) sqlMapClient.queryForObject(nameSpace +".selectBadWordByDomain", badWordDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 15:39:24
	 *--------------------------------------------------------------------------------------*/
	public List<BadWordDomain> queryBadWord(BadWordDomain badWordDomain){
		try {
			return (List<BadWordDomain>) sqlMapClient.queryForList(nameSpace +".selectBadWordList", badWordDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 15:39:24
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(BadWordDomain badWordDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", badWordDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	public boolean isLikeBadWord(BadWordDomain badWordDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isLikeBadWord", badWordDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
}
