package com.eyoubika.user.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.UserBasicDomain;
//>. <CustomImportTag>
import com.eyoubika.user.domain.UserInfoDomain;
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-17 15:22:56
 *==========================================================================================*/
public class UserBasicDao extends BaseDao {
	String nameSpace = "tu_UserBasicInfo";
	private UserBasicDomain userBasicDomain;	//<<attrNote>>
	public UserBasicDomain getUserBasicDomain(){
		return userBasicDomain;
	}
	public void setUserBasicDomain(UserBasicDomain userBasicDomain){
		this.userBasicDomain = userBasicDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-17 15:22:56
	 *--------------------------------------------------------------------------------------*/
	public int addUserBasic(UserBasicDomain userBasicDomain){
		int insertId = 0;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.insert(nameSpace +".insertUserBasic", userBasicDomain);
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-17 15:22:56
	 *--------------------------------------------------------------------------------------*/
	public int deleteUserBasic(Integer userId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteUserBasic", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-17 15:22:56
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-17 15:22:56
	 *--------------------------------------------------------------------------------------*/
	public void modifyUserBasic(UserBasicDomain userBasicDomain){
		try {
			sqlMapClient.update(nameSpace +".updateUserBasic", userBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-17 15:22:56
	 *--------------------------------------------------------------------------------------*/
	public UserBasicDomain findUserBasic(Integer userId){
		try {
			return (UserBasicDomain) sqlMapClient.queryForObject(nameSpace +".selectUserBasic", userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-17 15:22:56
	 *--------------------------------------------------------------------------------------*/
	public UserBasicDomain findUserBasicByDomain(UserBasicDomain userBasicDomain){
		try {
			return (UserBasicDomain) sqlMapClient.queryForObject(nameSpace +".selectUserBasicByDomain", userBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-17 15:22:56
	 *--------------------------------------------------------------------------------------*/
	public List<UserBasicDomain> queryUserBasic(UserBasicDomain userBasicDomain){
		try {
			return (List<UserBasicDomain>) sqlMapClient.queryForList(nameSpace +".selectUserBasicList", userBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-17 15:22:56
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(UserBasicDomain userBasicDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", userBasicDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
		/*--------------------------------------------------------------------------------------*
		 * Description:	是否手机号已经存在
		 * Method:		isExistPnumber
		 * Parameters:	String pnumber
		 * History:		1.0 created by lijiaxuan at 2015-5-24 11:45:22
		 *--------------------------------------------------------------------------------------*/
		public boolean isExistPnumber(String pnumber){
			boolean ret = true;
			Object ob = new Object();
			try {
				ob =  sqlMapClient.queryForObject(nameSpace +".isExistPnumber", pnumber);
				ret = (ob==null) ? false : true;			
			} catch (SQLException e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
			}
			return ret;
		}
		/*--------------------------------------------------------------------------------------*
		 * Description:	获取用户加盐
		 * Method:		getUserSalt
		 * Parameters:	int userId
		 * History:		1.0 created by lijiaxuan at 2015-5-24 12:03:11
		 *--------------------------------------------------------------------------------------*/
		public String findUserSalt(int userId){
			try {
				return  (String)sqlMapClient.queryForObject(nameSpace +".selectUserSalt", userId, new String());				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
			}
		}
		/*--------------------------------------------------------------------------------------*
		 * Description:	根据UserId获取token
		 * Method:		getUserInfo
		 * Parameters:	int userId
		 * History:		1.0 created by lijiaxuan at 2015-5-24 12:20:44
		 *--------------------------------------------------------------------------------------*/
		public String findUserToken(int userId){
			try {
				return  (String)sqlMapClient.queryForObject(nameSpace +".selectUserToken", userId, new String());				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
			}
		}
		/*--------------------------------------------------------------------------------------*
		 * Description:	根据手机号获取用户信息
		 * Method:		getUserInfoByPnumber
		 * Parameters:	String pnumber
		 * History:		1.0 created by lijiaxuan at 2015-5-24 12:13:28
		 *--------------------------------------------------------------------------------------*/
		public UserBasicDomain findUserBasicByPnumber(String pnumber){
			try {
				return (UserBasicDomain)sqlMapClient.queryForObject(nameSpace +".selectUserBasicByPnumber", pnumber);//, userDmain);
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
			}
		}
		public UserBasicDomain findUserBasicByNickName(String nickName){
			try {
				return (UserBasicDomain)sqlMapClient.queryForObject(nameSpace +".selectUserBasicByNickName", nickName);//, userDmain);
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
			}
		}
		public UserBasicDomain findUserBasicByEmail(String email){
			try {
				return (UserBasicDomain)sqlMapClient.queryForObject(nameSpace +".selectUserBasicByEmail", email);//, userDmain);
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
			}
		}
		
		
		/*--------------------------------------------------------------------------------------*
		 * Description:	根据UserId获取用户信息
		 * Method:		getUserInfo
		 * Parameters:	int userId
		 * History:		1.0 created by lijiaxuan at 2015-5-24 12:17:31
		 *--------------------------------------------------------------------------------------*/
		public UserInfoDomain findUserInfo(int userId){
			try {
				return  (UserInfoDomain)sqlMapClient.queryForObject(nameSpace +".selectUserInfo", userId, new UserInfoDomain());				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
			}
		}
		
		/*--------------------------------------------------------------------------------------*
		 * Description:	获取所有用户
		 * Method:		getAllUser
		 * Parameters:	void
		 * History:		1.0 created by lijiaxuan at 2015-5-24 12:30:21
		 *--------------------------------------------------------------------------------------*/
		/*public List<UserDao> getAllUser() {
			List<UserDao> Users = null;
			try {
			Users = sqlMapClient.queryForList("selectAllUser");
			} catch (SQLException e) {
			e.printStackTrace();
			}
			return Users;
			}*/	
		//>. <CustomFileTag>
}
