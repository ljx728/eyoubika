package com.eyoubika.user.dao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.FavoriteExsDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-11-19 10:24:49
 *==========================================================================================*/
public class FavoriteExsDao extends BaseDao {
	String nameSpace = "tu_FavoriteExList";
	private FavoriteExsDomain favoriteExsDomain;	//<<attrNote>>
	public FavoriteExsDomain getFavoriteExsDomain(){
		return favoriteExsDomain;
	}
	public void setFavoriteExsDomain(FavoriteExsDomain favoriteExsDomain){
		this.favoriteExsDomain = favoriteExsDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:24:49
	 *--------------------------------------------------------------------------------------*/
	public void addFavoriteExs(FavoriteExsDomain favoriteExsDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertFavoriteExs", favoriteExsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:24:49
	 *--------------------------------------------------------------------------------------*/
	public int deleteFavoriteExs(Integer userId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteFavoriteExs", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:24:49
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
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:24:49
	 *--------------------------------------------------------------------------------------*/
	public void modifyFavoriteExs(FavoriteExsDomain favoriteExsDomain){
		try {
			sqlMapClient.update(nameSpace +".updateFavoriteExs", favoriteExsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:24:49
	 *--------------------------------------------------------------------------------------*/
	public FavoriteExsDomain findFavoriteExs(Integer userId){
		try {
			return (FavoriteExsDomain) sqlMapClient.queryForObject(nameSpace +".selectFavoriteExs", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:24:49
	 *--------------------------------------------------------------------------------------*/
	public FavoriteExsDomain findFavoriteExsByDomain(FavoriteExsDomain favoriteExsDomain){
		try {
			return (FavoriteExsDomain) sqlMapClient.queryForObject(nameSpace +".selectFavoriteExsByDomain", favoriteExsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:24:49
	 *--------------------------------------------------------------------------------------*/
	public List<FavoriteExsDomain> queryFavoriteExs(FavoriteExsDomain favoriteExsDomain){
		try {
			return (List<FavoriteExsDomain>) sqlMapClient.queryForList(nameSpace +".selectFavoriteExsList", favoriteExsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-19 10:24:49
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(FavoriteExsDomain favoriteExsDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", favoriteExsDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	public List<FavoriteExsDomain> queryFavoriteExsByUserId(int userId){
		try {
			return (List<FavoriteExsDomain>) sqlMapClient.queryForList(nameSpace +".selectFavoriteExListByUserId", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	public int deleteFavoriteExsByDomain(FavoriteExsDomain favoriteExsDomain){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteFavoriteExListByDomain", favoriteExsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public int deleteFavoriteExsByExIds(Map<String,Object> map){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteFavoriteExListByExIdList", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	
	//>. <CustomFileTag>
}
