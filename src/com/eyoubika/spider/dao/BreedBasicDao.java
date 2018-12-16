package com.eyoubika.spider.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.spider.domain.BreedBasicDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-31 17:30:06
 *==========================================================================================*/
public class BreedBasicDao extends BaseDao {
	String nameSpace = "ta_FetchBreedBasic";
	private BreedBasicDomain breedBasicDomain;	//<<attrNote>>
	public BreedBasicDomain getBreedBasicDomain(){
		return breedBasicDomain;
	}
	public void setBreedBasicDomain(BreedBasicDomain breedBasicDomain){
		this.breedBasicDomain = breedBasicDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:30:06
	 *--------------------------------------------------------------------------------------*/
	public void addBreedBasic(BreedBasicDomain breedBasicDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertBreedBasic", breedBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:30:06
	 *--------------------------------------------------------------------------------------*/
	public int deleteBreedBasic(Integer breedId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteBreedBasic", breedId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:30:06
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:30:06
	 *--------------------------------------------------------------------------------------*/
	public void modifyBreedBasic(BreedBasicDomain breedBasicDomain){
		try {
			sqlMapClient.update(nameSpace +".updateBreedBasic", breedBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:30:06
	 *--------------------------------------------------------------------------------------*/
	public BreedBasicDomain findBreedBasic(Integer breedId){
		try {
			return (BreedBasicDomain) sqlMapClient.queryForObject(nameSpace +".selectBreedBasic", breedId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:30:06
	 *--------------------------------------------------------------------------------------*/
	public BreedBasicDomain findBreedBasicByDomain(BreedBasicDomain breedBasicDomain){
		try {
			return (BreedBasicDomain) sqlMapClient.queryForObject(nameSpace +".selectBreedBasicByDomain", breedBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:30:06
	 *--------------------------------------------------------------------------------------*/
	public List<BreedBasicDomain> queryBreedBasic(BreedBasicDomain breedBasicDomain){
		try {
			return (List<BreedBasicDomain>) sqlMapClient.queryForList(nameSpace +".selectBreedBasicList", breedBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:30:06
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(BreedBasicDomain breedBasicDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", breedBasicDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
}
