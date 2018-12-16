package com.eyoubika.user.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.UserInfoDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-08-01 09:33:37
 *==========================================================================================*/
public class UserInfoDao extends BaseDao {
	String nameSpace = "tu_UserInfoInfo";
	private UserInfoDomain userInfoDomain;	//<<attrNote>>
	public UserInfoDomain getUserInfoDomain(){
		return userInfoDomain;
	}
	public void setUserInfoDomain(UserInfoDomain userInfoDomain){
		this.userInfoDomain = userInfoDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:37
	 *--------------------------------------------------------------------------------------*/
	public void addUserInfo(UserInfoDomain userInfoDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertUserInfo", userInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:37
	 *--------------------------------------------------------------------------------------*/
	public int deleteUserInfo(Integer userId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteUserInfo", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:37
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
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:37
	 *--------------------------------------------------------------------------------------*/
	public void modifyUserInfo(UserInfoDomain userInfoDomain){
		try {
			sqlMapClient.update(nameSpace +".updateUserInfo", userInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:37
	 *--------------------------------------------------------------------------------------*/
	public UserInfoDomain findUserInfo(Integer userId){
		try {
			return (UserInfoDomain) sqlMapClient.queryForObject(nameSpace +".selectUserInfo", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:37
	 *--------------------------------------------------------------------------------------*/
	public UserInfoDomain findUserInfoByDomain(UserInfoDomain userInfoDomain){
		try {
			return (UserInfoDomain) sqlMapClient.queryForObject(nameSpace +".selectUserInfoByDomain", userInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:37
	 *--------------------------------------------------------------------------------------*/
	public List<UserInfoDomain> queryUserInfo(UserInfoDomain userInfoDomain){
		try {
			return (List<UserInfoDomain>) sqlMapClient.queryForList(nameSpace +".selectUserInfoList", userInfoDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-01 09:33:37
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(UserInfoDomain userInfoDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", userInfoDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}
