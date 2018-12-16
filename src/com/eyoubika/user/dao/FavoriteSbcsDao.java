package com.eyoubika.user.dao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.FavoriteSbcsDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-28 18:54:35
 *==========================================================================================*/
public class FavoriteSbcsDao extends BaseDao {
	String nameSpace = "tu_FavoriteSbcList";
	private FavoriteSbcsDomain favoriteSbcsDomain;	//<<attrNote>>
	public FavoriteSbcsDomain getFavoriteSbcsDomain(){
		return favoriteSbcsDomain;
	}
	public void setFavoriteSbcsDomain(FavoriteSbcsDomain favoriteSbcsDomain){
		this.favoriteSbcsDomain = favoriteSbcsDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-28 18:54:35
	 *--------------------------------------------------------------------------------------*/
	public void addFavoriteSbcs(FavoriteSbcsDomain favoriteSbcsDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertFavoriteSbcs", favoriteSbcsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-28 18:54:35
	 *--------------------------------------------------------------------------------------*/
	public int deleteFavoriteSbcs(Integer userId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteFavoriteSbcs", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-28 18:54:35
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-28 18:54:35
	 *--------------------------------------------------------------------------------------*/
	public void modifyFavoriteSbcs(FavoriteSbcsDomain favoriteSbcsDomain){
		try {
			sqlMapClient.update(nameSpace +".updateFavoriteSbcs", favoriteSbcsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-28 18:54:35
	 *--------------------------------------------------------------------------------------*/
	public FavoriteSbcsDomain findFavoriteSbcs(Integer userId){
		try {
			return (FavoriteSbcsDomain) sqlMapClient.queryForObject(nameSpace +".selectFavoriteSbcs", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-28 18:54:35
	 *--------------------------------------------------------------------------------------*/
	public FavoriteSbcsDomain findFavoriteSbcsByDomain(FavoriteSbcsDomain favoriteSbcsDomain){
		try {
			return (FavoriteSbcsDomain) sqlMapClient.queryForObject(nameSpace +".selectFavoriteSbcsByDomain", favoriteSbcsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-28 18:54:35
	 *--------------------------------------------------------------------------------------*/
	public List<FavoriteSbcsDomain> queryFavoriteSbcs(FavoriteSbcsDomain favoriteSbcsDomain){
		try {
			return (List<FavoriteSbcsDomain>) sqlMapClient.queryForList(nameSpace +".selectFavoriteSbcsList", favoriteSbcsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-28 18:54:35
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(FavoriteSbcsDomain favoriteSbcsDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", favoriteSbcsDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	public List<FavoriteSbcsDomain> queryFavoriteSbcsByUserId(int userId){
		try {
			return (List<FavoriteSbcsDomain>) sqlMapClient.queryForList(nameSpace +".selectFavoriteSbcsListByUserId", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	public int deleteFavoriteSbcsByDomain(FavoriteSbcsDomain favoriteSbcsDomain){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteFavoriteSbcsByDomain", favoriteSbcsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public int deleteFavoriteSbcsListBySbcIdLIst(Map<String,Object> map){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteFavoriteSbcsListBySbcIdLIst", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	//>. <CustomFileTag>
}
