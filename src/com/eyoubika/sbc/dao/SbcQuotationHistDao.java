package com.eyoubika.sbc.dao;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseDao;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.YbkException;
import com.eyoubika.sbc.domain.SbcQuotationHistDomain;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-08-31 17:48:53
 *==========================================================================================*/
public class SbcQuotationHistDao extends BaseDao {
	String nameSpace = "tp_SbcQuotationHist";
	private SbcQuotationHistDomain sbcQuotationHistDomain;	//<<attrNote>>
	public SbcQuotationHistDomain getSbcQuotationHistDomain(){
		return sbcQuotationHistDomain;
	}
	public void setSbcQuotationHistDomain(SbcQuotationHistDomain sbcQuotationHistDomain){
		this.sbcQuotationHistDomain = sbcQuotationHistDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-31 17:48:53
	 *--------------------------------------------------------------------------------------*/
	public void addSbcQuotationHist(SbcQuotationHistDomain sbcQuotationHistDomain, String exId){
	
		Map<String,Object> map = new HashMap<String,Object>();
		if (CommonUtil.isStringDigit(exId)){
			String table = "tp_Quotation_" + exId;
			map.put("table", table);			
			map.put("sbcId", sbcQuotationHistDomain.getSbcId());
			map.put("type", sbcQuotationHistDomain.getType());
			map.put("closePrice", sbcQuotationHistDomain.getClosePrice());
			map.put("date", sbcQuotationHistDomain.getDate());
			map.put("dealValue", sbcQuotationHistDomain.getDealValue());
			map.put("dealVolume", sbcQuotationHistDomain.getDealVolume());
			map.put("highestPrice", sbcQuotationHistDomain.getHighestPrice());
			map.put("lowestPrice", sbcQuotationHistDomain.getLowestPrice());
			map.put("openingPrice", sbcQuotationHistDomain.getOpeningPrice());
			map.put("riseScope", sbcQuotationHistDomain.getRiseScope());
			map.put("riseValue", sbcQuotationHistDomain.getRiseValue());
		} else {
			throw new YbkException(YbkException.CODE030002, YbkException.CODE030002);
		}
		try {
			sqlMapClient.insert(nameSpace +".insertSbcQuotationHist", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	

	
	

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		querySbcQuotationHistByParams
	 * Author:		1.0 created by lijiaxuan at 2015年9月6日 上午11:30:45
	 *--------------------------------------------------------------------------------------*/
	public List<SbcQuotationHistDomain> querySbcQuotationHistByParams(Map<String, Object> map){
		try {
			return (List<SbcQuotationHistDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcQuotationHistListByParams", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-31 17:48:53
	 *--------------------------------------------------------------------------------------*/
	public int deleteSbcQuotationHist(Integer sbcId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteSbcQuotationHist", sbcId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-31 17:48:53
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
	 * Author:		1.0 created by lijiaxuan at 2015-08-31 17:48:53
	 *--------------------------------------------------------------------------------------*/
	public void modifySbcQuotationHist(SbcQuotationHistDomain sbcQuotationHistDomain){
		try {
			sqlMapClient.update(nameSpace +".updateSbcQuotationHist", sbcQuotationHistDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public void modifySbcQuotationHist(SbcQuotationHistDomain sbcQuotationHistDomain, String exId){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			if (CommonUtil.isStringDigit(exId)){
				String table = "tp_Quotation_"  + exId;
				map.put("table", table);			
				map.put("sbcId", sbcQuotationHistDomain.getSbcId());
				map.put("type", sbcQuotationHistDomain.getType());
				map.put("closePrice", sbcQuotationHistDomain.getClosePrice());
				map.put("date", sbcQuotationHistDomain.getDate());
				map.put("dealValue", sbcQuotationHistDomain.getDealValue());
				map.put("dealVolume", sbcQuotationHistDomain.getDealVolume());
				map.put("highestPrice", sbcQuotationHistDomain.getHighestPrice());
				map.put("lowestPrice", sbcQuotationHistDomain.getLowestPrice());
				map.put("openingPrice", sbcQuotationHistDomain.getOpeningPrice());
				map.put("riseScope", sbcQuotationHistDomain.getRiseScope());
				map.put("riseValue", sbcQuotationHistDomain.getRiseValue());
			} else {
				throw new YbkException(YbkException.CODE030002, YbkException.CODE030002);
			}
			
			sqlMapClient.update(nameSpace +".updateSbcQuotationHistByParams", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	public int deleteSbcQuotationHist(SbcQuotationHistDomain sbcQuotationHistDomain, String exId){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			if (CommonUtil.isStringDigit(exId)){
				String table = "tp_Quotation_" + exId;
				map.put("table", table);			
				map.put("sbcId", sbcQuotationHistDomain.getSbcId());
				map.put("type", sbcQuotationHistDomain.getType());
				map.put("date", sbcQuotationHistDomain.getDate());
			} else {
				throw new YbkException(YbkException.CODE030002, YbkException.CODE030002);
			}
			
			return (int)sqlMapClient.delete(nameSpace +".deleteSbcQuotationHistByParams", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-31 17:48:53
	 *--------------------------------------------------------------------------------------*/
	public SbcQuotationHistDomain findSbcQuotationHist(Integer sbcId){
		try {
			return (SbcQuotationHistDomain) sqlMapClient.queryForObject(nameSpace +".selectSbcQuotationHist", sbcId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-31 17:48:53
	 *--------------------------------------------------------------------------------------*/
	public SbcQuotationHistDomain findSbcQuotationHistByDomain(SbcQuotationHistDomain sbcQuotationHistDomain){
		try {
			return (SbcQuotationHistDomain) sqlMapClient.queryForObject(nameSpace +".selectSbcQuotationHistByDomain", sbcQuotationHistDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-31 17:48:53
	 *--------------------------------------------------------------------------------------*/
	public List<SbcQuotationHistDomain> querySbcQuotationHist(SbcQuotationHistDomain sbcQuotationHistDomain){
		try {
			return (List<SbcQuotationHistDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcQuotationHistList", sbcQuotationHistDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-08-31 17:48:53
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(SbcQuotationHistDomain sbcQuotationHistDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", sbcQuotationHistDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	/*public List<SbcQuotationHistDomain> querySbcQuotationHistory(Integer sbcId){
		try {
			return (List<SbcQuotationHistDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcQuotationHistory", sbcId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}*/
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		querySbcQuotationHistory
	 * Author:		1.0 created by lijiaxuan at 2015年9月6日 上午9:23:39
	 *--------------------------------------------------------------------------------------*/
	public List<SbcQuotationHistDomain> querySbcQuotationHistory(String sbcId, String exId, String interval){
		Map<String,Object> map = new HashMap<String,Object>();
		if (CommonUtil.isStringDigit(exId)){
			String table = "tp_Quotation_"  + exId;
			map.put("table", table);			
			map.put("sbcId", sbcId);
			map.put("type", interval);
		}		
		try {
			return (List<SbcQuotationHistDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcQuotationHistoryLimit", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		querySbcClosePrice
	 * Author:		1.0 created by lijiaxuan at 2015年10月21日 上午10:50:25
	 *--------------------------------------------------------------------------------------*/
	public List<SbcQuotationHistDomain> queryDateQuotationHistory(String exId, String interval, String date ){
		Map<String,Object> map = new HashMap<String,Object>();
		if (CommonUtil.isStringDigit(exId)){
			String table = "tp_Quotation_"  + exId;
			map.put("table", table);			
			map.put("date", date);
			map.put("type", interval);
		}		
		try {
			return (List<SbcQuotationHistDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcQuotationHistory", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		findLastIntvalSbcQuotation
	 * Author:		1.0 created by lijiaxuan at 2015年9月6日 上午11:30:47
	 *--------------------------------------------------------------------------------------*/
	public SbcQuotationHistDomain findLastIntvalSbcQuotation(Map<String, Object> map){	
		try {
			return (SbcQuotationHistDomain) sqlMapClient.queryForObject(nameSpace +".selectLastIntvalSbcQuotation", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		findLastIntvalSbcQuotation
	 * Author:		1.0 created by lijiaxuan at 2015年9月6日 上午11:30:47
	 *--------------------------------------------------------------------------------------*/
	public SbcQuotationHistDomain findLastNIntvalSbcQuotation(Map<String, Object> map){	
		
		try {
			return (SbcQuotationHistDomain) sqlMapClient.queryForObject(nameSpace +".selectLastNIntvalSbcQuotation", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	public List<SbcQuotationHistDomain> querySbcQuotationBetweenDate(String exId, String sbcId, String type, String startDate, String endDate){	
		Map<String,Object> map = new HashMap<String,Object>();
		 String table = "tp_Quotation_"  + exId;
		 map.put("table", table);			
		 map.put("sbcId", sbcId);
		 map.put("type", CommonVariables.TIME_INTERVAL_DAY);
		 map.put("startDate", startDate);
		 map.put("endDate", endDate);

		try {
			return (List<SbcQuotationHistDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcQuotationBetweenDate", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	public List<SbcQuotationHistDomain> querySbcQuotationAfterDate(String exId, String sbcId, String type, String date){	
		Map<String,Object> map = new HashMap<String,Object>();
		 String table = "tp_Quotation_"  + exId;
		 map.put("table", table);			
		 map.put("sbcId", sbcId);
		 map.put("type", CommonVariables.TIME_INTERVAL_DAY);
		 map.put("date", date);

		try {
			return (List<SbcQuotationHistDomain>) sqlMapClient.queryForList(nameSpace +".selectSbcQuotationAfterDate", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	
	public SbcQuotationHistDomain findSbcQuotationHist(String exId, String sbcId, String type, String date ){
		Map<String,Object> map = new HashMap<String,Object>();
		 String table = "tp_Quotation_" + exId;
		 map.put("table", table);			
		 map.put("sbcId", sbcId);
		 map.put("type", CommonVariables.TIME_INTERVAL_DAY);
		 map.put("date", date);
		try {
			return (SbcQuotationHistDomain) sqlMapClient.queryForObject(nameSpace +".selectSbcQuotationByParam", map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	//>. <CustomFileTag>
}
