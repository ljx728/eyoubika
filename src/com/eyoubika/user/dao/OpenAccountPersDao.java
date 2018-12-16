package com.eyoubika.user.dao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseDao;
import com.eyoubika.common.PageInfo;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.OpenAccountBriefDomain;
import com.eyoubika.user.domain.OpenAccountPersDomain;
import com.eyoubika.util.ConverterUtil;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-24 15:36:34
 *==========================================================================================*/
public class OpenAccountPersDao extends BaseDao {
	String nameSpace = "tu_OpenAccountPers";
	private OpenAccountPersDomain openAccountPersDomain;	//<<attrNote>>
	public OpenAccountPersDomain getOpenAccountPersDomain(){
		return openAccountPersDomain;
	}
	public void setOpenAccountPersDomain(OpenAccountPersDomain openAccountPersDomain){
		this.openAccountPersDomain = openAccountPersDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:34
	 *--------------------------------------------------------------------------------------*/
	public void addOpenAccountPers(OpenAccountPersDomain openAccountPersDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertOpenAccountPers", openAccountPersDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:34
	 *--------------------------------------------------------------------------------------*/
	public int deleteOpenAccountPers(String openNo){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteOpenAccountPers", openNo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:34
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:34
	 *--------------------------------------------------------------------------------------*/
	public void modifyOpenAccountPers(OpenAccountPersDomain openAccountPersDomain){
		try {
			sqlMapClient.update(nameSpace +".updateOpenAccountPers", openAccountPersDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:34
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountPersDomain findOpenAccountPers(String openNo){
		try {
			return (OpenAccountPersDomain) sqlMapClient.queryForObject(nameSpace +".selectOpenAccountPers", openNo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:34
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountPersDomain findOpenAccountPersByDomain(OpenAccountPersDomain openAccountPersDomain){
		try {
			return (OpenAccountPersDomain) sqlMapClient.queryForObject(nameSpace +".selectOpenAccountPersByDomain", openAccountPersDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:34
	 *--------------------------------------------------------------------------------------*/
	public List<OpenAccountPersDomain> queryOpenAccountPers(OpenAccountPersDomain openAccountPersDomain){
		try {
			return (List<OpenAccountPersDomain>) sqlMapClient.queryForList(nameSpace +".selectOpenAccountPersList", openAccountPersDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-24 15:36:34
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(OpenAccountPersDomain openAccountPersDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", openAccountPersDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	
	public PageInfo queryOpenAccountInPage(OpenAccountBriefDomain openAccountBriefDomain, PageInfo pageInfo){
		try {
			//this.setPage();
			Map<String,Object> map = ConverterUtil.objectToMap(openAccountBriefDomain);		
			int total =  (int) sqlMapClient.queryForObject(nameSpace +".selectOpenAccountListCount", map);
		
			pageInfo.setTotalNum(total);
			
			if (pageInfo.getStart()!= null && pageInfo.getLimit() != null){
				map.put("start", pageInfo.getStart()); 
				map.put("limit", pageInfo.getLimit()); 
				map.put("ff", pageInfo.getFf());
				map.put("rank", pageInfo.getRank());
			}
		
			pageInfo.setResult((List<OpenAccountBriefDomain>) sqlMapClient.queryForList(nameSpace +".selectOpenAccountListInPage", map));
			return pageInfo;//(List<NewsBasicDomain>) sqlMapClient.queryForList(nameSpace +".selectNewsBasicListInPage", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	//>. <CustomFileTag>
}
