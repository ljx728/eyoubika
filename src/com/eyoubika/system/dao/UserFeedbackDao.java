package com.eyoubika.system.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseDao;
import com.eyoubika.common.PageInfo;
import com.eyoubika.common.YbkException;
import com.eyoubika.info.domain.InvestArticleDomain;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.system.domain.UserFeedbackDomain;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:	__Class__
 * Author:	lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:	1.0 created by lijiaxuan at 2016-01-13 16:30:20
 *==========================================================================================*/

public class UserFeedbackDao extends BaseDao {
	String nameSpace = "ts_UserFeedback";
	private UserFeedbackDomain userFeedbackDomain; // <<attrNote>>

	public UserFeedbackDomain getUserFeedbackDomain() {
		return userFeedbackDomain;
	}

	public void setUserFeedbackDomain(UserFeedbackDomain userFeedbackDomain) {
		this.userFeedbackDomain = userFeedbackDomain;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:	__Method__
	 * Author:	1.0 created by lijiaxuan at 2016-01-13 16:30:20
	 *--------------------------------------------------------------------------------------*/
	public int addUserFeedback(UserFeedbackDomain userFeedbackDomain) {
		int insertId = 0;
		Object ob = new Object();
		try {
			ob = sqlMapClient.insert(nameSpace + ".insertUserFeedback",
					userFeedbackDomain);
		} catch (SQLException e) {
			ob = null;
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020,
					YbkException.DESC000020);
		}
		insertId = Integer.parseInt(ob.toString());
		ob = null;
		return insertId;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:	__Method__
	 * Author:	1.0 created by lijiaxuan at 2016-01-13 16:30:20
	 *--------------------------------------------------------------------------------------*/
	public int deleteUserFeedback(Integer ufId) {
		try {
			return (int) sqlMapClient.delete(nameSpace + ".deleteUserFeedback",
					ufId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020,
					YbkException.DESC000020);
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:	__Method__
	 * Author:	1.0 created by lijiaxuan at 2016-01-13 16:30:20
	 *--------------------------------------------------------------------------------------*/
	public int deleteAll() {
		try {
			return (int) sqlMapClient.delete(nameSpace + ".deleteAll");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020,
					YbkException.DESC000020);
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:	__Method__
	 * Author:	1.0 created by lijiaxuan at 2016-01-13 16:30:20
	 *--------------------------------------------------------------------------------------*/
	public void modifyUserFeedback(UserFeedbackDomain userFeedbackDomain) {
		try {
			sqlMapClient.update(nameSpace + ".updateUserFeedback",
					userFeedbackDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020,
					YbkException.DESC000020);
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:	__Method__
	 * Author:	1.0 created by lijiaxuan at 2016-01-13 16:30:20
	 *--------------------------------------------------------------------------------------*/
	public UserFeedbackDomain findUserFeedback(Integer ufId) {
		try {
			return (UserFeedbackDomain) sqlMapClient.queryForObject(nameSpace
					+ ".selectUserFeedback", ufId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020,
					YbkException.DESC000020);
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:	__Method__
	 * Author:	1.0 created by lijiaxuan at 2016-01-13 16:30:20
	 *--------------------------------------------------------------------------------------*/
	public UserFeedbackDomain findUserFeedbackByDomain(
			UserFeedbackDomain userFeedbackDomain) {
		try {
			return (UserFeedbackDomain) sqlMapClient.queryForObject(nameSpace
					+ ".selectUserFeedbackByDomain", userFeedbackDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020,
					YbkException.DESC000020);
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:	__Method__
	 * Author:	1.0 created by lijiaxuan at 2016-01-13 16:30:20
	 *--------------------------------------------------------------------------------------*/
	public List<UserFeedbackDomain> queryUserFeedback(
			UserFeedbackDomain userFeedbackDomain) {
		try {
			return (List<UserFeedbackDomain>) sqlMapClient.queryForList(
					nameSpace + ".selectUserFeedbackList", userFeedbackDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020,
					YbkException.DESC000020);
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:	__Method__
	 * Author:	1.0 created by lijiaxuan at 2016-01-13 16:30:20
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(UserFeedbackDomain userFeedbackDomain) {
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace + ".isExistByDomain",
					userFeedbackDomain);
			ret = (ob == null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020,
					YbkException.DESC000020);
		}
		return ret;
	}

	// >. <CustomFileTag>

	public PageInfo queryUserFeedbackInPage(
			UserFeedbackDomain userFeedbackDomain, PageInfo pageInfo) {
		try {
			Map<String, Object> map = ConverterUtil
					.objectToMap(userFeedbackDomain);
			int total = (int) sqlMapClient.queryForObject(nameSpace
					+ ".selectUserFeedbackDomainListCount", map);
			pageInfo.setTotalNum(total);
			if (pageInfo.getStart() != null && pageInfo.getLimit() != null) {
				map.put("start", pageInfo.getStart());
				map.put("limit", pageInfo.getLimit());
			}
			if (pageInfo.getFf() != null) {
				String ff = CommonUtil.buildAbbr(nameSpace) + "_"
						+ CommonUtil.upperFirst(pageInfo.getFf());
				map.put("ff", ff);
			}
			if (pageInfo.getRank() != null) {
				map.put("rank", pageInfo.getRank());
			}
			pageInfo.setResult((List<UserFeedbackDomain>) sqlMapClient.queryForList( nameSpace + ".selectUserFeedbackDomainInPage", map));

			return pageInfo;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020,
					YbkException.DESC000020);
		}
	}
	// >. <CustomFileTag>
}
