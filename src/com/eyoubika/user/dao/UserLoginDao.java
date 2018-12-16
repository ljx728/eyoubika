package com.eyoubika.user.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.UserLoginDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-08-03 17:13:38
 *==========================================================================================*/
public class UserLoginDao extends BaseDao {
	String nameSpace = "tu_UserLoginInfo";
	private UserLoginDomain userLoginDomain;	//<<attrNote>>
	public UserLoginDomain getUserLoginDomain(){
		return userLoginDomain;
	}
	public void setUserLoginDomain(UserLoginDomain userLoginDomain){
		this.userLoginDomain = userLoginDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 17:13:38
	 *--------------------------------------------------------------------------------------*/
	public void addUserLogin(UserLoginDomain userLoginDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertUserLogin", userLoginDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 17:13:38
	 *--------------------------------------------------------------------------------------*/
	public int deleteUserLogin(Integer userId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteUserLogin", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 17:13:38
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
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 17:13:38
	 *--------------------------------------------------------------------------------------*/
	public void modifyUserLogin(UserLoginDomain userLoginDomain){
		try {
			sqlMapClient.update(nameSpace +".updateUserLogin", userLoginDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 17:13:38
	 *--------------------------------------------------------------------------------------*/
	public UserLoginDomain findUserLogin(Integer userId){
		try {
			return (UserLoginDomain) sqlMapClient.queryForObject(nameSpace +".selectUserLogin", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 17:13:38
	 *--------------------------------------------------------------------------------------*/
	public UserLoginDomain findUserLoginByDomain(UserLoginDomain userLoginDomain){
		try {
			return (UserLoginDomain) sqlMapClient.queryForObject(nameSpace +".selectUserLoginByDomain", userLoginDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 17:13:38
	 *--------------------------------------------------------------------------------------*/
	public List<UserLoginDomain> queryUserLogin(UserLoginDomain userLoginDomain){
		try {
			return (List<UserLoginDomain>) sqlMapClient.queryForList(nameSpace +".selectUserLoginList", userLoginDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-03 17:13:38
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(UserLoginDomain userLoginDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", userLoginDomain);
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
