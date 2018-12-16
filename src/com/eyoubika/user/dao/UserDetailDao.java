package com.eyoubika.user.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.UserDetailDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-08-01 09:33:44
 *==========================================================================================*/
public class UserDetailDao extends BaseDao {
	String nameSpace = "tu_UserDetailInfo";
	private UserDetailDomain userDetailDomain;	//<<attrNote>>
	public UserDetailDomain getUserDetailDomain(){
		return userDetailDomain;
	}
	public void setUserDetailDomain(UserDetailDomain userDetailDomain){
		this.userDetailDomain = userDetailDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:44
	 *--------------------------------------------------------------------------------------*/
	public void addUserDetail(UserDetailDomain userDetailDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertUserDetail", userDetailDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:44
	 *--------------------------------------------------------------------------------------*/
	public int deleteUserDetail(Integer userId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteUserDetail", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:44
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
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:44
	 *--------------------------------------------------------------------------------------*/
	public void modifyUserDetail(UserDetailDomain userDetailDomain){
		try {
			sqlMapClient.update(nameSpace +".updateUserDetail", userDetailDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:44
	 *--------------------------------------------------------------------------------------*/
	public UserDetailDomain findUserDetail(Integer userId){
		try {
			return (UserDetailDomain) sqlMapClient.queryForObject(nameSpace +".selectUserDetail", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:44
	 *--------------------------------------------------------------------------------------*/
	public UserDetailDomain findUserDetailByDomain(UserDetailDomain userDetailDomain){
		try {
			return (UserDetailDomain) sqlMapClient.queryForObject(nameSpace +".selectUserDetailByDomain", userDetailDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:44
	 *--------------------------------------------------------------------------------------*/
	public List<UserDetailDomain> queryUserDetail(UserDetailDomain userDetailDomain){
		try {
			return (List<UserDetailDomain>) sqlMapClient.queryForList(nameSpace +".selectUserDetailList", userDetailDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:44
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(UserDetailDomain userDetailDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", userDetailDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	public boolean isExist(Integer userId){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExist", userId);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
}
