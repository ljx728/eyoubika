package com.eyoubika.sbc.dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseDao;
import com.eyoubika.common.PageInfo;
import com.eyoubika.common.YbkException;
import com.eyoubika.sbc.domain.SbcBasicDomain;
import com.eyoubika.sbc.domain.SbcSimpleDomain;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-27 16:15:45
 *==========================================================================================*/
public class SbcBasicDao extends BaseDao {
	String nameSpace = "tp_SbcBasicInfo";
	private SbcBasicDomain sbcBasicDomain;	//<<attrNote>>
	public SbcBasicDomain getSbcBasicDomain(){
		return sbcBasicDomain;
	}
	public void setSbcBasicDomain(SbcBasicDomain sbcBasicDomain){
		this.sbcBasicDomain = sbcBasicDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-27 16:15:45
	 *--------------------------------------------------------------------------------------*/
	public int addSbcBasic(SbcBasicDomain sbcBasicDomain){
		int insertId = 0;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.insert(nameSpace +".insertSbcBasic", sbcBasicDomain);
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-27 16:15:45
	 *--------------------------------------------------------------------------------------*/
	public int deleteSbcBasic(Integer sbcId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteSbcBasic", sbcId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-27 16:15:45
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-27 16:15:45
	 *--------------------------------------------------------------------------------------*/
	public void modifySbcBasic(SbcBasicDomain sbcBasicDomain){
		try {
			sqlMapClient.update(nameSpace +".updateSbcBasic", sbcBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-27 16:15:45
	 *--------------------------------------------------------------------------------------*/
	public SbcBasicDomain findSbcBasic(Integer sbcId){
		try {
			return (SbcBasicDomain) sqlMapClient.queryForObject(nameSpace +".selectSbcBasic", sbcId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-27 16:15:45
	 *--------------------------------------------------------------------------------------*/
	public SbcBasicDomain findSbcBasicByDomain(SbcBasicDomain sbcBasicDomain){
		try {
			return (SbcBasicDomain) sqlMapClient.queryForObject(nameSpace +".selectSbcBasicByDomain", sbcBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-27 16:15:45
	 *--------------------------------------------------------------------------------------*/
	public List<SbcBasicDomain> querySbcBasic(SbcBasicDomain sbcBasicDomain){
		try {
			return (List<SbcBasicDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcBasicList", sbcBasicDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		querySbcBasicInPage
	 * Author:		1.0 created by lijiaxuan at 2015年12月16日 下午3:56:02
	 *--------------------------------------------------------------------------------------*/
	public List<SbcBasicDomain> querySbcBasicInPage(SbcBasicDomain sbcBasicDomain, PageInfo pageInfo){
		try {
			Map<String,Object> map = ConverterUtil.objectToMap(sbcBasicDomain);		
			int total =  (int) sqlMapClient.queryForObject(nameSpace +".selectSbcBasicListCount", map);		
			pageInfo.setTotalNum(total);
			
			if (pageInfo.getStart()!= null && pageInfo.getLimit() != null){
				map.put("start", pageInfo.getStart()); 
				map.put("limit", pageInfo.getLimit()); 			
			}
			if (pageInfo.getFf()!= null){
				String ff = CommonUtil.buildAbbr(nameSpace) + "_" + CommonUtil.upperFirst(pageInfo.getFf());
				map.put("ff", ff);
			}
			if (pageInfo.getRank()!= null){			
				map.put("rank", pageInfo.getRank());
			}
			if (sbcBasicDomain.getExId() != null){
				String[] exIdArray = sbcBasicDomain.getExId().split(",");
				List<String> exIdList = new ArrayList<String>();
				int size = exIdArray.length;
				for (int i = 0; i < size; i++){  
					if (!exIdArray[i].equals("")){
						exIdList.add(exIdArray[i]);
					}		
				}
				
				map.put("exIds", exIdList);
			}
			
			return (List<SbcBasicDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcBasicInPage", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-27 16:15:45
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(SbcBasicDomain sbcBasicDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", sbcBasicDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	public Integer findSbcIdBySbcCode(String sbcCode){
		try {
			return (Integer) sqlMapClient.queryForObject(nameSpace +".selectSbcIdBySbcCode", sbcCode);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public List<SbcSimpleDomain> fuzzyQueryBySbcName(String sbcName){
		try {
			return (List<SbcSimpleDomain>) sqlMapClient.queryForList(nameSpace +".fuzzyQueryBySbcName", sbcName);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public List<SbcSimpleDomain> fuzzyQueryBySbcCode(String sbcCode){
		try {
			return (List<SbcSimpleDomain>) sqlMapClient.queryForList(nameSpace +".fuzzyQueryBySbcCode", sbcCode);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		findSbcIdByName
	 * Author:		1.0 created by lijiaxuan at 2015年8月29日 上午11:06:56
	 *--------------------------------------------------------------------------------------*/
	public Integer findSbcIdByName(String sbcName, String exId){
		Map<String,String> map = new HashMap<String, String>();
		map.put("sbcName", sbcName);
		map.put("exId", exId);
		try {
			return (Integer) sqlMapClient.queryForObject(nameSpace +".findSbcIdByName", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		findSbcIdByCode
	 * Author:		1.0 created by lijiaxuan at 2015年11月13日 下午5:04:39
	 *--------------------------------------------------------------------------------------*/
	public Integer findSbcIdByCode(String sbcCode, String exId){
		Map<String,String> map = new HashMap<String, String>();
		map.put("sbcCode", sbcCode);
		map.put("exId", exId);
		try {
			return (Integer) sqlMapClient.queryForObject(nameSpace +".findSbcIdByCode", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		queryAllSbcIds
	 * Author:		1.0 created by lijiaxuan at 2015年8月29日 上午9:35:20
	 *--------------------------------------------------------------------------------------*/
	public List<Integer> queryAllSbcIds(){
		try {
			return (List<Integer>) sqlMapClient.queryForList(nameSpace +".selectAllSbcIdList");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	//>. <CustomFileTag>
}
